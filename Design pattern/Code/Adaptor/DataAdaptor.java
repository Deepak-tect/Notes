public class DataAdaptor implements Processor {
    private Datatypes jsonDatatypes;
    private Datatypes xDatatypes;
    private DataProcessorTool dataProcessor;

    
    public DataAdaptor(Datatypes jsonDatatypes) {
        this.jsonDatatypes = jsonDatatypes;
    }

    @Override
    public void startProcess() {
        System.out.println("Data started converting");
        jsonDatatypes.description();
        System.out.println("data converted");
        xDatatypes = new XMLtypes();
        dataProcessor = new DataProcessorTool(xDatatypes);
        dataProcessor.startProcess();
    }
    
}
