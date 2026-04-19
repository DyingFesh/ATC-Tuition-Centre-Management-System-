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
public class GetClassSchedule {
    private static final String tutor_file = "Text Files/tutors.txt";
    private static final String classSchedule_file = "Text Files/TutorClassInformation.txt";

    public ArrayList<String> getMatchingTeachers(String studentID) throws IOException {
        GetStudentInformation studentInfo = new GetStudentInformation(studentID);
        String studentLevel = studentInfo.getStudentLevel(); 
        ArrayList<String> studentSubjects = studentInfo.getSubject();
        
        ArrayList<String> matchingTeachers = new ArrayList<>();
        ArrayList<SubjectTeacherPair> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(tutor_file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            if (parts.length >= 6) {
                String teacherID = parts[0];
                String teacherLevel = parts[1];
                String teacherName = parts[2]; 
                String teacherSubject = parts[5].trim();

                if (teacherLevel.equalsIgnoreCase(studentLevel)) {
                    for (String subject : studentSubjects) {
                        if (teacherSubject.equalsIgnoreCase(subject.trim())) {
                            matchingTeachers.add(teacherName + " (" + teacherID + ")");                                                    
                        }
                    }
                }
            }
        }
        return matchingTeachers;
    }
    
    public ArrayList<ClassInfoSch> getClassInfoForTeachers(ArrayList<String> teacherIDs) throws IOException {
        ArrayList<ClassInfoSch> classInfoList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(classSchedule_file));
        String line;

        while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        if (teacherIDs.contains(parts[0].trim())) {
            ClassInfoSch info = new ClassInfoSch(
                parts[0].trim(),
                parts[1].trim(), 
                parts[2].trim(), 
                parts[3].trim(), 
                parts[4].trim(), 
                parts[5].trim()  
            );
            classInfoList.add(info);
        }
    }
    return classInfoList;
    }
    
    public String findTeacherNameByID(String teacherID) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(tutor_file));
    String line;

    while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 3 && parts[0].trim().equals(teacherID)) {
            reader.close();
            return parts[2].trim(); 
        }
    }
    return teacherID;
    }
    
    public static class SubjectTeacherPair {
    private String subject;
    private String teacher;

    public SubjectTeacherPair(String subject, String teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }
    }

    public ArrayList<SubjectTeacherPair> getSubjectTeacherPairs(String studentID) throws IOException {
        GetStudentInformation studentInfo = new GetStudentInformation(studentID);
        String studentLevel = studentInfo.getStudentLevel(); 
        ArrayList<String> studentSubjects = studentInfo.getSubject();

        ArrayList<SubjectTeacherPair> result = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(tutor_file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String teacherID = parts[0];
                String teacherLevel = parts[1];
                String teacherName = parts[2];
                String teacherSubject = parts[5].trim();

                if (teacherLevel.equalsIgnoreCase(studentLevel)) {
                    for (String subject : studentSubjects) {
                        if (teacherSubject.equalsIgnoreCase(subject.trim())) {
                            result.add(new SubjectTeacherPair(subject, teacherName));
                        }
                    }
                }
            }
        }
        return result;
    }  
}