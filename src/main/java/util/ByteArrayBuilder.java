package util;

import nbt.tag.Tag;

import java.util.Arrays;

/**
 * A utility class that is designed to build an array of bytes. It is capable of converting a tag back to it's original
 * byte sequence and appending it to a large array. The conversion follows the basic specification laid out by the NBT
 * format.
 * @author Killerkoen
 */
public class ByteArrayBuilder {
    /**
     * The array of bytes.
     */
    private byte[] array;

    /**
     * Constructs an instance of a ByteArrayBuilder object with an empty array of bytes.
     */
    public ByteArrayBuilder() {
        this.array = new byte[0];
    }

    /**
     * Constructs an instance of a ByteArrayBuilder object starting with the specified array of bytes.
     * @param array the array of bytes to start from
     */
    public ByteArrayBuilder(final byte[] array) {
        this.array = array;
    }

    /**
     * Appends the specified array of bytes to this array of bytes.
     * @param other the array of bytes to append
     */
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

    /**
     * Appends a single byte to this array of bytes.
     * @param other the byte to append
     */
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

    /**
     * Appends a single short to this array of bytes.
     * @param other the short to append
     */
    public void append(short other) {
        byte[] byteArray = new byte[2];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((other & 0xff) >> (8 - (i * 8)));
        }

        this.append(byteArray);
    }

    /**
     * Appends a single int to this array of bytes.
     * @param other the int to append
     */
    public void append(int other) {
        byte[] byteArray = new byte[4];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((other & 0xff) >> (24 - (i * 8)));
        }

        this.append(byteArray);
    }

    /**
     * Appends a single long to this array of bytes.
     * @param other the long to append
     */
    public void append(long other) {
        byte[] byteArray = new byte[8];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((other & 0xff) >> (56 - (i * 8)));
        }

        this.append(byteArray);
    }

    /**
     * Appends a single float to this array of bytes.
     * @param other the float to append
     */
    public void append(float other) {
        long temp = Float.floatToIntBits(other);
        byte[] byteArray = new byte[4];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((temp >> ((3 - i) * 8)) & 0xff);
        }

        this.append(byteArray);
    }

    /**
     * Appends a single double to this array of bytes.
     * @param other the double to append
     */
    public void append(double other) {
        long temp = Double.doubleToLongBits(other);
        byte[] byteArray = new byte[8];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) ((temp >> ((7 - i) * 8)) & 0xff);
        }

        this.append(byteArray);
    }

    /**
     * Appends a string to this array of bytes.
     * @param other the string to append
     */
    public void append(String other) {
        this.append(other.length());
        this.append(other.getBytes());
    }

    /**
     * Appends a tag header to this array of bytes. The tag header is the ordered sequence of it's ID and then name.
     * @param other the tag header to append
     */
    public void appendTagHeader(Tag other) {
        this.append(other.getTagID());
        this.append((byte) ((other.getName().length() >> 8) & 0xff));
        this.append((byte) (other.getName().length() & 0xff));
        this.append(other.getName().getBytes());
    }

    /**
     * Appends a tag payload to this array of bytes.
     * @param other the tag to append it's payload
     */
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

    /**
     * Get the complete array of bytes.
     * @return The array of bytes
     */
    public byte[] getByteArray() {
        return this.array;
    }

    /**
     * Returns a string representation of the complete array of bytes.
     * @return a string representation of the complete array of bytes
     */
    public String toString() {
        return Arrays.toString(this.array);
    }

}
