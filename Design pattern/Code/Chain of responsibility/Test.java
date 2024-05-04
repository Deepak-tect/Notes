public class Test {
    
    public static void main(String[] args) {
        Logger obj = new InfoHandle()
                            .setNextLogger(new ErrorHandle())
                            .setNextLogger(new Debug());
        
        obj.log(3, "this is debug message");
        obj.log(1, "this is info message");
        obj.log(2, "this is error message");
        
    }
}
