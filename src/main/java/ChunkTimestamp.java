import java.util.Date;

public class ChunkTimestamp {
    private Date timestamp;

    public ChunkTimestamp(byte[] timestampBytes) {
        long secondsSinceEpoch = (timestampBytes[3] & 0xff) | ((timestampBytes[2] & 0xff) << 8) |
                                 ((timestampBytes[1] & 0xff) << 16) | ((long) (timestampBytes[0] & 0xff) << 24);
        this.timestamp = new Date(secondsSinceEpoch * 1000);
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}
