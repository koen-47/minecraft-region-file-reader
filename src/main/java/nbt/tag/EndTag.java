package nbt.tag;

public class EndTag extends Tag {
    private final byte TAG_ID = 0;

    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return null;
    }

    public String getValue() {
        return null;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[] { 0 };
    }

}
