package nbt.tag;

/**
 * A tag that is designed to signal the end of a compound tag. It follows the specification defined by the NBT format.
 * @author Killerkoen
 */
public class EndTag extends Tag<EndTag> {
    /**
     * ID of this tag.
     */
    private final byte tagID = 0;

    /**
     * Get the ID of this tag.
     * @return A byte with it's value as an id that is used to identify the type of this tag.
     */
    public byte getTagID() {
        return this.tagID;
    }

    /**
     * Get the name of this tag.
     * @return A string with this tag's name as it's value
     */
    public String getName() {
        return "";
    }

    /**
     * Get the payload of this tag. This method is not used for anything and only exists because the EndTag class
     * is extended from the Tag class.
     * @return The instance of this EndTag object
     */
    public EndTag getPayload() {
        return this;
    }

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
    @Override
    public byte[] toByteArray() {
        return new byte[] { 0 };
    }

    /**
     * Compares this instance of a EndTag object to that of another to see if contain equal values.
     * @param other the other instance of a EndTag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        return other instanceof EndTag;
    }

    /**
     * Returns a string representation of this EndTag object.
     * @return A string representation of this EndTag object.
     */
    public String toString() {
        return "TAG_End() of " + this.getParent().getName() + "\n";
    }

}
