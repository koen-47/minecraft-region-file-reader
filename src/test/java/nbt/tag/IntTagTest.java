package nbt.tag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IntTagTest {

    @Test
    public void testIntTagToByteArray() {
        byte[] testByteArray = new IntTag("testIntTag", 5).toByteArray();
        byte[] correctByteArray = new byte[] {3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 5};

        assertArrayEquals(testByteArray, correctByteArray);
    }

}
