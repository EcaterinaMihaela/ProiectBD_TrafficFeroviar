package viewer;

import dao.Database;
import model.Bilet;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BiletViewer extends JFrame {

    private List<Bilet> biletList;
    private Bilet bilet;
    private Database db;
    private Connection con;
    private BiletTableModel tableModel;

    private JTextField idInput, detaliiInput, pretInput, tipReducereInput, idTrenInput,
            locVagonInput, nrLegitimatieInput, numeGaraInput, nrVagonInput;

    private JButton addButton, searchButton, deleteButton,updateButton;

    public BiletViewer() throws SQLException {
        super("Bilete");

        db = new Database();
        con = null;
        biletList = new ArrayList<>();

        try {
            con = db.connect();
            biletList = db.citesteBilete(con);
        } catch (Exception e) {
            System.out.println(e);
        }

        setLayout(new BorderLayout(4, 4));
        add(makeInputFieldsPanel(), BorderLayout.NORTH);

        tableModel = new BiletTableModel(biletList);
        add(makeResultTable(tableModel), BorderLayout.CENTER);
        addListeners();

        pack();
        setVisible(true);
    }

    private JPanel makeInputFieldsPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        idInput = new JTextField(5);
        detaliiInput = new JTextField(10);
        pretInput = new JTextField(5);
        tipReducereInput = new JTextField(8);
        idTrenInput = new JTextField(5);
        locVagonInput = new JTextField(5);
        nrLegitimatieInput = new JTextField(5);
        numeGaraInput = new JTextField(8);
        nrVagonInput = new JTextField(5);

        panel.add(new JLabel("ID Bilet:")); panel.add(idInput);
        panel.add(new JLabel("Detalii:")); panel.add(detaliiInput);
        panel.add(new JLabel("Pret:")); panel.add(pretInput);
        panel.add(new JLabel("Reducere:")); panel.add(tipReducereInput);
        panel.add(new JLabel("ID Tren:")); panel.add(idTrenInput);
        panel.add(new JLabel("Loc Vagon:")); panel.add(locVagonInput);
        panel.add(new JLabel("Nr Legitimatie:")); panel.add(nrLegitimatieInput);
        panel.add(new JLabel("Gara:")); panel.add(numeGaraInput);
        panel.add(new JLabel("Nr Vagon:")); panel.add(nrVagonInput);

        panel.add(makeControlPanel());
        return panel;
    }

    private JPanel makeControlPanel() {
        JPanel controlPanel = new JPanel();
        addButton = new JButton("Add");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");

        controlPanel.add(addButton);
        controlPanel.add(searchButton);
        controlPanel.add(deleteButton);
        controlPanel.add(updateButton);

        return controlPanel;
    }

    private JScrollPane makeResultTable(AbstractTableModel model) {
        JTable table = new JTable(model);
        return new JScrollPane(table);
    }

    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bilet = createBilet();
                if(bilet != null) {
                    try {
                        db.insereazaBilet(con, bilet);
                        biletList.add(bilet);
                        tableModel.fireTableDataChanged();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(BiletViewer.this,
                                "Eroare la adăugarea biletului:\n" + ex.getMessage(),
                                "Eroare",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    biletList.clear();
                    int id = Integer.parseInt(idInput.getText());
                    Bilet b = db.cautaBilet(con, id);
                    if(b != null) {
                        biletList.add(b);
                        tableModel.fireTableDataChanged();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // folosește JTable-ul din JScrollPane pentru selectare rând
                JTable table = ((JTable)((JScrollPane)getContentPane().getComponent(1)).getViewport().getView());
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0) {
                    Bilet b = biletList.get(selectedRow);
                    try {
                        db.stergeBilet(con, b.getIdBilet());
                        biletList.remove(selectedRow);
                        tableModel.fireTableDataChanged();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectează un bilet pentru ștergere!");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTable table = ((JTable)((JScrollPane)getContentPane()
                        .getComponent(1)).getViewport().getView());

                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    Bilet b = biletList.get(selectedRow);

                    try {
                        // actualizare obiect
                        b.setDetalii(detaliiInput.getText());
                        b.setPret(Double.parseDouble(pretInput.getText()));
                        b.setTipReducere(tipReducereInput.getText());
                        b.setLocVagon(Integer.parseInt(locVagonInput.getText()));
                        b.setNrLegitimatie(Integer.parseInt(nrLegitimatieInput.getText()));
                        b.setNumeGara(numeGaraInput.getText());
                        b.setNrVagon(Integer.parseInt(nrVagonInput.getText()));

                        // update în DB
                        db.updateBilet(con, b);

                        tableModel.fireTableDataChanged();
                        JOptionPane.showMessageDialog(null, "Bilet actualizat cu succes!");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Date invalide la update!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Selectează un bilet pentru update!");
                }
            }
        });

    }

    private Bilet createBilet() {
        try {
            int id = Integer.parseInt(idInput.getText());
            String detalii = detaliiInput.getText();
            double pret = Double.parseDouble(pretInput.getText());
            String reducere = tipReducereInput.getText();
            int idTren = Integer.parseInt(idTrenInput.getText());
            int locVagon = Integer.parseInt(locVagonInput.getText());
            int nrLegitimatie = Integer.parseInt(nrLegitimatieInput.getText());
            String gara = numeGaraInput.getText();
            int nrVagon = Integer.parseInt(nrVagonInput.getText());

            return new Bilet(id, detalii, pret, reducere, idTren, "valid", new Date(),
                    locVagon, nrLegitimatie, gara, nrVagon);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Date invalide!");
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new BiletViewer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
