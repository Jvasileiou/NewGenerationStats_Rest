package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeamDTO implements Serializable {


    @SerializedName("team_id")
    private String teamId;

    @SerializedName("name")
    private String name;

    @SerializedName("colour")
    private String colour;

    @SerializedName("coach")
    private CoachDTO coach;

    @SerializedName("players")
    private List<PlayerDTO> players;

    public TeamDTO(String teamId, String name, String colour, CoachDTO coach, List<PlayerDTO> players) {
        this.teamId = teamId;
        this.name = name;
        this.colour = colour;
        this.coach = coach;
        this.players = players;
    }

    public TeamDTO(String teamId, String name, String colour, List<PlayerDTO> players) {
        this.teamId = teamId;
        this.name = name;
        this.colour = colour;
        this.players = players;
    }

//    // Without colour
//    public TeamDTO(String teamId, String name, CoachDTO coach, List<PlayerDTO> players) {
//        this.teamId = teamId;
//        this.name = name;
//        this.coach = coach;
//        this.players = players;
//    }

    public String getColour() {
        return colour;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public CoachDTO getCoach() {
        return coach;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(String color) {
        this.colour = colour;
    }

    public void setCoach(CoachDTO coach) {
        this.coach = coach;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "teamId='" + teamId + '\'' +
                ", name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                ", coach=" + coach +
                ", players=" + players +
                '}';
    }
}
