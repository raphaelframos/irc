package com.powellapps.irc.model;

import androidx.annotation.NonNull;

import java.util.HashMap;



public class MensagemChat {

    private String idUser;
    private String nameUser;
    private String text;
    private Long creationDate;

    public HashMap<String, Object> getMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("idUser", getIdUser());
        map.put("nameUser", getNameUser());
        map.put("text", getText());
        map.put("creationDate", getCreationDate());
        return map;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }
}
