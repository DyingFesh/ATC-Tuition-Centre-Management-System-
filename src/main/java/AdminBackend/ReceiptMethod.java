/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import AdminModels.Payment;
import AdminModels.PaymentMade;
import AdminModels.Student;
import AdminModels.Tutor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author User
 */
public class ReceiptMethod {
    private static final String line1 = "=======================================";
    private static final String line2 = "---------------------------------------";

    private static final int subjectwidth = 14;
    private static final int monthyearwidth = 16;
    private static final int amountwidth = 7;

    public static void GenerateReceipt(String adminid, PaymentMade pm) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(("Receipts/" + pm.getPmid()) + " Receipt.txt"))) {
            
            // Header
            bw.write(line1);
            bw.newLine();
            bw.write("ATC TUITION CENTER");
            bw.newLine();
            bw.write("PAYMENT RECEIPT");
            bw.newLine();
            bw.write(line1);
            bw.newLine();
            bw.newLine();

            // Payment ID & Date
            bw.write("Payment ID   : " + pm.getPmid());
            bw.newLine();
            bw.write("Payment Date : " + pm.getPmdate());
            bw.newLine();
            bw.newLine();

            // Admin Details
            bw.write("Admin ID     : " + adminid);
            bw.newLine();
            bw.write("Admin Name   : " + AdminMethods.GetAdminName(adminid));
            bw.newLine();
            bw.newLine();

            // Student Details
            Student s = StudentMethods.GetStudentByID(pm.getStudentid());

            bw.write(line2);
            bw.newLine();
            bw.write("STUDENT DETAILS");
            bw.newLine();
            bw.write(line2);
            bw.newLine();
            bw.write("Student ID     : " + pm.getStudentid());
            bw.newLine();
            bw.write("Student Name   : " + s.getStudentname());
            bw.newLine();
            bw.write(line2);
            bw.newLine();

            // Student subjects table header
            bw.write(padRight("Level",   7) + padRight("Subject", subjectwidth) + padRight("Tutor",   amountwidth + monthyearwidth - subjectwidth)
            );
            bw.newLine();
            bw.write(line2);
            bw.newLine();

            // Student subjects table row
            List<String> tutors = StudentMethods.GetAssignedTutors(pm.getStudentid());
            String tutorid = null;
            
            for (String subject : s.getStudentsubjects().split(";")) {
                if (subject.equals("English")) {
                    tutorid = tutors.get(0);
                } else if (subject.equals("Mathematics")) {
                    tutorid = tutors.get(1);
                } else if (subject.equals("Science")) {
                    tutorid = tutors.get(2);
                }
                
                Tutor t = TutorMethods.GetTutorByID(tutorid);
                
                bw.write(padRight((s.getStudentlevel()),   7) + padRight(subject, subjectwidth) + padRight(t.getTutorname(), amountwidth + monthyearwidth - subjectwidth));
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();
            

            // Payment Details
            bw.write(line2);
            bw.newLine();
            bw.write("PAYMENT DETAILS");
            bw.newLine();
            bw.write(line2);
            bw.newLine();
            bw.write("Payment Method : " + pm.getPmmethod());
            bw.newLine();
            bw.write("Payment Plan   : " + pm.getPmplan());  
            bw.newLine();
            bw.write(line2);
            bw.newLine();

            // Payments table header
            bw.write(padRight("Subject", subjectwidth) + padRight("Month/Year", monthyearwidth) + padLeft("Amount", amountwidth));
            bw.newLine();
            bw.write(line2);
            bw.newLine();

            // Payments table row
            String[] paymentids = pm.getPmfor().split(";");
            
            for (String paymentid : paymentids) {
                List<Payment> payments = PaymentMethods.GetAllPayments();
                
                for (Payment p : payments) {
                    if (p.getPaymentid().equals(paymentid)) {
                        bw.write(padRight(p.getPaymentsubject(), subjectwidth) + padRight(p.getPaymentmonth() + " " + p.getPaymentyear(), monthyearwidth) + padLeft(Float.toString(p.getPaymentamount()), amountwidth));
                        bw.newLine();
                        break;
                    }
                }
            }

            // Subtotal
            bw.write(line2);
            bw.newLine();
            bw.write(padRight("Subtotal", subjectwidth + monthyearwidth) + padLeft(Float.toString(pm.getPmamt()), amountwidth));
            bw.newLine();
            bw.newLine();
            bw.newLine();

            // Footer
            bw.write(line1);
            bw.newLine();
            bw.write("Thank you for your payment!");
            bw.newLine();
            bw.write(line1);
            bw.newLine();
        }
    }

    // Pads on the right with spaces until total length == width
    private static String padRight(String s, int width) {
        int pad = width - s.length();
        if (pad <= 0) return s;
        return s + " ".repeat(pad);
    }

    // Pads on the left with spaces until total length == width
    private static String padLeft(String s, int width) {
        int pad = width - s.length();
        if (pad <= 0) return s;
        return " ".repeat(pad) + s;
    }
}

