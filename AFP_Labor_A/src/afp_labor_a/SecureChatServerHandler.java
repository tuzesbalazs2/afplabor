/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<ArrayList> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        //ctx.writeAndFlush("arghghgfhh\n");
                        //ctx.writeAndFlush(
                        //        "Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
                        //ctx.writeAndFlush(
                        //        "Your session is protected by " +
                        //                ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                        //                " cipher suite.\n");
                            //
                        channels.add(ctx.channel());
                    }
        });
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ArrayList msg) throws Exception {
        // Send the received message to all channels but the current one.
        //if ("megkapodott".equals(msg.toLowerCase())) {
        //        AFP_Labor_A.megkapta = true;
        //        }
        JOptionPane.showMessageDialog(null, msg.get(0), "Sikerestttttt", JOptionPane.PLAIN_MESSAGE);
        for (Channel c: channels) {
            //c.writeAndFlush("fakkk");
            if (c == ctx.channel())
//            if (c != ctx.channel()) {
//                //for (;;) {
//                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
//               // }
//            } else
            {
                if ("afff".equals(msg.get(0))) {
                //ű
                //c.writeAndFlush("fakkk\n");
                //AFP_Labor_A.aaa(ctx);
                    //String fukk = "fffffffffff";
                     //InputStream stream = new ByteArrayInputStream(fukk.getBytes(StandardCharsets.UTF_8));
                     //System.setIn("fukk");
                    //System.setIn(stream);
                }
                if ("fukk".equals(msg.get(0))) {
                //    AFP_Labor_A.bbb();
                }
                //AFP_Labor_A.db.connect();
                //for (;;) {
                    
                c.writeAndFlush("[you] " + msg.get(0) + '\n');
                //}
            }
            
        }
//JOptionPane.showMessageDialog(null, msg, "msg", JOptionPane.PLAIN_MESSAGE);
        // Close the connection if the client has sent 'bye'.
        
        
        if ("bye".equals(msg.get(0))) {
            ctx.close();
        }
//        if ("a".equals(msg.toLowerCase())) {
//            //try{JOptionPane.showMessageDialog(null, msg, "msg", JOptionPane.PLAIN_MESSAGE);}catch(Exception e) {System.out.println(e);}
//               for (Channel c: channels) {
//               
//               
//                   c.writeAndFlush("fakk");
//              
//            }
//            //ctx.writeAndFlush("fakk");
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

