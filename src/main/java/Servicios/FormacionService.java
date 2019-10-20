package Servicios;

import Entidades.Formacion;
import Entidades.Gol;
import Entidades.Jugador;
import Entidades.Marcador;
import org.xml.sax.SAXException;
import xml.ExploradorXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class FormacionService {

    private String path;
    private ExploradorXML exploradorXML;

    public FormacionService(String path) throws IOException, SAXException, ParserConfigurationException {
        this.path = path;
        //rompe con el path del xml. solo funciona si le das el nombre. no se como
        // funcionaria si ejecutas esto por consola fuera del ide. no va a servir quedarse solo con el nombre
        //del archivo ingresado
        System.out.println(this.path);
        exploradorXML = new ExploradorXML("quilmes_2012.xml"); //aca iria el path posta
    }

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

        for(Gol gol : goles){
            for(Jugador jugador : jugadores){
                if(jugador.getNombre().equals(gol.getAutor())){
                    jugador.setNombre(jugador.getNombre() + " " + gol.getMinuto());
                }
            }
        }
    }


    public void mostrarFiguraPartido() throws ParserConfigurationException, SAXException {
        exploradorXML.mostrarFiguraPartido();
    }

    public void mostrarResultado()
    {
        exploradorXML.mostrarResultado();
    }
}
