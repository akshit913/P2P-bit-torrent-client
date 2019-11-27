package peers;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectUN implements Runnable{
	private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null;
	private int port; 
	public boolean startPeer = false;
	public int totalChunks;
	public String root;
	public String PEER_ID;
	ObjectOutputStream out;
	ObjectInputStream in1;
	String message;
    
    public ConnectUN(int port0,int totalChunks,String root,String PEER_ID) {
    	this.port = port0;
    	this.totalChunks = totalChunks;
    	this.root = root;
    	this.PEER_ID = PEER_ID;
    }
    
    
	public void run() {
		try
        { 
            server = new ServerSocket(port); 
            System.out.println("Upload Neighbour started at " + port); 
            System.out.println("This is Server Thread"); 
            socket = server.accept();
            System.out.println("ACP at " + port);
            out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
            in1 = new ObjectInputStream(socket.getInputStream());
            
            System.out.println("Connected with Download Neighbour");
            //Thread.sleep(5000);
            startPeer = receiveACK();
            //System.out.println("start peer of server: "+ startPeer);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            sendChunks(totalChunks);
        }
        catch(IOException i) 
        { 
            System.out.println("Connection Error"+ i); 
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	private void sendChunks(int totalChunks) throws Exception {
		System.out.println("Sending Chunks from peer");
		int chunksReceived = 0;
		Set<String> chunks = new HashSet<String>();
		while(chunksReceived != totalChunks) {
			File folder = new File(root);
			File[] listOfFiles = folder.listFiles();
			for(int i = 0;i< listOfFiles.length;i++) {
				chunks.add(listOfFiles[i].getName());
				sendMessage(listOfFiles[i].getName());
				message = (String) in1.readObject();
				if(message.equals("SEND")) {
					System.out.println("Sending :" + listOfFiles[i].getName());
					sendFile(listOfFiles[i].getName(),root);
					chunksReceived++;
				}
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
			}
			sendMessage("OK");
		}
		
	}
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("Send message: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}


	public boolean receiveACK() throws IOException, ClassNotFoundException {
		String line="";
		//in = new DataInputStream( 
                //new BufferedInputStream(socket.getInputStream()));
         try
         { 
             line = (String)in1.readObject();
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
	/*void sendFile(String filename,String root) throws Exception {
		sendMessage(filename);
		File file = new File(root + filename);
		System.out.println(file);
		byte[] fileLength = new byte[(int) file.length()];
		System.out.println("is there a scam here? :"  + fileLength.length);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		System.out.println("Reached here");
		DataInputStream dis = new DataInputStream(bis);
		System.out.println("Woah this dis is blocking");
		dis.readFully(fileLength, 0, fileLength.length);
		OutputStream os = socket.getOutputStream();
		os.flush();
		// Sending size of file.
		DataOutputStream dos = new DataOutputStream(os);
		dos.flush();
		dos.writeLong(fileLength.length);
		dos.write(fileLength, 0, fileLength.length);
		System.out.println("File sent");
		dos.flush();
		//dos.close();
		//dis.close();
	}*/
	
	public void sendFile(String fileName,String root) {
        try {
            //System.out.print("Enter the file you want to upload: ");
            //Scanner sc = new Scanner(System.in);
            //String fileName = sc.nextLine();

            File myFile = new File(root+fileName);
            byte[] mybytearray = new byte[(int) myFile.length()];
	    if(!myFile.exists()) {
		System.out.println("File does not exist..");
		return;
		}

            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //bis.read(mybytearray, 0, mybytearray.length);

            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(mybytearray, 0, mybytearray.length);
		
            OutputStream os = socket.getOutputStream();

            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);
            //dos.writeUTF(myFile.getName());
            dos.writeLong(mybytearray.length);
            dos.write(mybytearray, 0, mybytearray.length);
            dos.flush();
            System.out.println("File "+fileName+" sent to Server.");
        } catch (Exception e) {
            System.err.println("Exceptionnnn: "+e);
        }
    }	
}
	
