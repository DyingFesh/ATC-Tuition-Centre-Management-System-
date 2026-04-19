/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;

import java.io.*;
import java.util.*;

/**
 *
 * @author User
 */
public class GetPaymentDetails {
    private String paymentID;
    private String studentID;
    private String paymentYear;
    private String paymentMonth;
    private String studentLevel;
    private String paymentSubject;
    private float paymentAmount;
    private String paymentStatus;
    
    private static final String payment_file = "Text Files/payments.txt";
    
    public GetPaymentDetails(String paymentID, String studentID, String paymentYear, String paymentMonth, 
                                String studentLevel, String paymentSubject, float paymentAmount, String paymentStatus){
        this.paymentID = paymentID;
        this.studentID = studentID;
        this.paymentYear = paymentYear;
        this.paymentMonth = paymentMonth;
        this.studentLevel = studentLevel;
        this.paymentSubject = paymentSubject;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = paymentStatus;
    }   
    
    public String getPaymentID(){
        return paymentID;
    }
    public String getPaymentYear(){
        return paymentYear;
    }
    public String getPaymentMonth(){
        return paymentMonth;
    }
    public String getstudentLevel(){
        return studentLevel;
    }
    public String getPaymentSubject(){
        return paymentSubject;
    }
    public float getPayementAmount(){
        return paymentAmount;
    }
    public String getPaymentStatus(){
        return paymentStatus;
    }
   
    public List<GetPaymentDetails> getAllPaymentRecords(String studentID) {
        List<GetPaymentDetails> payments = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(payment_file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                if(parts.length >= 8 && parts[1].equalsIgnoreCase(studentID)) {
                    GetPaymentDetails payment = new GetPaymentDetails(
                        parts[0].trim(), 
                        parts[1].trim(),
                        parts[2].trim(), 
                        parts[3].trim(), 
                        parts[4].trim(), 
                        parts[5].trim(), 
                        Float.parseFloat(parts[6].trim()),
                        parts[7].trim()  
                    );
                    payments.add(payment);
                }
            }        
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return payments;
    }
    
    public static List<GetPaymentDetails> getPendingPaymentsByYear(String studentID, String year) {
        List<GetPaymentDetails> pendingPayments = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(payment_file))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                
                if(parts[1].equalsIgnoreCase(studentID)) {
                    String paymentYear = parts[2].trim();
                    String paymentStatus = parts[7].trim();
                    
                    // Only add if it matches the year AND status is Pending
                    if(paymentYear.equals(year) && paymentStatus.equalsIgnoreCase("Pending")) {
                        GetPaymentDetails payment = new GetPaymentDetails(
                            parts[0].trim(), 
                            parts[1].trim(),  
                            parts[2].trim(), 
                            parts[3].trim(), 
                            parts[4].trim(), 
                            parts[5].trim(), 
                            Float.parseFloat(parts[6].trim()),
                            parts[7].trim()  
                        );
                        pendingPayments.add(payment);
                    }
                }
            }        
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return pendingPayments;
    }
}
