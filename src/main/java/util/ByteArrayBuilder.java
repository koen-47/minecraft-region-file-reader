package util;

import nbt.tag.Tag;

import java.util.Arrays;

public class ByteArrayBuilder {
    private byte[] array;

    public ByteArrayBuilder() {
        this.array = new byte[0];
    }

    public ByteArrayBuilder(byte[] array) {
        this.array = array;
    }

    public void append(byte other) {
        byte[] appendedArray = new byte[this.array.length + 1];
        int i = 0;

        for (byte currentByte : this.array) {
            appendedArray[i] = currentByte;
            i += 1;
        }

        appendedArray[this.array.length] = other;
        this.array = appendedArray;
    }

    public void append(byte[] other) {
        byte[] appendedArray = new byte[this.array.length + other.length];
        int i = 0;

        for (byte currentByte : this.array) {
            appendedArray[i] = currentByte;
            i += 1;
        }

        for (byte currentByte : other) {
            appendedArray[i] = currentByte;
            i += 1;
        }

        this.array = appendedArray;
    }

    public void append(int other) {
        byte[] byteArray = new byte[4];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((other & 0xff) >> (24 - (i*8)));
        }

        this.append(byteArray);
    }

    public void appendTagHeader(Tag other) {
        this.append(other.getTagID());
        this.append((byte) ((other.getName().length() >> 8) & 0xff));
        this.append((byte) (other.getName().length() & 0xff));
        this.append(other.getName().getBytes());
    }

    public byte[] getByteArray() {
        return this.array;
    }

    public String toString() {
        return Arrays.toString(this.array);
    }

}
