package nbt;

import mca.MCAFile;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
            case 3:
                return this.readIntTag();
        }

        return null;
    }

    public IntTag readIntTag() throws IOException {
        String tagName = this.readTagName();
        int tagValue = this.reader.readInt();
        return new IntTag(tagName, tagValue);
    }

    public String readTagName() throws IOException {
        short nameLength = (short) this.reader.readUnsignedShort();
        byte[] nameBytes = new byte[nameLength];
        for (int i = 0; i < nameLength; i++) {
            nameBytes[i] = (byte) this.reader.read();
        }

        return new String(nameBytes, StandardCharsets.UTF_8);
    }

}
