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
        ListTag sectionsListTag = (ListTag) chunkData.find(ListTag.class, tag -> tag.getName().equals("Sections"));
        for (int i = 0; i < this.sections.length; i++) {
            //System.out.println(new CompoundTagString((CompoundTag) sectionsListTag.getPayload()[i]).getString());
            sections[i] = new Section((CompoundTag) sectionsListTag.getPayload()[i]);
        }
    }

    public CompoundTag toNBTTag() {
        return this.chunkData;
    }

    public Section getSectionNumber(int i) {
        return this.sections[i];
    }

}
