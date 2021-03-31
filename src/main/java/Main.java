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
        MCAReader reader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = reader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        //printer.printChunkLocationTable();
        //printer.printChunkTimestampTable();

        CompoundTag root = mcaFile.getChunk(6, 8);
        System.out.println(new CompoundTagString(root).getString());

        CompoundTag sections = (CompoundTag) root.find(new StringTag("id", "minecraft:sign")).getParent();
        System.out.println(new CompoundTagString(sections).getString());

        //ArrayList<Tag> testSigns = (ArrayList<Tag>) root.findAll(new StringTag("id", "minecraft:sign"));
        //System.out.println(testSigns);

        //LongArrayTag blockStates = (LongArrayTag) root.find("BlockStates");
        //System.out.println(Arrays.toString(blockStates.getPayload()));


    }
}
