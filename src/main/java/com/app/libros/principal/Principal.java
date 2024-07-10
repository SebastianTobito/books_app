package com.app.libros.principal;

import com.app.libros.service.ConsumoAPI;
import com.app.libros.service.ConvierteDatos;

import java.net.URL;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversorDatos = new ConvierteDatos();
    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
    }

}
