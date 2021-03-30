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

    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof IntArrayTag))
            return false;

        if (!this.getName().equals(other.getName()))
            return false;

        if (this.payload.length != ((IntArrayTag) other).getPayload().length)
            return false;

        for (int i = 0; i < this.payload.length; i++)
            if (this.payload[i] != ((IntArrayTag) other).getPayload()[i]) return false;

        return true;
    }

    public String toString() {
        return "TAG_Int_Array('" + this.name + "'): [" + this.payload.length + " ints]\n";
    }
}
