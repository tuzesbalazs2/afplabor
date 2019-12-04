/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

/**
 *
 * @author Tóth_Zsolt
 */
public class AFP_Labor_A_Kliens {
    
    

    public static final String DB_ADDRESS = "jdbc:mysql://85.10.205.173:3306/afplabor";
    public static final String DB_NAME = "afplabor";
    public static final String DB_USERNAME = "zsolt1";
    public static final String DB_PASSWORD = "abcdefgh";
    
    public static Database db = new Database(DB_ADDRESS, DB_NAME,
            DB_USERNAME, DB_PASSWORD);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainPage m = new MainPage();
        m.setVisible(true);
        
        
    }
    
}
