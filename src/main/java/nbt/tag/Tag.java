package nbt.tag;

/**
 * An abstract class that outlines the basic fields and methods that all tags must contain.
 * @author Killerkoen
 * @param <T> the type of payload that is contained within this tag
 */
public abstract class Tag<T> {
    /**
     * The parent of this tag.
     */
    private Tag parent;

    /**
     * Get the ID of this tag.
     * @return A byte with it's value as an id that is used to identify the type of this tag.
     */
    public abstract byte getTagID();

    /**
     * Get the name of this tag.
     * @return A string with this tag's name as it's value
     */
    public abstract String getName();

    /**
     * Get the payload of this tag.
     * @return A generic type that changes based on the tag type
     */
    public abstract T getPayload();

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
    public abstract byte[] toByteArray();

    /**
     * Get the parent of this tag.
     * @return Another tag which is the parent of this tag
     */
    public Tag getParent() {
        return this.parent;
    }

    /**
     * Set the parent of this tag.
     * @param parent The tag to set the parent to
     */
    public void setParent(Tag parent) {
        this.parent = parent;
    }

    /**
     * Compares this instance of a Tag object to that of another to see if contain equal values.
     * @param other the other instance of a Tag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    public abstract boolean equals(Tag other);
}
