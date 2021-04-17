package nbt.tag;

import util.ByteArrayBuilder;
import util.TagString;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A tag is designed to a hold a fixed number of tag payloads with a specific tag type. It follows the specification
 * laid out by the ListTag in the NBT format.
 * @author Killerkoen
 * @param <T> the tag type of the tags contained within this list tag.
 */
public class ListTag<T extends Tag> extends Tag implements Iterable<Tag> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * The ID of the tags contained within this list tag.
     */
    private byte containedTagID;

    /**
     * An array of tags that is defined as the payload of this tag.
     */
    private T[] containedTags;

    /**
     * The ID of this tag.
     */
    private final byte tagID = 9;

    /**
     * Constructs an unnamed instance of a ListTag object containing the array of tags given by the variable arguments
     * parameter.
     * @param tags a variable arguments parameter that contains the array of tags that are used as a payload for this
     *             tag
     */
    @SafeVarargs
    public ListTag(T ...tags) {
        this.name = "";
        this.containedTagID = tags[0].getTagID();
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs an unnamed instance of a ListTag object containing the array of tags given by the variable arguments
     * parameter and the tag type of that array from the given tag ID parameter.
     * @param containedTagID the ID of the tags that are to be contained with this ListTag object
     * @param tags a variable arguments parameter that contains the array of tags that are used as a payload for this
     *             tag
     */
    @SafeVarargs
    public ListTag(byte containedTagID, T ...tags) {
        this.name = "";
        this.containedTagID = containedTagID;
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs a named instance of a ListTag object containing a name an an array of tags given by the variable
     * arguments parameter.
     * @param name the name of this tag
     * @param tags a variable arguments parameter that contains the array of tags that are used as a payload for this
     *             tag
     */
    @SafeVarargs
    public ListTag(String name, T ...tags) {
        this.name = name;
        this.containedTagID = tags[0].getTagID();
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs a named instance of a ListTag object containing a name, an array of tags given by the variable
     * arguments parameter and the tag type of that array from the given tag ID parameter.
     * @param name the name of this tag
     * @param containedTagID the ID of the tags that are to be contained with this ListTag object
     * @param tags a variable arguments parameter that contains the array of tags that are used as a payload for this
     *             tag
     */
    @SafeVarargs
    public ListTag(String name, byte containedTagID, T ...tags) {
        this.name = name;
        this.containedTagID = containedTagID;
        this.containedTags = tags;
        this.verifyTagTypes();
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Loops over the tags within this list tag to verify that each tag is of the same type.
     */
    private void verifyTagTypes() {
        for (T containedTag : this.containedTags) {
            if (containedTag.getTagID() != this.containedTagID) {
                throw new IllegalArgumentException("All tags in a ListTag must all be of the same type.");
            }

            if (!containedTag.getName().equals("")) {
                throw new IllegalArgumentException("All tags in a ListTag must be unnamed.");
            }
        }
    }

    /**
     * Get the ID of this tag.
     * @return A byte with it's value as an id that is used to identify the type of this tag.
     */
    @Override
    public byte getTagID() {
        return this.tagID;
    }

    /**
     * Get the ID of this tag.
     * @return A byte with it's value as an id that is used to identify the type of the tags contained within this list
     *         tag
     */
    public byte getContainedTagID() {
        return this.containedTagID;
    }

    /**
     * Get the name of this tag.
     * @return A string with this tag's name as it's value
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the payload of this tag.
     * @return An array of tags containing the tags held within this list tag
     */
    public T[] getPayload() {
        return this.containedTags;
    }

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
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

    /**
     * Finds all instances of tags contained in this ListTag object that fit certain conditions defined by the
     * {@code operation} parameter.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return An instance of an ArrayList that contains all the tags that were found
     */
    private ArrayList<Tag> findAll(Class<? extends Tag> targetTagClass, TagOperation operation) {
        ArrayList<Tag> foundTags = new ArrayList<>();
        Iterator<Tag> it = this.iterator();
        while (it.hasNext()) {
            Tag nextTag = it.next();
            if (nextTag.getClass().equals(targetTagClass) && operation.findTag(nextTag)) {
                foundTags.add(nextTag);
            }
        }

        return foundTags;
    }

    /**
     * Finds all the parents of instances of tags contained in this ListTag object that fit certain conditions
     * defined by the {@code operation} parameter.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return An instance of an ArrayList that contains all the parents of tags that were found
     */
    public ArrayList<Tag> findAllParents(Class<? extends Tag> targetTagClass, TagOperation operation) {
        ArrayList<Tag> foundTags = new ArrayList<>();
        Iterator<Tag> it = this.iterator();
        while (it.hasNext()) {
            Tag nextTag = it.next();
            if (nextTag.getClass().equals(targetTagClass) && operation.findTag(nextTag)) {
                foundTags.add(nextTag.getParent());
            }
        }

        return foundTags;
    }

    /**
     * Finds the first instance of a tag contained in this ListTag object that fits certain conditions defined by
     * the {@code operation} parameter.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return The first occurrence of the tag that was found
     */
    public Tag find(Class<? extends Tag> targetTagClass, TagOperation operation) {
        Iterator<Tag> it = this.iterator();
        while (it.hasNext()) {
            Tag nextTag = it.next();
            if (nextTag.getClass().equals(targetTagClass) && operation.findTag(nextTag)) {
                return nextTag;
            }
        }

        return null;
    }

    /**
     * Returns true of this list tag contains the specified element.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return {@code true} if this list tag contains the specified parameter tag; {@code false if otherwise}
     */
    public boolean contains(Class<? extends Tag> targetTagClass, TagOperation operation) {
        return this.find(targetTagClass, operation) != null;
    }

    /**
     * Compares this instance of a ListTag object to that of another to see if contain equal values.
     * @param other the other instance of a ListTag object to compare to
     * @return {@code true} if this tag equals the specified parameter tag; {@code false} if otherwise
     */
    @Override
    public boolean equals(Tag other) {
        if (!(other instanceof ListTag)) {
            return false;
        }

        if (!this.getName().equals(other.getName())) {
            return false;
        }

        if (this.containedTags.length != ((ListTag) other).getPayload().length) {
            return false;
        }

        for (int i = 0; i < this.containedTags.length; i++) {
            if (!this.containedTags[i].equals(((ListTag) other).getPayload()[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of this ListTag object.
     * @return A string representation of this ListTag object.
     */
    public String toString() {
        return new TagString(this).getString();
    }

    /**
     * Returns an iterator that will begin from the start of this list tag.
     * @return An instance of a new Iterator object
     */
    @Override
    public Iterator<Tag> iterator() {
        return new TagIterator(this);
    }
}
