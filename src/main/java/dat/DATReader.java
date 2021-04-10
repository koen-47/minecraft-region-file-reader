package dat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class that handles reading .dat files.
 * @author Killerkoen
 */
public class DATReader {
    /**
     * URL of the .dat file.
     */
    private URL fileName;

    /**
     * Input stream of the file which is used for reading.
     */
    private InputStream reader;

    /**
     * Constructor for the DATReader class.
     * @param fileName - URL of the .dat file
     * @throws FileNotFoundException - exception for when the URL of the file cannot be found
     */
    public DATReader(String fileName) throws FileNotFoundException {
        this.fileName = this.getClass().getResource(fileName);
        this.reader = new FileInputStream(this.fileName.getFile());
    }

    /**
     * Reads all bytes of the .dat file.
     * @return - an instance of a DATFile class containing the compressed bytes that were read
     * @throws IOException - exception for when an error occurs during IO operation
     */
    public DATFile readDATFile() throws IOException {
        byte[] datFileBytes = this.reader.readAllBytes();
        return new DATFile(this.fileName, datFileBytes);
    }
}
