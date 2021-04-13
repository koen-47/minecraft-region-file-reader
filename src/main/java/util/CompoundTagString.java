package util;

import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;

/**
 * A utility class that designed to completely stringify a CompoundTag object in a human readable format.
 * @author Killerkoen
 */
public class CompoundTagString {
    /**
     * The CompoundTag object to stringify.
     */
    private CompoundTag compoundTag;

    /**
     * The string to hold the stringified CompoundTag object.
     */
    private String compoundTagString;

    /**
     * Constructs an instance of a CompoundTagStringObject from the specified compound tag parameter.
     * @param compoundTag the compound tag to stringify
     */
    public CompoundTagString(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
        this.compoundTagString = this.stringify();
    }

    /**
     * Returns the string that holds the stringified CompoundTag object.
     * @return the string that holds the stringified CompoundTag object
     */
    public String getString() {
        return this.compoundTagString;
    }

    /**
     * Stringifies all tags within this CompoundTag object.
     * @return the completely stringified compound tag
     */
    private String stringify() {
        return this.stringifyCompoundTag(this.compoundTag, 1);
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
