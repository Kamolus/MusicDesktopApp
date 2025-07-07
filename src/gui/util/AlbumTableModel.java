package gui.util;

import model.Album;
import model.ObjectExtent;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AlbumTableModel extends AbstractTableModel {

    private List<Album> albums;
    private final String[] columnNames = { "Album Title", "Album Year", "Album Genre" ,"Album Sells"};

    public AlbumTableModel(List<Album> albums) {
        this.albums = albums;
    }

    public int getRowCount() { return albums.size(); }

    public int getColumnCount() { return columnNames.length; }

    public String getColumnName(int col) { return columnNames[col]; }

    public Object getValueAt(int rowIndex, int colIndex) {
        Album album = albums.get(rowIndex);
        return switch (colIndex){
            case 0 -> album.getTitle();
            case 1 -> album.getReleaseYear();
            case 2 -> album.getGenreNamee();
            case 3 -> album.getSells();
            default -> null;
        };
    }

    public Album getAlbumAt(int rowIndex) {
        return albums.get(rowIndex);
    }

    public void refresh() {
        this.albums = ObjectExtent.getExtentFromClass(Album.class);
        fireTableDataChanged();
    }
}
