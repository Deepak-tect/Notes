public class Test {
    public static void main(String[] args) {
        Forest forest = new Forest();
        forest.plantTree("Oak", "Green", "Rough", 1, 1);
        forest.plantTree("Oak", "Green", "Rough", 2, 2);
        forest.plantTree("Pine", "Green", "Smooth", 3, 3);
        forest.display();

    }
}
