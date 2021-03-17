package nbt;

public class EndTag extends Tag {
    private final int TAG_ID = 0;

    public EndTag() {}

    @Override
    public int getTagID() {
        return this.TAG_ID;
    }

    @Override
    public String getName() {
        return null;
    }
}
