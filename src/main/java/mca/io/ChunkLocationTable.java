package mca.io;

import java.util.Arrays;

/**
 * Class that holds all the instances of the ChunkLocation class.
 * @author Killerkoen
 */
public class ChunkLocationTable {
    /**
     * Array of chunk locations.
     */
    private final ChunkLocation[] locations;

    /**
     * Constructor of the ChunkLocationTable class.
     * @param bytes - array of bytes that are converted to chunk locations
     */
    public ChunkLocationTable(byte[] bytes) {
        final int numberOfLocations = 1024;
        final int bytesPerLocation = 4;
        this.locations = new ChunkLocation[numberOfLocations];

        for (int i = 0, j = 0; i < numberOfLocations; i++, j += bytesPerLocation) {
            byte[] locationBytes = Arrays.copyOfRange(bytes, j, j + bytesPerLocation);
            this.locations[i] = new ChunkLocation(locationBytes);
        }
    }

    /**
     * Get the number of chunk locations in this table.
     * @return The number of chunk locations in this table
     */
    public int getNumberOfChunkLocations() {
        return this.locations.length;
    }

    /**
     * Get a chunk location at the given index.
     * @param i - index of the target chunk location
     * @return The chunk location that was found at the given index
     */
    public ChunkLocation getChunkLocationAtIndex(int i) {
        return this.locations[i];
    }

}
