package model;

public class Vagon {

    private int numarVagon;
    private String clasa;
    private int nrLocuriLibere;
    private int nrLocuriTotal;
    private String tip;
    private Tren tren;

    public Vagon() {
    }

    public Vagon(int numarVagon) {
        this.numarVagon = numarVagon;
    }

    public int getNumarVagon() {
        return numarVagon;
    }

    public void setNumarVagon(int numarVagon) {
        this.numarVagon = numarVagon;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }

    public int getNrLocuriLibere() {
        return nrLocuriLibere;
    }

    public void setNrLocuriLibere(int nrLocuriLibere) {
        this.nrLocuriLibere = nrLocuriLibere;
    }

    public int getNrLocuriTotal() {
        return nrLocuriTotal;
    }

    public void setNrLocuriTotal(int nrLocuriTotal) {
        this.nrLocuriTotal = nrLocuriTotal;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Tren getTren() {
        return tren;
    }

    public void setTren(Tren tren) {
        this.tren = tren;
    }
}
