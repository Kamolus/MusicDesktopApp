package gui.util;

import model.Musician;
import model.ObjectExtent;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MusicianTableModel extends AbstractTableModel {

    private List<Musician> musicians;
    private final String[] columnNames = {"Musician Name","Type"};

    public MusicianTableModel(List<Musician> musicians) {
        this.musicians = musicians;
    }

    public int getRowCount() { return musicians.size(); }

    public int getColumnCount() { return columnNames.length; }

    public String getColumnName(int col) { return columnNames[col]; }

    public Object getValueAt(int rowIndex, int colIndex) {
        Musician musician = musicians.get(rowIndex);
        return switch (colIndex){
            case 0 -> musician.getName();
            case 1 -> String.join(", ",
                    musician.getTypes().stream()
                            .map(Enum::name)
                            .toList());
            default -> null;
        };
    }

    public Musician getMusicianAt(int rowIndex) {
        return musicians.get(rowIndex);
    }
}
