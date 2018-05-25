package com.fabio.professor.pedidos.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fabio.professor.pedidos.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Row> itens;

    public ListViewAdapter(Context context, List<Row> itens) {
        this.context = context;
        this.itens = itens;
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        ListViewItem item;

        if (result == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inflater.inflate(R.layout.listview_template, parent, false);
            item = new ListViewItem(result, itens);
            result.setTag(item);
        } else {
            item = (ListViewItem) convertView.getTag();
        }

        item.setData(position);

        return result;
    }
}
