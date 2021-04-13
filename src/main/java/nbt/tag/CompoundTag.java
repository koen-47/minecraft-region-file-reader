package nbt.tag;

import util.ByteArrayBuilder;
import util.TagString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A tag that contains an unspecified number of tags that are guaranteed to contain a name and a payload corresponding
 * to the type of that tag. It follows the specification laid out by the ByteArrayTag in the NBT format.
 * @author Killerkoen
 */
public class CompoundTag extends Tag implements Iterable<Tag> {
    /**
     * The name of this tag.
     */
    private String name;

    /**
     * An instance of an ArrayList object that contains the named tags that are used as a payload for this tag.
     */
    private ArrayList<Tag> containedTags;

    /**
     * The ID of this tag type.
     */
    private final byte tagID = 10;

    /**
     * Constructs an unnamed instance of a CompoundTag object.
     * @param containedTags an instance of an ArrayList object that contains the named tags that
     *                      are used as a payload for this tag.
     */
    public CompoundTag(ArrayList<Tag> containedTags) {
        this.name = "";
        this.containedTags = containedTags;
        this.containedTags.add(new EndTag());
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs an unnamed instance of a CompoundTag object.
     * @param tags a variable arguments parameter that contains an array of different tag types that are used as a
     *             payload for this tag
     */
    public CompoundTag(Tag ...tags) {
        this.name = "";
        this.containedTags = new ArrayList<>();
        this.containedTags.addAll(Arrays.asList(tags));
        this.containedTags.add(new EndTag());
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs a named instance of a CompoundTag object.
     * @param name the name of this tag
     * @param containedTags an instance of an ArrayList object that contains the named tags that
     *                      are used as a payload for this tag.
     */
    public CompoundTag(String name, ArrayList<Tag> containedTags) {
        this.name = name;
        this.containedTags = containedTags;
        this.containedTags.add(new EndTag());
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
        }
    }

    /**
     * Constructs a named instance of a CompoundTag object.
     * @param name the name of this tag
     * @param tags a variable arguments parameter that contains an array of different tag types that are used as a
     *             payload for this tag
     */
    public CompoundTag(String name, Tag ...tags) {
        this.name = name;
        this.containedTags = new ArrayList<>();
        this.containedTags.addAll(Arrays.asList(tags));
        this.containedTags.add(new EndTag());
        for (Tag currentTag : containedTags) {
            currentTag.setParent(this);
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
     * Get the name of this tag.
     * @return A string with this tag's name as it's value
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the payload of this tag.
     * @return An instance of an ArrayList containing the tags held within this compound tag
     */
    @Override
    public ArrayList<Tag> getPayload() {
        return this.containedTags;
    }

    /**
     * Adds the specified tag to the start of this compound tag.
     * @param tag the tag to be added to this compound tag
     */
    public void prepend(Tag tag) {
        this.containedTags.add(0, tag);
    }

    /**
     * Adds the specified tag to the end of this compound tag.
     * @param tag the tag to be added to this compound tag
     */
    public void append(Tag tag) {
        this.containedTags.add(Math.max(0, this.containedTags.size() - 1), tag);
    }

    /**
     * Generates an array of bytes corresponding to the specification laid out in the NBT format.
     * @return An array of bytes corresponding to the specification laid out in the NBT format
     */
    @Override
    public byte[] toByteArray() {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        byteArrayBuilder.appendTagHeader(this);

        for (Tag containedTag : this.containedTags) {
            byteArrayBuilder.append(containedTag.toByteArray());
        }

        return byteArrayBuilder.getByteArray();
    }

    /**
     * Finds all instances of tags contained in this CompoundTag object that fit certain conditions defined by the
     * {@code operation} parameter.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return An instance of an ArrayList that contains all the tags that were found
     */
    public ArrayList<Tag> findAll(Class<? extends Tag> targetTagClass, TagOperation operation) {
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
     * Finds all the parents of instances of tags contained in this CompoundTag object that fit certain conditions
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
     * Finds the first instance of a tag contained in this CompoundTag object that fits certain conditions defined by
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
     * Returns true of this compound tag contains the specified element.
     * @param targetTagClass the class type of the tag that is to be found
     * @param operation the conditions that the target tag must fulfill
     * @return {@code true} if this list contains the specified element; {@code false if otherwise}
     */
    public boolean contains(Class<? extends Tag> targetTagClass, TagOperation operation) {
        return this.find(targetTagClass, operation) != null;
    }

    /**
     * Compares this instance of a CompoundTag object to that of another to see if contain equal values.
     * @param other the other instance of a CompoundTag object to compare to
     * @return {@code true} if this list contains the specified element; {@code false} if otherwise
     */
    public boolean equals(Tag other) {
        if (!(other instanceof CompoundTag)) {
            return false;
        }

        if (!this.getName().equals(other.getName())) {
            return false;
        }

        if (this.containedTags.size() != ((CompoundTag) other).getPayload().size()) {
            return false;
        }

        for (int i = 0; i < this.containedTags.size(); i++) {
            if (!this.containedTags.get(i).equals(((CompoundTag) other).getPayload().get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a string representation of this CompoundTag object. <br> <br>
     * This method does not return a string representation of all the other tags contained within this compound
     * tag. Instead it returns this tag's name and the number of entries within it. In order to generate such a
     * string representation, the CompoundTagString class must be used instead.
     * @return A string representation of this CompoundTag object.
     */
    public String toString() {
        return new TagString(this).getString();
    }

    /**
     * Returns an iterator that will begin from the start of this compound tag.
     * @return An instance of a new Iterator object
     */
    @Override
    public Iterator<Tag> iterator() {
        return new TagIterator(this);
    }
}
