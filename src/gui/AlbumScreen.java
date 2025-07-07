package gui;

import model.Album;
import model.Band;
import model.Genre;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import static model.ObjectExtent.getExtentFromClass;

public class AlbumScreen extends JFrame {
    private JPanel panel1;
    private JTextField yearField;
    private JTextField titleField;
    private JTextField sellsField;
    private JCheckBox limitedEditionCheckBox;
    private JComboBox genreComboBox;
    private JButton saveButton;
    private JLabel titleLabel;
    private JLabel yearLabel;
    private JLabel sellsLabel;
    private JLabel editionLabel;
    private JLabel genreLabel;


    private Band band;

    public AlbumScreen(Band band) {
        this.band = band;

        setTitle("Nowy album dla zespołu: " + band.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setContentPane(panel1);


        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        for (JLabel jLabel : new JLabel[]{titleLabel, yearLabel, sellsLabel, editionLabel, genreLabel}) {
            jLabel.setFont(labelFont);
        }

        saveButton.setFont(labelFont);
        saveButton.setBackground(new Color(60, 120, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        for (Genre g : getExtentFromClass(Genre.class)) {
            genreComboBox.addItem(g);
        }

        saveButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String yearText = yearField.getText().trim();
            String sellsText = sellsField.getText().trim();
            Genre selectedGenre = (Genre) genreComboBox.getSelectedItem();
            boolean isLimited = limitedEditionCheckBox.isSelected();

            if (title.isEmpty() || yearText.isEmpty() || sellsText.isEmpty() || selectedGenre == null) {
                JOptionPane.showMessageDialog(this, "Wszystkie pola muszą być uzupełnione.");
                return;
            }

            try {
                int year = Integer.parseInt(yearText);
                int sells = Integer.parseInt(sellsText);

                Album album = new Album(title, year, isLimited, selectedGenre, sells);
                band.addAlbum(album);

                selectedGenre.addAlbum(album);

                JOptionPane.showMessageDialog(this, "Album dodany!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rok i sprzedaż muszą być liczbami.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
