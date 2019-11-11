package owner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


class FileSplit {
    public static void splitFile(File f,File directory) throws IOException {
        int partCounter = 1; //For naming chunks in order

        int sizeOfFiles = 100000; // 100KB
        byte[] fileBuffer = new byte[sizeOfFiles];

        String fileName = f.getName();

        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount = 0;
            while ((bytesAmount = bis.read(fileBuffer)) > 0) {
                String filePartName = String.format("%s.%03d", fileName, partCounter++);
                File newFile = new File(directory, filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(fileBuffer, 0, bytesAmount);
                }
            }
        }
    }
    public static void mergeFiles(List<File> files, File into)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(into);
             BufferedOutputStream mergingStream = new BufferedOutputStream(fos)) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
            }
        }
    }

    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
    	 String path = Paths.get("").toAbsolutePath().toString();
         File directory = new File(path+"\\src\\owner");
         String filePath = path+"\\src\\owner\\files";
         File fileDirectory = new File(filePath);
         boolean createFolder = fileDirectory.mkdir();
    	 splitFile(new File(directory + "\\song.mp3"),fileDirectory);
    }
}
