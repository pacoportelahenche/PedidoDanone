package com.pacoportela.android.pedidodanone;

import java.util.Comparator;

/**
 * Created by Francisco Portela Henche on 26/11/15.
 */
public class Dato implements Comparable{
    private String nombre;
    private String ctaPedida;
    private String ctaDevolucion;

    public Dato(){
        this.nombre = "";
        this.ctaPedida = "";
        this.ctaDevolucion = "";
    }

    public Dato (String nom, String cped, String cdev){
        this.nombre = nom;
        this.ctaPedida = cped;
        this.ctaDevolucion = cdev;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nom){ this.nombre = nom; }

    public String getCtaPedida(){
        return this.ctaPedida;
    }

    public void setCtaPedida(String cta){
        this.ctaPedida = cta;
    }

    public String getCtaDevolucion(){
        return this.ctaDevolucion;
    }

    public void setCtaDevolucion(String dev){
        this.ctaDevolucion = dev;
    }

    @Override
    public int compareTo(Object otro) {
        Dato d = (Dato)otro;
        return nombre.substring(2).compareTo(d.getNombre().substring(2));
    }
}
