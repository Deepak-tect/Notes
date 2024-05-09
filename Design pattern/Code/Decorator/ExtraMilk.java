public class ExtraMilk extends DecoratorCoffee {

    ExtraMilk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return super.cost() + 10;
    }

    @Override
    public String description() {
        return super.description() + " with extra milk";
    }
    
}
