/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminModels;

/**
 *
 * @author User
 */
public class Student {
    private final String studentid;
    private final String studentname;
    private int studentage;
    private String studentgender;
    private String studentic;
    private String studentph;
    private String studentemail;
    private String studentaddress;
    private String studentlevel;
    private String studentsubjects;
    private String studentenrol;
    
    public Student(String studentid, String studentname, int studentage, String studentgender, String studentic, String studentph, String studentemail, String studentaddress, String studentlevel, String studentsubjects, String studentenrol) {
        this.studentid = studentid;
        this.studentname = studentname;
        this.studentage = studentage;
        this.studentgender = studentgender;
        this.studentic = studentic;
        this.studentph = studentph;
        this.studentemail = studentemail;
        this.studentaddress = studentaddress;
        this.studentlevel = studentlevel;
        this.studentsubjects = studentsubjects;
        this.studentenrol = studentenrol;
    }

    // Getters for Student Model
    public String getStudentid() {
        return studentid;
    }

    public String getStudentname() {
        return studentname;
    }

    public int getStudentage() {
        return studentage;
    }

    public String getStudentgender() {
        return studentgender;
    }

    public String getStudentic() {
        return studentic;
    }
    
    public String getStudentph() {
        return studentph;
    }

    public String getStudentemail() {
        return studentemail;
    }

    public String getStudentaddress() {
        return studentaddress;
    }

    public String getStudentlevel() {
        return studentlevel;
    }

    public String getStudentsubjects() {
        return studentsubjects;
    }

    public String getStudentenrol() {
        return studentenrol;
    }

    // Setters for Student Model
    public void setStudentage(int studentage) {
        this.studentage = studentage;
    }

    public void setStudentgender(String studentgender) {
        this.studentgender = studentgender;
    }

    public void setStudentic(String studentic) {
        this.studentic = studentic;
    }
    
    public void setStudentph(String studentph) {
        this.studentph = studentph;
    }

    public void setStudentemail(String studentemail) {
        this.studentemail = studentemail;
    }

    public void setStudentaddress(String studentaddress) {
        this.studentaddress = studentaddress;
    }

    public void setStudentlevel(String studentlevel) {
        this.studentlevel = studentlevel;
    }

    public void setStudentsubjects(String studentsubjects) {
        this.studentsubjects = studentsubjects;
    }

    public void setStudentenrol(String studentenrol) {
        this.studentenrol = studentenrol;
    }
    
    
}
