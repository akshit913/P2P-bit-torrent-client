package peers;

import java.io.IOException;

public class PeerThree {
	public static void main(String[] args) throws IOException, InterruptedException {
		PeerThree p3 = new PeerThree();
		p3.connectNeighbours();
	}
	
	public void connectNeighbours() throws InterruptedException {
		Thread fo = new Thread(new ConnectFO());
		fo.start();
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
