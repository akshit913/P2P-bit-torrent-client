package peers;
import java.nio.file.Paths;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Peer1 {
	Socket requestSocket;           //socket connect to the server
	ObjectOutputStream out;         //stream write to the socket
 	ObjectInputStream in;          //stream read from the socket
	String message;                //message send to the server
	String MESSAGE;
	String path = Paths.get("").toAbsolutePath().toString();
	File directory = new File(path+"\\src\\peers\\Peer1Files");
	boolean su = directory.mkdir();                //capitalized message read from the server

	public final static int FILE_SIZE = 6022386;
	public static void main(String args[]) throws Exception
	{
		Peer1 client = new Peer1();
		client.run();
	}
	private void run() throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		requestSocket = new Socket("127.0.0.1", 8000);
		System.out.println("Connected to localhost in port 8000");
		//initialize inputStream and outputStream
		out = new ObjectOutputStream(requestSocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(requestSocket.getInputStream());
		
		sendMessage("Hello");
	}
	
	void sendMessage(String msg)
	{
		try{
			//stream write the message
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
}
