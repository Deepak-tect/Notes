package javacode;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> l = new ArrayList<>();

    public void subscribe(Observer observer){
        l.add(observer);
    }

    public void unSubscriber(Observer observer){
        l.remove(observer);
    }

    public void notify(String message){
        for(Observer observer : l){
            observer.update(message);
        }
    }
}
