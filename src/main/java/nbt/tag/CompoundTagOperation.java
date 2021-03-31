package nbt.tag;

@FunctionalInterface
public interface CompoundTagOperation {
    boolean findTag(Tag tag);
}
