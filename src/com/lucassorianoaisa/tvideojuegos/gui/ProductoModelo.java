package com.lucassorianoaisa.tvideojuegos.gui;

import com.lucassorianoaisa.tvideojuegos.base.Accesorio;
import com.lucassorianoaisa.tvideojuegos.base.Consola;
import com.lucassorianoaisa.tvideojuegos.base.Juego;
import com.lucassorianoaisa.tvideojuegos.base.Producto;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProductoModelo {
    //Lista con todos los productos
    public ArrayList<Producto> listaProducto = new ArrayList<>();

    //devolver la lista con todos los productos
    public ArrayList<Producto> obtenerProductos() {
        return listaProducto;
    }

    //alta Producto
    public void altaConsola(boolean usado, String plataforma,
                            Double precio, LocalDate fechaSalida, int espacio) {
        Consola nuevaConsola = new Consola(usado, plataforma, precio,
                fechaSalida, espacio);
        listaProducto.add(nuevaConsola);
    }

    public void altaJuego(boolean usado, String plataforma, Double precio,
                          LocalDate fechaSalida, String nombre) {
        Juego nuevoJuego= new Juego(usado, plataforma, precio,
                fechaSalida, nombre);
        listaProducto.add(nuevoJuego);
    }

    public void altaAccesorio(boolean usado, String plataforma, Double precio, LocalDate fechaSalida, String tipo) {
        Accesorio nuevoAccesorio = new Accesorio(usado, plataforma, precio,
                fechaSalida, tipo);
        listaProducto.add(nuevoAccesorio);
    }

    //exportar

    public void exportar(File fichero) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation dom = builder.getDOMImplementation();
        Document documento = dom.createDocument( null, "xml",  null);

        Element raiz = documento.createElement("Vehiculos");
        documento.getDocumentElement().appendChild(raiz);

        Element nodoProducto=null;
        Element nodoDatos=null;
        Text texto = null;

        for (Producto unProducto: listaProducto) {
            if (unProducto instanceof Consola) {
                nodoProducto = documento.createElement("Consola");
            }else if(unProducto instanceof Juego){
                nodoProducto = documento.createElement("Juego");
            }else{
                nodoProducto = documento.createElement("Accesorio");
            }
            raiz.appendChild(nodoProducto);

            nodoDatos = documento.createElement("usado");
            nodoProducto.appendChild(nodoDatos);

            //se usa TextNode para poder pasar el booleano a String
            texto = documento.createTextNode(String.valueOf(unProducto.isUsado()));
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("Plataforma");
            nodoProducto.appendChild(nodoDatos);

            texto=documento.createTextNode(unProducto.getPlataforma());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("precio");
            nodoProducto.appendChild(nodoDatos);

            texto=documento.createTextNode(unProducto.getPrecio().toString());
            nodoDatos.appendChild(texto);

            nodoDatos = documento.createElement("fechaSalida");
            nodoProducto.appendChild(nodoDatos);

            texto=documento.createTextNode(String.valueOf
                    (unProducto.getFechaSalida().toString()));
            nodoDatos.appendChild(texto);

            if (unProducto instanceof Consola) {
                nodoDatos = documento.createElement("Espacio");
                nodoProducto.appendChild(nodoDatos);

                texto = documento.createTextNode(String.valueOf(((Consola) unProducto).getEspacio()));
                nodoDatos.appendChild(texto);
            } else if (unProducto instanceof Juego){
                nodoDatos = documento.createElement("Nombre");
                nodoProducto.appendChild(nodoDatos);

                texto = documento.createTextNode(String.valueOf(((Juego) unProducto).getNombre()));
                nodoDatos.appendChild(texto);
            }else{
                nodoDatos = documento.createElement("Tipo");
                nodoProducto.appendChild(nodoDatos);

                texto = documento.createTextNode(String.valueOf(((Accesorio) unProducto).getTipo()));
                nodoDatos.appendChild(texto);
            }
        }
        //guardar fichero
        Source source = new DOMSource(documento);
        Result resultado = new StreamResult(fichero);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, resultado);

    }

    //importar
    public void importarXML(File fichero) throws ParserConfigurationException, IOException, SAXException {
        listaProducto = new ArrayList<Producto>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("Vehiculos")
                .item(0)
                .getChildNodes();

        for (int i = 0; i < listaElementos.getLength(); i++) {
            if (!(listaElementos.item(i) instanceof Element)) {
                continue;
            }
            Element nodoProducto = (Element) listaElementos.item(i);
            String etiqueta = nodoProducto.getTagName();
            // El orden de exportar() es: usado, Plataforma, precio, fechaSalida, dato extra
            boolean usado = Boolean.parseBoolean(nodoProducto.getChildNodes().item(0).getTextContent());
            String plataforma = nodoProducto.getChildNodes().item(1).getTextContent();
            Double precio = Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent());
            LocalDate fecha = LocalDate.parse(nodoProducto.getChildNodes().item(3).getTextContent());
            String extra = nodoProducto.getChildNodes().item(4).getTextContent();

            if (etiqueta.equals("Consola")) {
                listaProducto.add(new Consola(usado, plataforma, precio, fecha, Integer.parseInt(extra)));
            } else if (etiqueta.equals("Juego")) {
                listaProducto.add(new Juego(usado, plataforma, precio, fecha, extra));
            } else if (etiqueta.equals("Accesorio")) {
                listaProducto.add(new Accesorio(usado, plataforma, precio, fecha, extra));
            }
        }
    }


}