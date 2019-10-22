package xml;

import Entidades.Formacion;
import Entidades.Gol;
import Entidades.Jugador;
import Entidades.Marcador;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExploradorXML {

    private String pathXml;
    private ClassLoader classLoader;
    private InputStream inputStream;
    private DocumentBuilder builder;
    private Document doc;
    private Element root;


    public ExploradorXML(String pathXml) throws ParserConfigurationException, IOException, SAXException {
        //cargamos el XML en un input stream
        this.classLoader = ExploradorXML.class.getClassLoader();
        this.inputStream = classLoader.getResourceAsStream(pathXml);
        // Creamos el parser y parseamos el input stream
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        this.doc = builder.parse(new InputSource(inputStream));
        this.root = doc.getDocumentElement();
    }

    public Document getDocumentNode(){
        return this.doc;
    }

    public Formacion getFormacion(String localidad){
        Formacion formacion = new Formacion();

        //recupero formacion
        Node unEquipo =  doc.getElementsByTagName(localidad).item(0); //devuelve una NodeList de 1
        Node formacionNode = getFormacion(unEquipo);//getUniqueNodeByName(nodosHijosEquipo,"formacion");
        Collection<Node> jugadores = getNodesByName(formacionNode.getChildNodes(), "jugador");

        for(Node jugador : jugadores){
            formacion.agregar(getJugadorByNode(jugador));
        }

        return formacion;
    }

    private Jugador getJugadorByNode(Node jugadorNode){
        String nombreJugador = jugadorNode.getFirstChild().getNodeValue();
        Jugador jugador = new Jugador(nombreJugador);
        return jugador;
    }

    public Marcador getMarcador(){

        //recupero eqipos
        Node equipoLocal =  doc.getElementsByTagName("local").item(0); //devuelve una NodeList de 1
        Node equipoVisitante =  doc.getElementsByTagName("visitante").item(0); //devuelve una NodeList de 1

        String nombreLocal = equipoLocal.getAttributes().item(0).getNodeValue();
        String nombreVisitante = equipoVisitante.getAttributes().item(0).getNodeValue();

        //recupero goles
        Node goles = doc.getElementsByTagName("goles").item(0); //devuelve una NodeList de 1

        Node golesLocalNode = getGolesPorLocalidad(goles,"local");
        Node golesVisitanteNode = getGolesPorLocalidad(goles,"visitante");

        ArrayList<Gol> golesLocales = getGolesByGolesNode(golesLocalNode);
        ArrayList<Gol> golesVisitantes = getGolesByGolesNode(golesVisitanteNode);

        return new Marcador(nombreLocal,nombreVisitante, golesLocales, golesVisitantes);

    }

    private ArrayList<Gol> getGolesByGolesNode(Node golesNode){
        ArrayList<Gol> goles = new ArrayList<Gol>();

        if(golesNode == null){
            return goles;
        }

        Collection<Node> listGolesNode = getNodesByName(golesNode.getChildNodes(), "gol");

        for(Node gol : listGolesNode){
            goles.add(getGolByNode(gol));
        }

        return goles;
    }

    private Gol getGolByNode(Node golNode){
        NodeList datosDelGol = golNode.getChildNodes();
        Node minutoNode = getUniqueNodeByName(datosDelGol, "minuto");
        Node autorNode = getUniqueNodeByName(datosDelGol, "autor");

        String minuto = minutoNode.getFirstChild().getNodeValue();
        String autor = autorNode.getFirstChild().getNodeValue();

        return new Gol(minuto,autor);
    }

    public String getCapitanByLocalidad(String localidad){
        //recupero capitan
        Node unEquipo =  doc.getElementsByTagName(localidad).item(0); //devuelve una NodeList de 1
        Node capitanNode = getCapitan(unEquipo); //getUniqueNodeByName(nodosHijosEquipo, "capitan");
        String nombreCapitan = capitanNode.getFirstChild().getNodeValue();

        return nombreCapitan;
    }

    public void exportarXML(String comentarios) throws TransformerException, ParserConfigurationException {
        Node partido =  doc.getDocumentElement();
        String nombreArchivo = "outPutXML";

        //nuevo elemeto
        //Element notas = doc.createElement("notas");
        //notas.setNodeValue(comentarios);
        //partido.appendChild(notas);

        Element notas = doc.createElement("notas");
        Text notasTexto = doc.createTextNode(comentarios);
        notas.appendChild(notasTexto);
        partido.appendChild(notas);

        //Generate XML
        Source source = new DOMSource(doc);
        //Indicamos donde lo queremos almacenar
        Result result = new StreamResult(new java.io.File(nombreArchivo+".xml")); //nombre del archivo
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);

    }

    private Node getGolesPorLocalidad(Node marcador, String localidad)
    {
        NodeList marcadorPorEquipos = marcador.getChildNodes();
        Node unMarcador = getUniqueNodeByName(marcadorPorEquipos,localidad);
        return unMarcador;
    }

    private Node getFormacion(Node equipo)
    {
        NodeList nodosHijosEquipo = equipo.getChildNodes();
        Node formacion = getUniqueNodeByName(nodosHijosEquipo,"formacion");
        return formacion;
    }

    private Node getCapitan(Node equipo)
    {
        NodeList nodosHijosEquipo = equipo.getChildNodes();
        Node capitan = getUniqueNodeByName(nodosHijosEquipo, "capitan");
        return capitan;
    }


    private Node getUniqueNodeByName(NodeList nodeList, String name) {
        Collection<Node> nodesFound = getNodesByName(nodeList, name);
        if (!nodesFound.isEmpty() && nodesFound.size() > 1) {
            throw new IllegalStateException("Se encontró más de 1 nodo con ese nombre");
        }

        if(nodesFound.isEmpty()){
            return null;
        }
        return nodesFound.iterator().next();
    }


    private Collection<Node> getNodesByName(NodeList nodeList, String name) {
        Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
        List<Node> nodesFound = nodeStream.filter(n -> n.getNodeName().equals(name)).collect(Collectors.toList());
        return nodesFound;
    }

}
