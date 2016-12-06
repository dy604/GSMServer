package com.app.controller;

import io.netty.channel.socket.SocketChannel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.netty.NettyChannelMap;

@Controller
public class TestController {
	
	@RequestMapping(value = "/test.html", method = RequestMethod.GET)
	@ResponseBody
	public String getAll(){
		SocketChannel channel = NettyChannelMap.get("jiedian1");
		String res = "channal=";
		if (channel != null) {
			System.out.println("yunxing");
			channel.writeAndFlush("[you]wos#$");
			res += channel.toString();
		}
		return res;
	}
}
