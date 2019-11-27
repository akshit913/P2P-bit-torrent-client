package peers;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class ConnectDN implements Runnable{
	
	private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null;
    public String address;
    public int port;
    public boolean startPeer;
    public int totalChunks;
	public String root;
	public String PEER_ID;
	ObjectOutputStream out1;
	ObjectInputStream in;
	String message;
    
    public ConnectDN(String address0, int port0,int totalChunks,String root,String PEER_ID) {
    	this.address = address0;
    	this.port = port0;
    	this.totalChunks = totalChunks;
    	this.root = root;
    	this.PEER_ID = PEER_ID;
    }
	public void run() {
		try
        {   
			System.out.println("This is Client Thread");
            socket = new Socket(address, port); 
            System.out.println("Connected with upload neighbour");
            out1 = new ObjectOutputStream(socket.getOutputStream());
			out1.flush();
            in = new ObjectInputStream(socket.getInputStream());
            
            startPeer = sendACK(); 
            //Thread.sleep(5000);
            System.out.println("start peer of Client: "+ startPeer); 
            System.out.println("##################################");
            getChunks(totalChunks);
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println("Connection Error in"+ u); 
        } 
        catch(IOException i) 
        { 
            System.out.println("Connection Error"+ i); 
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 		
	}
	
	private void getChunks(int totalChunks) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Receeiving chunks from peer");
		int chunksReceived = 0;
		Set<String> chunks = new HashSet<String>();
		System.out.println("root is:" + root);
		while(chunksReceived != totalChunks) {
			File folder = new File(root);
			File[] listOfFiles = folder.listFiles();
			for(int i = 0;i< listOfFiles.length;i++) {
				chunks.add(listOfFiles[i].getName());
				//System.out.println(listOfFiles[i].getName());
			}
			while(true) {
				message = (String) in.readObject();
				System.out.println("Do you want:" + message);
				if(message.equals("OK")) break;
				if(!chunks.contains(message)) {
					sendMessage("SEND");
					receiveFile(root,message);
					System.out.println("received: " + message);
					chunks.add(message);
					chunksReceived++;
				}
				if(chunks.contains(message)) {
				sendMessage("NOT INTERESTED");
				}
			}
		
		}
		
	}
	boolean isConnected() {
		return socket.isConnected();
	}
	
	public boolean sendACK() throws IOException {
		 String line = "ACK"; 
		 //input  = new DataInputStream(System.in);  
         //out    = new DataOutputStream(socket.getOutputStream()); 
         try
         { 
             out1.writeObject(line); 
         } 
         catch(IOException i) 
         { 
             System.out.println(i); 
         }
         return true;
	}
	
	void sendMessage(String msg) {
		try {
			out1.writeObject(msg);
			out1.flush();
			// System.out.println("Send message: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	/*public void receiveFile(String directory,String fileName) throws IOException {
		FileOutputStream output = new FileOutputStream(directory+"\\"+ fileName);
		//output.flush();
		System.out.println("Receiving "+ fileName);
		InputStream is = socket.getInputStream();
		DataInputStream clientData = new DataInputStream(is);
		System.out.println("After this shitty inputstreams");
		//System.out.println("scam " + size);
        long size = clientData.readLong();
        System.out.println("Bro real scam is here:" + size);
        long real_size= size;
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            output.write(buffer, 0, bytesRead);
            size -= bytesRead;
            //System.out.println(size+fileName);
        }
        size = 0;
        output.flush();
        System.out.println("File " + fileName +" downloaded of size " +real_size);
        output.close();
	}*/
	
	public void receiveFile(String root,String fileName) {
        try {
            int bytesRead;
            InputStream in = socket.getInputStream();

            DataInputStream clientData = new DataInputStream(in);

            //fileName = clientData.readUTF();
            OutputStream output = new FileOutputStream(root+fileName);
            long size = clientData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.flush();
            output.close();
            //in.close();

            System.out.println("File "+fileName+" received from Server.");
        } catch (IOException ex) {
		System.out.println("Exception: "+ex);
         }
    
}
}
