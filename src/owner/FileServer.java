package owner;

import java.io.*;
import java.net.*;

public class FileServer {

	private static ServerSocket ss;

	public static void main(String[] args) throws IOException {
		// server is listening on port 8000
		System.out.println("Waiting for connection");
		ss = new ServerSocket(8000);

		// running infinite loop for getting
		// client request
		while (true) {
			Socket s = null;

			try {
				// socket object to receive incoming client requests
				s = ss.accept();

				System.out.println("Connection request from " + s.getInetAddress().getHostName());
				// obtaining input and out streams
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());

				System.out.println("Assigning new thread for this client");

				// create a new thread object
				Thread t = new FileOwner(s, dis, dos);

				// Invoking the start() method
				t.start();

			} catch (Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}

}
