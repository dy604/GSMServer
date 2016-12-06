package com.test.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务端 channel
 * 
 * @author waylau.com
 * @date 2015-2-16
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> { // (1)
	
	/**
	 * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
	 * A closed Channel is automatically removed from the collection,
	 */
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private static int i = 0;
	
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        
        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
        
        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");
        
        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }
  
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"在线");
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"掉线");
	}
	
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
    	Channel incoming = ctx.channel();
		System.out.println("SimpleChatClient:"+incoming.remoteAddress()+"异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String s)
			throws Exception {
		// TODO Auto-generated method stub
		Channel incoming = ctx.channel();
		/*for (Channel channel : channels) {
            if (channel != incoming){
            	System.out.println("line+++"+s.toUpperCase());
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            } else {
            	System.out.println("line+++"+s);
            	channel.writeAndFlush("[you]" + s.toUpperCase() + "\n");
            }
        }*/
		System.out.println("line+++"+s);
		System.out.println("NettyChannelMap + " + NettyChannelMap.get("jiedian1"));
		i++;
		incoming.writeAndFlush("[you]abc456#$");
	}
}