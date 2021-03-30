package nbt.tag;

import util.ByteArrayBuilder;

public class IntTag extends Tag<Integer> {
    private final byte TAG_ID = 3;

    private String name;
    private int payload;

    public IntTag(String name, int payload) {
        this.name = name;
        this.payload = payload;
    }

    public IntTag(int payload) {
        this.name = "";
        this.payload = payload;
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
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload);

        return byteArrayBuilder.getByteArray();
    }

    public boolean equals(IntTag other) {
        return (this.name.equals(other.getName()) && this.payload == other.getPayload());
    }

    public String toString() {
        return "TAG_Int('" + this.name + "'): " + this.payload + "\n";
    }
}
