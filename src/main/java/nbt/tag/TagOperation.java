package nbt.tag;

@FunctionalInterface
public interface TagOperation {
    boolean findTag(Tag tag);
}
