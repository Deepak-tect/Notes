public class Test {
    public static void main(String[] args) {
        JSONtype jsoNtype = new JSONtype();
        Processor dataAdaptor = new DataAdaptor(jsoNtype);
        dataAdaptor.startProcess();
    }
}
