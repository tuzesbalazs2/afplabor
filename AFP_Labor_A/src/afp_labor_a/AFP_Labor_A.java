/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

import io.netty.channel.ChannelHandlerContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Tóth_Zsolt
 */
public class AFP_Labor_A {
    
    public static void aaa(ChannelHandlerContext ctx){
        
        JOptionPane.showMessageDialog(null, "ehehe", "aaa", JOptionPane.PLAIN_MESSAGE);
    }
    public static void bbb(){JOptionPane.showMessageDialog(null, "aaahaaaaaaa", "aaa", JOptionPane.PLAIN_MESSAGE);}

    public static final String DB_ADDRESS = "jdbc:mysql://85.10.205.173:3306/afplabor_rft";
    public static final String DB_NAME = "afplabor_rft";
    public static final String DB_USERNAME = "felhasznalo1";
    public static final String DB_PASSWORD = "abcdefgh";
    
    public static Database db = new Database(DB_ADDRESS, DB_NAME,
            DB_USERNAME, DB_PASSWORD);
    /**
     * @param args the command line arguments
     */
    public static boolean megkapta = false;
    
    public static void main(String[] args) {
        MainPage m = new MainPage();
        m.setVisible(true);
        
        try {
            //EchoServer echoServer = new EchoServer();
            //echoServer.main();
            //EchoServer.main();
            //EchoServer.echo();//echo();
            SecureChatServer.main(args);
            //DiscardServer.main(args);
        } catch (Exception ex) {
            Logger.getLogger(AFP_Labor_A.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
