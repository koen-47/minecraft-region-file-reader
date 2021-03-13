public class MCAFile {
    private ChunkLocationTable chunkLocationTable;

    public MCAFile(ChunkLocationTable chunkLocationTable) {
        this.chunkLocationTable = chunkLocationTable;
    }

    public String toString() {
        String fileData = "";
        fileData += chunkLocationTable.toString();
        return fileData;
    }
}
