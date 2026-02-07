package com.andreafilice.lavorami;

public class UserProfile {
    private String id;
    private String name;
    private String email;

    public UserProfile(String name, String email) {
        this.email = email;
        this.name = name;
    }

    public String getName(){return name;}
    public String getEmail(){return email;}
}
