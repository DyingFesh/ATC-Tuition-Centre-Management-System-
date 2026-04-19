/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import AdminModels.Payment;
import AdminModels.PaymentMade;
import AdminModels.Student;
import AdminModels.Tutor;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class StudentMethods {
    private static final File studentfile = new File("Text Files/students.txt");
    private static final File userfile = new File("Text Files/users.txt");
    private static final File asgtutorsfile = new File("Text Files/assignedtutors.txt");
    private static final File tutorsfile = new File("Text Files/tutors.txt");
    private static final File subjectenrollmentfile = new File("Text Files/TutorStudentInformation.txt");
    
    private static final Path studentfilepath = Paths.get("Text Files/students.txt");
    private static final Path userfilepath = Paths.get("Text Files/users.txt");
    private static final Path asgtutorsfilepath = Paths.get("Text Files/assignedtutors.txt");
    private static final Path tutorsfilepath = Paths.get("Text Files/tutors.txt");
    private static final Path subjectenrollmentfilepath = Paths.get("Text Files/TutorStudentInformation.txt");
    
    DefaultTableModel studenttablemodel;
    
    DefaultListModel<String> listmodel;
    String studentid;
    
    public static List<Student> GetAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            FileReader fr = new FileReader(studentfile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Student s = new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10]);
                students.add(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return students;
    }
    
    public static Student GetStudentByID(String targetid) {
        for (Student s : GetAllStudents()) {
            if (s.getStudentid().equals(targetid)) {
                return s;
            }
        }
        return null;
    }
    
    public static List<String> GetAssignedTutors(String studentid) {
        List<String> asgtutors = new ArrayList<>();
        String engtutorid = null;
        String mathstutorid = null;
        String scitutorid = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(asgtutorsfile.toString()))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String stuid = parts[0];


                if (stuid.equals(studentid)) {
                    engtutorid = parts[1];
                    mathstutorid = parts[2];
                    scitutorid = parts[3];
                }
            }
            asgtutors.add(engtutorid);
            asgtutors.add(mathstutorid);
            asgtutors.add(scitutorid);
        } catch (IOException e) {
        }
        return asgtutors;
    }
    
    public static void PopulateStudentList(DefaultTableModel tablemodel) {
        tablemodel.setRowCount(0);

        List<Student> students = GetAllStudents();

        for (Student s : students) {
            Object[] row = new Object[] {s.getStudentid(), s.getStudentname(), s.getStudentlevel()};
            tablemodel.addRow(row);
        }
    }
    
    public static void PopulateSubjectList(DefaultTableModel tablemodel, String studentid, String subjects) throws IOException {
        tablemodel.setRowCount(0);

        String[] splitsubjects = subjects.split(";");
        String tutorid = null;
        String tutorname = null;
        String tutornameprefix = null;

        for (String subject : splitsubjects) {
            try (BufferedReader br = new BufferedReader(new FileReader(asgtutorsfile.toString()))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String stuid = parts[0];
                    String engtutorid = parts[1];
                    String mathstutorid = parts[2];
                    String scitutorid = parts[3];
                    
                    if (stuid.equals(studentid)) {
                        if (subject.trim().equals("English")) {
                            tutorid = engtutorid;
                        } else if (subject.trim().equals("Mathematics")) {
                            tutorid = mathstutorid;
                        } else if (subject.trim().equals("Science")) {
                            tutorid = scitutorid;
                        } else {
                            tutorid = null;
                        }
                    }
                    
                }
            } catch (IOException e) {
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(tutorsfile.toString()))) {
                String line;
                
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    String tutid = parts[0];
                    String tutname = parts[2];
                    String tutgender = parts[3];
                            
                    if (tutgender.equals("Male")) tutornameprefix = "Mr. ";
                    if (tutgender.equals("Female")) tutornameprefix = "Ms. ";
                    
                    if (tutid.equals(tutorid)) {
                        tutorname = tutornameprefix + tutname;
                    }
                    
                }
            } catch (IOException e) {
            }
            
            Object[] row = new Object[] {subject, tutorid, tutorname};
            tablemodel.addRow(row);
        }
    }
    
    public static void PopulateTutorList(DefaultComboBoxModel combomodel, String level, String subject) {
        combomodel.removeAllElements();
        
        List<Tutor> tutors = TutorMethods.GetAllTutors();
        
        for (Tutor t : tutors) {
            if (t.getTutorlevel().equals(level) && t.getTutorsubject().equals(subject)) {
                combomodel.addElement(t.getTutorname());
            }
        }
    }
    
    public static void PopulateStudentPaymentHistory(DefaultTableModel tablemodel, String studentid) {
        tablemodel.setRowCount(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        
        List<PaymentMade> paymentsmade = PaymentMethods.GetAllPaymentsMade();
        paymentsmade.sort(Comparator.comparing(pm ->
            LocalDate.parse(pm.getPmdate(), formatter)
        ));

        for (PaymentMade pm : paymentsmade) {
            if (!pm.getStudentid().equals(studentid)) continue;

            LocalDate pdate = LocalDate.parse(pm.getPmdate(), formatter);
            String year = Integer.toString(pdate.getYear());

            String paymentdate = pm.getPmdate();

            String plan = pm.getPmplan();
            String[] pids = pm.getPmfor().split(";");

            List<String> months = new ArrayList<>();
            List<String> subjects = new ArrayList<>();
            
            for (String pid : pids) {
                pid = pid.trim();
                if (pid.isEmpty()) continue;
                Payment pay = PaymentMethods.GetPaymentByID(pid);
                if (pay == null) continue;

                months.add(pay.getPaymentmonth());

                String subj = pay.getPaymentsubject();
                if (!subjects.contains(subj)) {
                    subjects.add(subj);
                }
            }

            months.sort(Comparator.comparing(m -> Month.valueOf(m.toUpperCase())));
            String paymentmonth;
            if (months.isEmpty()) {
                paymentmonth = "";
            } else if (months.size() == 1) {
                paymentmonth = months.get(0);
            } else {
                paymentmonth = months.get(0) + " – " + months.get(months.size() - 1);
            }

            String paymentsubjects = String.join(", ", subjects);

            Object[] row = new Object[] {year, paymentmonth, paymentsubjects, pm.getPmamt(), plan, pm.getPmmethod(), paymentdate};
            tablemodel.addRow(row);
        }
    }
    
    public static String GetStudentLevel(int studentage) {
        switch(studentage) {
            case 13: return "Form 1";
            case 14: return "Form 2";
            case 15: return "Form 3";
            case 16: return "Form 4";
            case 17: return "Form 5";
            default: return null;
        }
    }
    
    public static String GetNewStudentID() {
        String laststudentid = null;
        String newstudentid = "S000";
        
        try {
            FileReader fr = new FileReader(studentfile);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                laststudentid = parts[0];
            }
            
            int laststudentidnum = Integer.parseInt(laststudentid.substring(1));
            int newstudentidnum = laststudentidnum + 1;
            
            if (newstudentidnum > 999) {
                JOptionPane.showMessageDialog(null, "Maximum student count reached.");
                newstudentid = null;
            } else {
                newstudentid = String.format("S%03d", newstudentidnum);
            }

            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating new student ID.");
            ex.printStackTrace();
        }
        return newstudentid;
    }
    
    public static String SConvertToOneLine(Student s) {
        return String.join(",",
            s.getStudentid(),
            s.getStudentname(),
            String.valueOf(s.getStudentage()),
            s.getStudentgender(),
            s.getStudentic(),
            s.getStudentph(),
            s.getStudentemail(),
            s.getStudentaddress(),
            s.getStudentlevel(),
            s.getStudentsubjects(),
            s.getStudentenrol()
        );
    }

    public static void SaveNewStudent(Student s) {
        try {
            List<String> lines = new ArrayList<>();
            lines = Files.readAllLines(studentfilepath);
            
            String newline = SConvertToOneLine(s);
            boolean updated = false;
            
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(s.getStudentid() + ",")) {
                    lines.set(i, newline);
                    updated = true;
                    break;
                }
            }
            
            if (!updated) {
                lines.add(newline);
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(studentfile, false))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SaveNewAssignedTutors(String studentid, String englishtutorname, String mathstutorname, String sciencetutorname) {
        try {
            List<String> lines = Files.readAllLines(asgtutorsfilepath);
            String engtutorid = null;
            String mathstutorid = null;
            String scitutorid = null;
            
            if (englishtutorname == null) {
              englishtutorname = "null";
            } else {
                engtutorid = TutorMethods.GetTutorIDFromName(englishtutorname);
            }

            if (mathstutorname == null) {
              mathstutorname = "null";
            } else {
                mathstutorid = TutorMethods.GetTutorIDFromName(mathstutorname);
            }

            if (sciencetutorname == null) {
              sciencetutorname = "null";
            } else {
                scitutorid = TutorMethods.GetTutorIDFromName(sciencetutorname);
            }

            String newline = String.join(",", studentid, engtutorid, mathstutorid, scitutorid);

            boolean updated = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(studentid + ",")) {
                    lines.set(i, newline);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                lines.add(newline);
            }

            Files.write(asgtutorsfilepath, lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SaveSubjectEnrollment(Student s, String subject, String tutorid) {
        try {
            List<String> lines = Files.readAllLines(subjectenrollmentfilepath);

            String subjenrdate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            String newline = String.join(",", s.getStudentid(), s.getStudentname(), s.getStudentlevel(), subject, subjenrdate, tutorid);

            boolean updated = false;
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                String studentid = parts[0];
                String studentsubject = parts[3];
                String studenttutor = parts[5];
                
                if (s.getStudentid().equals(studentid) && subject.equals(studentsubject) && tutorid.equals(studenttutor)) {
                    lines.set(i, newline);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                lines.add(newline);
            }

            Files.write(subjectenrollmentfilepath, lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void RemoveSubjectEnrollment(Student s, String subject) {
        try {
            List<String> lines = Files.readAllLines(subjectenrollmentfilepath);

            boolean removed = false;
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                String studentid = parts[0];
                String studentsubject = parts[3];
                String studenttutor = parts[5];
                
                if (s.getStudentid().equals(studentid) && subject.equals(studentsubject)) {
                    lines.remove(i);
                    removed = true;
                    break;
                }
            }

            if (removed) {
                Files.write(subjectenrollmentfilepath, lines);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    public static void DeleteStudent(String studentid) {
        try {
            List<String> updatedstudent = Files.readAllLines(studentfilepath).stream().filter(line -> !line.startsWith(studentid + ",")).collect(Collectors.toList());
            Files.write(studentfilepath, updatedstudent);
            List<String> updateduser = Files.readAllLines(userfilepath).stream().filter(line -> !line.startsWith(studentid + ",")).collect(Collectors.toList());
            Files.write(userfilepath, updateduser);
            List<String> updatedtutors = Files.readAllLines(asgtutorsfilepath).stream().filter(line -> !line.startsWith(studentid + ",")).collect(Collectors.toList());
            Files.write(asgtutorsfilepath, updatedtutors);
            List<String> updatedsubjectenr = Files.readAllLines(subjectenrollmentfilepath).stream().filter(line -> !line.startsWith(studentid + ",")).collect(Collectors.toList());
            Files.write(subjectenrollmentfilepath, updatedsubjectenr);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean ValidateStudent(String studentid, String name, String ic, String ph, String email) {
        for (Student s : GetAllStudents()) {
            if (s.getStudentid().equalsIgnoreCase(studentid.trim())) {
                continue;
            } else if (s.getStudentname().equalsIgnoreCase(name.trim()) || s.getStudentic().equals(ic.trim()) || s.getStudentph().equals(ph.trim()) || s.getStudentemail().equalsIgnoreCase(email.trim())) {
                JOptionPane.showMessageDialog(null, "Error: Student already exists.");
                return false;
            }
        }
        return true;
    }
    
    public static void PopulatePendingRequests(DefaultTableModel tablemodel) {
        tablemodel.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("Text Files/SubjectEnrolmentRequest.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (!"Pending".equalsIgnoreCase(parts[3])) continue;
                    String studentid = parts[0];
                    String studentname = GetStudentByID(studentid).getStudentname();
                    String subject = parts[1];
                    String reason = parts[2];
                    String reqdate = parts[4];

                    tablemodel.addRow(new Object[]{ studentid, studentname, subject, reason, reqdate});
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void PopulateApprovedRequests(DefaultTableModel tablemodel) {
        tablemodel.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("Text Files/SubjectEnrolmentRequest.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if ("Pending".equalsIgnoreCase(parts[3])) continue;
                    String studentid = parts[0];
                    String studentname = GetStudentByID(studentid).getStudentname();
                    String subject = parts[1];
                    String reason = parts[2];
                    String reqdate = parts[4];

                    tablemodel.addRow(new Object[]{ studentid, studentname, subject, reason, reqdate});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ApproveRequest(String studentId) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("Text Files/SubjectEnrolmentRequest.txt"));
        String currentdate = GeneralMethods.GetCurrentDate();

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",", -1);
            if (parts[0].equals(studentId) && parts[3].equalsIgnoreCase("Pending")) {
                parts[3] = "Accepted";
                parts[5] = currentdate;
                lines.set(i, String.join(",", parts));
                break;
            }
        }
        Files.write(Paths.get("Text Files/SubjectEnrolmentRequest.txt"), lines);
    }
    
}
