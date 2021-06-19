package br.com.workmed.main.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.workmed.main.entidades.Produto;

public class ProdutoAdapter extends BaseAdapter {

    private final List<Produto> lista;
    private final Context context;

    public ProdutoAdapter(Context context, List<Produto> lista) {
        this.context = context;
        this.lista = lista;
    }


    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        Produto objeto = lista.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(objeto.getDescricao());
        return view;
    }
}
