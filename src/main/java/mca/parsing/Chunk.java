package mca.parsing;

import mca.io.RawChunkData;
import nbt.tag.CompoundTag;
import nbt.tag.ListTag;
import nbt.tag.StringTag;
import util.CompoundTagString;
import util.ListTagString;

public class Chunk {
    private RawChunkData rawChunkData;
    private CompoundTag chunkData;

    public Chunk(CompoundTag chunkData) {
        this.chunkData = chunkData;
    }

    public CompoundTag toNBTTag() {
        return this.chunkData;
    }

}
