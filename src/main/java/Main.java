import mca.MCAFile;
import mca.io.MCAReader;
import mca.parsing.Chunk;
import util.CompoundTagString;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        MCAReader mcaReader = new MCAReader("src/main/java/r.0.1.mca");
        MCAFile mcaFile = mcaReader.readMCAFile();
        Chunk chunk00 = mcaFile.getChunk(0, 0);
        System.out.println(new CompoundTagString(chunk00.toCompoundTag()).getString());


        File testFile = new File("./src/main/java/r.0.1.mca");
        System.out.println(testFile.getAbsolutePath());
    }

}
