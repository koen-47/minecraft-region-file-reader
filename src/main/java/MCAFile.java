public class MCAFile {
    private ChunkLocationTable chunkLocationTable;
    private ChunkTimestampTable chunkTimestampTable;

    public MCAFile(ChunkLocationTable chunkLocationTable, ChunkTimestampTable chunkTimestampTable) {
        this.chunkLocationTable = chunkLocationTable;
        this.chunkTimestampTable = chunkTimestampTable;
    }

    public ChunkLocationTable getChunkLocationTable() {
        return this.chunkLocationTable;
    }

    public ChunkTimestampTable getChunkTimestampTable() {
        return this.chunkTimestampTable;
    }
}
