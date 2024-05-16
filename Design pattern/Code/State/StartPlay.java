public class StartPlay implements State{

    private Player player;

    public StartPlay(Player player){
        this.player = player;
    }

    @Override
    public void pausePlay() {
        System.out.println("player already playing " + player.getName());
        player.setState(new PausePlay(player));

    }

    @Override
    public void startPlay() {
        System.out.println("player start playing " + player.getName());
    }

    @Override
    public void stopPlay() {
        System.out.println("Stopping the player " + player.getName());
        player.setState(new StopPlay(player));
        
    }
    
}
