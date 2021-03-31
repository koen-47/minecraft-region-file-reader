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
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        MCAReader mcaReader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = mcaReader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        //printer.printChunkLocationTable();
        //printer.printChunkTimestampTable();

        CompoundTag root = mcaFile.getChunk(6, 8);
        //System.out.println(new CompoundTagString(root).getString());

        CompoundTag sections = (CompoundTag) root.find(new StringTag("id", "minecraft:sign")).getParent();
        //System.out.println(new CompoundTagString(sections).getString());

        //ArrayList<Tag> testSigns = (ArrayList<Tag>) root.findAll(new StringTag("id", "minecraft:sign"));
        //System.out.println(testSigns);

        //LongArrayTag blockStates = (LongArrayTag) root.find("BlockStates");
        //System.out.println(Arrays.toString(blockStates.getPayload()));

        DATReader datReader = new DATReader("f5929dd8-1bf8-4a22-a2f7-371e6f17da53.dat");
        DATFile datFile = datReader.readDATFile();

        CompoundTag levelRoot = datFile.getData();
        System.out.println(new CompoundTagString(levelRoot).getString());
    }
}
