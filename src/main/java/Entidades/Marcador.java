package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Marcador {

    private List<Gol> golesLocales;
    private List<Gol> golesVisitantes;

    private Marcador(){
        golesLocales = new ArrayList<Gol>();
        golesVisitantes = new ArrayList<Gol>();
    }

    public Marcador(List<Gol> golesLocales, List<Gol> golesVisitantes){
        this();

        this.golesLocales = golesLocales;
        this.golesVisitantes = golesVisitantes;

        /*
        for(Gol gol : golesLocales){
            if(!this.golesLocales.stream().anyMatch((g) -> g.getAutor().equals(gol.getAutor()))){
                this.golesLocales.add(gol);
            }
            else{
                Gol golGrabado = this.golesLocales.stream().filter((g) -> g.getAutor().equals(gol.getAutor()))
                        .findFirst().get();
                golGrabado.setMinuto(golGrabado.getMinuto() + " " + gol.getMinuto());
            }
        }

        for(Gol gol : golesVisitantes){
            if(!this.golesVisitantes.stream().anyMatch((g) -> g.getAutor().equals(gol.getAutor()))){
                this.golesVisitantes.add(gol);
            }
            else{
                Gol golGrabado = this.golesVisitantes.stream().filter((g) -> g.getAutor().equals(gol.getAutor()))
                        .findFirst().get();
                golGrabado.setMinuto(golGrabado.getMinuto() + "," + gol.getMinuto());
            }
        }

         */

    }

    void agregarGolLocal(Gol gol){
        golesLocales.add(gol);
    }

    void agregarGolVisitante(Gol gol){
        golesVisitantes.add(gol);
    }

    public List<Gol> getGolesVisitantes(){
        return this.golesVisitantes;
    }

    public List<Gol> getGolesLocales(){
        return this.golesLocales;
    }

}
