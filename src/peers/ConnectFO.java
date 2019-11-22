package peers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectFO extends Thread{
	Socket s= null;
	private String message;
	ObjectInputStream in;
	private String directory0;
	String PEER_ID;
	ObjectOutputStream out;  
	
	
	public ConnectFO(String directory,String PEER_ID) {
    	directory0 = directory;
    	this.PEER_ID = PEER_ID;
    }
	
	public void run() {
		try {	
		InetAddress ip = InetAddress.getByName("localhost");  
	    s = new Socket(ip, 8000); 
	    in = new ObjectInputStream(s.getInputStream());
	    out = new ObjectOutputStream(s.getOutputStream());
		out.flush();
	    DataInputStream dis = new DataInputStream(s.getInputStream()); 
	    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
	    System.out.println("Peer connect to file owner");
	    message = (String)in.readObject();
	    int totalChunks = Integer.parseInt(message);
	    System.out.println("Total chunks:" + totalChunks);
	    sendMessage(PEER_ID);
	    message = (String)in.readObject();
	    int totChunks = Integer.parseInt(message);
	    System.out.println("Files to be received:" + message);
	    int [] peerFiles = new int[totChunks];
	    for(int i = 0; i < totChunks ; i++) {
   	 		peerFiles[i%5]++;
   	 	}
	    int count = Integer.parseInt(message);
	    int i = 0;
	    synchronized(ConnectFO.class) {
	    while(count != i) {
	    	//sendMessage(expectFile());
	    	message = (String)in.readObject();             //file name to be received
	    	receiveFile((String) directory0,message);
	    	i++;
	    }
	    }
		}catch (Exception e) {
			
		}
		
		
	}
	
	public void receiveFile(String directory,String fileName) throws IOException {
		FileOutputStream output = new FileOutputStream(directory+"\\"+ fileName);
		System.out.println("Receiving "+ fileName);
		InputStream is = s.getInputStream();
		DataInputStream clientData = new DataInputStream(is);
        long size = clientData.readLong();
        long real_size= size;
        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            output.write(buffer, 0, bytesRead);
            size -= bytesRead;
        }
        System.out.println("File " + fileName +" downloaded of size " +real_size);
        output.close();
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	String expectFile(int[]peerFiles,String PEER_ID) {
		int sum = 0;
		for (int i = 0; i < Integer.parseInt(PEER_ID); i++) {
			sum += peerFiles[i];
		}
		return PEER_ID;
		
	}
}
