/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;

/**
 *
 * @author User
 */
public class EnglishRequest extends Request {
    public EnglishRequest(String studentId, String reason) {
        super(studentId, "English", reason);
    }

    @Override
    public String getRequestType() {
        return "English Request";
    }

    @Override
    public boolean isValid() {
        // Simple validation for English
        return reason != null && !reason.trim().isEmpty();
    }
}