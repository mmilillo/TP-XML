package xml;

import Entidades.Formacion;
import Entidades.Gol;
import Entidades.Jugador;
import Entidades.Marcador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

    public Marcador GetMarcador(){

        //recupero goles
        Node goles = doc.getElementsByTagName("goles").item(0); //devuelve una NodeList de 1

        Node golesLocalNode = getGolesPorLocalidad(goles,"local");
        Node golesVisitanteNode = getGolesPorLocalidad(goles,"visitante");

        ArrayList<Gol> golesLocales = getGolesByGolesNode(golesLocalNode);
        ArrayList<Gol> golesVisitantes = getGolesByGolesNode(golesVisitanteNode);

        return new Marcador(golesLocales, golesVisitantes);

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

    /***
     * Muesta formacion local y visitante.
     * Marca con un * junto a cada jugador por cada gol anotado
     * Marca con [C] el jugador capitan de cada equipo
     */
    public void mostrarFormaciones()
    {

        //printFomrmacion("local");
        printFomrmacion("visitante");
    }

    private void printFomrmacion(String localidad)
    {
        System.out.println("Equipo " + localidad.toString());
        System.out.println("puta");

        //recupero formacion
        Node unEquipo =  doc.getElementsByTagName(localidad).item(0); //devuelve una NodeList de 1
        Node formacion = getFormacion(unEquipo);//getUniqueNodeByName(nodosHijosEquipo,"formacion");

        //recupero capitan
        Node capitan = getCapitan(unEquipo); //getUniqueNodeByName(nodosHijosEquipo, "capitan");

        //recupero goles
        Node goles = doc.getElementsByTagName("goles").item(0); //devuelve una NodeList de 1
        Node golesVisitante = getGolesPorLocalidad(goles,"visitante");
        printGoles(golesVisitante);

        imprimirFormacion(formacion, capitan, golesVisitante);
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

    private void printGoles(Node goles )
    {
        System.out.println("se van a imprimir los goles");
        Collection<Node> golesList = getNodesByName(goles.getChildNodes(), "gol");
        for(Node gol : golesList){
            NodeList datosDelGol = gol.getChildNodes();
            Node minuto = getUniqueNodeByName(datosDelGol, "minuto");
            Node autor = getUniqueNodeByName(datosDelGol, "autor");

            System.out.println(minuto.getFirstChild().getNodeValue());
            System.out.println(autor.getFirstChild().getNodeValue());
        }
    }


    private void imprimirFormacion( Node formacion, Node capitan, Node marcador)
    {
        String nombreCapitan = capitan.getFirstChild().getNodeValue();
        Collection<Node> jugadores = getNodesByName(formacion.getChildNodes(), "jugador");
        Collection<Node> goles = getNodesByName(marcador.getChildNodes(), "gol");

        //completo el dato de capitan
        for(Node jugador : jugadores){
            if(jugador.getFirstChild().getNodeValue().equals(nombreCapitan))
                jugador.getFirstChild().setNodeValue(jugador.getFirstChild().getNodeValue() + " [C]");
        }

        //completo el dato de goles
        for(Node gol : goles){
            NodeList datosDelGol = gol.getChildNodes();
            Node autor = getUniqueNodeByName(datosDelGol, "autor");
            Node minuto = getUniqueNodeByName(datosDelGol, "minuto");

            String stringAutor = autor.getFirstChild().getNodeValue();
            String stringMinuto = minuto.getFirstChild().getNodeValue();


            for(Node jugador : jugadores){
                if(jugador.getFirstChild().getNodeValue().equals(stringAutor))
                    jugador.getFirstChild().setNodeValue(jugador.getFirstChild().getNodeValue() + " " + stringMinuto);
            }
        }

        jugadores.stream().forEach( n -> System.out.println(n.getFirstChild().getNodeValue()));
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

    /***
     * Muestra las figuras del partido
     * No recorre el dom del XML
     */
    public void mostrarFiguraPartido() throws ParserConfigurationException, SAXException {

        SAXParserFactory factory = SAXParserFactory.newInstance();  //Creem una instancia d'una factoria de parser SAX
        ClassLoader classLoader = ExploradorXML.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("quilmes_2012.xml");

        SAXParser saxParser = factory.newSAXParser();
        ControladorSAX sh = new ControladorSAX();

        try{
            saxParser.parse(inputStream,sh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    /**
     * Muesta el nombre de los jugadores que anotaron.
     * Ordenando por equipo y de fomorma cronologica.
     * Agrupa los goles por jugador mostrando nombre seguido de la marca temporal en la que anoto
     */
    public void mostrarResultado()
    {

        String equipo = "local";

        ///formacion
        System.out.println("Equipo " + equipo.toString());
        System.out.println("");

        Node unEquipo = doc.getElementsByTagName("local").item(0); //devuelve una NodeList de 1
        NodeList nodosHijosEquipo = unEquipo.getChildNodes();

        Node formacion = getUniqueNodeByName(nodosHijosEquipo,"formacion");
        Node capitan = getUniqueNodeByName(nodosHijosEquipo, "capitan");

        String nombreCapitan = capitan.getFirstChild().getNodeValue();
        Collection<Node> jugadores = getNodesByName(formacion.getChildNodes(), "jugador");

        for(Node jugador : jugadores){
            if(jugador.getFirstChild().getNodeValue().equals(nombreCapitan))
                jugador.getFirstChild().setNodeValue(jugador.getFirstChild().getNodeValue() + " [C]");
        }

        jugadores.stream().forEach( n -> System.out.println(n.getFirstChild().getNodeValue()));
        ///formacion  end

        // Definir un HashMap
        HashMap golesLocales = new HashMap();
        HashMap golesvisitantes = new HashMap();


        Node resultado = doc.getElementsByTagName("goles").item(0); //devuelve una NodeList de 1
        NodeList golesPorEquiopo = unEquipo.getChildNodes();

        //Node golesVisitante = getUniqueNodeByName(golesPorEquiopo,"gol");
    }

    /**
     * Exporta el xml del partido agregando una seccion notas como hija de <partido>
     */
    //public void exportarXML(){};

/* por las dudas
    private Node getGolesLocales(Node documento, String localidad) {
        NodeList equipos = documento.getChildNodes();
        Node equipo = getUniqueNodeByName(equipos, localidad);
        return equipo;
    }
*/
}
