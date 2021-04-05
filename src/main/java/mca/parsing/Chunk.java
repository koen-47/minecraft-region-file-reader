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
    private Section[] sections;

    public Chunk(CompoundTag chunkData) {
        this.chunkData = chunkData;
        this.sections = new Section[16];
    }

    public CompoundTag toNBTTag() {
        return this.chunkData;
    }

    public Section getSectionNumber(int i) {
        return this.sections[i];
    }

}
