public class Main {
    public static void main(String[] args) {
        MCAReader reader = new MCAReader("r.0.-1.mca");
        MCAFile mcaFile = reader.readMCAFile();

        MCAFilePrinter printer = new MCAFilePrinter(mcaFile);
        printer.printChunkLocationTable();
        printer.printChunkTimestampTable();

        mcaFile.getChunkAtCoordinates(32);

        //System.out.println(4 * ((30 & 31) + (-3 & 31) * 32));
    }
}
