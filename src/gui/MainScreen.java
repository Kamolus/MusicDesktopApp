package gui;

import model.Band;
import model.ObjectExtent;
import model.PopularBand;
import model.UnpopularBand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends JFrame {

    private JPanel mainPanel;
    private JButton scoutButton;
    private JButton bandButton;


    public MainScreen() {
        setTitle("System zarzadzania zespolami");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        scoutButton.addActionListener(e -> {
            new ScoutScreen();
            dispose();
        });

        bandButton.addActionListener(e -> {
            List<Band> bands = new ArrayList<>();
            bands.addAll(ObjectExtent.getExtentFromClass(PopularBand.class));
            bands.addAll(ObjectExtent.getExtentFromClass(UnpopularBand.class));

            if (bands.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Brak zespołów w systemie!");
                return;
            }

            Band selectedBand = (Band) JOptionPane.showInputDialog(
                    this,
                    "Wybierz zespół:",
                    "Wybór zespołu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    bands.toArray(),
                    bands.get(0)
            );

            if (selectedBand != null) {
                new BandScreen(selectedBand);
                dispose();
            }
        });

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(250, 50);

        for (JButton btn : new JButton[]{scoutButton, bandButton}) {
            btn.setFont(buttonFont);
            btn.setMaximumSize(buttonSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(60, 120, 180));
            btn.setForeground(Color.WHITE);
        }



        setContentPane(mainPanel);
        setVisible(true);
    }
}


