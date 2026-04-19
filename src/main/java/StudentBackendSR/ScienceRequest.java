/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;

import StudentBackendSR.Request;

/**
 *
 * @author User
 */
public class ScienceRequest extends Request {
    public ScienceRequest(String studentId, String reason) {
        super(studentId, "Science", reason);
    }
    
    @Override
    public String getRequestType() {
        return "Science Request";
    }
    
    @Override
    public boolean isValid() {
        // Simple validation for Science
        return reason != null && !reason.trim().isEmpty();
    }
}