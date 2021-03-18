package nbt.parsing;

import nbt.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CompoundTagParsingTest {

    @Test
    public void testCompoundTagByteArray() throws IOException {
        ArrayList<Tag> containedTagsOuter = new ArrayList<Tag>();
        containedTagsOuter.add(new IntTag("testIntTag1", 1));
        containedTagsOuter.add(new IntTag("testIntTag2", 2));

        ArrayList<Tag> containedTagsInner = new ArrayList<Tag>();
        containedTagsInner.add(new IntTag("testNestedIntTag1", 3));
        containedTagsInner.add(new IntTag("testNestedIntTag2", 4));
        containedTagsInner.add(new EndTag());

        containedTagsOuter.add(new CompoundTag("testNestedCompoundTag", containedTagsInner));
        containedTagsOuter.add(new EndTag());

        byte[] testByteArray = new CompoundTag("testCompoundTag", containedTagsOuter).toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readTag();
        System.out.println(testTag.toString());
    }

    @Test
    public void testBasicCompoundTag1() throws IOException {
        byte[] testChunkData = new byte[] {10, 0, 12, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100,
                                                3, 0, 7, 116, 101, 115, 116, 73, 110, 116, 0, 0, 0, 10,
                                            0};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readTag();
        System.out.println(testTag.toString());
    }

    @Test
    public void testBasicCompoundTag2() throws IOException {
        ArrayList<Tag> containedTagsOuter = new ArrayList<Tag>();
        containedTagsOuter.add(new IntTag("testIntTag1", 1));
        containedTagsOuter.add(new IntTag("testIntTag2", 2));

        ArrayList<Tag> containedTagsInner = new ArrayList<Tag>();
        containedTagsInner.add(new IntTag("testNestedIntTag1", 3));
        containedTagsInner.add(new EndTag());

        containedTagsOuter.add(new CompoundTag("testNestedCompoundTag", containedTagsInner));
        containedTagsOuter.add(new EndTag());

        byte[] testByteArray = new CompoundTag("testCompoundTag", containedTagsOuter).toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readTag();
        //System.out.println(testTag.toString());
    }
}
