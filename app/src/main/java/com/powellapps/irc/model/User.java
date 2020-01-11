package com.powellapps.irc.model;

import java.util.HashMap;

public class User {

   private String name;
   private String id;

    public User() {
    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Object> returnUser() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", getId());
        user.put("name", getName());
        return user;
    }


}