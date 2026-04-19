/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminModels;

/**
 *
 * @author User
 */
public class PaymentMade {
    private String pmid;
    private String studentid;
    private String pmfor;
    private float pmamt;
    private String pmdate;
    private String pmplan;
    private String pmmethod;
    
    public PaymentMade(String pmid, String studentid, String pmfor, float pmamt, String pmdate, String pmplan, String pmmethod) {
        this.pmid = pmid;
        this.studentid = studentid;
        this.pmfor = pmfor;
        this.pmamt = pmamt;
        this.pmdate = pmdate;
        this.pmplan = pmplan;
        this.pmmethod = pmmethod;
    }
    
    // Getters for PaymentMade Model
    public String getPmid() {
        return pmid;
    }

    public String getStudentid() {
        return studentid;
    }

    public String getPmfor() {
        return pmfor;
    }

    public float getPmamt() {
        return pmamt;
    }

    public String getPmdate() {
        return pmdate;
    }

    public String getPmplan() {
        return pmplan;
    }

    public String getPmmethod() {
        return pmmethod;
    }
    
    // Setters for PaymentsMade Model
    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public void setPmfor(String pmfor) {
        this.pmfor = pmfor;
    }

    public void setPmamt(float pmamt) {
        this.pmamt = pmamt;
    }

    public void setPmdate(String pmdate) {
        this.pmdate = pmdate;
    }

    public void setPmplan(String pmplan) {
        this.pmplan = pmplan;
    }

    public void setPmmethod(String pmmethod) {
        this.pmmethod = pmmethod;
    }
   
}
