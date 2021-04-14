package util;

import nbt.tag.CompoundTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;

/**
 * A utility class that designed to stringify an instance of a Tag object in a human readable format.
 * @author Killerkoen
 */
public class TagString {
    /**
     * The Tag object to stringify.
     */
    private Tag currentTag;

    /**
     * The string to hold the stringified Tag object.
     */
    private String completeTagString;

    /**
     * Constructs an instance of a TagString object from the specified tag parameter.
     * @param currentTag the tag to stringify
     */
    public TagString(Tag currentTag) {
        this.currentTag = currentTag;
        this.completeTagString = this.stringify(this.currentTag, 0);
    }

    /**
     * Returns the string that holds the stringified Tag object.
     * @return the string that holds the stringified Tag object
     */
    public String getString() {
        return this.completeTagString;
    }

    /**
     * Returns a complete string representation of the specified tag.
     * @param currentTag the tag to stringify
     * @param depth the depth of the tag inside of a compound or list tag
     * @return A stringified representation of the specified tag
     */
    private String stringify(Tag currentTag, int depth) {
        String finalString = this.getTagHeaderStringBasedOnDepth(currentTag, depth);
        if (currentTag instanceof CompoundTag || currentTag instanceof ListTag) {
            int numberOfEntries = (currentTag instanceof ListTag) ? ((ListTag) currentTag).getPayload().length :
                                                                    ((CompoundTag) currentTag).getPayload().size() - 1;

            for (int i = 0; i < numberOfEntries; i++) {
                Tag nextTag = (currentTag instanceof ListTag) ? ((ListTag) currentTag).getPayload()[i] :
                                                                ((CompoundTag) currentTag).getPayload().get(i);
                finalString += this.stringify(nextTag, depth + 1);
            }

            finalString += this.getWhitespacesBasedOnDepth(depth) + "}\n";
        }

        return finalString;
    }


    /**
     * Returns a string of whitespaces based on the specified depth parameter.
     * @param depth the depth of the tag in a compound or list tag
     * @return A string of whitespaces
     */
    private String getWhitespacesBasedOnDepth(int depth) {
        String whitespaces = "";
        for (int i = 0; i < depth * 4; i++) {
            whitespaces += " ";
        }

        return whitespaces;
    }

    /**
     * Returns a string header of the specified tag with the number of whitespaces this string contains being based on
     * the given depth.
     * @param tag the tag to get it's string header
     * @param depth the depth of the tag inside of a compound or list tag
     * @return A stringified representation of the header of the specified tag
     */
    private String getTagHeaderStringBasedOnDepth(Tag tag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth);
        if ((tag instanceof CompoundTag) || (tag instanceof ListTag)) {
            int numberOfEntries = (tag instanceof CompoundTag) ? ((CompoundTag) tag).getPayload().size() - 1 :
                                                                 ((ListTag) tag).getPayload().length;
            String pluralOfEntry = (numberOfEntries == 1) ? "entry" : "entries";
            String tagType = (tag instanceof CompoundTag) ? "TAG_Compound" : "TAG_List";
            return whitespaces + tagType + "('" + tag.getName() + "'): " + numberOfEntries + " " + pluralOfEntry +
                    "\n" + whitespaces + "{\n";
        }

        return whitespaces + tag.toString();
    }

}
