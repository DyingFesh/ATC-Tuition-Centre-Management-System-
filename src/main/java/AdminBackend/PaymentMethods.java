/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import AdminModels.Payment;
import AdminModels.PaymentMade;
import AdminModels.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class PaymentMethods {

    private static final File studentfile = new File("Text Files/students.txt");
    private static final File feesfile = new File("Text Files/fees.txt");
    private static final File paymentsfile = new File("Text Files/payments.txt");
    private static final File paymentsmadefile = new File("Text Files/paymentsmade.txt");
    private static final Path paymentsfilepath = Paths.get("Text Files/payments.txt");
    private static final Path paymentsmadefilepath = Paths.get("Text Files/paymentsmade.txt");
    
    public static float GetFeeAmount(String level, String subject) {
        try {
            FileReader fr = new FileReader(feesfile);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String flevel = parts[0];
                String fsubject = parts[1];
                float famt = Float.parseFloat(parts[2]);
                
                if (flevel.trim().equals(level) && fsubject.trim().equals(subject)) {
                    return famt;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public static String GetLastPaymentID() {
        String lastpaymentid = "P000";

        try {
            FileReader fr = new FileReader(paymentsfile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                lastpaymentid = parts[0];
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lastpaymentid;
    }
    
    public static boolean CheckPaymentExists(String studentid, int year, int month, String subject) {
        try (FileReader fr = new FileReader(paymentsfile); BufferedReader br = new BufferedReader(fr)) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                String monthname = Month.of(month).name();
                monthname = monthname.charAt(0) + monthname.substring(1).toLowerCase();
                
                if (parts.length >= 6 && parts[1].equals(studentid) && parts[2].equals(String.valueOf(year)) && parts[3].equals(monthname) && parts[5].equals(subject)) return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static void GeneratePayment() {
        try (FileWriter fw = new FileWriter(paymentsfile, true); BufferedWriter bw = new BufferedWriter(fw)) { 
            List<Student> students = StudentMethods.GetAllStudents();
            
            String lastpaymentid = GetLastPaymentID();
            int currentpaymentnum = Integer.parseInt(lastpaymentid.substring(1)) + 1;


            for (Student s : students) {
                String[] subjects = s.getStudentsubjects().split(";");

                for (String subject : subjects) {
                    String[] enroll = s.getStudentenrol().split(" ");
                    int enrolmentyearnum  = Integer.parseInt(enroll[1]);
                    int enrolmentmonthnum = Month.valueOf(enroll[0].toUpperCase()).getValue();

                    String[] current = GeneralMethods.GetCurrentMonthYear().split(" ");
                    int currentyear  = Integer.parseInt(current[1]);
                    int currentmonth = Month.valueOf(current[0].toUpperCase()).getValue();

                    while (enrolmentyearnum < currentyear || (enrolmentyearnum == currentyear && enrolmentmonthnum <= currentmonth)) {
                        if (CheckPaymentExists(s.getStudentid(), enrolmentyearnum, enrolmentmonthnum, subject) == false) {
                            String paymentid = String.format("P%03d", currentpaymentnum++);
                            if (currentpaymentnum > 999) {
                                throw new RuntimeException("Payment ID limit exceeded!");
                            }

                            float feeamount = GetFeeAmount(s.getStudentlevel(), subject);

                            String newmonthname = Month.of(enrolmentmonthnum).name();
                            newmonthname = newmonthname.charAt(0) + newmonthname.substring(1).toLowerCase();

                            String newpayment = String.join(",", paymentid, s.getStudentid(), Integer.toString(enrolmentyearnum), newmonthname, s.getStudentlevel(), subject, Float.toString(feeamount), "Pending");
                            
                            bw.write(newpayment);
                            bw.newLine();
                        }
                        enrolmentmonthnum++;
                        if (enrolmentmonthnum > 12) {
                            enrolmentmonthnum = 1;
                            enrolmentyearnum++;
                        }
                    }
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static List<Payment> GetAllPayments() {
        List<Payment> payments = new ArrayList<>();
        
        try {
            FileReader fr = new FileReader(paymentsfile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Payment p = new Payment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], Float.parseFloat(parts[6]), parts[7]);
                payments.add(p);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return payments;
    }
    
    public static Payment GetPaymentByID(String targetid) {
        for (Payment p : GetAllPayments()) {
            if (p.getPaymentid().equals(targetid)) {
                return p;
            }
        }
        return null;
    }
    
    public static List<PaymentMade> GetAllPaymentsMade() {
        List<PaymentMade> paymentsmade = new ArrayList<>();
        
        try {
            FileReader fr = new FileReader(paymentsmadefile);
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                PaymentMade pm = new PaymentMade(parts[0], parts[1], parts[2], Float.parseFloat(parts[3]), parts[4], parts[5], parts[6]);
                paymentsmade.add(pm);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return paymentsmade;
    }
    
    public static PaymentMade GetPaymentMadeByID(String targetid) {
        for (PaymentMade pm : GetAllPaymentsMade()) {
            if (pm.getPmid().equals(targetid)) {
                return pm;
            }
        }
        return null;
    }
    
    
    public static void PopulatePaymentHistory(DefaultTableModel tablemodel, String year, String month) {
        tablemodel.setRowCount(0);

        List<PaymentMade> paymentsmade = GetAllPaymentsMade();
        List<Student> students = StudentMethods.GetAllStudents();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM yyyy");

        for (PaymentMade pm : paymentsmade) {
            String studentid   = pm.getStudentid();
            String studentname = "EMPTY";
            for (Student s : students) {
                if (studentid.equals(s.getStudentid())) {
                    studentname = s.getStudentname();
                    break;
                }
            }

            LocalDate olddate = LocalDate.parse(pm.getPmdate(), formatter1);
            String newdate = olddate.format(formatter2);
            String[] pmmonthyear = newdate.split(" ");
            String pmmonth = pmmonthyear[0];
            String pmyear  = pmmonthyear[1];

            boolean yearmatch = year.equals("All") || year.equals(pmyear);
            boolean monthmatch = month.equals("All") || month.equals(pmmonth);

            if (yearmatch && monthmatch) {
                Object[] row = new Object[] {pm.getPmid(), studentid, studentname, pm.getPmamt(), pm.getPmdate(), pm.getPmplan(), pm.getPmmethod()};
                tablemodel.addRow(row);
            }
        }
    }

    
    public static void PopulatePendingPayments(DefaultTableModel tablemodel, String year, String month) {
        tablemodel.setRowCount(0);

        List<Payment> payments = GetAllPayments();
        List<Student> students = StudentMethods.GetAllStudents();

        for (Payment p : payments) {
            if (p.getPaymentstatus().equals("Pending")) {
                
                String studentname = null;
                
                for (Student s : students) {
                    if (p.getStudentid().equals(s.getStudentid())) {
                        studentname = s.getStudentname();
                    }
                }
                
                boolean yearmatch  = year.equals("All")  || year.equals(p.getPaymentyear());
                boolean monthmatch = month.equals("All") || month.equals(p.getPaymentmonth());
                
                if (p.getPaymentstatus().equals("Pending") && yearmatch && monthmatch) {
                    Object[] row = new Object[] {p.getPaymentyear(), p.getPaymentmonth(), p.getStudentid(), studentname, p.getPaymentlevel(), p.getPaymentsubject(), p.getPaymentamount()};
                    tablemodel.addRow(row);
                }
            }
        }
    }
    
    public static void PopulateStudentPending(String studentid, DefaultTableModel tablemodel) {
        tablemodel.setRowCount(0);

        List<Payment> payments = GetAllPayments();

        for (Payment p : payments) {
                if (p.getPaymentstatus().equals("Pending") && p.getStudentid().equals(studentid)) {
                    Object[] row = new Object[] {p.getPaymentyear(), p.getPaymentmonth(), p.getPaymentsubject(), p.getPaymentamount()};
                    tablemodel.addRow(row);
                }
        }
    }
    
    public static List<Payment> GetPendingPaymentsByStudent(String studentid) {
        List<Payment> pendingpayments = new ArrayList<>();
        
        for (Payment p : GetAllPayments()) {
            if (p.getStudentid().equals(studentid) && p.getPaymentstatus().equals("Pending")) {
                pendingpayments.add(p);
            }
        }
        return pendingpayments;
    }
    
    public static void PopulateYearMonth(String studentid, DefaultComboBoxModel yearmodel, DefaultComboBoxModel monthmodel) {
        yearmodel.removeAllElements();
        monthmodel.removeAllElements();
        
        List<Payment> pendingpayments = PaymentMethods.GetPendingPaymentsByStudent(studentid);
        List<String> uniqueyears = new ArrayList<>();
        List<String> uniquemonths = new ArrayList<>();

        for (Payment p : pendingpayments) {
            String year = p.getPaymentyear();
            String month = p.getPaymentmonth();

            if (!uniqueyears.contains(year)) {
                uniqueyears.add(year);
            }
            if (!uniquemonths.contains(month)) {
                uniquemonths.add(month);
            }
        }
        
        for (String year : uniqueyears) {
            yearmodel.addElement(year);
        }
        
        for (String month : uniquemonths) {
            monthmodel.addElement(month);
        }
    }
    
    public static void PopulateMonth(String studentid, DefaultComboBoxModel yearmodel, DefaultComboBoxModel monthmodel) {
        monthmodel.removeAllElements();

        Object selectedyear = yearmodel.getSelectedItem();
        String paymentyear = selectedyear.toString();

        List<Payment> pendingpayments = PaymentMethods.GetPendingPaymentsByStudent(studentid);
        List<String> uniquemonths = new ArrayList<>();

        for (Payment p : pendingpayments) {
            if (p.getPaymentyear().equals(paymentyear)) {
                String month = p.getPaymentmonth();
                if (!uniquemonths.contains(month)) {
                    uniquemonths.add(month);
                }            
            }
        }

        for (String month : uniquemonths) {
            monthmodel.addElement(month);
        }
    }
    
    public static float CalculatePaymentAmount(String studentid, String selectedyear, String selectedmonth) {
        List<Payment> pendingpayments = PaymentMethods.GetPendingPaymentsByStudent(studentid);
        float amount = 0;
        
        if (selectedmonth == null) {
            for (Payment p : pendingpayments) {
                if (p.getPaymentyear().equals(selectedyear)) {
                    amount += p.getPaymentamount();
                }
            }
        } else {
            for (Payment p : pendingpayments) {
                if (p.getPaymentyear().equals(selectedyear) && p.getPaymentmonth().equals(selectedmonth)) {
                    amount += p.getPaymentamount();
                }
            }
        }
        
        return amount;
    }
    
    public static float CalculateTotalPending() {
        List<Payment> pendingpayments = new ArrayList<>();
        
        for (Payment p : GetAllPayments()) {
            if (p.getPaymentstatus().equals("Pending")) {
                pendingpayments.add(p);
            }
        }

        float amount = 0;
        
        for (Payment p : pendingpayments)
        amount += p.getPaymentamount();
        
        return amount;
    }
    
    public static void PopulateIncomeReport(DefaultTableModel tablemodel, JLabel label, String year, String month, String level, String subject) {
        float cashamt = 0;
        float creditamt = 0;
        float bankamt = 0;
        float ewalletamt = 0;

        List<Payment> payments = GetAllPayments();
        List<PaymentMade> paymentsmade = GetAllPaymentsMade();

        for (PaymentMade pm : paymentsmade) {
            String[] paymentids = pm.getPmfor().split(";");
            
            for (String paymentid : paymentids) {
                for (Payment p : payments) {
                    if (p.getPaymentid().equals(paymentid) && p.getPaymentstatus().equals("Paid")) {
                        boolean yearmatch = year.equals("All") || year.equals(p.getPaymentyear());
                        boolean monthmatch = month.equals("All") || month.equals(p.getPaymentmonth());
                        boolean levelmatch  = level.equals("All")  || level.equals(p.getPaymentlevel());
                        boolean subjectmatch = subject.equals("All") || subject.equals(p.getPaymentsubject());

                        if (p.getPaymentstatus().equals("Paid") && yearmatch && monthmatch && levelmatch && subjectmatch) {
                            switch (pm.getPmmethod()) {
                            case "Cash":
                                cashamt += p.getPaymentamount();
                                break;
                            case "Credit Card":
                                creditamt += p.getPaymentamount();
                                break;
                            case "Bank Transfer":
                                bankamt += p.getPaymentamount();
                                break;
                            case "E-Wallet":
                                ewalletamt += p.getPaymentamount();
                                break;
                            }
                        }
                    }
                }    
            }

        }
        tablemodel.setValueAt(cashamt, 0, 1);
        tablemodel.setValueAt(creditamt, 1, 1);
        tablemodel.setValueAt(bankamt, 2, 1);
        tablemodel.setValueAt(ewalletamt, 3, 1);

        float totalamount = cashamt + creditamt + bankamt + ewalletamt;
        label.setText(Float.toString(totalamount));
        
    }
    
    public static String GetNewPaymentMadeID() {
        String lastpmid = null;
        String newpmid = "PM001";
        
        try {
            FileReader fr = new FileReader(paymentsmadefile);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            String[] parts = line.split(",");
            lastpmid = parts[0];
            
            int lastpmidnum = Integer.parseInt(lastpmid.substring(2));
            int newpmidnum = lastpmidnum + 1;
            newpmid = String.format("PM%03d", newpmidnum);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return newpmid;
    }
    
    public static String PMConvertToOneLine(PaymentMade pm) {
        return String.join(",",
            pm.getPmid(),
            pm.getStudentid(),
            pm.getPmfor(),
            Float.toString(pm.getPmamt()),
            pm.getPmdate(),
            pm.getPmplan(),
            pm.getPmmethod()
        );
    }
    
    public static void SaveNewPayment(PaymentMade pm) {
        try {
            List<String> lines = new ArrayList<>();
            lines = Files.readAllLines(paymentsmadefilepath);
            
            String newline = PMConvertToOneLine(pm);
            boolean updated = false;
            
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(pm.getPmid() + ",")) {
                    lines.set(i, newline);
                    updated = true;
                    break;
                }
            }
            
            if (!updated) {
                lines.add(0, newline);
            }
            
            Files.write(paymentsmadefilepath, lines);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void UpdatePaymentStatus(Payment p) {
        try {
            List<String> lines = Files.readAllLines(paymentsfilepath);
            String targetid = p.getPaymentid();

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(targetid + ",")) {
                    if ("Pending".equals(p.getPaymentstatus())) {
                        p.setPaymentstatus("Paid");
                    }
                    
                    String[] parts = line.split(",", -1);
                    parts[parts.length - 1] = p.getPaymentstatus();
                    String newLine = String.join(",", parts);
                    lines.set(i, newLine);
                    break;
                }
            }

            Files.write(paymentsfilepath, lines);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
}