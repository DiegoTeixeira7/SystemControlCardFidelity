package com.example.eng221.systemcontrolcardfidelity.Model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

public class Empresa {
    private int idEmpresa;
    private int temSessao;
    private int inadimplente;
    private int pontos;
    private int idMetodo;
    private double reais;
    private String nome;

    public Empresa() {
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getTemSessao() {
        return temSessao;
    }

    public void setTemSessao(int temSessao) {
        this.temSessao = temSessao;
    }

    public int getInadimplente() {
        return inadimplente;
    }

    public void setInadimplente(int inadimplente) {
        this.inadimplente = inadimplente;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public double getReais() {
        return reais;
    }

    public void setReais(double reais) {
        this.reais = reais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String exibirEmpresa() {
        return  getNome();
    }

    public void pontosResgatar(int idCliente, double reais, String codeALpha, String qrCode, int pontoGanhar) {

        ContentValues valores = new ContentValues();
        valores.put("idEmpresa", getIdEmpresa());
        valores.put("idCliente", idCliente);
        valores.put("reais", reais);
        valores.put("pontoGanhar", pontoGanhar);
        valores.put("codeALpha", codeALpha);
        valores.put("qrCode", qrCode);

        BancoDadosSingleton.getInstance().inserir("pontosResgatar", valores);
    }

    public void excluirSolicitacao(int idSolic) {
        BancoDadosSingleton.getInstance().deletar("solicitacoesPontos", "idSolicitacoesPontos='"+idSolic+"'");
    }

}
