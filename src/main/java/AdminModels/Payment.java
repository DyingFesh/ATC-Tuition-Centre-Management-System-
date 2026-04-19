/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminModels;

/**
 *
 * @author User
 */
public class Payment {
    private String paymentid;
    private String studentid;
    private String paymentyear;
    private String paymentmonth;
    private String paymentlevel;
    private String paymentsubject;
    private float paymentamount;
    private String paymentstatus;
    
    public Payment(String paymentid, String studentid, String paymentyear, String paymentmonth, String paymentlevel, String paymentsubject, float paymentamount, String paymentstatus) {
        this.paymentid = paymentid;
        this.studentid = studentid;
        this.paymentyear = paymentyear;
        this.paymentmonth = paymentmonth;
        this.paymentlevel = paymentlevel;
        this.paymentsubject = paymentsubject;
        this.paymentamount = paymentamount;
        this.paymentstatus = paymentstatus;
    }

    // Getters for Payment Model
    public String getPaymentid() {
        return paymentid;
    }

    public String getStudentid() {
        return studentid;
    }

    public String getPaymentyear() {
        return paymentyear;
    }

    public String getPaymentmonth() {
        return paymentmonth;
    }

    public String getPaymentlevel() {
        return paymentlevel;
    }

    public String getPaymentsubject() {
        return paymentsubject;
    }

    public float getPaymentamount() {
        return paymentamount;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }
    
    //Setters for Payment Model
    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public void setPaymentyear(String paymentyear) {
        this.paymentyear = paymentyear;
    }

    public void setPaymentmonth(String paymentmonth) {
        this.paymentmonth = paymentmonth;
    }

    public void setPaymentlevel(String paymentlevel) {
        this.paymentlevel = paymentlevel;
    }

    public void setPaymentsubject(String paymentsubject) {
        this.paymentsubject = paymentsubject;
    }

    public void setPaymentamount(float paymentamount) {
        this.paymentamount = paymentamount;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }  
    
}
