package mca.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RawChunkDataReader {
    private URL fileName;
    private InputStream reader;

    public RawChunkDataReader(URL fileName) {
        try {
            this.fileName = fileName;
            this.reader = new FileInputStream(this.fileName.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public RawChunkData readChunkData(ChunkLocation location) throws IOException {
        if (location.getOffset() == 0 && location.getSectorCount() == 0)
            throw new IOException("Chunk has not been loaded yet...");

        int offset = location.getOffset() * 4096;

        try {
            this.reader.skip(offset);
            byte[] chunkDataHeader = new byte[5];
            this.reader.read(chunkDataHeader);
            int chunkDataLength = (chunkDataHeader[3] & 0xff) | ((chunkDataHeader[2] & 0xff) << 8) |
                                  ((chunkDataHeader[1] & 0xff) << 16) |  ((chunkDataHeader[0] & 0xff) << 24);

            byte[] compressedChunkData = new byte[chunkDataLength * location.getSectorCount()];
            this.reader.read(compressedChunkData);
            return new RawChunkData(chunkDataHeader, compressedChunkData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
