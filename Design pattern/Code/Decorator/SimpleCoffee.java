public class SimpleCoffee implements Coffee {

    @Override
    public int cost() {
        return 5;
    }

    @Override
    public String description() {
        return "Simple coffee";
    }
    
}
