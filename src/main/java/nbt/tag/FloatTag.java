package nbt.tag;

import util.ByteArrayBuilder;

public class FloatTag extends Tag<Float> {
    private String name;
    private float payload;

    private final byte TAG_ID = 5;

    public FloatTag(float payload) {
        this.name = "";
        this.payload = payload;
    }

    public FloatTag(String name, float payload) {
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
    public Float getPayload() {
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
        if (!(other instanceof FloatTag))
            return false;

        return (this.name.equals(other.getName()) && this.payload == ((FloatTag) other).getPayload());
    }

    public String toString() {
        return "TAG_Double('" + this.name + "'): " + this.payload + "\n";
    }
}
