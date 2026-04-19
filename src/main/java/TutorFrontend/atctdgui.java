package TutorFrontend;

import TutorFrontend.atctcigui;
import TutorFrontend.atctppgui;
import TutorFrontend.atctsigui;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author jeatr
 */

public class atctdgui extends JFrame {
    
    private String userID;
    private String form;
    
    // UI Components
    private JPanel mainTitlePanel;
    private JLabel welcomeLabel;
    private JLabel userNameLabel;
    private atctmpgui mainPagePanel;
    private DashboardPanel dashboardPanel;

    public atctdgui(String userID) {
        this.userID = userID;
        this.form = form;
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        setTitle("ATW Tutor Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Initialize main title panel
        mainTitlePanel = new JPanel();
        mainTitlePanel.setLayout(null);
        
        welcomeLabel = new JLabel("Welcome to ATC");
        welcomeLabel.setFont(new Font("Segoe UI Historic", Font.BOLD, 36));
        
        userNameLabel = new JLabel("User: " + userID);
        userNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        // Initialize main page panel (switching panel)
        mainPagePanel = new atctmpgui();
        mainPagePanel.setDashboard(this);
        
        // Initialize dashboard panel
        dashboardPanel = new DashboardPanel(userID);
    }
    
    private void setupLayout() {
        setSize(1000, 700);
        
        // Setup main title panel
        mainTitlePanel.add(welcomeLabel);
        welcomeLabel.setBounds(10, 10, 400, 50);
        
        mainTitlePanel.add(userNameLabel);
        userNameLabel.setBounds(420, 20, 200, 30);
        
        add(mainTitlePanel);
        mainTitlePanel.setBounds(6, 6, 918, 77);
        
        // Add main page panel (switching buttons)
        add(mainPagePanel);
        mainPagePanel.setBounds(10, 90, 160, 190);
        
        // Add dashboard panel
        add(dashboardPanel);
        dashboardPanel.setBounds(180, 90, 744, 500);
        
        setLocationRelativeTo(null);
    }
    
    public DashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }

    // Inner class for the dashboard panel
    public class DashboardPanel extends JPanel {

        private String userID;

        // Remove JTabbedPane, use CardLayout instead
        private CardLayout cardLayout;
        private atctppgui personalProfilePanel;
        private atctcigui classInformationPanel;
        private atctsigui studentInformationPanel;

        // Panel names for CardLayout
        private static final String PERSONAL_PROFILE_PANEL = "personalProfile";
        private static final String CLASS_INFORMATION_PANEL = "classInformation";
        private static final String STUDENT_INFORMATION_PANEL = "studentInformation";

        public DashboardPanel(String userID) {
            this.userID = userID;
            initializeDashboard();
        }

        private void initializeDashboard() {
            // Use CardLayout instead of BorderLayout with JTabbedPane
            cardLayout = new CardLayout();
            setLayout(cardLayout);

            // Create panel instances
            personalProfilePanel = new atctppgui(userID);
            classInformationPanel = new atctcigui(userID);
            studentInformationPanel = new atctsigui(userID);

            // Add panels to CardLayout
            add(personalProfilePanel, PERSONAL_PROFILE_PANEL);
            add(classInformationPanel, CLASS_INFORMATION_PANEL);
            add(studentInformationPanel, STUDENT_INFORMATION_PANEL);

            // Set default panel to Personal Profile
            cardLayout.show(this, PERSONAL_PROFILE_PANEL);
        }

        // Methods to switch panels (called by left side buttons)
        public void switchToPersonalProfile() {
            cardLayout.show(this, PERSONAL_PROFILE_PANEL);
            highlightSelectedButton("personalProfile");
        }

        public void switchToClassInformation() {
            cardLayout.show(this, CLASS_INFORMATION_PANEL);
            highlightSelectedButton("classInformation");
        }

        public void switchToStudentInformation() {
            cardLayout.show(this, STUDENT_INFORMATION_PANEL);
            highlightSelectedButton("studentInformation");
        }

        // Optional: Method to highlight the selected button
        private void highlightSelectedButton(String selectedButton) {
            System.out.println("Switched to: " + selectedButton);
        }

        // Getter methods for panels (if needed)
        public atctppgui getPersonalProfilePanel() {
            return personalProfilePanel;
        }

        public atctcigui getClassInformationPanel() {
            return classInformationPanel;
        }

        public atctsigui getStudentInformationPanel() {
            return studentInformationPanel;
        }
    }
}

