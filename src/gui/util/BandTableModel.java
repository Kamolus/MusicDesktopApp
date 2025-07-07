package gui.util;

import model.Band;
import model.ObjectExtent;
import model.PopularBand;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BandTableModel extends AbstractTableModel {

    private List<PopularBand> bands;
    private final String[] columnNames = {"Band Name"};

    public BandTableModel(List<PopularBand> bands) { this.bands = bands; }

    public int getColumnCount() { return columnNames.length; }

    public int getRowCount() { return bands.size(); }

    public String getColumnName(int col) { return columnNames[col]; }

    public Object getValueAt(int rowIndex, int colIndex) {
        Band band = bands.get(rowIndex);
        return switch (colIndex){
            case 0 -> band.getName();
            default -> null;
        };
    }

    public Band getBand(int row) { return bands.get(row); }

    public void refresh() {
        this.bands = ObjectExtent.getExtentFromClass(PopularBand.class);
        fireTableDataChanged();
    }
}
