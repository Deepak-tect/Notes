public class DataProcessorTool implements Processor {
    private Datatypes xmlDatatypes;
    public DataProcessorTool(Datatypes xmDatatypes){
        this.xmlDatatypes = xmDatatypes;
    }
    @Override
    public void startProcess() {
        System.out.println("Data processing stated ");
        xmlDatatypes.description();
    }


}
