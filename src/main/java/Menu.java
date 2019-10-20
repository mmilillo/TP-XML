import org.xml.sax.SAXException;
import xml.ExploradorXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private ExploradorXML exploradorXML;
    private Scanner entradaEscaner = null;
    private int opcion = -1;

    public Menu(String pathXml) throws IOException, SAXException, ParserConfigurationException {
        entradaEscaner = new Scanner (System.in);
        //rompe con el path del xml. solo funciona si le das el nombre. no se como
        // funcionaria si ejecutas esto por consola fuera del ide. no va a servir quedarse solo con el nombre
        //del archivo ingresado
        System.out.println(pathXml);
        exploradorXML = new ExploradorXML("quilmes_2012.xml");


        //para sax

    }

    public void mostrarMenu() throws ParserConfigurationException, SAXException {
        System.out.println("");
        System.out.println("MENU");
        System.out.println("1 Mostrar Formaciones");
        System.out.println("2 Mostrar FiguraPartido");
        System.out.println("3 Mostrar Resultado");
        System.out.println("4 Exportar XML");
        System.out.println("5 Salir");
        System.out.println ("Ingrese una opcion:");
        ejecutarAccion(tryParseInt(entradaEscaner.nextLine()));
    }

    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void ejecutarAccion(int opcion) throws ParserConfigurationException, SAXException {
        switch(opcion) {
            case 1:
                this.mostrarFormaciones();
                mostrarMenu();
                break;
            case 2:
                this.mostrarFiguraPartido();
                mostrarMenu();
                break;
            case 3:
                this.mostrarResultado();
                mostrarMenu();
                break;
            case 4:
                System.out.println("exportarXML");
                mostrarMenu();
                break;
            case 5:
                System.out.println("Saliendo... ");
                break;
            default:
                System.out.println("Opcion incorrecta, ingrese valor valido.");
                mostrarMenu();
                break;
        }

    }

    private void mostrarFormaciones(){
        exploradorXML.mostrarFormaciones();
    }

    private void mostrarFiguraPartido() throws ParserConfigurationException, SAXException {
        exploradorXML.mostrarFiguraPartido();
    }

    private void mostrarResultado()
    {
        exploradorXML.mostrarResultado();
    }
}
