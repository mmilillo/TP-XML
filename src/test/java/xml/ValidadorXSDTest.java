package xml;

import junit.framework.TestCase;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ValidadorXSDTest extends TestCase {

    String PATH_XML_VALIDO = "src/main/resources/quilmes_2012.xml";
    String PATH_XML_INVALIDO = "src/main/resources/quilmes_2012_malo.xml";
    String PATH_XSD = "src/main/resources/xml-schema.xsd";

    public void testArchivoValido() throws IOException, ParserConfigurationException {
        ValidadorXSD validador = new ValidadorXSD(PATH_XML_VALIDO,PATH_XSD);
        boolean resultado = validador.Validar();
        assertEquals(true, resultado);
    }

    public void testArchivoInvalido() throws IOException, ParserConfigurationException {
        ValidadorXSD validador = new ValidadorXSD(PATH_XML_INVALIDO,PATH_XSD);
        boolean resultado = validador.Validar();
        assertEquals(false, resultado);
    }

    //probar que tire la excepcion sperada
}