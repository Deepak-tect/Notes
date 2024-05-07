package javacode;

public class EmailObserver implements Observer {

    @Override
    public void update(String message) {
        System.out.println("Sending message via email :  " + message);
    }
    
}
