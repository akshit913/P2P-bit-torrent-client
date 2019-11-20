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
	ObjectOutputStream out;  
	
	
	public ConnectFO(String directory) {
    	directory0 = directory;
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
	    sendMessage("1");
	    message = (String)in.readObject();
	    System.out.println("Files to be received:" + message);
	    int count = Integer.parseInt(message);
	    int i = 0;
	    while(count != i) {
	    	message = (String)in.readObject();
	    	receiveFile((String) directory0,message);
	    	i++;
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
			//System.out.println("Send message: " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
