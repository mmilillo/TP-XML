package xml;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ValidadorXSDTest extends TestCase {

    String pathXMLValido = "src/main/resources/quilmes_2012.xml";
    String pathXMLInvalido = "src/main/resources/quilmes_2012_malo.xml";
    String pathXSD = "src/main/resources/xml-schema.xsd";

    @Test
    public void testArchivoValido() throws IOException, ParserConfigurationException {
        ValidadorXSD validador = new ValidadorXSD(pathXMLValido,pathXSD);
        boolean resultado = validador.validar();
        assertEquals(true, resultado);
    }

    @Test
    public void testArchivoInvalido() throws IOException, ParserConfigurationException {
        ValidadorXSD validador = new ValidadorXSD(pathXMLInvalido,pathXSD);
        boolean resultado = validador.validar();
        assertEquals(false, resultado);
    }

}