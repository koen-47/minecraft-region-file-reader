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

    public Tag readTag() throws IOException {
        int tagID = this.reader.read();

        switch (tagID) {
            case 0:
                return new EndTag();

            case 3:
                return this.readIntTag();

            case 10:
                return this.readCompoundTag();
        }

        return null;
    }

    private IntTag readIntTag() throws IOException {
        String tagName = this.readTagName();
        int tagValue = this.reader.readInt();
        return (tagName != null) ? new IntTag(tagName, tagValue) : new IntTag(tagValue);
    }

    private String readTagName() throws IOException {
        short nameLength = (short) this.reader.readUnsignedShort();
        if (nameLength == 0)
            return null;

        byte[] nameBytes = new byte[nameLength];
        for (int i = 0; i < nameLength; i++) {
            nameBytes[i] = (byte) this.reader.read();
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

    private CompoundTag readCompoundTag() throws IOException {
        String tagName = this.readTagName();
        ArrayList<Tag> containedTags = new ArrayList<>();

        while (true) {
            Tag newTag = this.readTag();
            if (newTag instanceof EndTag)
                break;

            containedTags.add(newTag);

        }

        return new CompoundTag(tagName, containedTags);
    }

}