package com.example.eng221.systemcontrolcardfidelity.Model;

public class Ponto {
    public int idPontos;
    public int idEmpresa;
    public int idCliente;
    public int pontosParaValidar = 0;
    public int pontosTotal = 0;
    public int pontosRegatar = 0;

    public Ponto(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getPontosParaValidar() {
        return pontosParaValidar;
    }

    public void setPontosParaValidar(int pontosParaValidar) {
        this.pontosParaValidar = pontosParaValidar;
    }

    public int getIdPontos() {
        return idPontos;
    }

    public void setIdPontos(int idPontos) {
        this.idPontos = idPontos;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getPontosTotal() {
        return pontosTotal;
    }

    public void setPontosTotal(int pontosTotal) {
        this.pontosTotal = pontosTotal;
    }

    public int getPontosRegatar() {
        return pontosRegatar;
    }

    public void setPontosRegatar(int pontosRegatar) {
        this.pontosRegatar = pontosRegatar;
    }
}
