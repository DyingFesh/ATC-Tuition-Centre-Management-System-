/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author User
 */
public class GetPaymentMadeDetails {
    private String payMadeID;
    private String StudentID;
    private String payMadeFor;
    private float payMadeAmount;
    private String payMadeDate;
    private String payMadePlan;
    private String payMadeMethod;

    private static final String paymentMade_file = "Text Files/paymentsmade.txt";

    public GetPaymentMadeDetails(String payMadeID, String studentID, String payMadeFor,
                                 float payMadeAmount, String payMadeDate,
                                 String payMadePlan, String payMadeMethod) {
        this.payMadeID = payMadeID;
        this.StudentID = studentID;
        this.payMadeFor = payMadeFor;
        this.payMadeAmount = payMadeAmount;
        this.payMadeDate = payMadeDate;
        this.payMadePlan = payMadePlan;
        this.payMadeMethod = payMadeMethod;
    }

    public String getPayMadeID() {
        return payMadeID;
    }

    public String getPayMadeFor() {
        return payMadeFor;
    }

    public float getPayMadeAmount() {
        return payMadeAmount;
    }

    public String getPayMadeDate() {
        return payMadeDate;
    }

    public String getPayMadePlan() {
        return payMadePlan;
    }

    public String getPayMadeMethod() {
        return payMadeMethod;
    }

    public static List<GetPaymentMadeDetails> getAllPaymentMadeRecords(String studentID) {
        List<GetPaymentMadeDetails> paymentMade = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(paymentMade_file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 7 && parts[1].trim().equalsIgnoreCase(studentID)) {
                    GetPaymentMadeDetails payment = new GetPaymentMadeDetails(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Float.parseFloat(parts[3].trim()),
                            parts[4].trim(),
                            parts[5].trim(),
                            parts[6].trim()
                    );
                    paymentMade.add(payment);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return paymentMade;
    }
}
