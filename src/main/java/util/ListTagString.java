package util;

import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;

/**
 * A utility class that designed to completely stringify a ListTag object in a human readable format.
 * @author Killerkoen
 */
public class ListTagString {
    /**
     * The ListTag object to stringify.
     */
    private ListTag listTag;

    /**
     * The string that holds the stringified ListTag object.
     */
    private String listTagString;

    /**
     * Constructs an instance of a ListTagStringObject from the specified compound tag parameter.
     * @param listTag an instance of the ListTag object to stringify
     */
    public ListTagString(ListTag listTag) {
        this.listTag = listTag;
        this.listTagString = this.stringify();
    }

    /**
     * Returns the string that holds the stringified ListTag object.
     * @return the string that holds the stringified ListTag object
     */
    public String getString() {
        return this.listTagString;
    }

    /**
     * Stringifies all tags within this ListTag object.
     * @return the completely stringified list tag
     */
    private String stringify() {
        int depth = 1;
        String whitespaces = this.getWhitespacesBasedOnDepth(depth);
        String finalString = this.listTag.toString() + this.getWhitespacesBasedOnDepth(depth - 1) + "{\n";

        for (Tag currentTag : this.listTag.getPayload()) {
            if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth - 1) + "}\n";
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += whitespaces + currentTag.toString();
            }
        }

        return finalString;
    }

    /**
     * Stringifies an instance of the given CompoundTag object with the specified depth parameter.
     * @param tag the compound tag to stringify
     * @param depth the depth of the tag in the compound tag
     * @return The stringified compound tag
     */
    private String stringifyCompoundTag(CompoundTag tag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth - 1);
        String finalString = whitespaces + tag.toString() + whitespaces + "{\n";

        for (Tag currentTag : tag.getPayload()) {
            if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth - 1) + "}\n";
            } else if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += this.getWhitespacesBasedOnDepth(depth) + currentTag.toString();
            }
        }

        return finalString;
    }

    /**
     * Stringifies an instance of the given ListTag object with the specified depth parameter.
     * @param listTag the compound tag to stringify
     * @param depth the depth of the tag in the list tag
     * @return The stringified list tag
     */
    private String stringifyListTag(ListTag<?> listTag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth - 1);
        String finalString = whitespaces + listTag.toString() + whitespaces + "{\n";

        for (Tag currentTag : listTag.getPayload()) {
            if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof ListTag) {
                finalString += this.stringifyListTag((ListTag) currentTag, depth + 1);
            } else {
                finalString += this.getWhitespacesBasedOnDepth(depth) + currentTag.toString();
            }
        }

        return finalString + this.getWhitespacesBasedOnDepth(depth - 1) + "}\n";
    }

    /**
     * Returns a string of whitespaces based on the specified depth parameter.
     * @param depth the depth of the tag in this compound or list tag
     * @return A string of whitespaces
     */
    private String getWhitespacesBasedOnDepth(int depth) {
        String whitespaces = "";
        for (int i = 0; i < depth * 4; i++) {
            whitespaces += " ";
        }

        return whitespaces;
    }
}
