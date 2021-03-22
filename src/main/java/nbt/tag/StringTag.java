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
        byteArrayBuilder.append(this.value.getBytes());

        return byteArrayBuilder.getByteArray();
    }
}
