package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag that is designed to hold a single byte and a name that may be associated with it. It follows the specification
 * laid out by the ByteArrayTag in the NBT format.
 * @author Killerkoen
 */
public class ByteTag extends Tag<Byte> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * A single byte that defines the payload of this tag.
     */
    private byte payload;

    /**
     * The ID of this tag type.
     */
    private final byte tagID = 1;

    /**
     * Constructs an unnamed instance of a ByteTag object.
     * @param payload a single byte that defines the payload of this tag
     */
    public ByteTag(byte payload) {
        this.name = "";
        this.payload = payload;
    }

    /**
     * Constructs a named instance of a ByteTag object.
     * @param name the name of this tag
     * @param payload a single byte that defines the payload of this tag
     */
    public ByteTag(String name, byte payload) {
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
     * @return A single byte
     */
    @Override
    public Byte getPayload() {
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
     * Compares this instance of a ByteTag object to that of another to see if contain equal values.
     * @param other the other instance of a ByteTag object to compare to
     * @return {@code true} if this list contains the specified element; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ByteTag)) {
            return false;
        }

        return (this.name.equals(other.getName()) && this.payload == ((ByteTag) other).getPayload());
    }

    /**
     * Returns a string representation of this ByteTag object.
     * @return A string representation of this ByteTag object.
     */
    public String toString() {
        return "TAG_Byte('" + this.name + "'): " + this.payload + "\n";
    }

}
