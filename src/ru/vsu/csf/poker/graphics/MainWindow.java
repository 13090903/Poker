package ru.vsu.csf.poker.graphics;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private DrawPanel panel;

    public static void main(String[] args) {
        new MainWindow();
    }

    public MainWindow() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1400, 800);
        panel = new DrawPanel();
        add(panel);
        setVisible(true);
    }
}
