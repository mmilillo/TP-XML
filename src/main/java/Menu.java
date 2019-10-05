import java.util.Scanner;

public class Menu {

    private Scanner entradaEscaner = null;
    private int opcion = -1;

    public Menu()
    {
        entradaEscaner = new Scanner (System.in);
    }

    public void mostrarMenu(){
        System.out.println("");
        System.out.println("MENU");
        System.out.println("mostrarFormaciones");
        System.out.println("mostrarFiguraPartido");
        System.out.println("mostrarResultado");
        System.out.println("exportarXML");
        System.out.println("");
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
                System.out.println("mostrarFormaciones");
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
}
