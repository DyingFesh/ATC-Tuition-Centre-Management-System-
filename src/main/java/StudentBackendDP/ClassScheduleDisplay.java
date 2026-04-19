/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;

import StudentBackendFH.ClassInfoSch;
import StudentBackendFH.GetClassSchedule;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ClassScheduleDisplay {
    public DefaultTableModel ClassScheduleDisplay(String studentID) {
        String[] columns = { "Subject", "Day", "Time Start", "Time End", "Room", "Teacher in Charge" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            GetClassSchedule schedule = new GetClassSchedule(); 

            ArrayList<String> teacherNamesAndIDs = schedule.getMatchingTeachers(studentID);

            ArrayList<String> teacherIDs = new ArrayList<>();
            for (String entry : teacherNamesAndIDs) {
                int start = entry.indexOf("(");
                int end = entry.indexOf(")");
                if (start != -1 && end != -1) {
                    teacherIDs.add(entry.substring(start + 1, end));
                }
            }

            ArrayList<ClassInfoSch> classInfoList = schedule.getClassInfoForTeachers(teacherIDs);

            for (ClassInfoSch record : classInfoList) {
                String teacherName = schedule.findTeacherNameByID(record.getTeacherID());

                Object[] row = {
                    record.getSubject(),
                    record.getDay(),
                    record.getStartTime().replace(";", ":"),
                    record.getEndTime().replace(";", ":"),
                    record.getRoom(),
                    teacherName
                };
                model.addRow(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return model; 
    }
}
