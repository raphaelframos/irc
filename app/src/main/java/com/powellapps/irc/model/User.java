package com.powellapps.irc.model;

import com.powellapps.irc.utils.ConstantsUtils;

import java.util.HashMap;
import java.util.List;

public class User {

   private String name;
   private String id;
   private List<String> channels;

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
        user.put(ConstantsUtils.ID, getId());
        user.put(ConstantsUtils.NAME, getName());
        user.put(ConstantsUtils.ACCESSED, channels);
        return user;
    }


    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}
