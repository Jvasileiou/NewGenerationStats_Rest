package com.jvvas.restnewgenstats.JavaDataTransferObjects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveDTO implements Serializable {

    @SerializedName("goal")
    private String goal;

    @SerializedName("shootIn")
    private String shootIn;

    @SerializedName("offside")
    private String offside;

    @SerializedName("faoulKata")
    private String faoulKata;

    @SerializedName("dieisdusiIn")
    private String dieisdusiIn;

    @SerializedName("kopsimo")
    private String kopsimo;

    @SerializedName("xamenoPenaltyIn")
    private String xamenoPenaltyIn;

    @SerializedName("shootOut")
    private String shootOut;

    @SerializedName("klepsimo")
    private String klepsimo;

    @SerializedName("yellowCard")
    private String yellowCard;

    @SerializedName("dieisdusiOut")
    private String dieisdusiOut;

    @SerializedName("penaltyScore")
    private String penaltyScore;

    @SerializedName("penalty")
    private String penalty;

    @SerializedName("apoFaoulOut")
    private String apoFaoulOut;

    @SerializedName("cornerRight")
    private String cornerRight;

    @SerializedName("block")
    private String block;

    @SerializedName("gemismaOut")
    private String gemismaOut;

    @SerializedName("apoKornerIn")
    private String apoKornerIn;

    @SerializedName("apokrousi")
    private String apokrousi;

    @SerializedName("xameniEukairia")
    private String xameniEukairia;

    @SerializedName("kefaliaOut")
    private String kefaliaOut;

    @SerializedName("apoKornerOut")
    private String apoKornerOut;

    @SerializedName("kefaliaIn")
    private String kefaliaIn;

    @SerializedName("extraTimeScore")
    private String extraTimeScore;

    @SerializedName("cornerLeft")
    private String cornerLeft;

    @SerializedName("gemismaIn")
    private String gemismaIn;

    @SerializedName("xamenoPenaltyOut")
    private String xamenoPenaltyOut;

    @SerializedName("apoFaoulIn")
    private String apoFaoulIn;

    @SerializedName("assist")
    private String assist;

    @SerializedName("faoulYper")
    private String faoulYper;

    @SerializedName("redCard")
    private String redCard;

    @SerializedName("epemvasi")
    private String epemvasi;

    @SerializedName("dokari")
    private String dokari;

    @SerializedName("lathos")
    private String lathos;

    @SerializedName("forwardingPass")
    private String forwardingPass;



    public LiveDTO()
    {
        this.goal = "0#0";
        this.shootIn = "0#0";
        this.offside = "0#0";
        this.faoulKata = "0#0";
        this.dieisdusiIn = "0#0";
        this.kopsimo = "0#0";
        this.xamenoPenaltyIn = "0#0";
        this.shootOut = "0#0";
        this.klepsimo = "0#0";
        this.yellowCard = "0#0";
        this.dieisdusiOut = "0#0";
        this.penaltyScore = "0#0";
        this.penalty = "0#0";
        this.apoFaoulOut = "0#0";
        this.cornerRight = "0#0";
        this.block = "0#0";
        this.gemismaOut = "0#0";
        this.apoKornerIn = "0#0";
        this.apokrousi = "0#0";
        this.xameniEukairia = "0#0";
        this.kefaliaOut = "0#0";
        this.apoKornerOut = "0#0";
        this.kefaliaIn = "0#0";
        this.extraTimeScore = "0#0";
        this.cornerLeft = "0#0";
        this.gemismaIn = "0#0";
        this.xamenoPenaltyOut = "0#0";
        this.apoFaoulIn = "0#0";
        this.assist = "0#0";
        this.faoulYper = "0#0";
        this.redCard = "0#0";
        this.epemvasi = "0#0";
        this.dokari = "0#0";
        this.lathos = "0#0";
        this.forwardingPass = "0#0";
    }


    public LiveDTO(String goal, String shootIn, String offside, String faoulKata, String dieisdusiIn,
                   String kopsimo, String xamenoPenaltyIn, String shootOut, String klepsimo,
                   String yellowCard, String dieisdusiOut, String penaltyScore, String penalty,
                   String apoFaoulOut, String cornerRight, String block, String gemismaOut,
                   String apoKornerIn, String apokrousi, String xameniEukairia, String kefaliaOut,
                   String apoKornerOut, String kefaliaIn, String extraTimeScore, String cornerLeft,
                   String gemismaIn, String xamenoPenaltyOut, String apoFaoulIn, String assist,
                   String faoulYper, String redCard, String epemvasi, String dokari, String lathos,
                   String forwardingPass)
    {
        this.goal = goal;
        this.shootIn = shootIn;
        this.offside = offside;
        this.faoulKata = faoulKata;
        this.dieisdusiIn = dieisdusiIn;
        this.kopsimo = kopsimo;
        this.xamenoPenaltyIn = xamenoPenaltyIn;
        this.shootOut = shootOut;
        this.klepsimo = klepsimo;
        this.yellowCard = yellowCard;
        this.dieisdusiOut = dieisdusiOut;
        this.penaltyScore = penaltyScore;
        this.penalty = penalty;
        this.apoFaoulOut = apoFaoulOut;
        this.cornerRight = cornerRight;
        this.block = block;
        this.gemismaOut = gemismaOut;
        this.apoKornerIn = apoKornerIn;
        this.apokrousi = apokrousi;
        this.xameniEukairia = xameniEukairia;
        this.kefaliaOut = kefaliaOut;
        this.apoKornerOut = apoKornerOut;
        this.kefaliaIn = kefaliaIn;
        this.extraTimeScore = extraTimeScore;
        this.cornerLeft = cornerLeft;
        this.gemismaIn = gemismaIn;
        this.xamenoPenaltyOut = xamenoPenaltyOut;
        this.apoFaoulIn = apoFaoulIn;
        this.assist = assist;
        this.faoulYper = faoulYper;
        this.redCard = redCard;
        this.epemvasi = epemvasi;
        this.dokari = dokari;
        this.lathos = lathos;
        this.forwardingPass = forwardingPass;
    }


    public String getGoal() {
        return goal;
    }

    public String getShootIn() {
        return shootIn;
    }

    public String getOffside() {
        return offside;
    }

    public String getFaoulKata() {
        return faoulKata;
    }

    public String getDieisdusiIn() {
        return dieisdusiIn;
    }

    public String getKopsimo() {
        return kopsimo;
    }

    public String getXamenoPenaltyIn() {
        return xamenoPenaltyIn;
    }

    public String getShootOut() {
        return shootOut;
    }

    public String getKlepsimo() {
        return klepsimo;
    }

    public String getYellowCard() {
        return yellowCard;
    }

    public String getDieisdusiOut() {
        return dieisdusiOut;
    }

    public String getPenaltyScore() {
        return penaltyScore;
    }

    public String getPenalty() {
        return penalty;
    }

    public String getApoFaoulOut() {
        return apoFaoulOut;
    }

    public String getCornerRight() {
        return cornerRight;
    }

    public String getBlock() {
        return block;
    }

    public String getGemismaOut() {
        return gemismaOut;
    }

    public String getApoKornerIn() {
        return apoKornerIn;
    }

    public String getApokrousi() {
        return apokrousi;
    }

    public String getXameniEukairia() {
        return xameniEukairia;
    }

    public String getKefaliaOut() {
        return kefaliaOut;
    }

    public String getApoKornerOut() {
        return apoKornerOut;
    }

    public String getKefaliaIn() {
        return kefaliaIn;
    }

    public String getExtraTimeScore() {
        return extraTimeScore;
    }

    public String getCornerLeft() {
        return cornerLeft;
    }

    public String getGemismaIn() {
        return gemismaIn;
    }

    public String getXamenoPenaltyOut() {
        return xamenoPenaltyOut;
    }

    public String getApoFaoulIn() {
        return apoFaoulIn;
    }

    public String getAssist() {
        return assist;
    }

    public String getFaoulYper() {
        return faoulYper;
    }

    public String getRedCard() {
        return redCard;
    }

    public String getEpemvasi() {
        return epemvasi;
    }

    public String getDokari() {
        return dokari;
    }

    public String getLathos() {
        return lathos;
    }

    public String getForwardingPass(){
        return forwardingPass;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setShootIn(String shootIn) {
        this.shootIn = shootIn;
    }

    public void setOffside(String offside) {
        this.offside = offside;
    }

    public void setFaoulKata(String faoulKata) {
        this.faoulKata = faoulKata;
    }

    public void setDieisdusiIn(String dieisdusiIn) {
        this.dieisdusiIn = dieisdusiIn;
    }

    public void setKopsimo(String kopsimo) {
        this.kopsimo = kopsimo;
    }

    public void setXamenoPenaltyIn(String xamenoPenaltyIn) {
        this.xamenoPenaltyIn = xamenoPenaltyIn;
    }

    public void setShootOut(String shootOut) {
        this.shootOut = shootOut;
    }

    public void setKlepsimo(String klepsimo) {
        this.klepsimo = klepsimo;
    }

    public void setYellowCard(String yellowCard) {
        this.yellowCard = yellowCard;
    }

    public void setDieisdusiOut(String dieisdusiOut) {
        this.dieisdusiOut = dieisdusiOut;
    }

    public void setPenaltyScore(String penaltyScore) {
        this.penaltyScore = penaltyScore;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public void setApoFaoulOut(String apoFaoulOut) {
        this.apoFaoulOut = apoFaoulOut;
    }

    public void setCornerRight(String cornerRight) {
        this.cornerRight = cornerRight;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setGemismaOut(String gemismaOut) {
        this.gemismaOut = gemismaOut;
    }

    public void setApoKornerIn(String apoKornerIn) {
        this.apoKornerIn = apoKornerIn;
    }

    public void setApokrousi(String apokrousi) {
        this.apokrousi = apokrousi;
    }

    public void setXameniEukairia(String xameniEukairia) {
        this.xameniEukairia = xameniEukairia;
    }

    public void setKefaliaOut(String kefaliaOut) {
        this.kefaliaOut = kefaliaOut;
    }

    public void setApoKornerOut(String apoKornerOut) {
        this.apoKornerOut = apoKornerOut;
    }

    public void setKefaliaIn(String kefaliaIn) {
        this.kefaliaIn = kefaliaIn;
    }

    public void setExtraTimeScore(String extraTimeScore) {
        this.extraTimeScore = extraTimeScore;
    }

    public void setCornerLeft(String cornerLeft) {
        this.cornerLeft = cornerLeft;
    }

    public void setGemismaIn(String gemismaIn) {
        this.gemismaIn = gemismaIn;
    }

    public void setXamenoPenaltyOut(String xamenoPenaltyOut) {
        this.xamenoPenaltyOut = xamenoPenaltyOut;
    }

    public void setApoFaoulIn(String apoFaoulIn) {
        this.apoFaoulIn = apoFaoulIn;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public void setFaoulYper(String faoulYper) {
        this.faoulYper = faoulYper;
    }

    public void setRedCard(String redCard) {
        this.redCard = redCard;
    }

    public void setEpemvasi(String epemvasi) {
        this.epemvasi = epemvasi;
    }

    public void setDokari(String dokari) {
        this.dokari = dokari;
    }

    public void setLathos(String lathos) {
        this.lathos = lathos;
    }

    public void setForwardingPass(String forwardingPass) {
        this.forwardingPass = forwardingPass;
    }

    @Override
    public String toString() {
        return "LiveDTO{" +
                "goal='" + goal + '\'' +
                ", shootIn='" + shootIn + '\'' +
                ", offside='" + offside + '\'' +
                ", faoulKata='" + faoulKata + '\'' +
                ", dieisdusiIn='" + dieisdusiIn + '\'' +
                ", kopsimo='" + kopsimo + '\'' +
                ", xamenoPenaltyIn='" + xamenoPenaltyIn + '\'' +
                ", shootOut='" + shootOut + '\'' +
                ", klepsimo='" + klepsimo + '\'' +
                ", yellowCard='" + yellowCard + '\'' +
                ", dieisdusiOut='" + dieisdusiOut + '\'' +
                ", penaltyScore='" + penaltyScore + '\'' +
                ", penalty='" + penalty + '\'' +
                ", apoFaoulOut='" + apoFaoulOut + '\'' +
                ", cornerRight='" + cornerRight + '\'' +
                ", block='" + block + '\'' +
                ", gemismaOut='" + gemismaOut + '\'' +
                ", apoKornerIn='" + apoKornerIn + '\'' +
                ", apokrousi='" + apokrousi + '\'' +
                ", xameniEukairia='" + xameniEukairia + '\'' +
                ", kefaliaOut='" + kefaliaOut + '\'' +
                ", apoKornerOut='" + apoKornerOut + '\'' +
                ", kefaliaIn='" + kefaliaIn + '\'' +
                ", extraTimeScore='" + extraTimeScore + '\'' +
                ", cornerLeft='" + cornerLeft + '\'' +
                ", gemismaIn='" + gemismaIn + '\'' +
                ", xamenoPenaltyOut='" + xamenoPenaltyOut + '\'' +
                ", apoFaoulIn='" + apoFaoulIn + '\'' +
                ", assist='" + assist + '\'' +
                ", faoulYper='" + faoulYper + '\'' +
                ", redCard='" + redCard + '\'' +
                ", epemvasi='" + epemvasi + '\'' +
                ", dokari='" + dokari + '\'' +
                ", lathos='" + lathos + '\'' +
                ", forwardingPass='" + forwardingPass + '\'' +
                '}';
    }
}
