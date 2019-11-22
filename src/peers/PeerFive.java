package peers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PeerFive {
	final String PEER_ID="5";
	String root;
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerFive p5 = new PeerFive();
		String path = Paths.get("").toAbsolutePath().toString();
		String root =  path+"\\src\\peers\\Peer5File";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer5File"); 
		boolean su = directory.mkdir();
		p5.connectNeighbours(root);
	}
	
	public void connectNeighbours(String root) throws InterruptedException {
		Thread fo = new Thread(new ConnectFO(root,PEER_ID));
		fo.start();
		fo.join();
		ConnectDN pdn5 = new ConnectDN("127.0.0.1",9003);
		Thread dn = new Thread(pdn5);
		dn.start();
		System.out.println(pdn5.startPeer);
		Thread un = new Thread(new ConnectUN(9004));
		un.start();
		while(true) {
			
		}
	}
}
