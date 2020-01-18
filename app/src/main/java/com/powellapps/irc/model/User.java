package com.powellapps.irc.model;

import com.google.firebase.auth.FirebaseUser;
import com.powellapps.irc.utils.ConstantsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String id;
    private String office;
    private List<String> channels = new ArrayList<>();
    private String nickname;

    public User() {}

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public User(FirebaseUser user) {
        setId(user.getUid());
        setName(user.getDisplayName());
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
        user.put(ConstantsUtils.OFFICE, getOffice());
        user.put(ConstantsUtils.NICKNAME, getNickname());
        user.put(ConstantsUtils.CHANNELS, getChannels());
        return user;
    }


    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

    public String getNameChannel() {
        return "+"+getName();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
}

    public void add(String id) {
        if(channels == null){
            channels = new ArrayList<>();
        }
        if(!channels.contains(id)) {
            this.channels.add(id);
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
