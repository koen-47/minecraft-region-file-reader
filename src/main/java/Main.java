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
        System.out.println(new CompoundTagString(root).getString());

        StringTag signId = (StringTag) root.find(StringTag.class, tag -> ((StringTag) tag).getPayload() == "minecraft:sign");
        System.out.println(signId.toString());
    }
}
