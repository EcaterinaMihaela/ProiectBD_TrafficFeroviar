package model;

public class Calator {

    private int nrLegitimatie;
    private String nume;
    private String prenume;
    private String tip;
    private String contact;

    public Calator() {
    }

    public Calator(int nrLegitimatie) {
        this.nrLegitimatie = nrLegitimatie;
    }

    public int getNrLegitimatie() {
        return nrLegitimatie;
    }

    public void setNrLegitimatie(int nrLegitimatie) {
        this.nrLegitimatie = nrLegitimatie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
