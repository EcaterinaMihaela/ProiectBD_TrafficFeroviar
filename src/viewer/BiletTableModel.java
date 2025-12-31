package viewer;

import model.Bilet;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BiletTableModel extends AbstractTableModel {

    private List<Bilet> biletList;
    private String[] colNames = {"ID", "Detalii", "Pret", "Reducere", "ID Tren", "Status", "Data", "Loc Vagon", "Nr Legitimatie", "Gara", "Nr Vagon"};

    public BiletTableModel(List<Bilet> biletList) {
        this.biletList = biletList;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public int getRowCount() {
        return biletList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex >= biletList.size()) return null;
        Bilet b = biletList.get(rowIndex);
        switch(columnIndex) {
            case 0: return b.getIdBilet();
            case 1: return b.getDetalii();
            case 2: return b.getPret();
            case 3: return b.getTipReducere();
            case 4: return b.getIdTren();
            case 5: return b.getStatus();
            case 6: return b.getDataCumpararii();
            case 7: return b.getLocVagon();
            case 8: return b.getNrLegitimatie();
            case 9: return b.getNumeGara();
            case 10: return b.getNrVagon();
            default: return null;
        }
    }
}
