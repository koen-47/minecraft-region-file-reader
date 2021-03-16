package mca;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ChunkDataReader {
    private URL fileName;
    private InputStream reader;

    public ChunkDataReader(URL fileName) {
        try {
            this.fileName = fileName;
            this.reader = new FileInputStream(this.fileName.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ChunkData readChunkData(ChunkLocation location) {
        int offset = location.getOffset() * 4096;

        try {
            this.reader.skip(offset);
            byte[] chunkDataHeader = new byte[5];
            this.reader.read(chunkDataHeader);
            int chunkDataLength = (chunkDataHeader[3] & 0xff) | ((chunkDataHeader[2] & 0xff) << 8) |
                                  ((chunkDataHeader[1] & 0xff) << 16) |  ((chunkDataHeader[0] & 0xff) << 24);

            byte[] compressedChunkData = new byte[chunkDataLength];
            this.reader.read(compressedChunkData);
            return new ChunkData(chunkDataHeader, compressedChunkData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
