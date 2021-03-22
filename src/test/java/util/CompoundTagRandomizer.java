package util;

import nbt.tag.CompoundTag;
import nbt.tag.IntTag;

import java.util.Random;

public class CompoundTagRandomizer {
    private int numberOfNestedCompoundTags;
    private int numberOfTagsInCompoundTag;

    public CompoundTagRandomizer(int numberOfNestedCompoundTags, int numberOfTagsInCompoundTag) {
        this.numberOfNestedCompoundTags = numberOfNestedCompoundTags;
        this.numberOfTagsInCompoundTag = numberOfTagsInCompoundTag;
    }

    public CompoundTag generate() {
        return this.generateHelper(new CompoundTag("testCompoundTag"), 0);
    }

    private CompoundTag generateHelper(CompoundTag currentCompoundTag, int depth) {
        Random rng = new Random();
        for (int i = 0; i < numberOfTagsInCompoundTag; i++)
            currentCompoundTag.prepend(new IntTag("testIntTag", rng.nextInt(100)+1));

        CompoundTag nestedCompoundTag = new CompoundTag("testCompoundTag" + depth, currentCompoundTag);

        if (depth == numberOfNestedCompoundTags)
            return nestedCompoundTag;

        return this.generateHelper(nestedCompoundTag, depth + 1);
    }
}
