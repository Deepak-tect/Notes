public class Player{
    private State state;
    private String name;
    
    public Player(String name){
        this.name = name;
        this.state = new StopPlay(this);
    }
    

    public String getName() {
        return name;
    }


    void play(){
        state.startPlay();
    }

    void restart(){
        state.pausePlay();
    }

    void stop(){
        state.stopPlay();
    }

    void setState(State state){
        this.state = state;
    }

}