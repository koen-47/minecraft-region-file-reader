package mca;

import mca.io.ChunkLocation;
import mca.io.ChunkLocationTable;
import mca.io.ChunkTimestampTable;
import mca.io.RawChunkData;
import mca.io.RawChunkDataReader;
import mca.parsing.Chunk;
import nbt.io.NBTFileInputStream;
import nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class that contains all the methods used to navigate the data related to the .mca file.
 * @author Killerkoen
 */
public class MCAFile {
    private File file;

    /**
     * Chunk location table that holds all chunk locations in the .mca file.
     */
    private ChunkLocationTable chunkLocationTable;

    /**
     * Chunk timestamp table that holds information when chunks were last modified.
     */
    private ChunkTimestampTable chunkTimestampTable;

    public MCAFile(File file, ChunkLocationTable chunkLocationTable, ChunkTimestampTable chunkTimestampTable) {
        this.file = file;
        this.chunkLocationTable = chunkLocationTable;
        this.chunkTimestampTable = chunkTimestampTable;
    }

    /**
     * Get the chunk location table.
     * @return The current instance of the ChunkLocationTable
     */
    public ChunkLocationTable getChunkLocationTable() {
        return this.chunkLocationTable;
    }

    /**
     * Get the chunk timestamp table.
     * @return The current instance of the ChunkTimestampTable
     */
    public ChunkTimestampTable getChunkTimestampTable() {
        return this.chunkTimestampTable;
    }

    /**
     * Returns an instance of the Chunk class based on the given coordinates.
     * @param chunkNumberX - coordinate x (in chunk coordinates)
     * @param chunkNumberZ - coordinate z (in chunk coordinates)
     * @return An instance of the Chunk class
     * @throws IOException - exception for when an error occurs during IO operations
     */
    public Chunk getChunk(int chunkNumberX, int chunkNumberZ) throws IOException {
        int chunkNumber = (chunkNumberZ * 32) + chunkNumberX;
        ChunkLocation location = this.chunkLocationTable.getChunkLocationAtIndex(chunkNumber);
        RawChunkData rawChunkData = new RawChunkDataReader(this.file).readChunkData(location);
        return new Chunk(this.processRawChunkData(rawChunkData));
    }

    /**
     * Processes the raw chunk data into a readable compound tag.
     * @param rawChunkData - the chunk data that is to be read
     * @return The root compound tag of the chunk
     * @throws IOException - exception for when an error occurs during IO operations
     */
    private CompoundTag processRawChunkData(RawChunkData rawChunkData) throws IOException {
        ByteArrayInputStream rawChunkBytes = new ByteArrayInputStream(rawChunkData.getUncompressedData());
        NBTFileInputStream nbtParser = new NBTFileInputStream(rawChunkBytes);
        return (CompoundTag) nbtParser.readNamedTag();
    }

}
