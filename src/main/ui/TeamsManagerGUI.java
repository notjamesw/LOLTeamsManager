package ui;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

// Represents a LOL team manager application with GUI
public class TeamsManagerGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues

    private JDesktopPane desktop;

    public TeamsManagerGUI() {
        super("LOL Teams Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        desktop = new JDesktopPane();
        setContentPane(desktop);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
        runApp();
        setVisible(true);
    }

    private void init() {
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
    }

    private void runApp() {
        addMenu();
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem(new SaveUI(allTeams, allLeagues));
        JMenuItem loadMenu = new JMenuItem(new LoadUI(allTeams, allLeagues));

        filesMenu.add(loadMenu);
        filesMenu.add(saveMenu);
        menuBar.add(filesMenu);
        setJMenuBar(menuBar);
    }


    public void actionPerformed(ActionEvent e) {

    }
}
