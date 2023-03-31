package ui;

import model.League;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents a Listener for adding leagues based on Java Oracle ListDemo
public class AddLeagueListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;
    private JList allLeaguesList;
    private DefaultListModel<String> listModelLeagueNames;
    private ArrayList<League> allLeagues;
    private JTextField newName;

    // EFFECTS: constructs a Listener with a button, a listmodel, a jlist, a list of all leagues and a jtextfield
    public AddLeagueListener(JButton button, DefaultListModel listModelLeagueNames, JList allLeaguesList,
                             ArrayList<League> allLeagues, JTextField newName) {
        this.button = button;
        this.allLeagues = allLeagues;
        this.allLeaguesList = allLeaguesList;
        this.listModelLeagueNames = listModelLeagueNames;
        this.newName = newName;
    }

    @Override
    //MODIFIES: this
    //EFFECTS: adds a league to the list of allLeagues
    public void actionPerformed(ActionEvent e) {
        String name = newName.getText();

        //User didn't type in a unique name...
        if (name.equals("") || alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            newName.requestFocusInWindow();
            newName.selectAll();
            return;
        }

        int index = allLeaguesList.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        listModelLeagueNames.addElement(newName.getText());
        allLeagues.add(new League(name));

        //Reset the text field.
        newName.requestFocusInWindow();
        newName.setText("");

        //Select the new item and make it visible.
        allLeaguesList.setSelectedIndex(index);
        allLeaguesList.ensureIndexIsVisible(index);
    }

    // EFFECTS: returns true if a league is already in the listModel
    protected boolean alreadyInList(String name) {
        return listModelLeagueNames.contains(name);
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
