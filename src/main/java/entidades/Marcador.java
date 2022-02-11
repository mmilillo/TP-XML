package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Marcador {

    private String equipoLocal;
    private String equipoVisitante;
    private List<Gol> golesLocales;
    private List<Gol> golesVisitantes;

    private Marcador(){
        golesLocales = new ArrayList<Gol>();
        golesVisitantes = new ArrayList<Gol>();
    }

    public Marcador(String equipoLocal,String equipoVisitante, List<Gol> golesLocales, List<Gol> golesVisitantes){
        this();

        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocales = golesLocales;
        this.golesVisitantes = golesVisitantes;
    }

    public List<Gol> getGolesVisitantes(){
        Collections.sort(golesVisitantes);
        return this.golesVisitantes;
    }

    public List<Gol> getGolesLocales(){
        Collections.sort(golesLocales);
        return this.golesLocales;
    }

    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisitante() {
        return equipoVisitante;
    }
}
