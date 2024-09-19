package com.jvvas.restnewgenstats.Objects;


import com.jvvas.restnewgenstats.JavaDataTransferObjects.PlayerDTO;

import java.io.Serializable;

public class Player implements Serializable, Comparable<Object>{

    private String playerId;
    private String surname;
    private String name;
    private String number;
    private String position;
    private Boolean selection;

    private int yellowCards = 0;
    private boolean redCard;

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

    public Player(PlayerDTO playerDto){
        playerId = playerDto.getPlayerId();
        surname = playerDto.getSurname();
        name = playerDto.getName();
        number = String.valueOf(playerDto.getNumber());
        position = playerDto.getPosition();
        if (playerDto.getSelection() !=null)
            selection = playerDto.getSelection().equals("basic");
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

    public boolean isBasic(){
        return selection != null && selection;
    }

    public boolean hasRedCard(){
        return redCard;
    }

    public void setHasRedCard(boolean redCard){
        this.redCard = redCard;
    }

    public int getYellowCards(){
        return yellowCards;
    }

    public void setYellowCards(int yellowCards){
        this.yellowCards = yellowCards;
        if (yellowCards==2) redCard = true;
    }

    public boolean isReplacement(){
        return selection != null && !selection;
    }

    @Override
    public int compareTo(Object o) {
        String position = this.getPosition();
        String oPosition = ((Player) o).getPosition();
        if (position == null || oPosition == null)
            return -1;
        else if (this.getPosition().equals("GK") && !((Player) o).getPosition().equals("GK"))
            return -1;
        else if (((Player) o).getPosition().equals("GK") && !this.getPosition().equals("GK"))
            return 1;

        if (Integer.parseInt(this.getNumber()) < Integer.parseInt(((Player) o).getNumber()))
            return -1;
        else if (Integer.parseInt(this.getNumber()) < Integer.parseInt(((Player) o).getNumber()))
            return 0;
        return 1;
    }
}
