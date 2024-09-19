package com.jvvas.restnewgenstats.Objects;


import java.io.Serializable;

public class Player implements Serializable {

    private String playerId;
    private String surname;
    private String name;
    private String number;
    private String position;
    private Boolean selection;


    public Player()
    {

    }

    public Player(String playerId, String surname, String name, String number, String position)
    {
        this.playerId = playerId;
        this.surname = surname;
        this.name = name;
        this.number = number;
        this.position = position;
    }

    public Player(String playerId, String surname, String name, String number, String position, Boolean selection)
    {
        this.playerId = playerId;
        this.surname = surname;
        this.name = name;
        this.number = number;
        this.position = position;
        this.selection = selection;
    }


    public String getPlayerId() {
        return playerId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public Boolean getSelection() {
        return selection;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSelection(Boolean selection) {
        this.selection = selection;
    }
}
