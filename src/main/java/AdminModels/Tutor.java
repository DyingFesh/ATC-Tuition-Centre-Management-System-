/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminModels;

/**
 *
 * @author User
 */
public class Tutor {
    private final String tutorid;
    private String tutorlevel;
    private final String tutorname;
    private String tutorgender;
    private String tutoraddress;
    private String tutorsubject;
    private String tutorph;
    private String tutoremail;
    
    public Tutor(String tutorid, String tutorlevel, String tutorname, String tutorgender, String tutoraddress, String tutorsubject, String tutorph, String tutoremail) {
        this.tutorid = tutorid;
        this.tutorlevel = tutorlevel;
        this.tutorname = tutorname;
        this.tutorgender = tutorgender;
        this.tutoraddress = tutoraddress;
        this.tutorsubject = tutorsubject;
        this.tutorph = tutorph;
        this.tutoremail = tutoremail;
    }

    // Getters for Tutor Model
    public String getTutorid() {
        return tutorid;
    }

    public String getTutorlevel() {
        return tutorlevel;
    }

    public String getTutorname() {
        return tutorname;
    }

    public String getTutorgender() {
        return tutorgender;
    }

    public String getTutoraddress() {
        return tutoraddress;
    }

    public String getTutorsubject() {
        return tutorsubject;
    }

    public String getTutorph() {
        return tutorph;
    }

    public String getTutoremail() {
        return tutoremail;
    }

    // Setters for Tutor Model
    public void setTutorlevel(String tutorlevel) {
        this.tutorlevel = tutorlevel;
    }

    public void setTutorgender(String tutorgender) {
        this.tutorgender = tutorgender;
    }

    public void setTutoraddress(String tutoraddress) {
        this.tutoraddress = tutoraddress;
    }

    public void setTutorsubject(String tutorsubject) {
        this.tutorsubject = tutorsubject;
    }

    public void setTutorph(String tutorph) {
        this.tutorph = tutorph;
    }

    public void setTutoremail(String tutoremail) {
        this.tutoremail = tutoremail;
    }

}
