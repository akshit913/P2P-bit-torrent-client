package log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.*; 
  
public class FileLogger { 
    private final static Logger LOGGER =  
                Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);  
   
    public void makeLogPeer(String PEER_ID,String message) throws SecurityException, IOException 
    {  
    	FileHandler fh;
        String path = Paths.get("").toAbsolutePath().toString();
        File directory = new File(path+"\\src\\log\\logs");
    	boolean su = directory.mkdir(); 
        LogManager lgmngr = LogManager.getLogManager();
		Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
		fh = new FileHandler(path+ "\\src\\log\\logs\\PeerLog.log");
		log.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter); 
		log.log(Level.INFO, PEER_ID + ": " + message);
		Logger globalLogger = Logger.getLogger("global");
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
		    globalLogger.removeHandler(handler);
		}
    }
    
    @SuppressWarnings("unused")
	public void makeLogOwner(String message) throws SecurityException, IOException 
    {  
        //LOGGER.log(Level.INFO, "LOG START");
        FileHandler fh;
        String path = Paths.get("").toAbsolutePath().toString();
        File directory = new File(path+"\\src\\log\\logs");
    	boolean su = directory.mkdir(); 
        LogManager lgmngr = LogManager.getLogManager();
		Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
		fh = new FileHandler(path+ "\\src\\log\\logs\\OwnerLog.log");
		log.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter); 
		log.log(Level.INFO, "Owner: "+ message);
		Logger globalLogger = Logger.getLogger("global");
		Handler[] handlers = globalLogger.getHandlers();
		for(Handler handler : handlers) {
		    globalLogger.removeHandler(handler);
		}
    }
    
}
