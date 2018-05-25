package com.fabio.professor.pedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fabio.professor.pedidos.dao.DaoItens;
import com.fabio.professor.pedidos.domain.Item;

public class NovoItemActivity extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtQuantidade;
    private EditText txtValorUnitario;
    private TextView txtSubTotal;
    private Item item;
    private DaoItens daoItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtQuantidade = findViewById(R.id.txtQuantidade);
        txtValorUnitario = findViewById(R.id.txtValorUnitario);
        txtSubTotal = findViewById(R.id.txtSubTotal);

        setTitle("Item");

        Bundle b = getIntent().getExtras();
        if(b == null) {
            Toast.makeText(this, "Não é possível criar um Item",
                    Toast.LENGTH_SHORT).show();
        }

        daoItens = new DaoItens(this);
        item = new Item();
        if(b.getLong("idItem") > 0){
            //alterando
            item.setIdItens(b.getLong("idItem"));
            item = daoItens.get(item);
            txtTitulo.setText(item.getTitulo());
            txtQuantidade.setText(String.valueOf(item.getQuantidade()));
            txtValorUnitario.setText(String.valueOf(item.getValorUnitario()));
            txtSubTotal.setText("R$ " + String.valueOf(item.getSubTotal()));
        } else {
            //inserindo
            item.setIdPedido(b.getLong("idPedido"));
        }

        txtQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                atualiza();
            }
        });
        txtValorUnitario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                atualiza();
            }
        });
    }

    private void atualiza(){
        if(txtQuantidade.getText() == null ||
                txtValorUnitario.getText() == null ||
                txtQuantidade.getText().toString().length() == 0 ||
                txtValorUnitario.getText().toString().length() == 0) {
            return;
        }

        double quantidade = Double.parseDouble(txtQuantidade.getText().toString());
        double valorUnitario = Double.parseDouble(txtValorUnitario.getText().toString());
        double total = quantidade * valorUnitario;
        item.setSubTotal(total);
        txtSubTotal.setText("R$ " + total);
    }

    public void salvar(View v){
        item.setTitulo(txtTitulo.getText().toString());
        item.setQuantidade(Integer.parseInt(txtQuantidade.getText().toString()));
        item.setValorUnitario(Double.parseDouble(txtValorUnitario.getText().toString()));

        boolean test;
        if(item.getIdItens() > 0){
            test = daoItens.update(item);
        } else{
            test = daoItens.insert(item) > 0;
        }

        if(test) {
            Toast.makeText(this, "Salvo!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Erro ao Salvar!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}
