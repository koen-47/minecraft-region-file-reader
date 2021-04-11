package nbt.io;

import mca.MCAFile;
import nbt.tag.ByteArrayTag;
import nbt.tag.ByteTag;
import nbt.tag.CompoundTag;
import nbt.tag.DoubleTag;
import nbt.tag.EndTag;
import nbt.tag.FloatTag;
import nbt.tag.IntArrayTag;
import nbt.tag.IntTag;
import nbt.tag.ListTag;
import nbt.tag.LongArrayTag;
import nbt.tag.LongTag;
import nbt.tag.ShortTag;
import nbt.tag.StringTag;
import nbt.tag.Tag;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A file input stream class that can be used to read and convert bytes to tags according to the specified protocols
 * given by the NBT format.
 * @author Killerkoen
 */
public class NBTFileInputStream {
    /**
     * The .mca file to read from.
     */
    private MCAFile mcaFile;

    /**
     * The data input stream that is used to read an array of bytes.
     */
    private DataInputStream reader;

    /**
     * Constructs a newly allocated NBTFileInputStream object that reads from the specified ByteArrayInputStream
     * parameter.
     * @param data an instance of a ByteArrayInputStream object that is to be read from
     */
    public NBTFileInputStream(ByteArrayInputStream data) {
        this.reader = new DataInputStream(data);
    }

    /**
     * Reads and converts bytes into tags that are guaranteed to contain a name and a payload.
     * @return An instance of a Tag object that is guaranteed to contain a name and a payload value
     * @throws IOException exception for when an error occurs during IO operations
     */
    public Tag readNamedTag() throws IOException {
        int tagID = this.reader.read();
        String tagName = (tagID != 0) ? this.readStringTagPayload() : "";

        switch (tagID) {
            case 0:
                return new EndTag();

            case 1:
                return new ByteTag(tagName, this.readByteTagPayload());

            case 2:
                return new ShortTag(tagName, this.readShortTagPayload());

            case 3:
                return new IntTag(tagName, this.readIntTagPayload());

            case 4:
                return new LongTag(tagName, this.readLongTagPayload());

            case 5:
                return new FloatTag(tagName, this.readFloatTagPayload());

            case 6:
                return new DoubleTag(tagName, this.readDoubleTagPayload());

            case 7:
                return new ByteArrayTag(tagName, this.readByteArrayTagPayload());

            case 8:
                return new StringTag(tagName, this.readStringTagPayload());

            case 9:
                byte containedTagID = (byte) this.reader.read();
                return new ListTag<>(tagName, containedTagID, this.readListTagPayload(containedTagID));

            case 10:
                return new CompoundTag(tagName, this.readCompoundTagPayload());

            case 11:
                return new IntArrayTag(tagName, this.readIntArrayTagPayload());

            case 12:
                return new LongArrayTag(tagName, this.readLongArrayTagPayload());
        }

        return null;
    }

    /**
     * Reads and converts bytes into tags that contains only a payload corresponding to the tag type.
     * @param tagID the tag id that is used to choose which type of tag is to be read.
     * @return An instance of a Tag object with the corresponding payload.
     * @throws IOException exception for when an error occurs during IO operations
     */
    public Tag<?> readUnnamedTag(int tagID) throws IOException {
        switch (tagID) {
            case 0:
                return new EndTag();

            case 1:
                return new ByteTag(this.readByteTagPayload());

            case 2:
                return new ShortTag(this.readShortTagPayload());

            case 3:
                return new IntTag(this.readIntTagPayload());

            case 4:
                return new LongTag(this.readLongTagPayload());

            case 5:
                return new FloatTag(this.readFloatTagPayload());

            case 6:
                return new DoubleTag(this.readDoubleTagPayload());

            case 7:
                return new ByteArrayTag(this.readByteArrayTagPayload());

            case 8:
                return new StringTag(this.readStringTagPayload());

            case 9:
                byte containedTagID = (byte) this.reader.read();
                return new ListTag<>(containedTagID, this.readListTagPayload(containedTagID));

            case 10:
                return new CompoundTag(this.readCompoundTagPayload());

            case 11:
                return new IntArrayTag(this.readIntArrayTagPayload());

            case 12:
                return new LongArrayTag(this.readLongArrayTagPayload());
        }

        return null;
    }

    /**
     * Reads a single byte from the data input stream.
     * @return A single byte value
     * @throws IOException exception for when an error occurs during IO operations
     */
    private byte readByteTagPayload() throws IOException {
        return (byte) this.reader.read();
    }

