package com.aoping.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

	private Socket socket;
	
	public WorkerRunnable(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		acceptClienConnection();
	}

	private void acceptClienConnection() {

		try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {

			long currentTime = System.currentTimeMillis();
			String respBody = "<html><body>" + " MultiThreadServer: " + currentTime + " " + Thread.currentThread().getName() + "</body></html>";
			String respHeader = "HTTP/1.1 200 OK\r\n" + "Content-Type: text\\html; charset=UTF-8" + "\r\n"
					+ "Content-Length: " + respBody.length() + "\r\n\r\n";
			out.write(respHeader.getBytes("UTF-8"));
			out.write(respBody.getBytes("UTF-8"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
