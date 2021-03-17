package nbt;

import java.util.ArrayList;

public class NBTTagPrinter {
    public static void printCompoundTag(CompoundTag tag) {
        ArrayList<Tag> containedTags = tag.getContainedTags();
        int depth = 1;

        System.out.println(tag.toString());
        for (int i = 0; i < tag.getContainedTags().size(); i++) {
            if (tag.getContainedTags().get(i) instanceof CompoundTag) {
                printCompoundTag((CompoundTag) tag.getContainedTags().get(i));
            }

            String tabbedSpaces = getTabbedSpaces(depth);
            String tagToPrint = tag.getContainedTags().get(i).toString();

            System.out.println(tabbedSpaces + tagToPrint);
        }
    }

    private static String getTabbedSpaces(int amount) {
        String tabbedSpaces = "";
        for (int i = 0; i < amount*4; i++) {
            tabbedSpaces += " ";
        }

        return tabbedSpaces;
    }
}
