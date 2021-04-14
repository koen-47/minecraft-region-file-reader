package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag that is designed to hold a single short and a name that may be associated with it. It follows the specification
 * laid out by the ShortTag in the NBT format.
 * @author Killerkoen
 */
public class ShortTag extends Tag<Short> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * A single short that defines the payload of this tag.
     */
    private short payload;

    /**
     * The ID of this tag.
     */
    private final byte tagID = 2;

    /**
     * Constructs an unnamed instance of a ShortTag object from the given payload.
     * @param payload a single short that defines the payload of this tag
     */
    public ShortTag(short payload) {
        this.name = "";
        this.payload = payload;
    }

    /**
     * Constructs a named instance of a ShortTag object from the given name and payload.
     * @param name the name of this tag
     * @param payload a single short that defines the payload of this tag
     */
    public ShortTag(String name, short payload) {
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
     * @return A single long
     */
    @Override
    public Short getPayload() {
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
        byteArrayBuilder.append(this.payload);
        return byteArrayBuilder.getByteArray();
    }

    /**
     * Compares this instance of a ShortTag object to that of another to see if contain equal values.
     * @param other the other instance of a ShortTag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ShortTag)) {
            return false;
        }

        return (this.name.equals(other.getName()) && this.payload == ((ShortTag) other).getPayload());
    }

    /**
     * Returns a string representation of this ShortTag object.
     * @return A string representation of this ShortTag object.
     */
    public String toString() {
        return "TAG_Short('" + this.name + "'): " + this.payload + "\n";
    }
}
