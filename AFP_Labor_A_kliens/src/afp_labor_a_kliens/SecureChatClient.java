/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afp_labor_a_kliens;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Tüzes
 */

/**
 * Simple SSL chat client modified from {@link TelnetClient}.
 */
public final class SecureChatClient {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx = SslContextBuilder.forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new SecureChatClientInitializer(sslCtx));

            // Start the connection attempt.
            Channel ch = b.connect(HOST, PORT).sync().channel();

            // Read commands from the stdin.
            ChannelFuture lastWriteFuture = null;
            //BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                //JOptionPane.showMessageDialog(null, AFP_Labor_A_Kliens.messagenez, "nez", JOptionPane.PLAIN_MESSAGE);
              //if (AFP_Labor_A_Kliens.messagenez == true){
                //String line = in.readLine();
                String line = AFP_Labor_A_Kliens.fukk;
                if ("".equals(line.toLowerCase())) {
                    continue;
                }
                //if (line == null) {
                //    break;
                //}
                //JOptionPane.showMessageDialog(null, AFP_Labor_A_Kliens.fukk, "line", JOptionPane.PLAIN_MESSAGE);
                // Sends the received line to the server.
                lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("fukk".equals(line.toLowerCase())) {
                    lastWriteFuture = ch.writeAndFlush(line + "++++");
                }
                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }
                //AFP_Labor_A_Kliens.messagenez = false;
                //break;
              //}
                AFP_Labor_A_Kliens.fukk = "";
            }

            // Wait until all messages are flushed before closing the channel.
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}

