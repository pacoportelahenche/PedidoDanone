package com.pacoportela.android.pedidodanone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ActividadPrincipal extends AppCompatActivity {
    private static List<Dato> listaDatos = new ArrayList<Dato>();
    private static String direccionCorreo = "****@****.com";
    private static String asunto = "Elco: 350053637";
    private static String saludo = "Muchas gracias y un cordial saludo.";
    //private static String paco = "pacoportela@yahoo.com";
    private static AdaptadorTablaYogures adaptador;
    private static String LISTA_DATOS = "datosGuardados";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast t = Toast.makeText(this, "Entra en onCreate()", Toast.LENGTH_SHORT);
        //t.show();
        setContentView(R.layout.layout_actividad_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarCorreo();
            }
        });
        fab.hide();
        obtenerPreferencias();
        ListView lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adaptador);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast toast = Toast.makeText(getApplicationContext(),
                        String.valueOf(position),Toast.LENGTH_LONG);
                toast.show();*/
                boolean b = modificarOborrarNombre(position);
                return b;
            }
        });
        lista.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_POINTER_DOWN){
                    int contador = event.getPointerCount();
                    if(contador == 2){
                        LayoutInflater li = getLayoutInflater();
                        View layout = li.inflate(R.layout.layout_urkel,null);
                        Toast t = new Toast(getApplicationContext());
                        t.setView(layout);
                        t.setDuration(Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        t.show();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast t = Toast.makeText(this, "Entra en onDestroy()", Toast.LENGTH_SHORT);
        //t.show();

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //Toast t = Toast.makeText(this, "Entra en onRestart()", Toast.LENGTH_SHORT);
        //t.show();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //Toast t = Toast.makeText(this, "Entra en onStart()", Toast.LENGTH_SHORT);
        //t.show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Toast t = Toast.makeText(this, "Entra en onResume()", Toast.LENGTH_SHORT);
        //t.show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Toast t = Toast.makeText(this, "Entra en onStop", Toast.LENGTH_SHORT);
        //t.show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        guardarDatos();
        //ActividadPrincipal.listaDatos.clear();
        //Toast t = Toast.makeText(this, "Entra en onPause()", Toast.LENGTH_SHORT);
        //t.show();
    }

    @Override
    public void onSaveInstanceState(Bundle guardarEstado){
        guardarEstado.putSerializable(ActividadPrincipal.LISTA_DATOS, adaptador);
    }

    @Override
    public void onRestoreInstanceState(Bundle recuperarEstado){
        this.adaptador =
                (AdaptadorTablaYogures)recuperarEstado.
                        getSerializable(ActividadPrincipal.LISTA_DATOS);
        ListView lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(adaptador);

    }

    protected boolean modificarOborrarNombre(int position){
        Intent intent = new Intent(getBaseContext(), ActividadEditar.class);
        intent.putExtra("nombre", listaDatos.get(Integer.valueOf(position)).getNombre());
        intent.putExtra("posicion", position);
        intent.putExtra("modificando", true);
        startActivity(intent);
        return false;
    }

    protected void obtenerPreferencias(){
        SharedPreferences preferences = getSharedPreferences("danone", Context.MODE_PRIVATE);
        Set<String> nombres = preferences.getStringSet("nombres", null);
        if (nombres == null) {
            listaDatos = new DataSource().getListaDatos();
            Collections.sort(listaDatos);
            adaptador = new AdaptadorTablaYogures(this, listaDatos);
        } else {
            Iterator<String> i = nombres.iterator();

            String nom = "";
            while (i.hasNext()) {
                Dato d = new Dato();
                nom = i.next();
                d.setNombre(nom);
                d.setCtaPedida("");
                d.setCtaDevolucion("");
                listaDatos.add(d);
            }
            //Collections.sort(listaDatos);
            Collections.sort(listaDatos, new Comparator(){
                @Override
                public int compare(Object a, Object b) {
                    Dato d1 = (Dato)a;
                    Dato d2 = (Dato)b;
                    return d1.getNombre().substring(9).compareTo(d2.getNombre().substring(9));
                }
            });
            adaptador = new AdaptadorTablaYogures(this, listaDatos);
        }
    }

    protected String recogerDatosHtml() {
        String mydate =
                DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String datos = "<html><body><h3>Pedido Elco " + mydate + " </h3>" +
                "<table border=1><tr><th>C&oacutedigo<th>Nombre</th><th>Pedido</th>" +
                "<th>Devoluci&oacuten</th></tr>";
        String cod = "";
        String nom = "";
        String ped = "";
        String dev = "";
        Dato dato = null;
        for (int i = 0; i < listaDatos.size(); i++) {
            dato = listaDatos.get(i);
            cod = dato.getNombre().substring(0, 6);
            nom = dato.getNombre().substring(8);
            ped = dato.getCtaPedida();
            dev = dato.getCtaDevolucion();
            if (ped.length() > 0 || dev.length() > 0) {
                datos += "<tr><td>"+cod+"</td><td>"+nom+"</td>";
                //datos += "<tr><td>"+nom+"</td>";
                if (ped.length() > 0) {
                    datos += "<td>"+ped+"</td>";
                }
                else if(ped.length() <= 0){
                    datos += "<td>&nbsp</td>";
                }
                if (dev.length() > 0) {
                    datos += "<td>"+dev+"</td>";
                }
                else if(dev.length() <= 0) {
                    datos += "<td>&nbsp</td>";
                }
                datos += "</tr>";
            }
        }
        datos += "</table><br>Muchas gracias y un saludo.</body></html>";
        return datos;
    }

    protected void enviarCorreo() {
        File fichero = grabarFichero();
        Intent itSend =
                new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto",ActividadPrincipal.direccionCorreo, null));
        itSend.putExtra(Intent.EXTRA_SUBJECT, ActividadPrincipal.asunto);
        //itSend.putExtra(Intent.EXTRA_TEXT, ActividadPrincipal.saludo);
        itSend.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fichero));
        startActivity(itSend);
    }

    protected File grabarFichero(){
        String datos = recogerDatosHtml();
        File tarjeta = Environment.getExternalStorageDirectory();
        File ficheroDatos = new File(tarjeta.getAbsolutePath(), "pedidoElco.html");
        try{
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(ficheroDatos));
            osw.write(datos);
            osw.flush();
            osw.close();
            Toast.makeText
                    (this, "Fichero de datos grabado correctamente", Toast.LENGTH_LONG).show();
        }
        catch (IOException ex){
            Toast.makeText
                    (this, "No se pudo grabar el fichero de datos", Toast.LENGTH_LONG).show();
        }
        return ficheroDatos;
    }

    protected void guardarDatos(){
        SharedPreferences preferences = getSharedPreferences("danone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();
        Set set = new HashSet<String>();
        for(int i = 0; i < listaDatos.size(); i++){
            set.add(listaDatos.get(i).getNombre());
        }
        editor.putStringSet("nombres", set);
        editor.commit();
    }
    protected void borrarDatos() {
        Dato d = null;
        for (int i = 0; i < listaDatos.size(); i++) {
            d = listaDatos.get(i);
            d.setCtaPedida("");
            d.setCtaDevolucion("");
            adaptador.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        if (id == R.id.enviar) {
            enviarCorreo();
        }
        if (id == R.id.borrar) {
            borrarDatos();
        }
        if(id == R.id.anadir){
            Intent intent = new Intent(getBaseContext(), ActividadEditar.class);
            intent.putExtra("modificando", false);
            startActivity(intent);
        }
        if(id == R.id.ayuda){
            Intent intent = new Intent(getBaseContext(), ActividadAyuda.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<Dato> getListaDatos(){
        return ActividadPrincipal.listaDatos;
    }

    public static AdaptadorTablaYogures getAdaptador(){
        return ActividadPrincipal.adaptador;
    }
}
