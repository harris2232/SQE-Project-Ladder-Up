package com.sqe.project.ladderup;

public class newUser {
    private String ID;
    private String Name;
    private String Email;
    private String Password;

    newUser(){

    }

    public newUser(String id, String name, String email, String password) {
        ID = id;
        Name = name;
        Email = email;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getID() {
        return ID;
    }
}
