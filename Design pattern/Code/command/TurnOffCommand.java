public class TurnOffCommand implements Command {
    Tv tv;
    TurnOffCommand(Tv tv){
        this.tv = tv;
    }
    @Override
    public void execute() {
        tv.turnOFF();
    }
    
}
