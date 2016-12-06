package com.test.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyContextListener implements ServletContextListener {

	Thread GSMSocketServer = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (GSMSocketServer != null){
			GSMSocketServer.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		GSMSocketServer = new Thread(new SocketServer(8888));
		GSMSocketServer.start();
	}
}
