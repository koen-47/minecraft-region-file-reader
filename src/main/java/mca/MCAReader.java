package mca;

import java.io.*;
import java.net.URL;

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
        byte[] locationBytes = this.readChunkLocations();
        ChunkLocationTable chunkLocationTable = new ChunkLocationTable(locationBytes);

        byte[] timestampBytes = this.readChunkTimestamps();
        ChunkTimestampTable chunkTimestampTable = new ChunkTimestampTable(timestampBytes);

        return new MCAFile(this.fileName, chunkLocationTable, chunkTimestampTable);
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

    private byte[] readChunkTimestamps() {
        byte[] chunkTimestamps = new byte[4096];

        try {
            this.reader.read(chunkTimestamps);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chunkTimestamps;
    }
}
