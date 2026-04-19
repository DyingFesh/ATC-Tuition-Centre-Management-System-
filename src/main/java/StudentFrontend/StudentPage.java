/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package StudentFrontend;

import ATCLogin.Login;
import AdminBackend.GeneralMethods;
import java.awt.CardLayout;
import StudentBackendFH.GetStudentInformation;
import StudentBackendFH.StudentInformationUpdated;
import StudentBackendFH.GetUserInformation;
import StudentBackendFH.UpdateUsername;     
import StudentBackendSR.RequestManager;
import StudentBackendDP.EditRequestTableDisplay;
import StudentBackendDP.ClassScheduleDisplay;
import StudentBackendDP.SubjectEnrollementTableDisplay;
import StudentBackendDP.UpdatedStudentInformation;
import StudentBackendDP.PaymentSummaryDisplay;
import StudentBackendDP.PaymentHistoryTableDisplay;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.io.*;
/**
 *
 * @author User
 */
public class StudentPage extends javax.swing.JFrame {
    CardLayout layout;
    String StudentID;
    GetStudentInformation studentInfo;
    GetUserInformation user;
    ArrayList<String> enrolledSubjects;
    
    // This is for password counter
    final int maxAttempt = 3;
    int attempt = 0;
    
    public StudentPage() {        
        initComponents();
        StudentID = StudentSession.getStudentid();
        this.setLocationRelativeTo(null);
        layout = (CardLayout) StudentMain.getLayout();
        studentInfo = new GetStudentInformation(StudentID);
        user = new GetUserInformation(StudentID);
        enrolledSubjects = studentInfo.getSubject();
        Name.setText("Name: " + studentInfo.getStudentName());
        GeneralMethods.LoadPFP(StudentPFP, "Profile Pictures/"+ StudentID +".jpg");
        
        StudentMain.add(MainPage,"main");
        StudentMain.add(ClassSchedule,"schedule");
        StudentMain.add(Payment,"payment");
        StudentMain.add(UpdateProfile,"profile");  
        StudentMain.add(UsernamePassword, "user");
        StudentMain.add(SubjectEnrolment,"SubEnrolment");
        StudentMain.add(EditRequest,"edit");
    }
    
    public class StudentSession {
        private static String currentstudentid;

        public static void setStudentid(String studentid) {
            currentstudentid = studentid;
        }

        public static String getStudentid() {
            return currentstudentid;
        }

        public static void clearSession() {
            currentstudentid = null;
        }
    }
    
    public void displayUpdateProfileInformation() {
        NameDisplay.setText(studentInfo.getStudentName());
        StudentIDDisplay.setText(studentInfo.getStudentID());
        IdentificationDisplay.setText(studentInfo.getStudentIdentification());
        AgeDisplay.setText(studentInfo.getStudentAge());
        GenderDisplay.setText(studentInfo.getStudentGender());
        LevelDisplay.setText(studentInfo.getStudentLevel());
        DateJoinedDisplay.setText(studentInfo.getDateJoined());   
        
        // This is set the check box for the enrolled subject to be uneditable and automatically checks the subject box the student is enrolled in
        if(enrolledSubjects.contains("Science")){
            Science.setSelected(true);
        }else {Science.setSelected(false);}
        Science.setEnabled(false);
        
        if(enrolledSubjects.contains("English")){
            English.setSelected(true);
        }else {English.setSelected(false);}
        English.setEnabled(false);
        
        if (enrolledSubjects.contains("Mathematics")){
            Maths.setSelected(true);
        }else {Maths.setSelected(false);}
        Maths.setEnabled(false);
        
        // This is to display the original informaiton for the student
        EmailField.setText(studentInfo.getStudentEmail().replace("@gmail.com", ""));
        PhoneNumberField.setText(studentInfo.getStudentPH().replace("+60",""));
        AddressField.setText(studentInfo.getStudentAddress());
        
    }
    
    public void updatedInformation() {
        String studentID = studentInfo.getStudentID();
        String name = studentInfo.getStudentName();
        String age = studentInfo.getStudentAge();
        String gender = studentInfo.getStudentGender();
        String identification = studentInfo.getStudentIdentification();
        String updatedEmail = EmailField.getText();
        String updatedPH = PhoneNumberField.getText();
        String updatedAddress = AddressField.getText();
        String level = studentInfo.getStudentLevel();
        ArrayList<String> arraySubjects = studentInfo.getSubject();
        String dateJoined = studentInfo.getDateJoined();
        
        // This is to join the array list making it into a string
        String subjects = String.join(";", arraySubjects);
        
        
        // This will add @gmail.com if they did not type it for the email
        if (!updatedEmail.contains("@gmail.com")) {
            updatedEmail += "@gmail.com";
        }
        
        // This will add 60 in front of the phone number if they did not type it
        if (!updatedPH.contains("+60" + updatedPH )) {
            updatedPH = "+60" + updatedPH;
        }
        
        UpdatedStudentInformation updatedInfo = new UpdatedStudentInformation(studentID, name, age , gender, 
                identification, updatedEmail, updatedPH, updatedAddress, level, subjects, dateJoined);
        
        //This is to pass to the file handler for student information
        StudentInformationUpdated.updateStudentFile(updatedInfo);
        
        JOptionPane.showMessageDialog(this, "Student info updated successfully.");
    } 
    
