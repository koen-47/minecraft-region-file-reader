package nbt.parsing;

import mca.MCAFile;
import nbt.tag.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NBTFileInputStream {
    private DataInputStream reader;

    public NBTFileInputStream(MCAFile mcaFile) {
        try {
            InputStream inputStream = new FileInputStream(mcaFile.getFileName().getFile());
            this.reader = new DataInputStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // for test classes
    public NBTFileInputStream(ByteArrayInputStream data) {
        this.reader = new DataInputStream(data);
    }

    public Tag readNamedTag() throws IOException {
        int tagID = this.reader.read();
        String tagName = (tagID != 0) ? this.readStringTagPayload() : "";

        switch (tagID) {
            case 0:
                return new EndTag();

            case 3:
                return new IntTag(tagName, this.readIntTagPayload());

            case 8:
                return new StringTag(tagName, this.readStringTagPayload());

            case 9:
                byte containedTagID = (byte) this.reader.read();
                return new ListTag<>(tagName, containedTagID, this.readListTagPayload(containedTagID));

            case 10:
                return new CompoundTag(tagName, this.readCompoundTagPayload());

            case 11:
                return new IntArrayTag(tagName, this.readIntArrayTagPayload());
        }

        return null;
    }

    public Tag<?> readUnnamedTag(int tagID) throws IOException {
        switch (tagID) {
            case 0:
                return new EndTag();

            case 3:
                return new IntTag(this.readIntTagPayload());

            case 8:
                return new StringTag(this.readStringTagPayload());

            case 9:
                byte containedTagID = (byte) this.reader.read();
                return new ListTag(containedTagID, this.readListTagPayload(containedTagID));

            case 10:
                return new CompoundTag(this.readCompoundTagPayload());

            case 11:
                return new IntArrayTag(this.readIntArrayTagPayload());
        }

        return null;
    }

    private int readIntTagPayload() throws IOException {
        return this.reader.readInt();
    }

    private String readStringTagPayload() throws IOException {
        short nameLength = (short) this.reader.readUnsignedShort();
        if (nameLength == 0)
            return "";

        byte[] nameBytes = new byte[nameLength];
        for (int i = 0; i < nameLength; i++) {
            nameBytes[i] = (byte) this.reader.read();
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

    private Tag[] readListTagPayload(int tagID) throws IOException {
        int numberOfTags = this.readIntTagPayload();
        Tag[] containedTags = new Tag[numberOfTags];

        for (int i = 0; i < numberOfTags; i++)
            containedTags[i] = this.readUnnamedTag(tagID);

        return containedTags;
    }

    private ArrayList<Tag> readCompoundTagPayload() throws IOException {
        ArrayList<Tag> containedTags = new ArrayList<>();

        while (true) {
            Tag newTag = this.readNamedTag();
            if (newTag instanceof EndTag)
                break;

            containedTags.add(newTag);
        }

        return containedTags;
    }

    private int[] readIntArrayTagPayload() throws IOException {
        int numberOfInts = this.readIntTagPayload();
        int[] payload = new int[numberOfInts];

        for (int i = 0; i < numberOfInts; i++)
            payload[i] = this.readIntTagPayload();

        return payload;
    }

}
