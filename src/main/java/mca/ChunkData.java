package mca;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.InflaterInputStream;

public class ChunkData {
    private int length;
    private int compressionType;
    private byte[] uncompressedData;

    public ChunkData(byte[] chunkHeaderBytes, byte[] chunkDataBytes) {
        this.length = (chunkHeaderBytes[3] & 0xff) | ((chunkHeaderBytes[2] & 0xff) << 8) |
                     ((chunkHeaderBytes[1] & 0xff) << 16) |  ((chunkHeaderBytes[0] & 0xff) << 24);
        this.compressionType = chunkHeaderBytes[4];
        this.uncompressedData = this.decompress(chunkDataBytes);
    }

    private byte[] decompress(byte[] compressedChunkDataBytes) {
        ByteArrayInputStream compressedDataStream = new ByteArrayInputStream(compressedChunkDataBytes);
        InflaterInputStream decompressor = new InflaterInputStream(compressedDataStream);

        try {
            return decompressor.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] getUncompressedData() {
        return this.uncompressedData;
    }
}
