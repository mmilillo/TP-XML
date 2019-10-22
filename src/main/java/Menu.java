import Servicios.FormacionService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private Scanner entradaEscaner = null;
    private int opcion = -1;
    private FormacionService service;

    public Menu(String pathXml) throws IOException, SAXException, ParserConfigurationException {
        entradaEscaner = new Scanner (System.in);
        service = new FormacionService(pathXml);
    }

    public void mostrarMenu() throws ParserConfigurationException, SAXException, TransformerException {
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

    private void ejecutarAccion(int opcion) throws ParserConfigurationException, SAXException, TransformerException {
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
                this.exportarXML();
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
        service.mostrarFormaciones();
    }

    private void mostrarFiguraPartido() throws ParserConfigurationException, SAXException {
        service.mostrarFiguraPartido();
    }

    private void mostrarResultado()
    {
        service.mostrarResultado();
    }

    private void exportarXML() throws TransformerException, ParserConfigurationException {
        System.out.println("Ingrese notas para adjuntar al XML: ");
        service.exportarXML(entradaEscaner.nextLine());
    }
}
