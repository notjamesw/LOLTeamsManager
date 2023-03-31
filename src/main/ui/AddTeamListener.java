package ui;

import model.League;
import model.Team;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents a Listener for adding teams based on Java Oracle ListDemo
public class AddTeamListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;
    private JList teamList;
    private DefaultListModel<String> listModel;
    private League league;
    private ArrayList<Team> allTeams;
    private JTextField newName;

    // EFFECTS: constructs a Listener with a button, a listmodel, a jlist, a list of all teams and a jtextfield
    public AddTeamListener(JButton button, DefaultListModel listModel, JList teamList,
                           League league, JTextField newName, ArrayList<Team> allTeams) {
        this.button = button;
        this.league = league;
        this.newName = newName;
        this.teamList = teamList;
        this.listModel = listModel;
        this.allTeams = allTeams;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = newName.getText();

        //User didn't type in a unique name...
        if (name.equals("") || alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            newName.requestFocusInWindow();
            newName.selectAll();
            return;
        }

        int index = teamList.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        listModel.addElement(newName.getText());
        Team team = new Team(name, league.getName());
        league.addTeam(team);
        allTeams.add(team);

        //Reset the text field.
        newName.requestFocusInWindow();
        newName.setText("");

        //Select the new item and make it visible.
        teamList.setSelectedIndex(index);
        teamList.ensureIndexIsVisible(index);
    }

    // EFFECTS: checks if a team is already in listModel
    protected boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: enables button
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: checks if the field is empty, if it is disable the button
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
