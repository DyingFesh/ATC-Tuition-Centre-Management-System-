package TutorFrontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeatr
 */
public class atctcigui extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    // REMOVED: private JTextField feeField;
    private JLabel subjectLabel;
    private JComboBox<String> classroomComboBox, dayComboBox;
    private JSpinner startHourSpinner, startMinuteSpinner, endHourSpinner, endMinuteSpinner;
    private JButton addButton, updateButton, deleteButton;
    private String userID;
    private String subject;
    private static final String FILE_PATH = "Text Files/TutorClassInformation.txt";

    public atctcigui(String tutorID) {
        this.userID = tutorID;
        setLayout(new BorderLayout());

        // Table (removed "Fee" column)
        tableModel = new DefaultTableModel(
                new String[]{"Subject", "Classroom", "Day", "Start Time", "End Time"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(ev -> {
            // ignore extra events
            if (ev.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            if (row == -1) return;

            // pull each column out of the model (updated indices without fee)
            String subj     = tableModel.getValueAt(row, 0).toString();
            String room     = tableModel.getValueAt(row, 1).toString();
            String dayText  = tableModel.getValueAt(row, 2).toString();
            String startStr = tableModel.getValueAt(row, 3).toString();  // "HH:mm"
            String endStr   = tableModel.getValueAt(row, 4).toString();

            // set the form controls (changed subject to label)
            subjectLabel.setText(subj);
            classroomComboBox.setSelectedItem(room);
            dayComboBox.setSelectedItem(dayText);

            // parse and set spinners
            String[] st = startStr.split(":");
            String[] et = endStr.split(":");
            startHourSpinner.setValue(Integer.parseInt(st[0]));
            startMinuteSpinner.setValue(Integer.parseInt(st[1]));
            endHourSpinner.setValue(Integer.parseInt(et[0]));
            endMinuteSpinner.setValue(Integer.parseInt(et[1]));
        });

        // Table Container with Border
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(BorderFactory.createTitledBorder("Class Schedule"));
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);

        // Form Fields (changed subject to label)
        subjectLabel = new JLabel("N/A");
        subjectLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        subjectLabel.setOpaque(true);
        subjectLabel.setBackground(Color.WHITE);
        classroomComboBox = new JComboBox<>(new String[]{"Room 1","Room 2","Room 3","Room 4","Room 5","Room 6","Room 7","Room 8"});
        dayComboBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});

        startHourSpinner   = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
        endHourSpinner     = new JSpinner(new SpinnerNumberModel(12, 0, 23, 1));
        startMinuteSpinner = new JSpinner(new SpinnerNumberModel( 0, 0, 59, 1));
        endMinuteSpinner   = new JSpinner(new SpinnerNumberModel( 0, 0, 59, 1));

        // Form Panel (updated layout without fee - changed to 3x4 grid to fit all elements)
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Class Details"));
        formPanel.add(new JLabel("Subject"));
        formPanel.add(subjectLabel);
        formPanel.add(new JLabel("Classroom:"));
        formPanel.add(classroomComboBox);
        formPanel.add(new JLabel("Day:"));
        formPanel.add(dayComboBox);
        formPanel.add(new JLabel("Start Time:"));
        formPanel.add(createTimePanel(startHourSpinner, startMinuteSpinner));
        formPanel.add(new JLabel("End Time:"));
        formPanel.add(createTimePanel(endHourSpinner, endMinuteSpinner));
        formPanel.add(new JLabel("")); // Empty space
        formPanel.add(new JLabel("")); // Empty space

        // Buttons
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Combine all
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        contentPanel.add(formPanel, BorderLayout.NORTH);
        contentPanel.add(tableContainer, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);

        loadTutorClassInformationFromFile(tutorID);
        setupAddButtonListener();
        setupUpdateButtonListener();
        setupDeleteButtonListener();
    }

    // FILE OPERATIONS - CREATE, WRITE, DELETE
    
    /**
     * Create the file if it doesn't exist
     */
    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created new file: " + FILE_PATH);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error creating file: " + e.getMessage(),
                    "File Creation Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Write a new class record to the file
     */
    private boolean writeClassToFile(String tutorID, String subject, String classroom, 
                                    String day, String startTime, String endTime) {
        createFileIfNotExists();
        
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            // Format: tutorID,subject,classroom,day,startTime,endTime (removed fee)
            String record = tutorID + "," + subject + "," + classroom + "," + 
                           day + "," + startTime + "," + endTime;
            
            // Check if file is empty to determine if we need a leading newline
            File file = new File(FILE_PATH);
            if (file.length() > 0) {
                writer.write(System.lineSeparator()); // Add newline before record
            }
            
            writer.write(record);
            writer.write(System.lineSeparator()); // Add newline after record
            writer.flush();
            System.out.println("Written to file: " + record);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error writing to file: " + e.getMessage(),
                "File Write Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    /**
     * Update a class record in the file
     */
    private boolean updateClassInFile(String tutorID, String oldSubject, String oldClassroom, 
                                     String oldDay, String oldStartTime, String oldEndTime,
                                     String newSubject, String newClassroom, String newDay, 
                                     String newStartTime, String newEndTime) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        // Read all lines
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(tutorID)) {
                    // Check if this is the record to update (match old values)
                    String fileSubject = parts[1];
                    String fileClassroom = parts[2];
                    String fileDay = parts[3];
                    String fileStartTime = parts[4].replace(";", ":");
                    String fileEndTime = parts[5].replace(";", ":");
                    
                    if (fileSubject.equals(oldSubject) && fileClassroom.equals(oldClassroom) &&
                        fileDay.equals(oldDay) && fileStartTime.equals(oldStartTime) &&
                        fileEndTime.equals(oldEndTime)) {
                        // Replace with new values
                        String updatedRecord = tutorID + "," + newSubject + "," + newClassroom + "," + 
                                             newDay + "," + newStartTime.replace(":", ";") + "," + 
                                             newEndTime.replace(":", ";");
                        lines.add(updatedRecord);
                        found = true;
                        System.out.println("Updated record: " + updatedRecord);
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error reading file for update: " + e.getMessage(),
                "File Read Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(
                this,
                "Record not found for update.",
                "Update Error",
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        // Write all lines back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error writing updated file: " + e.getMessage(),
                "File Write Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    /**
     * Delete a class record from the file
     */
    private boolean deleteClassFromFile(String tutorID, String subject, String classroom, 
                                       String day, String startTime, String endTime) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        
        // Read all lines except the one to delete
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(tutorID)) {
                    // Check if this is the record to delete
                    String fileSubject = parts[1];
                    String fileClassroom = parts[2];
                    String fileDay = parts[3];
                    String fileStartTime = parts[4].replace(";", ":");
                    String fileEndTime = parts[5].replace(";", ":");
                    
                    if (fileSubject.equals(subject) && fileClassroom.equals(classroom) &&
                        fileDay.equals(day) && fileStartTime.equals(startTime) &&
                        fileEndTime.equals(endTime)) {
                        // Skip this line (delete it)
                        found = true;
                        System.out.println("Deleted record: " + line);
                        continue;
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error reading file for deletion: " + e.getMessage(),
                "File Read Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        
        if (!found) {
            JOptionPane.showMessageDialog(
                this,
                "Record not found for deletion.",
                "Delete Error",
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        
        // Write remaining lines back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.println(line);
            }
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error writing file after deletion: " + e.getMessage(),
                "File Write Error",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    private void setupAddButtonListener() {
        addButton.addActionListener(e -> {
            try {
                // Gather and validate input (subject now from label)
                String subjectText = subjectLabel.getText();
                String classroomText = (String) classroomComboBox.getSelectedItem();
                String dayText = (String) dayComboBox.getSelectedItem();
                Object startHour = startHourSpinner.getValue();
                Object startMinute = startMinuteSpinner.getValue();
                Object endHour = endHourSpinner.getValue();
                Object endMinute = endMinuteSpinner.getValue();

                // Validation (subject validation changed)
                if (subjectText == null || subjectText.isEmpty() || subjectText.equals("N/A")) {
                    throw new Exception("No subject selected. Please select a class from the table first.");
                }
                if (classroomText == null || classroomText.isEmpty()) {
                    throw new Exception("Please select a classroom.");
                }
                if (dayText == null || dayText.isEmpty()) {
                    throw new Exception("Please select a day.");
                }

                // Validate time logic (start time should be before end time)
                int startHourInt = (Integer) startHour;
                int startMinuteInt = (Integer) startMinute;
                int endHourInt = (Integer) endHour;
                int endMinuteInt = (Integer) endMinute;

                int startTimeMinutes = startHourInt * 60 + startMinuteInt;
                int endTimeMinutes = endHourInt * 60 + endMinuteInt;

                if (startTimeMinutes >= endTimeMinutes) {
                    throw new Exception("Start time must be before end time.");
                }

                String startTimeStr = String.format("%02d:%02d", startHourInt, startMinuteInt);
                String endTimeStr = String.format("%02d:%02d", endHourInt, endMinuteInt);

                // Check for duplicate classes (same day, time, classroom) - updated indices
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String existingDay = tableModel.getValueAt(i, 2).toString();
                    String existingClassroom = tableModel.getValueAt(i, 1).toString();
                    String existingStartTime = tableModel.getValueAt(i, 3).toString();
                    
                    if (existingDay.equals(dayText) && 
                        existingClassroom.equals(classroomText) &&
                        existingStartTime.equals(startTimeStr)) {
                        throw new Exception("A class already exists at this time, day, and classroom.");
                    }
                }

                // WRITE TO FILE
                String fileStartTime = String.format("%02d;%02d", startHourInt, startMinuteInt);
                String fileEndTime = String.format("%02d;%02d", endHourInt, endMinuteInt);
                
                boolean fileSuccess = writeClassToFile(userID, subjectText, classroomText, 
                                                      dayText, fileStartTime, fileEndTime);
                
                if (!fileSuccess) {
                    throw new Exception("Failed to save class to file.");
                }

                // Add to table (removed fee column)
                tableModel.addRow(new Object[]{
                    subjectText,
                    classroomText,
                    dayText,
                    startTimeStr,
                    endTimeStr
                });

                // Clear form fields after successful add
                clearFormFields();

                JOptionPane.showMessageDialog(
                    atctcigui.this,
                    "Class added successfully and saved to file!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    atctcigui.this,
                    ex.getMessage(),
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }

    private void setupUpdateButtonListener() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new Exception("Please select a class from the table to update.");
                    }

                    // Get OLD values from table for file update
                    String oldSubject = tableModel.getValueAt(selectedRow, 0).toString();
                    String oldClassroom = tableModel.getValueAt(selectedRow, 1).toString();
                    String oldDay = tableModel.getValueAt(selectedRow, 2).toString();
                    String oldStartTime = tableModel.getValueAt(selectedRow, 3).toString();
                    String oldEndTime = tableModel.getValueAt(selectedRow, 4).toString();

                    // Get NEW input values (subject from label)
                    String subjectText = subjectLabel.getText();
                    String classroomText = (String) classroomComboBox.getSelectedItem();
                    String dayText = (String) dayComboBox.getSelectedItem();
                    Object startHour = startHourSpinner.getValue();
                    Object startMinute = startMinuteSpinner.getValue();
                    Object endHour = endHourSpinner.getValue();
                    Object endMinute = endMinuteSpinner.getValue();

                    // Validation (subject validation changed)
                    if (subjectText == null || subjectText.isEmpty() || subjectText.equals("N/A")) {
                        throw new Exception("No subject selected. Please select a class from the table first.");
                    }
                    if (classroomText == null || classroomText.isEmpty()) {
                        throw new Exception("Please select a classroom.");
                    }
                    if (dayText == null || dayText.isEmpty()) {
                        throw new Exception("Please select a day.");
                    }

                    // Validate time logic
                    int startHourInt = (Integer) startHour;
                    int startMinuteInt = (Integer) startMinute;
                    int endHourInt = (Integer) endHour;
                    int endMinuteInt = (Integer) endMinute;

                    int startTimeMinutes = startHourInt * 60 + startMinuteInt;
                    int endTimeMinutes = endHourInt * 60 + endMinuteInt;

                    if (startTimeMinutes >= endTimeMinutes) {
                        throw new Exception("Start time must be before end time.");
                    }

                    String newStartTimeStr = String.format("%02d:%02d", startHourInt, startMinuteInt);
                    String newEndTimeStr = String.format("%02d:%02d", endHourInt, endMinuteInt);

                    // Check for conflicts with other classes (excluding the selected row) - updated indices
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (i == selectedRow) continue; // Skip the row being updated
                        
                        String existingDay = tableModel.getValueAt(i, 2).toString();
                        String existingClassroom = tableModel.getValueAt(i, 1).toString();
                        String existingStartTime = tableModel.getValueAt(i, 3).toString();
                        
                        if (existingDay.equals(dayText) && 
                            existingClassroom.equals(classroomText) &&
                            existingStartTime.equals(newStartTimeStr)) {
                            throw new Exception("Another class already exists at this time, day, and classroom.");
                        }
                    }

                    // UPDATE FILE
                    boolean fileSuccess = updateClassInFile(userID, oldSubject, oldClassroom, oldDay, 
                                                          oldStartTime, oldEndTime, subjectText, 
                                                          classroomText, dayText, newStartTimeStr, newEndTimeStr);
                    
                    if (!fileSuccess) {
                        throw new Exception("Failed to update class in file.");
                    }

                    // Update the selected row (updated indices without fee)
                    tableModel.setValueAt(subjectText, selectedRow, 0);
                    tableModel.setValueAt(classroomText, selectedRow, 1);
                    tableModel.setValueAt(dayText, selectedRow, 2);
                    tableModel.setValueAt(newStartTimeStr, selectedRow, 3);
                    tableModel.setValueAt(newEndTimeStr, selectedRow, 4);

                    // Clear form fields after successful update
                    clearFormFields();

                    JOptionPane.showMessageDialog(
                        atctcigui.this,
                        "Class updated successfully in file!",
                        "Update Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        atctcigui.this,
                        ex.getMessage(),
                        "Update Error",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
    }

    private void setupDeleteButtonListener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow == -1) {
                        throw new Exception("Please select a class from the table to delete.");
                    }

                    // Get class details for confirmation (updated indices without fee)
                    String subject = tableModel.getValueAt(selectedRow, 0).toString();
                    String classroom = tableModel.getValueAt(selectedRow, 1).toString();
                    String day = tableModel.getValueAt(selectedRow, 2).toString();
                    String startTime = tableModel.getValueAt(selectedRow, 3).toString();
                    String endTime = tableModel.getValueAt(selectedRow, 4).toString();
                    String message = "Are you sure you want to delete this class?\n\n" +
                                   "Subject: " + subject + "\n" +
                                   "Classroom: " + classroom + "\n" +
                                   "Day: " + day + "\n" +
                                   "Time: " + startTime + " - " + endTime;

                    int choice = JOptionPane.showConfirmDialog(
                        atctcigui.this,
                        message,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // DELETE FROM FILE FIRST
                        boolean fileSuccess = deleteClassFromFile(userID, subject, classroom, 
                                                                 day, startTime, endTime);
                        
                        if (!fileSuccess) {
                            throw new Exception("Failed to delete class from file.");
                        }

                        // Remove the row from table
                        tableModel.removeRow(selectedRow);

                        // Clear form fields
                        clearFormFields();

                        JOptionPane.showMessageDialog(
                            atctcigui.this,
                            "Class deleted successfully from file!",
                            "Delete Success",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        atctcigui.this,
                        ex.getMessage(),
                        "Delete Error",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        });
    }

    private void clearFormFields() {
        subjectLabel.setText("N/A");
        classroomComboBox.setSelectedIndex(0);
        // REMOVED: feeField.setText("");
        dayComboBox.setSelectedIndex(0);
        startHourSpinner.setValue(12);
        startMinuteSpinner.setValue(0);
        endHourSpinner.setValue(12);
        endMinuteSpinner.setValue(0);
    }

    private JPanel createTimePanel(JSpinner hourSpinner, JSpinner minuteSpinner) {
        JPanel panel = new JPanel();
        panel.add(hourSpinner);
        panel.add(new JLabel(":"));
        panel.add(minuteSpinner);
        return panel;
    }

    public void loadTutorClassInformationFromFile(String tutorID) {
        tableModel.setRowCount(0);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Updated to handle 6 parts instead of 7 (removed fee)
                if (parts.length == 6 && parts[0].equals(tutorID)) {
                    String subject = parts[1];
                    String classroom = parts[2];
                    String day = parts[3];
                    String startTime = parts[4].replace(";", ":");
                    String endTime = parts[5].replace(";", ":");
                    // Removed fee from the row data
                    tableModel.addRow(new Object[]{subject, classroom, day, startTime, endTime});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                atctcigui.this,
                "Error reading class information file.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Getters for button actions
    public JButton getAddButton(){
        return addButton; 
    }
    public JButton getUpdateButton(){
        return updateButton; 
    }
    public JButton getDeleteButton(){ 
        return deleteButton; 
    }

    public String getSubject(){ 
        return subjectLabel.getText(); 
    }
    public String getClassroom(){ 
        return (String) classroomComboBox.getSelectedItem(); 
    }
    public String getDay(){ 
        return (String) dayComboBox.getSelectedItem(); 
    }
    public String getStartTime(){
        return String.format("%02d;%02d", (int) startHourSpinner.getValue(), (int) startMinuteSpinner.getValue());
    }
    public String getEndTime(){
        return String.format("%02d;%02d", (int) endHourSpinner.getValue(), (int) endMinuteSpinner.getValue());
    }

    public JTable getTable() { 
        return table; 
    }
}