package com.pacoportela.android.pedidodanone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Francisco Portela Henche on 26/11/15.
 */
public class AdaptadorTablaYogures extends ArrayAdapter<Dato> implements Serializable{

    public AdaptadorTablaYogures(Context context, List<Dato> objetos){
        super(context, 0, objetos);
    }

    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLista = convertView;
        if(convertView == null){
            itemLista = inflater.inflate(R.layout.layout_fila_tabla, parent, false);
        }

        final Dato item = (Dato)getItem(posicion);

        TextView nombreYogur = (TextView)itemLista.findViewById((R.id.tvNombreYogur));
        final EditText ctaPedida = (EditText)itemLista.findViewById((R.id.etCtaPedida));

        EditText ctaDevol = (EditText)itemLista.findViewById(R.id.etCtaDevolucion);

        nombreYogur.setText(item.getNombre().substring(7));
        ctaPedida.setText(item.getCtaPedida());
        ctaDevol.setText(item.getCtaDevolucion());
        ctaPedida.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText) v;
                item.setCtaPedida(et.getText().toString());
            }
        });
        ctaDevol.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText et = (EditText)v;
                item.setCtaDevolucion(et.getText().toString());
            }
        });
        return itemLista;
    }
}
