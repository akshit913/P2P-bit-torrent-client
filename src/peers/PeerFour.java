package peers;

import java.io.IOException;

public class PeerFour {
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerFour p4 = new PeerFour();
		p4.connectNeighbours();
	}
	
	public void connectNeighbours() throws InterruptedException {
		Thread fo = new Thread(new ConnectFO());
		fo.start();
		ConnectDN pdn4 = new ConnectDN("127.0.0.1",9002);
		Thread dn = new Thread(pdn4);
		dn.start();
		System.out.println(pdn4.startPeer);
		
		//if(pdn4.startPeer) {
			Thread un = new Thread(new ConnectUN(9003));
			un.start();
		//}
		while(true) {
			
		}
	}

}
