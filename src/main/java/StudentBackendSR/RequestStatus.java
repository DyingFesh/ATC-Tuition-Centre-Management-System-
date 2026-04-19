/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentBackendSR;


/**
 *
 * @author User
 */
// enum RequestStatus is to define the constant String values
public enum RequestStatus {    
    Pending("Pending"),
    Accepted("Accepted"),
    Rejected("Rejected"),
    Cancelled("Cancelled");

    private final String value; 


    private RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RequestStatus fromString(String status) {
        for (RequestStatus rs : RequestStatus.values()) {
            if (rs.value.equalsIgnoreCase(status)) {
                return rs;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}

