package com.fabio.professor.pedidos.dao;

import android.content.ContentValues;
import android.content.Context;

import com.fabio.professor.pedidos.domain.Pedido;

import java.util.ArrayList;
import java.util.List;

public class DaoPedidos {
    private DaoAdapter daoAdapter;

    public DaoPedidos(Context context){
        daoAdapter = new DaoAdapter(context);
    }

    public long insert(Pedido p){
        ContentValues values = new ContentValues();
        values.put("nome", p.getNome());
        values.put("total", p.getTotal());

        return daoAdapter.queryInsertLastId("pedido", values);
    }

    public boolean update(Pedido p){
        Object[] values = {
                p.getNome(),
                p.getTotal(),
                p.getIdPedido()
        };

        return daoAdapter.queryExecute(
                "UPDATE pedido SET nome = ?, total = ? WHERE idPedido = ?",
                values
        );
    }

    public boolean delete(Pedido p){
        Object[] values = {
                p.getIdPedido()
        };

        if(daoAdapter.queryExecute("" +
                "DELETE FROM itens WHERE idPedido = ?",values)){
            return daoAdapter.queryExecute(
                    "DELETE FROM pedido WHERE idPedido = ?", values
            );
        }
        return false;
    }

    public List<Pedido> get(){
        List<Pedido> pedidos = new ArrayList<>();
        String values[] = {};
        ObjetoBanco obj = daoAdapter.queryConsulta(
                "SELECT idPedido, nome, total FROM pedido ORDER BY idPedido ASC",
                values
        );

        for(int i = 0; i < obj.size(); i++) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(obj.getLong(i, "idPedido"));
            pedido.setNome(obj.getString(i, "nome"));
            pedido.setTotal(obj.getDouble(i, "total"));

            pedidos.add(pedido);
        }

        return pedidos;
    }

    public Pedido get(Pedido p){
        List<Pedido> pedidos = new ArrayList<>();
        String values[] = {
                String.valueOf(p.getIdPedido())
        };
        ObjetoBanco obj = daoAdapter.queryConsulta(
                "SELECT idPedido, nome, total FROM pedido WHERE idPedido  = ?",
                values
        );

        if(obj.size() == 0) return null;

        Pedido pedido = new Pedido();
        pedido.setIdPedido(obj.getLong(0, "idPedido"));
        pedido.setNome(obj.getString(0, "nome"));
        pedido.setTotal(obj.getDouble(0, "total"));

        return pedido;
    }
}