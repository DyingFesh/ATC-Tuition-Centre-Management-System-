/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AdminFrontend;

import AdminBackend.GeneralMethods;
import AdminBackend.StudentMethods;
import AdminBackend.TutorMethods;
import AdminModels.Student;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class AStudentRegisterPanel extends javax.swing.JPanel {
    
    private final AdminFrame parent;
    
    /**
     * Creates new form AStudentRegisterPanel
     */
    public AStudentRegisterPanel(AdminFrame parent) {
        this.parent = parent;
        initComponents();
        ResetStudentRegister();   
        LevelEditField.setText("Form 1");
        GeneralMethods.LoadPFP(NewStudentPFP, "Profile Pictures/Default.jpg");
        
        NameField.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        ICField.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        PHField.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        EmailField.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        AddressField.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));

        EnglishField.addActionListener(e -> CheckForEmptyFields());
        MathsField.addActionListener(e -> CheckForEmptyFields());
        ScienceField.addActionListener(e -> CheckForEmptyFields());
        
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) EnglishTutorField.getModel(), LevelEditField.getText(), "English");
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) MathsTutorField.getModel(), LevelEditField.getText(), "Mathematics");
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) ScienceTutorField.getModel(), LevelEditField.getText(), "Science");
        
        
    }
    
    private void ResetStudentRegister() {
        String newstudentid = StudentMethods.GetNewStudentID();
        int newstudentidnum = Integer.parseInt(newstudentid.substring(1));
        String newusername = "student" + newstudentidnum;
        String newpassword = "spassword" + newstudentidnum;
        String newenroldate = GeneralMethods.GetCurrentMonthYear();
        
        NewStudentIDLabel.setText(newstudentid);
        TempUNLabel.setText(newusername);
        TempPWLabel.setText(newpassword);
        CPEnrolLabel2.setText(newenroldate);
        
        NameField.setText("");
        AgeEditField.setValue(13);
        GenderField.setSelectedIndex(0);
        ICField.setText("");
        AddressField.setText("");
        PHField.setText("");
        EmailField.setText("");
        LevelEditField.setText("Form 1");
        EnglishField.setSelected(false);
        MathsField.setSelected(false);
        ScienceField.setSelected(false);
        EnglishTutorField.setEnabled(false);
        MathsTutorField.setEnabled(false);
        ScienceTutorField.setEnabled(false);
    }
    
    private boolean ValidateAllInputs() {
        // Validate Name
        String name = NameField.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
            return false;
        }

        // Validate IC
        String ic = ICField.getText().trim();
        if (ic.isEmpty() || ic.length() != 12 || !ic.matches("\\d+")) {
            return false;
        }

        // Validate Phone Number
        String ph = PHField.getText().trim();
        if (ph.isEmpty() || ph.length() != 9 || !ph.matches("\\d+")) {
            return false;
        }

        // Validate Email
        String email = EmailField.getText().trim();
        if (email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            return false;
        }

        // Validate Address
        String address = AddressField.getText().trim();
        if (address.isEmpty() || !address.matches("[a-zA-Z0-9 ]+")) {
            return false;
        }

        // Validate at least one subject is selected
        if (!EnglishField.isSelected() && !MathsField.isSelected() && !ScienceField.isSelected()) {
            return false;
        }

        return true; // All validations passed
    }
    
    private void CheckForEmptyFields() {
        boolean valid =
        !NameField.getText().trim().isEmpty() &&
        !ICField.getText().trim().isEmpty() &&
        !PHField.getText().trim().isEmpty() &&
        !EmailField.getText().trim().isEmpty() &&
        !AddressField.getText().trim().isEmpty() &&
        (EnglishField.isSelected() || MathsField.isSelected() || ScienceField.isSelected());

        RegisterButton.setEnabled(valid);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RegisterStudentPage = new javax.swing.JPanel();
        StudentRegisterBackButton = new javax.swing.JButton();
        RegisterButton = new javax.swing.JButton();
        StudentInfoLabel2 = new javax.swing.JPanel();
        PersonalContactInfoLabel = new javax.swing.JLabel();
        PNameLabel = new javax.swing.JLabel();
        PGenderLabel2 = new javax.swing.JLabel();
        PICLabel2 = new javax.swing.JLabel();
        PPHLabel2 = new javax.swing.JLabel();
        PHFormatLabel = new javax.swing.JLabel();
        PEmailLabel2 = new javax.swing.JLabel();
        PAddressLabel2 = new javax.swing.JLabel();
        NameField = new javax.swing.JTextField();
        AgeEditField = new javax.swing.JSpinner();
        ICField = new javax.swing.JTextField();
        GenderField = new javax.swing.JComboBox<>();
        PHField = new javax.swing.JTextField();
        EmailField = new javax.swing.JTextField();
        AddressField = new javax.swing.JTextField();
        NewStudentPFP = new AdminFrontend.ImagePanel();
        StudentIdNamePanel = new javax.swing.JPanel();
        PStudentIDLabel = new javax.swing.JLabel();
        PStudentNameLabel = new javax.swing.JLabel();
        PStudentUsernameLabel = new javax.swing.JLabel();
        NewStudentIDLabel = new javax.swing.JLabel();
        TempUNLabel = new javax.swing.JLabel();
        TempPWLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        PAgeLabel3 = new javax.swing.JLabel();
        StudentInfoLabel = new javax.swing.JLabel();
        PLevelLabel2 = new javax.swing.JLabel();
        PEnrolLabel2 = new javax.swing.JLabel();
        CPEnrolLabel2 = new javax.swing.JLabel();
        LevelEditField = new javax.swing.JLabel();
        EnglishField = new javax.swing.JRadioButton();
        MathsField = new javax.swing.JRadioButton();
        ScienceField = new javax.swing.JRadioButton();
        EnglishTutorField = new javax.swing.JComboBox<>();
        ScienceTutorField = new javax.swing.JComboBox<>();
        MathsTutorField = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        PSubjectLabel2 = new javax.swing.JLabel();
        PSubjectLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        StudentManagementTitle = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(930, 650));
        setMinimumSize(new java.awt.Dimension(930, 650));
        setPreferredSize(new java.awt.Dimension(930, 650));

        RegisterStudentPage.setMaximumSize(new java.awt.Dimension(930, 650));
        RegisterStudentPage.setMinimumSize(new java.awt.Dimension(930, 650));
        RegisterStudentPage.setPreferredSize(new java.awt.Dimension(930, 650));
        RegisterStudentPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentRegisterBackButton.setText("Back");
        StudentRegisterBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentRegisterBackButtonActionPerformed(evt);
            }
        });
        RegisterStudentPage.add(StudentRegisterBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 565, 70, 35));

        RegisterButton.setText("Register");
        RegisterButton.setEnabled(false);
        RegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterButtonActionPerformed(evt);
            }
        });
        RegisterStudentPage.add(RegisterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 565, 80, 35));

        StudentInfoLabel2.setMaximumSize(new java.awt.Dimension(10, 10));
        StudentInfoLabel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PersonalContactInfoLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        PersonalContactInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PersonalContactInfoLabel.setText("Personal & Contact Information");
        PersonalContactInfoLabel.setToolTipText("");
        PersonalContactInfoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PersonalContactInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, -1, 40));

        PNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PNameLabel.setText("Name:");
        PNameLabel.setToolTipText("");
        PNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 30));

        PGenderLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PGenderLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PGenderLabel2.setText("IC:");
        PGenderLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PGenderLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, -1, 30));

        PICLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PICLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PICLabel2.setText("Gender:");
        PICLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PICLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, -1, 30));

        PPHLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PPHLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PPHLabel2.setText("Phone Number:");
        PPHLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PPHLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, 30));

        PHFormatLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PHFormatLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PHFormatLabel.setText("+60 ");
        PHFormatLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PHFormatLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 30, 30));

        PEmailLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PEmailLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PEmailLabel2.setText("Email Address:");
        PEmailLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PEmailLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, -1, 30));

        PAddressLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PAddressLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PAddressLabel2.setText("Address:");
        PAddressLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PAddressLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, -1, 30));

        NameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                NameFieldFocusLost(evt);
            }
        });
        NameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(NameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 330, 30));

        AgeEditField.setModel(new javax.swing.SpinnerNumberModel(13, 13, 17, 1));
        AgeEditField.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AgeEditFieldStateChanged(evt);
            }
        });
        StudentInfoLabel2.add(AgeEditField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 100, 30));

        ICField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ICFieldFocusLost(evt);
            }
        });
        StudentInfoLabel2.add(ICField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 330, 30));

        GenderField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        GenderField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenderFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(GenderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 100, 30));

        PHField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                PHFieldFocusLost(evt);
            }
        });
        StudentInfoLabel2.add(PHField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 300, 30));

        EmailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmailFieldFocusLost(evt);
            }
        });
        EmailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(EmailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 330, 30));

        AddressField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                AddressFieldFocusLost(evt);
            }
        });
        AddressField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddressFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(AddressField, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 330, 30));

        javax.swing.GroupLayout NewStudentPFPLayout = new javax.swing.GroupLayout(NewStudentPFP);
        NewStudentPFP.setLayout(NewStudentPFPLayout);
        NewStudentPFPLayout.setHorizontalGroup(
            NewStudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        NewStudentPFPLayout.setVerticalGroup(
            NewStudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        StudentInfoLabel2.add(NewStudentPFP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 100, 100));

        StudentIdNamePanel.setMaximumSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel.setMinimumSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel.setPreferredSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PStudentIDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PStudentIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentIDLabel.setText("Student ID:");
        PStudentIDLabel.setToolTipText("");
        PStudentIDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(PStudentIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 160, -1));

        PStudentNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PStudentNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentNameLabel.setText("Temporary Username:");
        PStudentNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(PStudentNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 160, -1));

        PStudentUsernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PStudentUsernameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentUsernameLabel.setText("Temporary Password:");
        PStudentUsernameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(PStudentUsernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 160, -1));

        NewStudentIDLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NewStudentIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        NewStudentIDLabel.setText("Student ID");
        NewStudentIDLabel.setToolTipText("");
        NewStudentIDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(NewStudentIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 110, -1));

        TempUNLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TempUNLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TempUNLabel.setText("Username");
        TempUNLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(TempUNLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 110, -1));

        TempPWLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TempPWLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TempPWLabel.setText("Password");
        TempPWLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel.add(TempPWLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 110, -1));

        StudentInfoLabel2.add(StudentIdNamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 6, 320, 100));
        StudentInfoLabel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 830, 10));

        PAgeLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PAgeLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PAgeLabel3.setText("Age:");
        PAgeLabel3.setToolTipText("");
        PAgeLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PAgeLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, 30));

        StudentInfoLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        StudentInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel.setText("Student Information");
        StudentInfoLabel.setToolTipText("");
        StudentInfoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(StudentInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, -1, 40));

        PLevelLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PLevelLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PLevelLabel2.setText("Level: ");
        PLevelLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PLevelLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, -1, 30));

        PEnrolLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PEnrolLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PEnrolLabel2.setText("Enrolment: ");
        PEnrolLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PEnrolLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 225, -1, 30));

        CPEnrolLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CPEnrolLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CPEnrolLabel2.setText("MMMM yyyy");
        CPEnrolLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(CPEnrolLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 225, 150, 30));

        LevelEditField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LevelEditField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LevelEditField.setText("Form X");
        LevelEditField.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(LevelEditField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 150, 30));

        EnglishField.setText(" English");
        EnglishField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnglishFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(EnglishField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, 100, 30));

        MathsField.setText("Mathematics");
        MathsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MathsFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(MathsField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 355, 100, 30));

        ScienceField.setText(" Science");
        ScienceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScienceFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(ScienceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 100, 30));

        EnglishTutorField.setEnabled(false);
        StudentInfoLabel2.add(EnglishTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 320, 160, 30));

        ScienceTutorField.setEnabled(false);
        ScienceTutorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScienceTutorFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel2.add(ScienceTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 390, 160, 30));

        MathsTutorField.setEnabled(false);
        StudentInfoLabel2.add(MathsTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 355, 160, 30));
        StudentInfoLabel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 290, 10));

        PSubjectLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PSubjectLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PSubjectLabel2.setText("Subjects");
        PSubjectLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PSubjectLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 275, -1, 30));

        PSubjectLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PSubjectLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PSubjectLabel4.setText("Tutor");
        PSubjectLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel2.add(PSubjectLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 275, 80, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        StudentInfoLabel2.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 290, 160));

        RegisterStudentPage.add(StudentInfoLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 830, 480));

        jPanel3.setBackground(new java.awt.Color(184, 186, 191));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        StudentManagementTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        StudentManagementTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StudentManagementTitle.setText("Student Registration");
        StudentManagementTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StudentManagementTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(StudentManagementTitle)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        RegisterStudentPage.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RegisterStudentPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RegisterStudentPage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void StudentRegisterBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentRegisterBackButtonActionPerformed
        parent.switchPanel("student");
    }//GEN-LAST:event_StudentRegisterBackButtonActionPerformed

    private void EmailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailFieldActionPerformed

    private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterButtonActionPerformed
        if (!ValidateAllInputs()) return;
        
        String newstudentid = NewStudentIDLabel.getText();
        String newstudentname = GeneralMethods.Capitalize(NameField.getText());
        int newstudentage = (Integer) AgeEditField.getValue();
        String newstudentgender = (String) GenderField.getSelectedItem();
        String newstudentic = ICField.getText();
        String newstudentph = "+60" + PHField.getText();
        String newstudentemail = EmailField.getText();
        String newstudentaddress = GeneralMethods.Capitalize(AddressField.getText());
        String newstudentlevel = LevelEditField.getText();
        String newstudentenrol = CPEnrolLabel2.getText();
        String newengtutor = null;
        String newmathstutor = null;
        String newscitutor = null;
        String newengtutorid = null;
        String newmathstutorid = null;
        String newscitutorid = null;

        List<String> newstudentsubjectstemp = new ArrayList<>();
        if (EnglishField.isSelected()) {
            newstudentsubjectstemp.add("English");
            newengtutor = EnglishTutorField.getSelectedItem().toString();
            newengtutorid = TutorMethods.GetTutorIDFromName(newengtutor);
        }
        if (MathsField.isSelected()) {
            newstudentsubjectstemp.add("Mathematics");
            newmathstutor = MathsTutorField.getSelectedItem().toString();
            newmathstutorid = TutorMethods.GetTutorIDFromName(newmathstutor);
        }
        if (ScienceField.isSelected()) {
            newstudentsubjectstemp.add("Science");
            newscitutor = ScienceTutorField.getSelectedItem().toString();
            newscitutorid = TutorMethods.GetTutorIDFromName(newscitutor);
        }
        
        if (StudentMethods.ValidateStudent(newstudentid, newstudentname, newstudentic, newstudentph, newstudentemail)) {
            String newstudentsubjects = String.join(";", newstudentsubjectstemp);

            Student newstudent = new Student(newstudentid, newstudentname, newstudentage, newstudentgender, newstudentic, newstudentph, newstudentemail, newstudentaddress, newstudentlevel, newstudentsubjects, newstudentenrol);

            StudentMethods.SaveNewStudent(newstudent);
            StudentMethods.SaveNewAssignedTutors(newstudentid, newengtutor, newmathstutor, newscitutor);
            GeneralMethods.SaveNewLogin(newstudentid, TempUNLabel.getText(), TempPWLabel.getText());

            if (newengtutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "English", newengtutorid);
            }
            if (newmathstutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "Mathematics", newmathstutorid);
            }
            if (newscitutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "Science", newscitutorid);
            }

            JOptionPane.showMessageDialog(null, "Student successfully registered!");

            parent.switchPanel("student");
            ResetStudentRegister();
        }

    }//GEN-LAST:event_RegisterButtonActionPerformed

    private void NameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameFieldActionPerformed

    }//GEN-LAST:event_NameFieldActionPerformed

    private void EnglishFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnglishFieldActionPerformed
        if (EnglishField.isSelected()) {
            EnglishTutorField.setEnabled(true);
        } else {
            EnglishTutorField.setEnabled(false);
        }
    }//GEN-LAST:event_EnglishFieldActionPerformed

    private void MathsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MathsFieldActionPerformed
        if (MathsField.isSelected()) {
            MathsTutorField.setEnabled(true);
        } else {
            MathsTutorField.setEnabled(false);
        }
    }//GEN-LAST:event_MathsFieldActionPerformed

    private void ScienceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScienceFieldActionPerformed
        if (ScienceField.isSelected()) {
            ScienceTutorField.setEnabled(true);
        } else {
            ScienceTutorField.setEnabled(false);
        }
    }//GEN-LAST:event_ScienceFieldActionPerformed

    private void GenderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenderFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenderFieldActionPerformed

    private void AgeEditFieldStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AgeEditFieldStateChanged
        LevelEditField.setText(StudentMethods.GetStudentLevel((Integer) AgeEditField.getValue()));
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) EnglishTutorField.getModel(), LevelEditField.getText(), "English");
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) MathsTutorField.getModel(), LevelEditField.getText(), "Mathematics");
        StudentMethods.PopulateTutorList((DefaultComboBoxModel) ScienceTutorField.getModel(), LevelEditField.getText(), "Science");
    }//GEN-LAST:event_AgeEditFieldStateChanged

    private void NameFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameFieldFocusLost
        String field = NameField.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(NameField, null, 1, Integer.MAX_VALUE, "[a-zA-Z ]+", "Invalid name entered.\nPlease make sure input only contains letters.");
    }//GEN-LAST:event_NameFieldFocusLost

    private void ICFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICFieldFocusLost
        String field = ICField.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(ICField, null, 12, 12, "\\d+", "Invalid IC entered.\nPlease make sure input only contains digits and is exactly 12 characters long.");
    }//GEN-LAST:event_ICFieldFocusLost

    private void PHFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PHFieldFocusLost
        String field = PHField.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(PHField, null, 9, 9, "\\d+", "Invalid phone number entered.\nPlease make sure input only contains digits and is exactly 9 characters long excluding the country code (+60).");
    }//GEN-LAST:event_PHFieldFocusLost

    private void EmailFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailFieldFocusLost
        String field = EmailField.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(EmailField, null, 1, Integer.MAX_VALUE, "^[\\w.-]+@[\\w.-]+\\.\\w+$", "Invalid email address entered.\nPlease make sure input does not contain any illegal characters and includes the email domain (Example: @gmail.com).");
    }//GEN-LAST:event_EmailFieldFocusLost

    private void AddressFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddressFieldFocusLost
        String field = AddressField.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(AddressField, null, 1, Integer.MAX_VALUE, "[a-zA-Z0-9 ]+", "Invalid address entered.\nPlease make sure input does not contain any illegal characters.");
    }//GEN-LAST:event_AddressFieldFocusLost

    private void AddressFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddressFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressFieldActionPerformed

    private void ScienceTutorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScienceTutorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ScienceTutorFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AddressField;
    private javax.swing.JSpinner AgeEditField;
    private javax.swing.JLabel CPEnrolLabel2;
    private javax.swing.JTextField EmailField;
    private javax.swing.JRadioButton EnglishField;
    private javax.swing.JComboBox<String> EnglishTutorField;
    private javax.swing.JComboBox<String> GenderField;
    private javax.swing.JTextField ICField;
    private javax.swing.JLabel LevelEditField;
    private javax.swing.JRadioButton MathsField;
    private javax.swing.JComboBox<String> MathsTutorField;
    private javax.swing.JTextField NameField;
    private javax.swing.JLabel NewStudentIDLabel;
    private AdminFrontend.ImagePanel NewStudentPFP;
    private javax.swing.JLabel PAddressLabel2;
    private javax.swing.JLabel PAgeLabel3;
    private javax.swing.JLabel PEmailLabel2;
    private javax.swing.JLabel PEnrolLabel2;
    private javax.swing.JLabel PGenderLabel2;
    private javax.swing.JTextField PHField;
    private javax.swing.JLabel PHFormatLabel;
    private javax.swing.JLabel PICLabel2;
    private javax.swing.JLabel PLevelLabel2;
    private javax.swing.JLabel PNameLabel;
    private javax.swing.JLabel PPHLabel2;
    private javax.swing.JLabel PStudentIDLabel;
    private javax.swing.JLabel PStudentNameLabel;
    private javax.swing.JLabel PStudentUsernameLabel;
    private javax.swing.JLabel PSubjectLabel2;
    private javax.swing.JLabel PSubjectLabel4;
    private javax.swing.JLabel PersonalContactInfoLabel;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JPanel RegisterStudentPage;
    private javax.swing.JRadioButton ScienceField;
    private javax.swing.JComboBox<String> ScienceTutorField;
    private javax.swing.JPanel StudentIdNamePanel;
    private javax.swing.JLabel StudentInfoLabel;
    private javax.swing.JPanel StudentInfoLabel2;
    private javax.swing.JLabel StudentManagementTitle;
    private javax.swing.JButton StudentRegisterBackButton;
    private javax.swing.JLabel TempPWLabel;
    private javax.swing.JLabel TempUNLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
