package mca.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * An {@code RawChunkDataReader} handles all the byte reading operations and is able to construct an instance of a
 * {@code RawChunkData} object from that data.
 * @author Killerkoen
 */
public class RawChunkDataReader {
    /**
     * An instance of a File object that contains the raw chunk data.
     */
    private File file;

    /**
     * Input stream that reads the chunk data.
     */
    private InputStream reader;

    /**
     * Constructs an instance of an {@code RawChunkDataReader} object that reads the given file.
     * @param file the file that is to be read from
     * @throws FileNotFoundException exception for when the URL of the file cannot be found
     */
    public RawChunkDataReader(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new FileInputStream(this.file);
    }

    /**
     * Reads the raw chunk data based on the given chunk location.
     * @param location given chunk location
     * @return An instance of the RawChunkData class containing the data that was read
     * @throws IOException exception for when an error occurs during IO operations
     */
    public RawChunkData readChunkData(ChunkLocation location) throws IOException {
        if (location.getOffset() == 0 && location.getSectorCount() == 0) {
            throw new IOException("Chunk has not been loaded yet...");
        }

        int offset = location.getOffset() * 4096;
        this.reader.skip(offset);
        byte[] chunkDataHeader = new byte[5];
        this.reader.read(chunkDataHeader);
        int chunkDataLength = (chunkDataHeader[3] & 0xff) | ((chunkDataHeader[2] & 0xff) << 8) |
                              ((chunkDataHeader[1] & 0xff) << 16) |  ((chunkDataHeader[0] & 0xff) << 24);

        byte[] compressedChunkData = new byte[chunkDataLength * location.getSectorCount()];
        this.reader.read(compressedChunkData);
        return new RawChunkData(chunkDataHeader, compressedChunkData);
    }
}
