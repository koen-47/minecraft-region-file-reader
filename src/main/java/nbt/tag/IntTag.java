package nbt.tag;

import util.ByteArrayBuilder;

public class IntTag extends Tag<Integer> {
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
    public Integer getPayload() {
        return this.value;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.value);

        return byteArrayBuilder.getByteArray();
    }

    public boolean equals(IntTag other) {
        return (this.name.equals(other.getName()) && this.value == other.getPayload());
    }

    public String toString() {
        return "TAG_Int('" + this.name + "'): " + this.value + "\n";
    }
}
