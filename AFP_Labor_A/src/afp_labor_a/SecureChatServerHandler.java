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
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

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
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
//        if ("megkapodott".equals(msg.toLowerCase())) {
//                AFP_Labor_A.megkapta = true;
//                }

        String[] msgsplit = {""};

        for (Channel c: channels) {
            //c.writeAndFlush("fakkk");
            if (c == ctx.channel())
//            if (c != ctx.channel()) {
//                //for (;;) {
//                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
//               // }
//            } else
                
                
            {
                msgsplit = msg.split("\\$\\$\\$");
                
                if ("bejelentkezes".equals(msgsplit[0])) {
                
                          AFP_Labor_A.db.connect();
//                          AFP_Labor_A.db.login(msgsplit[1], msgsplit[2]);
                    //c.writeAndFlush(msg + " " + msgsplit[0] + " " + msgsplit[1] + '\n');
//                    c.writeAndFlush("bejelentkezesjo" + '\n');
                      c.writeAndFlush("bejelentkezes" + "$$$" + AFP_Labor_A.db.login(msgsplit[1], msgsplit[2]) + "$$$" + '\n');
                }
                
                if ("regisztracio".equals(msgsplit[0])){
                AFP_Labor_A.db.connect();
                c.writeAndFlush("regisztacio" + "$$$" + AFP_Labor_A.db.reg_insert(msgsplit[1], msgsplit[2]) + "$$$" + '\n');
                
                }
                
              if ("listazas".equals(msgsplit[0])){
                AFP_Labor_A.db.connect();
                c.writeAndFlush("listazas" + "$$$" + AFP_Labor_A.db.list() + "$$$" + '\n');
                
                }
                if ("dolgozofelvitel".equals(msgsplit[0])){
                AFP_Labor_A.db.connect();
                c.writeAndFlush("dolgozofelvitel" + "$$$" + AFP_Labor_A.db.dolgozo_insert(msgsplit[1], Short.parseShort(msgsplit[2]), Integer.parseInt(msgsplit[3]), msgsplit[4], msgsplit[5]) + "$$$" + '\n');
                System.out.println("dolgozo_insert meghívása lefut!");
                
                }
                //AFP_Labor_A.db.connect();
                //for (;;) {
                    //JOptionPane.showMessageDialog(null, msg, "Sikerestttttt", JOptionPane.PLAIN_MESSAGE);
//                c.writeAndFlush("[you] " + msg + '\n');
                  c.writeAndFlush(msg + " " + msgsplit[0] + " "
//                          + msgsplit[1]
                          + '\n');
                  
                //}
            }
            
        }
//JOptionPane.showMessageDialog(null, msg, "msg", JOptionPane.PLAIN_MESSAGE);
        // Close the connection if the client has sent 'bye'.
        
        
        if ("bye".equals(msg.toLowerCase())) {
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

