package owner;

import log.FileLogger;
import java.io.*;
import java.net.Socket;

public class FileOwner extends Thread {
	final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    String message;  
	ObjectOutputStream out;  
	ObjectInputStream in; 
	File directory;   
	FileLogger fileLog = new FileLogger();
	
    public FileOwner(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    }
    
    public void run() {
    	try {
    		out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			new PrintWriter( s.getOutputStream() );
			in = new ObjectInputStream(s.getInputStream());
			message = (String)in.readObject();
			System.out.println(message);
			//fileLog.makeLogOwner(message);
			message = (String)in.readObject();
			System.out.println(message);
			fileLog.makeLogOwner(message);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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
