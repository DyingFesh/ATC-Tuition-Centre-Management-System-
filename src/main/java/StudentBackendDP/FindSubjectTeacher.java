/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;

import java.io.*;

/**
 *
 * @author User
 */

public class FindSubjectTeacher {
    private static final String tutor_file = "Text Files/tutors.txt";
    
    public static String findSubjectTeacher(String studentLevel, String subject) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(tutor_file));
        String line;
        
        while((line = reader.readLine()) != null){
            String[] parts = line.split(",");
            
            String teacherLevel = parts[1].trim();
            String teacherName = parts[2].trim();
            String teacherSubject = parts[5].trim();
            
            if (teacherLevel.equalsIgnoreCase(studentLevel) && 
                    teacherSubject.equalsIgnoreCase(subject)) {                   
                    return teacherName;
            }
        }
        return "-";
    }
    
    public static String findTeacherNameByID(String teacherID) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(tutor_file));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].trim().equals(teacherID)) {
                return parts[2].trim(); 
            }
        }
        return teacherID;
    }
}
