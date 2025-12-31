package viewer;

import dao.Database;
import model.Calator;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalatorViewer extends JFrame {

    private List<Calator> calatori;
    private Database db;
    private Connection con;
    private CalatorTableModel tableModel;
    private JTable calatorTable;

    private JTextField nrLegitimatieInput;
    private JTextField numeInput;
    private JTextField prenumeInput;
    private JTextField tipInput;
    private JTextField contactInput;
    private JButton addButton, deleteButton, updateButton, searchButton;

    public CalatorViewer() throws SQLException {
        super("Calatori");

        db = new Database();
        calatori = new ArrayList<>();
        try {
            con = db.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nu s-a putut conecta la baza de date!");
        }

        calatori = db.citesteCalatori(con);

        setLayout(new BorderLayout(4,4));
        add(makeInputPanel(), BorderLayout.NORTH);

        tableModel = new CalatorTableModel(calatori);
        calatorTable = new JTable(tableModel);
        add(new JScrollPane(calatorTable), BorderLayout.CENTER);

        addListeners();

        pack();
        setVisible(true);
    }

    private JPanel makeInputPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        nrLegitimatieInput = new JTextField(5);
        numeInput = new JTextField(10);
        prenumeInput = new JTextField(10);
        tipInput = new JTextField(5);
        contactInput = new JTextField(10);

        panel.add(new JLabel("Nr Legitimatie:")); panel.add(nrLegitimatieInput);
        panel.add(new JLabel("Nume:")); panel.add(numeInput);
        panel.add(new JLabel("Prenume:")); panel.add(prenumeInput);
        panel.add(new JLabel("Tip:")); panel.add(tipInput);
        panel.add(new JLabel("Contact:")); panel.add(contactInput);

        addButton = new JButton("Add"); deleteButton = new JButton("Delete");
        updateButton = new JButton("Update"); searchButton = new JButton("Search");

        panel.add(addButton); panel.add(deleteButton);
        panel.add(updateButton); panel.add(searchButton);

        return panel;
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            try {
                Calator c = new Calator();
                c.setNrLegitimatie(Integer.parseInt(nrLegitimatieInput.getText()));
                c.setNume(numeInput.getText());
                c.setPrenume(prenumeInput.getText());
                c.setTip(tipInput.getText());
                c.setContact(contactInput.getText());

                db.insereazaCalator(con, c);
                calatori.add(c);
                tableModel.fireTableDataChanged();
            } catch(Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Date invalide!");
            }
        });

        deleteButton.addActionListener(e -> {
            int row = calatorTable.getSelectedRow();
            if(row >= 0) {
                try {
                    Calator c = calatori.get(row);
                    db.stergeCalator(con, c.getNrLegitimatie());
                    calatori.remove(row);
                    tableModel.fireTableDataChanged();
                } catch(SQLException ex) { ex.printStackTrace(); }
            }
        });

        updateButton.addActionListener(e -> {
            int row = calatorTable.getSelectedRow();
            if(row >= 0) {
                try {
                    Calator c = calatori.get(row);
                    c.setNume(numeInput.getText());
                    c.setPrenume(prenumeInput.getText());
                    c.setTip(tipInput.getText());
                    c.setContact(contactInput.getText());

                    db.updateCalator(con, c);
                    tableModel.fireTableDataChanged();
                } catch(SQLException ex) { ex.printStackTrace(); }
            }
        });

        searchButton.addActionListener(e -> {
            try {
                String nrText = nrLegitimatieInput.getText().trim();
                String contactText = contactInput.getText().trim();
                calatori.clear();

                if(!nrText.isEmpty()) {
                    Calator c = db.cautaCalatorDupaNr(con, Integer.parseInt(nrText));
                    if(c != null) calatori.add(c);
                } else if(!contactText.isEmpty()) {
                    calatori.addAll(db.cautaCalatorDupaContact(con, contactText));
                } else {
                    JOptionPane.showMessageDialog(this, "Introdu nr legitimatie sau contact!");
                    return;
                }

                tableModel.fireTableDataChanged();
            } catch(Exception ex) { ex.printStackTrace(); }
        });
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch(Exception e) { e.printStackTrace(); }

        try { new CalatorViewer(); } catch(SQLException ex) { ex.printStackTrace(); }
    }
}
