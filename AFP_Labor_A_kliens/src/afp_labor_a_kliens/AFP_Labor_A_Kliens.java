package afp_labor_a_kliens;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tüzes
 */
public class AFP_Labor_A_Kliens {
    
    

    
    public static String uzenet = "";
    //public static InputStream stream = new ByteArrayOutputStream(uzenet.getBytes(StandardCharsets.UTF_8));
    //public static InputStream stream = new ByteArrayInputStream(uzenet.getBytes(StandardCharsets.UTF_8));
    
    public static MessageHandler mh = new MessageHandler();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainPage m = new MainPage();
        m.setVisible(true);
        
        try {
            //String fukkk = "affff";
            //String fukkk = "afff";
       //AFP_Labor_A_Kliens.stream=new ByteArrayInputStream(fukkk.getBytes(StandardCharsets.UTF_8));
            //stream.write(uzenet.getBytes(Charset.forName("UTF-8")));
            //EchoServer echoServer = new EchoServer();
            //echoServer.main();
            //EchoServer.main();
            //EchoServer.echo();//echo();
            //EchoServer.main(args);
            Client.main(args);
            //DiscardServer.main(args);
        } catch (Exception ex) {
            Logger.getLogger(AFP_Labor_A_Kliens.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
