package com.celeste.backend;

public class Midia {
    private Integer id;
    private String titulo;
    private Tipo tipo;
    private Integer nota;
    private String foto_url;
    private String comentario;

    public Midia() {
    }

    public Midia(Integer id, String titulo, Tipo tipo, Integer nota, String foto_url, String comentario) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.nota = nota;
        this.foto_url = foto_url;
        this.comentario = comentario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
