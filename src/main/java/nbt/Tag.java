package nbt;

import java.util.Arrays;

abstract public class Tag<T> {
    abstract public byte getTagID();
    abstract public String getName();
    abstract public T getValue();
    abstract public byte[] toByteArray();

    public boolean equals(Tag other) {
        return Arrays.equals(this.toByteArray(), other.toByteArray());
    }
}
