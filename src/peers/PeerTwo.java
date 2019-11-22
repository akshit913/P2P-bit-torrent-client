package peers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PeerTwo {
	final String PEER_ID="2";
	String root;
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerTwo p2 = new PeerTwo();
		String path = Paths.get("").toAbsolutePath().toString();
		String root =  path+"\\src\\peers\\Peer2File";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer2File"); 
		boolean su = directory.mkdir();
		p2.connectNeighbours(root);
	}
	
	public void connectNeighbours(String root) throws InterruptedException {
		Thread fo = new Thread(new ConnectFO(root,PEER_ID));
		fo.start();
		fo.join();
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

	
	