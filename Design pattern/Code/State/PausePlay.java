public class PausePlay implements State {

    private Player player;
    
    public PausePlay(Player player) {
        this.player = player;
    }

    @Override
    public void startPlay() {
        System.out.println("Resuming the player " + player.getName());
        player.setState(new StartPlay(player));
    }

    @Override
    public void pausePlay() {
        System.out.println("Already paused " + player.getName());
    }

    @Override
    public void stopPlay() {
        System.out.println("Stopping the player from pause " + player.getName());
        player.setState(new StopPlay(player));
    }
    
}
