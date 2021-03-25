package nbt.parsing;

import mca.MCAFile;
import nbt.tag.CompoundTag;
import nbt.tag.EndTag;
import nbt.tag.IntTag;
import nbt.tag.Tag;

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
        String tagName = (tagID != 0) ? this.readTagName() : "";

        switch (tagID) {
            case 0:
                return new EndTag();

            case 3:
                return new IntTag(tagName, this.readIntTag().getPayload());

            case 10:
                return new CompoundTag(tagName, this.readCompoundTag().getPayload());
        }

        return null;
    }

    public Tag readUnnamedTag(int tagID) throws IOException {
        switch (tagID) {
            case 0:
                return new EndTag();

            case 3:
                return this.readIntTag();
        }

        return null;
    }

    private IntTag readIntTag() throws IOException {
        int tagValue = this.reader.readInt();
        return new IntTag(tagValue);
    }

    private String readTagName() throws IOException {
        short nameLength = (short) this.reader.readUnsignedShort();
        if (nameLength == 0)
            return "";

        byte[] nameBytes = new byte[nameLength];
        for (int i = 0; i < nameLength; i++) {
            nameBytes[i] = (byte) this.reader.read();
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

    private CompoundTag readCompoundTag() throws IOException {
        ArrayList<Tag> containedTags = new ArrayList<>();

        while (true) {
            Tag newTag = this.readNamedTag();
            if (newTag instanceof EndTag)
                break;

            containedTags.add(newTag);
        }

        return new CompoundTag(containedTags);
    }

}
