package com.pacoportela.android.pedidodanone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActividadAyuda extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_ayuda);
        TextView textView = (TextView)findViewById(R.id.tvAyuda);
        Button boton = (Button)findViewById(R.id.botonAyuda);
        boton.setOnClickListener(this);
        String textoAyuda = "<body><br><b>Introducir cantidades: </b>" +
                "Pulse sobre cualquier fila de la tabla para introducir las cantidades " +
                "a pedir y de devolución.<br><br><b>Modificar ó borrar un producto: </b>" +
                "Pulse prolongadamente sobre una fila de la tabla para poder modificar un " +
                "producto. Para borrarlo, borre el nombre y pulse <b>Aceptar</b>.<br><br>" +
                "<b>Añadir un producto: </b>Pulse en el menú de la esquina superior derecha " +
                "de la pantalla y seleccione <b>Añadir producto</b>. Escriba el nombre y pulse " +
                "<b>Aceptar</b>.<br><br><b>Borrar las cantidades del pedido: </b>Pulse el menú y " +
                "seleccione <b>Borrar pedido</b>.<br><br><b>Enviar el pedido por correo electrónico: </b>" +
                "Pulse el <b>botón flotante</b> de la parte inferior de la pantalla ó vaya al menú " +
                "y seleccione <b>Enviar Pedido</b>.</body>";
        textView.setText(Html.fromHtml(textoAyuda));
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
