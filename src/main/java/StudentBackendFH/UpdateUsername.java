/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class UpdateUsername {
    private String StudentID;
    private String newUsername;
    private String password;
    private static final String user = "Text Files/users.txt";
    
    public UpdateUsername(String StudentID, String newUsername, String password) {
        this.StudentID = StudentID;
        this.newUsername = newUsername;
        this.password = password;       
    }   
    
    public String JoinUser() {
        return String.join(",", StudentID, newUsername, password);
    }
    
    public void updateUserFile() throws FileNotFoundException{
        ArrayList<String> updatedLines = new ArrayList<>();

    try (Scanner reader = new Scanner(new File(user))) {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(",");

            if (parts[0].equalsIgnoreCase(StudentID)) {
                updatedLines.add(JoinUser()); // replace line
            } else {
                updatedLines.add(line); // keep existing line
            }
        }
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(user))) {
        for (String line : updatedLines) {
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error writing file: " + e.getMessage());
        }   
    }  
 }

