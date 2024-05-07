package javacode;

public class Test {
    
    public static void main(String[] args) {
        Observer emailObserver = new EmailObserver();
        Observer phObserver = new PhoneObserver();
        Subject newsPublisher = new Subject();
        newsPublisher.subscribe(phObserver);
        newsPublisher.subscribe(emailObserver);
        newsPublisher.notify("Kese ho");
    }
}
