package servicios;

import entidades.Formacion;
import entidades.Gol;
import entidades.Jugador;
import entidades.Marcador;
import org.xml.sax.SAXException;
import xml.ControladorSAX;
import xml.ExploradorXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class FormacionService {

    private final ExploradorXML exploradorXML;
    private final ControladorSAX sh;
    private final SAXParser saxParser;
    private final InputStream inputStream;


    public FormacionService(String path) throws IOException, SAXException, ParserConfigurationException {
        exploradorXML = new ExploradorXML(path);

        //para recorrer sin el dom
        sh = new ControladorSAX();

        SAXParserFactory factory = SAXParserFactory.newInstance();  //Creem una instancia de una factoria de parser SAX
        saxParser = factory.newSAXParser();

        ClassLoader classLoader = FormacionService.class.getClassLoader();
        inputStream = classLoader.getResourceAsStream(path);

    }

    /***
     * Muesta formacion local y visitante.
     * Marca con un * junto a cada jugador por cada gol anotado
     * Marca con [C] el jugador capitan de cada equipo
     */
    public void mostrarFormaciones(){
        Formacion formacionLocal = exploradorXML.getFormacion("local");
        Formacion formacionVisitante = exploradorXML.getFormacion("visitante");
        Marcador marcador = exploradorXML.getMarcador();
        String capitanLocal = exploradorXML.getCapitanByLocalidad("local");
        String capitanVisitante = exploradorXML.getCapitanByLocalidad("visitante");

        formatearFormacion(formacionLocal,marcador.getGolesLocales(),capitanLocal);
        formatearFormacion(formacionVisitante,marcador.getGolesVisitantes(),capitanVisitante);

        System.out.println(" -- Formacion local -- ");
        formacionLocal.imprimir();

        System.out.println(" -- Formacion visitante -- ");
        formacionVisitante.imprimir();
    }

    private void formatearFormacion(Formacion formacion, List<Gol> goles, String capitan){
        List<Jugador> jugadores = formacion.getFormacion();
        //agrega marca capitan
        for(Jugador jugador : jugadores){
            if(jugador.getNombre().equals(capitan)){
                jugador.setNombre(jugador.getNombre() + " [C]");
            }
        }

        //agrega marca de goles al jugador
        for(Gol gol : goles){
            Jugador jugador = jugadores.stream().filter(j -> j.getNombre().contains(gol.getAutor())).findFirst().get();
            jugador.setNombre(jugador.getNombre() + " *");
        }
    }


    /***
     * Muestra las figuras del partido
     * No recorre el dom del XML
     */
    public void mostrarFiguraPartido() throws SAXException {
        try{
            saxParser.parse(inputStream,sh);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muesta el nombre de los jugadores que anotaron.
     * Ordenando por equipo y de fomorma cronologica.
     * Agrupa los goles por jugador mostrando nombre seguido de la marca temporal en la que anoto
     */
    public void mostrarResultado()
    {
        Marcador marcador = exploradorXML.getMarcador();

        List<Gol> golesLocales = marcador.getGolesLocales();
        List<Gol> golesVisitantes = marcador.getGolesVisitantes();

        LinkedHashMap<String,String> autorGolesLocales = new  LinkedHashMap<String, String>();
        LinkedHashMap<String,String> autorGolesVisitantes = new  LinkedHashMap<String, String>();

        for(Gol gol : golesLocales){
            if(!autorGolesLocales.containsKey(gol.getAutor())){
                autorGolesLocales.put(gol.getAutor(),gol.getAutor() + " " + gol.getMinuto());
            }
            else{
                String autor = gol.getAutor();
                autorGolesLocales.replace(autor,autorGolesLocales.get(autor) + "," + gol.getMinuto());
            }
        }

        for(Gol gol : golesVisitantes){
            if(!autorGolesVisitantes.containsKey(gol.getAutor())){
                autorGolesVisitantes.put(gol.getAutor(),gol.getAutor() + " " + gol.getMinuto());
            }
            else{
                String autor = gol.getAutor();
                autorGolesVisitantes.replace(autor,autorGolesVisitantes.get(autor) + "," + gol.getMinuto());
            }
        }


        System.out.println(marcador.getEquipoLocal() + " " + marcador.getGolesLocales().size());
        autorGolesLocales.forEach((k,v) -> System.out.println(v));
        System.out.println(" ");
        System.out.println(marcador.getEquipoVisitante() + " " + marcador.getGolesVisitantes().size());
        autorGolesVisitantes.forEach((k,v) -> System.out.println(v));
    }

    /**
     * Exporta el xml del partido agregando una seccion notas como hija de <partido> el cual puede encontrarse en la raiz del proyecto.
     */
    public void exportarXML(String notas) throws TransformerException {
        System.out.println(notas);
        exploradorXML.exportarXML(notas, "outPut_" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    };
}
