package nbt.tag;

public class EndTag extends Tag<EndTag> {
    private final byte TAG_ID = 0;

    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return null;
    }

    public EndTag getPayload() {
        return null;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[] { 0 };
    }

    public String toString() {
        return "TAG_End() of " + this.getParent().getName() + "\n";
    }

    @Override
    public boolean equals(Tag other) {
        return other instanceof EndTag;
    }

}
