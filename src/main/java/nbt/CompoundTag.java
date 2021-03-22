package nbt;

import util.ByteArrayBuilder;
import util.CompoundTagString;

import java.util.ArrayList;
import java.util.Arrays;

public class CompoundTag extends Tag {
    private String name;
    private ArrayList<Tag> containedTags;

    private final byte TAG_ID = 10;

    public CompoundTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() ) ;
    }

    public CompoundTag(String name, Tag ...tags) {
        this.name = name;
        this.containedTags = new ArrayList<>();
        this.containedTags.addAll(Arrays.asList(tags));
        this.containedTags.add( new EndTag() );
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

    public void prepend(Tag tag) {
        this.containedTags.add(0, tag);
    }

    public void append(Tag tag) {
        this.containedTags.add( Math.max(0, this.containedTags.size()-1), tag );
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);

        for (Tag containedTag : this.containedTags) {
            byteArrayBuilder.append(containedTag.toByteArray());
        }

        return byteArrayBuilder.getByteArray();
    }


    public String toString() {
        int numberOfEntries = this.containedTags.size()-1;
        String pluralOfEntry = (numberOfEntries == 1) ? "entry" : "entries";
        return "TAG_Compound('" + this.name + "'): " + numberOfEntries + " " + pluralOfEntry + "\n";
    }
}
