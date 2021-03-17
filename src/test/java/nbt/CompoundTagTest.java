package nbt;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CompoundTagTest {

    @Test
    public void testBasicCompoundTag() throws IOException {
        byte[] testChunkData = new byte[] {10, 0, 12, 116, 101, 115, 116, 67, 111, 109, 112, 111, 117, 110, 100,
                                                3, 0, 7, 116, 101, 115, 116, 73, 110, 116, 0, 0, 0, 10,
                                            0};
        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testChunkData);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);

        CompoundTag testTag = (CompoundTag) nbtReader.readTag();
        System.out.println(testTag.toString());
    }
}
