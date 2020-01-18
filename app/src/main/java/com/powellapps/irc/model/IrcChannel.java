package com.powellapps.irc.model;

import com.google.firebase.firestore.DocumentId;
import com.powellapps.irc.utils.ConstantsUtils;

import java.util.HashMap;

public class IrcChannel {

    private String name;
    private String description;
    private String creator;
    @DocumentId
    private String id;

    public IrcChannel(){}

    public IrcChannel(String id) {
        setCreator(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Object> map(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConstantsUtils.NAME, getName());
        map.put(ConstantsUtils.DESCRIPTION, getDescription());
        map.put(ConstantsUtils.CREATOR, getCreator());
        return map;
    }

    public boolean contain(String text) {
        return getName().contains(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
