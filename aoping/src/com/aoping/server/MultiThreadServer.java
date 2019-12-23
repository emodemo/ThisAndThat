package com.aoping.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable {

	private int port;
	private boolean isStopped;
	private ServerSocket serverSocket;

	public MultiThreadServer(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		synchronized (this) {
			Thread.currentThread();
		}
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
			WorkerRunnable runnable = new WorkerRunnable(clientSocket);
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}

	public synchronized void stop() {

		isStopped = true;
		try {
			serverSocket.close();
		} catch (Exception e) {
			throw new RuntimeException("Error closing server.", e);
		}
	}

}