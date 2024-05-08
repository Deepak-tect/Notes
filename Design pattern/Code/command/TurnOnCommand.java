public class TurnOnCommand implements Command {
    Tv tv;
    TurnOnCommand(Tv tv){
        this.tv = tv;
    }
    @Override
    public void execute() {
        tv.turnOn();
    }
    
    
}
