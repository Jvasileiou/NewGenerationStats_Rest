package com.jvvas.restnewgenstats.Objects;

import android.graphics.Color;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.PlayerDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Team implements Serializable {

    private String name;
    private ArrayList<Player> allPlayers;
    private ArrayList<Player> removed; // basic -> unset
    private ArrayList<Player> entered; // replacement -> basic
    private String coachFullName;
    private boolean isTeamA;
    private int colour;

    public Team(String name, boolean isTeamA) {
        this.name = name;
        this.isTeamA = isTeamA;

        allPlayers = new ArrayList<>();
        removed = new ArrayList<>();
        entered = new ArrayList<>();
        defaultColours();
        makeSamplePlayers();
    }

    public Team(TeamDTO teamDto, boolean isTeamA) {
        name = teamDto.getName();
        this.isTeamA = isTeamA;

        if (teamDto.getCoach() != null)
            coachFullName = teamDto.getCoach().getName() + " " + teamDto.getCoach().getSurname();
        else
            coachFullName = "";
        colour = Color.parseColor(teamDto.getColour());
        allPlayers = new ArrayList<>();
        removed = new ArrayList<>();
        entered = new ArrayList<>();
        for (PlayerDTO playerDto : teamDto.getPlayers()) {
            allPlayers.add(new Player(playerDto));
        }
        sortAllByNumber();
    }

    private void defaultColours() {
        if (isTeamA) colour = Color.rgb(255, 220, 0);
        else colour = Color.rgb(57, 204, 204);
    }

    private void makeSamplePlayers() {
        int AMCounter = isTeamA ? 11111 : 22211;
        int numberCounter = 1;
        for (int i = 0; i < 17; i++) {
            Player newPlayer;
            if (numberCounter < 12)
                newPlayer = new Player(String.valueOf(AMCounter++), "", "",
                        String.valueOf(numberCounter++), "", true);
            else
                newPlayer = new Player(String.valueOf(AMCounter++), "", "",
                        String.valueOf(numberCounter++), "", false);
            allPlayers.add(newPlayer);
            if (numberCounter - 1 == 1 || numberCounter - 1 == 16) newPlayer.setPosition("GK");
        }
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public boolean getIsTeamA() {
        return isTeamA;
    }

    @NotNull
    public String toString() {
        return "NAME: " + name + ". COLOUR: " + colour;
    }

    public Player getBasicPlayer(int index) {
        int counter = 0;
        for (Player player : allPlayers) {
            if (player.isBasic()) {
                if (counter == index)
                    return player;
                counter++;
            }
        }
        return null;
    }

    public Player getReplacementPlayer(int index) {
        int counter = 0;
        for (Player player : allPlayers) {
            if (player.isReplacement()) {
                if (counter == index)
                    return player;
                counter++;
            }
        }
        return null;
    }

    public int getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public String getHexColour() {
        return String.format("#%06X", (0xFFFFFF & colour));
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public String getCoachFullName() {
        return coachFullName;
    }

    public void setCoachFullName(String coachFullName) {
        this.coachFullName = coachFullName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player pl) {
        addToCorrectPosition(allPlayers, pl);
    }

    private void addToCorrectPosition(ArrayList<Player> addHere, Player addHim) {
        int i = 0, start = 0, stopPosition = addHere.size();
        while (addHere.size() > i && addHere.get(i).getPosition().equals("GK")) i++;
        if (addHim.getPosition().equals("GK")) stopPosition = i;
        else start = i;
        if (!addHim.getNumber().matches("[0-9]+")) {
            addHere.add(stopPosition, addHim);
            return;
        }
        for (i = start; i < stopPosition; i++) {
            Player curr = addHere.get(i);
            if (!curr.getNumber().matches("[0-9]+")) {
                addHere.add(i, addHim);
                return;
            } else if (Integer.parseInt(curr.getNumber()) > Integer.parseInt(addHim.getNumber())) {
                addHere.add(i, addHim);
                return;
            }
        }
        addHere.add(stopPosition, addHim);
    }

    public void sortAllByNumber() {
        Collections.sort(allPlayers);
    }

    public int getBasicPlayerNumber() {
        int ret_value = 0;
        for (Player player : allPlayers)
            if (player.isBasic())
                ret_value++;
        return ret_value;
    }

    public String checkIfThereIsAnyInvalidInput() {
        Set<String> shirtNumbers = new HashSet<>();

        for (Player player : allPlayers) {
            String shirtNumber = player.getNumber();

            if (shirtNumber == null || shirtNumber.trim().isEmpty() || shirtNumber.equals("0")) {
                return "Invalid Tee Number Found";
            }

            if (!shirtNumbers.add(shirtNumber)) {
                return "2 Tees with the number: " + shirtNumber;
            }
        }

        return "notFoundAnyInvalidInput"; // No duplicates
    }

    public int getReplacementPlayerNumber() {
        int ret_value = 0;
        for (Player player : allPlayers)
            if (player.isReplacement())
                ret_value++;
        return ret_value;
    }

    public Player getPlayer(String number) {
        for (Player player : allPlayers) {
            if (player.getNumber().equals(number))
                return player;
        }
        return new Player();
    }

    public void addRemoved(Player player_removed) {
        removed.add(player_removed);
    }

    public void addEntered(Player player_entered) {
        entered.add(player_entered);
    }

    public ArrayList<Player> getRemoved() {
        return removed;
    }

    public ArrayList<Player> getEntered() {
        return entered;
    }

    public ArrayList<Player> getBasicPlayers() {
        ArrayList<Player> basic = new ArrayList<>();
        for (Player player : allPlayers) {
            if (player.isBasic())
                basic.add(player);
        }
        return basic;
    }

    public ArrayList<Player> getReplacementPlayers() {
        ArrayList<Player> basic = new ArrayList<>();
        for (Player player : allPlayers) {
            if (player.isReplacement())
                basic.add(player);
        }
        return basic;
    }

    public String[] getPlayerNumberArray() {
        String[] numbers = new String[allPlayers.size()];
        for (int i = 0; i < numbers.length; ++i) {
            numbers[i] = allPlayers.get(i).getNumber();
        }
        return numbers;
    }

}