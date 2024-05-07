package javacode;

public class PhoneObserver implements Observer {

    @Override
    public void update(String message) {
        System.out.println("Sending message via phone :  " + message);
    }
    
}
