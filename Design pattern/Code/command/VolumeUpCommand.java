public class VolumeUpCommand implements Command {
    Tv tv;
    VolumeUpCommand(Tv tv){
        this.tv = tv;
    }
    @Override
    public void execute() {
        tv.volumeUP();
    }
}
