package com.test.test;

import io.netty.channel.socket.SocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.test.netty.NettyChannelMap;

public class SToC {

	public static void main(String[] args) throws IOException {
		
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		SocketChannel channel = NettyChannelMap.get("jiedian1");
		
		String line = null;
		while((line=bufr.readLine())!=null){
			
			if("over".equals(line))
				break;
			
			if (channel != null) {
				channel.writeAndFlush(line);
			}
			System.out.println(channel);
			System.out.println(line);
		}
		
		System.out.println("quit");
	}
}
