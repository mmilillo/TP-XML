package entidades;

public class Gol implements Comparable<Gol> {

    private String minuto;
    private String autor;

    public Gol(String minuto, String autor){
        this.setMinuto(minuto);
        this.setAutor(autor);
    }

    @Override
    public int compareTo(Gol g){
        if( Integer.parseInt( g.getMinuto()) > Integer.parseInt(this.getMinuto())){
            return -1;
        }else if(Integer.parseInt( g.getMinuto()) < Integer.parseInt(this.getMinuto())){
            return 0;
        }else{
            return 1;
        }
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
