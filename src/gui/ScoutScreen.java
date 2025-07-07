package gui;

import gui.util.BandTableModel;
import gui.util.MusicianTableModel;
import model.Band;
import model.Musician;
import model.ObjectExtent;
import model.PopularBand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScoutScreen extends JFrame {
    private JTable musicianTable;
    private JTable bandTable;
    private JButton assignButton;
    private JButton returnToMenuButton;
    private JPanel panel1;
    private JPanel panel2;
    private boolean singleBandMode = false;
    private Band targetBand;

    public ScoutScreen() {
        setTitle("Scout Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(250, 50);

        for (JButton btn : new JButton[]{assignButton, returnToMenuButton}) {
            btn.setFont(buttonFont);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(60, 120, 180));
            btn.setForeground(Color.WHITE);
        }

        MusicianTableModel musicianModel = new MusicianTableModel(ObjectExtent.getExtentFromClass(Musician.class));
        BandTableModel bandModel = new BandTableModel(ObjectExtent.getExtentFromClass(PopularBand.class));

        musicianTable.setModel(musicianModel);
        bandTable.setModel(bandModel);

        assignButton.addActionListener(this::assignMusicianToBand);

        returnToMenuButton.addActionListener(e -> {
            new MainScreen();
            dispose();
        });

        setVisible(true);
    }

    public ScoutScreen(Band targetBand) {
        this();
        this.singleBandMode = true;
        this.targetBand = targetBand;

        bandTable.setVisible(false);
        returnToMenuButton.setVisible(false);
        returnToMenuButton.setEnabled(false);

        for (var listener : assignButton.getActionListeners()) {
            assignButton.removeActionListener(listener);
        }

        assignButton.addActionListener(this::assignToSingleBand);
    }

    private void assignMusicianToBand(ActionEvent e) {
        int musicianRow = musicianTable.getSelectedRow();
        int bandRow = bandTable.getSelectedRow();

        if (musicianRow == -1 || bandRow == -1) {
            JOptionPane.showMessageDialog(this, "Wybierz muzyka i zespół!");
            return;
        }

        Musician selectedMusician = ((MusicianTableModel) musicianTable.getModel()).getMusicianAt(musicianRow);
        Band selectedBand = ((BandTableModel) bandTable.getModel()).getBand(bandRow);

        try {
            if (!selectedMusician.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Muzyk już należy do innego zespołu!");
                return;
            }
            selectedBand.addMusician(selectedMusician);
            JOptionPane.showMessageDialog(this, "Dodano muzyka do zespołu!");
            musicianTable.repaint();
            bandTable.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd: " + ex.getMessage());
        }
    }

    private void assignToSingleBand(ActionEvent e) {
        int row = musicianTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Wybierz muzyka!");
            return;
        }

        Musician selectedMusician = ((MusicianTableModel) musicianTable.getModel()).getMusicianAt(row);

        try {
            if (!selectedMusician.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Muzyk już należy do innego zespołu!");
                return;
            }
            targetBand.addMusician(selectedMusician);
            JOptionPane.showMessageDialog(this, "Dodano muzyka do zespołu: " + targetBand.getName());
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd: " + ex.getMessage());
        }
    }
}