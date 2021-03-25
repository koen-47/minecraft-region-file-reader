package nbt.tag;

import util.ByteArrayBuilder;

public class StringTag extends Tag {
    private final byte TAG_ID = 8;

    private String name;
    private String payload;

    public StringTag(String payload) {
        this.name = "";
        this.payload = payload;
    }

    public StringTag(String name, String payload) {
        this.name = name;
        this.payload = payload;
    }

    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return this.name;
    }

    public String getPayload() {
        return this.payload;
    }

    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);

        byteArrayBuilder.append((byte) ((this.payload.length() >> 8) & 0xff));
        byteArrayBuilder.append((byte) (this.payload.length() & 0xff));
        byteArrayBuilder.append(this.payload.getBytes());

        return byteArrayBuilder.getByteArray();
    }

    public String toString() {
        return "TAG_String('" + this.name + "'): '" + this.payload + "'\n";
    }
}
