package owner;

import log.FileLogger;
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class FileOwner extends Thread {
	final DataInputStream dis;
	final DataOutputStream dos;
	final Socket s;
	String message;
	ObjectOutputStream out;
	ObjectInputStream in;
	File directory;
	FileLogger fileLog = new FileLogger();
	FileSplit fileSplit = new FileSplit();
	String root = "C:\\Users\\Vivek\\Desktop\\P2P-bit-torrent-client\\src\\owner\\files\\";
	final int PEER_NO = 5;
	//AtomicInteger chunkNum = new AtomicInteger(1);
	 volatile static int chunkNum = 1;
	
	public FileOwner(Socket s, DataInputStream dis, DataOutputStream dos) {
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		try {
			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		try {
//			System.out.println("Connection Established to File Owner");
			in = new ObjectInputStream(s.getInputStream());
			System.out.println("Sending chunks...");
			//chunkNum.set(chunkNum.getAndAdd(1));
			sendChunks();
			// sendFile("a1.pdf");

		} catch (Exception e) {
			// TODO Auto-generated catchblock
			e.printStackTrace();
		}

	}

	private void sendChunks() throws Exception {
		// TODO Auto-generated method stub
		String path = Paths.get("").toAbsolutePath().toString();
		File directory = new File(path + "\\src\\owner");
		String filePath = path + "\\src\\owner\\files";
		File fileDirectory = new File(filePath);
		boolean createFolder = fileDirectory.mkdir();
		fileSplit.splitFile(new File(directory + "\\song.mp3"), fileDirectory);

		File folder = new File(path + "\\src\\owner\\files");
		File[] listOfFiles = folder.listFiles();

		/*
		 * for (int i = 0; i < listOfFiles.length; i++) { if (listOfFiles[i].isFile()) {
		 * System.out.println("File " + listOfFiles[i].getName()); } else if
		 * (listOfFiles[i].isDirectory()) { System.out.println("Directory " +
		 * listOfFiles[i].getName()); } }
		 */
		System.out.println("Total files with server:" + listOfFiles.length);
		int[] peerFiles = new int[PEER_NO];
		for (int i = 0; i < listOfFiles.length; i++) {
			peerFiles[i % 5]++;
		}
		System.out.println("total peers are:" + peerFiles.length);
		for (int i = 0; i < peerFiles.length; i++) {
			System.out.println(peerFiles[i]);
		}
		sendMessage(String.valueOf(listOfFiles.length)); // sending message that there are 9 chunks total

		while (chunkNum != peerFiles.length) {

			message = (String) in.readObject();
			System.out.println("peer number:" + message); // peer send message that this is my number
			int peer_no = Integer.parseInt(message);
			sendMessage(String.valueOf(peerFiles[peer_no])); // server sends that expect these many files
			System.out.println("files to send: " + peerFiles[peer_no]);
			while (peerFiles[peer_no] > 0) {
				synchronized (FileOwner.class) {
					System.out.println("chunk num is: " + chunkNum);
					sendFile("song.mp3." + String.valueOf(chunkNum));
					chunkNum++;//.getAndAdd(1);
					peerFiles[peer_no]--;
				}
			}
		}

	}

	/*public int getChunkNum() {
		return this.chunkNum();
	}*/

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			// System.out.println("Send message: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	void sendFile(String filename) throws Exception {
		sendMessage(filename);
		String root = "C:\\Users\\Vivek\\Desktop\\P2P-bit-torrent-client\\src\\owner\\files\\";
		File file = new File(root + filename);
		System.out.println(file);
		byte[] fileLength = new byte[(int) file.length()];
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		System.out.println("Reached here");
		DataInputStream dis = new DataInputStream(bis);
		dis.readFully(fileLength, 0, fileLength.length);
		OutputStream os = s.getOutputStream();
		// Sending size of file.
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeLong(fileLength.length);
		dos.write(fileLength, 0, fileLength.length);
		System.out.println("File sent");
		dos.flush();
	}
}
