import org.xml.sax.SAXException;
import xml.ExploradorXML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private ExploradorXML exploradorXML;
    private Scanner entradaEscaner = null;
    private int opcion = -1;

    public Menu(String PathXml) throws IOException, SAXException, ParserConfigurationException {
        entradaEscaner = new Scanner (System.in);
        exploradorXML = new ExploradorXML("quilmes_2012.xml");
    }

    public void mostrarMenu(){
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

    private void ejecutarAccion(int opcion)
    {
        switch(opcion) {
            case 1:
                this.mostrarFormaciones();
                mostrarMenu();
                break;
            case 2:
                System.out.println("mostrarFiguraPartido");
                mostrarMenu();
                break;
            case 3:
                System.out.println("mostrarResultado");
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

}
