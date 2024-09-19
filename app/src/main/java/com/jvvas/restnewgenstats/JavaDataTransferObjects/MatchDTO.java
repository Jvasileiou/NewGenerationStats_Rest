package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchDTO implements Serializable {

    @SerializedName("match_id")
    private String matchId;

    @SerializedName("league_id")
    private String leagueId;

    @SerializedName("league")
    private String league;

    @SerializedName("season")
    private String season;

    @SerializedName("match_day")
    private Integer matchDay;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    @SerializedName("place")
    private String place;

    @SerializedName("field")
    private String field;

    @SerializedName("organization")
    private String organization;

    @SerializedName("colour")
    private String colour;

    @SerializedName("status")
    private String status;

    @SerializedName("delaysA")
    private Integer delaysA;

    @SerializedName("delaysB")
    private Integer delaysB;

    @SerializedName("delaysExtraA")
    private Integer delaysExtraA;

    @SerializedName("delaysExtraB")
    private Integer delaysExtraB;

//    @SerializedName("game_time")
//    private Integer gameTime;

    @SerializedName("basicPlayers")
    private String basicPlayers;

    @SerializedName("replacementPlayers")
    private String replacementPlayers;

    @SerializedName("LIVE")
    private LiveDTO live;

    @SerializedName("ActionsLive")
    private ArrayList<String> actionsLive;

    @SerializedName("teamA")
    private TeamDTO teamA;

    @SerializedName("teamB")
    private TeamDTO teamB;

    @SerializedName("referee")
    private RefereeDTO referee;

    @SerializedName("refereeHelperA")
    private RefereeDTO refereeHelperA;

    @SerializedName("refereeHelperB")
    private RefereeDTO refereeHelperB;

    @SerializedName("refereeD")
    private RefereeDTO refereeD;


    // With Match_id
    public MatchDTO(String matchId, String leagueId, String league, String season, Integer matchDay,
                    String date, String time, String place, String field, String organization,
                    String colour, String status, Integer delaysA, Integer delaysB, Integer delaysExtraA,
                    Integer delaysExtraB, String basicPlayers, String replacementPlayers, LiveDTO live,
                    ArrayList<String> actionsLive, TeamDTO teamA, TeamDTO teamB, RefereeDTO referee,
                    RefereeDTO refereeHelperA, RefereeDTO refereeHelperB, RefereeDTO refereeD)
    {
        this.matchId = matchId;
        this.leagueId = leagueId;
        this.league = league;
        this.season = season;
        this.matchDay = matchDay;
        this.date = date;
        this.time = time;
        this.place = place;
        this.field = field;
        this.organization = organization;
        this.colour = colour;
        this.status = status;
        this.delaysA = delaysA;
        this.delaysB = delaysB;
        this.delaysExtraA = delaysExtraA;
        this.delaysExtraB = delaysExtraB;
        this.basicPlayers = basicPlayers;
        this.replacementPlayers = replacementPlayers;
        this.live = live;
        this.actionsLive = actionsLive;
        this.teamA = teamA;
        this.teamB = teamB;
        this.referee = referee;
        this.refereeHelperA = refereeHelperA;
        this.refereeHelperB = refereeHelperB;
        this.refereeD = refereeD;
    }


    // No Match_id
    // With leagueId & NO Referees Strings
    public MatchDTO(String leagueId, String league, String season, Integer matchDay, String date,
                    String time, String place, String field, String organization, String colour,
                    String status, Integer delaysA, Integer delaysB, Integer delaysExtraA,
                    Integer delaysExtraB, String basicPlayers, String replacementPlayers, LiveDTO live,
                    ArrayList<String> actionsLive, TeamDTO teamA, TeamDTO teamB, RefereeDTO referee,
                    RefereeDTO refereeHelperA, RefereeDTO refereeHelperB, RefereeDTO refereeD)
    {
        this.leagueId = leagueId;
        this.league = league;
        this.season = season;
        this.matchDay = matchDay;
        this.date = date;
        this.time = time;
        this.place = place;
        this.field = field;
        this.organization = organization;
        this.colour = colour;
        this.status = status;
        this.delaysA = delaysA;
        this.delaysB = delaysB;
        this.delaysExtraA = delaysExtraA;
        this.delaysExtraB = delaysExtraB;
        this.basicPlayers = basicPlayers;
        this.replacementPlayers = replacementPlayers;
        this.live = live;
        this.actionsLive = actionsLive;
        this.teamA = teamA;
        this.teamB = teamB;
        this.referee = referee;
        this.refereeHelperA = refereeHelperA;
        this.refereeHelperB = refereeHelperB;
        this.refereeD = refereeD;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeague() {
        return league;
    }

    public String getSeason() {
        return season;
    }

    public Integer getMatchDay() {
        return matchDay;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getField() {
        return field;
    }

    public String getOrganization() {
        return organization;
    }

    public String getColour() {
        return colour;
    }

    public String getStatus() {
        return status;
    }

    public Integer getDelaysA() {
        return delaysA;
    }

    public Integer getDelaysB() {
        return delaysB;
    }

    public Integer getDelaysExtraA() {
        return delaysExtraA;
    }

    public Integer getDelaysExtraB() {
        return delaysExtraB;
    }

//    public Integer getGameTime() {
//        return gameTime;
//    }

    public String getBasicPlayers() {
        return basicPlayers;
    }

    public String getReplacementPlayers() {
        return replacementPlayers;
    }

    public LiveDTO getLive() {
        return live;
    }

    public ArrayList<String> getActionsLive() {
        return actionsLive;
    }

    public TeamDTO getTeamA() {
        return teamA;
    }

    public TeamDTO getTeamB() {
        return teamB;
    }

    public RefereeDTO getReferee() {
        return referee;
    }

    public RefereeDTO getRefereeHelperA() {
        return refereeHelperA;
    }

    public RefereeDTO getRefereeHelperB() {
        return refereeHelperB;
    }

    public RefereeDTO getRefereeD() {
        return refereeD;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setMatchDay(Integer matchDay) {
        this.matchDay = matchDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelaysA(Integer delaysA) {
        this.delaysA = delaysA;
    }

    public void setDelaysB(Integer delaysB) {
        this.delaysB = delaysB;
    }

    public void setDelaysExtraA(Integer delaysExtraA) {
        this.delaysExtraA = delaysExtraA;
    }

    public void setDelaysExtraB(Integer delaysExtraB) {
        this.delaysExtraB = delaysExtraB;
    }

    public void setBasicPlayers(String basicPlayers) {
        this.basicPlayers = basicPlayers;
    }

    public void setReplacementPlayers(String replacementPlayers) {
        this.replacementPlayers = replacementPlayers;
    }

    public void setLive(LiveDTO live) {
        this.live = live;
    }

    public void setActionsLive(ArrayList<String> actionsLive) {
        this.actionsLive = actionsLive;
    }

    public void setTeamA(TeamDTO teamA) {
        this.teamA = teamA;
    }

    public void setTeamB(TeamDTO teamB) {
        this.teamB = teamB;
    }

    public void setReferee(RefereeDTO referee) {
        this.referee = referee;
    }

    public void setRefereeHelperA(RefereeDTO refereeHelperA) {
        this.refereeHelperA = refereeHelperA;
    }

    public void setRefereeHelperB(RefereeDTO refereeHelperB) {
        this.refereeHelperB = refereeHelperB;
    }

    public void setRefereeD(RefereeDTO refereeD) {
        this.refereeD = refereeD;
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "leagueId='" + leagueId + '\'' +
                ", league='" + league + '\'' +
                ", season='" + season + '\'' +
                ", matchDay=" + matchDay +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", place='" + place + '\'' +
                ", field='" + field + '\'' +
                ", organization='" + organization + '\'' +
                ", colour='" + colour + '\'' +
                ", status='" + status + '\'' +
                ", delaysA=" + delaysA +
                ", delaysB=" + delaysB +
                ", delaysExtraA=" + delaysExtraA +
                ", delaysExtraB=" + delaysExtraB +
//                ", basicPlayers='" + basicPlayers + '\'' +
//                ", replacementPlayers='" + replacementPlayers + '\'' +
//                ", live=" + live +
//                ", actionsLive=" + actionsLive +
//                ", teamA=" + teamA +
//                ", teamB=" + teamB +
                ", referee=" + referee +
                ", refereeHelperA=" + refereeHelperA +
                ", refereeHelperB=" + refereeHelperB +
                ", refereeD=" + refereeD +
                '}';
    }
}
