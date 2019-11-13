package peers;
import java.nio.file.Paths;
import log.FileLogger;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;

import com.sun.istack.internal.logging.Logger;

public class Peer1 {
	Socket requestSocket;          
	ObjectOutputStream out;        
 	ObjectInputStream in;          
	String message;                
	String MESSAGE;
	String path = Paths.get("").toAbsolutePath().toString();
	File directory = new File(path+"\\src\\peers\\Peer1Files");
	boolean su = directory.mkdir();                

	public final static int FILE_SIZE = 6022386;
	public final static String PEER_ID = "Peer 1";
	
	public static void main(String args[]) throws Exception
	{
		Peer1 client = new Peer1();
		client.run();
	}
	
	private void run() throws UnknownHostException, IOException, ClassNotFoundException {
		FileLogger fileLog = new FileLogger();
		requestSocket = new Socket("127.0.0.1", 8000);
		fileLog.makeLogPeer(PEER_ID,"Peer One connected to File owner on port 8000");
		out = new ObjectOutputStream(requestSocket.getOutputStream()); 	
		out.flush();
		in = new ObjectInputStream(requestSocket.getInputStream());
		String message= "Hello";
		sendMessage(message);
		fileLog.makeLogPeer(PEER_ID,message);
		message = "How are you?.";
		sendMessage(message);
		fileLog.makeLogPeer(PEER_ID,message);
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
	
}
