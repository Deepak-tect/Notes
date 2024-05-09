public abstract class DecoratorCoffee implements Coffee{

    private Coffee coffee;

    DecoratorCoffee(Coffee coffee){
        this.coffee = coffee;
    }

    @Override
    public int cost() {
        return coffee.cost();
    }

    @Override
    public String description() {
        return coffee.description();
    }
    
}
