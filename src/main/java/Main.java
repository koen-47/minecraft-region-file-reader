import dat.DATFile;
import dat.DATReader;
import mca.MCAFile;
import mca.io.MCAReader;
import mca.parsing.Chunk;
import nbt.tag.*;
import util.TagString;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MCAReader mcaReader = new MCAReader("src/main/java/r.0.1.mca");
        MCAFile mcaFile = mcaReader.readMCAFile();
        Chunk chunk00 = mcaFile.getChunk(0, 0);
        System.out.println(chunk00.toCompoundTag().toString());

        DATReader datReader = new DATReader("src/main/java/level.dat");
        DATFile datFile = datReader.readDATFile();

        System.out.println(datFile.toCompoundTag().toString());

        File testFile = new File("./src/main/java/r.0.1.mca");
        System.out.println(testFile.getAbsolutePath());

        CompoundTag testCompoundTag = new CompoundTag("testCompoundTag",
                                                        new IntTag("testIntTag", 1),
                                                        new LongTag("testLongTag", 2L),
                                                        new ListTag("testListTag",
                                                                new StringTag("test1"),
                                                                new StringTag("test2")));

        System.out.println(testCompoundTag.toString());

        TagString tagString = new TagString(testCompoundTag);
        System.out.println(tagString.getString());
    }

}
