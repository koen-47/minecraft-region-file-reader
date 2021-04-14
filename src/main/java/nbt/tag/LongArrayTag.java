package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag that is designed to hold an array of longs and may have a name associated with it. It follows the specification
 * laid out by the LongArrayTag in the NBT format.
 * @author Killerkoen
 */
public class LongArrayTag extends Tag<long[]> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * An array of longs that is defined as the payload of this tag.
     */
    private long[] payload;

    /**
     * The ID of this tag.
     */
    private final byte tagID = 12;

    /**
     * Constructs an unnamed instance of an LongArrayTag object containing the array of longs from the specified
     * parameter.
     * @param payload an array of ints that defines the payload of this tag
     */
    public LongArrayTag(long[] payload) {
        this.name = "";
        this.payload = payload;
    }

    /**
     * Constructs a named instance of an LongArrayTag object containing the array of longs from the specified
     * parameter.
     * @param name the name of this tag
     * @param payload an array of ints that defines the payload of this tag
     */
    public LongArrayTag(String name, long[] payload) {
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
     * @return An array of longs
     */
    @Override
    public long[] getPayload() {
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

        for (long num : this.payload) {
            byteArrayBuilder.append(num);
        }

        return byteArrayBuilder.getByteArray();
    }

    /**
     * Compares this instance of a LongArrayTag object to that of another to see if contain equal values.
     * @param other the other instance of a LongArrayTag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof LongArrayTag)) {
            return false;
        }

        if (!this.getName().equals(other.getName())) {
            return false;
        }

        if (this.payload.length != ((LongArrayTag) other).getPayload().length) {
            return false;
        }

        for (int i = 0; i < this.payload.length; i++) {
            if (this.payload[i] != ((LongArrayTag) other).getPayload()[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of this LongArrayTag object.
     * @return A string representation of this LongArrayTag object.
     */
    public String toString() {
        return "TAG_Long_Array('" + this.name + "'): [" + this.payload.length + " longs]\n";
    }

}
