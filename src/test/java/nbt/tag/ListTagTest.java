package nbt.tag;

import org.junit.jupiter.api.Test;
import util.ListTagString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListTagTest {

    @Test
    public void testListTagConstructorNamed() {
        ListTag<IntTag> testListTagInteger = new ListTag<IntTag>("testListTagIntTag",
                                                                            new IntTag(1),
                                                                            new IntTag(2),
                                                                            new IntTag(3));

        ListTag<StringTag> testListTagString = new ListTag<StringTag>("testListTagStringTag",
                                                                            new StringTag("test1"),
                                                                            new StringTag("test2"));

        assertEquals(testListTagInteger.getContainedTagID(), 3);
        assertEquals(testListTagInteger.getPayload().length, 3);
        assertEquals(testListTagInteger.getName(), "testListTagIntTag");

        assertEquals(testListTagString.getContainedTagID(), 8);
        assertEquals(testListTagString.getPayload().length, 2);
        assertEquals(testListTagString.getName(), "testListTagStringTag");
    }

    @Test
    public void testIllegalListTag() {
        try {
            ListTag<?> testIllegalListTag = new ListTag("testIllegalListTag",
                                                            new IntTag(1),
                                                            new StringTag("test"));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "All tags in a ListTag must all be of the same type...");
        }
    }

    @Test
    public void testListTagFind1() {
        ListTag testListTag1 = new ListTag("testListTag1",
                                            new CompoundTag(
                                                    new IntTag("testIntTag1", 1),
                                                    new IntTag("testIntTag2", 2)),
                                            new CompoundTag(
                                                    new IntTag("testIntTag3", 3),
                                                    new IntTag("testIntTag4", 4)));

        IntTag targetTag = (IntTag) testListTag1.find(IntTag.class, tag -> ((IntTag) tag).getPayload() == 3);
        System.out.println(targetTag.toString());
    }

    @Test
    public void testListTagFind3() {
        ListTag testCompoundTag = new ListTag("testCompoundTag1",
                                                        new ListTag("testCompoundTag2",
                                                                new IntTag(1),
                                                                new IntTag(2)),
                                                        new ListTag("testCompoundTag3",
                                                                new ListTag("testCompoundTag4",
                                                                        new IntTag(5),
                                                                        new IntTag(6)),
                                                                new ListTag("testCompoundTag5",
                                                                        new IntTag(7),
                                                                        new IntTag(8))));

        IntTag targetTag = (IntTag) testCompoundTag.find(IntTag.class, tag -> ((IntTag) tag).getPayload() == 8);
        System.out.println(targetTag.toString());
    }

    @Test
    public void testListTagEquals() {
        ListTag testListTag1 = new ListTag("testListTag1",
                                                new IntTag(1),
                                                new IntTag(2),
                                                new IntTag(3));

        ListTag testListTag2 = new ListTag("testListTag1",
                                                new IntTag(1),
                                                new IntTag(2),
                                                new IntTag(3));

        assertTrue(testListTag1.equals(testListTag2));
    }

    @Test
    public void testListTagIterator1() {
        ListTag testListTag = new ListTag("testListTag",
                                            new IntTag(1),
                                            new IntTag(2));

        ListTagIterator it = new ListTagIterator(testListTag);
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
    }

    @Test
    public void testListTagIterator2() {
        ListTag testListTag = new ListTag("testListTag",
                                                new ListTag(
                                                        new IntTag(1),
                                                        new IntTag(2)),
                                                new ListTag(
                                                        new IntTag(3),
                                                        new IntTag(4)));

        ListTagIterator it = new ListTagIterator(testListTag);
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
        System.out.println(it.next().toString());
    }

    @Test
    public void testListTagIterator3() {
        ListTag testListTag = new ListTag("testListTag",
                                                new CompoundTag(
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new ListTag("testEmptyListTag", (byte) 10),
                                                        new StringTag("id", "abc"),
                                                        new CompoundTag("testEmptyCompoundTag")),
                                                new CompoundTag(
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new ListTag("testEmptyListTag", (byte) 10),
                                                        new StringTag("id", "abc"),
                                                        new CompoundTag("testEmptyCompoundTag"),
                                                        new ListTag("testIntTagList",
                                                                new IntTag(10),
                                                                new IntTag(11),
                                                                new IntTag(12),
                                                                new IntTag(13),
                                                                new IntTag(14))));

        Iterator<Tag> it = testListTag.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void testListTagIterator4() {
        ListTag testListTag = new ListTag("testListTag",
                                                new CompoundTag(
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new ListTag("testEmptyListTag", (byte) 10),
                                                        new StringTag("id", "abc"),
                                                        new CompoundTag("testEmptyCompoundTag")));


        Iterator<Tag> it = testListTag.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
