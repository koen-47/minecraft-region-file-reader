package mca.io;

/**
 * Class that holds information about the location of a chunk.
 * @author Killerkoen
 */
public class ChunkLocation {
    /**
     * Offset in the file.
     */
    private int offset;

    /**
     * Number of sectors associated with the chunk.
     */
    private int sectorCount;

    /**
     * Constructor for the ChunkLocation class.
     * @param location - array of bytes containing the offset and sectorCount
     */
    public ChunkLocation(byte[] location) {
        this.offset = (location[2] & 0xff) | ((location[1] & 0xff) << 8) | ((location[0] & 0xff) << 16);
        this.sectorCount = location[3];
    }

    /**
     * Get the offset of an instance of the class.
     * @return the offset
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Get the sector count of an instance of the class.
     * @return the sector count
     */
    public int getSectorCount() {
        return this.sectorCount;
    }

    /**
     * Converts information about the chunk location into a string.
     * @return string with information about the chunk location
     */
    public String toString() {
        return "offset: " + this.offset + ", sector count: " + this.sectorCount;
    }
}
