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
        this.completeTagString = this.stringify(this.currentTag);
    }

    /**
     * Returns the string that holds the stringified Tag object.
     * @return the string that holds the stringified Tag object
     */
    public String getString() {
        return this.completeTagString;
    }

    private String stringify(Tag currentTag) {
        Iterator<Tag> it = this.getIteratorType(currentTag);
        String finalString = this.getTagHeaderStringBasedOnDepth(currentTag, 0);
        int depth = 1;

        if (it != null) {
            while (it.hasNext()) {
                Tag nextTag = it.next();
                if (nextTag instanceof CompoundTag) {
                    finalString += this.getTagHeaderStringBasedOnDepth(nextTag, depth);
                    depth += 1;
                } else if (nextTag instanceof EndTag) {
                    finalString += this.getWhitespacesBasedOnDepth(depth - 1) + "}\n";
                    depth -= 1;
                } else if (nextTag instanceof ListTag) {

                } else {
                    finalString += this.getTagHeaderStringBasedOnDepth(nextTag, depth);
                }
            }
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
                                                                 ((ListTag) tag).getPayload().length - 1;
            String pluralOfEntry = (numberOfEntries == 1) ? "entry" : "entries";
            String tagType = (tag instanceof CompoundTag) ? "TAG_Compound" : "TAG_List";
            return whitespaces + tagType + "('" + tag.getName() + "'): " + numberOfEntries + " " + pluralOfEntry +
                    "\n" + whitespaces + "{\n";
        }

        return whitespaces + tag.toString();
    }

    private Iterator<Tag> getIteratorType(Tag tag) {
        Iterator<Tag> it = null;
        if (tag instanceof CompoundTag) {
            it = ((CompoundTag) tag).iterator();
        } else if (tag instanceof ListTag) {
            it = ((ListTag) tag).iterator();
        }

        return it;
    }

}
