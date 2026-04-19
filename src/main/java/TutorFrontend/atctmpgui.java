/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TutorFrontend;

import ATCLogin.Login;
import TutorFrontend.atctcigui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Window;
import javax.swing.SwingUtilities;

/**
 *
 * @author jeatr
 */

public class atctmpgui extends JPanel {
    
    // Main components
    private JPanel selectionPanel;
    private JButton personalProfileButton;
    private JButton classInformationButton;
    private JButton studentInformationButton;
    private JButton logoutButton;
    
    private atctdgui dashboard;
    
    public atctmpgui() {
        initComponents();
        setupLayout();
        setupButtonListeners();
    }
    
    private void initComponents() {
        // Initialize selection panel
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(4, 1, 0, 20));
        
        personalProfileButton = new JButton("Personal Profile");
        personalProfileButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        classInformationButton = new JButton("Class Information");
        classInformationButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        studentInformationButton = new JButton("Student Information");
        studentInformationButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Setup selection panel
        selectionPanel.add(personalProfileButton);
        selectionPanel.add(classInformationButton);
        selectionPanel.add(studentInformationButton);
        selectionPanel.add(logoutButton);
        
        // Add selection panel to this panel
        add(selectionPanel, BorderLayout.CENTER);
    }
    
    // Set up button event listeners
    private void setupButtonListeners() {
        personalProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dashboard != null) {
                    dashboard.getDashboardPanel().switchToPersonalProfile();
                }
            }
        });

        classInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dashboard != null) {
                    dashboard.getDashboardPanel().switchToClassInformation();
                }
            }
        });

        studentInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dashboard != null) {
                    dashboard.getDashboardPanel().switchToStudentInformation();
                }
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logout functionality here
                int result = JOptionPane.showConfirmDialog(
                    null, 
                    "Are you sure you want to logout?", 
                    "Logout Confirmation", 
                    JOptionPane.YES_NO_OPTION
                );
                if (result == JOptionPane.YES_OPTION) {
                    Login login = new Login();
                    login.setVisible(true);
                    dashboard.dispose();
                }
            }
        });
    }

    // Method to set dashboard reference
    public void setDashboard(atctdgui dashboard) {
        this.dashboard = dashboard;
    }
    
    // Getter methods for buttons (if needed for styling)
    public JButton getPersonalProfileButton() {
        return personalProfileButton;
    }
    
    public JButton getClassInformationButton() {
        return classInformationButton;
    }
    
    public JButton getStudentInformationButton() {
        return studentInformationButton;
    }
    
    public JButton getLogoutButton() {
        return logoutButton;
    }
}
/// ORIGINAL INCASE
///
//////*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package atwTutorGUI;
//
//
//import atwTutorGUI.atctcigui;
//import atwTutorGUI.atctppgui;
//import atwTutorGUI.atctsigui;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// *
// * @author jeatr
// */
//
//public class atctmpgui extends JPanel {
//    
//    // Main components
//    private JPanel tutorMainTitle;
//    private JLabel tutorTitle;
//    private JPanel headerPanel;
//    private JLabel tutorPageTitle;
//    private JButton logoutButton;
//    private JPanel selectionPanel;
//    private JButton personalProfileButton;
//    private JButton classInformationButton;
//    private JButton studentInformationButton;
//    
////    // Panel instances
////    private atctppgui personalProfilePanel;
////    private atctcigui classInformationPanel;
////    private atctsigui studentInformationPanel;
//    private atctdgui dashboard;
//    
//    public atctmpgui() {
//        initComponents();
//        setupLayout();
//        setupButtonListeners();
//    }
//    
//    private void initComponents() {
//        // Initialize main title panel
//        tutorMainTitle = new JPanel();
//        tutorTitle = new JLabel("Welcome to Advance Tuition Center (ATC)");
//        tutorTitle.setFont(new Font("Segoe UI Historic", Font.BOLD, 36));
//        
//        // Initialize header panel
//        headerPanel = new JPanel();
//        headerPanel.setLayout(new GridLayout(2, 1, 0, 10));
//        tutorPageTitle = new JLabel("  Tutor Page");
//        logoutButton = new JButton("Logout");
//        
//        // Initialize selection panel
//        selectionPanel = new JPanel();
//        selectionPanel.setLayout(new GridLayout(3, 1, 0, 20));
//        
//        personalProfileButton = new JButton("Personal Profile");
//        personalProfileButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        
//        classInformationButton = new JButton("Class Information");
//        classInformationButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        
//        studentInformationButton = new JButton("Student Information");
//        studentInformationButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        
//        
//    }
//    private void setupLayout() {
//        setLayout(null);
//        
//        // Setup main title panel
//        tutorMainTitle.setLayout(null);
//        tutorMainTitle.add(tutorTitle);
//        tutorTitle.setBounds(10, 10, 600, 50);
//        
//        // Setup header panel
//        headerPanel.add(tutorPageTitle);
//        headerPanel.add(logoutButton);
//        tutorMainTitle.add(headerPanel);
//        headerPanel.setBounds(830, 10, 100, 60);
//        
//        // Add main title to panel
//        add(tutorMainTitle);
//        tutorMainTitle.setBounds(6, 6, 918, 77);
//        
//        // Setup selection panel
//        selectionPanel.add(personalProfileButton);
//        selectionPanel.add(classInformationButton);
//        selectionPanel.add(studentInformationButton);
//        
//        // Add selection panel to panel
//        add(selectionPanel);
//        selectionPanel.setBounds(10, 150, 160, 190);
//    }
//    
//    // Set up button event listeners
//    private void setupButtonListeners() {
//        personalProfileButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (dashboard != null) {
//                    dashboard.getDashboardPanel().switchToPersonalProfile();
//                }
//            }
//        });
//
//        classInformationButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                atctcigui classPanel = new atctcigui();
//                classPanel.loadTutorClassInformationFromFile(); //Load data here!
//
//                JFrame frame = new JFrame("Class Information");
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.setSize(800, 600);
//                frame.setLocationRelativeTo(null);
//                frame.setContentPane(classPanel);
//                frame.setVisible(true);
//            }
//
//        });
//
//        studentInformationButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (dashboard != null) {
//                    dashboard.getDashboardPanel().switchToStudentInformation();
//                }
//            }
//        });
//    }
//
//    // Method to set dashboard reference
//    public void setDashboard(atctdgui dashboard) {
//        this.dashboard = dashboard;
//    }
//    
//    // Getter methods for buttons (if needed for styling)
//    public JButton getPersonalProfileButton() {
//        return personalProfileButton;
//    }
//    
//    public JButton getClassInformationButton() {
//        return classInformationButton;
//    }
//    
//    public JButton getStudentInformationButton() {
//        return studentInformationButton;
//    }
//}