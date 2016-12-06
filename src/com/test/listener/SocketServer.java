package com.test.listener;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.test.netty.SimpleChatServerInitializer;

public class SocketServer implements Runnable {

	private int port = 8888;
	
	public SocketServer(int port) {
		this.port = port;
	}
	
	/**
	 * 1，serversocket服务。
	 * 2，获取socket对象。
	 * 3，源：socket，读取客户端发过来的需要转换的数据。
	 * 4，目的：显示在控制台上。
	 * 5，将数据转成大写发给客户端。 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*try {
			while (true) {
				//1，serversocket服务。
				ServerSocket ss = new ServerSocket(port);
				//2,获取socket对象。
				Socket s = ss.accept();
				//获取ip.
				String ip = s.getInetAddress().getHostAddress();
				System.out.println(ip+"......connected");
				
				//3,获取socket读取流，并装饰。 
				BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
				//4,获取socket的输出流，并装饰。
				PrintWriter out = new PrintWriter(s.getOutputStream(),true);
				
				
				String line = null;
				
				while((line=bufIn.readLine())!=null){
					System.out.println("line+++"+line);
					out.println(line.toUpperCase());
				}
				
				System.out.println("guanbi");
				s.close();
				ss.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new SimpleChatServerInitializer())  //(4)
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            
    		System.out.println("SimpleChatServer 启动了");
    		
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync(); // (7)

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
    		System.out.println("SimpleChatServer 关闭了");
        }
	}

}
