package nbt;

public class EndTag extends Tag {
    private final int TAG_ID = 0;

    public EndTag() {}

    public int getTagID() {
        return this.TAG_ID;
    }
    public String getName() {
        return null;
    }
    public String getValue() { return null; }

}
