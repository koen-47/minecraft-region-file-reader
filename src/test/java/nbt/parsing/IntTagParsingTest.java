package nbt.parsing;

import nbt.tag.IntTag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class IntTagParsingTest {

    @Test
    public void testIntTag() throws IOException {
        byte[] testChunkData = new byte[] {3, 0, 4, 116, 101, 115, 116, 0, 0, 0, 10};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        IntTag testTag = (IntTag) nbtReader.readTag();
        IntTag correctTag = new IntTag("test", 10);

        assertTrue(testTag.equals(correctTag));
    }

    @Test
    public void testUnnamedIntTag() throws IOException {
        byte[] testChunkData = new byte[] {3, 0, 0, 0, 0, 0, 15};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        IntTag testTag = (IntTag) nbtReader.readTag();
        IntTag correctTag = new IntTag(15);

        assertTrue(testTag.equals(correctTag));
    }
}
