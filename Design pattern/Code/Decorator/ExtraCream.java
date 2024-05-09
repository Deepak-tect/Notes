public class ExtraCream extends DecoratorCoffee {

    ExtraCream(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int cost() {
        return super.cost() + 20;
    }

    @Override
    public String description() {
        return super.description() + " with extra cream";
    }
    
}
