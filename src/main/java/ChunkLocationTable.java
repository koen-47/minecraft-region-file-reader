import java.util.Arrays;

public class ChunkLocationTable extends ChunkTable {
    private ChunkLocation[] locations;

    public ChunkLocationTable(byte[] bytes) {
        this.locations = new ChunkLocation[NUMBER_OF_ENTRIES];
        for (int i = 0, j = 0; i < NUMBER_OF_ENTRIES; i++, j += BYTES_PER_ENTRY) {
            byte[] locationBytes = Arrays.copyOfRange(bytes, j, j + BYTES_PER_ENTRY);
            this.locations[i] = new ChunkLocation(locationBytes);
        }
    }

    public int getNumberOfChunkLocations() {
        return this.locations.length;
    }

    public ChunkLocation getChunkLocationAtIndex(int i) {
        return this.locations[i];
    }

    public String toString() {
        String ret = "";
        for (int i = 1; i <= NUMBER_OF_ENTRIES; i++)
            ret += "Chunk location " + i + ") " + this.locations[i-1].toString() + " \n";

        return ret;
    }
}
