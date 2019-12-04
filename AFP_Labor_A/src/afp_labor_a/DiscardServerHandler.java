/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */


/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
    try {
        while (in.isReadable()) { // (1)
            //System.out.print((char) in.readByte());
            JOptionPane.showMessageDialog(null, (char) in.readByte(), "Sikeres feltöltés", JOptionPane.PLAIN_MESSAGE);
        }
    } finally {
        ReferenceCountUtil.release(msg); // (2)
    }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}