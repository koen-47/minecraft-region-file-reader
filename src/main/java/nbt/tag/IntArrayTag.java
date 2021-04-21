package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag that is designed to hold an array of ints and may have a name associated with it. It follows the specification
 * laid out by the IntArrayTag in the NBT format.
 * @author Killerkoen
 */
public class IntArrayTag extends Tag {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * An array of bytes that defines the payload of this tag.
     */
    private int[] payload;

    /**
     * The ID of this tag.
     */
    private final byte tagID = 11;

    /**
     * Constructs an unnamed instance of an IntArrayTag object using a variable arguments array
     * of ints.
     * @param tags an array of ints that defines the payload of this tag
     */
    public IntArrayTag(int ...tags) {
        this.name = "";
        this.payload = tags;
    }

    /**
     * Constructs a named instance of an IntArrayTag object using a name and a variable arguments array
     * of ints.
     * @param name the name of this tag
     * @param tags an array of ints that defines the payload of this tag
     */
    public IntArrayTag(String name, int ...tags) {
        this.name = name;
        this.payload = tags;
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
     * @return An array of ints
     */
    @Override
    public int[] getPayload() {
        return this.payload;
    }

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.payload.length);

        for (int num : this.payload) {
            byteArrayBuilder.append(num);
        }

        return byteArrayBuilder.getByteArray();
    }

    /**
     * Compares this instance of a IntArrayTag object to that of another to see if contain equal values.
     * @param other the other instance of a IntArrayTag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof IntArrayTag)) {
            return false;
        }

        if (!this.getName().equals(other.getName())) {
            return false;
        }

        if (this.payload.length != ((IntArrayTag) other).getPayload().length) {
            return false;
        }

        for (int i = 0; i < this.payload.length; i++) {
            if (this.payload[i] != ((IntArrayTag) other).getPayload()[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of this IntArrayTag object.
     * @return A string representation of this IntArrayTag object.
     */
    public String toString() {
        return "TAG_Int_Array('" + this.name + "'): [" + this.payload.length + " ints]\n";
    }
}
