import org.xml.sax.SAXException;
import xml.ValidadorXSD;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String pathXml = "";
    static String pathXsd = "";
    public static void main (String args[]) throws Exception{

        Scanner entradaEscaner = new Scanner (System.in);

        System.out.println ("Validando XML con XSD");

        //pathXml = solicitarPath(entradaEscaner,"Ingrese path de archivo XML:");
        //pathXsd = solicitarPath(entradaEscaner,"Ingrese path de archivo XSD:");

        pathXml = "/home/manuel/Desktop/xml/XML/TP-XML/src/main/resources/quilmes_2012.xml";
        pathXsd = "/home/manuel/Desktop/xml/XML/TP-XML/src/main/resources/xml-schema.xsd";

        ValidadorXSD validador = new ValidadorXSD(pathXml,pathXsd);

        if(validador.Validar())
            mostrarMenu(pathXml);
        else
            mostrarError();

    }


    public static String solicitarPath(Scanner scanner, String mensaje)
     {
         System.out.println (mensaje);
         scanner = new Scanner (System.in);
         return scanner.nextLine();
     }

     public static void mostrarMenu(String pathXml) throws ParserConfigurationException, SAXException, IOException {
         System.out.println("archivo correcto");
         Menu menu = new Menu(pathXml);
         menu.mostrarMenu();
     }

    public static void mostrarError()
    {
        System.out.println("archivo incorrecto");
    }



}
