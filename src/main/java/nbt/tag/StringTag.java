package nbt.tag;

import util.ByteArrayBuilder;

public class StringTag extends Tag {
    private final byte TAG_ID = 8;

    private String name;
    private String value;

    public StringTag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);

        byteArrayBuilder.append((byte) ((this.value.length() >> 8) & 0xff));
        byteArrayBuilder.append((byte) (this.value.length() & 0xff));
        byteArrayBuilder.append(this.value.getBytes());

        return byteArrayBuilder.getByteArray();
    }

    public String toString() {
        return "TAG_String('" + this.name + "'): '" + this.value + "'\n";
    }
}
