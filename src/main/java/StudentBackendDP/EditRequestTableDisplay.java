/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;

import StudentBackendFH.GetStudentInformation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class EditRequestTableDisplay {
    public DefaultTableModel EditRequestTableDisplay(String studentID) {
        String[] columns = {"Level", "Subject", "Teacher In Charge", "Status", "Date Request"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String subjectEnrolment_file = "Text Files/SubjectEnrolmentRequest.txt";

        try {
            GetStudentInformation studentInfo = new GetStudentInformation(studentID);
            String studentLevel = studentInfo.getStudentLevel();

            BufferedReader reader = new BufferedReader(new FileReader(subjectEnrolment_file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts[0].equalsIgnoreCase(studentID)) {
                    String subject = parts[1].trim();
                    String status = parts[3];
                    String dateEnrolled = parts[4];

                    String teacher = FindSubjectTeacher.findSubjectTeacher(studentLevel, subject);

                    model.addRow(new Object[]{studentLevel, subject, teacher, status, dateEnrolled});
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }
}        
