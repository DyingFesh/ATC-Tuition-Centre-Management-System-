/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;

/**
 *
 * @author User
 */
// This will get all of the updated student information 
public class UpdatedStudentInformation  {
    private String studentID;
    private String studentName;
    private String studentAge;
    private String studentGender;
    private String studentIdentification;
    private String studentEmail;
    private String studentPH;
    private String studentAddress;
    private String studentLevel;
    private String studentSubjects;   
    private String dateJoined;
    
    public UpdatedStudentInformation(String studentID, String studentName, String studentAge, String studentGender, 
            String studentIdentification, String studentEmail, String studentPH, String studentAddress, String studentLevel,
            String studentSubjects, String dateJoined) {
        
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.studentGender = studentGender;
        this.studentIdentification = studentIdentification;
        this.studentEmail = studentEmail;
        this.studentPH = studentPH;
        this.studentAddress = studentAddress;
        this.studentLevel = studentLevel;
        this.studentSubjects = studentSubjects;
        this.dateJoined = dateJoined;
    }
    
    public String getStudentID() {
        return studentID;
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
    public String joinStudentInformation() {
        return String.join(",", studentID, studentName, studentAge, studentGender, studentIdentification, 
                studentEmail, studentPH, studentAddress, studentLevel, studentSubjects, dateJoined);
    }
}
