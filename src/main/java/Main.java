import xml.ValidadorXSD;

import java.util.Scanner;

public class Main {

    public static void main (String args[]) throws Exception{
        String pathXml = "";
        String pathXsd = "";
        Scanner entradaEscaner = new Scanner (System.in);

        System.out.println ("Validando XML con XSD");

        pathXml = solicitarPath(entradaEscaner,"Ingrese path de archivo XML:");
        pathXsd = solicitarPath(entradaEscaner,"Ingrese path de archivo XSD:");

        ValidadorXSD validador = new ValidadorXSD(pathXml,pathXsd);

        if(validador.Validar())
            mostrarMenu();
        else
            mostrarError();

    }


    public static String solicitarPath(Scanner scanner, String mensaje)
     {
         System.out.println (mensaje);
         scanner = new Scanner (System.in);
         return scanner.nextLine();
     }

     public static void mostrarMenu()
     {
         System.out.println("archivo correcto");
         Menu menu = new Menu();
         menu.mostrarMenu();
     }

    public static void mostrarError()
    {
        System.out.println("archivo incorrecto");
    }



}
