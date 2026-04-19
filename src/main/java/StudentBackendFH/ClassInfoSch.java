/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendFH;

/**
 *
 * @author User
 */
public class ClassInfoSch {
    private String teacherID;
    private String subject;
    private String room;
    private String day;
    private String startTime;
    private String endTime;

    public ClassInfoSch(String teacherID, String subject, String room, String day, String startTime, String endTime) {
        this.teacherID = teacherID;
        this.subject = subject;
        this.room = room;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    // This is to covert the datas into strings
    public String[] toRow() {
        return new String[] {
            teacherID,
            subject,
            room,
            day,
            startTime.replace(";", ":"),
            endTime.replace(";", ":")
        };
    }
    
    // Getters
    public String getTeacherID() { 
        return teacherID; 
    }
    public String getSubject() {
        return subject; 
    }
    public String getRoom() { 
        return room;
    }
    public String getDay() { 
        return day; 
    }
    public String getStartTime() { 
        return startTime;
    }
    public String getEndTime() { 
        return endTime;
    }

}

