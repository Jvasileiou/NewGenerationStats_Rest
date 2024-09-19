package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;
import com.jvvas.restnewgenstats.Objects.Player;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class PlayerDTO implements Serializable {

    @SerializedName("player_id")
    private String playerId;

    @SerializedName("surname")
    private String surname;

    @SerializedName("name")
    private String name;

    @SerializedName("number")
    private int number;

    @SerializedName("position")
    private String position;

    @SerializedName("selection")
    private String selection;

    public PlayerDTO(String playerId, String surname, String name, int number, String position) {
        this.playerId = playerId;
        this.surname = surname;
        this.name = name;
        this.number = number;
        this.position = position;
    }

    public PlayerDTO(String playerId, String surname, String name, int number, String position, String selection) {
        this.playerId = playerId;
        this.surname = surname;
        this.name = name;
        this.number = number;
        this.position = position;
        this.selection = selection;
    }

    public PlayerDTO(Player p) {
        this.playerId = p.getPlayerId();
        this.surname = p.getSurname();
        this.name = p.getName();
        this.number = Integer.parseInt(p.getNumber());
        this.position = p.getPosition();
        if(p.getSelection() == null )
            this.selection = "unset";
        else if (!p.getSelection())
            this.selection = "replacement";
        else
            this.selection = "basic";
    }

    public PlayerDTO(Player p, String selection) {
        this.playerId = p.getPlayerId();
        this.surname = p.getSurname();
        this.name = p.getName();
        this.number = Integer.parseInt(p.getNumber());
        this.position = p.getPosition();
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

    public int getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public String getSelection() {
        return selection;
    }

    @NotNull
    @Override
    public String toString() {
        return "PlayerDTO{" +
                "playerId='" + playerId + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", position='" + position + '\'' +
                ", selection='" + selection + '\'' +
                '}';
    }
}
