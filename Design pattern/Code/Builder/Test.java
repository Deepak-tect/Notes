package Builder;

public class Test {
    public static void main(String[] args) {
        User user1 = new User.Builder("1", "Deepak").setGender("Male").build();
        User user2 = new User.Builder("1", "Hims").setEmail("hims@iiitb.ac.in").setGender("Trans").build();

        System.out.println(user1);
        System.out.println(user2);
    }
}
