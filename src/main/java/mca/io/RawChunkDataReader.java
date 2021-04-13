package mca.io;

import java.io.*;
import java.net.URL;

/**
 * Class that reads the raw chunk data.
 * @author Killerkoen
 */
public class RawChunkDataReader {
    private File file;

    /**
     * Input stream that reads the chunk data.
     */
    private InputStream reader;

    /**
     * Constructor for the RawChunkDataReader class.
     *
     * @throws FileNotFoundException - exception for when the URL of the file cannot be found
     */
    public RawChunkDataReader(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new FileInputStream(this.file);
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
