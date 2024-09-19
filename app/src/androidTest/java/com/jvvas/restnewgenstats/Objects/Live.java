package com.jvvas.restnewgenstats.Objects;

public class Live {


    String block = "0#0";
    String offside = "0#0";
    String cornerLeft = "0#0";
    String cornerRight = "0#0";
    String faoulKata = "0#0";
    String faoulYper = "0#0";
    String klepsimo = "0#0";
    String lathos = "0#0";
    String yellowCard = "0#0";
    String redCard = "0#0";
    String gemismaIn = "0#0" ;
    String gemismaOut = "0#0" ;
    String apokrousi = "0#0" ;
    String epemvasi = "0#0" ;
    String dokari = "0#0";

    String penalty = "0#0"; // einai gia to fragment PENALTY
    //Telikes
    String goal = "0#0";
    String shootIn = "0#0";
    String shootOut = "0#0";
    String kefaliaIn = "0#0";
    String kefaliaOut = "0#0" ;
    String apoFaoulIn = "0#0" ;
    String apoFaoulOut = "0#0" ;
    String apoKornerIn = "0#0" ;
    String apoKornerOut = "0#0" ;
    String xamenoPenaltyIn = "0#0";
    String xamenoPenaltyOut = "0#0";
    String assist = "0#0";

    String kopsimo = "0#0";
    String dieisdusiIn = "0#0";
    String dieisdusiOut = "0#0";
    String xameniEukairia = "0#0";
    String extraTimeScore = "0#0";
    String penaltyScore = "0#0";

    String forwardingPass = "0#0";
    String pCenterTime = "0#0";
    String pLeftTime = "0#0";
    String pRightTime = "0#0";
    String pTotalTime = "0#0";


    public Live() {  }

