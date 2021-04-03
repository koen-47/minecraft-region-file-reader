package mca.io;

import java.util.Arrays;

public class ChunkLocationTable {
    private ChunkLocation[] locations;

    private final int NUMBER_OF_LOCATIONS = 1024;
    private final int BYTES_PER_LOCATION = 4;

    public ChunkLocationTable(byte[] bytes) {
        this.locations = new ChunkLocation[NUMBER_OF_LOCATIONS];
        for (int i = 0, j = 0; i < NUMBER_OF_LOCATIONS; i++, j += BYTES_PER_LOCATION) {
            byte[] locationBytes = Arrays.copyOfRange(bytes, j, j + BYTES_PER_LOCATION);
            this.locations[i] = new ChunkLocation(locationBytes);
        }
    }

    public int getNumberOfChunkLocations() {
        return this.locations.length;
    }

    public ChunkLocation getChunkLocationAtIndex(int i) {
        return this.locations[i];
    }

}
