package afp_labor_a_kliens;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */


public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        String[] msgsplit = {""};
        
        msgsplit = msg.split("\\$\\$\\$");
        
        if ("bejelentkezes".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.login(msgsplit);
        }
        if ("regisztracio".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.reg_insert(msgsplit);
                System.out.println("reg_insert meghívva a ClientHandler-ből!");
        }
        if ("listazas".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.empList.list(msgsplit);
        }
        if ("dolgozofelvitel".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.dolgozo_insert(msgsplit);
        }
        if ("dolgozotolt".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.empage.betolt(msgsplit);
        }
         if ("dolgozomodosit".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.dolgozo_update(msgsplit);
        }
        if ("dolgozotorol".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.mh.dolgozo_delete(msgsplit);
        }
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        cause.printStackTrace();
        ctx.close();
    }
}

