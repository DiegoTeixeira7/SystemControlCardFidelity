package com.example.eng221.systemcontrolcardfidelity.Model;

import android.database.Cursor;
import android.util.Log;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

import java.util.HashMap;
import java.util.Map;

public class ControladoraFachadaSingleton {
    private Cliente cliente;
    private Empresa empresa;
    private static ControladoraFachadaSingleton INSTANCECLIENTE = new ControladoraFachadaSingleton();
    private static ControladoraFachadaSingleton INSTANCEMPRESA;

    public Map<Integer, Ponto> map = null;

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

        map = buscaPontos(cliente.getIdCliente());

        if(map != null && map.size() != 0) {
            cliente.setPonto(map);
        }

        c.close();
    }

    private Map<Integer, Ponto> buscaPontos(int idCliente) {

        try {
            Cursor c = BancoDadosSingleton.getInstance().buscar("pontos",new String[]{"idEmpresa","pontosTotal","pontosResgatar"},"idCliente='"+idCliente+"'","");
            //Cursor c = BancoDadosSingleton.getInstance().buscar("pontos",new String[]{"pontosTotal","pontosRegatar"},"idEmpresa='"+idEmpresa+"' AND idCliente='"+idCliente+"'","");

            Ponto p = null;
            Map<Integer, Ponto> map = new HashMap<Integer, Ponto>();

            while(c.moveToNext()){
                int pontosTotal = c.getColumnIndex("pontosTotal");
                int idEmpresa = c.getColumnIndex("idEmpresa");
                int pontosResgatar = c.getColumnIndex("pontosResgatar");

                p = new Ponto(idCliente, c.getInt(pontosTotal), c.getInt(pontosResgatar));
                map.put(c.getInt(idEmpresa),p);

            }
            c.close();

            return map;

        } catch (Exception e) {
            Log.i("AndroidT","Map vazio");
        }

        return map;
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
            empresa.setIdEmpresa(idEmpresa);
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
