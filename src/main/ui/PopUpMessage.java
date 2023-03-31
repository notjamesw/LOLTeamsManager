package ui;

import javax.swing.*;

// Represents a popup message
public class PopUpMessage {
    public PopUpMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
