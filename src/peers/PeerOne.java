package peers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PeerOne {
	
	 String root;
	 final String PEER_ID = "1";
	 
	public static void main(String[] args) throws IOException, InterruptedException {
		
		PeerOne P1 = new PeerOne();
		String path = Paths.get("").toAbsolutePath().toString();
		String root =  path+"\\src\\peers\\Peer1File\\";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer1File"); 
		boolean su = directory.mkdir();
		P1.connectNeighbours(root);
	}

	public static void makeDir() {
		
	}

	public void connectNeighbours(String root) {
		ConnectFO fileO = new ConnectFO(root,PEER_ID);
		Thread fo = new Thread(fileO);
		fo.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalChunks = fileO.getTotalChunks();
		System.out.println("Finally i got totchunks as: " + totalChunks );
		ConnectUN p1UN = new ConnectUN(9000,totalChunks,root,PEER_ID);
		Thread un = new Thread(p1UN);
		un.start();
		if(p1UN.startPeer) {
			Thread dn = new Thread(new ConnectDN("127.0.0.1",9004,totalChunks,root,PEER_ID));
			dn.start();
		}
		while(true) {
			
		}
	}
	
}