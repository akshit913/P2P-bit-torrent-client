package peers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PeerFour {
	final String PEER_ID="4";
	String root;
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerFour p4 = new PeerFour();
		String path = Paths.get("").toAbsolutePath().toString();
		String root =  path+"\\src\\peers\\Peer4File\\";
		System.out.println("Root dir: " + root);
		File directory = new File(path+"\\src\\peers\\Peer4File\\"); 
		boolean su = directory.mkdir();
		p4.connectNeighbours(root);
	}
	
	public void connectNeighbours(String root) throws InterruptedException {
		ConnectFO fileO = new ConnectFO(root,PEER_ID);
		Thread fo = new Thread(fileO);
		fo.start();
		fo.join();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalChunks = fileO.getTotalChunks();
		ConnectDN pdn4 = new ConnectDN("127.0.0.1",9002,totalChunks,root,PEER_ID);
		Thread dn = new Thread(pdn4);
		dn.start();
		System.out.println(pdn4.startPeer);
		
		//if(pdn4.startPeer) {
			Thread un = new Thread(new ConnectUN(9003,totalChunks,root,PEER_ID));
			un.start();
		//}
		while(true) {
			
		}
	}

}
