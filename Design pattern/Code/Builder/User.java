package Builder;

public class User {
    private String id;
    private String name;
    private String email;
    private String gender;

    private User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.gender = builder.gender;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    

    @Override
    public String toString() {
        return "Id: "+ this.id + "," + " Name: " + this.name + "," + " Email: " + this.email + "," + " Gender: " + this.gender;
    }


    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String gender;

        public Builder(String id , String name){
            this.name = name;
            this.id  = id;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setGender(String gender){
            this.gender = gender;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }

    
}