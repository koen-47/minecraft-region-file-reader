package nbt.tag;

import util.ByteArrayBuilder;

public class LongArrayTag extends Tag<long[]> {
    private String name;
    private long[] payload;

    private final byte TAG_ID = 12;

    public LongArrayTag(long[] payload) {
        this.name = "";
        this.payload = payload;
    }

    public LongArrayTag(String name, long[] payload) {
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
    public long[] getPayload() {
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload.length);

        for (long num : this.payload)
            byteArrayBuilder.append(num);

        return byteArrayBuilder.getByteArray();
    }

    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof LongArrayTag))
            return false;

        if (!this.getName().equals(other.getName()))
            return false;

        if (this.payload.length != ((LongArrayTag) other).getPayload().length)
            return false;

        for (int i = 0; i < this.payload.length; i++)
            if (this.payload[i] != ((LongArrayTag) other).getPayload()[i]) return false;

        return true;
    }

    public String toString() {
        return "TAG_Long_Array('" + this.name + "'): [" + this.payload.length + " longs]\n";
    }

}
