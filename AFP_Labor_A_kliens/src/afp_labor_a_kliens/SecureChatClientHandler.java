/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //System.err.println(msg);
        //JOptionPane.showMessageDialog(null, "fakkk", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        //AFP_Labor_A_Kliens.fukk = "megkapodott";
       
       //String[] args = null;
       //SecureChatClient.main(args);
        JOptionPane.showMessageDialog(null, msg, "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        
        String[] msgsplit = {""};
        
        msgsplit = msg.split("\\$\\$\\$");
        
        if ("bejelentkezes".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.db.login(msgsplit);
        }
        if ("resgisztracio".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.db.reg_insert(msgsplit);
        }
        if ("listazas".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.db.empList.list(msgsplit);
        }
        if ("dolgozofelvitel".equals(msgsplit[0])) {
                AFP_Labor_A_Kliens.db.dolgozo_insert(msgsplit);
        }
        if ("dolgozotolt".equals(msgsplit[0])) {
                //Employee_page.
                AFP_Labor_A_Kliens.db.empage.betolt(msgsplit);
        }
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //JOptionPane.showMessageDialog(null, "fakkk", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        cause.printStackTrace();
        ctx.close();
    }
}

