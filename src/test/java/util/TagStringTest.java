package util;

import nbt.tag.CompoundTag;
import nbt.tag.IntTag;
import nbt.tag.ListTag;
import nbt.tag.StringTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagStringTest {
    @Test
    public void testTagStringBasic() {
        IntTag testIntTag = new IntTag("testIntTag", 1);
        System.out.println(new TagString(testIntTag).getString());
        assertEquals(new TagString(testIntTag).getString(), "TAG_Int('testIntTag'): 1\n");
    }

    @Test
    public void testTagStringCompoundTag() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag", 1));

        System.out.println(new TagString(testCompoundTag).getString());
    }

    @Test
    public void testTagStringNestedCompoundTag() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTagOuter",
                                                            new IntTag("testIntTagOuter1", 3),
                                                            new StringTag("testStringTagOuter2", "s1"),
                                                            new CompoundTag("testCompoundTagInner",
                                                                    new IntTag("testIntTagInner1", 1),
                                                                    new IntTag("testIntTagInner2", 2)));

        System.out.println(new TagString(testCompoundTag).getString());
    }

    @Test
    public void testTagStringNestedCompoundTag2() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTagOuter",
                                                        new IntTag("testIntTagOuter1", 3),
                                                        new StringTag("testStringTagOuter2", "s1"),
                                                        new CompoundTag("testCompoundTagInner1",
                                                                new IntTag("testIntTagInner1", 1),
                                                                new IntTag("testIntTagInner2", 2)),
                                                        new CompoundTag("testCompoundTagInner2",
                                                                new IntTag("testIntTagInner1", 1),
                                                                new IntTag("testIntTagInner2", 2)));

        System.out.println(new TagString(testCompoundTag).getString());
    }

    @Test
    public void testTagStringListTag() {
        ListTag testListTag = new ListTag("testCompoundTagOuter",
                                                new IntTag(1),
                                                new IntTag(2),
                                                new IntTag(3));


        System.out.println(new TagString(testListTag).getString());
    }
 }
