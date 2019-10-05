package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
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


    public ExploradorXML(String pathXml) throws ParserConfigurationException, IOException, SAXException {
        //cargamos el XML en un input stream
        this.classLoader = ExploradorXML.class.getClassLoader();
        this.inputStream = classLoader.getResourceAsStream(pathXml);
        // Creamos el parser y parseamos el input stream
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        this.doc = builder.parse(new InputSource(inputStream));
    }

    public boolean algo(){
        return true;
    }


    /***
     * Muesta formacion local y visitante.
     * Marca con un * junto a cada jugador por cada gol anotado
     * Marca con [C] el jugador capitan de cada equipo
     */
    public void mostrarFormaciones()
    {

        printFomrmacion("local");
        printFomrmacion("visitante");
    }

    private void printFomrmacion(String equipo)
    {
        System.out.println("Equipo " + equipo.toString());
        System.out.println("");


        Node unEquipo = doc.getElementsByTagName(equipo.toString()).item(0); //devuelve una NodeList de 1
        NodeList nodosHijosEquipo = unEquipo.getChildNodes();

        Node formacion = getUniqueNodeByName(nodosHijosEquipo,"formacion");
        Node capitan = getUniqueNodeByName(nodosHijosEquipo, "capitan");

        imprimirFormacionConCapitan(formacion, capitan);
    }

    private void imprimirFormacionConCapitan( Node formacion, Node capitan)
    {
        String nombreCapitan = capitan.getFirstChild().getNodeValue();
        Collection<Node> jugadores = getNodesByName(formacion.getChildNodes(), "jugador");

        for(Node jugador : jugadores){
            if(jugador.getFirstChild().getNodeValue().equals(nombreCapitan))
                jugador.getFirstChild().setNodeValue(jugador.getFirstChild().getNodeValue() + " [C]");
        }

        jugadores.stream().forEach( n -> System.out.println(n.getFirstChild().getNodeValue()));
    }


    private Node getUniqueNodeByName(NodeList nodeList, String name) {
        Collection<Node> nodesFound = getNodesByName(nodeList, name);
        if (!nodesFound.isEmpty() && nodesFound.size() > 1) {
            throw new IllegalStateException("Se encontró más de 1 nodo con ese nombre");
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
    //public void mostrarFiguraPartido(){};

    /**
     * Muesta el nombre de los jugadores que anotaron.
     * Ordenando por equipo y de fomorma cronologica.
     * Agrupa los goles por jugador mostrando nombre seguido de la marca temporal en la que anoto
     */
    //public void mostrarResultado(){}

    /**
     * Exporta el xml del partido agregando una seccion notas como hija de <partido>
     */
    //public void exportarXML(){};
}
