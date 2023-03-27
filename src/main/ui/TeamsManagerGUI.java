package ui;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents a LOL team manager application with GUI
public class TeamsManagerGUI extends JFrame implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues

    private JPanel mainMenu;
    private JDesktopPane desktopPane;

    public TeamsManagerGUI() {
        super("LOL Teams Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
/*        mainMenu = new JPanel();
        mainMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(mainMenu);*/
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
        addLabelMain();
        addButtonsMain();
    }

    private void addLabelMain() {
        ImageIcon appImage = new ImageIcon("./images/LOLTM.png");
        JLabel menu = new JLabel("Main Menu");
        JLabel title = new JLabel(appImage);
        title.setIcon(appImage);
        setLayout(new BorderLayout());
        setContentPane(title);

        menu.setLayout(new BorderLayout());
        menu.setFont(new Font("Georgia", Font.BOLD, 24));
        menu.setForeground(new Color(0xCE920F));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.ipady = 40;
        setLayout(new GridBagLayout());

        add(menu, gbc);
    }

    private void addButtonsMain() {
        JButton button1;
        JButton button2;
        button1 = new JButton("View All Leagues");
        button1.setActionCommand("All Leagues");
        button2 = new JButton("View All Teams");
        button2.setActionCommand("All Teams");
        button1.setPreferredSize(new Dimension(200, 40));
        button2.setPreferredSize(new Dimension(200, 40));
        button1.addActionListener(this);
        button2.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(button1, gbc);
        add(button2, gbc);
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
        if (e.getActionCommand().equals("All Leagues")) {
            allLeaguesMenu();
        } else if (e.getActionCommand().equals("All Teams")) {
            allTeamsMenu();
        }
    }

    private void allLeaguesMenu() {

    }

    private void allTeamsMenu() {

    }
}
