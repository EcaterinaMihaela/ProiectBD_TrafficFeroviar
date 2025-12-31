package model;

public class Angajat {

    private int idAngajat;
    private String numeAngajat;
    private String prenumeAngajat;
    private String rol;
    private Operator operator;  // obiect Operator, corespunzător NumeOperator
    private String cnp;

    public Angajat() {
    }

    public Angajat(int idAngajat, String nume, String prenume, String rol, String cnp) {
        this.idAngajat = idAngajat;
        this.numeAngajat = nume;
        this.prenumeAngajat = prenume;
        this.rol = rol;
        this.cnp = cnp;
    }

    // Getter & Setter

    public int getIdAngajat() {
        return idAngajat;
    }

    public void setIdAngajat(int idAngajat) {
        this.idAngajat = idAngajat;
    }

    public String getNumeAngajat() {
        return numeAngajat;
    }

    public void setNumeAngajat(String numeAngajat) {
        this.numeAngajat = numeAngajat;
    }

    public String getPrenumeAngajat() {
        return prenumeAngajat;
    }

    public void setPrenumeAngajat(String prenumeAngajat) {
        this.prenumeAngajat = prenumeAngajat;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
