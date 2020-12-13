package com.example.eng221.systemcontrolcardfidelity.Model;

import android.database.Cursor;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

public class ControladoraFachadaSingleton {
    private Cliente cliente;
    private Empresa empresa;
    private static ControladoraFachadaSingleton INSTANCECLIENTE = new ControladoraFachadaSingleton();
    private static ControladoraFachadaSingleton INSTANCEMPRESA;

    private ControladoraFachadaSingleton() {
        daoCliente();
    }

    public ControladoraFachadaSingleton(int idEmpresa) {
        daoEmpresa(idEmpresa);
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

    private void daoEmpresa(int idEmpresa) {
        Cursor c = BancoDadosSingleton.getInstance().buscar("empresa",new String[]{"idEmpresa","nome","inadimplente","pontos","temSessao","idMetodo","reais"},"idEmpresa='"+idEmpresa+"'","");

        while(c.moveToNext()){
            int nome = c.getColumnIndex("nome");
            int inadimplente = c.getColumnIndex("inadimplente");
            int pontos = c.getColumnIndex("pontos");
            int idMetodo = c.getColumnIndex("idMetodo");
            int reais = c.getColumnIndex("reais");
            int temSessao = c.getColumnIndex("temSessao");

            empresa = new Empresa();

            empresa.setInadimplente(c.getInt(inadimplente));
            empresa.setNome(c.getString(nome));
            empresa.setPontos(c.getInt(pontos));
            empresa.setIdMetodo(c.getInt(idMetodo));
            empresa.setTemSessao(c.getInt(temSessao));
            empresa.setReais(c.getDouble(reais));
            empresa.setIdEmpresa(c.getInt(idEmpresa));
        }

        c.close();
    }

    public static ControladoraFachadaSingleton getInstance(){
        return INSTANCECLIENTE;
    }

    public static ControladoraFachadaSingleton getInstance(int idEmpresa){
        return INSTANCEMPRESA = new ControladoraFachadaSingleton(idEmpresa);
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public Empresa getEmpresa(){
        return this.empresa;
    }

}
