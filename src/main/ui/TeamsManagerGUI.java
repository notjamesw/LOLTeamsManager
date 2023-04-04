package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

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
    private static final Font titleFont = new Font("Georgia", Font.BOLD, 24);

    private ArrayList<Team> allTeams;     // list of all teams
    private ArrayList<League> allLeagues; // list of all leagues
    private League currentLeague;

    private JPanel mainMenu;
    private Container contentPane;
    private CardLayout allMenus;
    private JPanel allLeaguesMenu;
    private JPanel allTeamsMenu;
    private JPanel leagueMenu;
    private JPanel teamMenu;
    private JTextField newName;
    private AddLeagueListener addLeagueListener;
    private JButton buttonSelectLeague;
    private AddTeamListener addTeamListener;
    private JButton buttonSelectTeam;
    private GridBagConstraints gbc;
    private JButton buttonRemoveTeam;

    private DefaultListModel<String> listModelLeagueNames;
    private DefaultListModel<String> listModelTeamNames;
    private JList allLeaguesList;
    private JList allTeamsList;

    // EFFECTS: runs the teams manager GUI application
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

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void init() {
        this.allTeams = new ArrayList<>();
        this.allLeagues = new ArrayList<>();
        this.allTeamsMenu = new JPanel();
        this.allLeaguesMenu = new JPanel();
        this.allMenus = new CardLayout();
        this.leagueMenu = new JPanel();
        this.teamMenu = new JPanel();
        this.currentLeague = null;
        this.buttonRemoveTeam = new JButton("Remove Team");
        this.buttonSelectTeam = new JButton("Team Info");
        this.allTeamsList = new JList();
        this.allLeaguesList = new JList();
        gbc();

        this.mainMenu = new JPanel();
        mainMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        layerMenus();
    }

    // MODIFIES: this
    // EFFECTS: set up GridBagConstraints
    private void gbc() {
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    // MODIFIES: this
    // EFFECTS: sets up a layered contentPane
    private void layerMenus() {
        contentPane = this.getContentPane();
        contentPane.setLayout(allMenus);
        contentPane.setPreferredSize(winSize);
        contentPane.add("main", mainMenu);
        contentPane.add("allTeams", allTeamsMenu);
        contentPane.add("allLeagues", allLeaguesMenu);
        contentPane.add("League Menu", leagueMenu);
        contentPane.add("Team Menu", teamMenu);
    }

    // MODIFIES: this
    // EFFECTS: sets up and displays mainMenu
    private void runApp() {
        addDropDownMenu();
        addLabelMain();
        addButtonsMain();
    }

    // MODIFIES: this
    // EFFECTS: adds the title and the background icon to mainMenu panel
    private void addLabelMain() {
        ImageIcon appImage = new ImageIcon("./images/LOLTM.png");
        JLabel menu = new JLabel("Main Menu");
        JLabel title = new JLabel(appImage);

        menu.setLayout(new BorderLayout());
        menu.setFont(titleFont);
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

    // MODIFIES: this
    // EFFECTS: adds buttons to the mainMenu panel
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

        mainMenu.add(buttonLeagues, this.gbc);
        mainMenu.add(buttonTeams, this.gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds the drop-down menu bar
    private void addDropDownMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save Files");
        JMenuItem loadMenu = new JMenuItem("Load Files");
        loadMenu.addActionListener(this);
        saveMenu.addActionListener(this);

        filesMenu.add(loadMenu);
        filesMenu.add(saveMenu);
        menuBar.add(filesMenu);
        setJMenuBar(menuBar);
    }

    @Override
    // MODIFIES: this
    // EFFECTS: checks for action events and carries out corresponding events
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("All Leagues")) {
            allMenus.show(contentPane, "allLeagues");
            allLeaguesMenu();
            currentLeague = null;
        } else if (e.getActionCommand().equals("All Teams")) {
            allMenus.show(contentPane, "allTeams");
            allTeamsMenu();
        } else if (e.getActionCommand().equals("Main Menu")) {
            allMenus.first(contentPane);
        } else if (e.getActionCommand().equals("League Menu")) {
            leagueMenuActionPerformed(e);
        } else if (e.getActionCommand().equals("Team Menu")) {
            teamSelectActionPerformed(e);
        } else if (e.getActionCommand().equals("Remove Team")) {
            removeTeamActionPerformed();
        } else if (e.getActionCommand().equals("Return")) {
            returnFromTeamActionPerformed();
        } else {
            menuBarActionPerformed(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a team
    private void removeTeamActionPerformed() {
        int index = allTeamsList.getSelectedIndex();
        int size = listModelTeamNames.getSize();
        if (size == 0) {
            buttonRemoveTeam.setEnabled(false);
        } else {
            buttonRemoveTeam.setEnabled(true);
            Team team = currentLeague.getTeams().get(index);
            currentLeague.removeTeam(team);
            allTeams.remove(team);
            listModelTeamNames.remove(index);

            allTeamsList.setSelectedIndex(index);
            allTeamsList.ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: this
    // EFFECTS: return to original menu from team menu
    private void returnFromTeamActionPerformed() {
        if (currentLeague == null) {
            allMenus.show(contentPane, "allTeams");
            allTeamsMenu();
        } else {
            allMenus.show(contentPane, "League Menu");
            leagueMenu(currentLeague);
        }
    }

    // MODIFIES: this
    // EFFECTS: goes to league Menu
    private void leagueMenuActionPerformed(ActionEvent e) {
        int index = allLeaguesList.getSelectedIndex();
        int size = listModelLeagueNames.getSize();
        if (size == 0) {
            buttonSelectLeague.setEnabled(false);
        } else {
            buttonSelectLeague.setEnabled(true);
            allMenus.show(contentPane, "League Menu");
            currentLeague = allLeagues.get(index);
            leagueMenu(currentLeague);
        }
    }

    // REQUIRES: team model list is not empty
    // MODIFIES: this
    // EFFECTS: goes to team Menu
    private void teamSelectActionPerformed(ActionEvent e) {
        int index = allTeamsList.getSelectedIndex();
        allMenus.show(contentPane, "Team Menu");
        if (currentLeague == null) {
            teamMenu(allTeams.get(index));
        } else {
            teamMenu(currentLeague.getTeams().get(index));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays and sets up league Menu
    private void leagueMenu(League league) {
        leagueMenu.removeAll();
        leagueMenu.setLayout(new GridBagLayout());
        JLabel title = new JLabel("League Menu");
        JLabel leagueName = new JLabel("League: " + league.getName());
        title.setPreferredSize(new Dimension(700,50));
        title.setFont(titleFont);
        leagueName.setFont(titleFont);

        listModelTeamNames = makeListModelTeamsName(league.getTeams());
        allTeamsList = new JList(listModelTeamNames);
        allTeamsListSetUp();

        JScrollPane listScrollPane = new JScrollPane(allTeamsList);

        leagueMenu.add(title, gbc);
        leagueMenu.add(leagueName, gbc);
        leagueMenu.add(listScrollPane, gbc);
        leagueMenu.add(buttonPanelLeague(league), gbc);
    }

    // MODIFIES: this
    // EFFECTS: sets up allTeams JList
    private void allTeamsListSetUp() {
        allTeamsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allTeamsList.setSelectedIndex(0);
        allTeamsList.addListSelectionListener(this);
        allTeamsList.setVisibleRowCount(20);
        allTeamsList.setPreferredSize(new Dimension(700,400));
    }

    // MODIFIES: this
    // EFFECTS: returns a JPanel and set up buttons to League Menu Panel
    private JPanel buttonPanelLeague(League league) {
        JButton buttonBack;
        JButton buttonAdd;

        buttonBack = new JButton("Return to Leagues");
        buttonBack.setActionCommand("All Leagues");
        buttonAdd = new JButton("Create Team");
        buttonAdd.setActionCommand("Add Team");
        //buttonRemoveTeam = new JButton("Remove Team");
        buttonRemoveTeam.setActionCommand("Remove Team");
        //buttonSelectTeam = new JButton("Team Info");
        buttonSelectTeam.setActionCommand("Team Menu");

        addLeagueField();

        addTeamListener = new AddTeamListener(buttonAdd, listModelTeamNames, allTeamsList, league, newName, allTeams);
        buttonAdd.addActionListener(addTeamListener);
        buttonBack.addActionListener(this);
        buttonAdd.addActionListener(this);
        buttonSelectTeam.addActionListener(this);
        buttonRemoveTeam.addActionListener(this);
        return addButtonsLeagueMenu(buttonBack, buttonAdd);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to League Menu Panel
    private JPanel addButtonsLeagueMenu(JButton buttonBack, JButton buttonAdd) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(buttonBack);
        buttonsPanel.add(buttonAdd);
        buttonsPanel.add(newName);
        buttonsPanel.add(buttonSelectTeam);
        buttonsPanel.add(buttonRemoveTeam);
        return buttonsPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up teamMenu JPanel
    private void teamMenu(Team team) {
        teamMenu.removeAll();
        teamMenu.setLayout(new GridBagLayout());
        JLabel title = new JLabel("Team Menu");
        title.setFont(titleFont);
        JLabel teamName = new JLabel("Team Name:" + team.getTeamName());
        JLabel teamLeague = new JLabel("League:" + team.getLeague());
        JButton back = new JButton("Return");
        back.setActionCommand("Return");
        back.addActionListener(this);

        teamMenu.add(title, gbc);
        teamMenu.add(teamName, gbc);
        teamMenu.add(teamLeague, gbc);
        teamMenu.add(back, gbc);
    }

    // MODIFIES: this
    // EFFECTS: changes based on action performed
    public void menuBarActionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load Files")) {
            loadFiles();
        } else if (e.getActionCommand().equals("Save Files")) {
            saveFiles();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads files
    private void loadFiles() {
        int answer = JOptionPane.showConfirmDialog(null, "Load Save Files?",
                "Load", JOptionPane.YES_NO_OPTION);
        PopUpMessage message;

        if (answer == JOptionPane.YES_OPTION) {
            // for testing
            //JsonReader reader = new JsonReader("./data/savedLeagues.json", "./data/savedTeams.json");
            JsonReader reader = new JsonReader("./data/savedLeaguesGUI.json", "./data/savedTeamsGUI.json");
            try {
                allLeagues = reader.readLeagues();
                allTeams = reader.readTeams();
                message = new PopUpMessage("Load was successful.");
                allLeaguesMenuSetUpLists();
            } catch (IOException e) {
                message = new PopUpMessage("Exception was thrown. No save file was found. ");
            }
        } else {
            message = new PopUpMessage("No files were loaded.");
        }
    }

    // MODIFIES: this
    // EFFECTS: save files
    private void saveFiles() {
        int answer = JOptionPane.showConfirmDialog(null, "Save to Files?",
                "Save", JOptionPane.YES_NO_OPTION);
        PopUpMessage message;

        if (answer == JOptionPane.YES_OPTION) {
            try {
                JsonWriter writer = new JsonWriter("./data/savedLeaguesGUI.json", "./data/savedTeamsGUI.json");
                writer.open();
                writer.writeLeagues(allLeagues);
                writer.writeTeams(allTeams);
                writer.close();
                message = new PopUpMessage("Save was successful.");
            } catch (IOException e) {
                message = new PopUpMessage("Exception was thrown. Save failed.");
            }
        } else {
            message = new PopUpMessage("No files were saved.");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (allLeaguesList.getSelectedIndex() == -1) {
                buttonSelectLeague.setEnabled(false);
            } else {
                buttonSelectLeague.setEnabled(true);
            }

            if (allTeamsList.getSelectedIndex() == -1) {
                buttonRemoveTeam.setEnabled(false);
            } else {
                buttonRemoveTeam.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up all leagues menu
    private void allLeaguesMenu() {
        allLeaguesMenu.removeAll();
        allLeaguesMenu.setLayout(new GridBagLayout());
        JLabel title = new JLabel("All Leagues Menu");
        title.setPreferredSize(new Dimension(700,50));
        title.setFont(titleFont);
        allLeaguesMenuSetUpLists();
        JScrollPane listScrollPane = new JScrollPane(allLeaguesList);

        allLeaguesMenu.add(title, gbc);
        allLeaguesMenu.add(listScrollPane, gbc);
        allLeaguesMenu.add(buttonPanelAllLeagues(), gbc);
    }

    // MODIFIES: this
    // EFFECTS: set up all leagues menu lists
    private void allLeaguesMenuSetUpLists() {
        listModelLeagueNames = makeListModelLeaguesName(allLeagues);
        allLeaguesList = new JList(listModelLeagueNames);
        allLeaguesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allLeaguesList.setSelectedIndex(0);
        allLeaguesList.addListSelectionListener(this);
        allLeaguesList.setVisibleRowCount(20);
        allLeaguesList.setPreferredSize(new Dimension(700,400));
    }

    // MODIFIES: this
    // EFFECTS: makes a ListModel of leagues
    private DefaultListModel<String> makeListModelLeaguesName(ArrayList<League> leagues) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (League league : leagues) {
            listModel.addElement(league.getName());
        }
        return listModel;
    }

    // MODIFIES: this
    // EFFECTS: adds button panel to all leagues
    private JPanel buttonPanelAllLeagues() {
        JPanel buttonsPanel = new JPanel();
        JButton buttonBack;
        JButton buttonAdd;

        buttonBack = new JButton("Return to Main Menu");
        buttonBack.setActionCommand("Main Menu");
        buttonAdd = new JButton("Create League");
        buttonAdd.setActionCommand("Add League");
        buttonSelectLeague = new JButton("League Info");
        buttonSelectLeague.setActionCommand("League Menu");

        addLeagueField();

        addLeagueListener = new AddLeagueListener(buttonAdd, listModelLeagueNames, allLeaguesList, allLeagues, newName);
        buttonBack.addActionListener(this);
        buttonAdd.addActionListener(addLeagueListener);
        buttonSelectLeague.addActionListener(this);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(buttonBack);
        buttonsPanel.add(buttonAdd);
        buttonsPanel.add(newName);
        buttonsPanel.add(buttonSelectLeague);
        return buttonsPanel;
    }

    // MODIFIES: this
    // EFFECTS: add field to league menu
    private void addLeagueField() {
        newName = new JTextField(10);
        newName.addActionListener(addLeagueListener);
        newName.getDocument().addDocumentListener(addLeagueListener);
        //String name = listModelLeagueNames.getElementAt(allLeaguesList.getSelectedIndex()).toString();
    }

    // MODIFIES: this
    // EFFECTS: makes an all team menu
    private void allTeamsMenu() {
        allTeamsMenu.removeAll();
        allTeamsMenu.setLayout(new GridBagLayout());
        JLabel title = new JLabel("All Teams Menu");
        title.setPreferredSize(new Dimension(700,50));
        title.setFont(titleFont);
        listModelTeamNames = makeListModelTeamsName(allTeams);
        allTeamsList = new JList(listModelTeamNames);
        allTeamsListSetUp();
        JScrollPane listScrollPane = new JScrollPane(allTeamsList);

        allTeamsMenu.add(title, gbc);
        allTeamsMenu.add(listScrollPane, gbc);
        allTeamsMenu.add(buttonPanelAllTeams(), gbc);
    }

    // MODIFIES: this
    // EFFECTS: add buttons to all teams panel
    private JPanel buttonPanelAllTeams() { //removed adding functionality due to time restraints
        JPanel buttonsPanel = new JPanel();
        JButton buttonBack;
        //JButton buttonAdd;
        JButton buttonSelect;

        buttonBack = new JButton("Return to Main Menu");
        buttonBack.setActionCommand("Main Menu");
        /*
        buttonAdd = new JButton("Create Team");
        buttonAdd.setActionCommand("Add Team NL");
        */

        buttonSelectTeam = new JButton("Team Info");
        buttonSelectTeam.setActionCommand("Team Menu");

        buttonBack.addActionListener(this);
        //buttonAdd.addActionListener(this);
        buttonSelectTeam.addActionListener(this);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
        buttonsPanel.add(buttonBack);
        //buttonsPanel.add(buttonAdd);
        buttonsPanel.add(buttonSelectTeam);
        return buttonsPanel;
    }

    // MODIFIES: this
    // EFFECTS: makes a ListModel of teams
    private DefaultListModel<String> makeListModelTeamsName(ArrayList<Team> teams) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Team team : teams) {
            listModel.addElement(team.getTeamName());
        }
        return listModel;
    }
}