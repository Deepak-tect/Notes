public class FilterCoffee implements Coffee {
    @Override
    public int cost() {
        return 7;
    }

    @Override
    public String description() {
        return "Filter coffee";
    }
}
