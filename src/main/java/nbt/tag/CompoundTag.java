package nbt.tag;

import util.ByteArrayBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CompoundTag extends Tag implements Iterable<Tag> {
    private String name;
    private ArrayList<Tag> containedTags;

    private final byte TAG_ID = 10;

    public CompoundTag(ArrayList<Tag> containedTags) {
        this.name = "";
        this.containedTags = containedTags;
        this.containedTags.add( new EndTag() );
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    public CompoundTag(Tag ...tags) {
        this.name = "";
        this.containedTags = new ArrayList<>();
        this.containedTags.addAll(Arrays.asList(tags));
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

    public ArrayList<Tag> findAll(Class<? extends Tag> targetTagClass, CompoundTagOperation operation) {
        return this.findAll(targetTagClass, operation, this, new ArrayList<Tag>());
    }

    private ArrayList<Tag> findAll(Class<? extends Tag> targetTagClass, CompoundTagOperation operation, Tag targetTag, ArrayList<Tag> currentlyFoundTags) {
        return null;
    }

    public Tag find(Class<? extends Tag> targetTagClass, CompoundTagOperation operation) {
        return this.find(targetTagClass, operation, this);
    }

    private Tag find(Class<? extends Tag> targetTagClass, CompoundTagOperation operation, Tag targetTag) {
        if (targetTag.getClass().equals(targetTagClass) && operation.findTag(targetTag)) {
            return targetTag;
        }

        if (targetTag instanceof CompoundTag) {
            for (Tag currentTag : ((CompoundTag) targetTag).getPayload()) {
                Tag foundTag = this.find(targetTagClass, operation, currentTag);
                if (foundTag != null) {
                    return foundTag;
                }
            }
        } else if (targetTag instanceof ListTag) {
            for (Tag currentTag : ((ListTag) targetTag).getPayload()) {
                Tag foundTag = this.find(targetTagClass, operation, currentTag);
                if (foundTag != null) return foundTag;
            }
        }


        return null;
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

    @Override
    public Iterator<Tag> iterator() {
        return null;
    }

    public List<Tag> toList() {
        return this.toList(this);
    }

    private List<Tag> toList(Tag tag) {
        List<Tag> compoundedTagList = new ArrayList<>();
        if (tag instanceof CompoundTag) {
            for (Tag currentTag : ((CompoundTag) tag).getPayload()) {
                if (currentTag instanceof CompoundTag) {
                    List<Tag> nestedList = this.toList(currentTag);
                    compoundedTagList.addAll(nestedList);
                } else if (currentTag instanceof ListTag) {
                    List<Tag> nestedList = this.toList(currentTag);
                    compoundedTagList.addAll(nestedList);
                } else if (!(currentTag instanceof EndTag)) {
                    compoundedTagList.add(currentTag);
                }
            }
        }

        return compoundedTagList;
    }
}
