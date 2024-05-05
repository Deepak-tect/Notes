public class InfoHandle implements Logger {

    private Logger nextLogger;

    @Override
    public Logger setNextLogger(Logger nextLogger){
        if (this.nextLogger == null) {
            this.nextLogger = nextLogger;
        } else {
            this.nextLogger.setNextLogger(nextLogger);
        }
        return this;
    }

    @Override
    public void log(int level, String message) {
        if(level == 1){
            System.out.println("Info: "+ message);
        }
        else if(nextLogger != null){
            nextLogger.log(level, message);
        }
        else{
            System.out.println("Invalid level");
        }

    }
    
}
