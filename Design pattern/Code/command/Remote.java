import java.util.*;
public class Remote {
    private Command command;
    private Stack<Command> history ;

    Remote(){
        history = new Stack<>();
    }

    public void setCommand(Command command){
        this.command = command;
    }

    public void pressButton(){
        history.push(command);
        command.execute();
    }

    public void undo(){
        if(!history.empty()){
            history.pop();
            Command prevCommand = history.peek(); 
            prevCommand.execute();   
        }
    }
}
