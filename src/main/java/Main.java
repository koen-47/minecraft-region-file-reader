import dat.DATFile;
import dat.DATReader;
import mca.MCAFile;
import mca.MCAFilePrinter;
import mca.MCAReader;
import nbt.parsing.NBTFileInputStream;
import nbt.tag.*;
import util.CompoundTagString;
import util.ListTagString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MCAReader mcaReader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = mcaReader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        //printer.printChunkLocationTable();
        //printer.printChunkTimestampTable();

        CompoundTag root = mcaFile.getChunk(6, 8);
        System.out.println(new CompoundTagString(root).getString());

        //StringTag signId = (StringTag) root.find(StringTag.class, tag -> ((StringTag) tag).getPayload().equals("minecraft:sign"));
        //System.out.println(signId.toString());

        //ArrayList<Tag> sections = root.findAll(StringTag.class, tag -> ((StringTag) tag).getPayload().equals("minecraft:sign"));
        //System.out.println(sections);

        CompoundTag section4 = (CompoundTag) (root.find(ByteTag.class, tag -> ((ByteTag) tag).getName().equals("Y") &&
                                                                              ((ByteTag) tag).getPayload().equals((byte) 4)))
                                                                              .getParent();

        LongArrayTag blockStatesSection4 = (LongArrayTag) section4.find(LongArrayTag.class, tag -> tag.getName().equals("BlockStates"));

        System.out.println(new CompoundTagString(section4).getString());
        System.out.println(Arrays.toString(blockStatesSection4.getPayload()));

        ListTag sections = (ListTag) root.find(ListTag.class, tag -> tag.getName().equals("Sections"));
        System.out.println(new ListTagString(sections).getString());
    }
}
