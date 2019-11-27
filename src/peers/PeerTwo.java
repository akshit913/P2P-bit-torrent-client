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
		String root =  path+"\\src\\peers\\Peer2File\\";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer2File"); 
		boolean su = directory.mkdir();
		p2.connectNeighbours(root);
	}
	
	public void connectNeighbours(String root) throws InterruptedException {
		ConnectFO fileO = new ConnectFO(root,PEER_ID);
		Thread fo = new Thread(fileO);
		fo.start();
		fo.join();
		try {
			Thread.sleep(000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalChunks = fileO.getTotalChunks();
		ConnectDN pdn2 = new ConnectDN("127.0.0.1",9000,totalChunks,root,PEER_ID);
		Thread dn = new Thread(pdn2);
		dn.start();
		System.out.println("Should i start server?: "+pdn2.startPeer);
			Thread un = new Thread(new ConnectUN(9001,totalChunks,root,PEER_ID));
			un.start();
		
		while(true) {
			
		}
	}
}

	
	