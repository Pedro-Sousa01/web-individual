package com.cadastro.backend;
import java.time.LocalDate;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private LocalDate dtNascimento;
    private Tipo tipo;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String telefone, LocalDate dtNascimento, Tipo tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dtNascimento = dtNascimento;
        this.tipo = tipo;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNasc) {
        this.dtNascimento = dtNasc;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
