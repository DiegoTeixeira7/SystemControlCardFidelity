package com.example.eng221.systemcontrolcardfidelity.Model;

import android.database.Cursor;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

public class ControladoraFachadaSingleton {
    private Cliente cliente;
    private static ControladoraFachadaSingleton INSTANCE = new ControladoraFachadaSingleton();

    private ControladoraFachadaSingleton() {
        daoCliente();
    }

    private void daoCliente(){

        Cursor c = BancoDadosSingleton.getInstance().buscar("cliente",new String[]{"idCliente","nome","email","CPF","temSessao"},"","");

        while(c.moveToNext()){
            int idCliente = c.getColumnIndex("idCliente");
            int nome = c.getColumnIndex("nome");
            int email = c.getColumnIndex("email");
            int CPF = c.getColumnIndex("CPF");
            int temSessao = c.getColumnIndex("temSessao");

            cliente = new Cliente();

            cliente.setIdCliente(c.getInt(idCliente));
            cliente.setNome(c.getString(nome));
            cliente.setEmail(c.getString(email));
            cliente.setCPF(c.getString(CPF));
            cliente.setTemSessao(c.getInt(temSessao));
        }

        c.close();

    }

    public static ControladoraFachadaSingleton getInstance(){
        return INSTANCE;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

}
