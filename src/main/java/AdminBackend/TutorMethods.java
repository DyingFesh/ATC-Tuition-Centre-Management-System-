/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import AdminModels.Tutor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class TutorMethods {
    private static final File tutorfile = new File("Text Files/tutors.txt");
    private static final File userfile = new File("Text Files/users.txt");
    private static final Path asgtutorsfile = Paths.get("Text Files/assignedtutors.txt");
    private static final Path tutorfilepath = Paths.get("Text Files/tutors.txt");
    private static final Path userfilepath = Paths.get("Text Files/users.txt");
    private static final Path asgtutorsfilepath = Paths.get("Text Files/assignedtutors.txt");
    
    public static List<Tutor> GetAllTutors() {
        List<Tutor> tutors = new ArrayList<>();
        try {
            FileReader fr = new FileReader(tutorfile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Tutor t = new Tutor(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
                tutors.add(t);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tutors;
    }
    
    public static Tutor GetTutorByID(String targetid) {
        for (Tutor t : GetAllTutors()) {
            if (t.getTutorid().equals(targetid)) {
                return t;
            }
        }
        return null;
    }
    
    public static String GetTutorIDFromName(String name) {
        if (name == null) return null;
        for (Tutor t : TutorMethods.GetAllTutors()) {
          if (t.getTutorname().equals(name.trim())) {
            return t.getTutorid();
          }
        }
        return null;
    }
    
    public static void PopulateTutorList(DefaultTableModel tablemodel) {
        tablemodel.setRowCount(0);

        List<Tutor> tutors = GetAllTutors();

        for (Tutor t : tutors) {
            Object[] row = new Object[] {t.getTutorid(), t.getTutorname(), t.getTutorlevel(), t.getTutorsubject()};
            tablemodel.addRow(row);
        }
    }
    
    public static void DeleteTutor(String tutorid) {
        try {
            List<String> updatedtutor = Files.readAllLines(tutorfilepath).stream().filter(line -> !line.startsWith(tutorid + ",")).collect(Collectors.toList());
            Files.write(tutorfilepath, updatedtutor);
            List<String> updateduser = Files.readAllLines(userfilepath).stream().filter(line -> !line.startsWith(tutorid + ",")).collect(Collectors.toList());
            Files.write(userfilepath, updateduser);
        } catch (IOException e) {
        }
    }
    
    public static String TConvertToOneLine(Tutor t) {
        return String.join(",",
            t.getTutorid(),
            t.getTutorlevel(),
            t.getTutorname(),
            t.getTutorgender(),
            t.getTutoraddress(),
            t.getTutorsubject(),
            t.getTutorph(),
            t.getTutoremail()
        );
    }
    
    public static String GetNewTutorID() {
        String lasttutorid = null;
        String newtutorid = "S000";
        
        try {
            FileReader fr = new FileReader(tutorfile);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                lasttutorid = parts[0];
            }
            
            int lasttutoridnum = Integer.parseInt(lasttutorid.substring(1));
            int newtutoridnum = lasttutoridnum + 1;
            
            if (newtutoridnum > 999) {
                JOptionPane.showMessageDialog(null, "Maximum tutor count reached.");
                newtutorid = null;
            } else {
                newtutorid = String.format("T%03d", newtutoridnum);
            }

            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error generating new tutor ID.");
            ex.printStackTrace();
        }
        return newtutorid;
    }
    
    public static void SaveNewTutor(Tutor t) {
        try {
            List<String> lines = new ArrayList<>();
            lines = Files.readAllLines(tutorfilepath);
            
            String newline = TConvertToOneLine(t);
            boolean updated = false;
            
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(t.getTutorid() + ",")) {
                    lines.set(i, newline);
                    updated = true;
                    break;
                }
            }
            
            if (!updated) {
                lines.add(newline);
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tutorfile, false))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean ValidateTutor(String tutorid, String name, String ph, String email) {
        for (Tutor t : GetAllTutors()) {
            if (t.getTutorid().equals(tutorid)) {
                continue;
            } else if (t.getTutorname().equals(name) || t.getTutorph().equals(ph) || t.getTutoremail().equals(email)) {
                JOptionPane.showMessageDialog(null, "Error: Tutor already exists.");
                return false;
            }
        }
        return true;
    }
    
}
