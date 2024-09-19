package com.jvvas.restnewgenstats.Objects;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.LiveDTO;

public class LiveUpdater {

    private LiveDTO liveDTO;

    public LiveUpdater(LiveDTO liveDTO){
        this.liveDTO = liveDTO;
    }


    private String increaseOrDecrease(String[] splittedElement, boolean isTeamA, boolean increase)
    {
        int number = Integer.parseInt(splittedElement[isTeamA ? 0 : 1]);
        return isTeamA ? (increase?++number:--number) + "#" + splittedElement[1] :
                splittedElement[0] + "#" + (increase?++number:--number);
    }

    //Method that increases or decreased a stat for on team
    public String increaseOrDecreaseStat(String stat, boolean isTeamA, boolean increase)
    {
        String retValue = null;
        switch (stat)
        {
            case "apoFaoulIn":
                retValue = increaseOrDecrease(liveDTO.getApoFaoulIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setApoFaoulIn(retValue);
                break;
            case "apoFaoulOut":
                retValue = increaseOrDecrease(liveDTO.getApoFaoulOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setApoFaoulOut(retValue);
                break;
            case "apoKornerIn":
                retValue = increaseOrDecrease(liveDTO.getApoKornerIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setApoKornerIn(retValue);
                break;
            case "apoKornerOut":
                retValue = increaseOrDecrease(liveDTO.getApoKornerOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setApoKornerOut(retValue);
                break;
            case "apokrousi":
                retValue = increaseOrDecrease(liveDTO.getApokrousi()
                        .split("#"),isTeamA, increase);
                liveDTO.setApokrousi(retValue);
                break;
            case "assist":
                retValue = increaseOrDecrease(liveDTO.getAssist()
                        .split("#"),isTeamA, increase);
                liveDTO.setAssist(retValue);
                break;
            case "block":
                retValue = increaseOrDecrease(liveDTO.getBlock()
                        .split("#"),isTeamA, increase);
                liveDTO.setBlock(retValue);
                break;
            case "cornerLeft":
                retValue = increaseOrDecrease(liveDTO.getCornerLeft()
                        .split("#"),isTeamA, increase);
                liveDTO.setCornerLeft(retValue);
                break;
            case "cornerRight":
                retValue = increaseOrDecrease(liveDTO.getCornerRight()
                        .split("#"),isTeamA, increase);
                liveDTO.setCornerRight(retValue);
                break;
            case "dieisdusiIn":
                retValue = increaseOrDecrease(liveDTO.getDieisdusiIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setDieisdusiIn(retValue);
                break;
            case "dieisdusiOut":
                retValue = increaseOrDecrease(liveDTO.getDieisdusiOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setDieisdusiOut(retValue);
                break;
            case "dokari":
                retValue = increaseOrDecrease(liveDTO.getDokari()
                        .split("#"),isTeamA, increase);
                liveDTO.setDokari(retValue);
                break;
            case "epemvasi":
                retValue = increaseOrDecrease(liveDTO.getEpemvasi()
                        .split("#"),isTeamA, increase);
                liveDTO.setEpemvasi(retValue);
                break;
            case "extraTimeScore":
                retValue = increaseOrDecrease(liveDTO.getExtraTimeScore()
                        .split("#"),isTeamA, increase);
                liveDTO.setExtraTimeScore(retValue);
                break;
            case "faoulKata":
                retValue = increaseOrDecrease(liveDTO.getFaoulKata()
                        .split("#"),isTeamA, increase);
                liveDTO.setFaoulKata(retValue);
                break;
            case "faoulYper":
                retValue = increaseOrDecrease(liveDTO.getFaoulYper()
                        .split("#"),isTeamA, increase);
                liveDTO.setFaoulYper(retValue);
                break;
            case "forwardingPass":
                retValue = increaseOrDecrease(liveDTO.getForwardingPass()
                        .split("#"),isTeamA, increase);
                liveDTO.setForwardingPass(retValue);
                break;
            case "gemismaIn":
                retValue = increaseOrDecrease(liveDTO.getGemismaIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setGemismaIn(retValue);
                break;
            case "gemismaOut":
                retValue = increaseOrDecrease(liveDTO.getGemismaOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setGemismaOut(retValue);
                break;
            case "goal":
                retValue = increaseOrDecrease(liveDTO.getGoal()
                        .split("#"),isTeamA, increase);
                liveDTO.setGoal(retValue);
                break;
            case "kefaliaIn":
                retValue = increaseOrDecrease(liveDTO.getKefaliaIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setKefaliaIn(retValue);
                break;
            case "kefaliaOut":
                retValue = increaseOrDecrease(liveDTO.getKefaliaOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setKefaliaOut(retValue);
                break;
            case "klepsimo":
                retValue = increaseOrDecrease(liveDTO.getKlepsimo()
                        .split("#"),isTeamA, increase);
                liveDTO.setKlepsimo(retValue);
                break;
            case "kopsimo":
                retValue = increaseOrDecrease(liveDTO.getKopsimo()
                        .split("#"),isTeamA, increase);
                liveDTO.setKopsimo(retValue);
                break;
            case "lathos":
                retValue = increaseOrDecrease(liveDTO.getLathos()
                        .split("#"),isTeamA, increase);
                liveDTO.setLathos(retValue);
                break;
            case "offside":
                retValue = increaseOrDecrease(liveDTO.getOffside()
                        .split("#"),isTeamA, increase);
                liveDTO.setOffside(retValue);
                break;
            case "penalty":
                retValue = increaseOrDecrease(liveDTO.getPenalty()
                        .split("#"),isTeamA, increase);
                liveDTO.setPenalty(retValue);
                break;
            case "penaltyScore":
                retValue = increaseOrDecrease(liveDTO.getPenaltyScore()
                        .split("#"),isTeamA, increase);
                liveDTO.setPenaltyScore(retValue);
                break;
            case "redCard":
                retValue = increaseOrDecrease(liveDTO.getRedCard()
                        .split("#"),isTeamA, increase);
                liveDTO.setRedCard(retValue);
                break;
            case "shootIn":
                retValue = increaseOrDecrease(liveDTO.getShootIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setShootIn(retValue);
                break;
            case "shootOut":
                retValue = increaseOrDecrease(liveDTO.getShootOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setShootOut(retValue);
                break;
            case "xameniEukairia":
                retValue = increaseOrDecrease(liveDTO.getXameniEukairia()
                        .split("#"),isTeamA, increase);
                liveDTO.setXameniEukairia(retValue);
                break;
            case "xamenoPenaltyIn":
                retValue = increaseOrDecrease(liveDTO.getXamenoPenaltyIn()
                        .split("#"),isTeamA, increase);
                liveDTO.setXamenoPenaltyIn(retValue);
                break;
            case "xamenoPenaltyOut":
                retValue = increaseOrDecrease(liveDTO.getXamenoPenaltyOut()
                        .split("#"),isTeamA, increase);
                liveDTO.setXamenoPenaltyOut(retValue);
                break;
            case "yellowCard":
                retValue = increaseOrDecrease(liveDTO.getYellowCard()
                        .split("#"),isTeamA, increase);
                liveDTO.setYellowCard(retValue);
                break;
        }
        return retValue;
    }

    public String updateRedCards(int cards, String team) {
        String[] splittedElement = liveDTO.getRedCard().split("#");
        if(team.equals("A"))
            liveDTO.setRedCard(cards+"#"+splittedElement[1]);
        else
            liveDTO.setRedCard(splittedElement[0]+"#"+cards);
        return liveDTO.getRedCard();
    }

    public String updateYellowCards(int cards, String team) {
        String[] splittedElement = liveDTO.getYellowCard().split("#");
        if (team.equals("A"))
            liveDTO.setYellowCard(cards + "#" + splittedElement[1]);
         else
            liveDTO.setYellowCard(splittedElement[0] + "#" + cards);
        return liveDTO.getYellowCard();
    }

}
