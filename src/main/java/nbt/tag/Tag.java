package nbt.tag;

import java.util.Arrays;

abstract public class Tag<T> {
    private Tag parent;

    abstract public byte getTagID();
    abstract public String getName();
    abstract public T getPayload();
    abstract public byte[] toByteArray();

    public Tag getParent() {
        return this.parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public boolean equals(Tag other) {
        return Arrays.equals(this.toByteArray(), other.toByteArray());
    }
}
