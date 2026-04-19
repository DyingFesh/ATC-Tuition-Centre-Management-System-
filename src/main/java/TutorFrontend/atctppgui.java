package TutorFrontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import TutorBackend.atctpp;

/**
 *
 * @author jeatr
 */

public class atctppgui extends JPanel implements ActionListener {
    
    // Components
    private JLabel titleLabel;
    
    // Username section
    private JPanel usernamePanel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    
    // Password section
    private JPanel passwordPanel;
    private JLabel passwordLabel;
    private JTextField passwordField;
    
    // Form section
    private JPanel formPanel;
    private JLabel formLabel;
    private JTextField formField;
    
    // Name section
    private JPanel tutorNamePanel;
    private JLabel tutorNameLabel;
    private JTextField tutorNameField;
    
    // Gender section
    private JPanel genderPanel;
    private JLabel genderLabel;
    private JComboBox<String> genderComboBox;
    
    // Address section
    private JPanel addressPanel;
    private JLabel addressLabel;
    private JTextField addressField;
    
    // Gmail section
    private JPanel gmailPanel;
    private JLabel gmailLabel;
    private JTextField gmailField;
    
    // Phone Number section
    private JPanel phoneNumberPanel;
    private JLabel phoneNumberLabel;
    private JTextField phoneNumberField;
    
    // Update button
    private JButton updateButton;
    
    private final String userID; //userID string
    private String form; //form string
    
    public atctppgui(String userID) {
        this.userID = userID;
        initComponents();
        loadProfileData();
        setupLayout();
    }
  
