package nbt.parsing;

import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.IntTag;
import nbt.tag.Tag;
import org.junit.jupiter.api.Test;
import util.CompoundTagString;

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

        containedTagsOuter.add(new CompoundTag("testNestedCompoundTag", containedTagsInner));

        byte[] testByteArray = new CompoundTag("testCompoundTag", containedTagsOuter).toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readNamedTag();
        System.out.println(testTag.toString());
    }

    @Test
    public void testBasicCompoundTag1() throws IOException {
        byte[] testChunkData = new byte[] {10, 0, 12, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100,
                                                3, 0, 7, 116, 101, 115, 116, 73, 110, 116, 0, 0, 0, 10,
                                            0};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readNamedTag();
        System.out.println(new CompoundTagString(testTag).getString());
    }

    @Test
    public void testBasicCompoundTag2() throws IOException {
        ArrayList<Tag> containedTagsOuter = new ArrayList<Tag>();
        containedTagsOuter.add(new IntTag("testIntTag1", 1));
        containedTagsOuter.add(new IntTag("testIntTag2", 2));

        ArrayList<Tag> containedTagsInner = new ArrayList<Tag>();
        containedTagsInner.add(new IntTag("testNestedIntTag1", 3));

        containedTagsOuter.add(new CompoundTag("testNestedCompoundTag", containedTagsInner));

        byte[] testByteArray = new CompoundTag("testCompoundTag", containedTagsOuter).toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readNamedTag();
        System.out.println(new CompoundTagString(testTag).getString());
    }
}
