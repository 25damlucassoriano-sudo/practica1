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
    public ArrayList<Producto> listaProducto;

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
        Consola nuevaConsola = null;
        Juego nuevoJuego = null;
        Accesorio nuevoAccesorio = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(fichero);

        NodeList listaElementos = documento.getElementsByTagName("*");

        for (int i=0; i<listaElementos.getLength(); i++) {
            Element nodoProducto = (Element) listaElementos.item(i);
            if (nodoProducto.getTagName().equals("Consola")) {
                nuevaConsola = new Consola();
                nuevaConsola.setPlataforma(nodoProducto.getChildNodes().item(0).getTextContent());
                //se cambia esta linea y se usa el parse para poder usar el boolean
                nuevaConsola.setUsado(Boolean.parseBoolean(
                        nodoProducto.getChildNodes().item(1).getTextContent()
                ));
                nuevaConsola.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevaConsola.setFechaSalida
                        (LocalDate.parse(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevaConsola.setEspacio(Integer.parseInt(nodoProducto.getChildNodes().item(4).getTextContent()));
                listaProducto.add(nuevaConsola);
            }else if(nodoProducto.getTagName().equals("Juego")){
                nuevoJuego = new Juego();
                nuevoJuego.setPlataforma(nodoProducto.getChildNodes().item(0).getTextContent());
                nuevaConsola.setUsado(Boolean.parseBoolean(
                        nodoProducto.getChildNodes().item(1).getTextContent()
                ));
                nuevoJuego.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevoJuego.setFechaSalida
                        (LocalDate.parse(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevaConsola.setEspacio(Integer.parseInt(nodoProducto.getChildNodes().item(4).getTextContent()));
                listaProducto.add(nuevoJuego);
            }else{
                nuevoAccesorio = new Accesorio();
                nuevoAccesorio.setPlataforma(nodoProducto.getChildNodes().item(0).getTextContent());
                nuevaConsola.setUsado(Boolean.parseBoolean(
                        nodoProducto.getChildNodes().item(1).getTextContent()
                ));
                nuevoAccesorio.setPrecio(Double.parseDouble(nodoProducto.getChildNodes().item(2).getTextContent()));
                nuevoAccesorio.setFechaSalida
                        (LocalDate.parse(nodoProducto.getChildNodes().item(3).getTextContent()));
                nuevoAccesorio.setTipo(nodoProducto.getChildNodes().item(4).getTextContent());
                listaProducto.add(nuevoAccesorio);
            }
        }

    }


}