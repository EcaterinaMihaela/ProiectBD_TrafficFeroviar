package viewer;

import model.Tren;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TrenTableModel extends AbstractTableModel {

    private List<Tren> trenList;
    private String[] colNames = {"ID Tren", "Tip", "Numar", "Clase", "Nr Vagoane", "Operator", "Ruta"};

    public TrenTableModel(List<Tren> trenList) {
        this.trenList = trenList;
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
        return trenList.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        if(row < 0 || row >= trenList.size()) return null;
        Tren t = trenList.get(row);
        switch(column) {
            case 0: return t.getIdTren();
            case 1: return t.getTip();
            case 2: return t.getNumar();
            case 3: return t.getClase();
            case 4: return t.getNrVagoane();
            case 5: return t.getOperator() != null ? t.getOperator().getNumeOperator() : "";
            case 6: return t.getRuta() != null ? t.getRuta().getNumeRuta() : "";
            default: return null;
        }
    }
}
