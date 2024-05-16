public class Test {
    public static void main(String[] args) {
        Player player1 = new Player("abc");
        Player player2 = new Player("xyz");
        player1.play();
        player2.play();
        player1.restart();
        player2.stop();
    }
}