    //Method that takes a string formatted like x#y and returns one x'#y' when according to
    //isTeamA we changed x or y and according to increase we increased it or decreased it by 1
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
                retValue = apoFaoulIn = increaseOrDecrease(
                        apoFaoulIn.split("#"),isTeamA, increase);
                break;
            case "apoFaoulOut":
                retValue = apoFaoulOut = increaseOrDecrease(
                        apoFaoulOut.split("#"),isTeamA, increase);
                break;
            case "apoKornerIn":
                retValue = apoKornerIn = increaseOrDecrease(
                        apoKornerIn.split("#"),isTeamA, increase);
                break;
            case "apoKornerOut":
                retValue = apoKornerOut = increaseOrDecrease(
                        apoKornerOut.split("#"),isTeamA, increase);
                break;
            case "apokrousi":
                retValue = apokrousi = increaseOrDecrease(
                        apokrousi.split("#"),isTeamA, increase);
                break;
            case "assist":
                retValue = assist = increaseOrDecrease(
                        assist.split("#"),isTeamA, increase);
                break;
            case "block":
                retValue = block = increaseOrDecrease(
                        block.split("#"),isTeamA, increase);
                break;
            case "cornerLeft":
                retValue = cornerLeft = increaseOrDecrease(
                        cornerLeft.split("#"),isTeamA, increase);
                break;
            case "cornerRight":
                retValue = cornerRight = increaseOrDecrease(
                        cornerRight.split("#"),isTeamA, increase);
                break;
            case "dieisdusiIn":
                retValue = dieisdusiIn = increaseOrDecrease(
                        dieisdusiIn.split("#"),isTeamA, increase);
                break;
            case "dieisdusiOut":
                retValue = dieisdusiOut = increaseOrDecrease(
                        dieisdusiOut.split("#"),isTeamA, increase);
                break;
            case "dokari":
                retValue = dokari = increaseOrDecrease(
                        dokari.split("#"),isTeamA, increase);
                break;
            case "epemvasi":
                retValue = epemvasi = increaseOrDecrease(
                        epemvasi.split("#"),isTeamA, increase);
                break;
            case "extraTimeScore":
                retValue = extraTimeScore = increaseOrDecrease(
                        extraTimeScore.split("#"),isTeamA, increase);
                break;
            case "faoulKata":
                retValue = faoulKata = increaseOrDecrease(
                        faoulKata.split("#"),isTeamA, increase);
                break;
            case "faoulYper":
                retValue = faoulYper = increaseOrDecrease(
                        faoulYper.split("#"),isTeamA, increase);
                break;
            case "forwardingPass":
                retValue = forwardingPass = increaseOrDecrease(
                        forwardingPass.split("#"),isTeamA, increase);
                break;
            case "gemismaIn":
                retValue = gemismaIn = increaseOrDecrease(
                        gemismaIn.split("#"),isTeamA, increase);
                break;
            case "gemismaOut":
                retValue = gemismaOut = increaseOrDecrease(
                        gemismaOut.split("#"),isTeamA, increase);
                break;
            case "goal":
                retValue = goal = increaseOrDecrease(
                        goal.split("#"),isTeamA, increase);
                break;
            case "kefaliaIn":
                retValue = kefaliaIn = increaseOrDecrease(
                        kefaliaIn.split("#"),isTeamA, increase);
                break;
            case "kefaliaOut":
                retValue = kefaliaOut = increaseOrDecrease(
                        kefaliaOut.split("#"),isTeamA, increase);
                break;
            case "klepsimo":
                retValue = klepsimo = increaseOrDecrease(
                        klepsimo.split("#"),isTeamA, increase);
                break;
            case "kopsimo":
                retValue = kopsimo = increaseOrDecrease(
                        kopsimo.split("#"),isTeamA, increase);
                break;
            case "lathos":
                retValue = lathos = increaseOrDecrease(
                        lathos.split("#"),isTeamA, increase);
                break;
            case "offside":
                retValue = offside = increaseOrDecrease(
                        offside.split("#"),isTeamA, increase);
                break;
            case "penalty":
                retValue = penalty = increaseOrDecrease(
                        penalty.split("#"),isTeamA, increase);
                break;
            case "penaltyScore":
                retValue = penaltyScore = increaseOrDecrease(
                        penaltyScore.split("#"),isTeamA, increase);
                break;
            case "redCard":
                retValue = redCard = increaseOrDecrease(
                        redCard.split("#"),isTeamA, increase);
                break;
            case "shootIn":
                retValue = shootIn = increaseOrDecrease(
                        shootIn.split("#"),isTeamA, increase);
                break;
            case "shootOut":
                retValue = shootOut = increaseOrDecrease(
                        shootOut.split("#"),isTeamA, increase);
                break;
            case "xameniEukairia":
                retValue = xameniEukairia = increaseOrDecrease(
                        xameniEukairia.split("#"),isTeamA, increase);
                break;
            case "xamenoPenaltyIn":
                retValue = xamenoPenaltyIn = increaseOrDecrease(
                        xamenoPenaltyIn.split("#"),isTeamA, increase);
                break;
            case "xamenoPenaltyOut":
                retValue = xamenoPenaltyOut = increaseOrDecrease(
                        xamenoPenaltyOut.split("#"),isTeamA, increase);
                break;
            case "yellowCard":
                retValue = yellowCard = increaseOrDecrease(
                        yellowCard.split("#"),isTeamA, increase);
                break;
        }
        return retValue;
    }



    public String updateRedCards(int cards, String team)
    {
        String[] splittedElement = redCard.split("#");
        if(team.equals("A"))
        {
            redCard = Integer.toString(cards)+"#"+splittedElement[1];
        }
        else
        {
            redCard = splittedElement[0]+"#"+Integer.toString(cards);
        }
        return redCard;
    }

    public String updateYellowCards(int cards, String team)
    {
        String []splittedElement = yellowCard.split("#");
        if(team.equals("A"))
        {
            yellowCard = Integer.toString(cards)+"#"+splittedElement[1];
        }
        else
        {
            yellowCard = splittedElement[0]+"#"+Integer.toString(cards);
        }
        return yellowCard;
    }


    // ------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER and SETTER
    // ------------------------------------------------------------------------------------------------------------------------------------------------------

    public String getXamenoPenaltyIn() {
        return xamenoPenaltyIn;
    }

    public void setXamenoPenaltyIn(String xamenoPenaltyIn) {
        this.xamenoPenaltyIn = xamenoPenaltyIn;
    }

    public String getXamenoPenaltyOut() {
        return xamenoPenaltyOut;
    }

    public void setXamenoPenaltyOut(String xamenoPenaltyOut) {
        this.xamenoPenaltyOut = xamenoPenaltyOut;
    }

    public String getApokrousi() {
        return apokrousi;
    }

    public void setApokrousi(String apokrousi) {
        this.apokrousi = apokrousi;
    }

    public String getEpemvasi() {
        return epemvasi;
    }

    public void setEpemvasi(String epemvasi) {
        this.epemvasi = epemvasi;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setCornerLeft(String cornerLeft) {
        this.cornerLeft = cornerLeft;
    }

    public void setCornerRight(String cornerRight) {
        this.cornerRight = cornerRight;
    }

    public void setOffside(String offside) {
        this.offside = offside;
    }

    public void setShootIn(String shootIn) {
        this.shootIn = shootIn;
    }

    public void setShootOut(String shootOut) {
        this.shootOut = shootOut;
    }

    public void setKlepsimo(String klepsimo) {
        this.klepsimo = klepsimo;
    }

    public void setLathos(String lathos) {
        this.lathos = lathos;
    }

    public void setYellowCard(String yellowCard) {
        this.yellowCard = yellowCard;
    }

    public void setRedCard(String redCard) {
        this.redCard = redCard;
    }

    public String getGoal() {
        return goal;
    }

    public String getBlock() {
        return block;
    }

    public String getCornerLeft() {
        return cornerLeft;
    }

    public String getCornerRight() {
        return cornerRight;
    }

    public String getOffside() {
        return offside;
    }

    public String getShootIn() {
        return shootIn;
    }

    public String getShootOut() {
        return shootOut;
    }

    public String getKlepsimo() {
        return klepsimo;
    }

    public String getLathos() {
        return lathos;
    }

    public String getYellowCard() {
        return yellowCard;
    }

    public String getRedCard() {
        return redCard;
    }

    public String getKefaliaIn() {
        return kefaliaIn;
    }

    public void setKefaliaIn(String kefaliaIn) {
        this.kefaliaIn = kefaliaIn;
    }

    public String getKefaliaOut() {
        return kefaliaOut;
    }

    public void setKefaliaOut(String kefaliaOut) {
        this.kefaliaOut = kefaliaOut;
    }

    public String getApoFaoulIn() {
        return apoFaoulIn;
    }

    public void setApoFaoulIn(String apoFaoulIn) {
        this.apoFaoulIn = apoFaoulIn;
    }

    public String getApoFaoulOut() {
        return apoFaoulOut;
    }

    public void setApoFaoulOut(String apoFaoulOut) {
        this.apoFaoulOut = apoFaoulOut;
    }

    public String getApoKornerIn() {
        return apoKornerIn;
    }

    public void setApoKornerIn(String apoKornerIn) {
        this.apoKornerIn = apoKornerIn;
    }

    public String getApoKornerOut() {
        return apoKornerOut;
    }

    public void setApoKornerOut(String apoKornerOut) {
        this.apoKornerOut = apoKornerOut;
    }

    public String getGemismaIn() {
        return gemismaIn;
    }

    public void setGemismaIn(String gemismaIn) {
        this.gemismaIn = gemismaIn;
    }

    public String getGemismaOut() {
        return gemismaOut;
    }

    public void setGemismaOut(String gemismaOut) {
        this.gemismaOut = gemismaOut;
    }

    public String getFaoulKata() {
        return faoulKata;
    }

    public void setFaoulKata(String faoulKata) {
        this.faoulKata = faoulKata;
    }

    public String getFaoulYper() {
        return faoulYper;
    }

    public void setFaoulYper(String faoulYper) {
        this.faoulYper = faoulYper;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getDokari()  { return dokari; }

    public void setDokari(String dokari) { this.dokari = dokari; }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getExtraTimeScore() {
        return extraTimeScore;
    }

    public void setExtraTimeScore(String extraTimeScore) {
        this.extraTimeScore = extraTimeScore;
    }

    public String getPenaltyScore() {
        return penaltyScore;
    }

    public void setPenaltyScore(String penaltyScore) {
        this.penaltyScore = penaltyScore;
    }

    public String getXameniEukairia() {
        return xameniEukairia;
    }

    public void setXameniEukairia(String xameniEukairia) {
        this.xameniEukairia = xameniEukairia;
    }

    public String getKopsimo() {
        return kopsimo;
    }

    public void setKopsimo(String kopsimo) {
        this.kopsimo = kopsimo;
    }

    public String getDieisdusiIn() {
        return dieisdusiIn;
    }

    public void setDieisdusiIn(String dieisdusiIn) {
        this.dieisdusiIn = dieisdusiIn;
    }

    public String getDieisdusiOut() {
        return dieisdusiOut;
    }

    public void setDieisdusiOut(String dieisdusiOut) {
        this.dieisdusiOut = dieisdusiOut;
    }

    public String getForwardingPass() {
        return forwardingPass;
    }

    public void setForwardingPass(String forwardingPass) {
        this.forwardingPass = forwardingPass;
    }

    public String getpCenterTime() {
        return pCenterTime;
    }

    public void setpCenterTime(String pCenterTime) {
        this.pCenterTime = pCenterTime;
    }

    public String getpLeftTime() {
        return pLeftTime;
    }

    public void setpLeftTime(String pLeftTime) {
        this.pLeftTime = pLeftTime;
    }

    public String getpRightTime() {
        return pRightTime;
    }

    public void setpRightTime(String pRightTime) {
        this.pRightTime = pRightTime;
    }

    public String getpTotalTime() {
        return pTotalTime;
    }

    public void setpTotalTime(String pTotalTime) {
        this.pTotalTime = pTotalTime;
    }
}
