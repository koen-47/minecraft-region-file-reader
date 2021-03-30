package nbt.tag;

import util.ByteArrayBuilder;

public class ShortTag extends Tag<Short> {
    private String name;
    private short payload;

    private final byte TAG_ID = 2;

    public ShortTag(short payload) {
        this.name = "";
        this.payload = payload;
    }

    public ShortTag(String name, short payload) {
        this.name = name;
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
    public Short getPayload() {
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload);
        return byteArrayBuilder.getByteArray();
    }

    public String toString() {
        return "TAG_Short('" + this.name + "'): " + this.payload + "\n";
    }
}
