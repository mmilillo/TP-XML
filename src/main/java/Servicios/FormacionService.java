package Servicios;

import Entidades.Formacion;
import Entidades.Gol;
import Entidades.Jugador;
import Entidades.Marcador;
import org.xml.sax.SAXException;
import xml.ControladorSAX;
import xml.ExploradorXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormacionService {

    private String path;
    private ExploradorXML exploradorXML;
    private ControladorSAX sh;
    private SAXParser saxParser;
    private InputStream inputStream;



    public FormacionService(String path) throws IOException, SAXException, ParserConfigurationException {
        this.path = path;
        //rompe con el path del xml. solo funciona si le das el nombre. no se como
        // funcionaria si ejecutas esto por consola fuera del ide. no va a servir quedarse solo con el nombre
        //del archivo ingresado
        System.out.println(this.path);
        exploradorXML = new ExploradorXML("quilmes_2012.xml"); //aca iria el path posta

        //para recorrer sin el dom
        sh = new ControladorSAX();

        SAXParserFactory factory = SAXParserFactory.newInstance();  //Creem una instancia d'una factoria de parser SAX
        saxParser = factory.newSAXParser();

        ClassLoader classLoader = FormacionService.class.getClassLoader();
        inputStream = classLoader.getResourceAsStream("quilmes_2012.xml");//aca iria el path posta

    }

    /***
     * Muesta formacion local y visitante.
     * Marca con un * junto a cada jugador por cada gol anotado
     * Marca con [C] el jugador capitan de cada equipo
     */
    public void mostrarFormaciones(){
        Formacion formacionLocal = exploradorXML.getFormacion("local");
        Formacion formacionVisitante = exploradorXML.getFormacion("visitante");
        Marcador marcador = exploradorXML.GetMarcador();
        String capitanLocal = exploradorXML.getCapitanByLocalidad("local");
        String capitanVisitante = exploradorXML.getCapitanByLocalidad("visitante");

        formatearFormacion(formacionLocal,marcador.getGolesLocales(),capitanLocal);
        formatearFormacion(formacionVisitante,marcador.getGolesVisitantes(),capitanVisitante);

        System.out.println(" -- Formacion local -- ");
        formacionLocal.imprimir();

        System.out.println(" -- Formacion visitante -- ");
        formacionVisitante.imprimir();

        //exploradorXML.mostrarFormaciones();
    }

    private void formatearFormacion(Formacion formacion, List<Gol> goles, String capitan){
        List<Jugador> jugadores = formacion.getFormacion();

        for(Jugador jugador : jugadores){
            if(jugador.getNombre().equals(capitan)){
                jugador.setNombre(jugador.getNombre() + " [C]");
            }
        }

        /////
        Map<String, String> autorGoles = new HashMap<String, String>();

        for(Gol gol : goles){
            String autor = gol.getAutor();
            if(!autorGoles.containsKey(autor)){
                autorGoles.put(gol.getAutor(), "*");
            }
            else{
                autorGoles.replace(autor,autorGoles.get(autor) + "*");
            }
        }
        /////

        for(Gol gol : goles){
            for(Jugador jugador : jugadores){
                if(jugador.getNombre().equals(gol.getAutor())){
                    jugador.setNombre(jugador.getNombre() + " " + autorGoles.get(gol.getAutor()));
                }
            }
        }
    }

    /***
     * Muestra las figuras del partido
     * No recorre el dom del XML
     */
    public void mostrarFiguraPartido() throws ParserConfigurationException, SAXException {
        try{
            saxParser.parse(inputStream,sh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //exploradorXML.mostrarFiguraPartido();
    }

    /**
     * Muesta el nombre de los jugadores que anotaron.
     * Ordenando por equipo y de fomorma cronologica.
     * Agrupa los goles por jugador mostrando nombre seguido de la marca temporal en la que anoto
     */
    public void mostrarResultado()
    {
        exploradorXML.mostrarResultado();
    }
}
