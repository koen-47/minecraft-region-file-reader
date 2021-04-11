package mca.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class that reads the raw chunk data.
 * @author Killerkoen
 */
public class RawChunkDataReader {
    /**
     * URL of the file.
     */
    private URL fileName;

    /**
     * Input stream that reads the chunk data.
     */
    private InputStream reader;

    /**
     * Constructor for the RawChunkDataReader class.
     * @param fileName - URL of the file
     * @throws FileNotFoundException - exception for when the URL of the file cannot be found
     */
    public RawChunkDataReader(URL fileName) throws FileNotFoundException {
        this.fileName = fileName;
        this.reader = new FileInputStream(this.fileName.getFile());
    }

    /**
     * Reads the raw chunk data based on the given chunk location.
     * @param location - given chunk location
     * @return An instance of the RawChunkData class containing the data that was read
     * @throws IOException - exception for when an error occurs during IO operations
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
