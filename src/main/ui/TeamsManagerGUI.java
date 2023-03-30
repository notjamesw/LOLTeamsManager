package ui;

import model.*;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

// Represents a LOL team manager application with GUI
public class TeamsManagerGUI extends JFrame implements ActionListener, ListSelectionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Dimension winSize = new Dimension(800, 600);
    private static final Color brown = new Color(0xCE920F);
    private static final Color blue = new Color(0x122839);

    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues

    private JPanel mainMenu;
    private Container contentPane;
    private CardLayout allMenus;
    private JPanel allLeaguesMenu;
    private JPanel allTeamsMenu;

    public TeamsManagerGUI() {
        super("LOL Teams Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(winSize);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
        runApp();
        setVisible(true);
    }

    private void init() {
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
        this.allTeamsMenu = new JPanel();
        this.allLeaguesMenu = new JPanel();
        this.allMenus = new CardLayout();

        mainMenu = new JPanel();
        mainMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        layerMenus();
    }

    private void layerMenus() {
        contentPane = this.getContentPane();
        contentPane.setLayout(allMenus);
        contentPane.setPreferredSize(winSize);
        contentPane.add(mainMenu, 0);
        contentPane.add(allTeamsMenu, 1);
        contentPane.add(allLeaguesMenu, 2);
    }

    private void runApp() {
        addDropDownMenu();
        addLabelMain();
        addButtonsMain();
    }

    private void addLabelMain() {
        ImageIcon appImage = new ImageIcon("./images/LOLTM.png");
        JLabel menu = new JLabel("Main Menu");
        JLabel title = new JLabel(appImage);
        title.setIcon(appImage);

        menu.setLayout(new BorderLayout());
        menu.setFont(new Font("Georgia", Font.BOLD, 24));
        menu.setForeground(brown);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.ipady = 40;
        mainMenu.setLayout(new GridBagLayout());
        mainMenu.setBackground(blue);

        mainMenu.add(title, gbc);
        mainMenu.add(menu, gbc);
    }

    private void addButtonsMain() {
        JButton buttonLeagues;
        JButton buttonTeams;
        buttonLeagues = new JButton("View All Leagues");
        buttonLeagues.setActionCommand("All Leagues");
        buttonTeams = new JButton("View All Teams");
        buttonTeams.setActionCommand("All Teams");
        buttonLeagues.setPreferredSize(new Dimension(200, 40));
        buttonTeams.setPreferredSize(new Dimension(200, 40));
        buttonLeagues.addActionListener(this);
        buttonTeams.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainMenu.add(buttonLeagues, gbc);
        mainMenu.add(buttonTeams, gbc);
    }

    private void addDropDownMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem(new SaveUI(allTeams, allLeagues));
        JMenuItem loadMenu = new JMenuItem("Load Files");
        loadMenu.addActionListener(this);

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
        } else if (e.getActionCommand().equals("Load Files")) {
            loadFiles();
        } else if (e.getActionCommand().equals("Main Menu")) {
            allMenus.first(contentPane);
        }
    }

    private void loadFiles() {
        int answer = JOptionPane.showConfirmDialog(null, "Load Save Files?",
                "Load", JOptionPane.YES_NO_OPTION);
        PopUpMessage message;

        if (answer == JOptionPane.YES_OPTION) {
            // for testing
            JsonReader reader = new JsonReader("./data/savedLeagues.json", "./data/savedTeams.json");
            //JsonReader reader = new JsonReader("./data/savedLeaguesGUI.json", "./data/savedTeamsGUI.json");
            try {
                allLeagues = reader.readLeagues();
                allTeams = reader.readTeams();
                message = new PopUpMessage("Load was successful.");
            } catch (IOException e) {
                message = new PopUpMessage("Exception was thrown. No save file was found. ");
            }
        } else {
            message = new PopUpMessage("No files were loaded.");
        }
    }

    public void valueChanged(ListSelectionEvent e) {

    }

    private void allLeaguesMenu() {

    }

    private void allTeamsMenu() {
        allMenus.next(contentPane);
        allTeamsMenu.setLayout(new GridBagLayout());
        DefaultListModel<Team> listModel = makeListModelTeams(allTeams);
        DefaultListModel<String> listModelTeamNames = makeListModelTeamsName(allTeams);
        JList jListAllTeams = new JList(listModelTeamNames);
        jListAllTeams.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListAllTeams.setSelectedIndex(0);
        jListAllTeams.addListSelectionListener(this);
        jListAllTeams.setVisibleRowCount(20);
        jListAllTeams.setPreferredSize(new Dimension(700,400));
        JScrollPane listScrollPane = new JScrollPane(jListAllTeams);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        allTeamsMenu.add(listScrollPane, gbc);
        allTeamsMenu.add(buttonPanelAllTeams(), gbc);
    }

    private JPanel buttonPanelAllTeams() {
        JPanel buttonsPanel = new JPanel();
        JButton buttonBack;
        JButton buttonAdd;
        JButton buttonSelect;

        buttonBack = new JButton("Return to Main Menu");
        buttonBack.setActionCommand("Main Menu");
        buttonAdd = new JButton("Create Team");
        buttonAdd.setActionCommand("Add Team NL");
        buttonSelect = new JButton("Team Info");
        buttonSelect = new JButton("Team Menu");

        buttonBack.addActionListener(this);
        buttonAdd.addActionListener(this);
        buttonSelect.addActionListener(this);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(buttonBack);
        buttonsPanel.add(buttonAdd);
        buttonsPanel.add(buttonSelect);
        return buttonsPanel;
    }

    private DefaultListModel<Team> makeListModelTeams(ArrayList<Team> teams) {
        DefaultListModel<Team> listModel = new DefaultListModel<>();
        for(Team team : teams) {
            listModel.addElement(team);
        }
        return listModel;
    }

    private DefaultListModel<String> makeListModelTeamsName(ArrayList<Team> teams) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(Team team : teams) {
            listModel.addElement(team.getTeamName());
        }
        return listModel;
    }
}
