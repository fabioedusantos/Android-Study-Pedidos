package com.fabio.professor.pedidos.domain;

public class Item {
    private long idItens;
    private String titulo;
    private int quantidade;
    private double valorUnitario;
    private double subTotal;
    private long idPedido;

    public long getIdItens() {
        return idItens;
    }

    public void setIdItens(long idItens) {
        this.idItens = idItens;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }
}