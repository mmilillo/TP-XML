package Entidades;

public class Jugador {
    private String nombre;

    public Jugador(String nombre){
        this.setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void imprimir(){
        System.out.println(this.nombre);
    }
}
