package mca.io;

import mca.MCAFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class that reads .mca files.
 * @author Killerkoen
 */
public class MCAReader {
    /**
     * URL of the .dat file.
     */
    private URL fileName;

    /**
     * Input stream of the file which is used for reading.
     */
    private InputStream reader;

    /**
     * Constructor for the MCAReader class.
     * @param fileName - URL of the .mca file
     * @throws FileNotFoundException - exception for when the URL of the file cannot be found
     */
    public MCAReader(String fileName) throws FileNotFoundException {
        this.fileName = this.getClass().getResource(fileName);
        this.reader = new FileInputStream(this.fileName.getFile());
    }

    /**
     * Reads all bytes of the .mca file.
     * @return - an instance of a DATFile class containing the compressed bytes that were read
     * @throws IOException - exception for when an error occurs during IO operation
     */
    public MCAFile readMCAFile() throws IOException {
        byte[] locationBytes = this.readChunkLocations();
        ChunkLocationTable chunkLocationTable = new ChunkLocationTable(locationBytes);

        byte[] timestampBytes = this.readChunkTimestamps();
        ChunkTimestampTable chunkTimestampTable = new ChunkTimestampTable(timestampBytes);

        return new MCAFile(this.fileName, chunkLocationTable, chunkTimestampTable);
    }

    /**
     * Reads the raw data of all chunk locations.
     * @return Array of bytes containing the raw data of all chunk locations
     * @throws IOException - exception for when an error occurs during IO operation
     */
    private byte[] readChunkLocations() throws IOException {
        byte[] chunkLocations = new byte[4096];
        this.reader.read(chunkLocations);
        return chunkLocations;
    }

    /**
     * Reads the raw data of all chunk timestamps.
     * @return Array of bytes containing the raw data of all chunk timestamps
     * @throws IOException - exception for when an error occurs during IO operation
     */
    private byte[] readChunkTimestamps() throws IOException {
        byte[] chunkTimestamps = new byte[4096];
        this.reader.read(chunkTimestamps);
        return chunkTimestamps;
    }
}
