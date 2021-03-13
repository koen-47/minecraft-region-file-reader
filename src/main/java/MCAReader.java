import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class MCAReader {
    private URL fileName;
    private InputStream reader;

    public MCAReader(String fileName) {
        try {
            this.fileName = this.getClass().getResource("/mca/" + fileName);
            this.reader = new FileInputStream(this.fileName.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MCAFile readMCAFile() {
        byte[] locations = this.readChunkLocations();
        ChunkLocationTable chunkLocationTable = new ChunkLocationTable(locations);
        return new MCAFile(chunkLocationTable);
    }

    private byte[] readChunkLocations() {
        byte[] chunkLocations = new byte[4096];

        try {
            this.reader.read(chunkLocations);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunkLocations;
    }
}
