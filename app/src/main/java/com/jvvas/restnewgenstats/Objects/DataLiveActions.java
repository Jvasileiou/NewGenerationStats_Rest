package com.jvvas.restnewgenstats.Objects;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DataLiveActions implements Serializable {

    // These are used to display event list
    private int minute;
    private String event;
    private String player;
    private String timePeriod;
    private String location = " "; // default
    private boolean isTeamA;
    private boolean isFlagged;

    // This is useless but I am scared to delete it
    private boolean isDefault;

    // These are used to specify 3 different kinds of events
    private boolean isRaw;
    private boolean justTeam;

    // Used for delete
    private String stat;

    // For creating raw action
    public DataLiveActions(int minute, String event, String timePeriod, boolean isDefault){
        isRaw = true;

        this.minute = minute;
        this.event = event;
        this.timePeriod = timePeriod;
        this.isDefault = isDefault;
    }

    // For creating justTeam action
    public DataLiveActions(int minute, String event, String stat, boolean isTeamA, String timePeriod, boolean isDefault){
        justTeam = true;

        this.minute = minute;
        this.event = event;
        this.stat = stat;
        this.isTeamA = isTeamA;
        this.timePeriod = timePeriod;
        this.isDefault = isDefault;
    }

    // For creating full data action
    public DataLiveActions(int minute, String event, String stat, String player, boolean isTeamA, String location, String timePeriod, boolean isDefault){
        this.minute = minute;
        this.event = event;
        this.stat = stat;
        this.player = player;
        this.isTeamA = isTeamA;
        this.location = location;
        this.timePeriod = timePeriod;
        this.isDefault = isDefault;
    }

    // For creating action from the DB strings
    public DataLiveActions(String db){
        String[] splitMin = db.split(":",2);
        String[] info = splitMin[1].split("\\|");

        this.minute = Integer.parseInt(splitMin[0].trim());
        this.event = info[0].trim();
        this.timePeriod = info[info.length-2].trim();
        this.isDefault = Boolean.parseBoolean(info[info.length-1].trim());
        // Raw
        if (info[1].trim().equals("")){
            isRaw = true;
            return;
        }
         this.stat = findStat();
        // Just team
        if (info.length == 5){
            this.justTeam = true;
            this.isTeamA = info[1].trim().equals("Α");
            return;
        }

        this.player = info[1].trim();
        this.isTeamA = info[2].trim().equals("Α");
        this.location = info[3].trim();
    }

    public String display(){
        // $$$ is team placeholder to be replaced with the team's name
        if (isRaw)
            return event + " | " + timePeriod;
        if (justTeam)
            return event + " | $$$ | " + timePeriod;
        if (location.equals(" "))
            return event + " | " + player + " | $$$ | " + timePeriod;
        return event + " | " + player + " | $$$ | " + location + " | " + timePeriod;
    }

    public int getMinute(){
        return minute;
    }

    @NotNull
    public String toString(){
        String team = isTeamA ? " | A | " : " | B | ";
        if (isRaw)
            return minute + ":" + event + " | | | " + timePeriod + " | " + isDefault;
        else if (justTeam)
            return minute + ":" + event + team  + " | " + timePeriod + " | " + isDefault;
        return minute + ":" + event + " | " + player  +  team  + location +
                " | " + timePeriod+" | "+isDefault;
    }


    public boolean isFlagged(){
        return isFlagged;
    }
    public boolean isRaw(){
        return isRaw;
    }
    public boolean isTeamA(){
        return  isTeamA;
    }
    public boolean isJustTeam(){
        return justTeam;
    }
    public void setMinute(int minute){
        this.minute = minute;
    }
    public void setEvent(String event){
        this.event = event;
    }
    public void setTimePeriod(String timePeriod){
        this.timePeriod = timePeriod;
    }
    public void setIsTeamA(boolean isTeamA){
        this.isTeamA = isTeamA;
    }
    public void setPlayer(String player){
        this.player = player;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public String getTimePeriod(){
        return timePeriod;
    }
    public String getStat(){
        return stat;
    }
    public String getEvent(){
        return event;
    }
    public String getPlayer(){
        return player;
    }

    public void toggleFlag(){
        isFlagged = !isFlagged;
    }

    public String findStat(){
        switch (event){
            case "ΠΡΟΩΘΗΜΕΝΗ ΠΑΣΑ":
                return "forwardingPass";
            case "ΚΙΤΡΙΝΗ":
            case "2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ":
                return "yellowCard";
            case "ΚΟΚΚΙΝΗ":
                return "redCard";
            case "ΚΟΨΙΜΟ":
                return "kopsimo";
            case "ΑΠΟΤ ΔΙΕΙΣΔΥΣΗ":
                return "dieisdusiOut";
            case "ΕΠΙΤ ΔΙΕΙΣΔΥΣΗ":
                return "dieisdusiIn";
            case "ΦΑΟΥΛ ΤΡΑΒΗΓ ΦΑΝ":
            case "ΦΑΟΥΛ ΤΑΚΛΙΝ":
            case "ΦΑΟΥΛ ΧΕΡΙ":
            case "ΦΑΟΥΛ ΣΚΛΗΡΟ ΦΑΟΥΛ":
            case "ΦΑΟΥΛ ΣΩΜΑΤΙΚΗ ΕΠΑΦΗ":
                return "faoulKata";
            case "ΥΠΕΡ ΚΕΡΔΙΣΜΕΝΟ ΠΕΝΑΛΤΥ":
            case "ΥΠΕΡ":
                return "faoulYper";
            case "ΕΠΙΤ ΓΕΜ":
                return "gemismaIn";
            case "ΑΠΟΤ ΓΕΜ":
                return "gemismaOut";
            case "ΕΠΕΜΒΑΣΗ":
                return "epemvasi";
            case "ΑΣΙΣΤ":
                return "assist";
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ":
                return "apoKornerIn";
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ":
                return "apoFaoulIn";
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ":
                return "penalty";
            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ":
                return "kefaliaIn";
            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ":
                return "shootIn";
            case "ΚΛΕΨΙΜΟ":
                return "klepsimo";
            case "ΛΑΘΟΣ ΚΟΝΤΡΟΛ":
            case "ΛΑΘΟΣ ΚΑΚΗ ΠΑΣΑ":
                return "lathos";
            case "ΠΕΝΑΛΤΙ ΕΥΣΤΟΧΟ":
                return"penaltyScore";
            case "ΑΠΟΚΡΟΥΣΗ":
                return "apokrousi";
            case "ΚΟΡΝΕΡ ΔΕΞΙΑ ΠΛΕΥΡΑ":
                return "cornerRight";
            case "ΚΟΡΝΕΡ ΑΡΙΣΤΕΡΗ ΠΛΕΥΡΑ":
                return "cornerLeft";
            case "ΟΦΣΑΙΝΤ":
                return "offside";
            default:
                return checkTelikes();
        }
    }

    private String checkTelikes(){
        if (event.contains("ΣΟΥΤ ΕΚΤΟΣ"))
            return "shootOut";
        else if (event.contains("ΚΕΦΑΛΙΑ ΕΚΤΟΣ"))
            return "kefaliaOut";
        else if (event.contains("ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ"))
            return "apoFaoulOut";
        else if (event.contains("ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ"))
            return "xamenoPenaltyOut";
        else if (event.contains("ΣΟΥΤ ΕΝΤΟΣ"))
            return "shootIn";
        else if (event.contains("ΚΕΦΑΛΙΑ ΕΝΤΟΣ"))
            return "kefaliaIn";
        else if (event.contains("ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ"))
            return "apoFaoulIn";
        else if (event.contains("ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ"))
            return "xamenoPenaltyIn";
        else
            return "";
    }


    public void setStat(String stat){
        this.stat = stat;
    }
}
