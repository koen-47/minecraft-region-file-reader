package mca.io;

import mca.MCAFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * An {@code MCAReader} handles all the byte reading operations and is able to construct an instance of a
 * {@code MCAFile} object from that data.
 * @author Killerkoen
 */
public class MCAReader {
    /**
     * An instance of a File object that contains the .mca file
     */
    private File file;

    /**
     * Input stream of the file which is used for reading.
     */
    private InputStream reader;

    /**
     * Constructs an instance of an {@code MCAReader} object that reads the given file location.
     * @param fileName location of the .mca file in the form of a string
     * @throws FileNotFoundException exception for when the URL of the file cannot be found
     */
    public MCAReader(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);
        this.reader = new FileInputStream(this.file);
    }

    /**
     * Reads all bytes of the .mca file.
     * @return -An instance of a DATFile class containing the compressed bytes that were read
     * @throws IOException exception for when an error occurs during IO operation
     */
    public MCAFile readMCAFile() throws IOException {
        byte[] locationBytes = this.readChunkLocations();
        ChunkLocationTable chunkLocationTable = new ChunkLocationTable(locationBytes);

        byte[] timestampBytes = this.readChunkTimestamps();
        ChunkTimestampTable chunkTimestampTable = new ChunkTimestampTable(timestampBytes);

        return new MCAFile(this.file, chunkLocationTable, chunkTimestampTable);
    }

    /**
     * Reads the raw data of all chunk locations.
     * @return Array of bytes containing the raw data of all chunk locations
     * @throws IOException exception for when an error occurs during IO operation
     */
    private byte[] readChunkLocations() throws IOException {
        byte[] chunkLocations = new byte[4096];
        this.reader.read(chunkLocations);
        return chunkLocations;
    }

    /**
     * Reads the raw data of all chunk timestamps.
     * @return Array of bytes containing the raw data of all chunk timestamps
     * @throws IOException exception for when an error occurs during IO operation
     */
    private byte[] readChunkTimestamps() throws IOException {
        byte[] chunkTimestamps = new byte[4096];
        this.reader.read(chunkTimestamps);
        return chunkTimestamps;
    }
}
