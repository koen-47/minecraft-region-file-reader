package nbt.tag;

import util.ByteArrayBuilder;

public class ByteTag extends Tag<Byte> {
    private String name;
    private byte payload;

    private final byte TAG_ID = 1;

    public ByteTag(byte payload) {
        this.name = "";
        this.payload = payload;
    }

    public ByteTag(String name, byte payload) {
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
    public Byte getPayload() {
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload);
        return byteArrayBuilder.getByteArray();
    }

    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ByteTag))
            return false;

        return (this.name.equals(other.getName()) && this.payload == ((ByteTag) other).getPayload());
    }

    public String toString() {
        return "TAG_Byte('" + this.name + "'): " + this.payload + "\n";
    }

}
