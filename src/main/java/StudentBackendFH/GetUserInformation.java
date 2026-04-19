 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author User
 */
public class GetUserInformation {
    private String fileStudentID;
    private String studentUsername;
    private String studentPassword;
    
    private static final String user_file = "Text Files/users.txt";
    
    public GetUserInformation(String StudentID){
        loadStudentUserInformation(StudentID);
    }
    
    public void loadStudentUserInformation (String StudentID){
        try(BufferedReader br = new BufferedReader(new FileReader(user_file));){
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
               if (parts[0].equalsIgnoreCase(StudentID)){
                    this.fileStudentID = parts[0].trim();
                    this.studentUsername = parts[1].trim();
                    this.studentPassword = parts[2];
        }
               
            }
    }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getStudentID(){
        return fileStudentID;
    }
    
    public String getStudentUsername(){
        return studentUsername;
    }
    
    public String getStudentPassword(){
        return studentPassword;
    }
    
    public static boolean isUsernameTaken(String newUsername) {
        try (BufferedReader br = new BufferedReader(new FileReader(user_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String existingUsername = parts[1].trim();
                    if (existingUsername.equalsIgnoreCase(newUsername)) {
                        return true; // Username already exists
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Username is not taken
    }
}
