package peers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PeerTwo {
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerTwo p2 = new PeerTwo();
		p2.connectNeighbours();
	}
	
	public void connectNeighbours() throws InterruptedException {
		Thread fo = new Thread(new ConnectFO());
		fo.start();
		ConnectDN pdn2 = new ConnectDN("127.0.0.1",9000);
		Thread dn = new Thread(pdn2);
		dn.start();
		System.out.println("Should i start server?: "+pdn2.startPeer);
			Thread un = new Thread(new ConnectUN(9001));
			un.start();
		
		while(true) {
			
		}
	}
}

	
	