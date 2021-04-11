package mca;

import mca.io.ChunkLocation;
import mca.io.ChunkLocationTable;
import mca.io.ChunkTimestamp;
import mca.io.ChunkTimestampTable;
import mca.parsing.Chunk;
import util.CompoundTagString;

/**
 * Class that prints or returns a string of specific parts of a .mca file.
 * @author Killerkoen
 */
public class MCAFilePrinter {
    /**
     * The .mca file to print from
     */
    private MCAFile mcaFile;

    /**
     * Constructor for the MCAFilePrinter class.
     * @param mcaFile - the .mca file to print from
     */
    public MCAFilePrinter(MCAFile mcaFile) {
        this.mcaFile = mcaFile;
    }

    /**
     * Prints the chunk location table of the assigned .mca file.
     */
    public void printChunkLocationTable() {
        ChunkLocationTable chunkLocationTable = this.mcaFile.getChunkLocationTable();
        int numberOfChunkLocations = chunkLocationTable.getNumberOfChunkLocations();

        String[][] stringTable = new String[numberOfChunkLocations][2];
        for (int i = 0; i < numberOfChunkLocations; i++) {
            ChunkLocation currentChunkLocation = chunkLocationTable.getChunkLocationAtIndex(i);
            String offset = String.valueOf(currentChunkLocation.getOffset());
            String sectorCount = String.valueOf(currentChunkLocation.getSectorCount());
            stringTable[i] = new String[] { offset, sectorCount };
        }

        System.out.format("%-15s%-15s%-15s\n", "Chunk number", "Offset", "Sector count");
        for (int i = 0; i < stringTable.length; i++) {
            String[] row = stringTable[i];
            System.out.format("%-15s%-15s%-15s\n", i, row[0], row[1]);
        }
    }

    /**
     * Prints the timestamp table of the assigned .mca file.
     */
    public void printChunkTimestampTable() {
        ChunkTimestampTable chunkTimestampTable = this.mcaFile.getChunkTimestampTable();
        int numberOfChunkTimestamps = chunkTimestampTable.getNumberOfChunkTimestamps();

        String[][] stringTable = new String[numberOfChunkTimestamps][1];
        for (int i = 0; i < numberOfChunkTimestamps; i++) {
            ChunkTimestamp currentChunkTimestamp = chunkTimestampTable.getChunkTimestampAtIndex(i);
            String timestamp = (currentChunkTimestamp.getTimestamp().getTime() != 0) ?
                                currentChunkTimestamp.getTimestamp().toString() : null;
            stringTable[i] = new String[] { timestamp };
        }

        System.out.format("%-15s%-15s\n", "Chunk number", "Last modified");
        for (int i = 0; i < stringTable.length; i++) {
            String[] row = stringTable[i];
            System.out.format("%-15s%-15s\n", i, row[0]);
        }
    }

    /**
     * Gets a string of the root compound tag of the assigned .mca file.
     * @param chunk - the chunk to convert to a string
     * @return String of the root compound tag of the assigned .mca file
     */
    public String getChunkString(Chunk chunk) {
        return new CompoundTagString(chunk.toNBTTag()).getString();
    }
}
