/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

// if u wanna import something out of current directory, do this
// package "ThePackage"."Folder"."File"
package TutorMain;

import ATCLogin.Login;
import TutorBackend.*;
import TutorFrontend.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;


/**
 * Main class to run the ATW Tutor Management System
 * @author jeatr
 */
public class ATWTutorMain {
    
    public static void main(String[] args) {
        // Set look and feel to system default for better appearance
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        try {
            // Show login first
            Login login = new Login();
            login.setVisible(true);

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, 
                "Failed to start ATW Tutor Management System:\n" + e.getMessage(),
                "Application Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

