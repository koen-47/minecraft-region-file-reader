package nbt;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CompoundTagTest {

    @Test
    public void testBasicCompoundTag() throws IOException {
        byte[] testChunkData = new byte[] {10, 0, 2, 116, 116, 3, 0, 4, 116, 101, 115, 116, 0, 0, 0, 10, 10, 0, 2, 116, 116, 3, 0, 4, 116, 101, 115, 116, 0, 0, 0, 10, 0, 0};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readTag();
        NBTTagPrinter.printCompoundTag(testTag);
    }
}
