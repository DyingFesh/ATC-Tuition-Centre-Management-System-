package TutorFrontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class atctsigui extends JPanel {
    
    private JLabel titleLabel;
    private JScrollPane tableScrollPane;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    public atctsigui(String tutorID) {
        initComponents();
        setupLayout();
        loadStudentInformationFromFile(tutorID);
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Student Information");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        // Initialize table model FIRST with column names
        String[] columnNames = {"StudentID", "Name", "Age", "Subject Enroll", "Enroll Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Now create table with the initialized model
        studentTable = new JTable(tableModel);
        
        // Optional: Make first three columns non-resizable
        if (studentTable.getColumnCount() > 0) {
            studentTable.getColumnModel().getColumn(0).setResizable(false);
            studentTable.getColumnModel().getColumn(1).setResizable(false);
            studentTable.getColumnModel().getColumn(2).setResizable(false);       
        }
        
        tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 400));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tablePanel.add(tableScrollPane);
        add(tablePanel, BorderLayout.CENTER);
        
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    // FIXED: Added String parameter type
    private void loadStudentInformationFromFile(String tutorID) {
        File file = new File("Text Files/TutorStudentInformation.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Student data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    // Extract the tutorID field (last field)
                    String tutorIDField = parts[5];
                    
                    // Check if the provided tutorID matches the tutorID field
                    if (tutorIDField.trim().equals(tutorID)) {
                        String studentID = parts[0];
                        String name = parts[1];
                        String age = parts[2];
                        String subjectEnroll = parts[3];
                        String enrollTime = parts[4];
                        tableModel.addRow(new Object[]{studentID, name, age, subjectEnroll, enrollTime});
                    }
                } else {
                    System.out.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading student data file!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Getters
    public JTable getStudentTable() { 
        return studentTable; 
    }
    
    public DefaultTableModel getTableModel() { 
        return tableModel; 
    }
}