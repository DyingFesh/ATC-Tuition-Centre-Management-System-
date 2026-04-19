/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;
import StudentBackendSR.Request;
import StudentBackendSR.RequestStatus;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author User
 */
public class RequestManager {
    private static final String SubjectEnrolment_file = "Text Files/SubjectEnrolmentRequest.txt";
    private List<Request> requests;
    
    public RequestManager() {
        this.requests = new ArrayList<>();
    }
    
    public void loadFromFile() {        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                
        try (BufferedReader reader = new BufferedReader(new FileReader(SubjectEnrolment_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String studentId = parts[0];
                String subject = parts[1];
                String reason = parts[2].replace(";", ",");
                String status = parts[3];
                String dateRequest = parts[4];
                String dateProcessed = parts[5];

                Request request = RequestFactory.createRequest(studentId, subject, reason);
                request.setStatus(RequestStatus.fromString(status));
                request.setDateRequested(LocalDate.parse(dateRequest, formatter));

                if (!dateProcessed.equals("null")) {
                    request.setDateProcessed(LocalDate.parse(dateProcessed, formatter));
                }

             requests.add(request);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // This is to add the new request into the text file
    public void appendToFile(Request request) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(SubjectEnrolment_file, true))) {
        writer.write(request.toFileFormat());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    
    // This is to add a new request to the request list
    public boolean addRequest(String studentId, String subject, String reason) {
    try {
        Request request = RequestFactory.createRequest(studentId, subject, reason);
        if (request.isValid()) {
            requests.add(request);
            appendToFile(request);  
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
    }
   
    // This is to Cancel Request
    public int cancelRequest(String studentId, String subject) {
        for (Request request : requests) {
            if (request.getStudentId().equals(studentId) && 
                request.getSubject().equals(subject)) {

                // Check if request is already cancelled
                if (request.getStatus() == RequestStatus.Cancelled) {
                    return -1; // Already cancelled
                }

                // Check if request is pending (can be cancelled)
                if (request.isPending()) {
                    request.setStatus(RequestStatus.Cancelled);
                    return 1; // Successfully cancelled
                }

                // Request exists but is not pending (maybe completed, etc.)
                return 0; // Cannot be cancelled
            }
        }
        return 2; // Request not found
    }

    // This will rewrite everything in the file that includes the changes made
    public void rewriteFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SubjectEnrolment_file))) {
            for (Request request : requests) {
                writer.write(request.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
