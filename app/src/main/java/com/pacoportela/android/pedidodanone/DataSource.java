package com.pacoportela.android.pedidodanone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Portela Henche on 27/11/15.
 */
public class DataSource {
    List listaDatos = new ArrayList<Dato>();
    String[] nombresYogures = {" 14049-2-Natural X4"," 14050-2-Natural azuc. X4",
            " 94480-3-Natural X8"," 14051-2-Sabor fresa"," 14052-2-Sabor limón",
            " 14054-2-Sabor macedonia"," 14056-2-Vitalinea natural",
            " 14057-2-Vitalinea natural edul."," 14058-2-Vitalinea limón",
            " 14059-2-Vitalinea fresa"," 81842-3-Oikos nat.azuc."," 80641-2-Oikos fresa",
            " 78154-2-Oikos mediterraneo"," 14037-3-Activia natural"," 14042-6-Activia 0% natural",
            " 32826-3-Activia 0% nat.edulc."," 14046-6-Activia 0% ciruelas",
            " 14047-2-Activia 0% piña"," 14076-2-Activia 0% kiwi"," 16762-2-Activia 0% f.rojos",
            " 22238-3-Activia 0% melocotón"," 14558-3-Activia 0% muesli",
            " 16761-3-Activia 0% cereales"," 14032-3-Activia con fresa",
            " 14039-3-Activia con f.bosque"," 14040-3-Activia fibras c/cereales",
            " 14041-3-Activia fibras c/muesli","  2604-4-Actimel natural",
            "  2594-4-Actimel 0% natural"," 13540-4-Actimel fresa"," 11318-4-Danacol natural",
            " 11319-4-Danacol fresa"," 99657-6-Densia natural"," 98147-4-Danet vainilla",
            "106735-4-Danet choco/nata"," 81586-6-La copa"," 81434-2-Arroz con leche",
            "  7907-2-Cuajada"," 78921-2-Danonino fresa"," 106439-4-Densia forte fresa"};

    public DataSource(){
        for(int i = 0; i < nombresYogures.length; i++){
            listaDatos.add(new Dato(nombresYogures[i], "", ""));
        }
    }

    public List<Dato> getListaDatos(){
        return this.listaDatos;
    }

}