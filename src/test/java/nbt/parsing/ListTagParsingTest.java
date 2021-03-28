package nbt.parsing;

import nbt.tag.CompoundTag;
import nbt.tag.IntTag;
import nbt.tag.ListTag;
import nbt.tag.Tag;
import org.junit.jupiter.api.Test;
import util.ListTagString;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ListTagParsingTest {

    @Test
    public void testBasicListTag1() throws IOException {
        ListTag<Tag> correctListTagIntTag = new ListTag<Tag>("testListTag",
                                                            new IntTag(1),
                                                            new IntTag(2),
                                                            new IntTag(3));

        byte[] testListTagIntTagByteArray = correctListTagIntTag.toByteArray();

        ByteArrayInputStream testByteStream = new ByteArrayInputStream(testListTagIntTagByteArray);
        NBTFileInputStream nbtReader = new NBTFileInputStream(testByteStream);
        ListTag testListTagIntTag = (ListTag) nbtReader.readNamedTag();

        assertArrayEquals(testListTagIntTag.toByteArray(), correctListTagIntTag.toByteArray());
    }

}
