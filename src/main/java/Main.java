public class Main {
    public static void main(String[] args) {
        MCAReader reader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = reader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        printer.printChunkLocationTable();
    }
}
