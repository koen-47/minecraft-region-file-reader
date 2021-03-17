package nbt;

import util.ByteArrayBuilder;

import java.util.ArrayList;

public class CompoundTag extends Tag {
    private String name;
    private ArrayList<Tag> containedTags;

    private final byte TAG_ID = 10;

    public CompoundTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
    }

    public byte getTagID() {
        return this.TAG_ID;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Tag> getValue() {
        return this.containedTags;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.append(this.TAG_ID);
        byteArrayBuilder.append((byte) ((this.name.length() >> 8) & 0xff));
        byteArrayBuilder.append((byte) (this.name.length() & 0xff));
        byteArrayBuilder.append(this.name.getBytes());

        for (Tag containedTag : this.containedTags) {
            byteArrayBuilder.append(containedTag.toByteArray());
        }

        return byteArrayBuilder.getByteArray();
    }

    public String toString() {
        return this.toString(1);
    }

    private String toString(int depth) {
        String whiteSpaces = this.getWhitespaces(depth);

        int numberOfValidEntries = this.containedTags.size()-1;
        String pluralEntries = (numberOfValidEntries == 1) ? "entry" : "entries";
        String finalString = "TAG_Compound('" + this.name + "'): " + numberOfValidEntries + " " + pluralEntries + "\n";
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
