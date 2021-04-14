package mca.io;

import java.util.Arrays;

/**
 * A {@code ChunkLocationTable} holds the references to all ChunkLocation objects.
 * @author Killerkoen
 */
public class ChunkLocationTable {
    /**
     * Array of chunk locations.
     */
    private final ChunkLocation[] locations;

    /**
     * Constructs an instance of a {@code ChunkLocationTable} object from the given array of bytes that holds the data
     * of all chunk locations.
     * @param bytes an array of bytes that are converted to chunk locations
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
     * @param i index of the target chunk location
     * @return The chunk location that was found at the given index
     */
    public ChunkLocation getChunkLocationAtIndex(int i) {
        return this.locations[i];
    }

}
