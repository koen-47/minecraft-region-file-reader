package nbt;

import util.ByteArrayBuilder;

import java.util.Arrays;

public class IntTag extends Tag {
    private final byte TAG_ID = 3;

    private String name;
    private int value;

    public IntTag(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public IntTag(int value) {
        this.name = "";
        this.value = value;
    }

    @Override
    public byte getTagID() {
        return this.TAG_ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.append(this.TAG_ID);
        byteArrayBuilder.append((byte) ((this.name.length() >> 8) & 0xff));
        byteArrayBuilder.append((byte) (this.name.length() & 0xff));
        byteArrayBuilder.append(this.name.getBytes());
        byteArrayBuilder.append(this.value);

        return byteArrayBuilder.getByteArray();
    }

    public boolean equals(IntTag other) {
        return (this.name.equals(other.getName()) && this.value == other.getValue());
    }

    public String toString() {
        return "TAG_Int('" + this.name + "'): " + this.value + "\n";
    }
}
