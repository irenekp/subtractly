package com.example.mainpage_subapp;

public class User {
    String Email;
    long createdAt;

    public User(){};

    public User(String Email, long createdAt){
        this.Email = Email;
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }

}
