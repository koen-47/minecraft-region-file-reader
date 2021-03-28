package util;

import nbt.tag.CompoundTag;
import nbt.tag.IntTag;
import nbt.tag.ListTag;
import nbt.tag.StringTag;
import org.junit.jupiter.api.Test;

// this is not really a test class, this is only really used to see if stuff is printed correctly when i try
// to convert a Tag to a String without having to call main :)

public class CompoundTagStringTest {

    @Test
    public void testBasicCompoundTagString() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                       new IntTag("testIntTag", 1));

        CompoundTagString testCompoundTagString = new CompoundTagString(testCompoundTag);
        System.out.println(testCompoundTagString.getString());
    }

    @Test
    public void testNestedCompoundTagString() {
        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 1),
                                                            new IntTag("testIntTagInner2", 2));

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 3),
                                                            new StringTag("testStringTagOuter2", "s1"),
                                                            testCompoundTagInner);

        CompoundTagString testCompoundTagString = new CompoundTagString(testCompoundTagOuter);
        System.out.println(testCompoundTagString.getString());

    }

    @Test
    public void testDoubleCompoundTagString() {
        CompoundTag testCompoundTagDoubleInner = new CompoundTag("testCompoundTagDoubleInner",
                                                                 new IntTag("testIntTagDoubleInner1", 0),
                                                                 new IntTag("testIntTagDoubleInner2", 1));

        CompoundTag testCompoundTagInner = new CompoundTag("testCompoundTagInner",
                                                            new IntTag("testIntTagInner1", 2),
                                                            new IntTag("testIntTagInner2", 3),
                                                            testCompoundTagDoubleInner);

        CompoundTag testCompoundTagOuter = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 4),
                                                            new IntTag("testIntTagOuter2", 5),
                                                            testCompoundTagInner);

        CompoundTagString compoundTagString = new CompoundTagString(testCompoundTagOuter);
        System.out.println(compoundTagString.getString());
    }
    
    @Test
    public void testRandomCompoundTagString() {
        final int MAX_NUMBER_OF_NESTED_COMPOUND_TAGS = 10;
        final int MAX_NUMBER_OF_TAGS_IN_COMPOUND_TAG = 10;
        CompoundTag testCompleteCompoundTag = new CompoundTagRandomizer(MAX_NUMBER_OF_NESTED_COMPOUND_TAGS,
                                                                        MAX_NUMBER_OF_TAGS_IN_COMPOUND_TAG).generate();

        System.out.println(new CompoundTagString(testCompleteCompoundTag).getString());
    }

    @Test
    public void testCompoundTagWithListTag() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                            new IntTag("testIntTag1", 1),
                                                            new CompoundTag("testCompoundTag2",
                                                                    new IntTag("testIntTag2", 2),
                                                                    new ListTag<IntTag>("testListTag",
                                                                            new IntTag(3),
                                                                            new IntTag(3))));

        System.out.println(new CompoundTagString(testCompoundTag).getString());
    }

}
