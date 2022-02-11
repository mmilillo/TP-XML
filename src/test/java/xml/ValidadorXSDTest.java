package xml;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class ValidadorXSDTest extends TestCase {

    String pathXMLValido = "quilmes_2012.xml";
    String pathXMLInvalido = "quilmes_2012_malo.xml";
    String pathXSD = "xml-schema.xsd";

    @Test
    public void testArchivoValido() throws IOException {
        ValidadorXSD validador = new ValidadorXSD(pathXMLValido,pathXSD);
        boolean resultado = validador.validar();
        assertEquals(true, resultado);
    }

    @Test
    public void testArchivoInvalido() throws IOException {
        ValidadorXSD validador = new ValidadorXSD(pathXMLInvalido,pathXSD);
        boolean resultado = validador.validar();
        assertEquals(false, resultado);
    }

}