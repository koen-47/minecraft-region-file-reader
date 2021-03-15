import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

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

    public void getChunkAtCoordinates(int chunkNumber) {
        ChunkLocation location = this.chunkLocationTable.getChunkLocationAtIndex(chunkNumber);
        ChunkDataReader chunkDataReader = new ChunkDataReader(this.fileName);
        ChunkData compressedChunkData = chunkDataReader.readCompressedChunkData(location);
    }

}
