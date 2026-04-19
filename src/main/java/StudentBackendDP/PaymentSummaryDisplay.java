/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;
import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import StudentBackendFH.GetPaymentDetails;
/**
 *
 * @author User
 */
public class PaymentSummaryDisplay {
    public static DefaultTableModel PaymentSummaryDisplay(String StudentID, String YearSelected){
        String[] columns = {"Month", "Subject", "Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        model.setRowCount(0);
        
        List<GetPaymentDetails> pendingPayments = GetPaymentDetails.getPendingPaymentsByYear(StudentID, YearSelected);
         for (GetPaymentDetails payment : pendingPayments) {
            String month = payment.getPaymentMonth();
            String subject = payment.getPaymentSubject();
            float amount = payment.getPayementAmount();
            String status = payment.getPaymentStatus();
            
            Object[] rowData = {month, subject, amount, status};
            model.addRow(rowData);
        }

        return model;
    }
}
