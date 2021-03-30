package nbt.tag;

import util.ByteArrayBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundTag extends Tag {
    private String name;
    private ArrayList<Tag> containedTags;

    private final byte TAG_ID = 10;

    public CompoundTag(ArrayList<Tag> containedTags) {
        this.name = "";
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() );
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    public CompoundTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() ) ;
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    public CompoundTag(String name, Tag ...tags) {
        this.name = name;
        this.containedTags = new ArrayList<>();
        this.containedTags.addAll(Arrays.asList(tags));
        this.containedTags.add( new EndTag() );
        for (Tag currentTag : containedTags) currentTag.setParent(this);
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

    public Tag find(Tag targetTag) { return this.find(this, targetTag); }

    private Tag find(Tag currentTag, Tag targetTag) {
        if (currentTag != null && currentTag.equals(targetTag))
            return currentTag;

        if (currentTag instanceof CompoundTag) {
            for (Tag tag : ((CompoundTag) currentTag).containedTags) {
                Tag target = this.find(tag, targetTag);
                if (target != null) {
                    return target;
                }
            }
        } else if (currentTag instanceof ListTag) {
            for (Tag tag : ((ListTag) currentTag).getPayload()) {
                Tag target = this.find(tag, targetTag);
                if (target != null) {
                    return target;
                }
            }
        }

        return null;
    }

    public boolean contains(Tag targetTag) {
        return this.find(this, targetTag) != null;
    }

    public boolean equals(Tag other) {
        if (!(other instanceof CompoundTag))
            return false;

        if (!this.getName().equals(other.getName()))
            return false;

        if (this.containedTags.size() != ((CompoundTag) other).getPayload().size())
            return false;

        for (int i = 0; i < this.containedTags.size(); i++)
            if (!this.containedTags.get(i).equals(((CompoundTag) other).getPayload().get(i))) return false;

        return true;
    }


    public String toString() {
        int numberOfEntries = this.containedTags.size()-1;
        String pluralOfEntry = (numberOfEntries == 1) ? "entry" : "entries";
        return "TAG_Compound('" + this.name + "'): " + numberOfEntries + " " + pluralOfEntry + "\n";
    }
}
