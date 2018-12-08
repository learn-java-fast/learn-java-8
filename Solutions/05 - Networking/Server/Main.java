package com.example.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main (String[] args){
		logger.info("Starting Server");

		try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
			logger.info("Channel open");
			serverSocketChannel.socket().bind(new InetSocketAddress(9999));
			logger.info("Channel bound");
			while(true){
				SocketChannel socketChannel = serverSocketChannel.accept();

				Runnable handler = ()-> {
				    logger.info("getting a message");
				    ByteBuffer buf = ByteBuffer.allocate(1024); 

				    try {
						int bytes = socketChannel.read(buf);
						logger.info(bytes + " bytes read: "+ new String(buf.array(),"ASCII"));
					    socketChannel.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
			    };
			    new Thread(handler).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
