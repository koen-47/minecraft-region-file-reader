import java.util.Arrays;

public class ChunkLocationTable {
    private byte[] table;

    public ChunkLocationTable(byte[] table) {
        this.table = table;
    }

    public String toString() {
        return Arrays.toString(this.table);
    }
}
