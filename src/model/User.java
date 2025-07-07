package model;

import java.util.HashSet;
import java.util.Set;

public abstract class User extends ObjectExtent {

    private static final Set<String> usedEmails = new HashSet<>();

    private String name;
    private String email;

    public User(String name, String email) {
        try{
            if(!usedEmails.add(email)) {
                throw new IllegalArgumentException("Email already used");
            }
            setName(name);
            setEmail(email);
        }catch (Exception e){
            e.printStackTrace();
            removeFromExtent();
        }
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getName() { return name;}
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
