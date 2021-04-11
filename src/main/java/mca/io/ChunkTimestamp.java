package mca.io;

import java.util.Date;

/**
 * Class that contains information about when a chunk was last modified.
 * @author Killerkoen
 */
public class ChunkTimestamp {
    /**
     * Date of when the chunk was last modified.
     */
    private Date timestamp;

    /**
     * Constructor for the ChunkTimestamp class.
     * @param timestampBytes - array of bytes to read from
     */
    public ChunkTimestamp(byte[] timestampBytes) {
        long secondsSinceEpoch = (timestampBytes[3] & 0xff) | ((timestampBytes[2] & 0xff) << 8) |
                                 ((timestampBytes[1] & 0xff) << 16) | ((long) (timestampBytes[0] & 0xff) << 24);
        this.timestamp = new Date(secondsSinceEpoch * 1000);
    }

    /**
     * Get the timestamp.
     * @return The timestamp
     */
    public Date getTimestamp() {
        return this.timestamp;
    }
}
