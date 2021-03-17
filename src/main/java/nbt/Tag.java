package nbt;

abstract public class Tag<T> {
    abstract public byte getTagID();
    abstract public String getName();
    abstract public T getValue();
    abstract public byte[] toByteArray();
}
