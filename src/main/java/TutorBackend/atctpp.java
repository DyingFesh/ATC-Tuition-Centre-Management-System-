package TutorBackend;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jeatr
 */

public class atctpp {
    private static final String USERS_FILE = "Text Files/users.txt";
    private static final String TUTORS_FILE = "Text Files/tutors.txt";

    public static String[] getTutorLoginById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text Files/tutors.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(id)) {
                    return parts; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String[] getTutorPersonalById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Text Files/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(id)) {
                    return parts; // Return full row for that tutor ID
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // User class to represent user data
    public static class User {
        public String userID;
        public String username;
        public String password;
        
        public User(String userID, String username, String password) {
            this.userID = userID;
            this.username = username;
            this.password = password;
        }
    }
    
    // Tutor class to represent tutor data
    public static class Tutor {
        public String tutorID;
        public String form;
        public String tutorName;
        public String gender;
        public String address;
        public String gmail;
        public String phoneNumber;
        
        public Tutor(String tutorID, String form, String tutorName, 
                    String gender, String address, String gmail, 
                    String phoneNumber) {
            this.tutorID = tutorID;
            this.form = form;
            this.tutorName = tutorName;
            this.gender = gender;
            this.address = address;
            this.gmail = gmail;
            this.phoneNumber = phoneNumber;
        }
    }
    
    /**
     * Validates and converts phone number string to integer
     * @param phoneString The phone number string to validate and convert
     * @return Integer value if valid, -1 if invalid
     */
    public static int validatePhoneNumber(String phoneString) {
        if (phoneString == null || phoneString.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Phone number cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        try {
            int phoneNumber = Integer.parseInt(phoneString.trim());
            if (phoneNumber <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "Phone number must be a positive number!", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            return phoneNumber;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Invalid phone number format! Please enter numbers only (e.g., 1234567890).", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    
    /**
     * Authenticates user login credentials
     * @param username The username to check
     * @param password The password to check
     * @return User object if login successful, null otherwise
     */
    public static User authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String userID = parts[0].trim();
                    String fileUsername = parts[1].trim();
                    String filePassword = parts[2].trim();
                    
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return new User(userID, fileUsername, filePassword);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error reading users file: " + e.getMessage(), 
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Loads tutor profile data based on userID
     * @param userID The user ID to match with tutorID
     * @return Tutor object if found, null otherwise
     */
    public static Tutor loadTutorProfile(String userID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TUTORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String tutorID = parts[0].trim();
                    
                    // Match userID with tutorID
                    if (tutorID.equals(userID)) {
                        return new Tutor(
                            tutorID,
                            parts[1].trim(), // form
                            parts[2].trim(), // name
                            parts[3].trim(), // gender
                            parts[4].trim(), // address
                            parts[7].trim(), // gmail
                            parts[6].trim()  // phoneNumber
                        );
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error reading tutors file: " + e.getMessage(), 
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    /**
     * Validates that all required fields are not empty
     * @param username Username field
     * @param password Password field
     * @param tutorName tutorName field
     * @param gender Gender field
     * @param address Address field
     * @param gmail Gmail field
     * @param phoneNumber Phone field
     * @return true if all fields are valid, false otherwise
     */
    public static boolean validateFields(String username, String password, 
                                        String tutorName, 
                                        String gender, String address, 
                                        String gmail, String phoneNumber) {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tutorName == null || tutorName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gender == null || gender.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gender cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (address == null || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gmail == null || gmail.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Gmail cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone number cannot be empty!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Updates both users.txt and tutors.txt with new information
     * @param userID The user ID to update
     * @param newUsername New username
     * @param newPassword New password
     * @param newForm New form
     * @param newtutorName New tutor name
     * @param newGender New gender
     * @param newAddress New address
     * @param newGmail New gmail
     * @param newPhoneNumber New phone number
     * @return true if update successful, false otherwise
     */
    public static boolean updateTutorProfile(String userID, String newUsername, 
                                           String newPassword, String newForm, 
                                           String newtutorName, String newGender, 
                                           String newAddress, String newGmail, 
                                           String newPhoneNumber) {
        
        // First validate all fields
        if (!validateFields(newUsername, newPassword, 
                          newtutorName, newGender, newAddress, newGmail, newPhoneNumber)) {
            return false;
        }
        
        // Update users.txt
        if (!updateUsersFile(userID, newUsername, newPassword)) {
            return false;
        }
        
        // Update tutors.txt
        if (!updateTutorsFile(userID, newForm, newtutorName, newGender, 
                            newAddress, newGmail, newPhoneNumber)) {
            return false;
        }
        
        return true;
    }
    
    //update for user information exp: username & password
    private static boolean updateUsersFile(String userID, String newUsername, String newPassword) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].trim().equals(userID)) {
                    // Update this line
                    lines.add(userID + "," + newUsername + "," + newPassword);
                    found = true;
                } else {
                    // Keep original line
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error reading users file: " + e.getMessage(), //fail to get user file
                "File Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(null, 
                "User ID not found in users file!",  //fail to get userID
                "Update Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Write updated content back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error writing to users file: " + e.getMessage(),  //error to rewrite user file
                "File Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    //update for tutor information exp: tutorID, Form, Name, Gender, Address, Gmail, Phone Number
    private static boolean updateTutorsFile(String tutorID, String newForm, 
                                          String newtutorName, String newGender, 
                                          String newAddress, String newGmail, 
                                          String newPhoneNumber) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(TUTORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[0].trim().equals(tutorID)) {
                    // Update this line
                    lines.add(tutorID + "," + newForm + "," + newtutorName + "," + 
                             newGender + "," + newAddress + "," + newGmail + "," + newPhoneNumber);
                    found = true;
                } else {
                    // Keep original line
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error reading tutors file: " + e.getMessage(), 
                "File Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(null, 
                "Tutor ID not found in tutors file!", 
                "Update Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Write updated content back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(TUTORS_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error writing to tutors file: " + e.getMessage(), 
                "File Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public static String[] getTutorProfileById(String id) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Text Files/tutors.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(id)) {
                    reader.close();
                    return parts;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}