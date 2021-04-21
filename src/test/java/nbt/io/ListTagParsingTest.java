package nbt.io;

import nbt.tag.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ListTagParsingTest {

    @Test
    public void testBasicListTag1() throws IOException {
        ListTag<Tag> correctListTagIntTag = new ListTag<Tag>("testListTag",
                                                                        new IntTag(1),
                                                                        new IntTag(2),
                                                                        new IntTag(3));

        byte[] testListTagIntTagByteArray = correctListTagIntTag.toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testListTagIntTagByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);
        ListTag testListTagIntTag = (ListTag) nbtReader.readNamedTag();

        assertArrayEquals(testListTagIntTag.toByteArray(), correctListTagIntTag.toByteArray());
    }

    @Test
    public void testListTagToByteArray1() throws IOException {
        ListTag testListTag = new ListTag("testListTag",
                                                    new IntArrayTag(1),
                                                    new IntArrayTag(2, 3),
                                                    new IntArrayTag(4, 5, 6));

        ByteArrayInputStream byteStream = new ByteArrayInputStream(testListTag.toByteArray());
        NBTFileInputStream nbtParser = new NBTFileInputStream(byteStream);
        System.out.println(nbtParser.readNamedTag().toString());
    }

    @Test
    public void testListTagToByteArray2() throws IOException {
        ListTag testListTag = new ListTag("testListTag",
                                                    new StringTag("1"),
                                                    new StringTag("2, 3"),
                                                    new StringTag("4, 5, 6"));

        System.out.println(Arrays.toString(testListTag.toByteArray()));
        ByteArrayInputStream byteStream = new ByteArrayInputStream(testListTag.toByteArray());
        NBTFileInputStream nbtParser = new NBTFileInputStream(byteStream);
        System.out.println(nbtParser.readNamedTag().toString());
    }

    @Test
    public void testListTagToByteArray3() throws IOException {
        ListTag testListTag = new ListTag("testListTag",
                                                    new ListTag(
                                                            new IntTag(1),
                                                            new IntTag(2)),
                                                    new ListTag(
                                                            new LongTag(3L),
                                                            new LongTag(4L)));

        System.out.println(testListTag.toString());
        System.out.println(Arrays.toString(testListTag.toByteArray()));

        ByteArrayInputStream byteStream = new ByteArrayInputStream(testListTag.toByteArray());
        NBTFileInputStream nbtParser = new NBTFileInputStream(byteStream);
        System.out.println(nbtParser.readNamedTag());
    }

    @Test
    public void testListTagToByteArray4() throws IOException {
        ListTag testListTag = new ListTag("testListTag",
                                            new CompoundTag(
                                                    new IntTag("testIntTag1", 1),
                                                    new IntTag("testIntTag2", 2)),
                                            new CompoundTag(
                                                    new StringTag("testStringTag1", "test123"),
                                                    new StringTag("testStringTag2", "test123")));

        System.out.println(testListTag.toString());
        System.out.println(Arrays.toString(testListTag.toByteArray()));

        ByteArrayInputStream byteStream = new ByteArrayInputStream(testListTag.toByteArray());
        NBTFileInputStream nbtParser = new NBTFileInputStream(byteStream);
        System.out.println(nbtParser.readNamedTag());
    }

}
