package gui;

import gui.util.AlbumTableModel;
import gui.util.MusicianTableModel;
import model.Band;
import model.Musician;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BandScreen extends JFrame {
    private Band band;
    private JTable musicianTable;
    private JTable albumTable;
    private JButton addAlbumButton;
    private JButton addMusicianButton;
    private JButton backButton;
    private JLabel bandSellsLabel;
    private JLabel bandStatusLabel;
    private JLabel bandNameLabel;
    private JPanel panel1;
    private JButton removeMusicianButton;
    private JPanel musiciansPanel;
    private JPanel albumsPanel;
    private JPanel labelPanel;

    public BandScreen(Band band) {
        this.band = band;

        setTitle("Zarządzanie zespołem: " + band.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Color accentColor = new Color(60, 120, 180);
        Color dangerColor = new Color(200, 60, 60);

        for (JButton btn : new JButton[]{addAlbumButton, addMusicianButton, removeMusicianButton, backButton}) {
            btn.setFont(buttonFont);
            btn.setBackground(accentColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        removeMusicianButton.setBackground(dangerColor);

        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBackground(new Color(245, 245, 245));
        labelPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Informacje o zespole"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        bandNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bandStatusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bandSellsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Wypełnij dane zespołu
        updateLabels();

        // Tabele


        albumTable.setRowHeight(25);
        albumTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        albumTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        albumTable.getTableHeader().setBackground(new Color(220, 220, 220));

        albumTable.setModel(new AlbumTableModel(band.getAlbums()));

        albumsPanel.setLayout(new GridLayout());
        albumsPanel.setBorder(BorderFactory.createTitledBorder("Albums"));


        musicianTable.setRowHeight(25);
        musicianTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        musicianTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        musicianTable.getTableHeader().setBackground(new Color(220, 220, 220));

        musicianTable.setModel(new MusicianTableModel(band.getMembers()));
        musicianTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        musiciansPanel.setLayout(new GridLayout());
        musiciansPanel.setBorder(BorderFactory.createTitledBorder("Musicians"));


        addAlbumButton.addActionListener(e -> {
            AlbumScreen albumScreen = new AlbumScreen(band);
            albumScreen.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTables();
                }
            });
        });

        // Dodawanie muzyków przez ScoutScreen
        addMusicianButton.addActionListener(e -> {
            ScoutScreen scoutScreen = new ScoutScreen(band);
            scoutScreen.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    refreshTables();
                }
            });
        });

        // Powrót do menu
        backButton.addActionListener(e -> {
            new MainScreen();
            dispose();
        });

        removeMusicianButton.addActionListener(e -> {
            int selectedRow = musicianTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Wybierz muzyka do usunięcia.");
                return;
            }

            Musician selectedMusician = ((MusicianTableModel) musicianTable.getModel()).getMusicianAt(selectedRow);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Czy na pewno chcesz usunąć " + selectedMusician.getName() + " z zespołu?",
                    "Potwierdzenie", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                band.removeMusician(selectedMusician);
                refreshTables();
            }
        });


        setVisible(true);
    }

    private void updateLabels() {
        bandNameLabel.setText("Nazwa zespołu: " + band.getName());
        bandStatusLabel.setText("Status: " + band.getClass().getSimpleName());
        bandSellsLabel.setText("Sprzedaż albumów: " + band.getTotalSells());
    }

    private void refreshTables() {
        albumTable.setModel(new AlbumTableModel(band.getAlbums()));
        musicianTable.setModel(new MusicianTableModel(band.getMembers()));
        updateLabels();
    }
}
