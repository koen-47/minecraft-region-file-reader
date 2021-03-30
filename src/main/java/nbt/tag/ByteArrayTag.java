package nbt.tag;

import util.ByteArrayBuilder;

public class ByteArrayTag extends Tag<byte[]> {
    private String name;
    private byte[] payload;

    private final byte TAG_ID = 7;

    public ByteArrayTag(byte[] payload) {
        this.name = "";
        this.payload = payload;
    }

    public ByteArrayTag(String name, byte[] payload) {
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
    public byte[] getPayload() {
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload.length);

        for (byte num : this.payload)
            byteArrayBuilder.append(num);

        return byteArrayBuilder.getByteArray();
    }

    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ByteArrayTag))
            return false;

        if (!this.getName().equals(other.getName()))
            return false;

        if (this.payload.length != ((ByteArrayTag) other).getPayload().length)
            return false;

        for (int i = 0; i < this.payload.length; i++)
            if (this.payload[i] != ((ByteArrayTag) other).getPayload()[i]) return false;

        return true;
    }

    public String toString() {
        return "TAG_Byte_Array('" + this.name + "'): [" + this.payload.length + " bytes]\n";
    }

}
