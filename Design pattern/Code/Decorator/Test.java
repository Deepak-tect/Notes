public class Test {
    public static void main(String[] args) {
        Coffee simCoffee = new SimpleCoffee();
        System.out.println("Simple coffee price " + simCoffee.cost());
        Coffee advCoffee = new ExtraMilk(new ExtraCream(simCoffee));
        System.out.println(advCoffee.description());
        System.out.println("Cost of coffee "+advCoffee.cost());

    }
}
