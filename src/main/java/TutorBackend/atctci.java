package TutorBackend;

import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;
import TutorFrontend.atctcigui;

public class atctci {

    public class ClassFileManager {
        private static final String FILE_PATH = "Text Files/ClassInformation.txt";
        private static final String DELIMITER = ",";

        // Class data string
        public static class ClassRecord {
            private String tutorID;
            private String subject;
            private String classroom;
            private String day;
            private String startTime;
            private String endTime;
            
            //constructor
            public ClassRecord(String tutorID, String subject, String classroom, 
                               String day, String startTime, String endTime) {
                this.tutorID = tutorID;
                this.subject = subject;
                this.classroom = classroom;
                this.day = day;
                this.startTime = startTime;
                this.endTime = endTime;
            }

            // Getters
            public String getTutorID(){
                return tutorID; 
            }
            public String getSubject(){ 
                return subject; 
            }
            public String getClassroom(){ 
                return classroom; 
            }
            public String getDay(){ 
                return day; 
            }
            public String getStartTime(){
                return startTime;
            }
            public String getEndTime(){
                return endTime;
            }

            // Setters
            public void setSubject(String subject){
                this.subject = subject; // Fixed: was setting to classroom
            } 
            public void setClassroom(String classroom){
                this.classroom = classroom;
            }
            public void setDay(String day){
                this.day = day; 
           }
            public void setStartTime(String startTime){
                this.startTime = startTime; 
            }
            public void setEndTime(String endTime){
                this.endTime = endTime; 
            }

            // Convert to txt format (removed fee field)
            public String totxt() {
                return tutorID + DELIMITER + subject + DELIMITER + classroom + DELIMITER + 
                       day + DELIMITER + startTime + DELIMITER + endTime;
            }

            // Create from txt line (updated for 6 fields instead of 7)
            public static ClassRecord fromtxt(String csvLine) {
                String[] parts = csvLine.split(DELIMITER);
                if (parts.length != 6) {
                    throw new IllegalArgumentException("Invalid TEXT format");
                }

                return new ClassRecord(
                    parts[0].trim(),  // tutorID
                    parts[1].trim(),  // subject
                    parts[2].trim(),  // classroom
                    parts[3].trim(),  // day
                    parts[4].trim(),  // startTime
                    parts[5].trim()   // endTime
                );
            }
        }

        /**
         * Read all class records from file
         * @return List of ClassRecord objects
         */
        public static List<ClassRecord> readAllClasses() {
            List<ClassRecord> classes = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        try {
                            ClassRecord record = ClassRecord.fromtxt(line);
                            classes.add(record);
                            System.out.println("Hi");
                        } catch (Exception e) {
                            System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Classes file not found. Starting with empty list.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading classes file: " + e.getMessage(), 
                                            "File Error", JOptionPane.ERROR_MESSAGE);
            }

            return classes;
        }

        /**
         * Read classes for a specific tutor
         * @param tutorID The tutor ID to filter by
         * @return List of ClassRecord objects for the specified tutor
         */
        public static List<ClassRecord> readClassesForTutor(String tutorID) {
            List<ClassRecord> allClasses = readAllClasses();
            List<ClassRecord> tutorClasses = new ArrayList<>();

            for (ClassRecord record : allClasses) {
                if (record.getTutorID().equals(tutorID)) {
                    tutorClasses.add(record);
                }
            }

            return tutorClasses;
        }

        /**
         * Write all class records to file
         * @param classes List of ClassRecord objects to write
         * @return true if successful, false otherwise
         */
        public static boolean writeAllClasses(List<ClassRecord> classes) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
                for (ClassRecord record : classes) {
                    pw.println(record.totxt());
                }
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to classes file: " + e.getMessage(),"File Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        /**
         * Add a new class record
         * @param newClass The ClassRecord to add
         * @return true if successful, false otherwise
         */
        public static boolean addClass(ClassRecord newClass) {
            List<ClassRecord> classes = readAllClasses();
            classes.add(newClass);
            return writeAllClasses(classes);
        }

