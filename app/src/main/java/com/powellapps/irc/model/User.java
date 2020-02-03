package com.powellapps.irc.model;

import com.google.firebase.auth.FirebaseUser;
import com.powellapps.irc.utils.ConstantsUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private String name;
    private String id;
    private String office = "";
    private String nickname = "";

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
        return user;
    }


    public String getChannelName() {
        return "+"+getNickname();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
}

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                id.equals(user.id) &&
                nickname.equals(user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, nickname);
    }

}
