/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */

/**
 * Handles a client-side channel.
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<ArrayList> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ArrayList msg) throws Exception {
        //System.err.println(msg);
        //JOptionPane.showMessageDialog(null, "fakkk", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        //AFP_Labor_A_Kliens.fukk = "megkapodott";
       
       //String[] args = null;
       //SecureChatClient.main(args);
        JOptionPane.showMessageDialog(null, msg.get(0), "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //JOptionPane.showMessageDialog(null, "fakkk", "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        cause.printStackTrace();
        ctx.close();
    }
}

