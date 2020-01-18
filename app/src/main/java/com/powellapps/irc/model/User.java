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
   private String nickName;
   private List<String> channels;


    public User() {
    }

   private List<String> channels = new ArrayList<>();



    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public User(String name, String id, String nickName) {
        this.name = name;
        this.id = id;
        this.nickName = nickName;
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
        user.put(ConstantsUtils.NICKNAME, getNickName());
        user.put(ConstantsUtils.ACCESSED, getChannels());
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


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;

    public void add(String id) {
        if(channels == null){
            channels = new ArrayList<>();
        }
        this.channels.add(id);
    }
}
