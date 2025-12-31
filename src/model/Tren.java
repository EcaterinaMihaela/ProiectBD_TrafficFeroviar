package model;

public class Tren {

    private int idTren;
    private String tip;
    private int numar;
    private String clase;
    private int nrVagoane;
    private Operator operator;
    private Ruta ruta;

    public Tren() {
    }

    public Tren(int idTren, String tip, int numar, String clase, int nrVagoane, Operator operator, Ruta ruta) {
        this.idTren = idTren;
        this.tip = tip;
        this.numar = numar;
        this.clase = clase;
        this.nrVagoane = nrVagoane;
        this.operator = operator;
        this.ruta = ruta;
    }

    public Tren(int idTren, String tip, int numar) {
        this.idTren = idTren;
        this.tip = tip;
        this.numar = numar;
    }

    public int getIdTren() {
        return idTren;
    }

    public void setIdTren(int idTren) {
        this.idTren = idTren;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public int getNrVagoane() {
        return nrVagoane;
    }

    public void setNrVagoane(int nrVagoane) {
        this.nrVagoane = nrVagoane;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
}
