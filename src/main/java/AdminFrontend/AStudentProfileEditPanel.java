/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AdminFrontend;

import AdminBackend.GeneralMethods;
import AdminBackend.StudentMethods;
import AdminBackend.TutorMethods;
import AdminModels.Student;
import AdminModels.Tutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class AStudentProfileEditPanel extends javax.swing.JPanel {
    
    private final AdminFrame parent;
    Student selectedstudent;
    List<String> selectedlogin;
    
    /**
     * Creates new form AStudentProfileEditPanel
     */
    public AStudentProfileEditPanel(AdminFrame parent, Student selectedstudent, List<String> selectedlogin) {
        this.parent = parent;
        this.selectedstudent = selectedstudent;
        this.selectedlogin = selectedlogin;
        initComponents();
        GeneralMethods.LoadPFP(StudentPFP, "Profile Pictures/" + selectedstudent.getStudentid() + ".jpg");
        
        
        NameField1.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        ICField1.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        PHField1.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        EmailField1.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));
        AddressField1.getDocument().addDocumentListener(GeneralMethods.MakeSimpleDocumentListener(() -> CheckForEmptyFields()));

        EnglishField.addActionListener(e -> CheckForEmptyFields());
        MathsField.addActionListener(e -> CheckForEmptyFields());
        ScienceField.addActionListener(e -> CheckForEmptyFields());
        
        if (selectedstudent != null) {
            CPStudentIDLabel.setText(selectedstudent.getStudentid());
            NameField1.setText(selectedstudent.getStudentname());
            CPStudentUsernameLabel1.setText(selectedlogin.get(0));
            AgeField1.setValue(selectedstudent.getStudentage());
            ICField1.setText(selectedstudent.getStudentic());
            GenderField1.setSelectedItem(GeneralMethods.Capitalize(selectedstudent.getStudentgender()));
            PHField1.setText(selectedstudent.getStudentph().substring(3));
            EmailField1.setText(selectedstudent.getStudentemail());
            AddressField1.setText(selectedstudent.getStudentaddress());
            LevelEditField.setText(selectedstudent.getStudentlevel());
            CPEnrolLabel2.setText(selectedstudent.getStudentenrol());
            
            StudentMethods.PopulateTutorList((DefaultComboBoxModel) EnglishTutorField.getModel(), LevelEditField.getText(), "English");
            StudentMethods.PopulateTutorList((DefaultComboBoxModel) MathsTutorField.getModel(), LevelEditField.getText(), "Mathematics");
            StudentMethods.PopulateTutorList((DefaultComboBoxModel) ScienceTutorField.getModel(), LevelEditField.getText(), "Science");

            String[] studentsubjects = selectedstudent.getStudentsubjects().split(";");
            
            if (Arrays.asList(studentsubjects).contains("English")) {
                EnglishField.setSelected(true);
            } else {
                EnglishField.setSelected(false);
            }
            
            if (Arrays.asList(studentsubjects).contains("Mathematics")) {
                MathsField.setSelected(true);
            } else {
                MathsField.setSelected(false);
            }            
        
            if (Arrays.asList(studentsubjects).contains("Science")) {
                ScienceField.setSelected(true);
            } else {
                ScienceField.setSelected(false);
            }
            
            if (EnglishField.isSelected()) EnglishTutorField.setEnabled(true);
            if (MathsField.isSelected()) MathsTutorField.setEnabled(true);
            if (ScienceField.isSelected()) ScienceTutorField.setEnabled(true);
            
            List<String> assignedtutors = StudentMethods.GetAssignedTutors(selectedstudent.getStudentid());
            String englishtutor = assignedtutors.get(0);
            String mathstutor = assignedtutors.get(1);
            String sciencetutor = assignedtutors.get(2);

            if (englishtutor != null && !englishtutor.equals("null") && !englishtutor.equals("")) {
                Tutor tutor = TutorMethods.GetTutorByID(englishtutor);
                String tutorname = tutor.getTutorname();
                EnglishTutorField.setSelectedItem(tutorname);
            }
            if (mathstutor != null && !mathstutor.equals("null") && !mathstutor.equals("")) {
                Tutor tutor = TutorMethods.GetTutorByID(mathstutor);
                String tutorname = tutor.getTutorname();
                MathsTutorField.setSelectedItem(tutorname);
            }
            if (sciencetutor != null && !sciencetutor.equals("null") && !sciencetutor.equals("")) {
                Tutor tutor = TutorMethods.GetTutorByID(sciencetutor);
                String tutorname = tutor.getTutorname();
                ScienceTutorField.setSelectedItem(tutorname);
            }
        }
    }
    
    private boolean ValidateAllInputs() {
        // Validate Name
        String name = NameField1.getText().trim();
        if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
            return false;
        }

        // Validate IC
        String ic = ICField1.getText().trim();
        if (ic.isEmpty() || ic.length() != 12 || !ic.matches("\\d+")) {
            return false;
        }

        // Validate Phone Number
        String ph = PHField1.getText().trim();
        if (ph.isEmpty() || ph.length() != 9 || !ph.matches("\\d+")) {
            return false;
        }

        // Validate Email
        String email = EmailField1.getText().trim();
        if (email.isEmpty() || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            return false;
        }

        // Validate Address
        String address = AddressField1.getText().trim();
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
        !NameField1.getText().trim().isEmpty() &&
        !ICField1.getText().trim().isEmpty() &&
        !PHField1.getText().trim().isEmpty() &&
        !EmailField1.getText().trim().isEmpty() &&
        !AddressField1.getText().trim().isEmpty() &&
        (EnglishField.isSelected() || MathsField.isSelected() || ScienceField.isSelected());

        SaveButton.setEnabled(valid);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AStudentProfileEditPanel = new javax.swing.JPanel();
        StudentProfileBackButton5 = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();
        StudentInfoLabel3 = new javax.swing.JPanel();
        PPersonalInfoLabel4 = new javax.swing.JLabel();
        PNameLabel1 = new javax.swing.JLabel();
        PGenderLabel3 = new javax.swing.JLabel();
        PICLabel3 = new javax.swing.JLabel();
        PPHLabel3 = new javax.swing.JLabel();
        PHFormatLabel1 = new javax.swing.JLabel();
        PEmailLabel3 = new javax.swing.JLabel();
        PAddressLabel3 = new javax.swing.JLabel();
        NameField1 = new javax.swing.JTextField();
        AgeField1 = new javax.swing.JSpinner();
        ICField1 = new javax.swing.JTextField();
        GenderField1 = new javax.swing.JComboBox<>();
        PHField1 = new javax.swing.JTextField();
        EmailField1 = new javax.swing.JTextField();
        AddressField1 = new javax.swing.JTextField();
        StudentIdNamePanel1 = new javax.swing.JPanel();
        PStudentIDLabel1 = new javax.swing.JLabel();
        PStudentNameLabel1 = new javax.swing.JLabel();
        CPStudentIDLabel = new javax.swing.JLabel();
        CPStudentUsernameLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        PAgeLabel4 = new javax.swing.JLabel();
        PStudentInfoLabel3 = new javax.swing.JLabel();
        StudentPFP = new AdminFrontend.ImagePanel();
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

        AStudentProfileEditPanel.setMaximumSize(new java.awt.Dimension(930, 650));
        AStudentProfileEditPanel.setMinimumSize(new java.awt.Dimension(930, 650));
        AStudentProfileEditPanel.setPreferredSize(new java.awt.Dimension(930, 650));
        AStudentProfileEditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentProfileBackButton5.setText("Back");
        StudentProfileBackButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentProfileBackButton5ActionPerformed(evt);
            }
        });
        AStudentProfileEditPanel.add(StudentProfileBackButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 565, 70, 35));

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });
        AStudentProfileEditPanel.add(SaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 565, 80, 35));

        StudentInfoLabel3.setMaximumSize(new java.awt.Dimension(10, 10));
        StudentInfoLabel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PPersonalInfoLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        PPersonalInfoLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PPersonalInfoLabel4.setText("Personal Information");
        PPersonalInfoLabel4.setToolTipText("");
        PPersonalInfoLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PPersonalInfoLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, -1, 40));

        PNameLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PNameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PNameLabel1.setText("Name:");
        PNameLabel1.setToolTipText("");
        PNameLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PNameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, -1, 30));

        PGenderLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PGenderLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PGenderLabel3.setText("IC:");
        PGenderLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PGenderLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, -1, 30));

        PICLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PICLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PICLabel3.setText("Gender:");
        PICLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PICLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, -1, 30));

        PPHLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PPHLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PPHLabel3.setText("Phone Number:");
        PPHLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PPHLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, 30));

        PHFormatLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PHFormatLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PHFormatLabel1.setText("+60 ");
        PHFormatLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PHFormatLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 30, 30));

        PEmailLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PEmailLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PEmailLabel3.setText("Email Address:");
        PEmailLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PEmailLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, -1, 30));

        PAddressLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PAddressLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PAddressLabel3.setText("Address:");
        PAddressLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PAddressLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, -1, 30));

        NameField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                NameField1FocusLost(evt);
            }
        });
        NameField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameField1ActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(NameField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 330, 30));

        AgeField1.setModel(new javax.swing.SpinnerNumberModel(13, 13, 17, 1));
        AgeField1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AgeField1StateChanged(evt);
            }
        });
        StudentInfoLabel3.add(AgeField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 100, 30));

        ICField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ICField1FocusLost(evt);
            }
        });
        StudentInfoLabel3.add(ICField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 330, 30));

        GenderField1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        GenderField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenderField1ActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(GenderField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 100, 30));

        PHField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                PHField1FocusLost(evt);
            }
        });
        StudentInfoLabel3.add(PHField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 300, 30));

        EmailField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                EmailField1FocusLost(evt);
            }
        });
        EmailField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailField1ActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(EmailField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 330, 30));

        AddressField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                AddressField1FocusLost(evt);
            }
        });
        StudentInfoLabel3.add(AddressField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, 330, 30));

        StudentIdNamePanel1.setMaximumSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel1.setMinimumSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel1.setPreferredSize(new java.awt.Dimension(200, 80));
        StudentIdNamePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PStudentIDLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PStudentIDLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentIDLabel1.setText("Student ID:");
        PStudentIDLabel1.setToolTipText("");
        PStudentIDLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel1.add(PStudentIDLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 25, 160, -1));

        PStudentNameLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PStudentNameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentNameLabel1.setText("Username:");
        PStudentNameLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel1.add(PStudentNameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 55, 160, -1));

        CPStudentIDLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CPStudentIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CPStudentIDLabel.setText("Student ID");
        CPStudentIDLabel.setToolTipText("");
        CPStudentIDLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel1.add(CPStudentIDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 25, 110, -1));

        CPStudentUsernameLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CPStudentUsernameLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CPStudentUsernameLabel1.setText("Username");
        CPStudentUsernameLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentIdNamePanel1.add(CPStudentUsernameLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 55, 110, -1));

        StudentInfoLabel3.add(StudentIdNamePanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 6, 320, 100));
        StudentInfoLabel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 830, 10));

        PAgeLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PAgeLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PAgeLabel4.setText("Age:");
        PAgeLabel4.setToolTipText("");
        PAgeLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PAgeLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, 30));

        PStudentInfoLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        PStudentInfoLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PStudentInfoLabel3.setText("Student Information");
        PStudentInfoLabel3.setToolTipText("");
        PStudentInfoLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PStudentInfoLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, -1, 40));

        javax.swing.GroupLayout StudentPFPLayout = new javax.swing.GroupLayout(StudentPFP);
        StudentPFP.setLayout(StudentPFPLayout);
        StudentPFPLayout.setHorizontalGroup(
            StudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        StudentPFPLayout.setVerticalGroup(
            StudentPFPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        StudentInfoLabel3.add(StudentPFP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 100, 100));

        PLevelLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PLevelLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PLevelLabel2.setText("Level: ");
        PLevelLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PLevelLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, -1, 30));

        PEnrolLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PEnrolLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PEnrolLabel2.setText("Enrolment: ");
        PEnrolLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PEnrolLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 225, -1, 30));

        CPEnrolLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        CPEnrolLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        CPEnrolLabel2.setText("MMMM yyyy");
        CPEnrolLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(CPEnrolLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 225, 150, 30));

        LevelEditField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LevelEditField.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LevelEditField.setText("Form X");
        LevelEditField.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(LevelEditField, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 150, 30));

        EnglishField.setText(" English");
        EnglishField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnglishFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(EnglishField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, 100, 30));

        MathsField.setText("Mathematics");
        MathsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MathsFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(MathsField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 355, 100, 30));

        ScienceField.setText(" Science");
        ScienceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScienceFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(ScienceField, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 100, 30));

        EnglishTutorField.setEnabled(false);
        StudentInfoLabel3.add(EnglishTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 320, 160, 30));

        ScienceTutorField.setEnabled(false);
        ScienceTutorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScienceTutorFieldActionPerformed(evt);
            }
        });
        StudentInfoLabel3.add(ScienceTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 390, 160, 30));

        MathsTutorField.setEnabled(false);
        StudentInfoLabel3.add(MathsTutorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 355, 160, 30));
        StudentInfoLabel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, 290, 10));

        PSubjectLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PSubjectLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PSubjectLabel2.setText("Subjects");
        PSubjectLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PSubjectLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 275, -1, 30));

        PSubjectLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PSubjectLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PSubjectLabel4.setText("Tutor");
        PSubjectLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        StudentInfoLabel3.add(PSubjectLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 275, 80, 30));

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

        StudentInfoLabel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 290, 160));

        AStudentProfileEditPanel.add(StudentInfoLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 830, 460));

        jPanel3.setBackground(new java.awt.Color(184, 186, 191));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        StudentManagementTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        StudentManagementTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StudentManagementTitle.setText("Edit Student Profile");
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

        AStudentProfileEditPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AStudentProfileEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AStudentProfileEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void StudentProfileBackButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentProfileBackButton5ActionPerformed
        parent.switchPanelParameters("sprofile", selectedstudent, selectedlogin);
    }//GEN-LAST:event_StudentProfileBackButton5ActionPerformed

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        if (!ValidateAllInputs()) return;
        
        String newstudentid = CPStudentIDLabel.getText();
        String newstudentname = GeneralMethods.Capitalize(NameField1.getText());
        int newstudentage = (Integer) AgeField1.getValue();
        String newstudentgender = (String) GenderField1.getSelectedItem();
        String newstudentic = ICField1.getText();
        String newstudentph = "+60" + PHField1.getText();
        String newstudentemail = EmailField1.getText();
        String newstudentaddress = GeneralMethods.Capitalize(AddressField1.getText());
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

            if (newengtutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "English", newengtutorid);
            } else {
                StudentMethods.RemoveSubjectEnrollment(newstudent, "English");
            }
            if (newmathstutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "Mathematics", newmathstutorid);
            } else {
                StudentMethods.RemoveSubjectEnrollment(newstudent, "Mathematics");
            }
            if (newscitutor != null) {
                StudentMethods.SaveSubjectEnrollment(newstudent, "Science", newscitutorid);
            } else {
                StudentMethods.RemoveSubjectEnrollment(newstudent, "Science");
            }

            JOptionPane.showMessageDialog(null, "Changes saved successfully!");
            parent.switchPanelParameters("sprofile", newstudent, GeneralMethods.GetLoginCreds(newstudentid));
        }

    }//GEN-LAST:event_SaveButtonActionPerformed

    private void NameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameField1ActionPerformed

    }//GEN-LAST:event_NameField1ActionPerformed

    private void GenderField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenderField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenderField1ActionPerformed

    private void EmailField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmailField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmailField1ActionPerformed

    private void AgeField1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AgeField1StateChanged
        LevelEditField.setText(StudentMethods.GetStudentLevel((Integer) AgeField1.getValue()));
    }//GEN-LAST:event_AgeField1StateChanged

    private void NameField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameField1FocusLost
        String field = NameField1.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(NameField1, null, 1, Integer.MAX_VALUE, "[a-zA-Z ]+", "Invalid name entered.\nPlease make sure input only contains letters.");
    }//GEN-LAST:event_NameField1FocusLost

    private void ICField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ICField1FocusLost
        String field = ICField1.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(ICField1, null, 12, 12, "\\d+", "Invalid IC entered.\nPlease make sure input only contains digits and is exactly 12 characters long.");
    }//GEN-LAST:event_ICField1FocusLost

    private void PHField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PHField1FocusLost
        String field = PHField1.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(PHField1, null, 9, 9, "\\d+", "Invalid phone number entered.\nPlease make sure input only contains digits and is exactly 9 characters long excluding the country code (+60).");
    }//GEN-LAST:event_PHField1FocusLost

    private void EmailField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_EmailField1FocusLost
        String field = EmailField1.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(EmailField1, null, 1, Integer.MAX_VALUE, "^[\\w.-]+@[\\w.-]+\\.\\w+$", "Invalid email address entered.\nPlease make sure input does not contain any illegal characters and includes the email domain (Example: @gmail.com).");
    }//GEN-LAST:event_EmailField1FocusLost

    private void AddressField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddressField1FocusLost
        String field = AddressField1.getText().trim();
        if (field.isEmpty()) return;
        GeneralMethods.InputValidation(AddressField1, null, 1, Integer.MAX_VALUE, "[a-zA-Z0-9 ]+", "Invalid address entered.\nPlease make sure input does not contain any illegal characters.");
    }//GEN-LAST:event_AddressField1FocusLost

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

    private void ScienceTutorFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScienceTutorFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ScienceTutorFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AStudentProfileEditPanel;
    private javax.swing.JTextField AddressField1;
    private javax.swing.JSpinner AgeField1;
    private javax.swing.JLabel CPEnrolLabel2;
    private javax.swing.JLabel CPStudentIDLabel;
    private javax.swing.JLabel CPStudentUsernameLabel1;
    private javax.swing.JTextField EmailField1;
    private javax.swing.JRadioButton EnglishField;
    private javax.swing.JComboBox<String> EnglishTutorField;
    private javax.swing.JComboBox<String> GenderField1;
    private javax.swing.JTextField ICField1;
    private javax.swing.JLabel LevelEditField;
    private javax.swing.JRadioButton MathsField;
    private javax.swing.JComboBox<String> MathsTutorField;
    private javax.swing.JTextField NameField1;
    private javax.swing.JLabel PAddressLabel3;
    private javax.swing.JLabel PAgeLabel4;
    private javax.swing.JLabel PEmailLabel3;
    private javax.swing.JLabel PEnrolLabel2;
    private javax.swing.JLabel PGenderLabel3;
    private javax.swing.JTextField PHField1;
    private javax.swing.JLabel PHFormatLabel1;
    private javax.swing.JLabel PICLabel3;
    private javax.swing.JLabel PLevelLabel2;
    private javax.swing.JLabel PNameLabel1;
    private javax.swing.JLabel PPHLabel3;
    private javax.swing.JLabel PPersonalInfoLabel4;
    private javax.swing.JLabel PStudentIDLabel1;
    private javax.swing.JLabel PStudentInfoLabel3;
    private javax.swing.JLabel PStudentNameLabel1;
    private javax.swing.JLabel PSubjectLabel2;
    private javax.swing.JLabel PSubjectLabel4;
    private javax.swing.JButton SaveButton;
    private javax.swing.JRadioButton ScienceField;
    private javax.swing.JComboBox<String> ScienceTutorField;
    private javax.swing.JPanel StudentIdNamePanel1;
    private javax.swing.JPanel StudentInfoLabel3;
    private javax.swing.JLabel StudentManagementTitle;
    private AdminFrontend.ImagePanel StudentPFP;
    private javax.swing.JButton StudentProfileBackButton5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
