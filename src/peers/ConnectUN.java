package peers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectUN implements Runnable{
	private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null;
	private int port; 
	public boolean startPeer = false;
    
    public ConnectUN(int port0) {
    	this.port = port0;
    }
    
    
	public void run() {
		try
        { 
            server = new ServerSocket(port); 
            System.out.println("Upload Neighbour started at " + port); 
            System.out.println("This is Server Thread"); 
            socket = server.accept(); 
            System.out.println("Connected with Download Neighbour");
            //Thread.sleep(5000);
            startPeer = receiveACK();
            System.out.println("start peer of server: "+ startPeer);
        }
        catch(IOException i) 
        { 
            System.out.println("Connection Error"+ i); 
        } 
		
	}
	public boolean receiveACK() throws IOException {
		String line="";
		in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream()));
         try
         { 
             line = in.readUTF();
        	 System.out.println(line);
             if (line.equals("ACK")) {

            	 return true;
             }
         }
         catch(IOException i) 
         { 
             System.out.println(i); 
         } 
         return false;
     }
	
}
	
