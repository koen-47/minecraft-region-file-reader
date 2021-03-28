package nbt.tag;

import org.junit.jupiter.api.Test;
import util.ListTagString;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
