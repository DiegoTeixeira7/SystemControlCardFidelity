package com.example.eng221.systemcontrolcardfidelity.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

public class Cliente {
    private int idCliente;
    private int temSessao;
    private String nome;
    private String email;
    private String CPF;

    public Cliente() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }


    public int getTemSessao() {
        return temSessao;
    }

    public void setTemSessao(int temSessao) {
        this.temSessao = temSessao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void solictarPontos(Integer idEmpresa, double reais) {
        ContentValues valores = new ContentValues();
        assert idEmpresa != null;
        valores.put("idEmpresa", idEmpresa.toString());
        valores.put("reais", reais);
        valores.put("idCliente", getIdCliente());
        valores.put("nomeC", getNome());
        BancoDadosSingleton.getInstance().inserir("solicitacoesPontos", valores);
    }

    public String exibirSolicitacoes(){
        Cursor c = BancoDadosSingleton.getInstance().buscar("solicitacoesPontos",new String[]{"idCliente","idEmpresa","reais"},"","");

        String aux = "";
        while(c.moveToNext()){
            int idCliente = c.getColumnIndex("idCliente");
            int idEmpresa = c.getColumnIndex("idEmpresa");
            int reais = c.getColumnIndex("reais");

            aux += "idCliente: " + c.getInt(idCliente) + " - idEmpresa: " + c.getInt(idEmpresa) + " - reais: "  + c.getDouble(reais) + "\n\n";
        }

        c.close();

        return  aux;
    }

}
