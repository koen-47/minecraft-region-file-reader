public class Main {
    public static void main(String[] args) {
        MCAReader reader = new MCAReader("r.0.0.mca");
        MCAFile mcaFile = reader.readMCAFile();
        System.out.println(mcaFile.toString());
    }
}
