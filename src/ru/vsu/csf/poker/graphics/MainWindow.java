package ru.vsu.csf.poker.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {

    private DrawPanel panel;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 800);
        panel = new DrawPanel();
        add(panel);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
