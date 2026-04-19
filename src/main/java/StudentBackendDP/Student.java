/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package StudentBackendDP;
import StudentBackendFH.GetStudentInformation;
import java.io.FileNotFoundException;

/**
 *
 * @author User
 */
public class Student {
   
    public static void main(String[] args) throws FileNotFoundException {        
        GetStudentInformation student = new GetStudentInformation("S001");
        System.out.println("Name: " + student.getStudentName());
        System.out.println(String.join(", ", student.getSubject()));
        for (String sub : student.getSubject()) {
            System.out.println("Subject: " + sub);
        }   
            
        System.out.println(" ");    
        
    
    }
}

