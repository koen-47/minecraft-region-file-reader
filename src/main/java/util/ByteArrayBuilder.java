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
        for (int i = 0; i < byteArray.length; i++)
            byteArray[i] = (byte) ((other & 0xff) >> (24 - (i*8)));

        this.append(byteArray);
    }

    public void append(double other) {
        long temp = Double.doubleToLongBits(other);
        byte[] byteArray = new byte[8];
        for (int i = 0; i < byteArray.length; i++)
            byteArray[i] = (byte)((temp >> ((7 - i) * 8)) & 0xff);

        this.append(byteArray);
    }

    public void append(String other) {
        this.append(other.length());
        this.append(other.getBytes());
    }

    public void appendTagHeader(Tag other) {
        this.append(other.getTagID());
        this.append((byte) ((other.getName().length() >> 8) & 0xff));
        this.append((byte) (other.getName().length() & 0xff));
        this.append(other.getName().getBytes());
    }

    public void appendTagPayload(Tag other) {
        switch (other.getTagID()) {
            case 3:
                this.append((int) other.getPayload());
                break;

            case 8:
                this.append((String) other.getPayload());
                break;
        }
    }

    public byte[] getByteArray() {
        return this.array;
    }

    public String toString() {
        return Arrays.toString(this.array);
    }

}
