/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;


import StudentBackendFH.GetStudentInformation;
import java.io.*;
import java.util.*;
import StudentBackendDP.FindSubjectTeacher;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class SubjectEnrollementTableDisplay {    
    public DefaultTableModel SubjectEnrollementTableDisplay(String studentID) {
        String[] columns = {"Level", "Subject", "Teacher In Charge", "Status", "Date Enrolled"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String subjectEnrolment = "Text Files/SubjectEnrolmentRequest.txt";
            
        try {
            GetStudentInformation studentInfo = new GetStudentInformation(studentID);
            String studentLevel = studentInfo.getStudentLevel();
            ArrayList<String> currentSubjects = studentInfo.getSubject();

            BufferedReader reader = new BufferedReader(new FileReader(subjectEnrolment));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts[3].equalsIgnoreCase("accepted") && parts[0].equalsIgnoreCase(studentID)) {
                    String subject = parts[1].trim();
                    String status = parts[3];
                    String dateEnrolled = parts[5];
                    
                    String teacher = FindSubjectTeacher.findSubjectTeacher(studentLevel, subject);

                    model.addRow(new Object[]{studentLevel, subject, teacher, status, dateEnrolled});
                }
            }
            
            for (String subject : currentSubjects) {
            String teacher = FindSubjectTeacher.findSubjectTeacher(studentLevel, subject);
            // Check if this subject is already added from enrollment requests to avoid duplicates
            boolean alreadyExists = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 1).toString().equalsIgnoreCase(subject)) {
                    alreadyExists = true;
                    break;
                }
            }
            
            String enrolledStatus = "Enrolled";
            String dateEnrolled = studentInfo.getDateJoined();
            
            if (!alreadyExists) {
                model.addRow(new Object[]{studentLevel, subject, teacher, enrolledStatus, dateEnrolled});
            }
        }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }
}        
