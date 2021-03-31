package dat;

import nbt.parsing.NBTFileInputStream;
import nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class DATFile {
    private URL fileName;
    private byte[] datFileBytes;

    public DATFile(URL fileName, byte[] datFileBytes) throws IOException {
        this.fileName = fileName;
        this.datFileBytes = this.decompress(datFileBytes);
    }

    private byte[] decompress(byte[] uncompressedDatFileBytes) throws IOException {
        ByteArrayInputStream compressedDataStream = new ByteArrayInputStream(uncompressedDatFileBytes);
        GZIPInputStream decompressor = new GZIPInputStream(compressedDataStream);

        try {
            return decompressor.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] getRawByteData() {
        return this.datFileBytes;
    }

    public CompoundTag getData() throws IOException {
        ByteArrayInputStream chunkData = new ByteArrayInputStream(this.datFileBytes);
        NBTFileInputStream nbtParser = new NBTFileInputStream(chunkData);
        return (CompoundTag) nbtParser.readNamedTag();
    }
}
