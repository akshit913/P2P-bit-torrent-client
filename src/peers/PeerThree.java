package peers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PeerThree {
	final String PEER_ID="3";
	String root;
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerThree p3 = new PeerThree();
		String path = Paths.get("").toAbsolutePath().toString();
		String root =  path+"\\src\\peers\\Peer3File";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer3File"); 
		boolean su = directory.mkdir();
		p3.connectNeighbours(root);
	}
	
	public void connectNeighbours(String root) throws InterruptedException {
		Thread fo = new Thread(new ConnectFO(root,PEER_ID));
		fo.start();
		fo.join();
		ConnectDN pdn3 = new ConnectDN("127.0.0.1",9001);
		Thread dn = new Thread(pdn3);
		dn.start();
		System.out.println(pdn3.startPeer);
		
		//if(pdn3.startPeer) {
			
			Thread un = new Thread(new ConnectUN(9002));
			un.start();
		//}
		while(true) {
			
		}
	}

}
