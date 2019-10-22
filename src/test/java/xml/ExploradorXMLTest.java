package xml;

import Entidades.Formacion;
import Entidades.Marcador;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class ExploradorXMLTest extends TestCase {

    String pathXMLValido = "quilmes_2012.xml";
    ExploradorXML exploradorXML;


    public ExploradorXMLTest() throws IOException, SAXException, ParserConfigurationException {
        exploradorXML = new ExploradorXML(pathXMLValido);
    }



    @Test
    public void testgetFormacion() {
        Formacion formacion = exploradorXML.getFormacion("visitante");
        assertNotNull(formacion);
    }

    @Test
    public void testCantidadJugadoresFormacion(){
        Formacion formacion = exploradorXML.getFormacion("visitante");
        assertEquals(11,formacion.getFormacion().size());
    }


    @Test
    public void testGetMarcador() {
        Marcador marcador = exploradorXML.getMarcador();
        assertNotNull(marcador);
    }

    @Test
    public void testGetMarcadorResultado(){
        Marcador marcador = exploradorXML.getMarcador();

        assertEquals(2,marcador.getGolesVisitantes().size());
        assertEquals(0,marcador.getGolesLocales().size());
    }

    @Test
    public void testGetCapitanByLocalidad() {
        String capitanLocal = exploradorXML.getCapitanByLocalidad("local");
        String capitanVisitante = exploradorXML.getCapitanByLocalidad("visitante");

        assertTrue(!capitanLocal.isEmpty());
        assertTrue(!capitanVisitante.isEmpty());
    }


    @Test
    public void testExportarXML() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        exploradorXML.exportarXML("prueba");
    }


}