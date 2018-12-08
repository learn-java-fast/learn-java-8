package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args){
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while(true){
				String line = reader.readLine();
				if (line != null) {
					try (SocketChannel socketChannel = SocketChannel.open()) {
						logger.info("Channel open");
						socketChannel.connect(new InetSocketAddress("localhost",9999));
						logger.info("Channel connect");
					    ByteBuffer buf = ByteBuffer.allocate(1024);
					    buf.clear();
					    buf.put(line.getBytes());
					    buf.flip();
						try {
							while (buf.hasRemaining()){
								int bytes = socketChannel.write(buf);
								logger.info(bytes + " bytes written");
								socketChannel.close(); // <-- this breaks the while(true).... 
							}
					    } catch (Exception e) {
							// TODO: handle exception
						};
						logger.info("message "+line);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
}
