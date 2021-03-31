package nbt.predicate;

import nbt.tag.CompoundTag;

@FunctionalInterface
public interface CompoundTagOperation {
    boolean abc(CompoundTag tag);
}
