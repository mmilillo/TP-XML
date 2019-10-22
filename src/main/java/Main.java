import org.xml.sax.SAXException;
import xml.ValidadorXSD;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    static String pathXml = "";
    static String pathXsd = "";

    public static void main (String args[]) throws Exception{
        System.out.println ("Validando XML con XSD");

        pathXml = "src/main/resources/procesar.xml";
        pathXsd = "src/main/resources/xml-schema.xsd";

        ValidadorXSD validador = new ValidadorXSD(pathXml,pathXsd);

        if(validador.Validar())
            mostrarMenu(pathXml);
        else
            mostrarError();

    }

     public static void mostrarMenu(String pathXml) throws ParserConfigurationException, SAXException, IOException, TransformerException {
         System.out.println("archivo correcto");
         Menu menu = new Menu(pathXml);
         menu.mostrarMenu();
     }

    public static void mostrarError()
    {
        System.out.println("archivo incorrecto");
    }



}
