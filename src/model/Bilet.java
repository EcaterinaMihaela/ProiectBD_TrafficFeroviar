package model;

import java.util.Date;

public class Bilet {
    private int idBilet;
    private String detalii;
    private double pret;
    private String tipReducere;
    private int idTren;
    private String status;
    private Date dataCumpararii;
    private int locVagon;
    private int nrLegitimatie;
    private String numeGara;
    private int nrVagon;

    // constructori
    public Bilet() {}

    public Bilet(int idBilet, String detalii, double pret, String tipReducere,
                 int idTren, String status, Date dataCumpararii,
                 int locVagon, int nrLegitimatie, String numeGara, int nrVagon) {
        this.idBilet = idBilet;
        this.detalii = detalii;
        this.pret = pret;
        this.tipReducere = tipReducere;
        this.idTren = idTren;
        this.status = status;
        this.dataCumpararii = dataCumpararii;
        this.locVagon = locVagon;
        this.nrLegitimatie = nrLegitimatie;
        this.numeGara = numeGara;
        this.nrVagon = nrVagon;
    }

    // getter/setter pentru toate atributele
    public int getIdBilet() { return idBilet; }
    public void setIdBilet(int idBilet) { this.idBilet = idBilet; }

    public String getDetalii() { return detalii; }
    public void setDetalii(String detalii) { this.detalii = detalii; }

    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    public String getTipReducere() { return tipReducere; }
    public void setTipReducere(String tipReducere) { this.tipReducere = tipReducere; }

    public int getIdTren() { return idTren; }
    public void setIdTren(int idTren) { this.idTren = idTren; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDataCumpararii() { return dataCumpararii; }
    public void setDataCumpararii(Date dataCumpararii) { this.dataCumpararii = dataCumpararii; }

    public int getLocVagon() { return locVagon; }
    public void setLocVagon(int locVagon) { this.locVagon = locVagon; }

    public int getNrLegitimatie() { return nrLegitimatie; }
    public void setNrLegitimatie(int nrLegitimatie) { this.nrLegitimatie = nrLegitimatie; }

    public String getNumeGara() { return numeGara; }
    public void setNumeGara(String numeGara) { this.numeGara = numeGara; }

    public int getNrVagon() { return nrVagon; }
    public void setNrVagon(int nrVagon) { this.nrVagon = nrVagon; }
}
