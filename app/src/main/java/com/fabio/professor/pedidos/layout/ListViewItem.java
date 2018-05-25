package com.fabio.professor.pedidos.layout;

import android.view.View;
import android.widget.TextView;

import com.fabio.professor.pedidos.R;

import java.util.List;

public class ListViewItem {
    private TextView item;
    private TextView quantidade;
    private TextView valorUnitario;
    private TextView subTotal;
    private List<Row> itens;

    public ListViewItem(View view, List<Row> itens) {
        this.itens = itens;
        item = view.findViewById(R.id.item);
        quantidade = view.findViewById(R.id.quantidade);
        valorUnitario = view.findViewById(R.id.valorUnitario);
        subTotal = view.findViewById(R.id.subTotal);
    }

    public void setData(int i) {
        item.setText(itens.get(i).getItem());
        quantidade.setText(String.valueOf(itens.get(i).getQuantidade()));
        valorUnitario.setText("R$ " + String.valueOf(itens.get(i).getValorUnitario()));
        subTotal.setText("R$ " + String.valueOf(itens.get(i).getSubTotal()));
    }

}