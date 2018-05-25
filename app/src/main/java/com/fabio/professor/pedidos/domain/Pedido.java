package com.fabio.professor.pedidos.domain;

public class Pedido {
    private long idPedido;
    private String nome;
    private double total;

    public Pedido() {}

    public Pedido(String nome) {
        this.nome = nome;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}