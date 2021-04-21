package nbt.tag;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class IntArrayTagTest {

    @Test
    public void testIntArrayTagToByteArray() {
        IntArrayTag testIntArrayTag = new IntArrayTag("testIntArrayTag", 1, 2, 3, 4, 5);
        byte[] correctIntArrayTagByteArray = new byte[] {11, 0, 15, 116, 101, 115, 116, 73, 110, 116, 65, 114, 114, 97, 121, 84, 97, 103, 0, 0, 0, 5,
                                                            0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0, 0, 5};

        assertArrayEquals(testIntArrayTag.toByteArray(), correctIntArrayTagByteArray);
    }

    @Test
    public void testIntArrayTagEquals() {
        IntArrayTag testIntArrayTag1 = new IntArrayTag("testIntArrayTag", 1, 2, 3, 4, 5);
        IntArrayTag testIntArrayTag2 = new IntArrayTag("testIntArrayTag", 1, 2, 3, 4, 5);
        assertTrue(testIntArrayTag1.equals(testIntArrayTag2));
    }
}
