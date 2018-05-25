package com.fabio.professor.pedidos.layout;

import com.fabio.professor.pedidos.domain.Item;

public class Row {
    private String item;
    private int quantidade;
    private double valorUnitario;
    private double subTotal;

    public Row(String item, int quantidade, double valorUnitario, double subTotal) {
        this.item = item;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subTotal = subTotal;
    }

    public Row(Item item){
        this.item = item.getTitulo();
        this.quantidade = item.getQuantidade();
        this.valorUnitario = item.getValorUnitario();
        this.subTotal = item.getSubTotal();
    }

    public String getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }
}
