package nbt.tag;

import org.junit.jupiter.api.Test;
import util.CompoundTagRandomizer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompoundTagTest {

    @Test
    public void testCompoundTagConstructor1() {
        ArrayList<Tag> containedTags = new ArrayList<>();
        containedTags.add(new IntTag("testIntTag", 1));
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", containedTags);

        byte[] correctCompoundTagBytes = new byte[] {10, 0, 15, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100, 84, 97, 103,
                                                        3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 1,
                                                     0};

        assertArrayEquals(testCompoundTag.toByteArray(), correctCompoundTagBytes);
    }

    @Test
    public void testCompoundTagConstructor2() {
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
}
