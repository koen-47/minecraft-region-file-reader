package nbt.tag;

import nbt.CompoundTag;
import nbt.EndTag;
import nbt.IntTag;
import nbt.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CompoundTagTest {

    @Test
    public void testCompoundTagConstructor1() {
        ArrayList<Tag> containedTags = new ArrayList<>();
        containedTags.add(new IntTag("testIntTag", 1));
        containedTags.add(new EndTag());
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", containedTags);

        byte[] correctCompoundTagBytes = new byte[] {10, 0, 15, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100, 84, 97, 103,
                                                        3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 1,
                                                     0};

        assertArrayEquals(testCompoundTag.toByteArray(), correctCompoundTagBytes);
    }

    @Test
    public void testCompoundTagConstructor2() {
        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag", new IntTag("testIntTag", 1), new EndTag());

        byte[] correctCompoundTagBytes = new byte[] {10, 0, 15, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100, 84, 97, 103,
                                                        3, 0, 10, 116, 101, 115, 116, 73, 110, 116, 84, 97, 103, 0, 0, 0, 1,
                                                     0};

        assertArrayEquals(testCompoundTag.toByteArray(), correctCompoundTagBytes);
    }
}