    private void initComponents() {
        // Title
        titleLabel = new JLabel("PERSONAL PROFILE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        // Username section
        usernamePanel = new JPanel();
        usernamePanel.setLayout(new BorderLayout(0, 5));
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40));
        usernameField.setMinimumSize(new Dimension(200, 40));
        usernameField.setMaximumSize(new Dimension(400, 40));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        
        // Password section
        passwordPanel = new JPanel();
        passwordPanel.setLayout(new BorderLayout(0, 5));
        passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(200, 40));
        passwordField.setMinimumSize(new Dimension(200, 40));
        passwordField.setMaximumSize(new Dimension(400, 40));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // Form section (READ-ONLY)
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout(0, 5));
        formLabel = new JLabel("Form:");
        formField = new JTextField();      
        formField.setEditable(false);
        formField.setBackground(Color.LIGHT_GRAY);
        formField.setBorder(BorderFactory.createLoweredBevelBorder());
        formField.setPreferredSize(new Dimension(200, 40));
        formField.setMinimumSize(new Dimension(200, 40));
        formField.setMaximumSize(new Dimension(400, 40));
        formField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(formLabel, BorderLayout.NORTH);
        formPanel.add(formField, BorderLayout.CENTER);
        
        // Name section
        tutorNamePanel = new JPanel();
        tutorNamePanel.setLayout(new BorderLayout(0, 5));
        tutorNameLabel = new JLabel("Name:");
        tutorNameField = new JTextField();
        tutorNameField.setPreferredSize(new Dimension(200, 40));
        tutorNameField.setMinimumSize(new Dimension(200, 40));
        tutorNameField.setMaximumSize(new Dimension(400, 40));
        tutorNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tutorNamePanel.add(tutorNameLabel, BorderLayout.NORTH);
        tutorNamePanel.add(tutorNameField, BorderLayout.CENTER);
        
        // Gender section
        genderPanel = new JPanel();
        genderPanel.setLayout(new BorderLayout(0, 5));
        genderLabel = new JLabel("Gender:");
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setPreferredSize(new Dimension(200, 40));
        genderComboBox.setMinimumSize(new Dimension(200, 40));
        genderComboBox.setMaximumSize(new Dimension(400, 40));
        genderComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        genderPanel.add(genderLabel, BorderLayout.NORTH);
        genderPanel.add(genderComboBox, BorderLayout.CENTER);
        
        // Address section
        addressPanel = new JPanel();
        addressPanel.setLayout(new BorderLayout(0, 5));
        addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(200, 40));
        addressField.setMinimumSize(new Dimension(200, 40));
        addressField.setMaximumSize(new Dimension(400, 40));
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressPanel.add(addressLabel, BorderLayout.NORTH);
        addressPanel.add(addressField, BorderLayout.CENTER);
        
        // Gmail section
        gmailPanel = new JPanel();
        gmailPanel.setLayout(new BorderLayout(0, 5));
        gmailLabel = new JLabel("Gmail:");
        gmailField = new JTextField();
        gmailField.setPreferredSize(new Dimension(200, 40));
        gmailField.setMinimumSize(new Dimension(200, 40));
        gmailField.setMaximumSize(new Dimension(400, 40));
        gmailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gmailPanel.add(gmailLabel, BorderLayout.NORTH);
        gmailPanel.add(gmailField, BorderLayout.CENTER);
        
        // Phone Number section
        phoneNumberPanel = new JPanel();
        phoneNumberPanel.setLayout(new BorderLayout(0, 5));
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        phoneNumberField.setPreferredSize(new Dimension(200, 40));
        phoneNumberField.setMinimumSize(new Dimension(200, 40));
        phoneNumberField.setMaximumSize(new Dimension(400, 40));
        phoneNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneNumberPanel.add(phoneNumberLabel, BorderLayout.NORTH);
        phoneNumberPanel.add(phoneNumberField, BorderLayout.CENTER);
        
        // Update button
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Form panel with all components
        JPanel mainFormPanel = new JPanel(new GridLayout(4, 2, 50, 50));
        mainFormPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add components to form panel
        mainFormPanel.add(usernamePanel);
        mainFormPanel.add(passwordPanel);
        mainFormPanel.add(formPanel);
        mainFormPanel.add(tutorNamePanel);
        mainFormPanel.add(genderPanel);
        mainFormPanel.add(addressPanel);
        mainFormPanel.add(gmailPanel);
        mainFormPanel.add(phoneNumberPanel);
        
        // Form container with center alignment
        JPanel formContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        formContainer.add(mainFormPanel);
        add(formContainer, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Set preferred size for form panel
        mainFormPanel.setPreferredSize(new Dimension(600, 400));
    }
    
    private void loadProfileData() {
        String[] tutorDetails = atctpp.getTutorProfileById(userID);
        String[] tutorsPersonalDetails = atctpp.getTutorPersonalById(userID);

        if (tutorDetails != null && tutorDetails.length >= 7 && 
            tutorsPersonalDetails != null && tutorsPersonalDetails.length >= 3) {

            usernameField.setText(tutorsPersonalDetails[1]);  // username from users.txt
            passwordField.setText(tutorsPersonalDetails[2]);  // password from users.txt
            formField.setText(tutorDetails[1]);               // form from tutors.txt
            this.form = tutorDetails[1];                      // store form for updates
            tutorNameField.setText(tutorDetails[2]);          // name
            genderComboBox.setSelectedItem(tutorDetails[3]);  // gender
            addressField.setText(tutorDetails[4]);            // address
            gmailField.setText(tutorDetails[7]);              // gmail
            phoneNumberField.setText(tutorDetails[6]);        // phone
            
        } else if (tutorDetails != null) {
            JOptionPane.showMessageDialog(this, 
                "Invalid data format for tutor ID: " + userID + 
                "\nExpected 7 fields in tutors.txt but found " + tutorDetails.length, 
                "Data Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Tutor profile not found for ID: " + userID, 
                "Profile Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Getters for accessing components (for event handling)
    public JTextField getUsernameField(){ 
        return usernameField;
    }
    public JTextField getPasswordField(){
        return passwordField;
    }
    public JTextField getTutorNameField(){
        return tutorNameField; 
    }
    public JComboBox<String> getGenderComboBox(){
        return genderComboBox;
    }
    public JTextField getAddressField(){ 
        return addressField; 
    }
    public JTextField getGmailField(){ 
        return gmailField; 
    }
    public JTextField getPhoneNumberField(){ 
        return phoneNumberField; 
    }
    public JButton getUpdateButton(){ 
        return updateButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            // Get all the values from the fields
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String tutorName = tutorNameField.getText().trim();
            String gender = (String) genderComboBox.getSelectedItem();
            String address = addressField.getText().trim();
            String gmail = gmailField.getText().trim();
            String phone = phoneNumberField.getText().trim();

            // Validation
            if (username.isEmpty() || password.isEmpty() || tutorName.isEmpty()
                || gender.isEmpty() || address.isEmpty() || gmail.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "All fields must be filled out.", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Update logic - form cannot be changed by user
            boolean success = atctpp.updateTutorProfile(
                userID, username, password,
                this.form, tutorName, gender,
                address, gmail, phone
            );

            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Profile updated successfully!", 
                    "Update Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update profile. Please try again.", 
                    "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}