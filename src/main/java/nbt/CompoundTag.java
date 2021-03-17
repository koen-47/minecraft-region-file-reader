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

    public int getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Tag> getValue() {
        return this.containedTags;
    }

    public String toString() {
        return this.toString(1);
    }

    private String toString(int depth) {
        String whiteSpaces = this.getWhitespaces(depth);

        int numberOfValidEntries = this.containedTags.size()-1;
        String finalString = "TAG_Compound('" + this.name + "'): " + numberOfValidEntries + " entries\n";
        finalString += this.getWhitespaces(depth-1) + "{\n";

        for (Tag containedTag : this.containedTags) {
            if (containedTag instanceof CompoundTag) {
                finalString += whiteSpaces + ((CompoundTag) containedTag).toString(depth + 1);
            } else if (containedTag instanceof EndTag) {
                finalString += this.getWhitespaces(depth-1) + "}\n";
            } else {
                finalString += whiteSpaces + containedTag.toString();
            }

        }

        return finalString;
    }

    private String getWhitespaces(int amount) {
        String whiteSpaces = "";
        for (int i = 0; i < amount * 4; i++) {
            whiteSpaces += " ";
        }

        return whiteSpaces;
    }
}
