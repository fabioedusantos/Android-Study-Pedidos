package com.fabio.professor.pedidos.dao;

import android.content.ContentValues;
import android.content.Context;

import com.fabio.professor.pedidos.domain.Item;
import com.fabio.professor.pedidos.domain.Pedido;

import java.util.ArrayList;
import java.util.List;

public class DaoItens {
    private DaoAdapter daoAdapter;

    public DaoItens(Context context){
        daoAdapter = new DaoAdapter(context);
    }

    public long insert(Item i){
        ContentValues values = new ContentValues();
        values.put("titulo", i.getTitulo());
        values.put("quantidade", i.getQuantidade());
        values.put("valorUnitario", i.getValorUnitario());
        values.put("subTotal", i.getSubTotal());
        values.put("idPedido", i.getIdPedido());

        return daoAdapter.queryInsertLastId("itens", values);
    }

    public boolean update(Item i){
        Object[] values = {
                i.getTitulo(),
                i.getQuantidade(),
                i.getValorUnitario(),
                i.getSubTotal(),
                i.getIdPedido(),
                i.getIdItens()
        };

        return daoAdapter.queryExecute(
                "UPDATE itens SET " +
                        "titulo = ?," +
                        "quantidade = ?," +
                        "valorUnitario = ?," +
                        "subTotal = ?," +
                        "idPedido = ? " +
                        "WHERE idItens = ?",
                values
        );
    }

    public boolean delete(Item i){
        Object[] values = {
                i.getIdItens()
        };

        return daoAdapter.queryExecute(
                "DELETE FROM itens WHERE idItens = ?", values
        );
    }

    public List<Item> get(Pedido pedido){
        List<Item> itens = new ArrayList<>();
        String values[] = {
                String.valueOf(pedido.getIdPedido())
        };
        ObjetoBanco obj = daoAdapter.queryConsulta(
                "SELECT " +
                        "idItens, titulo, quantidade, valorUnitario, " +
                        "subTotal, idPedido FROM itens " +
                        "WHERE idPedido = ?",
                values
        );

        for(int i = 0; i < obj.size(); i++) {
            Item item = new Item();
            item.setIdItens(obj.getLong(i, "idItens"));
            item.setTitulo(obj.getString(i, "titulo"));
            item.setQuantidade(obj.getInt(i, "quantidade"));
            item.setValorUnitario(obj.getDouble(i, "valorUnitario"));
            item.setSubTotal(obj.getDouble(i, "subTotal"));
            item.setIdPedido(obj.getLong(i, "idPedido"));

            itens.add(item);
        }

        return itens;
    }

    public Item get(Item i){
        String values[] = {
                String.valueOf(i.getIdItens())
        };
        ObjetoBanco obj = daoAdapter.queryConsulta(
                "SELECT " +
                        "idItens, titulo, quantidade, valorUnitario, " +
                        "subTotal, idPedido FROM itens " +
                        "WHERE idItens = ?",
                values
        );

        if(obj.size() == 0) return null;

        Item item = new Item();
        item.setIdItens(obj.getLong(0, "idItens"));
        item.setTitulo(obj.getString(0, "titulo"));
        item.setQuantidade(obj.getInt(0, "quantidade"));
        item.setValorUnitario(obj.getDouble(0, "valorUnitario"));
        item.setSubTotal(obj.getDouble(0, "subTotal"));
        item.setIdPedido(obj.getLong(0, "idPedido"));

        return item;
    }
}