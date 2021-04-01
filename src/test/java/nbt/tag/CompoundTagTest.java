package nbt.tag;

import org.junit.jupiter.api.Test;
import util.CompoundTagRandomizer;
import util.CompoundTagString;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompoundTagTest {

    @Test
    public void testCompoundTagConstructors() {
        ArrayList<Tag> containedTags = new ArrayList<>();
        containedTags.add(new IntTag("testIntTag", 1));
        containedTags.add(new StringTag("testStringTag", "abc"));
        CompoundTag testCompoundTagConstructor1 = new CompoundTag("testCompoundTag", containedTags);

        CompoundTag testCompoundTagConstructor2 = new CompoundTag("testCompoundTag",
                                                                        new IntTag("testIntTag", 1),
                                                                        new StringTag("testStringTag", "abc"));

        assertTrue(testCompoundTagConstructor1.equals(testCompoundTagConstructor2));
    }

    @Test
    public void testCompoundTagToByteArray1() {
        ArrayList<Tag> containedTags = new ArrayList<>();
        containedTags.add(new IntTag("testIntTag", 1));
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", containedTags);

        byte[] correctCompoundTagBytes = new byte[] {10, 0, 15, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100, 84, 97, 103,
                                                        3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 1,
                                                     0};

        assertArrayEquals(testCompoundTag.toByteArray(), correctCompoundTagBytes);
    }

    @Test
    public void testCompoundTagToByteArray2() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", new IntTag("testIntTag", 1));

        byte[] correctCompoundTagBytes = new byte[] {10, 0, 15, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100, 84, 97, 103,
                                                        3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 1,
                                                     0};

        assertArrayEquals(testCompoundTag.toByteArray(), correctCompoundTagBytes);
    }

    @Test
    public void testCompoundTagAppend() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                             new IntTag("testIntTag1", 1));

        testCompoundTag.append(new IntTag("testIntTag2", 2));

        CompoundTag correctCompoundTag = new CompoundTag("testCompoundTag",
                                                                new IntTag("testIntTag1", 1),
                                                                new IntTag("testIntTag2", 2));

        assertTrue(testCompoundTag.equals(correctCompoundTag));
    }

    @Test
    public void testCompoundTagEquals() {
        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                                 new IntTag("testIntTagInner1", 1),
                                                                 new IntTag("testIntTagInner2", 2));

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                                 new IntTag("testIntTagOuter1", 3),
                                                                 new IntTag("testIntTagOuter2", 4),
                                                                 testCompoundTagInner);

        CompoundTag correctCompoundTag = new CompoundTag("testCompoundTagOuter",
                                                                new IntTag("testIntTagOuter1", 3),
                                                                new IntTag("testIntTagOuter2", 4),
                                                                new CompoundTag("testCompoundTagInner",
                                                                        new IntTag("testIntTagInner1", 1),
                                                                        new IntTag("testIntTagInner2", 2)));

        assertTrue(testCompoundTagOuter.equals(correctCompoundTag));
    }

    @Test
    public void testCompoundTagEqualsBig() {
        final int MAX_NUMBER_OF_NESTED_COMPOUND_TAGS = 5;
        final int MAX_NUMBER_OF_TAGS_IN_COMPOUND_TAG = 2048;
        CompoundTag testCompleteCompoundTag = new CompoundTagRandomizer(MAX_NUMBER_OF_NESTED_COMPOUND_TAGS,
                                                                        MAX_NUMBER_OF_TAGS_IN_COMPOUND_TAG).generate();

        assertTrue(testCompleteCompoundTag.equals(testCompleteCompoundTag));
    }

    @Test
    public void testCompoundTagFind1() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new IntTag("testIntTag3", 3),
                                                        new StringTag("testStringTag1", "test"),
                                                        new LongTag("testLongTag1", 6000),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag6", 6),
                                                                new IntTag("testIntTag7", 7),
                                                                new CompoundTag("testCompoundTag3",
                                                                        new IntTag("testIntTag8", 8),
                                                                        new ListTag("testListTag1",
                                                                                new IntTag(9),
                                                                                new IntTag(10)))),
                                                        new IntTag("testIntTag7", 7));


        IntTag targetTag = (IntTag) testCompoundTag.find(IntTag.class, tag -> ((IntTag) tag).getPayload() == 9);
        IntTag correctTag = new IntTag(9);

        assertTrue(targetTag.equals(correctTag));
    }

    @Test
    public void testCompoundTagFind2() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag1",
                                                    new CompoundTag("testCompoundTag2",
                                                            new IntTag("testIntTag1", 1),
                                                            new IntTag("testIntTag2", 2)),
                                                    new CompoundTag("testCompoundTag3",
                                                            new IntTag("testIntTag3", 3),
                                                            new IntTag("testIntTag4", 4),
                                                            new CompoundTag("testCompoundTag4",
                                                                    new IntTag("testIntTag5", 5),
                                                                    new IntTag("testIntTag6", 6)),
                                                            new CompoundTag("testCompoundTag5",
                                                                    new StringTag("testStringTag1", "test1"),
                                                                    new StringTag("testStringTag2", "test2"))));

        StringTag targetTag = (StringTag) testCompoundTag.find(StringTag.class, tag -> ((StringTag) tag).getPayload() == "test1");
        System.out.println(targetTag.toString());
    }

    @Test
    public void testCompoundTagFind3() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag1",
                                                        new ListTag("testListTag1",
                                                                new IntTag(1),
                                                                new IntTag(2)),
                                                        new ListTag("testListTag2",
                                                                new ListTag("testListTag3", (byte) 3),
                                                                new ListTag("testListTag4",
                                                                        new IntTag(7),
                                                                        new IntTag(8))));

        IntTag targetTag = (IntTag) testCompoundTag.find(IntTag.class, tag -> ((IntTag) tag).getPayload() == 8);
        System.out.println(targetTag.toString());
    }

    @Test
    public void testCompoundTagGetParent() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new IntTag("testIntTag3", 3),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag4", 4),
                                                                new IntTag("testIntTag5", 5)),
                                                        new IntTag("testIntTag6", 6));

        /*
        Tag targetTestIntTag1 = testCompoundTag.find(new IntTag("testIntTag1", 1));
        assertEquals(targetTestIntTag1.getParent().getName(), new CompoundTag("testCompoundTag").getName());

        Tag targetTestIntTag5 = testCompoundTag.find(new IntTag("testIntTag5", 5));
        assertEquals(targetTestIntTag5.getParent().getName(), new CompoundTag("testCompoundTag2").getName());
        */
    }

    @Test
    public void testCompoundTagEqualsBasic() {
        CompoundTag testCompoundTag1 = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new IntTag("testIntTag3", 3));

        CompoundTag testCompoundTag2 = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new IntTag("testIntTag2", 2),
                                                        new IntTag("testIntTag3", 3));

        assertTrue(testCompoundTag1.equals(testCompoundTag2));
    }

    @Test
    public void testCompoundTagEqualsNested() {
        CompoundTag testCompoundTag1 = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new CompoundTag("testCompoundTag2",
                                                            new IntTag("testIntTag2", 2)));

        CompoundTag testCompoundTag2 = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag2", 2)));

        assertTrue(testCompoundTag1.equals(testCompoundTag2));
    }

    @Test
    public void testCompoundTagIterator() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new StringTag("testStringTag1", "test123"));

        Iterator<Tag> it = testCompoundTag.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
    }

    @Test
    public void testCompoundTagIteratorNested() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag2", 2),
                                                                new CompoundTag("testCompoundTag3",
                                                                        new IntTag("testIntTag3", 3),
                                                                new CompoundTag("testCompoundTag4",
                                                                        new IntTag("testIntTag4", 4),
                                                                        new CompoundTag("testCompoundTag5",
                                                                                new IntTag("testIntTag5", 5))),
                                                                        new CompoundTag("testCompoundTag6",
                                                                                new IntTag("testIntTag6", 6),
                                                                                new IntTag("testIntTag7", 7)))),
                                                        new CompoundTag("testCompoundTag7",
                                                                new IntTag("testIntTag8", 8)));

        Iterator<Tag> it = testCompoundTag.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    @Test
    public void testCompoundTagIteratorListTag() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new StringTag("testStringTag1", "test123"),
                                                        new ListTag("testListTag",
                                                                new IntTag(2),
                                                                new IntTag(3)));

        Iterator<Tag> it = testCompoundTag.iterator();
        System.out.println(it.next());
        System.out.println(it.next());
    }

    @Test
    public void testCompoundTagToList() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new StringTag("testStringTag1", "test123"),
                                                        new ByteTag("testByteTag1", (byte) 1));

        List<Tag> testCompoundTagList = testCompoundTag.toList();
        System.out.println(testCompoundTagList);
    }

    @Test
    public void testCompoundTagToListNested() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag1", 1),
                                                        new StringTag("testStringTag1", "test123"),
                                                        new ByteTag("testByteTag1", (byte) 1),
                                                        new CompoundTag("testCompoundTag2",
                                                                new IntTag("testIntTag2", 2)),
                                                                new CompoundTag("testCompoundTag3",
                                                                        new IntTag("testIntTag3", 3),
                                                                        new StringTag("testIntTag4", "test1234")),
                                                                        new ListTag("testListTag1",
                                                                                new IntTag(1)));

        List<Tag> testCompoundTagList = testCompoundTag.toList();
        System.out.println(testCompoundTagList);
    }
}
