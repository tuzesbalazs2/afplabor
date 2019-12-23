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

    public static MessageHandler mh = new MessageHandler();

    public static void main(String[] args) {
        MainPage m = new MainPage();
        m.setVisible(true);
        
        try {

            Client.main(args);

        } catch (Exception ex) {
            Logger.getLogger(AFP_Labor_A_Kliens.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
