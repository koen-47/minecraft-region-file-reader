package dat;

import nbt.io.NBTFileInputStream;
import nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Class that contains all the methods used to navigate the data related to the .dat file.
 * @author Killerkoen
 */
public class DATFile {
    /**
     * URL of the file.
     */
    private URL fileName;

    /**
     * Arrays of bytes containing the data of the .dat file.
     */
    private byte[] datFileBytes;

    /**
     * Constructor for the DATFile class.
     * @param fileName - the URL of the file
     * @param datFileBytes - arrays of bytes containing the data of the .dat file
     * @throws IOException - exception for when an error occurs during IO operations
     */
    public DATFile(URL fileName, byte[] datFileBytes) throws IOException {
        this.fileName = fileName;
        this.datFileBytes = this.decompress(datFileBytes);
    }

    /**
     * Decompresses the compressed byte array using GZIP decompression.
     * @param compressedDatFileBytes - array of uncompressed bytes
     * @return Uncompressed array of bytes
     * @throws IOException - exception for when an error occurs during IO operations
     */
    private byte[] decompress(byte[] compressedDatFileBytes) throws IOException {
        ByteArrayInputStream compressedDataStream = new ByteArrayInputStream(compressedDatFileBytes);
        GZIPInputStream decompressor = new GZIPInputStream(compressedDataStream);
        return decompressor.readAllBytes();
    }

    /**
     * Get the root compound tag for this .dat file.
     * @return Root compound tag for this.dat file
     * @throws IOException - exception for when an error occurs during IO operations
     */
    public CompoundTag getData() throws IOException {
        ByteArrayInputStream chunkData = new ByteArrayInputStream(this.datFileBytes);
        NBTFileInputStream nbtParser = new NBTFileInputStream(chunkData);
        return (CompoundTag) nbtParser.readNamedTag();
    }
}
