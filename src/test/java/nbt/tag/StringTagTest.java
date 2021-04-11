package nbt.tag;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StringTagTest {

    @Test
    public void testStringTagToByteArray() {
        StringTag testStringTag = new StringTag("testStringTag", "test");
        byte[] correctStringTag = new byte[] { 8, 0, 13, 116, 101, 115, 116, 83, 116, 114, 105, 110, 103, 84, 97, 103,
                                               0, 4, 116, 101, 115, 116 };

        assertArrayEquals(testStringTag.toByteArray(), correctStringTag);
    }

}
