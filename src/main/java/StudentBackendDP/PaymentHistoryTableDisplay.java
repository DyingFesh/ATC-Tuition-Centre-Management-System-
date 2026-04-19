/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendDP;

import StudentBackendFH.GetPaymentMadeDetails;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class PaymentHistoryTableDisplay {
    public static DefaultTableModel PaymentHistoryTableDisplay(String StudentID) {
        String[] columns = {"Payment Made ID", "Payment ID", "Total Amount", "Date Paid", "Payment Plan", "Payment Method"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<GetPaymentMadeDetails> paymentMade = GetPaymentMadeDetails.getAllPaymentMadeRecords(StudentID);
        for (GetPaymentMadeDetails payment : paymentMade) {
            String paymentMadeID = payment.getPayMadeID(); // Replace with actual unique ID getter if exists
            float amount = payment.getPayMadeAmount();
            String datePaid = payment.getPayMadeDate();
            String paymentPlan = payment.getPayMadePlan();
            String paymentMethod = payment.getPayMadeMethod();

            for (String paymentID : payment.getPayMadeFor().split(";")) {
                paymentID = paymentID.trim();
                if (!paymentID.isEmpty()) {
                    Object[] rowData = {paymentMadeID, paymentID, amount, datePaid, paymentPlan, paymentMethod};
                    model.addRow(rowData);
                }
            }
        }

        return model;
    }
}
