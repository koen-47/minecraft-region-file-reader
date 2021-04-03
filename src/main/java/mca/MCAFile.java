package mca;

import mca.io.*;
import mca.parsing.Chunk;
import nbt.io.NBTFileInputStream;
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

    public Chunk getChunk(int chunkNumberX, int chunkNumberZ) throws IOException {
        int chunkNumber = (chunkNumberZ * 32) + chunkNumberX;
        ChunkLocation location = this.chunkLocationTable.getChunkLocationAtIndex(chunkNumber);
        RawChunkData rawChunkData = new RawChunkDataReader(this.fileName).readChunkData(location);
        return new Chunk(this.processRawChunkData(rawChunkData));
    }

    private CompoundTag processRawChunkData(RawChunkData rawChunkData) throws IOException {
        ByteArrayInputStream rawChunkBytes = new ByteArrayInputStream(rawChunkData.getUncompressedData());
        NBTFileInputStream nbtParser = new NBTFileInputStream(rawChunkBytes);
        return (CompoundTag) nbtParser.readNamedTag();
    }

    public URL getFileName() {
        return this.fileName;
    }

}
