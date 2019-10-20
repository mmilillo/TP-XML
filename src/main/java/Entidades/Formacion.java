package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Formacion {

    private List<Jugador> jugadores;

    public Formacion (){
        this.jugadores = new ArrayList<Jugador>();
    }

    public void agregar(Jugador jugador){
        jugadores.add(jugador);
    }

    public void imprimir(){
        this.jugadores.forEach((j) -> j.imprimir());
    }

    public List<Jugador> getFormacion(){
        return this.jugadores;
    }


}
