/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author User
 */
public abstract class Request {
    protected String studentId;
    protected String subject;
    protected String reason;
    protected RequestStatus status;
    protected LocalDate dateRequested;
    protected LocalDate dateProcessed;
    
   public Request(String studentId, String subject, String reason) {
        this.studentId = studentId;
        this.subject = subject;
        this.reason = reason;
        this.status = RequestStatus.Pending;
        this.dateRequested = LocalDate.now();
        this.dateProcessed = null;
    }
     
    public abstract String getRequestType();
    public abstract boolean isValid();
    
    public boolean isPending() {
        return status == RequestStatus.Pending;
    }
    
    // This is to format the strings to be consistent to be written into the text file
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
        String formattedReason = reason.replace(",", ";");
        String requestedDate = dateRequested.format(formatter);
        String processedDate = (dateProcessed != null) ? 
            dateProcessed.format(formatter) : "null";
        
        return String.format("%s,%s,%s,%s,%s,%s", 
            studentId, subject, formattedReason, status, requestedDate, processedDate);
    }
    
    public String getStudentId() {
        return studentId; 
    }
    public String getSubject() {
        return subject; 
    }
    public String getReason() { 
        return reason;
    }
    public RequestStatus getStatus() { 
        return status;
    }
    public LocalDate getDateRequested() { 
        return dateRequested; 
    }
    public LocalDate getDateProcessed() { 
        return dateProcessed; 
    }
    
    public void setDateRequested(LocalDate date) { 
        this.dateRequested = date;
    }
    public void setDateProcessed(LocalDate date) { 
        this.dateProcessed = date; 
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
