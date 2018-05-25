package com.fabio.professor.pedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fabio.professor.pedidos.dao.DaoItens;
import com.fabio.professor.pedidos.dao.DaoPedidos;
import com.fabio.professor.pedidos.domain.Item;
import com.fabio.professor.pedidos.domain.Pedido;
import com.fabio.professor.pedidos.layout.ListViewAdapter;
import com.fabio.professor.pedidos.layout.Row;

import java.util.ArrayList;
import java.util.List;

public class NovoPedidoActivity extends AppCompatActivity {

    private EditText txtNome;
    private TextView txtValor;
    private ListView listView;
    private DaoPedidos daoPedidos;
    private DaoItens daoItens;
    private Pedido pedido;
    private double total;
    private boolean isAlteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pedido);

        txtNome = findViewById(R.id.txtNome);
        txtValor = findViewById(R.id.txtValor);
        listView = findViewById(R.id.listView);

        setTitle("Pedido");

        daoPedidos = new DaoPedidos(this);
        daoItens = new DaoItens(this);

        Bundle b = getIntent().getExtras();
        long idPedido;
        if(b != null) { //alterando um pedido
            idPedido = b.getLong("idPedido");
            pedido = new Pedido();
            pedido.setIdPedido(idPedido);
            pedido = daoPedidos.get(pedido);
            txtNome.setText(pedido.getNome());
            txtValor.setText("R$ " + pedido.getTotal());
            isAlteracao = true;
        } else{ //criando um novo pedido
            pedido = new Pedido("[{Novo}]");
            idPedido = daoPedidos.insert(pedido);
            pedido.setIdPedido(idPedido);
        }

        if(idPedido < 0) {
            finish();
            Toast.makeText(this, "Erro Interno",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final List<Item> linhas = daoItens.get(pedido);
        List<Row> itens = new ArrayList<>();
        total = 0;
        for (Item i : linhas) {
            Row row = new Row(i);
            total += row.getSubTotal();
            itens.add(row);
        }
        txtValor.setText("R$ " + total);
        ListViewAdapter adapter = new ListViewAdapter(this, itens);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = linhas.get(position);
                Intent i = new Intent(NovoPedidoActivity.this,
                        NovoItemActivity.class);
                i.putExtra("idItem", item.getIdItens());
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = linhas.get(position);
                boolean test = daoItens.delete(item);
                if(test) {
                    Toast.makeText(NovoPedidoActivity.this, "Sucesso",
                        Toast.LENGTH_SHORT).show();
                    onResume();
                } else{
                    Toast.makeText(NovoPedidoActivity.this, "Erro",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 111, 0, "Novo Item");
        menu.add(0, 222, 1, "Salvar");
        menu.add(0, 333, 2, "Cancelar");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 111:
                Intent i = new Intent(this, NovoItemActivity.class);
                i.putExtra("idPedido", pedido.getIdPedido());
                startActivity(i);
                break;
            case 222:
                salvar();
                break;
            case 333:
                if(isAlteracao){
                    finish();
                } else {
                    if (daoPedidos.delete(pedido)) {
                        Toast.makeText(NovoPedidoActivity.this,
                                "Pedido " + pedido.getIdPedido() + " Cancelado",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(NovoPedidoActivity.this,
                                "Erro ao Cancelar",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvar(){
        pedido.setNome(txtNome.getText().toString());
        pedido.setTotal(total);
        if(daoPedidos.update(pedido)){
            Toast.makeText(NovoPedidoActivity.this,
                    "Pedido " + pedido.getIdPedido() + " Salvo",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else{
            Toast.makeText(NovoPedidoActivity.this,
                    "Erro ao Salvar",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
