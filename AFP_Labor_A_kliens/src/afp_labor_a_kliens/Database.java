/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tóth_Zsolt
 */
public class Database {

    private final String dbaddress;
    private final String dbusername;
    private final String dbpassword;
    
    Connection conn;
    

    public Database(String dbaddress, String database, String dbusername, String dbpassword) {
        this.dbaddress = dbaddress;
        this.dbusername = dbusername;
        this.dbpassword = dbpassword;
    }

    public void connect() throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(dbaddress, dbusername, dbpassword);

            if (conn != null) {
                System.out.println("Connected to the database");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hiba az adatbázishoz kapcsolódáskor", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"HIba a kapdcsolat zárásakor", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

//    public void login(String username, String password) throws ClassNotFoundException {
      public void login(String[] bejelentkeztet) throws ClassNotFoundException {

//        String q_username = null;
//        String q_password = null;
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Statement st = this.conn.createStatement();
//            ResultSet rs = st.executeQuery("SELECT * FROM felhasznalo WHERE username='"
//                    + username + "' AND password='" + password + "'");
//
//            while (rs.next()) {
//
//                int id = rs.getInt("id");
//                q_username = rs.getString("username");
//                q_password = rs.getString("password");
//
//            }

//            if ((q_username.equals((String)username)) && (q_password.equals((String)password))) {
        try {
            if ("bejelentkezesjo".equals((String)bejelentkeztet[1])) {

                Employee_list empList = new Employee_list();
                empList.setVisible(true);

            }         

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"HIbás felhasználónév vagy jelszó");
        }
    }

    public void select(String q) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            //TODO..
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hiba történt");
        }
    }

    public void delete(String q) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            //TODO..
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hiba a törlésnél", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int dolgozo_insert(String nev, short szul_ev, String varos, int fizetes, String utca_hsz) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            
           int rowCount = st.executeUpdate("INSERT INTO dolgozo (nev, szul_ev, fizetes, varos, utca_hsz)"
                    + " VALUES(" + "'" + nev + "'" + ", "
                                 + szul_ev + ", "
                                 + fizetes + ", "
                                 + "'" + varos + "'" + ", "
                                 + "'" + utca_hsz + "'" + ") ");
           
           if (rowCount > 0) {
               return rowCount;
           }         
           
        } catch (Exception e) {
            System.out.println("Hiba a feltöltéssel!");  
        JOptionPane.showMessageDialog(null, "Hiba a feltöltéssel!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public void update(String q) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hiba a módosításnál", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean reg_insert(String username, String password) throws ClassNotFoundException {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rc FROM felhasznalo WHERE username='" + username +"'");            
            rs.next();
            int rc = rs.getInt("rc");
            rs.close();
            if(rc > 0) {
                return false;
            } 
            
            if(st.executeUpdate("INSERT INTO felhasznalo (username, password) VALUES ('" + username + "', '" + password + "')" ) > 0) {
               return true;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hiba a beszúrásnál", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
