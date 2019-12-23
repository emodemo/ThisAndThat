package com.aoping.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer implements Runnable {

	private int port;
	private boolean isStopped;
	private ServerSocket serverSocket;

	public SingleThreadServer(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		synchronized (this) {
			
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + port, e);
		}
		while (isStopped == false) {
			Socket clientSocket = null;
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				if (isStopped) {
					System.out.println("Server Stopped");
					return;
				}
				throw new RuntimeException("Error accepting client conection.", e);
			}
			try {
				acceptClienConnection(clientSocket);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// go to next request
			}
		}
		}
	}

	public synchronized void stop() {
		
		isStopped = true;
		try {
			serverSocket.close();
		}catch (Exception e) {
			throw new RuntimeException("Error closing server.", e);
		}
	}
	
	private void acceptClienConnection(Socket socket) {

		try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {

			long currentTime = System.currentTimeMillis();
			String respBody = "<html><body>" + " SingleThreadServer: " + currentTime + "</body></html>";
			String respHeader = "HTTP/1.1 200 OK\r\n" + "Content-Type: text\\html; charset=UTF-8" + "\r\n"
					+ "Content-Length: " + respBody.length() + "\r\n\r\n";
			out.write(respHeader.getBytes("UTF-8"));
			out.write(respBody.getBytes("UTF-8"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
