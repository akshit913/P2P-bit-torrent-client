package peers;

import java.io.IOException;

public class PeerFive {

	public static void main(String[] args) throws IOException, InterruptedException {
		PeerFive p5 = new PeerFive();
		p5.connectNeighbours();
	}
	
	public void connectNeighbours() throws InterruptedException {
		Thread fo = new Thread(new ConnectFO());
		fo.start();
		ConnectDN pdn5 = new ConnectDN("127.0.0.1",9003);
		Thread dn = new Thread(pdn5);
		dn.start();
		System.out.println(pdn5.startPeer);
		
		//if(pdn5.startPeer) {
			Thread un = new Thread(new ConnectUN(9004));
			un.start();
		//}
		while(true) {
			
		}
	}
}
