package model;

public class Operator {

    private String numeOperator;
    private String contact;

    public Operator() {
    }

    public Operator(String numeOperator) {
        this.numeOperator = numeOperator;
    }

    public String getNumeOperator() {
        return numeOperator;
    }

    public void setNumeOperator(String numeOperator) {
        this.numeOperator = numeOperator;
    }

    @Override
    public String toString() {
        return numeOperator; // asta va fi afișat în JComboBox
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
