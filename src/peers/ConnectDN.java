package peers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectDN implements Runnable{
	
	private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null;
    public String address;
    public int port;
    public boolean startPeer;
    
    public ConnectDN(String address0, int port0) {
    	this.address = address0;
    	this.port = port0;
    }
	public void run() {
		try
        {   
			System.out.println("This is Client Thread");
            socket = new Socket(address, port); 
            System.out.println("Connected with upload neighbour");
            startPeer = sendACK(); 
            //Thread.sleep(5000);
            System.out.println("start peer of Client: "+ startPeer); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println("Connection Error in"+ u); 
        } 
        catch(IOException i) 
        { 
            System.out.println("Connection Error"+ i); 
        } 
 		
	}
	
	boolean isConnected() {
		return socket.isConnected();
	}
	
	public boolean sendACK() throws IOException {
		 String line = "ACK"; 
		 input  = new DataInputStream(System.in);  
         out    = new DataOutputStream(socket.getOutputStream()); 
         try
         { 
             out.writeUTF(line); 
         } 
         catch(IOException i) 
         { 
             System.out.println(i); 
         }
         return true;
	}
}
