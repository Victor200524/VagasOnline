package com.example.vagasapp.classes;

import java.io.Serializable;

public class Empresa implements Serializable {

    private String nome_fantasia;

    private String razao_social;

    private String tipo;

    // Getters
    public String getNomeFantasia() { return nome_fantasia; }
    public String getRazaoSocial() { return razao_social; }
    public String getTipo() { return tipo; }
}