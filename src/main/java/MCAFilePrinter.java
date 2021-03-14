public class MCAFilePrinter {
    private MCAFile mcaFile;

    public MCAFilePrinter(MCAFile mcaFile) {
        this.mcaFile = mcaFile;
    }

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
            System.out.format("%-15s%-15s%-15s\n", i+1, row[0], row[1]);
        }
    }

    public void printChunkTimestampTable() {
        ChunkTimestampTable chunkTimestampTable = this.mcaFile.getChunkTimestampTable();
        int numberOfChunkTimestamps = chunkTimestampTable.getNumberOfChunkTimestamps();

        String[][] stringTable = new String[numberOfChunkTimestamps][1];
        for (int i = 0; i < numberOfChunkTimestamps; i++) {
            ChunkTimestamp currentChunkTimestamp = chunkTimestampTable.getChunkTimestampAtIndex(i);
            String timestamp = String.valueOf(currentChunkTimestamp.getTimestamp());
            stringTable[i] = new String[] { timestamp };
        }

        System.out.format("%-15s%-15s\n", "Chunk number", "Last modified");
        for (int i = 0; i < stringTable.length; i++) {
            String[] row = stringTable[i];
            System.out.format("%-15s%-15s\n", i+1, row[0]);
        }
    }
}
