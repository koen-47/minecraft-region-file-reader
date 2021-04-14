package dat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@code DATReader} handles all the byte reading operations and is able to construct an instance of a
 * {@code DATFile} object from that data.
 * @author Killerkoen
 */
public class DATReader {
    /**
     * An instance of a File object that contains the .dat file
     */
    private File file;

    /**
     * Input stream of the file which is used for reading.
     */
    private InputStream reader;

    /**
     * Constructs an instance of a {@code DATReader} object that reads the given file location.
     * @param fileName location of the .dat file in the form of a string
     * @throws FileNotFoundException exception for when the URL of the file cannot be found
     */
    public DATReader(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.reader = new FileInputStream(this.file);
    }

    /**
     * Reads all bytes of the .dat file.
     * @return An instance of a DATFile class containing the compressed bytes that were read
     * @throws IOException exception for when an error occurs during IO operation
     */
    public DATFile readDATFile() throws IOException {
        byte[] datFileBytes = this.reader.readAllBytes();
        return new DATFile(this.file, datFileBytes);
    }
}