    public void changeUsername() {                             
        String StudentID = user.getStudentID();
        String newUsername = NewUsernameField.getText();
        String enteredPassword = EnterPasswordField.getText();

        if (enteredPassword.equals(user.getStudentPassword())) {
            if (!newUsername.isEmpty()) {
                if (GetUserInformation.isUsernameTaken(newUsername)) {
                    JOptionPane.showMessageDialog(this, 
                        "The username " + newUsername + " is already taken. Please choose another.",
                        "Username Taken", JOptionPane.WARNING_MESSAGE);
                    return; 
                }

                try {
                    UpdateUsername UsernameUpdate = new UpdateUsername(StudentID, newUsername, enteredPassword);
                    UsernameUpdate.updateUserFile();
                    JOptionPane.showMessageDialog(this, "Username updated successfully.");

                    user = new GetUserInformation(StudentID);            
                    DisplayCurrentUsername.setText(user.getStudentUsername());

                    NewUsernameField.setText("");
                    EnterPasswordField.setText("");
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(this, "User file not found: " + e.getMessage(),
                        "File Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "New Username is not entered.");
            }
        } else {
            attempt++;
            int attemptLeft = maxAttempt - attempt;

            if (attemptLeft > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Incorrect password. You have " + attemptLeft + " tries left.",
                    "Wrong Password", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "You have entered the wrong password 3 times. Access denied.",
                    "Access Denied", JOptionPane.ERROR_MESSAGE);
                layout.show(StudentMain, "profile");
            }
        }
    }
    
    public void changePassword() {                           
        String StudentID = user.getStudentID();
        String newPassword = NewPasswordField.getText();
        String confirmPassword = ConfirmNewPasswordField.getText();
        String currentPassword = currentPasswordField.getText();
        String username = user.getStudentUsername();

        if (currentPassword.equals(user.getStudentPassword())) {
            if (!newPassword.isEmpty() && !confirmPassword.isEmpty()) {
                if (newPassword.equals(confirmPassword)) {
                    if (newPassword.equals(currentPassword)) {
                        JOptionPane.showMessageDialog(null, "New password cannot be the same as current password.");
                        return;
                    }
                    try {
                        UpdateUsername passwordUpdate = new UpdateUsername(StudentID, username, confirmPassword);
                        passwordUpdate.updateUserFile();
                        JOptionPane.showMessageDialog(this, "Password updated successfully.");
                        NewPasswordField.setText("");
                        ConfirmNewPasswordField.setText("");
                        currentPasswordField.setText("");

                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(this, "User file not found: " + e.getMessage(),
                            "File Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "New password and confirm password do not match.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both new password fields.");
            }
        } else {
            attempt++;            
            int attemptLeft = maxAttempt - attempt;

            if (attemptLeft > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Incorrect password. You have " + attemptLeft + " tries left.",
                    "Wrong Password", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "You have entered the wrong password 3 times. Access denied.",
                    "Access Denied", JOptionPane.ERROR_MESSAGE);          
                layout.show(StudentMain, "profile");
            }
        }
    }
    
    public void showClassSchedule() {
        ClassScheduleDisplay schedule = new ClassScheduleDisplay();
        DefaultTableModel model = schedule.ClassScheduleDisplay(StudentID);
        Timetable.setModel(model);
    }
    
    public void subjectEnrollement () {
        jTextField1.setText("");
        
        if(enrolledSubjects.contains("Science")){
            reqSci.setEnabled(false);
        }else {reqSci.setSelected(false);}
               
        if(enrolledSubjects.contains("English")){
           reqEnglish.setEnabled(false);
        }else {reqEnglish.setSelected(false);}
               
        if (enrolledSubjects.contains("Mathematics")){
            reqMaths.setEnabled(false);
        }else {reqMaths.setSelected(false);}
        
        SubjectEnrollementTableDisplay display = new SubjectEnrollementTableDisplay();
        DefaultTableModel model = display.SubjectEnrollementTableDisplay(StudentID);
        SubjectEnrolledTable.setModel(model);
    }
    
    public void subEnrollmentReq(){
        String reason = jTextField1.getText().trim();

        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter something in the text field.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Count how many subjects are selected
        int selectedCount = 0;
        if (reqMaths.isSelected()) selectedCount++;
        if (reqSci.isSelected()) selectedCount++;
        if (reqEnglish.isSelected()) selectedCount++;

        if (selectedCount != 1) {
            JOptionPane.showMessageDialog(null, "Please select exactly one subject.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Continue with request submission
        RequestManager manager = new RequestManager();
        manager.loadFromFile();

        if (reqMaths.isSelected()) {
            manager.addRequest(StudentID, "Mathematics", reason);
        } else if (reqSci.isSelected()) {
            manager.addRequest(StudentID, "Science", reason);
        } else if (reqEnglish.isSelected()) {
            manager.addRequest(StudentID, "English", reason);
        }

        JOptionPane.showMessageDialog(this, "Request submitted successfully!");
    }
    
    public void editRequest(){             
        EditRequestTableDisplay display = new EditRequestTableDisplay();
        DefaultTableModel model = display.EditRequestTableDisplay(StudentID);
        EditReq.setModel(model);
    }
    
    public void paymentTablesDisplay(){
        String selectedYear = jTabbedPane3.getTitleAt(jTabbedPane3.getSelectedIndex());

        DefaultTableModel model = PaymentSummaryDisplay.PaymentSummaryDisplay(StudentID, selectedYear);

        switch (selectedYear) {
            case "2025" -> Table2025.setModel(model);
            case "2024" -> Table2024.setModel(model);
            case "2023" -> Table2023.setModel(model);
            case "2022" -> Table2022.setModel(model);
            case "2021" -> Table2021.setModel(model);
            default -> System.out.println("Unknown tab/year selected: " + selectedYear);
        }
        
        PaymentHistoryTableDisplay history = new PaymentHistoryTableDisplay();
        DefaultTableModel model2 = history.PaymentHistoryTableDisplay(StudentID);
        PaymentHistory.setModel(model2);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        StudentMain = new javax.swing.JPanel();
        MainPage = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        ClassScheduleButtton = new javax.swing.JButton();
        SubjectEnrollmentButton = new javax.swing.JButton();
        PaymentButton = new javax.swing.JButton();
        EditRequestButton = new javax.swing.JButton();
        UpdateProfileButton = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        Name = new javax.swing.JLabel();
        ClassSchedule = new javax.swing.JPanel();
        MainPageButton1 = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jLabel4 = new javax.swing.JLabel();
        PayButton = new javax.swing.JButton();
        SubEnrollment1 = new javax.swing.JButton();
        ReqButton1 = new javax.swing.JButton();
        UpdateProfButton1 = new javax.swing.JButton();
        LOButton1 = new javax.swing.JButton();
        jDesktopPane4 = new javax.swing.JDesktopPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        Timetable = new javax.swing.JTable();
        Payment = new javax.swing.JPanel();
        MainPageButton = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        ClassScheButton = new javax.swing.JButton();
        SubEnrollment = new javax.swing.JButton();
        ReqButton = new javax.swing.JButton();
        UpdateProfButton = new javax.swing.JButton();
        LOButton = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        HistoryLabel = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        PaymentHistory = new javax.swing.JTable();
        Summary = new javax.swing.JDesktopPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        Table25 = new javax.swing.JScrollPane();
        Table2025 = new javax.swing.JTable();
        Table24 = new javax.swing.JScrollPane();
        Table2024 = new javax.swing.JTable();
        Tabel23 = new javax.swing.JScrollPane();
        Table2023 = new javax.swing.JTable();
        Table22 = new javax.swing.JScrollPane();
        Table2022 = new javax.swing.JTable();
        Table21 = new javax.swing.JScrollPane();
        Table2021 = new javax.swing.JTable();
        SummaryLabel = new javax.swing.JLabel();
        UpdateProfile = new javax.swing.JPanel();
        MainPageButton2 = new javax.swing.JButton();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jLabel5 = new javax.swing.JLabel();
        ClassScheduleBut = new javax.swing.JButton();
        ReqButton2 = new javax.swing.JButton();
        UpdateProfButton2 = new javax.swing.JButton();
        LOButton2 = new javax.swing.JButton();
        jDesktopPane5 = new javax.swing.JDesktopPane();
        NameLabel = new javax.swing.JLabel();
        StudentLabel = new javax.swing.JLabel();
        EmailField = new javax.swing.JTextField();
        IdentificationNoField = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        PhoneNumberLabel = new javax.swing.JLabel();
        PhoneNumberField = new javax.swing.JTextField();
        AddressLabel = new javax.swing.JLabel();
        AddressField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Maths = new javax.swing.JCheckBox();
        Science = new javax.swing.JCheckBox();
        English = new javax.swing.JCheckBox();
        dateJoinedLabel = new javax.swing.JLabel();
        formLabel1 = new javax.swing.JLabel();
        formLabel2 = new javax.swing.JLabel();
        StudentLabel1 = new javax.swing.JLabel();
        StudentLabel2 = new javax.swing.JLabel();
        StudentPFP = new AdminFrontend.ImagePanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        StudentLabel3 = new javax.swing.JLabel();
        NameDisplay = new javax.swing.JLabel();
        StudentIDDisplay = new javax.swing.JLabel();
        IdentificationDisplay = new javax.swing.JLabel();
        AgeDisplay = new javax.swing.JLabel();
        GenderDisplay = new javax.swing.JLabel();
        LevelDisplay = new javax.swing.JLabel();
        SaveChanges = new javax.swing.JButton();
        ChangeUsernamePassword = new javax.swing.JButton();
        DateJoinedDisplay = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ChangePFP = new javax.swing.JButton();
        ResetPFP = new javax.swing.JButton();
        SubEnrolment2 = new javax.swing.JButton();
        UsernamePassword = new javax.swing.JPanel();
        MainPageButton3 = new javax.swing.JButton();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jLabel7 = new javax.swing.JLabel();
        ClassScheduleBut1 = new javax.swing.JButton();
        PaymentButton3 = new javax.swing.JButton();
        EditRequestButton3 = new javax.swing.JButton();
        LOButton3 = new javax.swing.JButton();
        jDesktopPane6 = new javax.swing.JDesktopPane();
        ChangeUsernameLabel = new javax.swing.JLabel();
        CurrentUsername = new javax.swing.JLabel();
        DisplayCurrentUsername = new javax.swing.JLabel();
        NewUsername = new javax.swing.JLabel();
        NewUsernameField = new javax.swing.JTextField();
        EnteredPassword = new javax.swing.JLabel();
        EnterPasswordField = new javax.swing.JTextField();
        SaveUsername = new javax.swing.JButton();
        SubEnrollment3 = new javax.swing.JButton();
        jDesktopPane7 = new javax.swing.JDesktopPane();
        ChangePasswordLabel = new javax.swing.JLabel();
        NewPasswordLabel = new javax.swing.JLabel();
        ConfirmNewPasswordLabel = new javax.swing.JLabel();
        CurrentPasswordLabel = new javax.swing.JLabel();
        ConfirmNewPasswordField = new javax.swing.JTextField();
        currentPasswordField = new javax.swing.JTextField();
        NewPasswordField = new javax.swing.JTextField();
        SavePassword = new javax.swing.JButton();
        SubjectEnrolment = new javax.swing.JPanel();
        MainPageButton5 = new javax.swing.JButton();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        jLabel10 = new javax.swing.JLabel();
        ClassScheduleBut3 = new javax.swing.JButton();
        ReqButton5 = new javax.swing.JButton();
        UpdateProfButton5 = new javax.swing.JButton();
        LOButton5 = new javax.swing.JButton();
        jDesktopPane9 = new javax.swing.JDesktopPane();
        SubjectEnrolled = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SubjectEnrolledTable = new javax.swing.JTable();
        UpdateProfile2 = new javax.swing.JButton();
        jDesktopPane10 = new javax.swing.JDesktopPane();
        RequestSubject = new javax.swing.JLabel();
        NewPasswordLabel1 = new javax.swing.JLabel();
        ConfirmNewPasswordLabel1 = new javax.swing.JLabel();
        CurrentPasswordLabel1 = new javax.swing.JLabel();
        ConfirmNewPasswordField1 = new javax.swing.JTextField();
        currentPasswordField1 = new javax.swing.JTextField();
        NewPasswordField1 = new javax.swing.JTextField();
        SavePassword1 = new javax.swing.JButton();
        reqMaths = new javax.swing.JCheckBox();
        reqSci = new javax.swing.JCheckBox();
        reqEnglish = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        EditRequest = new javax.swing.JPanel();
        MainPageButton4 = new javax.swing.JButton();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        jLabel9 = new javax.swing.JLabel();
        ClassScheButton1 = new javax.swing.JButton();
        SubEnrollment4 = new javax.swing.JButton();
        ReqButton4 = new javax.swing.JButton();
        UpdateProfButton4 = new javax.swing.JButton();
        LOButton4 = new javax.swing.JButton();
        jDesktopPane8 = new javax.swing.JDesktopPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        EditReq = new javax.swing.JTable();
        CancelReq = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(930, 650));

        StudentMain.setPreferredSize(new java.awt.Dimension(930, 650));
        StudentMain.setLayout(new java.awt.CardLayout());

        MainPage.setPreferredSize(new java.awt.Dimension(930, 650));

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(0, 0, 153));
        WelcomeLabel.setText("Welcome to ATC! ");

        ClassScheduleButtton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        ClassScheduleButtton.setText("Class Schedule");
        ClassScheduleButtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheduleButttonActionPerformed(evt);
            }
        });

        SubjectEnrollmentButton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        SubjectEnrollmentButton.setText("Subject Enrolment");
        SubjectEnrollmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubjectEnrollmentButtonActionPerformed(evt);
            }
        });

        PaymentButton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        PaymentButton.setText("Payment");
        PaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentButtonActionPerformed(evt);
            }
        });

        EditRequestButton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        EditRequestButton.setText("Edit Request");
        EditRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditRequestButtonActionPerformed(evt);
            }
        });

        UpdateProfileButton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        UpdateProfileButton.setText("Update Profile");
        UpdateProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfileButtonActionPerformed(evt);
            }
        });

        LogOutButton.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        LogOutButton.setText("Log Out");
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });

        Name.setText("Name:");

        javax.swing.GroupLayout MainPageLayout = new javax.swing.GroupLayout(MainPage);
        MainPage.setLayout(MainPageLayout);
        MainPageLayout.setHorizontalGroup(
            MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(WelcomeLabel)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(312, 312, 312))
            .addGroup(MainPageLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClassScheduleButtton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SubjectEnrollmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
            .addGroup(MainPageLayout.createSequentialGroup()
                .addGap(342, 342, 342)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MainPageLayout.setVerticalGroup(
            MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPageLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Name)
                .addGap(51, 51, 51)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClassScheduleButtton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SubjectEnrollmentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(90, 90, 90)
                .addGroup(MainPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        StudentMain.add(MainPage, "card2");

        ClassSchedule.setPreferredSize(new java.awt.Dimension(950, 630));

        MainPageButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton1.setText("Main Page");
        MainPageButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButton1ActionPerformed(evt);
            }
        });

        jLayeredPane3.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Class Schedule");

        jLayeredPane3.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(627, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        PayButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PayButton.setText("Payment");
        PayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayButtonActionPerformed(evt);
            }
        });

        SubEnrollment1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubEnrollment1.setText("Subject Enrolment");
        SubEnrollment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEnrollment1ActionPerformed(evt);
            }
        });

        ReqButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqButton1.setText("Edit Request");
        ReqButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqButton1ActionPerformed(evt);
            }
        });

        UpdateProfButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfButton1.setText("Update Profile");
        UpdateProfButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfButton1ActionPerformed(evt);
            }
        });

        LOButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton1.setText("Log Out");
        LOButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButton1ActionPerformed(evt);
            }
        });

        jDesktopPane4.setBackground(new java.awt.Color(153, 153, 153));

        Timetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Subject", "Day", "Start Time", "End Time", "Room", "Teacher In Charge"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(Timetable);
        if (Timetable.getColumnModel().getColumnCount() > 0) {
            Timetable.getColumnModel().getColumn(0).setResizable(false);
            Timetable.getColumnModel().getColumn(1).setResizable(false);
            Timetable.getColumnModel().getColumn(2).setResizable(false);
            Timetable.getColumnModel().getColumn(3).setResizable(false);
            Timetable.getColumnModel().getColumn(4).setResizable(false);
            Timetable.getColumnModel().getColumn(5).setResizable(false);
        }

        jDesktopPane4.setLayer(jScrollPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane4Layout = new javax.swing.GroupLayout(jDesktopPane4);
        jDesktopPane4.setLayout(jDesktopPane4Layout);
        jDesktopPane4Layout.setHorizontalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 736, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane4Layout.setVerticalGroup(
            jDesktopPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ClassScheduleLayout = new javax.swing.GroupLayout(ClassSchedule);
        ClassSchedule.setLayout(ClassScheduleLayout);
        ClassScheduleLayout.setHorizontalGroup(
            ClassScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3)
            .addGroup(ClassScheduleLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(ClassScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ClassScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PayButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReqButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateProfButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LOButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SubEnrollment1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jDesktopPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ClassScheduleLayout.setVerticalGroup(
            ClassScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClassScheduleLayout.createSequentialGroup()
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ClassScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ClassScheduleLayout.createSequentialGroup()
                        .addComponent(MainPageButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(SubEnrollment1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(PayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ReqButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(UpdateProfButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(LOButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDesktopPane4))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        StudentMain.add(ClassSchedule, "card3");

        Payment.setPreferredSize(new java.awt.Dimension(950, 630));

        MainPageButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton.setText("Main Page");
        MainPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButtonActionPerformed(evt);
            }
        });

        jLayeredPane2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Payment");

        jLayeredPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ClassScheButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ClassScheButton.setText("Class Schedule");
        ClassScheButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheButtonActionPerformed(evt);
            }
        });

        SubEnrollment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubEnrollment.setText("Subject Enrolment");
        SubEnrollment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEnrollmentActionPerformed(evt);
            }
        });

        ReqButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqButton.setText("Edit Request");
        ReqButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqButtonActionPerformed(evt);
            }
        });

        UpdateProfButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfButton.setText("Update Profile");
        UpdateProfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfButtonActionPerformed(evt);
            }
        });

        LOButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton.setText("Log Out");
        LOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButtonActionPerformed(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane1.setToolTipText("");
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(760, 260));

        HistoryLabel.setBackground(new java.awt.Color(0, 0, 0));
        HistoryLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 28)); // NOI18N
        HistoryLabel.setForeground(new java.awt.Color(0, 0, 0));
        HistoryLabel.setText("History");

        PaymentHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Payment Made ID", "Payment ID", "Total Amount", "Date Paid", "Payment Plan", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(PaymentHistory);
        if (PaymentHistory.getColumnModel().getColumnCount() > 0) {
            PaymentHistory.getColumnModel().getColumn(0).setResizable(false);
            PaymentHistory.getColumnModel().getColumn(0).setPreferredWidth(50);
            PaymentHistory.getColumnModel().getColumn(1).setResizable(false);
            PaymentHistory.getColumnModel().getColumn(1).setPreferredWidth(50);
            PaymentHistory.getColumnModel().getColumn(2).setResizable(false);
            PaymentHistory.getColumnModel().getColumn(2).setPreferredWidth(50);
            PaymentHistory.getColumnModel().getColumn(3).setResizable(false);
            PaymentHistory.getColumnModel().getColumn(4).setResizable(false);
            PaymentHistory.getColumnModel().getColumn(5).setResizable(false);
        }

        jDesktopPane1.setLayer(HistoryLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(HistoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HistoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );

        Summary.setBackground(new java.awt.Color(153, 153, 153));
        Summary.setPreferredSize(new java.awt.Dimension(760, 260));

        Table2025.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Subject", "Amount", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table25.setViewportView(Table2025);
        if (Table2025.getColumnModel().getColumnCount() > 0) {
            Table2025.getColumnModel().getColumn(0).setResizable(false);
            Table2025.getColumnModel().getColumn(0).setPreferredWidth(50);
            Table2025.getColumnModel().getColumn(1).setResizable(false);
            Table2025.getColumnModel().getColumn(2).setResizable(false);
            Table2025.getColumnModel().getColumn(3).setResizable(false);
        }

        jTabbedPane3.addTab("2025", Table25);

        Table2024.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Subject", "Amount", "Status"
            }
        ));
        Table24.setViewportView(Table2024);
        if (Table2024.getColumnModel().getColumnCount() > 0) {
            Table2024.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jTabbedPane3.addTab("2024", Table24);

        Table2023.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Subject", "Amount", "Status"
            }
        ));
        Tabel23.setViewportView(Table2023);
        if (Table2023.getColumnModel().getColumnCount() > 0) {
            Table2023.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jTabbedPane3.addTab("2023", Tabel23);

        Table2022.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Subject", "Amount", "Status"
            }
        ));
        Table22.setViewportView(Table2022);
        if (Table2022.getColumnModel().getColumnCount() > 0) {
            Table2022.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jTabbedPane3.addTab("2022", Table22);

        Table2021.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Month", "Subject", "Amount", "Status"
            }
        ));
        Table21.setViewportView(Table2021);
        if (Table2021.getColumnModel().getColumnCount() > 0) {
            Table2021.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jTabbedPane3.addTab("2021", Table21);

        SummaryLabel.setBackground(new java.awt.Color(0, 0, 0));
        SummaryLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 28)); // NOI18N
        SummaryLabel.setForeground(new java.awt.Color(0, 0, 0));
        SummaryLabel.setText("Summary");

        Summary.setLayer(jTabbedPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        Summary.setLayer(SummaryLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout SummaryLayout = new javax.swing.GroupLayout(Summary);
        Summary.setLayout(SummaryLayout);
        SummaryLayout.setHorizontalGroup(
            SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                    .addGroup(SummaryLayout.createSequentialGroup()
                        .addComponent(SummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        SummaryLayout.setVerticalGroup(
            SummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SummaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PaymentLayout = new javax.swing.GroupLayout(Payment);
        Payment.setLayout(PaymentLayout);
        PaymentLayout.setHorizontalGroup(
            PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
            .addGroup(PaymentLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ClassScheButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReqButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateProfButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addComponent(LOButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SubEnrollment, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Summary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        PaymentLayout.setVerticalGroup(
            PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaymentLayout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(PaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PaymentLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(MainPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ClassScheButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(SubEnrollment, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ReqButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(UpdateProfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(LOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PaymentLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Summary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        StudentMain.add(Payment, "card4");

        UpdateProfile.setPreferredSize(new java.awt.Dimension(950, 630));

        MainPageButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton2.setText("Main Page");
        MainPageButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButton2ActionPerformed(evt);
            }
        });

        jLayeredPane4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Update Profile");

        jLayeredPane4.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane4Layout = new javax.swing.GroupLayout(jLayeredPane4);
        jLayeredPane4.setLayout(jLayeredPane4Layout);
        jLayeredPane4Layout.setHorizontalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane4Layout.setVerticalGroup(
            jLayeredPane4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ClassScheduleBut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ClassScheduleBut.setText("Class Schedule");
        ClassScheduleBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheduleButActionPerformed(evt);
            }
        });

        ReqButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqButton2.setText("Payment");
        ReqButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqButton2ActionPerformed(evt);
            }
        });

        UpdateProfButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfButton2.setText("Edit Request");
        UpdateProfButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfButton2ActionPerformed(evt);
            }
        });

        LOButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton2.setText("Log Out");
        LOButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButton2ActionPerformed(evt);
            }
        });

        jDesktopPane5.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane5.setPreferredSize(new java.awt.Dimension(696, 500));

        NameLabel.setBackground(new java.awt.Color(255, 255, 255));
        NameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        NameLabel.setText("Name: ");

        StudentLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        StudentLabel.setText("Student ID:");

        EmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailFieldActionPerformed(evt);
            }
        });

        IdentificationNoField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdentificationNoField.setText("Identification No:");

        EmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        EmailLabel.setText("Email:");

        PhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        PhoneNumberLabel.setText("Phone Number:");

        PhoneNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneNumberFieldActionPerformed(evt);
            }
        });

        AddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        AddressLabel.setText("Address:");

        AddressField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressFieldActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Subjects:");

        Maths.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Maths.setText("Mathematics");
        Maths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MathsActionPerformed(evt);
            }
        });

        Science.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Science.setText("Science");
        Science.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScienceActionPerformed(evt);
            }
        });

        English.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        English.setText("English");
        English.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnglishActionPerformed(evt);
            }
        });

        dateJoinedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        dateJoinedLabel.setText("Date Joined:");

        formLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        formLabel1.setText("+60");

        formLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        formLabel2.setText("@gmail.com");

        StudentLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        StudentLabel1.setText("Age:");

        StudentLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        StudentLabel2.setText("Level:");

        javax.swing.GroupLayout StudentPFPLayout = new javax.swing.GroupLayout(StudentPFP);
        StudentPFP.setLayout(StudentPFPLayout);
        StudentPFPLayout.setHorizontalGroup(
            StudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        StudentPFPLayout.setVerticalGroup(
            StudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        StudentLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        StudentLabel3.setText("Gender:");

        NameDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        NameDisplay.setText("...");

        StudentIDDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        StudentIDDisplay.setText("...");

        IdentificationDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        IdentificationDisplay.setText("...");

        AgeDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        AgeDisplay.setText("...");

        GenderDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        GenderDisplay.setText("...");

        LevelDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        LevelDisplay.setText("...");

        SaveChanges.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SaveChanges.setText("Save Changes");
        SaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveChangesActionPerformed(evt);
            }
        });

        ChangeUsernamePassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ChangeUsernamePassword.setText("Change Username and Password");
        ChangeUsernamePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeUsernamePasswordActionPerformed(evt);
            }
        });

        DateJoinedDisplay.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        DateJoinedDisplay.setText("...");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Personal Information");

        ChangePFP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ChangePFP.setText("Change Profile Picture");
        ChangePFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePFPActionPerformed(evt);
            }
        });

        ResetPFP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ResetPFP.setText("Reset Profile Picture");
        ResetPFP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetPFPActionPerformed(evt);
            }
        });

        jDesktopPane5.setLayer(NameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(EmailField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(IdentificationNoField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(EmailLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(PhoneNumberLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(PhoneNumberField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(AddressLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(AddressField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(Maths, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(Science, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(English, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(dateJoinedLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(formLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(formLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentPFP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jSeparator2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jSeparator3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(NameDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(StudentIDDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(IdentificationDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(AgeDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(GenderDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(LevelDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(SaveChanges, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(ChangeUsernamePassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(DateJoinedDisplay, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(ChangePFP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane5.setLayer(ResetPFP, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane5Layout = new javax.swing.GroupLayout(jDesktopPane5);
        jDesktopPane5.setLayout(jDesktopPane5Layout);
        jDesktopPane5Layout.setHorizontalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                    .addGap(240, 240, 240)
                    .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane5Layout.createSequentialGroup()
                            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(NameLabel)
                                .addComponent(StudentLabel))
                            .addGap(55, 55, 55)
                            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(NameDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(StudentIDDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jDesktopPane5Layout.createSequentialGroup()
                            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                    .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(StudentLabel1)
                                        .addComponent(StudentLabel3)
                                        .addComponent(StudentLabel2))
                                    .addGap(79, 79, 79))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createSequentialGroup()
                                    .addComponent(IdentificationNoField)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(GenderDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AgeDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(IdentificationDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                    .addComponent(LevelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                                    .addComponent(dateJoinedLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(DateJoinedDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(13, 13, 13))
                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jDesktopPane5Layout.createSequentialGroup()
                            .addGap(237, 237, 237)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jDesktopPane5Layout.createSequentialGroup()
                            .addComponent(ChangeUsernamePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ChangePFP, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(ResetPFP)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(SaveChanges))
                        .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane5Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(37, 37, 37)
                                .addComponent(Maths, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Science, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(English, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(227, 227, 227))
                            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                .addComponent(EmailLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(formLabel2))
                            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                .addComponent(AddressLabel)
                                .addGap(20, 20, 20)
                                .addComponent(AddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                                .addComponent(PhoneNumberLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(formLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PhoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(17, Short.MAX_VALUE)))
            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(StudentPFP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(524, Short.MAX_VALUE)))
        );
        jDesktopPane5Layout.setVerticalGroup(
            jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StudentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StudentIDDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdentificationNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdentificationDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StudentLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AgeDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StudentLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GenderDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StudentLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LevelDisplay)
                    .addComponent(dateJoinedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DateJoinedDisplay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PhoneNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Maths, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(English, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Science, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChangeUsernamePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChangePFP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetPFP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jDesktopPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jDesktopPane5Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(StudentPFP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(333, Short.MAX_VALUE)))
        );

        SubEnrolment2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubEnrolment2.setText("Subject Enrolment");
        SubEnrolment2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEnrolment2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UpdateProfileLayout = new javax.swing.GroupLayout(UpdateProfile);
        UpdateProfile.setLayout(UpdateProfileLayout);
        UpdateProfileLayout.setHorizontalGroup(
            UpdateProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4)
            .addGroup(UpdateProfileLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(UpdateProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UpdateProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ClassScheduleBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReqButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateProfButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addComponent(LOButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SubEnrolment2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jDesktopPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        UpdateProfileLayout.setVerticalGroup(
            UpdateProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpdateProfileLayout.createSequentialGroup()
                .addComponent(jLayeredPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(UpdateProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UpdateProfileLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDesktopPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UpdateProfileLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(MainPageButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(ClassScheduleBut, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(SubEnrolment2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(ReqButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(UpdateProfButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(LOButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        StudentMain.add(UpdateProfile, "card5");

        UsernamePassword.setPreferredSize(new java.awt.Dimension(930, 650));

        MainPageButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton3.setText("Main Page");
        MainPageButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButton3ActionPerformed(evt);
            }
        });

        jLayeredPane5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Change Username and Password");

        jLayeredPane5.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane5Layout = new javax.swing.GroupLayout(jLayeredPane5);
        jLayeredPane5.setLayout(jLayeredPane5Layout);
        jLayeredPane5Layout.setHorizontalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane5Layout.setVerticalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ClassScheduleBut1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ClassScheduleBut1.setText("Class Schedule");
        ClassScheduleBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheduleBut1ActionPerformed(evt);
            }
        });

        PaymentButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PaymentButton3.setText("Payment");
        PaymentButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentButton3ActionPerformed(evt);
            }
        });

        EditRequestButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        EditRequestButton3.setText("Edit Request");
        EditRequestButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditRequestButton3ActionPerformed(evt);
            }
        });

        LOButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton3.setText("Log Out");
        LOButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButton3ActionPerformed(evt);
            }
        });

        jDesktopPane6.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane6.setPreferredSize(new java.awt.Dimension(696, 500));

        ChangeUsernameLabel.setBackground(new java.awt.Color(0, 0, 0));
        ChangeUsernameLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        ChangeUsernameLabel.setForeground(new java.awt.Color(0, 0, 0));
        ChangeUsernameLabel.setText("Change Username");

        CurrentUsername.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CurrentUsername.setText("Current Username:");

        DisplayCurrentUsername.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        DisplayCurrentUsername.setText("...");

        NewUsername.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        NewUsername.setText("New Username:");

        NewUsernameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NewUsernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewUsernameFieldActionPerformed(evt);
            }
        });

        EnteredPassword.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        EnteredPassword.setText("Password:");

        EnterPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        EnterPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterPasswordFieldActionPerformed(evt);
            }
        });

        SaveUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SaveUsername.setText("Save Username");
        SaveUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveUsernameActionPerformed(evt);
            }
        });

        jDesktopPane6.setLayer(ChangeUsernameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(CurrentUsername, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(DisplayCurrentUsername, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(NewUsername, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(NewUsernameField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(EnteredPassword, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(EnterPasswordField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane6.setLayer(SaveUsername, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane6Layout = new javax.swing.GroupLayout(jDesktopPane6);
        jDesktopPane6.setLayout(jDesktopPane6Layout);
        jDesktopPane6Layout.setHorizontalGroup(
            jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CurrentUsername)
                    .addComponent(NewUsername)
                    .addComponent(EnteredPassword))
                .addGap(27, 27, 27)
                .addGroup(jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EnterPasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(NewUsernameField)
                    .addComponent(DisplayCurrentUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(SaveUsername)
                .addContainerGap())
            .addGroup(jDesktopPane6Layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(ChangeUsernameLabel)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jDesktopPane6Layout.setVerticalGroup(
            jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(ChangeUsernameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CurrentUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DisplayCurrentUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewUsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EnteredPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EnterPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        SubEnrollment3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubEnrollment3.setText("Subject Enrollment");
        SubEnrollment3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEnrollment3ActionPerformed(evt);
            }
        });

        jDesktopPane7.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane7.setPreferredSize(new java.awt.Dimension(696, 500));

        ChangePasswordLabel.setBackground(new java.awt.Color(0, 0, 0));
        ChangePasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        ChangePasswordLabel.setForeground(new java.awt.Color(0, 0, 0));
        ChangePasswordLabel.setText("Change Password");

        NewPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        NewPasswordLabel.setText("New Password:");

        ConfirmNewPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ConfirmNewPasswordLabel.setText("Confirm New Password:");

        CurrentPasswordLabel.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CurrentPasswordLabel.setText("Current Password:");

        ConfirmNewPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ConfirmNewPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmNewPasswordFieldActionPerformed(evt);
            }
        });

        currentPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        currentPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentPasswordFieldActionPerformed(evt);
            }
        });

        NewPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NewPasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewPasswordFieldActionPerformed(evt);
            }
        });

        SavePassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SavePassword.setText("Save Password");
        SavePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavePasswordActionPerformed(evt);
            }
        });

        jDesktopPane7.setLayer(ChangePasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(NewPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(ConfirmNewPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(CurrentPasswordLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(ConfirmNewPasswordField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(currentPasswordField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(NewPasswordField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane7.setLayer(SavePassword, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane7Layout = new javax.swing.GroupLayout(jDesktopPane7);
        jDesktopPane7.setLayout(jDesktopPane7Layout);
        jDesktopPane7Layout.setHorizontalGroup(
            jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane7Layout.createSequentialGroup()
                        .addComponent(NewPasswordLabel)
                        .addGap(63, 63, 63)
                        .addComponent(NewPasswordField))
                    .addGroup(jDesktopPane7Layout.createSequentialGroup()
                        .addComponent(ConfirmNewPasswordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConfirmNewPasswordField))
                    .addGroup(jDesktopPane7Layout.createSequentialGroup()
                        .addComponent(CurrentPasswordLabel)
                        .addGap(42, 42, 42)
                        .addComponent(currentPasswordField)))
                .addGap(77, 77, 77)
                .addComponent(SavePassword)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ChangePasswordLabel)
                .addGap(277, 277, 277))
        );
        jDesktopPane7Layout.setVerticalGroup(
            jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(ChangePasswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmNewPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConfirmNewPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CurrentPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SavePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout UsernamePasswordLayout = new javax.swing.GroupLayout(UsernamePassword);
        UsernamePassword.setLayout(UsernamePasswordLayout);
        UsernamePasswordLayout.setHorizontalGroup(
            UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane5)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsernamePasswordLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ClassScheduleBut1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(PaymentButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EditRequestButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LOButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SubEnrollment3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDesktopPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                    .addComponent(jDesktopPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        UsernamePasswordLayout.setVerticalGroup(
            UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsernamePasswordLayout.createSequentialGroup()
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UsernamePasswordLayout.createSequentialGroup()
                        .addComponent(MainPageButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(ClassScheduleBut1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(SubEnrollment3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDesktopPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(UsernamePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UsernamePasswordLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(PaymentButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(EditRequestButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(LOButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UsernamePasswordLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jDesktopPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        StudentMain.add(UsernamePassword, "card5");

        SubjectEnrolment.setPreferredSize(new java.awt.Dimension(930, 650));

        MainPageButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton5.setText("Main Page");
        MainPageButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButton5ActionPerformed(evt);
            }
        });

        jLayeredPane7.setBackground(new java.awt.Color(153, 153, 153));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Subject Enrollment");

        jLayeredPane7.setLayer(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane7Layout = new javax.swing.GroupLayout(jLayeredPane7);
        jLayeredPane7.setLayout(jLayeredPane7Layout);
        jLayeredPane7Layout.setHorizontalGroup(
            jLayeredPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane7Layout.setVerticalGroup(
            jLayeredPane7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ClassScheduleBut3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ClassScheduleBut3.setText("Class Schedule");
        ClassScheduleBut3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheduleBut3ActionPerformed(evt);
            }
        });

        ReqButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqButton5.setText("Payment");
        ReqButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqButton5ActionPerformed(evt);
            }
        });

        UpdateProfButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfButton5.setText("Edit Request");
        UpdateProfButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfButton5ActionPerformed(evt);
            }
        });

        LOButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton5.setText("Log Out");
        LOButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButton5ActionPerformed(evt);
            }
        });

        jDesktopPane9.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane9.setPreferredSize(new java.awt.Dimension(696, 500));

        SubjectEnrolled.setBackground(new java.awt.Color(0, 0, 0));
        SubjectEnrolled.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        SubjectEnrolled.setForeground(new java.awt.Color(0, 0, 0));
        SubjectEnrolled.setText("Subject Enrolled");

        SubjectEnrolledTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Level", "Subject", "Teacher In Charge", "Status", "Date Enrolled"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(SubjectEnrolledTable);
        if (SubjectEnrolledTable.getColumnModel().getColumnCount() > 0) {
            SubjectEnrolledTable.getColumnModel().getColumn(0).setResizable(false);
            SubjectEnrolledTable.getColumnModel().getColumn(0).setPreferredWidth(120);
            SubjectEnrolledTable.getColumnModel().getColumn(1).setResizable(false);
            SubjectEnrolledTable.getColumnModel().getColumn(2).setResizable(false);
            SubjectEnrolledTable.getColumnModel().getColumn(3).setResizable(false);
            SubjectEnrolledTable.getColumnModel().getColumn(4).setResizable(false);
        }

        jDesktopPane9.setLayer(SubjectEnrolled, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane9.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane9Layout = new javax.swing.GroupLayout(jDesktopPane9);
        jDesktopPane9.setLayout(jDesktopPane9Layout);
        jDesktopPane9Layout.setHorizontalGroup(
            jDesktopPane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane9Layout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addComponent(SubjectEnrolled)
                .addGap(284, 284, 284))
            .addGroup(jDesktopPane9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jDesktopPane9Layout.setVerticalGroup(
            jDesktopPane9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(SubjectEnrolled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        UpdateProfile2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfile2.setText("Update Profile");
        UpdateProfile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfile2ActionPerformed(evt);
            }
        });

        jDesktopPane10.setBackground(new java.awt.Color(102, 102, 102));
        jDesktopPane10.setPreferredSize(new java.awt.Dimension(696, 500));

        RequestSubject.setBackground(new java.awt.Color(0, 0, 0));
        RequestSubject.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        RequestSubject.setForeground(new java.awt.Color(0, 0, 0));
        RequestSubject.setText("Request Subject");

        NewPasswordLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        NewPasswordLabel1.setText("New Password:");

        ConfirmNewPasswordLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        ConfirmNewPasswordLabel1.setText("Confirm New Password:");

        CurrentPasswordLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        CurrentPasswordLabel1.setText("Current Password:");

        ConfirmNewPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ConfirmNewPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmNewPasswordField1ActionPerformed(evt);
            }
        });

        currentPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        currentPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentPasswordField1ActionPerformed(evt);
            }
        });

        NewPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NewPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewPasswordField1ActionPerformed(evt);
            }
        });

        SavePassword1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SavePassword1.setText("Save Password");
        SavePassword1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SavePassword1ActionPerformed(evt);
            }
        });

        reqMaths.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reqMaths.setText("Mathematics");
        reqMaths.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqMathsActionPerformed(evt);
            }
        });

        reqSci.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reqSci.setText("Science");
        reqSci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqSciActionPerformed(evt);
            }
        });

        reqEnglish.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        reqEnglish.setText("English");
        reqEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqEnglishActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Request Subject");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Please enter your reason:");

        jDesktopPane10.setLayer(RequestSubject, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(NewPasswordLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(ConfirmNewPasswordLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(CurrentPasswordLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(ConfirmNewPasswordField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(currentPasswordField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(NewPasswordField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(SavePassword1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(reqMaths, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(reqSci, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(reqEnglish, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(jTextField1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane10.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane10Layout = new javax.swing.GroupLayout(jDesktopPane10);
        jDesktopPane10.setLayout(jDesktopPane10Layout);
        jDesktopPane10Layout.setHorizontalGroup(
            jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(NewPasswordLabel1)
                        .addGap(63, 63, 63)
                        .addComponent(NewPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                    .addGroup(jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(ConfirmNewPasswordLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ConfirmNewPasswordField1))
                    .addGroup(jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(CurrentPasswordLabel1)
                        .addGap(42, 42, 42)
                        .addComponent(currentPasswordField1)))
                .addGap(77, 77, 77)
                .addComponent(SavePassword1)
                .addContainerGap())
            .addGroup(jDesktopPane10Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(reqMaths)
                .addGap(121, 121, 121)
                .addComponent(reqSci)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(reqEnglish)
                .addGap(102, 102, 102))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(RequestSubject)
                        .addGap(280, 280, 280))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(16, 16, 16))))
            .addGroup(jDesktopPane10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane10Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jDesktopPane10Layout.setVerticalGroup(
            jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(RequestSubject)
                .addGap(24, 24, 24)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reqMaths)
                    .addComponent(reqSci)
                    .addComponent(reqEnglish))
                .addGap(20, 20, 20)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(219, 219, 219)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewPasswordLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmNewPasswordLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConfirmNewPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CurrentPasswordLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SavePassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SubjectEnrolmentLayout = new javax.swing.GroupLayout(SubjectEnrolment);
        SubjectEnrolment.setLayout(SubjectEnrolmentLayout);
        SubjectEnrolmentLayout.setHorizontalGroup(
            SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane7)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubjectEnrolmentLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ClassScheduleBut3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReqButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateProfButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LOButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(UpdateProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDesktopPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDesktopPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        SubjectEnrolmentLayout.setVerticalGroup(
            SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubjectEnrolmentLayout.createSequentialGroup()
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubjectEnrolmentLayout.createSequentialGroup()
                        .addComponent(MainPageButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(ClassScheduleBut3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(UpdateProfile2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDesktopPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(SubjectEnrolmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubjectEnrolmentLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(ReqButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(UpdateProfButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(LOButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SubjectEnrolmentLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jDesktopPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        StudentMain.add(SubjectEnrolment, "card5");

        EditRequest.setPreferredSize(new java.awt.Dimension(950, 630));

        MainPageButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainPageButton4.setText("Main Page");
        MainPageButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainPageButton4ActionPerformed(evt);
            }
        });

        jLayeredPane6.setBackground(new java.awt.Color(153, 153, 153));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Edit Request");

        jLayeredPane6.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane6Layout = new javax.swing.GroupLayout(jLayeredPane6);
        jLayeredPane6.setLayout(jLayeredPane6Layout);
        jLayeredPane6Layout.setHorizontalGroup(
            jLayeredPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(706, Short.MAX_VALUE))
        );
        jLayeredPane6Layout.setVerticalGroup(
            jLayeredPane6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        ClassScheButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ClassScheButton1.setText("Class Schedule");
        ClassScheButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClassScheButton1ActionPerformed(evt);
            }
        });

        SubEnrollment4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SubEnrollment4.setText("Payment");
        SubEnrollment4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubEnrollment4ActionPerformed(evt);
            }
        });

        ReqButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ReqButton4.setText("Subject Enrolment");
        ReqButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReqButton4ActionPerformed(evt);
            }
        });

        UpdateProfButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateProfButton4.setText("Update Profile");
        UpdateProfButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateProfButton4ActionPerformed(evt);
            }
        });

        LOButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        LOButton4.setText("Log Out");
        LOButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LOButton4ActionPerformed(evt);
            }
        });

        jDesktopPane8.setBackground(new java.awt.Color(153, 153, 153));

        EditReq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Form", "Subject", "Teacher In Charge", "Status", "Date Request"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(EditReq);
        if (EditReq.getColumnModel().getColumnCount() > 0) {
            EditReq.getColumnModel().getColumn(0).setResizable(false);
            EditReq.getColumnModel().getColumn(0).setPreferredWidth(120);
            EditReq.getColumnModel().getColumn(1).setResizable(false);
            EditReq.getColumnModel().getColumn(2).setResizable(false);
            EditReq.getColumnModel().getColumn(3).setResizable(false);
            EditReq.getColumnModel().getColumn(4).setResizable(false);
        }

        CancelReq.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        CancelReq.setText("Cancel Request");
        CancelReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelReqActionPerformed(evt);
            }
        });

        jDesktopPane8.setLayer(jScrollPane7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane8.setLayer(CancelReq, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane8Layout = new javax.swing.GroupLayout(jDesktopPane8);
        jDesktopPane8.setLayout(jDesktopPane8Layout);
        jDesktopPane8Layout.setHorizontalGroup(
            jDesktopPane8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CancelReq, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(jDesktopPane8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jDesktopPane8Layout.setVerticalGroup(
            jDesktopPane8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane8Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(CancelReq, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout EditRequestLayout = new javax.swing.GroupLayout(EditRequest);
        EditRequest.setLayout(EditRequestLayout);
        EditRequestLayout.setHorizontalGroup(
            EditRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane6)
            .addGroup(EditRequestLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(EditRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(EditRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(MainPageButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ClassScheButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReqButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UpdateProfButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                        .addComponent(LOButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SubEnrollment4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jDesktopPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EditRequestLayout.setVerticalGroup(
            EditRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditRequestLayout.createSequentialGroup()
                .addComponent(jLayeredPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(EditRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditRequestLayout.createSequentialGroup()
                        .addComponent(MainPageButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ClassScheButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(SubEnrollment4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(ReqButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(UpdateProfButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(LOButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDesktopPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        StudentMain.add(EditRequest, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(StudentMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(StudentMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MainPageButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButton2ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
        
    }//GEN-LAST:event_MainPageButton2ActionPerformed

    private void ClassScheduleButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheduleButActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheduleButActionPerformed

    private void SubEnrolment2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEnrolment2ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();  
    }//GEN-LAST:event_SubEnrolment2ActionPerformed

    private void ReqButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqButton2ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
    }//GEN-LAST:event_ReqButton2ActionPerformed

    private void UpdateProfButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfButton2ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_UpdateProfButton2ActionPerformed

    private void LOButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButton2ActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButton2ActionPerformed

    private void EmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailFieldActionPerformed

    private void PhoneNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhoneNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhoneNumberFieldActionPerformed

    private void AddressFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressFieldActionPerformed

    private void LOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButtonActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButtonActionPerformed

    private void UpdateProfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"profile");
        displayUpdateProfileInformation();
    }//GEN-LAST:event_UpdateProfButtonActionPerformed

    private void ReqButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_ReqButtonActionPerformed

    private void SubEnrollmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEnrollmentActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();  
    }//GEN-LAST:event_SubEnrollmentActionPerformed

    private void ClassScheButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheButtonActionPerformed

    private void MainPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
    }//GEN-LAST:event_MainPageButtonActionPerformed

    private void LOButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButton1ActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButton1ActionPerformed

    private void UpdateProfButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfButton1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"profile");
        displayUpdateProfileInformation();
    }//GEN-LAST:event_UpdateProfButton1ActionPerformed

    private void ReqButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqButton1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_ReqButton1ActionPerformed

    private void SubEnrollment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEnrollment1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();  
    }//GEN-LAST:event_SubEnrollment1ActionPerformed

    private void PayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
        
    }//GEN-LAST:event_PayButtonActionPerformed

    private void MainPageButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButton1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
    }//GEN-LAST:event_MainPageButton1ActionPerformed

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void UpdateProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfileButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"profile");
        displayUpdateProfileInformation();
    }//GEN-LAST:event_UpdateProfileButtonActionPerformed

    private void EditRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditRequestButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_EditRequestButtonActionPerformed

    private void PaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
    }//GEN-LAST:event_PaymentButtonActionPerformed

    private void SubjectEnrollmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubjectEnrollmentButtonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();     
    }//GEN-LAST:event_SubjectEnrollmentButtonActionPerformed

    private void ClassScheduleButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheduleButttonActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheduleButttonActionPerformed

    private void ScienceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScienceActionPerformed
        // TODO add your handling code here:  
    }//GEN-LAST:event_ScienceActionPerformed

    private void SaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveChangesActionPerformed
        // TODO add your handling code here:
        updatedInformation();
    }//GEN-LAST:event_SaveChangesActionPerformed

    private void ChangeUsernamePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeUsernamePasswordActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"user");
        DisplayCurrentUsername.setText(user.getStudentUsername());
        NewUsernameField.setText("");
        EnterPasswordField.setText("");
        NewPasswordField.setText("");
        ConfirmNewPasswordField.setText("");
        currentPasswordField.setText("");
    }//GEN-LAST:event_ChangeUsernamePasswordActionPerformed

    private void MathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MathsActionPerformed
        // TODO add your handling code here       
        
    }//GEN-LAST:event_MathsActionPerformed

    private void EnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnglishActionPerformed
        // TODO add your handling code here:        
    }//GEN-LAST:event_EnglishActionPerformed

    private void MainPageButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButton3ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
    }//GEN-LAST:event_MainPageButton3ActionPerformed

    private void ClassScheduleBut1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheduleBut1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheduleBut1ActionPerformed

    private void PaymentButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentButton3ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
    }//GEN-LAST:event_PaymentButton3ActionPerformed

    private void EditRequestButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditRequestButton3ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_EditRequestButton3ActionPerformed

    private void LOButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButton3ActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButton3ActionPerformed

    private void SubEnrollment3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEnrollment3ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();
    }//GEN-LAST:event_SubEnrollment3ActionPerformed

    private void MainPageButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButton5ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
    }//GEN-LAST:event_MainPageButton5ActionPerformed

    private void ClassScheduleBut3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheduleBut3ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheduleBut3ActionPerformed

    private void ReqButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqButton5ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
    }//GEN-LAST:event_ReqButton5ActionPerformed

    private void UpdateProfButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfButton5ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"edit");
        editRequest();
    }//GEN-LAST:event_UpdateProfButton5ActionPerformed

    private void LOButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButton5ActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButton5ActionPerformed

    private void UpdateProfile2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfile2ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"profile");
        displayUpdateProfileInformation();
    }//GEN-LAST:event_UpdateProfile2ActionPerformed

    private void ConfirmNewPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmNewPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmNewPasswordField1ActionPerformed

    private void currentPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentPasswordField1ActionPerformed

    private void NewPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewPasswordField1ActionPerformed

    private void SavePassword1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavePassword1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SavePassword1ActionPerformed

    private void SavePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SavePasswordActionPerformed
        // TODO add your handling code here:
        changePassword();
    }//GEN-LAST:event_SavePasswordActionPerformed

    private void currentPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentPasswordFieldActionPerformed

    private void ConfirmNewPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmNewPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmNewPasswordFieldActionPerformed

    private void NewPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewPasswordFieldActionPerformed

    private void NewUsernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewUsernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewUsernameFieldActionPerformed

    private void SaveUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveUsernameActionPerformed
        // TODO add your handling code here:
        changeUsername();
    }//GEN-LAST:event_SaveUsernameActionPerformed

    private void EnterPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterPasswordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EnterPasswordFieldActionPerformed

    private void reqMathsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqMathsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqMathsActionPerformed

    private void reqSciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqSciActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqSciActionPerformed

    private void reqEnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqEnglishActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqEnglishActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        subEnrollmentReq();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void MainPageButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainPageButton4ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"main");
    }//GEN-LAST:event_MainPageButton4ActionPerformed

    private void ClassScheButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClassScheButton1ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"schedule");
        showClassSchedule();
    }//GEN-LAST:event_ClassScheButton1ActionPerformed

    private void SubEnrollment4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubEnrollment4ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"payment");
        paymentTablesDisplay();
    }//GEN-LAST:event_SubEnrollment4ActionPerformed

    private void ReqButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReqButton4ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"SubEnrolment");
        subjectEnrollement();  
    }//GEN-LAST:event_ReqButton4ActionPerformed

    private void UpdateProfButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateProfButton4ActionPerformed
        // TODO add your handling code here:
        layout.show(StudentMain,"profile");
        displayUpdateProfileInformation();    
    }//GEN-LAST:event_UpdateProfButton4ActionPerformed

    private void LOButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LOButton4ActionPerformed
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LOButton4ActionPerformed

    private void CancelReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelReqActionPerformed
        // TODO add your handling code here:
        RequestManager manager = new RequestManager();
        int selectedRow = EditReq.getSelectedRow();
        
        EditRequestTableDisplay tableDisplay = new EditRequestTableDisplay();
        DefaultTableModel model = tableDisplay.EditRequestTableDisplay(StudentID);
        EditReq.setModel(model);
       
        if (selectedRow != -1) {
            String Subject = EditReq.getValueAt(selectedRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to cancel this request?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION){
                manager.loadFromFile();
                
                int result = manager.cancelRequest(StudentID, Subject);
                switch(result) {
                    case 1:
                       manager.rewriteFile();
                       JOptionPane.showMessageDialog(null, "Request cancelled successfully.");
                       
                       EditReq.setModel(model);
                       break;
                       
                    case -1: // Already cancelled
                    JOptionPane.showMessageDialog(null, 
                        "This request is already cancelled.", 
                        "Already Cancelled", 
                        JOptionPane.WARNING_MESSAGE);
                        break;
                        
                    case 0: // Cannot be cancelled (not pending)
                    JOptionPane.showMessageDialog(null, 
                        "This request cannot be cancelled as it is no longer pending.", 
                        "Cannot Cancel", 
                        JOptionPane.WARNING_MESSAGE);
                        break;
                    
                    case 2: // Request not found
                        JOptionPane.showMessageDialog(null, "Request not found.");
                        break;      
                }
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Please selecct a row to cancel.");
        }
    }//GEN-LAST:event_CancelReqActionPerformed

    private void ChangePFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePFPActionPerformed
        GeneralMethods.ChangePFP(StudentPFP, UpdateProfile, StudentID);
    }//GEN-LAST:event_ChangePFPActionPerformed

    private void ResetPFPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetPFPActionPerformed
        GeneralMethods.ResetPFP(StudentPFP, StudentID);
        GeneralMethods.LoadPFP(StudentPFP, "Profile Pictures/"+ StudentID +".jpg");
    }//GEN-LAST:event_ResetPFPActionPerformed

    /** 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressField;
    private javax.swing.JLabel AddressLabel;
    private javax.swing.JLabel AgeDisplay;
    private javax.swing.JButton CancelReq;
    private javax.swing.JButton ChangePFP;
    private javax.swing.JLabel ChangePasswordLabel;
    private javax.swing.JLabel ChangeUsernameLabel;
    private javax.swing.JButton ChangeUsernamePassword;
    private javax.swing.JButton ClassScheButton;
    private javax.swing.JButton ClassScheButton1;
    private javax.swing.JPanel ClassSchedule;
    private javax.swing.JButton ClassScheduleBut;
    private javax.swing.JButton ClassScheduleBut1;
    private javax.swing.JButton ClassScheduleBut3;
    private javax.swing.JButton ClassScheduleButtton;
    private javax.swing.JTextField ConfirmNewPasswordField;
    private javax.swing.JTextField ConfirmNewPasswordField1;
    private javax.swing.JLabel ConfirmNewPasswordLabel;
    private javax.swing.JLabel ConfirmNewPasswordLabel1;
    private javax.swing.JLabel CurrentPasswordLabel;
    private javax.swing.JLabel CurrentPasswordLabel1;
    private javax.swing.JLabel CurrentUsername;
    private javax.swing.JLabel DateJoinedDisplay;
    private javax.swing.JLabel DisplayCurrentUsername;
    private javax.swing.JTable EditReq;
    private javax.swing.JPanel EditRequest;
    private javax.swing.JButton EditRequestButton;
    private javax.swing.JButton EditRequestButton3;
    private javax.swing.JTextField EmailField;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JCheckBox English;
    private javax.swing.JTextField EnterPasswordField;
    private javax.swing.JLabel EnteredPassword;
    private javax.swing.JLabel GenderDisplay;
    private javax.swing.JLabel HistoryLabel;
    private javax.swing.JLabel IdentificationDisplay;
    private javax.swing.JLabel IdentificationNoField;
    private javax.swing.JButton LOButton;
    private javax.swing.JButton LOButton1;
    private javax.swing.JButton LOButton2;
    private javax.swing.JButton LOButton3;
    private javax.swing.JButton LOButton4;
    private javax.swing.JButton LOButton5;
    private javax.swing.JLabel LevelDisplay;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel MainPage;
    private javax.swing.JButton MainPageButton;
    private javax.swing.JButton MainPageButton1;
    private javax.swing.JButton MainPageButton2;
    private javax.swing.JButton MainPageButton3;
    private javax.swing.JButton MainPageButton4;
    private javax.swing.JButton MainPageButton5;
    private javax.swing.JCheckBox Maths;
    private javax.swing.JLabel Name;
    private javax.swing.JLabel NameDisplay;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JTextField NewPasswordField;
    private javax.swing.JTextField NewPasswordField1;
    private javax.swing.JLabel NewPasswordLabel;
    private javax.swing.JLabel NewPasswordLabel1;
    private javax.swing.JLabel NewUsername;
    private javax.swing.JTextField NewUsernameField;
    private javax.swing.JButton PayButton;
    private javax.swing.JPanel Payment;
    private javax.swing.JButton PaymentButton;
    private javax.swing.JButton PaymentButton3;
    private javax.swing.JTable PaymentHistory;
    private javax.swing.JTextField PhoneNumberField;
    private javax.swing.JLabel PhoneNumberLabel;
    private javax.swing.JButton ReqButton;
    private javax.swing.JButton ReqButton1;
    private javax.swing.JButton ReqButton2;
    private javax.swing.JButton ReqButton4;
    private javax.swing.JButton ReqButton5;
    private javax.swing.JLabel RequestSubject;
    private javax.swing.JButton ResetPFP;
    private javax.swing.JButton SaveChanges;
    private javax.swing.JButton SavePassword;
    private javax.swing.JButton SavePassword1;
    private javax.swing.JButton SaveUsername;
    private javax.swing.JCheckBox Science;
    private javax.swing.JLabel StudentIDDisplay;
    private javax.swing.JLabel StudentLabel;
    private javax.swing.JLabel StudentLabel1;
    private javax.swing.JLabel StudentLabel2;
    private javax.swing.JLabel StudentLabel3;
    private javax.swing.JPanel StudentMain;
    private AdminFrontend.ImagePanel StudentPFP;
    private javax.swing.JButton SubEnrollment;
    private javax.swing.JButton SubEnrollment1;
    private javax.swing.JButton SubEnrollment3;
    private javax.swing.JButton SubEnrollment4;
    private javax.swing.JButton SubEnrolment2;
    private javax.swing.JLabel SubjectEnrolled;
    private javax.swing.JTable SubjectEnrolledTable;
    private javax.swing.JButton SubjectEnrollmentButton;
    private javax.swing.JPanel SubjectEnrolment;
    private javax.swing.JDesktopPane Summary;
    private javax.swing.JLabel SummaryLabel;
    private javax.swing.JScrollPane Tabel23;
    private javax.swing.JTable Table2021;
    private javax.swing.JTable Table2022;
    private javax.swing.JTable Table2023;
    private javax.swing.JTable Table2024;
    private javax.swing.JTable Table2025;
    private javax.swing.JScrollPane Table21;
    private javax.swing.JScrollPane Table22;
    private javax.swing.JScrollPane Table24;
    private javax.swing.JScrollPane Table25;
    private javax.swing.JTable Timetable;
    private javax.swing.JButton UpdateProfButton;
    private javax.swing.JButton UpdateProfButton1;
    private javax.swing.JButton UpdateProfButton2;
    private javax.swing.JButton UpdateProfButton4;
    private javax.swing.JButton UpdateProfButton5;
    private javax.swing.JPanel UpdateProfile;
    private javax.swing.JButton UpdateProfile2;
    private javax.swing.JButton UpdateProfileButton;
    private javax.swing.JPanel UsernamePassword;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JTextField currentPasswordField;
    private javax.swing.JTextField currentPasswordField1;
    private javax.swing.JLabel dateJoinedLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel formLabel1;
    private javax.swing.JLabel formLabel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane10;
    private javax.swing.JDesktopPane jDesktopPane4;
    private javax.swing.JDesktopPane jDesktopPane5;
    private javax.swing.JDesktopPane jDesktopPane6;
    private javax.swing.JDesktopPane jDesktopPane7;
    private javax.swing.JDesktopPane jDesktopPane8;
    private javax.swing.JDesktopPane jDesktopPane9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox reqEnglish;
    private javax.swing.JCheckBox reqMaths;
    private javax.swing.JCheckBox reqSci;
    // End of variables declaration//GEN-END:variables
}
