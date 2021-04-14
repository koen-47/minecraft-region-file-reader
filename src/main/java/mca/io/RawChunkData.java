package mca.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

/**
 * A class that contains the raw byte data of a chunk and contains methods to navigate or decompress that data in order
 * to prepare it for the conversion to an NBT tag.
 * @author Killerkoen
 */
public class RawChunkData {
    /**
     * Number of bytes in this chunk.
     */
    private final int length;

    /**
     * Compression type of this chunk (1 = GZIP, 2 = ZLIB).
     */
    private final int compressionType;

    /**
     * Array of uncompressed bytes.
     */
    private final byte[] uncompressedData;

    /**
     * Constructs an instance of a {@code RawChunkData} object from the given header and data bytes.
     * @param chunkHeaderBytes array of bytes containing the header bytes
     * @param chunkDataBytes array of bytes containing the compressed data bytes
     * @throws IOException exception for when an error occurs during IO operations
     */
    public RawChunkData(byte[] chunkHeaderBytes, byte[] chunkDataBytes) throws IOException {
        this.length = (chunkHeaderBytes[3] & 0xff) | ((chunkHeaderBytes[2] & 0xff) << 8) |
                     ((chunkHeaderBytes[1] & 0xff) << 16) |  ((chunkHeaderBytes[0] & 0xff) << 24);
        this.compressionType = chunkHeaderBytes[4];
        this.uncompressedData = this.decompress(chunkDataBytes);
    }

    /**
     * Decompress the given array of bytes using ZLIB.
     * @param compressedChunkDataBytes array of compressed chunk data bytes
     * @return Array of decompressed chunk data bytes
     * @throws IOException - exception for when an error occurs during IO operations
     */
    private byte[] decompress(byte[] compressedChunkDataBytes) throws IOException {
        ByteArrayInputStream compressedDataStream = new ByteArrayInputStream(compressedChunkDataBytes);
        InflaterInputStream decompressor = new InflaterInputStream(compressedDataStream);
        return decompressor.readAllBytes();
    }

    /**
     * Get the array of uncompressed bytes.
     * @return The array of uncompressed bytes
     */
    public byte[] getUncompressedData() {
        return this.uncompressedData;
    }
}
