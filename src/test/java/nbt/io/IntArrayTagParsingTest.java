package nbt.io;

import nbt.tag.IntArrayTag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntArrayTagParsingTest {

    @Test
    public void testBasicIntArrayTagParsing() throws IOException {
        byte[] testIntArrayTag = new IntArrayTag("testIntArrayTag", 1, 2, 3, 4, 5).toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testIntArrayTag);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        IntArrayTag testTag = (IntArrayTag) nbtReader.readNamedTag();
        IntArrayTag correctIntArray = new IntArrayTag("testIntArrayTag", 1, 2, 3, 4, 5);

        System.out.println(testTag.toString());
        System.out.println(Arrays.toString(testTag.toByteArray()));

        System.out.println(correctIntArray.toString());
        System.out.println(Arrays.toString(correctIntArray.toByteArray()));

        assertTrue(testTag.equals(correctIntArray));
    }
}
