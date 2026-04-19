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

public class MathematicsRequest extends Request {
    public MathematicsRequest(String studentId, String reason) {
        super(studentId, "Mathematics", reason);
    }
    
    @Override
    public String getRequestType() {
        return "Math Request";
    }
    
    @Override
    public boolean isValid() {
        // Simple validation for Math
        return reason != null && !reason.trim().isEmpty();
    }
}
