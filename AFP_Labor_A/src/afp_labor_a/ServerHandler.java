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
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        channels.add(ctx.channel());
                    }
        });
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        String[] msgsplit = {""};

        for (Channel c: channels) {
            
            if (c == ctx.channel())

                
                
            {
                msgsplit = msg.split("\\$\\$\\$");
                
                if ("bejelentkezes".equals(msgsplit[0])) {
                

                      c.writeAndFlush("bejelentkezes" + "$$$" + AFP_Labor_A.db.login(msgsplit[1], msgsplit[2]) + "$$$" + '\n');
                }
                
                if ("regisztracio".equals(msgsplit[0])){
                c.writeAndFlush("regisztracio" + "$$$" + AFP_Labor_A.db.reg_insert(msgsplit[1], msgsplit[2]) + "$$$" + '\n');
                
                }
                
              if ("listazas".equals(msgsplit[0])){
                c.writeAndFlush("listazas" + "$$$" + AFP_Labor_A.db.list() + "$$$" + '\n');
                
                }
                if ("dolgozofelvitel".equals(msgsplit[0])){
                c.writeAndFlush("dolgozofelvitel" + "$$$" + AFP_Labor_A.db.dolgozo_insert(msgsplit[1], Short.parseShort(msgsplit[2]), Integer.parseInt(msgsplit[3]), msgsplit[4], msgsplit[5]) + "$$$" + '\n');
                System.out.println("dolgozo_insert meghívása lefut!");
                
                }
                if ("dolgozotolt".equals(msgsplit[0])){
                c.writeAndFlush("dolgozotolt" + "$$$" + AFP_Labor_A.db.employee_page(Integer.parseInt(msgsplit[1])) + "$$$" + '\n');
                
                }
                if ("dolgozomodosit".equals(msgsplit[0])){
                c.writeAndFlush("dolgozomodosit" + "$$$" + AFP_Labor_A.db.dolgozomodosit(msgsplit) + "$$$" + '\n');
                      
                }
                                
                if ("dolgozotorol".equals(msgsplit[0])){
                c.writeAndFlush("dolgozotorol" + "$$$" + AFP_Labor_A.db.dolgozo_delete(Integer.parseInt(msgsplit[1])) + "$$$" + '\n');
                
                }

            }
            
        }

        
        
        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

