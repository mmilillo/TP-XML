import java.util.Scanner;

public class Main {

    public static void main (String args[]) throws Exception{
        String PATH_XML = "";
        String PATH_XSD = "";
        Scanner entradaEscaner = null;

        System.out.println ("Validando XML con XSD");

        System.out.println ("Ingrese path de archivo XML:");
        entradaEscaner = new Scanner (System.in);
        PATH_XML = entradaEscaner.nextLine ();

        System.out.println ("Ingrese path de archivo XSD:");
        entradaEscaner = new Scanner (System.in);
        PATH_XSD = entradaEscaner.nextLine ();

        System.out.println ("Resultado:");

        XSDValidador  validador = new XSDValidador(PATH_XML,PATH_XSD);

        if(validador.Validar())
            System.out.println("archivo correcto");
        else
            System.out.println("archivo incorrecto");
    }

}
