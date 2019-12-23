package com.aoping.server;

public class ServerTest {

	public static void main(String[] args) {
		//SingleThreadServer server = new SingleThreadServer(9000);
		//MultiThreadServer server = new MultiThreadServer(9000);
		MultiThreadServerThreadPool server = new MultiThreadServerThreadPool(9000);
		new Thread(server).start();
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
