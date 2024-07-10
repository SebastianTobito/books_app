package com.app.libros.principal;

import com.app.libros.model.Datos;
import com.app.libros.model.DatosLibros;
import com.app.libros.service.ConsumoAPI;
import com.app.libros.service.ConvierteDatos;

import javax.swing.text.html.Option;
import java.net.URL;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversorDatos = new ConvierteDatos();
    private Scanner scanner = new Scanner(System.in);
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
        System.out.println("Di el nombre del libro que quieres buscar");
        var nombreLibro = scanner.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search="+nombreLibro.replace(" ", "+"));
        var busquedaPorNombre = conversorDatos.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = busquedaPorNombre.resultados().stream()
                .filter(l->l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
        }else{
            System.out.println("libro no encontrado");
        }

        //Estadisticas de los libros
        DoubleSummaryStatistics estdisticas = datos.resultados().stream()
                .filter(d->d.numeroDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("Cantidad media de descargas "+estdisticas.getAverage());
        System.out.println("Cantidad maxima de descargas "+ estdisticas.getMax());
        System.out.println("Cantidad minima de descargas "+ estdisticas.getMin());
        System.out.println("Cantidad de registros que se evaluaron " + estdisticas.getCount());
    }

}
