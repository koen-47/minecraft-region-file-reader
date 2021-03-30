package nbt.tag;

import util.ByteArrayBuilder;

import java.util.ArrayList;

public class IntArrayTag extends Tag {
    private String name;
    private int[] payload;

    private final byte TAG_ID = 11;

    public IntArrayTag(int ...tags) {
        this.payload = tags;
    }

    public IntArrayTag(String name, int ...tags) {
        this.name = name;
        this.payload = tags;
    }

    @Override
    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return this.name;
    }

    public int[] getPayload() {
        return this.payload;
    }

    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload.length);

        for (int num : this.payload)
            byteArrayBuilder.append(num);

        return byteArrayBuilder.getByteArray();
    }

    public String toString() {
        return "TAG_Int_Array('" + this.name + "'): [" + this.payload.length + " ints]\n";
    }
}
