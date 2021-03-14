import java.util.Arrays;

public class ChunkTimestampTable extends ChunkTable {
    private ChunkTimestamp[] chunkTimestamps;

    public ChunkTimestampTable(byte[] bytes) {
        this.chunkTimestamps = new ChunkTimestamp[NUMBER_OF_ENTRIES];
        for (int i = 0, j = 0; i < NUMBER_OF_ENTRIES; i++, j += BYTES_PER_ENTRY) {
            byte[] timestampBytes = Arrays.copyOfRange(bytes, j, j + BYTES_PER_ENTRY);
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
