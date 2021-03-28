package nbt.tag;

import util.ByteArrayBuilder;

public class DoubleTag extends Tag<Double> {
    private String name;
    private double payload;

    private final byte TAG_ID = 6;

    public DoubleTag(double payload) {
        this.name = "";
        this.payload = payload;
    }

    public DoubleTag(String name, double payload) {
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
    public Double getPayload() {
        return this.payload;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload);
        return byteArrayBuilder.getByteArray();
    }
}
