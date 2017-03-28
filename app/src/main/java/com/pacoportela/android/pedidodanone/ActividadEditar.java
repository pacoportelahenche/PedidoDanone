package com.pacoportela.android.pedidodanone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class ActividadEditar extends AppCompatActivity implements View.OnClickListener{
    EditText etNombre;
    int posicion;
    boolean modificando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_edicion);
        etNombre = (EditText)findViewById(R.id.etNombreYogur);
        Button botonModificar = (Button)findViewById(R.id.botonAceptar);
        botonModificar.setOnClickListener(this);
        Button botonCancelar = (Button)findViewById(R.id.botonCancelar);
        botonCancelar.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        posicion = bundle.getInt("posicion");
        modificando = bundle.getBoolean("modificando");

        if(modificando){
            etNombre.setText(nombre);
            etNombre.selectAll();
            setTitle(R.string.title_activity_actividad_editar);
        }
        else{
            etNombre.setText("");
            etNombre.requestFocus();
            setTitle("Añadir producto");
        }
    }

    private void anadirNombre(){
        String nombre = etNombre.getText().toString();
        if(nombre.length() == 0){
            Toast toast = Toast.makeText
                    (getApplicationContext(), "Escriba un nombre por favor", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        List<Dato> lista = ActividadPrincipal.getListaDatos();
        Dato d = new Dato(nombre, "", "");
        lista.add(d);
        Collections.sort(lista);
        ActividadPrincipal.getAdaptador().notifyDataSetChanged();
        finalizar();
    }

    private void modificar(){
        String nombre = etNombre.getText().toString();
        final List<Dato> lista = ActividadPrincipal.getListaDatos();
        final Dato d = lista.get(posicion);
        if(nombre.length() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea borrar este artículo?")
                    .setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lista.remove(d);
                    Collections.sort(lista);
                    ActividadPrincipal.getAdaptador().notifyDataSetChanged();
                    finalizar();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else{
            d.setNombre(nombre);
            ActividadPrincipal.getAdaptador().notifyDataSetChanged();
            finalizar();
        }
    }

    private void finalizar(){
        finish();
    }

    @Override
    public void onClick(View v) {
        Button boton = (Button)v;
        if(boton.getText().toString().equalsIgnoreCase("aceptar")){
            if(modificando){
                modificar();
            }
            else{
                anadirNombre();
            }
        }
        else if(boton.getText().toString().equalsIgnoreCase("cancelar")){
            finalizar();
        }
    }
}
