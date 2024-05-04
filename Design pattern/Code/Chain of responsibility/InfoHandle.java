public class InfoHandle implements Logger {

    private Logger logger;

    @Override
    public Logger setNextLogger(Logger logger){
        if (this.logger == null) {
            this.logger = logger;
        } else {
            this.logger.setNextLogger(logger);
        }
        return this;
    }

    @Override
    public void log(int level, String message) {
        if(level == 1){
            System.out.println("Info: "+ message);
        }
        else if(logger != null){
            logger.log(level, message);
        }
        else{
            System.out.println("Invalid level");
        }

    }
    
}
