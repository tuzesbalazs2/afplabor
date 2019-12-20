package afp_labor_a_kliens;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */
public class MessageHandler {
    
    public Employee_list empList;
    public Employee_page empage;

    public MessageHandler() {

    }


      public void login(String[] bejelentkeztet) throws ClassNotFoundException {

        try {
            if ("bejelentkezesjo".equals((String)bejelentkeztet[1])) {

                empList = new Employee_list();
                empList.setVisible(true);
                System.out.println("Sikeres bejelentkezés!");
            }         

        } catch (Exception e) {
            System.out.println("Hibás felhasználónév vagy jelszó");
            JOptionPane.showMessageDialog(null,"Hibás felhasználónév vagy jelszó");
        }
    }

    
    public void dolgozoldal(int ind) throws ClassNotFoundException {
        try {
            //JOptionPane.showMessageDialog(null,ind);
            empage = new Employee_page();
            empage.setVisible(true);
            empage.lekerdez(ind);
            //empage.ind = ind;
        } catch (Exception e) {
            System.out.println("Hiba történt");
            JOptionPane.showMessageDialog(null,"Hiba történt");
        }
    }
    
    

    public void dolgozo_delete(String[] dolgozotorles) throws ClassNotFoundException {
        try {

           if ("dolgozotoroljo".equals((String)dolgozotorles[1])) {

           System.out.println("Sikeres törlés");
           JOptionPane.showMessageDialog(null, "Sikeres törlés", "Sikeres törlés", JOptionPane.PLAIN_MESSAGE);
           empage.dispose();
           AFP_Labor_A_Kliens.uzenet = "listazas";
            }        
           if ("dolgozotorolhiba".equals((String)dolgozotorles[1])) {

           System.out.println("Hiba a törlésnél!");
           JOptionPane.showMessageDialog(null, "Hiba a törlésnél!", "Hiba", JOptionPane.ERROR_MESSAGE);

            }      
            
        } catch (Exception e) {
            System.out.println("Hiba a törlésnél");
            JOptionPane.showMessageDialog(null,"Hiba a törlésnél", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void dolgozo_insert(String[] dolgozofelvisz) throws ClassNotFoundException {
        try {   

System.out.println("dolgozo_insert lefut!");

            if ("dolgozofelviteljo".equals((String)dolgozofelvisz[1])) {

                System.out.println("Sikeres feltöltés");
           JOptionPane.showMessageDialog(null, "Sikeres feltöltés", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
           AFP_Labor_A_Kliens.uzenet = "listazas";
            }        
            if ("dolgozofelvitelhiba".equals((String)dolgozofelvisz[1])) {

                System.out.println("Hiba a feltöltéssel!");
           JOptionPane.showMessageDialog(null, "Hiba a feltöltéssel!", "Hiba", JOptionPane.ERROR_MESSAGE);

            }      

           
        } catch (Exception e) {
            System.out.println("Hiba a feltöltéssel!");  
        JOptionPane.showMessageDialog(null, "Hiba a feltöltéssel!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        //return 0;
    }
    
        public void dolgozo_update(String[] dolgozomodosit) throws ClassNotFoundException {
        try {   

System.out.println("dolgozo_update lefut!");

            if ("dolgozomodositjo".equals((String)dolgozomodosit[1])) {

                System.out.println("Sikeres módosítás");
           JOptionPane.showMessageDialog(null, "Sikeres módosítás", "Sikeres módosítás", JOptionPane.PLAIN_MESSAGE);
           AFP_Labor_A_Kliens.uzenet = "listazas";
            }        
            if ("dolgozomodosíthiba".equals((String)dolgozomodosit[1])) {

                System.out.println("Hiba a módosítás közben!");
           JOptionPane.showMessageDialog(null, "Hiba a módosítás közben!", "Hiba", JOptionPane.ERROR_MESSAGE);

            }      

           
        } catch (Exception e) {
            System.out.println("Hiba a módosítás közben!");  
        JOptionPane.showMessageDialog(null, "Hiba a módosítás közben!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        //return 0;
    }

    
    public void reg_insert(String[] regisztraltat) throws ClassNotFoundException {
        try {           

            System.out.println("reg_insert lefut!");

            if ("regisztraciojo".equals((String)regisztraltat[1])) {

            System.out.println("Sikeres Regisztráció!");
                JOptionPane.showMessageDialog(null, "Sikeres Regisztráció!", "Sikeres regisztráció", JOptionPane.PLAIN_MESSAGE);

            }
            if ("regisztraciorossz".equals((String)regisztraltat[1])) {

            System.out.println("Sikertelen regisztráció: Már létezik ilyen felhasználónév.");
                JOptionPane.showMessageDialog(null, "Sikertelen regisztráció: Már létezik ilyen felhasználónév.", "Hiba", JOptionPane.ERROR_MESSAGE);

            }
            if ("regisztraciohiba".equals((String)regisztraltat[1])) {

            System.out.println("Hiba a beszúrásnál");
            JOptionPane.showMessageDialog(null,"Hiba a beszúrásnál", "Hiba", JOptionPane.ERROR_MESSAGE);

            }
            
        } catch (Exception e) {
            System.out.println("Hiba a beszúrásnál");
            JOptionPane.showMessageDialog(null,"Hiba a beszúrásnál", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        //return false;
    }
}
