package owner;

import java.io.*;
import java.net.*;

public class FileServer {

	private static ServerSocket ss;

	public static void main(String[] args) throws IOException {
		System.out.println("Waiting for connection");
		ss = new ServerSocket(8000);
		while (true) {
			Socket s = null;

			try {
				s = ss.accept();
				System.out.println("Connection request from " + s.getInetAddress().getHostName());
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				System.out.println("Assigning new thread for this client");
				Thread t = new FileOwner(s, dis, dos);     
				t.start();

			} catch (Exception e) {
				s.close();
				e.printStackTrace();
			}
		}
	}

}
