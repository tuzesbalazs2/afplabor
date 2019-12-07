/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tóth_Zsolt
 */
public class AFP_Labor_A_Kliens {
    
    

    public static final String DB_ADDRESS = "jdbc:mysql://85.10.205.173:3306/afplabor_rft";
    public static final String DB_NAME = "afplabor_rft";
    public static final String DB_USERNAME = "felhasznalo1";
    public static final String DB_PASSWORD = "abcdefgh";
    //public static String fukk = "";
    //public static String[] uzenet ={""};
    public static List<String> uzenet = new ArrayList<>();
    //public static InputStream stream = new ByteArrayOutputStream(fukk.getBytes(StandardCharsets.UTF_8));
    //public static InputStream stream = new ByteArrayInputStream(fukk.getBytes(StandardCharsets.UTF_8));
    public static boolean messagenez = false;
    public static Database db = new Database(DB_ADDRESS, DB_NAME,
            DB_USERNAME, DB_PASSWORD);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainPage m = new MainPage();
        m.setVisible(true);
        
        try {
            uzenet.add("x");
            //String fukkk = "affff";
            //String fukkk = "afff";
       //AFP_Labor_A_Kliens.stream=new ByteArrayInputStream(fukkk.getBytes(StandardCharsets.UTF_8));
            //stream.write(fukk.getBytes(Charset.forName("UTF-8")));
            //EchoServer echoServer = new EchoServer();
            //echoServer.main();
            //EchoServer.main();
            //EchoServer.echo();//echo();
            //EchoServer.main(args);
            SecureChatClient.main(args);
            //DiscardServer.main(args);
        } catch (Exception ex) {
            Logger.getLogger(AFP_Labor_A_Kliens.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
