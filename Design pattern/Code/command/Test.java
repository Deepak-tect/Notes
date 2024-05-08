public class Test {
    public static void main(String[] args) {
        Remote remote = new Remote();
        remote.setCommand(new TurnOnCommand(new Tv()));
        remote.pressButton();
        remote.setCommand(new VolumeUpCommand(new Tv()));
        remote.pressButton();
        remote.undo();

    }
}
