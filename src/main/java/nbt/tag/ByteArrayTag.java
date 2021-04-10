package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag that is designed to hold an array of bytes and may have a name associated with it. It follows the specification
 * laid out by the ByteArrayTag in the NBT format.
 * @author Killerkoen
 */
public class ByteArrayTag extends Tag<byte[]> {
    /**
     * Name of the tag.
     */
    private String name;

    /**
     * An array of bytes that defines the payload of this tag.
     */
    private byte[] payload;

    /**
     * ID of this tag type.
     */
    private final byte tagID = 7;

    /**
     * Constructs an unnamed instance of a ByteArrayTag object.
     * @param payload an array of bytes that defines the payload of this tag.
     */
    public ByteArrayTag(byte[] payload) {
        this.name = "";
        this.payload = payload;
    }

    /**
     * Constructs a named instance of a ByteArrayTag object.
     * @param name the name of this tag
     * @param payload an array of bytes that defines the payload of this tag
     */
    public ByteArrayTag(String name, byte[] payload) {
        this.name = name;
        this.payload = payload;
    }

    /**
     * Get the ID of this tag.
     * @return A byte with it's value as an id that is used to identify the type of this tag.
     */
    @Override
    public byte getTagID() {
        return this.tagID;
    }

    /**
     * Get the name of this tag.
     * @return A string with this tag's name as it's value
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the payload of this tag.
     * @return An array of bytes
     */
    @Override
    public byte[] getPayload() {
        return this.payload;
    }

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload.length);

        for (byte num : this.payload) {
            byteArrayBuilder.append(num);
        }

        return byteArrayBuilder.getByteArray();
    }

    /**
     * Compares this instance of a ByteArrayTag object to that of another to see if contain equal values.
     * @param other the other instance of a ByteArrayTag object to compare to
     * @return {@code true} if this list contains the specified element; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ByteArrayTag)) {
            return false;
        }

        if (!this.getName().equals(other.getName())) {
            return false;
        }

        if (this.payload.length != ((ByteArrayTag) other).getPayload().length) {
            return false;
        }

        for (int i = 0; i < this.payload.length; i++) {
            if (this.payload[i] != ((ByteArrayTag) other).getPayload()[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of this ByteArrayTag object.
     * @return A string representation of this ByteArrayTag object.
     */
    public String toString() {
        return "TAG_Byte_Array('" + this.name + "'): [" + this.payload.length + " bytes]\n";
    }

}
