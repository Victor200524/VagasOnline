package com.example.vagasapp.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vaga implements Serializable {

    private String registro;

    private Empresa empresa;

    private String cargo;

    private String cidade;

    private String estado;

    private String pre_requisitos;

    private String formacao;

    private String conhecimentos_requeridos;

    private String regime;

    private String jornada_trabalho;

    private String remuneracao;

    // Getters
    public String getRegistro() { return registro; }
    public Empresa getEmpresa() { return empresa; }
    public String getCargo() { return cargo; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getPreRequisitos() { return pre_requisitos; }
    public String getFormacao() { return formacao; }
    public String getConhecimentosRequeridos() { return conhecimentos_requeridos; }
    public String getRegime() { return regime; }
    public String getJornadaTrabalho() { return jornada_trabalho; }
    public String getRemuneracao() { return remuneracao; }
}