package nbt.tag;

import util.ByteArrayBuilder;

import java.util.ArrayList;
import java.util.Iterator;

public class ListTag<T extends Tag> extends Tag implements Iterable<Tag> {
    private String name;
    private byte containedTagID;
    private T[] containedTags;

    private final byte TAG_ID = 9;

    @SafeVarargs
    public ListTag(T ...tags) {
        this.name = "";
        this.containedTagID = tags[0].getTagID();
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    @SafeVarargs
    public ListTag(byte containedTagID, T ...tags) {
        this.name = "";
        this.containedTagID = containedTagID;
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    @SafeVarargs
    public ListTag(String name, T ...tags) {
        this.name = name;
        this.containedTagID = tags[0].getTagID();
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    @SafeVarargs
    public ListTag(String name, byte containedTagID, T ...tags) {
        this.name = name;
        this.containedTagID = containedTagID;
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) currentTag.setParent(this);
    }

    private void verifyTagTypes() {
        for (int i = 0; i < this.containedTags.length; i++) {
            if (this.containedTags[i].getTagID() != this.containedTagID) {
                throw new IllegalArgumentException("All tags in a ListTag must all be of the same type...");
            }
        }
    }

    public byte getTagID() {
        return this.TAG_ID;
    }

    public byte getContainedTagID() {
        return this.containedTagID;
    }

    public String getName() {
        return this.name;
    }

    public T[] getPayload() {
        return this.containedTags;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);
        byteArrayBuilder.append(this.containedTagID);
        byteArrayBuilder.append(this.containedTags.length);

        for (T containedTag : this.containedTags) {
            byteArrayBuilder.appendTagPayload(containedTag);
        }

        return byteArrayBuilder.getByteArray();
    }

    private ArrayList<Tag> findAll(Class<? extends Tag> targetTagClass, TagOperation operation, Tag targetTag, ArrayList<Tag> currentlyFoundTags) {
        ArrayList<Tag> foundTags = new ArrayList<>();
        Iterator<Tag> it = this.iterator();
        while (it.hasNext()) {
            Tag nextTag = it.next();
            if (nextTag.getClass().equals(targetTagClass) && operation.findTag(nextTag))
                foundTags.add(nextTag);
        }

        return foundTags;
    }

    public Tag find(Class<? extends Tag> targetTagClass, TagOperation operation) {
        Iterator<Tag> it = this.iterator();
        while (it.hasNext()) {
            Tag nextTag = it.next();
            if (nextTag.getClass().equals(targetTagClass) && operation.findTag(nextTag))
                return nextTag;
        }

        return null;
    }

    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ListTag))
            return false;

        if (!this.getName().equals(other.getName()))
            return false;

        if (this.containedTags.length != ((ListTag) other).getPayload().length)
            return false;

        for (int i = 0; i < this.containedTags.length; i++) {
            if (!this.containedTags[i].equals(((ListTag) other).getPayload()[i])) return false;
        }

        return true;
    }

    public String toString() {
        int numberOfEntries = this.containedTags.length;
        String pluralOfEntry = (numberOfEntries == 1) ? "entry" : "entries";
        return "TAG_List('" + this.name + "'): " + numberOfEntries + " " + pluralOfEntry + "\n";
    }

    @Override
    public Iterator<Tag> iterator() {
        return new TagIterator(this);
    }
}
