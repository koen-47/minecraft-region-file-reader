package mca;

public class ChunkLocation {
    private int offset;
    private int sectorCount;

    public ChunkLocation(byte[] location) {
        this.offset = (location[2] & 0xff) | ((location[1] & 0xff) << 8) | ((location[0] & 0xff) << 16);
        this.sectorCount = location[3];
    }

    public int getOffset() {
        return this.offset;
    }

    public int getSectorCount() {
        return this.sectorCount;
    }

    public String toString() {
        return "offset: " + this.offset + ", sector count: " + this.sectorCount;
    }
}
