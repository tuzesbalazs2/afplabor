package afp_labor_a;

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
 * @author Tüzes
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
        try {
            this.connect();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(dbaddress, dbusername, dbpassword);

            if (conn != null) {
                System.out.println("Az adatbázishoz való kapcsolódás sikeres");
            }

        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"Hiba az adatbázishoz kapcsolódáskor", "Hiba", JOptionPane.ERROR_MESSAGE);
              System.out.println("Hiba az adatbázishoz kapcsolódáskor");
        }
    }
    
    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null,"Hiba a kapcsolat zárásakor", "Hiba", JOptionPane.ERROR_MESSAGE);
              System.out.println("Hiba a kapcsolat zárásakor");
        }
    }

    public String login(String username, String password) throws ClassNotFoundException {

        String q_username = null;
        String q_password = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM felhasznalo WHERE username='"
                    + username + "' AND password='" + password + "'");

            while (rs.next()) {

                int id = rs.getInt("id");
                q_username = rs.getString("username");
                q_password = rs.getString("password");

            }


             if ((q_username.equals((String)username)) && (q_password.equals((String)password))) {
                 
                 System.out.println("Sikeres bejelentkezés!");
                 return "bejelentkezesjo";                
             }
             
             else {return "bejelentkezesrossz";}

            

        } catch (Exception e) {
//              JOptionPane.showMessageDialog(null,e);
              System.out.println("Hibás felhasználónév vagy jelszó");
            return "bejelentkezeshiba";
            
        }

        
    }

    public void select(String q) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            //TODO..
        } catch (Exception e) {
            System.out.println("Hiba történt");
        }
    }
    
    public String list() throws ClassNotFoundException {
        try {
            String lista = "";
            //String l = "";
            Statement st = AFP_Labor_A.db.conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, dolgozo.nev, szul_ev, fizetes, varos, utca_hsz, irsz FROM dolgozo LEFT JOIN varos ON dolgozo.varos=varos.nev");
            String iranyitoszam = "ismeretlen";
            while (rs.next()) {
                if (rs.getInt("irsz") == 0)
                {
                iranyitoszam = "ismeretlen";
                }
                else
                {
                iranyitoszam = rs.getString("irsz");
                }
                lista += (rs.getInt("id") + ", Név: " + rs.getString("nev") + ", Szül. év: " + rs.getInt("szul_ev") + ", Fizetés: " + rs.getInt("fizetes") + ", Város: " + rs.getString("varos") + ", Utca, házszám: " + rs.getString("utca_hsz") + ", Irányítószám: " + iranyitoszam + "$$$");
            }
            //String l = String.join(",", lista);
            return "listajo"+"$$$"+lista;
        } catch (Exception e) {

            System.out.println("Hiba történt a listázás közben");
            return "listahiba";
        }
    }
    
    public String employee_page(int ind) throws ClassNotFoundException {
        try {
            //String dolgozo = "";
            //String l = "";
            //JOptionPane.showMessageDialog(null,ind);
            //ind = 4;
            Statement st = AFP_Labor_A.db.conn.createStatement();
            //ResultSet rs = st.executeQuery("SELECT id, nev, szul_ev, fizetes, varos, utca_hsz FROM dolgozo WHERE id="+ind);
            ResultSet rs = st.executeQuery("SELECT id, nev, szul_ev, fizetes, varos, utca_hsz FROM dolgozo LIMIT "+ind+",1");
            rs.next();
            //String l = String.join(",", lista);
            return "dolgozoadatjo"+"$$$"+(rs.getString("nev")+"$$$"+Integer.toString(rs.getInt("szul_ev"))+"$$$"+Integer.toString(rs.getInt("fizetes"))+"$$$"+rs.getString("varos")+"$$$"+rs.getString("utca_hsz"));
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null,e);
            System.out.println(e);
            return "dolgozoadathiba";
        }
    }

    public String dolgozo_delete(int ind) throws ClassNotFoundException {
        try {
            PreparedStatement pstmt = AFP_Labor_A.db.conn.prepareStatement("DELETE FROM dolgozo WHERE id IN (SELECT id FROM (SELECT id FROM dolgozo ORDER BY id ASC LIMIT ?, 1) tmp)");
    pstmt.setInt(1, ind);
    pstmt.executeUpdate();

             return "dolgozotoroljo";
        } catch (Exception e) {
            System.out.println("Hiba a törlésnél!");  

        } return "dolgozotorolhiba";
    }

    public String dolgozo_insert(String nev, short szul_ev, int fizetes, String varos, String utca_hsz) throws ClassNotFoundException {
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
//               return rowCount;
               return "dolgozofelviteljo";
           }         
           
        } catch (Exception e) {
            System.out.println("Hiba a feltöltéssel!");

        }
//        return 0;
          return "dolgozofelvitelhiba";
    }

    public String dolgozomodosit(String[] msgsplit) throws ClassNotFoundException {
        try {
//            int aff = 4;//Integer.parseInt(msgsplit[6]);
            //int aff = Integer.parseInt(msgsplit[6]);
            PreparedStatement pstmt = AFP_Labor_A.db.conn.prepareStatement("UPDATE dolgozo SET nev = ?, szul_ev = ?, fizetes = ?, varos = ?, utca_hsz = ? WHERE id IN (SELECT id FROM (SELECT id FROM dolgozo ORDER BY id ASC LIMIT ?, 1) tmp)");
    pstmt.setString(1, msgsplit[1]);
    pstmt.setShort(2, Short.parseShort(msgsplit[2]));
    pstmt.setInt(3, Integer.parseInt(msgsplit[3]));
    pstmt.setString(4, msgsplit[4]);
    pstmt.setString(5, msgsplit[5]);
    pstmt.setInt(6, Integer.parseInt(msgsplit[6]));
    pstmt.executeUpdate();
//JOptionPane.showMessageDialog(null,"Sikeres módosítás!");
//System.out.println("Sikeres módosítás!");
//return (msgsplit[0] + "$$$" + msgsplit[1] + "$$$" + msgsplit[2] + "$$$" + msgsplit[3] + "$$$" + msgsplit[4] + "$$$" + msgsplit[5] + "$$$" + msgsplit[6] + "$$$" + '\n');
return "dolgozomodositjo";
            } catch (Exception e) {
              //System.out.println(e.getMessage());
              return "dolgozomodosíthiba";
              //JOptionPane.showMessageDialog(null, "Hiba: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String reg_insert(String username, String password) throws ClassNotFoundException {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Statement st = this.conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT COUNT(*) AS rc FROM felhasznalo WHERE username='" + username +"'");            
            rs.next();
            int rc = rs.getInt("rc");
            rs.close();
            if(rc > 0) {
                return "regisztraciorossz";
            } 
            
            if(st.executeUpdate("INSERT INTO felhasznalo (username, password) VALUES ('" + username + "', '" + password + "')" ) > 0) {
               return "regisztraciojo";
            }
            
        } catch (Exception e) {

            System.out.println("Hiba a beszúrásnál!");
        }
        return "regisztraciohiba";
    }
}
