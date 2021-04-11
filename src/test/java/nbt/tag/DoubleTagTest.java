package nbt.tag;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DoubleTagTest {

    @Test
    public void testDoubleTagToByteArray() {
        DoubleTag testDoubleTag = new DoubleTag("testDoubleTag", -1.10101);
        System.out.println(Arrays.toString(testDoubleTag.toByteArray()));

        DoubleTag testDoubleTagPayload = new DoubleTag(-1.10101);
        System.out.println(Arrays.toString(testDoubleTagPayload.toByteArray()));
    }

}
