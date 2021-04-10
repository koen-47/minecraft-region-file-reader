package nbt.tag;

import util.ByteArrayBuilder;

/**
 * A tag is designed to hold a single double and a name that may be associated with it. The double value follows the
 * double-precision floating-point representation and the DoubleTag follows the specification defined by the NBT format.
 * @author Killerkoen
 */
public class DoubleTag extends Tag<Double> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * A single double that defines the payload of this tag.
     */
    private double payload;

    /**
     * The ID of this tag.
     */
    private final byte tagID = 6;

    /**
     * Constructs an unnamed instance of a DoubleTag object.
     * @param payload a single double that defines the payload of this tag
     */
    public DoubleTag(double payload) {
        this.name = "";
        this.payload = payload;
    }

    /**
     * Constructs a named instance of a DoubleTag object.
     * @param name the name of this tag
     * @param payload a single double that defines the payload of this tag
     */
    public DoubleTag(String name, double payload) {
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
     * @return A single double
     */
    @Override
    public Double getPayload() {
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
     * Compares this instance of a DoubleTag object to that of another to see if contain equal values.
     * @param other the other instance of a DoubleTag object to compare to
     * @return {@code true} if this list contains the specified element; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof DoubleTag)) {
            return false;
        }

        return (this.name.equals(other.getName()) && this.payload == ((DoubleTag) other).getPayload());
    }

    /**
     * Returns a string representation of this DoubleTag object.
     * @return A string representation of this DoubleTag object.
     */
    public String toString() {
        return "TAG_Double('" + this.name + "'): " + this.payload + "\n";
    }
}