    /**
     * Reads a single short from the data input stream.
     * @return A single short value
     * @throws IOException exception for when an error occurs during IO operations
     */
    private short readShortTagPayload() throws IOException {
        return this.reader.readShort();
    }

    /**
     * Reads a single int from the data input stream.
     * @return A single int value
     * @throws IOException exception for when an error occurs during IO operations
     */
    private int readIntTagPayload() throws IOException {
        return this.reader.readInt();
    }

    /**
     * Reads a single long from the data input stream.
     * @return A single long value
     * @throws IOException exception for when an error occurs during IO operations
     */
    private long readLongTagPayload() throws IOException {
        return this.reader.readLong();
    }

    /**
     * Reads a single float from the data input stream that is used as a payload for an instance of a FloatTag object.
     * Float values follow the single-precision floating-point format.
     * @return A single int float.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private float readFloatTagPayload() throws IOException {
        return this.reader.readFloat();
    }

    /**
     * Reads a single double from the data input stream that is used as a payload for an instance of a DoubleTag object.
     * Double values follow the double-precision floating-point format.
     * @return A single double.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private double readDoubleTagPayload() throws IOException {
        return this.reader.readDouble();
    }

    /**
     * Reads an array of bytes from the data input stream that is used as a payload for an instance of a ByteArrayTag
     * object.
     * @return An array of bytes.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private byte[] readByteArrayTagPayload() throws IOException {
        int numberOfInts = this.readIntTagPayload();
        byte[] payload = new byte[numberOfInts];

        for (int i = 0; i < numberOfInts; i++) {
            payload[i] = this.readByteTagPayload();
        }

        return payload;
    }

    /**
     * Reads a string from the data input stream that is used as a payload for an instance of a StringTag object.
     * @return A string
     * @throws IOException exception for when an error occurs during IO operations
     */
    private String readStringTagPayload() throws IOException {
        short nameLength = (short) this.reader.readUnsignedShort();
        if (nameLength == 0) {
            return "";
        }

        byte[] nameBytes = new byte[nameLength];
        for (int i = 0; i < nameLength; i++) {
            nameBytes[i] = (byte) this.reader.read();
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

    /**
     * Reads a fixed number of tag payloads based on a tag specified by the tag id parameter from the data input stream.
     * These payloads are used to create a list of tag payloads.
     * @param tagID the tag id that is used to define the type of tags contained in the ListTag.
     * @return An array of tags payloads that is used as a payload for an instance of a ListTag object.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private Tag[] readListTagPayload(int tagID) throws IOException {
        int numberOfTags = this.readIntTagPayload();
        Tag[] containedTags = new Tag[numberOfTags];

        for (int i = 0; i < numberOfTags; i++) {
            containedTags[i] = this.readUnnamedTag(tagID);
        }

        return containedTags;
    }

    /**
     * Reads an unspecified number of tags that are guaranteed to contain a name and a payload value from the data input
     * stream. This method keeps reading until reaching an instance of the EndTag object that signals the end of the
     * compound tag.
     * @return An instance of an ArrayList object that contains an unspecified number of named tags and their payloads.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private ArrayList<Tag> readCompoundTagPayload() throws IOException {
        ArrayList<Tag> containedTags = new ArrayList<>();

        while (true) {
            Tag newTag = this.readNamedTag();
            if (newTag instanceof EndTag) {
                break;
            }

            containedTags.add(newTag);
        }

        return containedTags;
    }

    /**
     * Reads an array of ints from the data input stream that is used as a payload for an instance of a IntArrayTag
     * object.
     * @return An array of ints.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private int[] readIntArrayTagPayload() throws IOException {
        int numberOfInts = this.readIntTagPayload();
        int[] payload = new int[numberOfInts];

        for (int i = 0; i < numberOfInts; i++) {
            payload[i] = this.readIntTagPayload();
        }

        return payload;
    }

    /**
     * Reads an array of longs from the data input stream that is used as a payload for an instance of a LongArrayTag
     * object.
     * @return An array of longs.
     * @throws IOException exception for when an error occurs during IO operations
     */
    private long[] readLongArrayTagPayload() throws IOException {
        int numberOfInts = this.readIntTagPayload();
        long[] payload = new long[numberOfInts];

        for (int i = 0; i < numberOfInts; i++) {
            payload[i] = this.readLongTagPayload();
        }

        return payload;
    }

}
