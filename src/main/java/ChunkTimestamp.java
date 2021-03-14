public class ChunkTimestamp {
    private long timestamp;

    public ChunkTimestamp(byte[] timestampBytes) {
        this.timestamp = (timestampBytes[3] & 0xff) | ((timestampBytes[2] & 0xff) << 8) |
                         ((timestampBytes[1] & 0xff) << 16) | ((timestampBytes[0] & 0xff) << 24);
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
