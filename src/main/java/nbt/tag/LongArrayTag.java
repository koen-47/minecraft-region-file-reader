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

    public String toString() {
        return "TAG_Long_Array('" + this.name + "'): [" + this.payload.length + " longs]\n";
    }

}
