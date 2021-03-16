package mca;

import java.net.URL;

public class MCAFile {
    private URL fileName;
    private ChunkLocationTable chunkLocationTable;
    private ChunkTimestampTable chunkTimestampTable;

    public MCAFile(URL fileName, ChunkLocationTable chunkLocationTable, ChunkTimestampTable chunkTimestampTable) {
        this.fileName = fileName;
        this.chunkLocationTable = chunkLocationTable;
        this.chunkTimestampTable = chunkTimestampTable;
    }

    public ChunkLocationTable getChunkLocationTable() {
        return this.chunkLocationTable;
    }

    public ChunkTimestampTable getChunkTimestampTable() {
        return this.chunkTimestampTable;
    }

    public ChunkData getChunk(int chunkNumber) {
        ChunkLocation location = this.chunkLocationTable.getChunkLocationAtIndex(chunkNumber);
        ChunkDataReader chunkDataReader = new ChunkDataReader(this.fileName);
        return chunkDataReader.readChunkData(location);
    }

    public URL getFileName() {
        return this.fileName;
    }

}
