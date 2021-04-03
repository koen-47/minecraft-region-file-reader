import mca.MCAFile;
import mca.MCAFilePrinter;
import mca.io.MCAReader;
import mca.parsing.Chunk;
import mca.parsing.Section;
import nbt.tag.*;
import util.CompoundTagString;
import util.ListTagString;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        MCAReader mcaReader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = mcaReader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        //printer.printChunkLocationTable();
        //printer.printChunkTimestampTable();

        Chunk chunk = mcaFile.getChunk(6, 8);
        Section section4 = chunk.getSectionNumber(4);
        section4.getBlockStateAtIndex(1);

        //StringTag signId = (StringTag) root.find(StringTag.class, tag -> ((StringTag) tag).getPayload().equals("minecraft:sign"));
        //System.out.println(signId.toString());

        //ArrayList<Tag> sections = root.findAll(StringTag.class, tag -> ((StringTag) tag).getPayload().equals("minecraft:sign"));
        //System.out.println(sections);

    }
}