        /**
         * Update an existing class record
         * @param tutorID The tutor ID
         * @param oldClassroom The old classroom (used as identifier)
         * @param oldDay The old day (used as identifier)
         * @param oldStartTime The old start time (used as identifier)
         * @param updatedClass The updated ClassRecord
         * @return true if successful, false otherwise
         */
        public static boolean updateClass(String tutorID, String oldClassroom, String oldDay, 
                                        String oldStartTime, ClassRecord updatedClass) {
            List<ClassRecord> classes = readAllClasses();
            boolean found = false;

            for (int i = 0; i < classes.size(); i++) {
                ClassRecord record = classes.get(i);
                if (record.getTutorID().equals(tutorID) && 
                    record.getClassroom().equals(oldClassroom) &&
                    record.getDay().equals(oldDay) &&
                    record.getStartTime().equals(oldStartTime)) {

                    classes.set(i, updatedClass);
                    found = true;
                    break;
                }
            }

            if (found) {
                return writeAllClasses(classes);
            } else {
                JOptionPane.showMessageDialog(null, "Class record not found for update.", 
                                            "Update Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        /**
         * Delete a class record
         * @param tutorID The tutor ID
         * @param classroom The classroom
         * @param day The day
         * @param startTime The start time
         * @return true if successful, false otherwise
         */
        public static boolean deleteClass(String tutorID, String classroom, String day, String startTime) {
            List<ClassRecord> classes = readAllClasses();
            boolean found = false;

            Iterator<ClassRecord> iterator = classes.iterator();
            while (iterator.hasNext()) {
                ClassRecord record = iterator.next();
                if (record.getTutorID().equals(tutorID) && 
                    record.getClassroom().equals(classroom) &&
                    record.getDay().equals(day) &&
                    record.getStartTime().equals(startTime)) {

                    iterator.remove();
                    found = true;
                    break;
                }
            }

            if (found) {
                return writeAllClasses(classes);
            } else {
                JOptionPane.showMessageDialog(null, "Class record not found for deletion.", 
                                            "Delete Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        /**
         * Validate time format (HH;MM)
         * @param time The time string to validate
         * @return true if valid, false otherwise
         */
        public static boolean isValidTimeFormat(String time) {
            if (time == null || !time.contains(";")) {
                return false;
            }

            String[] parts = time.split(";");
            if (parts.length != 2) {
                return false;
            }

            try {
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /**
         * Get unique subjects from all classes
         * @return Set of unique subjects
         */
        public static Set<String> getAllSubjects() {
            List<ClassRecord> classes = readAllClasses();
            Set<String> subjects = new HashSet<>();

            for (ClassRecord record : classes) {
                subjects.add(record.getSubject());
            }

            return subjects;
        }

        /**
         * Convert ClassRecord list to 2D Object array for JTable (removed fee column)
         * Note: Excludes tutorID as per rules (index 0 not shown)
         */
        public static Object[][] convertToTableData(List<ClassRecord> classes) {
            Object[][] data = new Object[classes.size()][5]; // 5 columns (excluding tutorID and fee)

            for (int i = 0; i < classes.size(); i++) {
                ClassRecord record = classes.get(i);
                data[i][0] = record.getSubject();     // Not editable
                data[i][1] = record.getClassroom();
                data[i][2] = record.getDay();
                data[i][3] = record.getStartTime();
                data[i][4] = record.getEndTime();
            }

            return data;
        }

        /**
         * Get column names for JTable (excluding tutorID and fee)
         */
        public static String[] getTableColumnNames() {
            return new String[]{"Subject", "Classroom", "Day", "Start Time", "End Time"};
        }

        /**
         * Add button action - Shows input dialog and adds new class (removed fee input)
         * @param tutorID The logged-in tutor's ID
         * @return true if class was added successfully
         */
        public static boolean addClassAction(String tutorID) {
            try {
                // Get subject (from existing subjects or new one)
                Set<String> existingSubjects = getAllSubjects();
                String[] subjectOptions = existingSubjects.toArray(new String[0]);

                String subject;
                if (subjectOptions.length > 0) {
                    Object selectedSubject = JOptionPane.showInputDialog(
                        null,
                        "Select existing subject or type new one:",
                        "Add Class - Subject",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        subjectOptions,
                        subjectOptions[0]
                    );

                    if (selectedSubject == null) return false; // User cancelled
                    subject = selectedSubject.toString().trim();

                    // Allow entering new subject
                    if (subject.isEmpty()) {
                        subject = JOptionPane.showInputDialog(null, "Enter new subject:", "Add Class", JOptionPane.QUESTION_MESSAGE);
                        if (subject == null || subject.trim().isEmpty()) return false;
                        subject = subject.trim();
                    }
                } else {
                    subject = JOptionPane.showInputDialog(null, "Enter subject:", "Add Class", JOptionPane.QUESTION_MESSAGE);
                    if (subject == null || subject.trim().isEmpty()) return false;
                    subject = subject.trim();
                }

                // Get classroom
                String classroom = JOptionPane.showInputDialog(null, "Enter classroom:", "Add Class", JOptionPane.QUESTION_MESSAGE);
                if (classroom == null || classroom.trim().isEmpty()) return false;
                classroom = classroom.trim();

                // Get day
                String[] dayOptions = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                String day = (String) JOptionPane.showInputDialog(
                    null,
                    "Select day:",
                    "Add Class",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dayOptions,
                    dayOptions[0]
                );
                if (day == null) return false;

                // Get start time
                String startTime = JOptionPane.showInputDialog(null, "Enter start time (HH;MM format, e.g., 14;30):", "Add Class", JOptionPane.QUESTION_MESSAGE);
                if (startTime == null || !isValidTimeFormat(startTime.trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid time format! Please use HH;MM format (e.g., 14;30).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                startTime = startTime.trim();

                // Get end time
                String endTime = JOptionPane.showInputDialog(null, "Enter end time (HH;MM format, e.g., 15;30):", "Add Class", JOptionPane.QUESTION_MESSAGE);
                if (endTime == null || !isValidTimeFormat(endTime.trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid time format! Please use HH;MM format (e.g., 15;30).", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                endTime = endTime.trim();

                // Create and add the class (removed fee parameter)
                ClassRecord newClass = new ClassRecord(tutorID, subject, classroom, day, startTime, endTime);
                boolean success = addClass(newClass);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Class added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add class!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                return success;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error adding class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        /**
         * Update button action - Shows dialog to update selected class (removed fee)
         * @param tutorID The logged-in tutor's ID
         * @param selectedRow The selected row in the table (-1 if none selected)
         * @param tableData The current table data
         * @return true if class was updated successfully
         */
        public static boolean updateClassAction(String tutorID, int selectedRow, Object[][] tableData) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a class to update!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try {
                // Get current values from table (updated indices without fee)
                String currentSubject = tableData[selectedRow][0].toString();
                String currentClassroom = tableData[selectedRow][1].toString();
                String currentDay = tableData[selectedRow][2].toString();
                String currentStartTime = tableData[selectedRow][3].toString();
                String currentEndTime = tableData[selectedRow][4].toString();

                // Show current values and get new ones (removed fee)
                JOptionPane.showMessageDialog(null, 
                    "Current class details:\n" +
                    "Subject: " + currentSubject + " (Cannot be edited)\n" +
                    "Classroom: " + currentClassroom + "\n" +
                    "Day: " + currentDay + "\n" +
                    "Start Time: " + currentStartTime + "\n" +
                    "End Time: " + currentEndTime,
                    "Current Class Info", JOptionPane.INFORMATION_MESSAGE);

                // Get new classroom
                String newClassroom = JOptionPane.showInputDialog(null, "Enter new classroom:", currentClassroom);
                if (newClassroom == null) return false; // User cancelled
                newClassroom = newClassroom.trim();
                if (newClassroom.isEmpty()) newClassroom = currentClassroom;

                // Get new day
                String[] dayOptions = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
                String newDay = (String) JOptionPane.showInputDialog(
                    null,
                    "Select new day:",
                    "Update Class",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dayOptions,
                    currentDay
                );
                if (newDay == null) return false; // User cancelled

                // Get new start time
                String newStartTime = JOptionPane.showInputDialog(null, "Enter new start time (HH;MM format):", currentStartTime);
                if (newStartTime == null) return false; // User cancelled
                if (!isValidTimeFormat(newStartTime.trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid time format! Please use HH;MM format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                newStartTime = newStartTime.trim();

                // Get new end time
                String newEndTime = JOptionPane.showInputDialog(null, "Enter new end time (HH;MM format):", currentEndTime);
                if (newEndTime == null) return false; // User cancelled
                if (!isValidTimeFormat(newEndTime.trim())) {
                    JOptionPane.showMessageDialog(null, "Invalid time format! Please use HH;MM format.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                newEndTime = newEndTime.trim();

                // Create updated record (subject remains the same, removed fee)
                ClassRecord updatedClass = new ClassRecord(tutorID, currentSubject, newClassroom, newDay, newStartTime, newEndTime);

                // Update using old values as identifiers
                boolean success = updateClass(tutorID, currentClassroom, currentDay, currentStartTime, updatedClass);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Class updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update class!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                return success;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        /**
         * Delete button action - Confirms and deletes selected class (removed fee from display)
         * @param tutorID The logged-in tutor's ID
         * @param selectedRow The selected row in the table (-1 if none selected)
         * @param tableData The current table data
         * @return true if class was deleted successfully
         */
        public static boolean deleteClassAction(String tutorID, int selectedRow, Object[][] tableData) {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a class to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            try {
                // Get class details from table (updated indices without fee)
                String subject = tableData[selectedRow][0].toString();
                String classroom = tableData[selectedRow][1].toString();
                String day = tableData[selectedRow][2].toString();
                String startTime = tableData[selectedRow][3].toString();
                String endTime = tableData[selectedRow][4].toString();

                // Confirmation dialog (removed fee display)
                String message = "Are you sure you want to delete this class?\n\n" +
                               "Subject: " + subject + "\n" +
                               "Classroom: " + classroom + "\n" +
                               "Day: " + day + "\n" +
                               "Time: " + startTime + " - " + endTime;

                int choice = JOptionPane.showConfirmDialog(
                    null,
                    message,
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (choice != JOptionPane.YES_OPTION) {
                    return false; // User cancelled
                }

                // Delete the class
                boolean success = deleteClass(tutorID, classroom, day, startTime);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Class deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete class!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                return success;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error deleting class: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
}