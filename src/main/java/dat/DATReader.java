package dat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DATReader {
    private URL fileName;
    private InputStream reader;

    public DATReader(String fileName) {
        try {
            this.fileName = this.getClass().getResource("/dat/" + fileName);
            this.reader = new FileInputStream(this.fileName.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DATFile readDATFile() throws IOException {
        byte[] datFileBytes = this.reader.readAllBytes();
        return new DATFile(this.fileName, datFileBytes);
    }
}
