package util;

import nbt.CompoundTag;
import nbt.EndTag;
import nbt.Tag;

public class CompoundTagPrinter {
    private CompoundTag compoundTag;

    public CompoundTagPrinter(CompoundTag compoundTag) {
        this.compoundTag = compoundTag;
    }

    public String stringify() {
        int depth = 1;
        String whitespaces = this.getWhitespacesBasedOnDepth(depth);
        String finalString = this.compoundTag.toString() + this.getWhitespacesBasedOnDepth(depth - 1) + "{\n";

        for (Tag currentTag : this.compoundTag.getValue()) {
            if (currentTag instanceof CompoundTag) {
                finalString += this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth-1) + "}\n";
            } else {
                finalString += whitespaces + currentTag.toString();
            }
        }

        return finalString;
    }

    private String stringifyCompoundTag(CompoundTag tag, int depth) {
        String whitespaces = this.getWhitespacesBasedOnDepth(depth-1);
        String finalString = whitespaces + tag.toString() + whitespaces + "{\n";

        for (Tag currentTag : tag.getValue()) {
            if (currentTag instanceof EndTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth-1) + "}\n";
            } else if (currentTag instanceof CompoundTag) {
                finalString += this.getWhitespacesBasedOnDepth(depth-2) + this.stringifyCompoundTag((CompoundTag) currentTag, depth + 1);
            } else {
                finalString += this.getWhitespacesBasedOnDepth(depth) + currentTag.toString();
            }
        }

        return finalString;
    }

    private String getWhitespacesBasedOnDepth(int depth) {
        String whitespaces = "";
        for (int i = 0; i < depth * 4; i++) {
            whitespaces += " ";
        }

        return whitespaces;
    }

    public void print() {
        // TBD
    }
}
