package model;

public class Ruta {

    private String numeRuta;
    private int nrStatii;
    private String intervalOrar;
    private String intarziereAproximativa;

    public Ruta() {
    }

    public Ruta(String numeRuta) {
        this.numeRuta = numeRuta;
    }

    public String getNumeRuta() {
        return numeRuta;
    }

    public void setNumeRuta(String numeRuta) {
        this.numeRuta = numeRuta;
    }

    public int getNrStatii() {
        return nrStatii;
    }

    public void setNrStatii(int nrStatii) {
        this.nrStatii = nrStatii;
    }

    public String getIntervalOrar() {
        return intervalOrar;
    }

    public void setIntervalOrar(String intervalOrar) {
        this.intervalOrar = intervalOrar;
    }

    public String getIntarziereAproximativa() {
        return intarziereAproximativa;
    }

    public void setIntarziereAproximativa(String intarziereAproximativa) {
        this.intarziereAproximativa = intarziereAproximativa;
    }

    @Override
    public String toString() {
        return numeRuta;
    }
}
