package model;

public class Gara {

    private String numeGara;
    private int nrLinii;

    public Gara() {
    }

    public Gara(String numeGara) {
        this.numeGara = numeGara;
    }

    public String getNumeGara() {
        return numeGara;
    }

    public void setNumeGara(String numeGara) {
        this.numeGara = numeGara;
    }

    public int getNrLinii() {
        return nrLinii;
    }

    public void setNrLinii(int nrLinii) {
        this.nrLinii = nrLinii;
    }
}
