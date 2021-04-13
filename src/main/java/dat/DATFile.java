package dat;

import nbt.io.NBTFileInputStream;
import nbt.tag.CompoundTag;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * An {@code DATFile} contains all the methods that can be used to navigate all the data related to a .dat file.
 * @author Killerkoen
 */
public class DATFile {
    /**
     * An instance of a File object that contains the .dat file
     */
    private File file;

    /**
     * Arrays of bytes containing the data of the .dat file.
     */
    private byte[] datFileBytes;

    /**
     * Constructs an instance of an MCAFile object with the specified File object and array of .dat file bytes.
     * @param file - the instance of the File object that contains the .dat file
     * @param datFileBytes - arrays of bytes containing the data of the .dat file
     * @throws IOException - exception for when an error occurs during IO operations
     */
    public DATFile(File file, byte[] datFileBytes) throws IOException {
        this.file = file;
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
    public CompoundTag toCompoundTag() throws IOException {
        ByteArrayInputStream chunkData = new ByteArrayInputStream(this.datFileBytes);
        NBTFileInputStream nbtParser = new NBTFileInputStream(chunkData);
        return (CompoundTag) nbtParser.readNamedTag();
    }
}
