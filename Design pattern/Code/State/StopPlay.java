public class StopPlay implements State {
    private Player player;

    public StopPlay(Player player) {
        this.player = player;
    }

    @Override
    public void pausePlay() {
        System.out.println("Cannot pause. Player is stopped "+ player.getName());
    }

    @Override
    public void startPlay() {
        System.out.println("Starting the player "+ player.getName());
        player.setState(new StartPlay(player));
        
    }

    @Override
    public void stopPlay() {
        System.out.println("Already stopped "+ player.getName());
        
    }
    
}
