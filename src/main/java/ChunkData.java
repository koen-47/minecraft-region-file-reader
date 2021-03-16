import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.InflaterInputStream;

public class ChunkData {
    private int length;
    private int compressionType;
    private byte[] compressedData;

    public ChunkData(byte[] chunkHeaderBytes, byte[] chunkDataBytes) {
        this.length = (chunkHeaderBytes[3] & 0xff) | ((chunkHeaderBytes[2] & 0xff) << 8) |
                     ((chunkHeaderBytes[1] & 0xff) << 16) |  ((chunkHeaderBytes[0] & 0xff) << 24);
        this.compressionType = chunkHeaderBytes[4];
        this.compressedData = chunkDataBytes;

        System.out.println(Arrays.toString(chunkHeaderBytes));
        //System.out.println(Arrays.toString(chunkDataBytes));
    }

    public void decompress() {
        ByteArrayInputStream compressedDataStream = new ByteArrayInputStream(this.compressedData);
        InflaterInputStream decompressor = new InflaterInputStream(compressedDataStream);

        try {
            byte[] buffer = decompressor.readAllBytes();
            System.out.println(Arrays.toString(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
