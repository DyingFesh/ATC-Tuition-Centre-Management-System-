/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;

/**
 *
 * @author User
 */
public class RequestFactory {
    public static Request createRequest(String studentId, String subject, String reason) {
        switch (subject) {
            case "Science":
                return new ScienceRequest(studentId, reason);
            case "English":
                return new EnglishRequest(studentId, reason);
            case "Mathematics":
                return new MathematicsRequest(studentId, reason);
            default:
                throw new IllegalArgumentException("Unknown subject: " + subject);
        }
    }
}
