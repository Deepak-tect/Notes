
public interface Logger {
    public Logger setNextLogger(Logger logger);
    public void log(int level , String message);
} 
