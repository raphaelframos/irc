package com.powellapps.irc.model;

public enum Category {

    CREATOR("criador"), MODERATOR("moderador"), USER("usuário");

    private final String name;

    Category(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
