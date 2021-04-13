package mca.parsing;

import mca.io.RawChunkData;
import nbt.tag.CompoundTag;

/**
 * Class that contains the raw and processed chunk data.
 * @author Killerkoen
 */
public class Chunk {
    /**
     * Raw chunk data.
     */
    private RawChunkData rawChunkData;

    /**
     * Root compound tag of this chunk.
     */
    private CompoundTag chunkData;

    /**
     * Constructor for the Chunk class.
     * @param chunkData - the root compound tag of this chunk
     */
    public Chunk(CompoundTag chunkData) {
        this.chunkData = chunkData;
    }

    /**
     * Get the root compound tag of this chunk.
     * @return The root compound tag of this chunk
     */
    public CompoundTag toCompoundTag() {
        return this.chunkData;
    }

}
