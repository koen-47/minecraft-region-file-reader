package util;

import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;

import java.util.Iterator;

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
