package viewer;

import model.Calator;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CalatorTableModel extends AbstractTableModel {

    private List<Calator> calatorList;
    private String[] colNames = {"Nr Legitimatie", "Nume", "Prenume", "Tip", "Contact"};

    public CalatorTableModel(List<Calator> calatorList) {
        this.calatorList = calatorList;
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
        return calatorList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex >= calatorList.size()) return null;
        Calator c = calatorList.get(rowIndex);
        switch(columnIndex) {
            case 0: return c.getNrLegitimatie();
            case 1: return c.getNume();
            case 2: return c.getPrenume();
            case 3: return c.getTip();
            case 4: return c.getContact();
            default: return null;
        }
    }
}
