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


public final class Client {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {

        final SslContext sslCtx = SslContextBuilder.forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ClientInitializer(sslCtx));


            Channel ch = b.connect(HOST, PORT).sync().channel();


            ChannelFuture lastWriteFuture = null;

            for (;;) {


                String line = AFP_Labor_A_Kliens.uzenet;
                if ("".equals(line.toLowerCase())) {
                    continue;
                }

                lastWriteFuture = ch.writeAndFlush(line + "$$$" + "\r\n");


                if ("bye".equals(line.toLowerCase())) {
                    ch.closeFuture().sync();
                    break;
                }

                AFP_Labor_A_Kliens.uzenet = "";
            }


            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        } catch(Exception e){JOptionPane.showMessageDialog(null, "Nem lehet csatlakozni a kiszolgálóhoz!!!", "Hiba", JOptionPane.ERROR_MESSAGE);}
        finally {

            group.shutdownGracefully();
        }
    }
}

