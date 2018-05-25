package com.fabio.professor.pedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fabio.professor.pedidos.dao.DaoAdapter;
import com.fabio.professor.pedidos.dao.DaoPedidos;
import com.fabio.professor.pedidos.domain.Pedido;
import com.fabio.professor.pedidos.layout.ListViewAdapter;
import com.fabio.professor.pedidos.layout.Row;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DaoPedidos daoPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criamos o banco
        DaoAdapter daoAdapter = new DaoAdapter(this);
        daoAdapter.onCreate(daoAdapter.getWritableDatabase());
        //Criamos o banco
        daoPedidos = new DaoPedidos(this);

        listView = findViewById(R.id.listView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<String> values = new ArrayList<String>();
        final List<Pedido> pedidos = daoPedidos.get();

        if(pedidos.size() > 0) setTitle("Todos Pedidos");
        else setTitle("Não Há Pedidos");

        //inserimos na lista de nomes que será exibida no listview
        for(Pedido p : pedidos){
            values.add(p.getIdPedido() + "\t" + p.getNome() + " R$ " + p.getTotal());
        }
        //inserimos na lista de nomes que será exibida no listview

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido selecionado = pedidos.get(position);
                Intent i = new Intent(MainActivity.this, NovoPedidoActivity.class);
                i.putExtra("idPedido", selecionado.getIdPedido());
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Pedido selecionado = pedidos.get(position);
                boolean test = daoPedidos.delete(selecionado);
                if(test){
                    Toast.makeText(MainActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                    onResume();
                } else{
                    Toast.makeText(MainActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 111, 0, "Novo Pedido");
        menu.add(0, 222, 0, "Atualizar tela");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 111:
                startActivity(new Intent(this,
                        NovoPedidoActivity.class));
                break;
            case 222:
                Toast.makeText(this, "Atualizando...",
                        Toast.LENGTH_SHORT).show();
                onResume();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
