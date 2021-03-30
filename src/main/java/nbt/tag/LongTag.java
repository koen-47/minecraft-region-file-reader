package nbt.tag;

import util.ByteArrayBuilder;

public class LongTag extends Tag<Long> {
    private String name;
    private long payload;

    private final byte TAG_ID =  4;

    public LongTag(long payload) {
        this.name = "";
        this.payload = payload;
    }

    public LongTag(String name, long payload) {
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
    public Long getPayload() {
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
        if (!(other instanceof LongTag))
            return false;

        return (this.name.equals(other.getName()) && this.payload == ((LongTag) other).getPayload());
    }

    public String toString() {
        return "TAG_Long('" + this.name + "'): " + this.payload + "\n";
    }

}
