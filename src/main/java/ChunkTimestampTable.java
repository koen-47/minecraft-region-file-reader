import java.util.Arrays;

public class ChunkTimestampTable {
    private ChunkTimestamp[] chunkTimestamps;

    private final int NUMBER_OF_TIMESTAMPS = 1024;
    private final int BYTES_PER_TIMESTAMP = 4;

    public ChunkTimestampTable(byte[] bytes) {
        this.chunkTimestamps = new ChunkTimestamp[NUMBER_OF_TIMESTAMPS];
        for (int i = 0, j = 0; i < NUMBER_OF_TIMESTAMPS; i++, j += BYTES_PER_TIMESTAMP) {
            byte[] timestampBytes = Arrays.copyOfRange(bytes, j, j + BYTES_PER_TIMESTAMP);
            this.chunkTimestamps[i] = new ChunkTimestamp(timestampBytes);
        }
    }

    public int getNumberOfChunkTimestamps() {
        return this.chunkTimestamps.length;
    }

    public ChunkTimestamp getChunkTimestampAtIndex(int i) {
        return this.chunkTimestamps[i];
    }
}
