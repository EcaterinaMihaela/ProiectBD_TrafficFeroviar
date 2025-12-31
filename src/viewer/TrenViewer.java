package viewer;

import dao.Database;
import model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrenViewer extends JFrame {

    private List<Tren> trenList;
    private List<Bilet> bileteTren;
    private Tren tren;
    private Database db;
    private Connection con;
    private TrenTableModel tableModel;
    private BiletTableModel biletTableModel;

    private JTextField idInput;
    private JTextField tipInput;
    private JTextField numarInput;
    private JTextField claseInput;
    private JTextField nrVagoaneInput;
    private JLabel bileteLabel; // label pentru partea de jos (bilete)

    private JComboBox<Operator> operatorCombo;
    private JComboBox<Ruta> rutaCombo;

    private JButton addButton;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton updateButton;

    private JTable trenTable;
    private JTable biletTable;

    public TrenViewer() throws SQLException {
        super("Trenuri disponibile");

        db = new Database();
        con = null;
        trenList = new ArrayList<>();

        try {
            con = db.connect();
            trenList = db.citesteTrenuri(con);
        } catch (Exception e) {
            System.out.println(e);
        }

        setLayout(new BorderLayout(4, 4));
        add(makeInputFieldsPanel(), BorderLayout.NORTH);

        tableModel = new TrenTableModel(trenList);
        //add(makeResultTable(tableModel), BorderLayout.CENTER);

        bileteTren = new ArrayList<>();
        biletTableModel = new BiletTableModel(bileteTren);
        biletTable = new JTable(biletTableModel);
        trenTable = new JTable(tableModel);

        // Creează panelul pentru bilete
        JPanel biletePanel = new JPanel();
        biletePanel.setLayout(new BoxLayout(biletePanel, BoxLayout.Y_AXIS));

        // Titlu label
        bileteLabel = new JLabel("Bilete disponibile pentru trenul selectat");
        bileteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        biletePanel.add(bileteLabel);

        // Adaugă tabelul de bilete
        biletePanel.add(new JScrollPane(biletTable));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(trenTable),
                biletePanel
        );
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        addListeners();

        pack();
        setVisible(true);
    }

    private JPanel makeInputFieldsPanel() {
        JPanel inputFieldsPanel = new JPanel();
        inputFieldsPanel.setLayout(new FlowLayout());

        idInput = new JTextField(5);
        tipInput = new JTextField(10);
        numarInput = new JTextField(5);
        claseInput = new JTextField(10);
        nrVagoaneInput = new JTextField(5);

        inputFieldsPanel.add(new JLabel("ID:"));
        inputFieldsPanel.add(idInput);
        inputFieldsPanel.add(new JLabel("Tip:"));
        inputFieldsPanel.add(tipInput);
        inputFieldsPanel.add(new JLabel("Numar:"));
        inputFieldsPanel.add(numarInput);
        inputFieldsPanel.add(new JLabel("Clase:"));
        inputFieldsPanel.add(claseInput);
        inputFieldsPanel.add(new JLabel("Nr Vagoane:"));
        inputFieldsPanel.add(nrVagoaneInput);

        // Operator și Ruta - citite din DB
        try {
            List<Operator> operatorList = db.citesteOperatori(con);
            operatorCombo = new JComboBox<>(operatorList.toArray(new Operator[0]));

            List<Ruta> rutaList = db.citesteRute(con);
            rutaCombo = new JComboBox<>(rutaList.toArray(new Ruta[0]));
        } catch (Exception e) {
            System.out.println(e);
        }

        inputFieldsPanel.add(new JLabel("Operator:"));
        inputFieldsPanel.add(operatorCombo);

        inputFieldsPanel.add(new JLabel("Ruta:"));
        inputFieldsPanel.add(rutaCombo);

        inputFieldsPanel.add(makeControlPanel());

        return inputFieldsPanel;
    }

    private JPanel makeControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        addButton = new JButton("Add");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update"); // buton update


        controlPanel.add(addButton);
        controlPanel.add(searchButton);
        controlPanel.add(deleteButton);
        controlPanel.add(updateButton);

        return controlPanel;
    }

    /*
    private JScrollPane makeResultTable(AbstractTableModel model) {
        trenTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(trenTable);
        return scrollPane;
    }*/

    private void addListeners() {
        trenTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = trenTable.getSelectedRow();
                if (row >= 0) {
                    Tren t = trenList.get(row);
                    bileteLabel.setText("Bilete disponibile pentru trenul #" + t.getIdTren());

                    try {
                        List<Bilet> bilete = db.citesteBileteDupaTren(con, t.getIdTren());
                        System.out.println("Bilete găsite: " + bilete.size());
                        bileteTren.clear();
                        bileteTren.addAll(
                                db.citesteBileteDupaTren(con, t.getIdTren())
                        );
                        biletTableModel.fireTableDataChanged();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tren = createTren();
                    if (tren != null && !db.existaTren(con, tren.getIdTren())) {
                        db.insereazaTren(con, tren);
                        trenList.add(tren);
                        tableModel.fireTableDataChanged();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(e -> {
            try {
                trenList.clear();

                String idText = idInput.getText().trim();
                Ruta rutaSelectata = (Ruta) rutaCombo.getSelectedItem();

                if (!idText.isEmpty()) {
                    // căutare după ID
                    int id = Integer.parseInt(idText);
                    Tren t = db.cautaTren(con, id);
                    if (t != null) {
                        trenList.add(t);
                    }

                } else if (rutaSelectata != null) {
                    // căutare după rută
                    trenList.addAll(
                            db.cautaTrenDupaRuta(con, rutaSelectata.getNumeRuta())
                    );

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Introdu ID-ul trenului sau selectează o rută!");
                    return;
                }

                tableModel.fireTableDataChanged();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID invalid!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Eroare la căutare!");
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = trenTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Tren tren = trenList.get(selectedRow);
                    try {
                        db.stergeTren(con, tren.getIdTren()); // șterge din DB
                        trenList.remove(selectedRow);          // șterge din lista locală
                        tableModel.fireTableDataChanged();    // actualizează tabelul
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectează un tren pentru a șterge!");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = trenTable.getSelectedRow();
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Selectează un tren pentru update!");
                    return;
                }

                Tren tren = trenList.get(selectedRow);

                // verifică câmpurile text
                String tipText = tipInput.getText().trim();
                String numarText = numarInput.getText().trim();
                String claseText = claseInput.getText().trim();
                String nrVagoaneText = nrVagoaneInput.getText().trim();
                Operator operator = (Operator) operatorCombo.getSelectedItem();
                Ruta ruta = (Ruta) rutaCombo.getSelectedItem();

                if (tipText.isEmpty() || numarText.isEmpty() || claseText.isEmpty()
                        || nrVagoaneText.isEmpty() || operator == null || ruta == null) {
                    JOptionPane.showMessageDialog(null, "Completează toate câmpurile!");
                    return;
                }

                try {
                    // transformă numerele
                    int numar = Integer.parseInt(numarText);
                    int nrVagoane = Integer.parseInt(nrVagoaneText);

                    // actualizează obiectul tren
                    tren.setTip(tipText);
                    tren.setNumar(numar);
                    tren.setClase(claseText);
                    tren.setNrVagoane(nrVagoane);
                    tren.setOperator(operator);
                    tren.setRuta(ruta);

                    // update în DB
                    db.updateTren(con, tren);

                    // actualizează tabelul
                    tableModel.fireTableDataChanged();
                    JOptionPane.showMessageDialog(null, "Tren actualizat cu succes!");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Numerele trebuie să fie valide!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Eroare la actualizarea trenului în DB!");
                }
            }
        });



    }

    private Tren createTren() {
        try {
            int id = Integer.parseInt(idInput.getText());
            String tip = tipInput.getText();
            int numar = Integer.parseInt(numarInput.getText());
            String clase = claseInput.getText();
            int nrVagoane = Integer.parseInt(nrVagoaneInput.getText());

            Operator operator = (Operator) operatorCombo.getSelectedItem();
            Ruta ruta = (Ruta) rutaCombo.getSelectedItem();

            return new Tren(id, tip, numar, clase, nrVagoane, operator, ruta);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Date invalide");
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new TrenViewer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
