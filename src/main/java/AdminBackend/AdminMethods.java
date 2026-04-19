/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminBackend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author User
 */
public class AdminMethods {
    public class AdminSession {
        private static String currentadminid;

        public static void setAdminid(String adminid) {
            currentadminid = adminid;
        }

        public static String getAdminid() {
            return currentadminid;
        }

        public static void clearSession() {
            currentadminid = null;
        }
    }

    public static String GetAdminName(String id) {
            try {
            FileReader fr = new FileReader("Text Files/admins.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String adminid = parts[0];
                String adminname = parts[1];
                
                if (adminid.equals(id)) {
                    return adminname;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
