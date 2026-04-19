/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import StudentBackendDP.UpdatedStudentInformation;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author User
 */
public class StudentInformationUpdated {
    private static final String student_file = "Text Files/students.txt";
    
    public static void updateStudentFile(UpdatedStudentInformation updatedStudent){
        File f = new File(student_file);
        ArrayList<String> updatedLines = new ArrayList<>();

    try (Scanner reader = new Scanner(f)) {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] parts = line.split(",");

            if (parts[0].equalsIgnoreCase(updatedStudent.getStudentID())) {
                updatedLines.add(updatedStudent.joinStudentInformation()); // replace line
            } else {
                updatedLines.add(line); // keep existing line
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(student_file))) {
        for (String line : updatedLines) {
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

