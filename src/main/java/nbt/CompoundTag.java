package nbt;

import java.util.ArrayList;

public class CompoundTag extends Tag {
    private String name;
    private ArrayList<Tag> containedTags;

    private final int TAG_ID = 10;

    public CompoundTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
    }

    @Override
    public int getTagID() {
        return this.TAG_ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public ArrayList<Tag> getContainedTags() {
        return this.containedTags;
    }

    public String toString() {
        return "TAG_Compound('" + this.name + "')";
    }
}
