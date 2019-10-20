package Entidades;

public class Gol {

    private String minuto;
    private String autor;

    public Gol(String minuto, String autor){
        this.setMinuto(minuto);
        this.setAutor(autor);
    }


    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
