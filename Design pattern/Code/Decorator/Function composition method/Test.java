public class Test {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String input = "hello";
        TextProcessor updatedText = new TextProcessor(
            i-> i.toUpperCase(),
            i -> new StringBuilder(i).reverse().toString()
        );

        System.out.println(updatedText.process(input));
    }
}
