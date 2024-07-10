package com.app.libros.principal;

import com.app.libros.model.Datos;
import com.app.libros.model.DatosLibros;
import com.app.libros.service.ConsumoAPI;
import com.app.libros.service.ConvierteDatos;

import java.net.URL;
import java.util.Comparator;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/sarch=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversorDatos = new ConvierteDatos();
    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversorDatos.obtenerDatos(json, Datos.class);
        System.out.println(datos);
        //Top 10 más descargados
        System.out.println("Top 10");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10)
                .map(l->l.titulo().toUpperCase())
                .forEach(System.out::println);
        //Busqueda de libros por nombre
    }


}
