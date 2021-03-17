package nbt;

abstract public class Tag<T> {
    abstract public int getTagID();
    abstract public String getName();
    abstract public T getValue();
}
