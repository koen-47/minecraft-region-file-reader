import mca.MCAFile;
import mca.MCAFilePrinter;
import mca.io.MCAReader;
import mca.parsing.Chunk;
import mca.parsing.Section;
import nbt.tag.*;
import util.CompoundTagString;
import util.ListTagString;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        URL regionDirURL = Main.class.getResource("/mca/2b2t");
        File regionDir = new File(regionDirURL.getFile());

        for (File regionFile : regionDir.listFiles()) {
            MCAReader mcaReader = new MCAReader(regionFile.getName());
            MCAFile mcaFile = mcaReader.readMCAFile();

            ArrayList<Tag> allSigns = findAllSigns(mcaFile);
            for (Tag tag : allSigns) {
                System.out.println(new CompoundTagString((CompoundTag) tag).getString());
            }
        }


    }

    public static ArrayList<Tag> findAllSigns(MCAFile mcaFile) throws IOException {
        ArrayList<Tag> allSigns = new ArrayList<>();
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                Chunk currentChunk = mcaFile.getChunk(x, y);
                ArrayList<Tag> signs = currentChunk.toNBTTag().findAllParents(ListTag.class,
                        tag -> ((ListTag) tag).getPayload().length > 0 && tag.getName().equals("Items"));
                allSigns.addAll(signs);
            }
        }

        return allSigns;
    }
}
