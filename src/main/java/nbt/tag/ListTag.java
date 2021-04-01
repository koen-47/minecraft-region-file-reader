package nbt.tag;

import util.ByteArrayBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTag<T extends Tag> extends Tag {
    private String name;
    private byte containedTagID;
    private T[] containedTags;

    private final byte TAG_ID = 9;

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

    public Tag find(Class<? extends Tag> targetTagClass, CompoundTagOperation operation) {
        return this.find(targetTagClass, operation, this);
    }

    public Tag find(Class<? extends Tag> targetTagClass, CompoundTagOperation operation, Tag targetTag) {
        if (targetTag.getClass().equals(targetTagClass) && operation.findTag(targetTag)) {
            return targetTag;
        }

        if (targetTag instanceof CompoundTag) {
            for (Tag currentTag : ((CompoundTag) targetTag).getPayload()) {
                Tag foundTag = this.find(targetTagClass, operation, currentTag);
                if (foundTag != null) return foundTag;
            }
        } else if (targetTag instanceof ListTag) {
            for (Tag currentTag : ((ListTag) targetTag).getPayload()) {
                Tag foundTag = this.find(targetTagClass, operation, currentTag);
                if (foundTag != null) return foundTag;
            }
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
}
