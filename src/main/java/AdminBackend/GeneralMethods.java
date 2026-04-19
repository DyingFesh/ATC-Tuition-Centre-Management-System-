/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import AdminFrontend.ImagePanel;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author User
 */
public class GeneralMethods {
    
    private static final File userfile = new File("Text Files/users.txt");

    public static String Capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        str = str.trim().replaceAll("\\s+", " ");
        
        String[] temp = str.split(" ");
        List<String> temp2 = new ArrayList<>();
        String output;
        
        for (String parts : temp) {
            if (parts.isEmpty()) continue;
            String first = parts.substring(0,1).toUpperCase();
            String rest  = parts.substring(1).toLowerCase();
            temp2.add(first + rest);
        }
            output = String.join(" ", temp2);
        return output;
    }
    
    public static String GetCurrentMonthYear() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        return date;
    }
    
    public static String GetCurrentDate() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        return date;
    }
    
    public static void LoadPFP(ImagePanel pfppanel, String path) {
        File f = new File(path);
        
        if (path != null && !path.isBlank()) {
            f = new File(path);
        }

        if (!f.exists()) {
            f = new File("Profile Pictures/Default.jpg");
        }
        
        try {
            BufferedImage img = ImageIO.read(f);
            pfppanel.setImage(img);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void ChangePFP(ImagePanel pfppanel, JPanel panel, String id) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(
          new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        if (chooser.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File source = chooser.getSelectedFile();
        String path = "Profile Pictures/" + id + ".jpg";
        File newpfp = new File(path);

        try {
            Files.createDirectories(newpfp.getParentFile().toPath());
            Files.copy(source.toPath(), newpfp.toPath(), StandardCopyOption.REPLACE_EXISTING);

            GeneralMethods.LoadPFP(pfppanel, path);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Failed to update new profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void ResetPFP(JPanel panel, String id) {
        String path = "Profile Pictures/" + id + ".jpg";
        if (path != null && !path.isBlank()) {
            try {
                int choice = JOptionPane.showConfirmDialog(panel, "Confirm profile picture reset?", "Reset Profile Picture", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Path p = Paths.get(path);
                    Files.deleteIfExists(p);
                } else {
                    return;
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(panel, "Unable to reset profile picture.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    public static List<String> GetLoginCreds(String id2) {
        String selectedusername = null;
        String selectedpassword = null;
        List<String> logincreds = new ArrayList<>();
        try {
            FileReader fr = new FileReader(userfile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String username = parts[1];
                String password = parts[2];
                
                if (id.equals(id2)) {
                    logincreds.add(username);
                    logincreds.add(password);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return logincreds;
    }
    
    public static boolean SaveNewLogin(String id, String username, String password) {
        boolean updated = false;
        try {
            List<String> lines = Files.readAllLines(userfile.toPath());

            String newline = id + "," + username + "," + password;

             for (int i = 0; i < lines.size(); i++) {
                 if (lines.get(i).startsWith(id + ",")) {
                    lines.set(i, newline);
                    updated = true;
                     break;
                }
            }

            if (!updated) {
                 lines.add(newline);
             }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userfile, false))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
           }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return updated;
    }
        
    public static void InputValidation(JTextField field, String oldtext, int minlength, int maxlength, String format, String errormsg) {
        String text = field.getText().trim();
        if (text.length() < minlength || text.length() > maxlength || !text.matches(format)) {
            JOptionPane.showMessageDialog(null, errormsg, "Input Error", JOptionPane.ERROR_MESSAGE);
            field.setText(oldtext);
        } else {
            return;
        }
    }

    public static abstract class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        public abstract void update();

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            update();
        }
    }
    
    public static DocumentListener MakeSimpleDocumentListener(Runnable r) {
        return new SimpleDocumentListener() {
            @Override
            public void update() {
                r.run();
            }
        };
    }
    
    public static String GetGreeting() {
        int hour = LocalTime.now().getHour();
        if (hour < 12) {
            return "Good morning";
        } else if (hour < 17) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }
    
    public static boolean ValidateUsername(String id, String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(userfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String fileid = parts[0];
                String fileusername = parts[1];

                if (fileid.equalsIgnoreCase(id.trim())) {
                    continue;
                }
                
                if (fileusername.equalsIgnoreCase(username.trim())) {
                    JOptionPane.showMessageDialog(null, "Error: Username already exists.");
                    return false;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
}
