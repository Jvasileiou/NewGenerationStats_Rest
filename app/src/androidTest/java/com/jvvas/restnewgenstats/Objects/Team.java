package com.jvvas.restnewgenstats.Objects;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Team implements Serializable {

    private String teamId ;
    private String name ;
    private ArrayList<Player> allPlayers;

    private ArrayList<Player> basicPlayers;
    private ArrayList<Player> replacementPlayers;
    private ArrayList<Player> removed;

    private boolean isTeamA;
    private int colour;

    //I guess it should be AM:yellow,red
    private HashMap<String,int[]> cards;
    private int redCards;
    private int yellowCards;

    private int changesAvailable = 100;
    private String coachFullName;

    public Team(String name, boolean isTeamA)
    {
        this.name = name.replace(",",".");
        this.isTeamA = isTeamA;

        initCollections();
        defaultColours();
    }

    private void initCollections()
    {
        allPlayers = new ArrayList<Player>();
        basicPlayers = new ArrayList<Player>();
        replacementPlayers = new ArrayList<Player>();
        removed = new ArrayList<Player>();
        cards = new HashMap<>();
    }

    private void defaultColours()
    {
        if(isTeamA) colour = Color.rgb(255,220,0);
        else colour = Color.rgb(57,204,204);
    }



    public void makeSamplePlayers()
    {
        int AMCounter = isTeamA ? 11111 : 22211;
        int numberCounter = 1;
        for (int i = 0; i < 17; i++)
        {
            Player newPlayer = new Player(String.valueOf(AMCounter++),"","",
                    String.valueOf(numberCounter++),"");
            allPlayers.add(newPlayer);
            if(numberCounter-1 == 1 || numberCounter-1 == 16) newPlayer.setPosition("GK");
            if(numberCounter<=12) basicPlayers.add(newPlayer);
            else replacementPlayers.add(newPlayer);
        }
    }


    public void setColour(int colour)
    {
        this.colour = colour;
    }

    public boolean getIsTeamA()
    {
        return isTeamA;
    }


    public boolean hasRedCard(String AM)
    {
        if (!cards.containsKey(AM)) return false;
        else if(cards.get(AM)[1] == 1) return true;
        return false;
    }

    public int getYellowCards(String AM)
    {
        if (!cards.containsKey(AM)) return 0;
        else return cards.get(AM)[0];
    }

    public void toggleRedCard(String AM)
    {
        if (!cards.containsKey(AM))
        {
            cards.put(AM, new int[] {0,1});
            redCards++;
        }
        else
        {
            int[] playerCards = cards.get(AM);
            if (playerCards[1] == 0)
            {
                playerCards[1] = 1;
                redCards++;
            }
            cards.put(AM,playerCards);
        }
    }

    public void toggleYellowCard(String AM)
    {
        if (!cards.containsKey(AM))
        {
            yellowCards++;
            cards.put(AM, new int[] {1,0});
        }
        else
        {
            int[] playerCards = cards.get(AM);
            if (playerCards[0] == 1)
            {
                yellowCards++;redCards++;
                playerCards[0] = 2;
                playerCards[1] = 1;
            }
            else if (playerCards[0] == 0)
            {
                playerCards[0] = 1;
                yellowCards++;
            }
            cards.put(AM,playerCards);
        }
    }



    public String toString()
    {
        return "NAME: "+name+". COLOUR: "+colour+" Basic: "+basicPlayers.size()+". Replacements: "+replacementPlayers.size();
    }

    public Player getBasicPlayer(int index)
    {
        return basicPlayers.get(index);
    }

    public int getColour()
    {
        return colour;
    }

    public ArrayList<Player> getReplacementPlayers()
    {
        return replacementPlayers;
    }

    public ArrayList<Player> getBasicPlayers()
    {
        return basicPlayers;
    }

    public ArrayList<Player> getRemoved()
    {
        return removed;
    }

    public void replace(Player playerIn, Player playerOut)
    {
        addBasicPlayer(playerIn);
        basicPlayers.remove(playerOut);
        replacementPlayers.remove(playerIn);
        removed.add(playerOut);
        changesAvailable--;
    }

    public void replaceEdit(Player playerIn, Player playerOut)
    {
        addBasicPlayer(playerIn);
        basicPlayers.remove(playerOut);
        removed.remove(playerIn);
        addReplacementPlayer(playerOut);
        changesAvailable++;
    }

    public String getName()
    {
        return name;
    }

    public int getTotalRedCards()
    {
        return redCards;
    }

    public int getTotalYellowCards()
    {
        return yellowCards;
    }

    public void reduceRed(String number)
    {
        String playerAM ="";
        for(Player pl:basicPlayers)
        {
            if(pl.getNumber().equals(number.trim())) playerAM = pl.getPlayerId();
        }
        if(cards.containsKey(playerAM))
        {
            int[] cardArray = cards.get(playerAM);
            if (cardArray[1]>0) cardArray[1]--;
            cards.put(playerAM,cardArray);
            redCards--;
        }
    }

    public void reduceYellow(String number)
    {
        String playerAM ="";
        for(Player pl:basicPlayers)
        {
            if(pl.getNumber().equals(number.trim())) playerAM = pl.getPlayerId();
        }
        if(cards.containsKey(playerAM))
        {
            int[] cardArray = cards.get(playerAM);
            if (cardArray[0] == 2)
            {
                cardArray[1]--;redCards--;
            }
            if (cardArray[0]>0) cardArray[0]--;
            cards.put(playerAM,cardArray);
            yellowCards--;
        }
    }

    public void replaceFromNumbers(String in, String out)
    {
        Player playerIn = null;
        Player playerOut = null;
        for(Player currPlayer: basicPlayers)
        {
            if (currPlayer.getNumber().trim().equals(in))
            {
                playerIn = currPlayer;
                break;
            }
        }
        for(Player currPlayer: removed)
        {
            if (currPlayer.getNumber().trim().equals(out))
            {
                playerOut = currPlayer;
                break;
            }
        }
        if (playerIn != null && playerOut != null)
        {
            replaceEdit(playerOut,playerIn);
        }
    }

    public void redFromNumber(String player)
    {
        for (Player currPlayer: basicPlayers)
        {
            if (currPlayer.getNumber().trim().equals(player))
            {
                if (!hasRedCard(currPlayer.getPlayerId()))
                {
                    toggleRedCard(currPlayer.getPlayerId());
                }
                break;
            }
        }
    }

    public void yellowFromNumber(String player)
    {
        for (Player currPlayer: basicPlayers)
        {
            if (currPlayer.getNumber().trim().equals(player))
            {
                int yellowNum = getYellowCards(currPlayer.getPlayerId());
                if (yellowNum == 0)
                {
                    toggleYellowCard(currPlayer.getPlayerId());
                }
                else if (yellowNum == 1)
                {
                    toggleYellowCard(currPlayer.getPlayerId());
                    toggleRedCard(currPlayer.getPlayerId());
                }
                break;
            }
        }
    }

    public void changeFromReadingActions(String in,String out)
    {
        Player playerIn = null;
        Player playerOut = null;
        for(Player currPlayer: replacementPlayers)
        {
            if (currPlayer.getNumber().trim().equals(in))
            {
                playerIn = currPlayer;
                break;
            }
        }
        for(Player currPlayer: basicPlayers)
        {
            if (currPlayer.getNumber().trim().equals(out))
            {
                playerOut = currPlayer;
                break;
            }
        }
        if (playerIn != null && playerOut != null)
        {
            replace(playerIn,playerOut);
        }
    }


    public ArrayList<Player> sortByPosition(ArrayList<Player> unsorted)
    {
        ArrayList<Player>[] buckets = new ArrayList[17];
        for (int i=0; i<17; i++) buckets[i] = new ArrayList<>();
        for (Player currplayer: unsorted)
        {
            if (currplayer.getPosition().equals("GK")) buckets[0].add(currplayer);
            else if (currplayer.getPosition().equals("SW")) buckets[1].add(currplayer);
            else if (currplayer.getPosition().equals("LB")) buckets[2].add(currplayer);
            else if (currplayer.getPosition().equals("RB")) buckets[3].add(currplayer);
            else if (currplayer.getPosition().equals("CB")) buckets[4].add(currplayer);
            else if (currplayer.getPosition().equals("LWB")) buckets[5].add(currplayer);
            else if (currplayer.getPosition().equals("RWB")) buckets[6].add(currplayer);
            else if (currplayer.getPosition().equals("DM")) buckets[7].add(currplayer);
            else if (currplayer.getPosition().equals("LM")) buckets[8].add(currplayer);
            else if (currplayer.getPosition().equals("RM")) buckets[9].add(currplayer);
            else if (currplayer.getPosition().equals("CM")) buckets[10].add(currplayer);
            else if (currplayer.getPosition().equals("AM")) buckets[11].add(currplayer);
            else if (currplayer.getPosition().equals("LW")) buckets[12].add(currplayer);
            else if (currplayer.getPosition().equals("RW")) buckets[13].add(currplayer);
            else if (currplayer.getPosition().equals("SS")) buckets[14].add(currplayer);
            else if (currplayer.getPosition().equals("CF")) buckets[15].add(currplayer);
            else buckets[16].add(currplayer);
        }
        ArrayList<Player> sorted = new ArrayList<>();
        for (ArrayList<Player> list: buckets) sorted.addAll(list);
        return sorted;
    }

    public ArrayList<Player> sortByNumber()
    {
        ArrayList<Player> sorted = new ArrayList<Player>();
        ArrayList<Player> goalkeepers = new ArrayList<Player>();
        ArrayList<Player> NaNPlayers = new ArrayList<Player>();
        for (int i = 0; i < basicPlayers.size(); i++)
        {
            if(!basicPlayers.get(i).getNumber().matches("[0-9]+"))
            {
                NaNPlayers.add(basicPlayers.get(i));
                basicPlayers.remove(basicPlayers.get(i));
            }
        }
        int size = basicPlayers.size();
        while (sorted.size()+goalkeepers.size()<size)
        {
            Player currPlayer = basicPlayers.get(0);
            for (int i = 1; i < basicPlayers.size(); i++)
            {
                if (Integer.parseInt(currPlayer.getNumber())>
                        Integer.parseInt(basicPlayers.get(i).getNumber()))
                    currPlayer = basicPlayers.get(i);
            }
            if (currPlayer.getPosition().equals("GK")) goalkeepers.add(currPlayer);
            else sorted.add(currPlayer);
            basicPlayers.remove(currPlayer);
        }
        basicPlayers = new ArrayList<Player>();
        basicPlayers.addAll(goalkeepers);
        basicPlayers.addAll(sorted);
        basicPlayers.addAll(NaNPlayers);
        return basicPlayers;
    }

    public int getChangesAvailable() { return changesAvailable;    }


    public String generatedBasicPlayers()
    {
        String retStr = "";
        for (Player player:basicPlayers)
        {
            retStr += player.getPlayerId()+",";
        }
        if (retStr.length()<2) return "NONE";
        return retStr.substring(0,retStr.length()-1);
    }

    public String generatedReplacementPlayers()
    {
        String retStr = "";
        for (Player player:replacementPlayers)
        {
            retStr += player.getPlayerId()+",";
        }
        if (retStr.length()<2) return "NONE";
        return retStr.substring(0,retStr.length()-1);
    }


    public void setRemoved(ArrayList<Player> rem)
    {
        removed = rem;
    }
    public void setChangesAvailable(int changesAvailable){this.changesAvailable = changesAvailable;}

    public String getHexColour()
    {
        return String.format("#%06X", (0xFFFFFF & colour));
    }

    public void readFromHexColour(String hex)
    {
        if (hex != null) colour = Color.parseColor(hex);
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
        for (Player player: allPlayers){
            if (player.getSelection() !=null)
                if (player.getSelection())
                    basicPlayers.add(player);
                else
                    replacementPlayers.add(player);
        }
    }

    public String getCoachFullName() {
        return coachFullName;
    }

    public void setCoachFullName(String coachFullName) {
        this.coachFullName = coachFullName;
    }

    public String getKeyName()
    {
        return name.replace(".",",");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player pl) {
        addToCorrectPosition(allPlayers,pl);
    }
    public void addBasicPlayer(Player pl) {
        addToCorrectPosition(basicPlayers,pl);
    }
    public void addReplacementPlayer(Player pl) {
        addToCorrectPosition(replacementPlayers,pl);
    }

    private void addToCorrectPosition(ArrayList<Player> addHere, Player addHim){
        int i=0,start = 0,stopPosition = addHere.size();
        while (addHere.size()>i && addHere.get(i).getPosition().equals("GK")) i++;
        if (addHim.getPosition().equals("GK")) stopPosition = i;
        else start = i;
        if (!addHim.getNumber().matches("[0-9]+")) {
            addHere.add(stopPosition,addHim);
            return;
        }
        for (i = start; i<stopPosition;i++) {
            Player curr = addHere.get(i);
            if (!curr.getNumber().matches("[0-9]+")) {
                addHere.add(i,addHim);
                return;
            }
            else if(Integer.parseInt(curr.getNumber()) > Integer.parseInt(addHim.getNumber())) {
                addHere.add(i,addHim);
                return;
            }
        }
        addHere.add(stopPosition,addHim);
    }

    public void editedPlayer(Player edited) {
        allPlayers.remove(edited);
        addPlayer(edited);
        if(basicPlayers.contains(edited)){
            basicPlayers.remove(edited);
            addBasicPlayer(edited);
        }
        else if (replacementPlayers.contains(edited)){
            replacementPlayers.remove(edited);
            addReplacementPlayer(edited);
        }
        else if (removed.contains(edited))
        {
            removed.remove(edited);
            addToCorrectPosition(removed,edited);
        }
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}