/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;

import java.io.*; 
import java.util.*;
/**
 *
 * @author User
 */
// this method is to get that specific student's information
public class GetStudentInformation {
    private String fileStudentID;
    private String studentName;
    private String studentIdentification;
    private String studentAge;
    private String studentGender;
    private String studentEmail;
    private String studentPH;
    private String studentAddress;
    private String level;
    private ArrayList<String> subject;
    private String dateJoined;
    
    // Getters
    public String getStudentID(){
        return fileStudentID;
    }
    public String getStudentName (){
        return studentName;
    }
    public String getStudentIdentification () {
        return studentIdentification;
    }
    public String getStudentAge (){
        return studentAge;
    }
    public String getStudentGender () {
        return studentGender;
    }
    public String getStudentEmail() {
        return studentEmail;
    }
    public String getStudentPH() {
        return studentPH;
    }
    public String getStudentAddress() {
        return studentAddress;
    }
    public String getStudentLevel() {
        return level;
    }
    public ArrayList<String> getSubject() {
        return this.subject;        
    }
    public String getDateJoined() {
        return dateJoined;
    }
    
    // The file path for student Txt
    private static final String student_file = "Text Files/students.txt";
    
    //this method is an empty constructor 
    public GetStudentInformation(String studentID) {
        searchStudent(studentID);
    }
    
    // This searches for the student's information
    public final boolean searchStudent(String studentID) {
        try (BufferedReader br = new BufferedReader(new FileReader(student_file));){  
            String line;
            while ((line = br.readLine()) != null) {             
                String[] parts = line.split(",");
                
                if (parts[0].equalsIgnoreCase(studentID)){
                    this.fileStudentID = parts[0];
                    this.studentName = parts[1];
                    this.studentAge = parts[2];
                    this.studentGender = parts[3];
                    this.studentIdentification = parts[4];                    
                    this.studentPH = parts[5];
                    this.studentEmail = parts[6];
                    this.studentAddress = parts[7];
                    this.level = parts[8];     
                    
                    this.subject = new ArrayList<>();
                    String[] subjectArray = parts[9].split(";");
                    for (String s : subjectArray){
                        subject.add(s.trim());
                    }
                    
                    this.dateJoined = parts[10];
                    return true;                 
                }
            
            }
        }catch (IOException e){
            System.out.println("Please ensure Student file exsist.");
        }
        return false;
    }
    
   
}
