package mca;

import nbt.parsing.NBTFileInputStream;
import nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    public ChunkData getRawChunk(int chunkNumber) throws IOException {
        ChunkLocation location = this.chunkLocationTable.getChunkLocationAtIndex(chunkNumber);
        ChunkDataReader chunkDataReader = new ChunkDataReader(this.fileName);
        return chunkDataReader.readChunkData(location);
    }

    public CompoundTag getChunk(int chunkNumber) throws IOException {
        ChunkData rawChunk = this.getRawChunk(chunkNumber);
        ByteArrayInputStream chunkData = new ByteArrayInputStream(rawChunk.getUncompressedData());
        NBTFileInputStream nbtParser = new NBTFileInputStream(chunkData);
        return (CompoundTag) nbtParser.readNamedTag();
    }

    public CompoundTag getChunk(int chunkNumberX, int chunkNumberZ) throws IOException {
        int chunkNumberIndex = (chunkNumberZ * 32) + chunkNumberX;
        ChunkData rawChunk = this.getRawChunk(chunkNumberIndex);
        ByteArrayInputStream chunkData = new ByteArrayInputStream(rawChunk.getUncompressedData());
        NBTFileInputStream nbtParser = new NBTFileInputStream(chunkData);
        return (CompoundTag) nbtParser.readNamedTag();
    }

    public URL getFileName() {
        return this.fileName;
    }

}
