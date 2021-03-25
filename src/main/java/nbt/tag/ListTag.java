package nbt.tag;

import util.ByteArrayBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ListTag extends Tag {
    private String name;
    private ArrayList<Tag> containedTags;

    private final byte TAG_ID = 9;

    public ListTag(ArrayList<Tag> containedTags) {
        this.name = "";
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() );
    }

    public ListTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() ) ;
    }

    public ListTag(String name, Tag ...tags) {
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

    public ArrayList<Tag> getPayload() {
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
        return "TAG_List('" + this.name + "'): " + numberOfEntries + " " + pluralOfEntry + "\n";
    }
}
