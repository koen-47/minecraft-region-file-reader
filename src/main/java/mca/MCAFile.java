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

/**
 * An {@code MCAFile} contains all the methods that can be used to navigate all the data related to a .mca file.
 * @author Killerkoen
 */
public class MCAFile {
    /**
     * An instance of a File object that contains the .mca file
     */
    private File file;

    /**
     * Chunk location table that holds all chunk locations in the .mca file.
     */
    private ChunkLocationTable chunkLocationTable;

    /**
     * Chunk timestamp table that holds information when chunks were last modified.
     */
    private ChunkTimestampTable chunkTimestampTable;

    /**
     * Constructs an instance of an MCAFile object with the specified File object, chunk location table and
     * chunk timestamp table.
     * @param file the instance of the File object that contains the .mca file
     * @param chunkLocationTable the chunk location table that holds the references to all chunk locations
     * @param chunkTimestampTable the chunk timestamp table that holds the references to all chunk timestamps
     */
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
     * @param chunkNumberX coordinate x (in chunk coordinates)
     * @param chunkNumberZ coordinate z (in chunk coordinates)
     * @return An instance of the Chunk class
     * @throws IOException exception for when an error occurs during IO operations
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
