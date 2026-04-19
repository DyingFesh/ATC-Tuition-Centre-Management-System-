/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AdminFrontend;

import AdminBackend.PaymentMethods;
import AdminBackend.StudentMethods;
import AdminModels.Student;
import java.awt.Window;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class PopupAcceptPayment extends javax.swing.JPanel {
    DefaultComboBoxModel sidcombomodel;
    DefaultComboBoxModel yearcombomodel;
    DefaultComboBoxModel monthcombomodel;
    DefaultTableModel studentspendingmodel;
    
    String selectedstudentid;
    
    private final int[] choice = {-1};
    JButton okbutton = new JButton("OK");
    JButton cancelbutton = new JButton("Cancel");
    /**
     * Creates new form LoginCredentialsPopup
     */
    public PopupAcceptPayment() {
        initComponents();
        
        okbutton.addActionListener(e -> {
            choice[0] = 0;
            Window w = SwingUtilities.getWindowAncestor(okbutton);
            if (w != null) w.dispose();
        });

        this.cancelbutton.addActionListener(e -> {
            choice[0] = 1;
            Window w = SwingUtilities.getWindowAncestor(cancelbutton);
            if (w != null) w.dispose();
        });
        
        okbutton.setEnabled(false);
        
        // Populate Student ID JComboBox
        sidcombomodel = (DefaultComboBoxModel) StudentIDField.getModel();
        sidcombomodel.removeAllElements();
        List<Student> students = StudentMethods.GetAllStudents();
        for (Student s : students) {
            sidcombomodel.addElement(s.getStudentid());
        }
        
        StudentIDField.setSelectedIndex(0);
        selectedstudentid = StudentIDField.getSelectedItem().toString();
        
        // Set student name & student level labels
        for (Student s : students) {
            if (s.getStudentid().equals(selectedstudentid)) {
                StudentNameLabel.setText(s.getStudentname());
                StudentLevelLabel.setText(s.getStudentlevel());
            }
        }

        // Populate Student Pending Payments Table
        studentspendingmodel = (DefaultTableModel) StudentPendingPayments.getModel();
        PaymentMethods.PopulateStudentPending(selectedstudentid, studentspendingmodel);

        
        // Populate Year/Month JComboBox based on student's pending payments
        yearcombomodel = (DefaultComboBoxModel) PaymentForYearField.getModel();
        monthcombomodel = (DefaultComboBoxModel) PaymentForMonthField.getModel();
        PaymentMethods.PopulateYearMonth(selectedstudentid, yearcombomodel, monthcombomodel);
        PaymentForYearField.setSelectedIndex(0);
        PaymentMethods.PopulateMonth(selectedstudentid, yearcombomodel, monthcombomodel);
        PaymentForMonthField.setSelectedIndex(0);
        
        // Calculate payment amount
        String paymentyear = null;
        String paymentmonth = null;
        Object selectedyear = PaymentForYearField.getSelectedItem();
        if (selectedyear != null) {
            paymentyear = selectedyear.toString();

            // Check payment plan, set month to null if yearly plan is selected
            if (PaymentPlanField.getSelectedIndex() == 0) {
                if (monthcombomodel.getSelectedItem() != null) {
                    paymentmonth = monthcombomodel.getSelectedItem().toString();
                }
            } else {
                paymentmonth = null;
            }

            float amount = PaymentMethods.CalculatePaymentAmount(selectedstudentid, paymentyear, paymentmonth);
            TotalAmountDue.setText("RM" + amount);
        }
    }
    
    public JButton getAPOKButton() {
        return okbutton;
    }
    
    public JButton getAPCancelButton() {
        return cancelbutton;
    }
    
    public int getAPChoice() {
        return choice[0];
    }

    public String getAPStudentid() {
        return StudentIDField.getSelectedItem().toString();
    }

    public JTable getStudentPendingPayments() {
        return StudentPendingPayments;
    }

    public JComboBox<String> getPaymentForMonthField() {
        return PaymentForMonthField;
    }

    public JComboBox<String> getPaymentForYearField() {
        return PaymentForYearField;
    }

    public JComboBox<String> getPaymentMethodField() {
        return PaymentMethodField;
    }

    public JComboBox<String> getPaymentPlanField() {
        return PaymentPlanField;
    }

    public JComboBox<String> getStudentIDField() {
        return StudentIDField;
    }

    public JLabel getStudentLevelLabel() {
        return StudentLevelLabel;
    }

    public JLabel getTotalAmountDue() {
        return TotalAmountDue;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentIDField = new javax.swing.JComboBox<>();
        StudentNameLabel = new javax.swing.JLabel();
        StudentLevelLabel = new javax.swing.JLabel();
        StudentPendingPaymentsScrollPane = new javax.swing.JScrollPane();
        StudentPendingPayments = new javax.swing.JTable();
        PaymentPlanLabel = new javax.swing.JLabel();
        PaymentForLabel = new javax.swing.JLabel();
        PaymentMethodLabel = new javax.swing.JLabel();
        TotalAmountDueLabel = new javax.swing.JLabel();
        PaymentPlanField = new javax.swing.JComboBox<>();
        PaymentMethodField = new javax.swing.JComboBox<>();
        PaymentForYearField = new javax.swing.JComboBox<>();
        PaymentForMonthField = new javax.swing.JComboBox<>();
        TotalAmountDue = new javax.swing.JLabel();
        ConfirmationCheckbox = new javax.swing.JCheckBox();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        StudentIDField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        StudentIDField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                StudentIDFieldItemStateChanged(evt);
            }
        });
        StudentIDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StudentIDFieldActionPerformed(evt);
            }
        });
        add(StudentIDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, 30));

        StudentNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StudentNameLabel.setText("Student Name");
        add(StudentNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 160, 30));

        StudentLevelLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StudentLevelLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        StudentLevelLabel.setText("Form X");
        add(StudentLevelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 50, 30));

        StudentPendingPayments.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StudentPendingPayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Year", "Month", "Subject", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        StudentPendingPayments.setRowHeight(30);
        StudentPendingPayments.setRowMargin(5);
        StudentPendingPayments.setRowSelectionAllowed(false);
        StudentPendingPayments.getTableHeader().setResizingAllowed(false);
        StudentPendingPayments.getTableHeader().setReorderingAllowed(false);
        StudentPendingPayments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StudentPendingPaymentsMouseClicked(evt);
            }
        });
        StudentPendingPayments.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                StudentPendingPaymentsComponentShown(evt);
            }
        });
        StudentPendingPaymentsScrollPane.setViewportView(StudentPendingPayments);

        add(StudentPendingPaymentsScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 380, 180));

        PaymentPlanLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PaymentPlanLabel.setText("Payment Plan:");
        add(PaymentPlanLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 130, 30));

        PaymentForLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PaymentForLabel.setText("Payment For:");
        add(PaymentForLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, 130, 30));

        PaymentMethodLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PaymentMethodLabel.setText("Payment Method:");
        add(PaymentMethodLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 130, 30));

        TotalAmountDueLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TotalAmountDueLabel.setText("Total Amount Due:");
        add(TotalAmountDueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 130, 30));

        PaymentPlanField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monthly", "Yearly" }));
        PaymentPlanField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PaymentPlanFieldItemStateChanged(evt);
            }
        });
        PaymentPlanField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentPlanFieldActionPerformed(evt);
            }
        });
        add(PaymentPlanField, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 120, 30));

        PaymentMethodField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Credit Card", "Bank Transfer", "E-Wallet" }));
        PaymentMethodField.setToolTipText("");
        PaymentMethodField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentMethodFieldActionPerformed(evt);
            }
        });
        add(PaymentMethodField, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 120, 30));

        PaymentForYearField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PaymentForYearFieldItemStateChanged(evt);
            }
        });
        PaymentForYearField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentForYearFieldActionPerformed(evt);
            }
        });
        add(PaymentForYearField, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 70, 30));

        PaymentForMonthField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PaymentForMonthFieldItemStateChanged(evt);
            }
        });
        PaymentForMonthField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PaymentForMonthFieldActionPerformed(evt);
            }
        });
        add(PaymentForMonthField, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 130, 30));

        TotalAmountDue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TotalAmountDue.setText("RM0");
        add(TotalAmountDue, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 130, 30));

        ConfirmationCheckbox.setText("  I acknowledge that the correct fee amount has been received.");
        ConfirmationCheckbox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ConfirmationCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmationCheckboxActionPerformed(evt);
            }
        });
        add(ConfirmationCheckbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 380, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void StudentPendingPaymentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentPendingPaymentsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentPendingPaymentsMouseClicked

    private void StudentPendingPaymentsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_StudentPendingPaymentsComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentPendingPaymentsComponentShown

    private void PaymentPlanFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentPlanFieldActionPerformed

    }//GEN-LAST:event_PaymentPlanFieldActionPerformed

    private void PaymentMethodFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentMethodFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentMethodFieldActionPerformed

    private void PaymentForMonthFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentForMonthFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PaymentForMonthFieldActionPerformed

    private void ConfirmationCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmationCheckboxActionPerformed
        if (ConfirmationCheckbox.isSelected()) {
            okbutton.setEnabled(true);
        } else {
            okbutton.setEnabled(false);
        }
    }//GEN-LAST:event_ConfirmationCheckboxActionPerformed

    private void StudentIDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentIDFieldActionPerformed

    }//GEN-LAST:event_StudentIDFieldActionPerformed

    private void StudentIDFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_StudentIDFieldItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            List<Student> students = StudentMethods.GetAllStudents();
            selectedstudentid = (String) evt.getItem();

            for (Student s : students) {
                if (s.getStudentid().equals(selectedstudentid)) {
                    StudentNameLabel.setText(s.getStudentname());
                    StudentLevelLabel.setText(s.getStudentlevel());
                    break;
                }
            }
            
            // Populate Student Pending Payments Table
            studentspendingmodel = (DefaultTableModel) StudentPendingPayments.getModel();
            PaymentMethods.PopulateStudentPending(selectedstudentid, studentspendingmodel);

            // Populate Year/Month JComboBox based on student's pending payments
            yearcombomodel = (DefaultComboBoxModel) PaymentForYearField.getModel();
            monthcombomodel = (DefaultComboBoxModel) PaymentForMonthField.getModel();

            
            PaymentMethods.PopulateYearMonth(selectedstudentid, yearcombomodel, monthcombomodel);
            PaymentForYearField.setSelectedIndex(0);
            PaymentMethods.PopulateMonth(selectedstudentid, yearcombomodel, monthcombomodel);
            PaymentForMonthField.setSelectedIndex(0);
            
            if (monthcombomodel.getSize() > 0) {
                PaymentForMonthField.setSelectedIndex(0);
            }
            
            Object selectedyear = PaymentForYearField.getSelectedItem();
            if (selectedyear != null) {
                String paymentyear = selectedyear.toString();
                String paymentmonth = null;
                
                // Check payment plan, set month to null if yearly plan is selected
                if (PaymentPlanField.getSelectedIndex() == 0) {
                    if (monthcombomodel.getSelectedItem() != null) {
                        paymentmonth = monthcombomodel.getSelectedItem().toString();
                    }
                } else {
                    paymentmonth = null;
                }

                float amount = PaymentMethods.CalculatePaymentAmount(selectedstudentid, paymentyear, paymentmonth);
                TotalAmountDue.setText("RM" + amount);
            }
        }
    }//GEN-LAST:event_StudentIDFieldItemStateChanged

    private void PaymentForYearFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PaymentForYearFieldActionPerformed

    }//GEN-LAST:event_PaymentForYearFieldActionPerformed

    private void PaymentForYearFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PaymentForYearFieldItemStateChanged
        if (evt.getStateChange() != java.awt.event.ItemEvent.SELECTED) return;
        
        yearcombomodel = (DefaultComboBoxModel) PaymentForYearField.getModel();
        monthcombomodel = (DefaultComboBoxModel) PaymentForMonthField.getModel();
        PaymentMethods.PopulateMonth(selectedstudentid, yearcombomodel, monthcombomodel);
        
        if (monthcombomodel.getSize() > 0) {
            PaymentForMonthField.setSelectedIndex(0);
        }
        
        Object selectedyear = PaymentForYearField.getSelectedItem();
        if (selectedyear != null) {
            String paymentyear = selectedyear.toString();
            String paymentmonth = null;
            
            // Check payment plan, set month to null if yearly plan is selected
            if (PaymentPlanField.getSelectedIndex() == 0) {
                if (monthcombomodel.getSelectedItem() != null) {
                    paymentmonth = monthcombomodel.getSelectedItem().toString();
                }
            } else {
                paymentmonth = null;
            }
            
            float amount = PaymentMethods.CalculatePaymentAmount(selectedstudentid, paymentyear, paymentmonth);
            TotalAmountDue.setText("RM" + amount);
        }
    }//GEN-LAST:event_PaymentForYearFieldItemStateChanged

    private void PaymentForMonthFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PaymentForMonthFieldItemStateChanged
        if (evt.getStateChange() != java.awt.event.ItemEvent.SELECTED) return;
        
        yearcombomodel = (DefaultComboBoxModel) PaymentForYearField.getModel();
        monthcombomodel = (DefaultComboBoxModel) PaymentForMonthField.getModel();
        
        Object selectedyear = PaymentForYearField.getSelectedItem();
        if (selectedyear != null) {
            String paymentyear = selectedyear.toString();
            String paymentmonth = null;
            
            // Check payment plan, set month to null if yearly plan is selected
            if (PaymentPlanField.getSelectedIndex() == 0) {
                if (monthcombomodel.getSelectedItem() != null) {
                    paymentmonth = monthcombomodel.getSelectedItem().toString();
                }
            } else {
                paymentmonth = null;
            }

            float amount = PaymentMethods.CalculatePaymentAmount(selectedstudentid, paymentyear, paymentmonth);
            TotalAmountDue.setText("RM" + amount);
        }
    }//GEN-LAST:event_PaymentForMonthFieldItemStateChanged

    private void PaymentPlanFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PaymentPlanFieldItemStateChanged
        if (evt.getStateChange() != java.awt.event.ItemEvent.SELECTED) return;
        
        yearcombomodel = (DefaultComboBoxModel) PaymentForYearField.getModel();
        monthcombomodel = (DefaultComboBoxModel) PaymentForMonthField.getModel();
        monthcombomodel.removeAllElements();
        
        // Monthly payment plan selected
        if (PaymentPlanField.getSelectedIndex() == 0) {
            PaymentForMonthField.setEnabled(true);
            PaymentMethods.PopulateMonth(selectedstudentid, (DefaultComboBoxModel) PaymentForYearField.getModel(), (DefaultComboBoxModel) PaymentForMonthField.getModel());
            if (monthcombomodel.getSize() > 0) {
                PaymentForMonthField.setSelectedIndex(0);
            }
        } 
        // Yearly payment plan selected
        else if (PaymentPlanField.getSelectedIndex() == 1) {
            PaymentForMonthField.setEnabled(false);
            ((DefaultComboBoxModel) PaymentForMonthField.getModel()).removeAllElements();
        }
        
        Object selectedyear = PaymentForYearField.getSelectedItem();
        if (selectedyear != null) {
            String paymentyear = selectedyear.toString();
            String paymentmonth = null;
            
            // Check payment plan, set month to null if yearly plan is selected
            if (PaymentPlanField.getSelectedIndex() == 0) {
                if (monthcombomodel.getSelectedItem() != null) {
                    paymentmonth = monthcombomodel.getSelectedItem().toString();
                }
            } else {
                paymentmonth = null;
            }

            float amount = PaymentMethods.CalculatePaymentAmount(selectedstudentid, paymentyear, paymentmonth);
            TotalAmountDue.setText("RM" + amount);
        }
    }//GEN-LAST:event_PaymentPlanFieldItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ConfirmationCheckbox;
    private javax.swing.JLabel PaymentForLabel;
    private javax.swing.JComboBox<String> PaymentForMonthField;
    private javax.swing.JComboBox<String> PaymentForYearField;
    private javax.swing.JComboBox<String> PaymentMethodField;
    private javax.swing.JLabel PaymentMethodLabel;
    private javax.swing.JComboBox<String> PaymentPlanField;
    private javax.swing.JLabel PaymentPlanLabel;
    private javax.swing.JComboBox<String> StudentIDField;
    private javax.swing.JLabel StudentLevelLabel;
    private javax.swing.JLabel StudentNameLabel;
    private javax.swing.JTable StudentPendingPayments;
    private javax.swing.JScrollPane StudentPendingPaymentsScrollPane;
    private javax.swing.JLabel TotalAmountDue;
    private javax.swing.JLabel TotalAmountDueLabel;
    // End of variables declaration//GEN-END:variables
}
