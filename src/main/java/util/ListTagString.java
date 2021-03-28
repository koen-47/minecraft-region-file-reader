package util;

import nbt.tag.ListTag;

public class ListTagString {
    private ListTag listTag;
    private String listTagString;

    public ListTagString(ListTag listTag) {
        this.listTag = listTag;
        this.listTagString = this.stringify();
    }

    public String getString() {
        return this.listTagString;
    }

    private String stringify() {
        String finalString = listTag.toString() + "{\n";

        for (int i = 0; i < listTag.getPayload().length; i++)
            finalString += "    " + listTag.getPayload()[i].toString();

        return finalString + "}\n";
    }
}
