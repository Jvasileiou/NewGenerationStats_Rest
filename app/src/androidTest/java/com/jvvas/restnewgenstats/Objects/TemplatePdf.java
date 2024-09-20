package com.jvvas.restnewgenstats.Objects;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.jvvas.restnewgenstats.Activities.CountStatsActivity;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class TemplatePdf {

    private static String IMG_CARDS = "";
    private static String IMG_CARD_YELLOW = "";
    private static String IMG_CARD_RED = "";
    private static String IMG_ATTACK_TEAM_A = "";
    private static String IMG_ATTACK_TEAM_B = "";

    int counterForPlayerWhoPlayedTeamA = 0, counterForPlayerWhoPlayedTeamB = 0;

    private ArrayList<DataLiveActions> eventsList ;
    private CountStatsActivity.BasicPlayerAdapter teamAdapter1,  teamAdapter2;
    private Team teamA, teamB;
    private String coachA, coachB;

    private int numberOfPlayersTeamA,numberOfPlayersTeamB;
    private ArrayList<Player> allPlayersOfTeamA = new ArrayList<>() ;
    private ArrayList<Player> allPlayersOfTeamB = new ArrayList<>() ;
    private int[][] arrayDataTeamIntA, arrayDataTeamIntB ;
    private String[] statsNameForMiddleTotalStats = new String[22];
    private int[][] totalDataForBothTeams = new int[23][2]; // Το τελευταιο , δλδ 22 κραταω τις ευστοχες Διεισδυσεις ΜΟΝΟ
    private String[] arrayDataOfNamesStringTeamA, arrayDataOfNamesStringTeamB;

    private int[][] arrayPositionsOfSynolikesTelikesTeamA = new int [4][5];
    private int[][] arrayPositionsOfTelikesEntosTeamA = new int [4][5];
    private int[][] arrayPositionsOfSynolikaGemismataTeamA = new int [4][5];
    private int[][] arrayPositionsOfEustoxaGemismataTeamA = new int [4][5];

    private int[][] arrayPositionsOfSynolikesTelikesTeamB = new int [4][5];
    private int[][] arrayPositionsOfTelikesEntosTeamB = new int [4][5];
    private int[][] arrayPositionsOfSynolikaGemismataTeamB = new int [4][5];
    private int[][] arrayPositionsOfEustoxaGemismataTeamB = new int [4][5];

//    private int[][] countersForPerformanceOfTeams = new int[6][4]; // 6 rows : 0-15 , 15 -30 ..
//    private double[] performanceTeamA = new double[6];
//    private double[] performanceTeamB = new double[6];

    private String dest = "";
    private String IMG = "";
    private Context context ;
    private PdfWriter writer;
    private PdfDocument pdf;
    private Document document;
    private File file;

    String[] possessionAttackSidesTeamA = new String[3];
    String[] possessionAttackSidesTeamB = new String[3];

    private Cell cellForFieldsA,cellForFieldsB;
    private Cell cellForSecondPageFieldsA,cellForSecondPageFieldsB;
    private Cell cellForStats, cellForPossession;
    private Table tableTelikesTeamA ,tableGemismataTeamA, tableTelikesTeamB, tableGemismataTeamB;
    private Table tableBig, tableHeaderTemplate, tableRow1Head, tableRow2Head , tableCoachA,
            tableCoachB, totalStatsMidTable, totalPossessionTable,tableRowPage1,tableRowPage2,
            tableHead;

    //    private Table performanceTableTeamA, performanceTableTeamB,
    private PdfFont normalFont;

    private String numberRacingStr, scoreStr, startTimeStr, dateStr, fieldStr, nameTeamA,
            nameTeamB, nameOrganizationStr, totalTimePossession, leftTimePossession,
            centerTimePossession, rightTimePossession;
    private Table tableAttacksNameTeam;
    private Cell cellAttacksNameTeam;

    public TemplatePdf(Context applicationContext, ArrayList<DataLiveActions> list,
                       CountStatsActivity.BasicPlayerAdapter leftAdapter,
                       CountStatsActivity.BasicPlayerAdapter rightAdapter,
                       String numberRacingStr, String startTimeStr, String dateStr, String fieldStr,
                       String nameTeamA, String nameTeamB, String nameOrganizationStr, String scoreStr,
                       String coachA,String coachB, String totalTimePossession, String leftTimePossession,
                       String centerTimePossession, String rightTimePossession)
    {
        this.context = applicationContext;
        this.eventsList = list;
        this.teamAdapter1 = leftAdapter;
        this.teamAdapter2 = rightAdapter;
        this.numberRacingStr = numberRacingStr;
        this.scoreStr = scoreStr;
        this.startTimeStr = startTimeStr;
        this.dateStr = dateStr;
        this.fieldStr = fieldStr;
        this.nameTeamA = nameTeamA;
        this.nameTeamB = nameTeamB;
        this.nameOrganizationStr = nameOrganizationStr;
        this.coachA = coachA;
        this.coachB = coachB;
        this.totalTimePossession = totalTimePossession;
        this.leftTimePossession = leftTimePossession;
        this.centerTimePossession = centerTimePossession;
        this.rightTimePossession = rightTimePossession;

        setTheRightTeam();
        //showList();
        //showAllPlayersOfTeamA();
        //showAllPlayersOfTeamB();

        initializeStatsNames();
    }

    private void initializeStatsNames() {
        statsNameForMiddleTotalStats[0] = "ΤΕΛΙΚΕΣ ΕΝΤΟΣ ΠΕΡΙΟΧΗΣ";
        statsNameForMiddleTotalStats[1] = "ΤΕΛΙΚΕΣ ΕΚΤΟΣ ΠΕΡΙΟΧΗΣ";
        statsNameForMiddleTotalStats[2] = "ΓΕΜΙΣΜΑΤΑ ΑΠΟ ΑΡΙΣΤ Α΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[3] = "ΓΕΜΙΣΜΑΤΑ ΑΠΟ ΔΕΞΙΑ Α΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[4] = "ΓΕΜΙΣΜΑΤΑ ΑΠΟ ΑΡΙΣΤ Β΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[5] = "ΓΕΜΙΣΜΑΤΑ ΑΠΟ ΔΕΞΙΑ Β΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[6] = "ΦΑΟΥΛ Α΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[7] = "ΦΑΟΥΛ Β΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[8] = "ΤΕΛΙΚΕΣ Α΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[9] = "ΤΕΛΙΚΕΣ Β΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[10] = "ΛΑΘΗ Α΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[11] = "ΛΑΘΗ Β΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[12] = "ΚΕΦΑΛΙΕΣ";
        statsNameForMiddleTotalStats[13] = "ΣΟΥΤ";
        //statsNameForMiddleTotalStats[14] = "ΦΑΟΥΛ ΣΕ ΕΠΙΚΙΝΔΥΝΗ ΘΕΣΗ";
        statsNameForMiddleTotalStats[14] = "ΓΚΟΛ ΑΠΟ ΣΤΑΤΙΚΗ ΦΑΣΗ";
        statsNameForMiddleTotalStats[15] = "ΤΑΚΛΙΝ";
        statsNameForMiddleTotalStats[16] = "ΦΑΟΥΛ ΣΕ ΕΠΙΚΙΝΔΥΝΗ ΘΕΣΗ";
        statsNameForMiddleTotalStats[17] = "ΛΑΘΟΣ ΚΟΝΤΡΟΛ";
        statsNameForMiddleTotalStats[18] = "ΛΑΘΟΣ ΠΑΣΕΣ";
//        statsNameForMiddleTotalStats[19] = "ΕΠΕΜΒΑΣΕΙΣ Α΄ ΗΜΙΧ";
//        statsNameForMiddleTotalStats[20] = "ΕΠΕΜΒΑΣΕΙΣ B΄ ΗΜΙΧ";
        statsNameForMiddleTotalStats[19] = "ΕΠΕΜΒΑΣΕΙΣ";
        statsNameForMiddleTotalStats[20] = "ΔΙΕΙΣΔΥΣΕΙΣ";
        statsNameForMiddleTotalStats[21] = "ΔΟΚΑΡΙΑ";
    }

//    private void showAllPlayersOfTeamB() {
//        System.out.println(" ΟΜΑΔΑ Β : " + teamB.getName());
//        for(Player p : teamB.getBasicPlayers()){
//            System.out.println("------>  " + p.getSurname() + " " +  p.getNumber());
//        }
//        System.out.println(" ΑΛΛΑΓΕΣ ΟΜΑΔΑ Β : " + teamB.getName());
//        for(Player p : teamB.getReplacementPlayers()){
//            System.out.println("------>  " + p.getSurname() + " " +  p.getNumber());
//        }
//        System.out.println();
//    }
//
//    private void showAllPlayersOfTeamA() {
//        System.out.println(" ΟΜΑΔΑ Α : " + teamA.getName());
//        for(Player p : teamA.getBasicPlayers()){
//            System.out.println("------>  " + p.getSurname() + " " +  p.getNumber());
//        }
//        System.out.println(" ΑΛΛΑΓΕΣ ΟΜΑΔΑ Α : " + teamA.getName());
//        for(Player p : teamA.getReplacementPlayers()){
//            System.out.println("------>  " + p.getSurname() + " " +  p.getNumber());
//        }
//        System.out.println();
//    }

    private void setTheRightTeam() {
        if(teamAdapter1.isTeamA()){
            this.teamA = teamAdapter1.getTeam();
            this.teamB = teamAdapter2.getTeam();
        }else{
            this.teamA = teamAdapter2.getTeam();
            this.teamB = teamAdapter1.getTeam();
        }

        // For A
        allPlayersOfTeamA.addAll(teamA.getBasicPlayers()) ;
        allPlayersOfTeamA.addAll(teamA.getRemoved()) ;
        counterForPlayerWhoPlayedTeamA = allPlayersOfTeamA.size();
        allPlayersOfTeamA = sortByNumberExceptGk(allPlayersOfTeamA);
        allPlayersOfTeamA.addAll(sortByNumberExceptGk(teamA.getReplacementPlayers()));
        numberOfPlayersTeamA = allPlayersOfTeamA.size();
        arrayDataTeamIntA = new int[numberOfPlayersTeamA][20];
        arrayDataOfNamesStringTeamA = new String[numberOfPlayersTeamA];
        //printA();

        // For B
        allPlayersOfTeamB.addAll(teamB.getBasicPlayers()) ;
        allPlayersOfTeamB.addAll(teamB.getRemoved()) ;
        counterForPlayerWhoPlayedTeamB = allPlayersOfTeamB.size();
        allPlayersOfTeamB = sortByNumberExceptGk(allPlayersOfTeamB);
        allPlayersOfTeamB.addAll(sortByNumberExceptGk(teamB.getReplacementPlayers()));
        numberOfPlayersTeamB = allPlayersOfTeamB.size();
        arrayDataTeamIntB = new int[numberOfPlayersTeamB][20];
        arrayDataOfNamesStringTeamB = new String[numberOfPlayersTeamB];

        addDataToArraysOfTeams();
    }

    private  ArrayList<Player> sortByNumberExceptGk(ArrayList<Player> players) {
        ArrayList<Player> sorted = new ArrayList<>();
        ArrayList<Player> goalkeepers = new ArrayList<>();

        int size = players.size();
        while (sorted.size()+goalkeepers.size()<size)
        {
            Player currPlayer = players.get(0);
            for (int i = 1; i < players.size(); i++)
            {
                if (Integer.parseInt(currPlayer.getNumber())>
                        Integer.parseInt(players.get(i).getNumber()))
                    currPlayer = players.get(i);
            }
            if (currPlayer.getPosition().equals("GK")) goalkeepers.add(currPlayer);
            else sorted.add(currPlayer);
            players.remove(currPlayer);
        }
        players = new ArrayList<>();
        players.addAll(goalkeepers);
        players.addAll(sorted);

        return players;
    }

    private void addDataToArraysOfTeams() {

        addTheNumbersOfTshirtsInFirstColumn();
        addTheNamesOfPlayersInSecondColumn();

        countStatsForTeams();
    }

    private void countStatsForTeams()
    {
        for(DataLiveActions e : eventsList){
            String strAction = e.toString();
            // System.out.println("THIS : -- > " + strAction);

            String[] value = strAction.split("[:\\|]+");
            // System.out.println("LENGTH : -- > " + value.length + " value[5] = "+ value[5].trim());
//            value[4] --> postionofField
//            value[5] --> Α Β ημιχ

            if(value.length >= 4){
                if(value[3].trim().equals("A"))
                {
                    //System.out.println("THIS : -- > " + strAction);
                    if(value.length == 7 ){
                        increaseForTeamA(Integer.parseInt(value[0].trim() ),value[1].trim(), value[2].trim(), value[4].trim(), value[5].trim() , value[6].trim());
                    }
                    else{
                        // WARNING if it is OFFSIDE or CORNER : to value[5] exei true/false oxi imixrono
                        // alla den mas epireazei gt se autes tis periptwseis den to koitame
                        increaseForTeamA(Integer.parseInt(value[0].trim() ), value[1].trim(), value[2].trim(), value[4].trim(), value[5].trim() , value[5].trim() );
                    }
                }
                else if(value[3].trim().equals("B"))
                {
                    if(value.length == 7){
                        increaseForTeamB(Integer.parseInt(value[0].trim() ), value[1].trim(), value[2].trim(), value[4].trim(), value[5].trim() , value[6].trim());
                    }else{
                        // WARNING if it is OFFSIDE or CORNER : to value[5] exei true/false oxi imixrono
                        // alla den mas epireazei gt se autes tis periptwseis den to koitame
                        increaseForTeamB(Integer.parseInt(value[0].trim() ), value[1].trim(), value[2].trim(), value[4].trim(), value[5].trim() , value[5].trim());
                    }

                }
            }
        }
//        printA();
    }

    private void increaseForTeamB(int time, String action, String number, String posOfField, String imixrono, String wordIsDefault) {
        switch (action)
        {
            // ------- ΤΕΛΙΚΕΣ ΕΝΤΟΣ -------
            case "ΣΟΥΤ ΕΝΤΟΣ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΝΤΟΣ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ":
            case "ΣΟΥΤ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBSuccess(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams[13][1] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

//
            case "ΣΟΥΤ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ ΔΟΚΑΡΙ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;

                totalDataForBothTeams[13][1] ++;
                totalDataForBothTeams[21][1] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ":
            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBSuccess(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][6] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][5] ++;
                totalDataForBothTeams[12][1] ++;
                totalDataForBothTeams[0][1] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;


            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ ΔΟΚΑΡΙ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][6] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][5] ++;
                totalDataForBothTeams[12][1] ++;
                totalDataForBothTeams[0][1] ++ ;
                totalDataForBothTeams[21][1] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            // ------- ΤΕΛΙΚΕΣ ΕΚΤΟΣ -------
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ":
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][5] ++;
                totalDataForBothTeams[12][1] ++;
                totalDataForBothTeams[0][1] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                break;


            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][5] ++;
                totalDataForBothTeams[12][1] ++;
                totalDataForBothTeams[0][1] ++ ;
                totalDataForBothTeams[21][1] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΣΟΥΤ ΕΚΤΟΣ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ":
            case "ΣΟΥΤ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams[13][1] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΣΟΥΤ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΔΟΚΑΡΙ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamBFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams[13][1] ++;
                totalDataForBothTeams[21][1] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΠΟΚΡΟΥΣΗ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][11] ++;
                break;



            // ------- ΓΕΜΙΣΜΑΤΑ -------
            case "ΕΠΙΤ ΓΕΜ":
//                increaseForPerformanceTeamBSuccess(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][8] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][7] ++;
                increaseForGemismataForB(imixrono, posOfField , wordIsDefault);
                increaseThePositionForGemismataTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForEustoxaGemismataTeamB(imixrono, posOfField, wordIsDefault);
                break;
            case "ΑΠΟΤ ΓΕΜ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][7] ++;
                increaseForGemismataForB(imixrono, posOfField , wordIsDefault);
                increaseThePositionForGemismataTeamB(imixrono, posOfField, wordIsDefault);
                break;


            case "ΕΠΕΜΒΑΣΗ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][12] ++;
                totalDataForBothTeams[19][1] ++;
//                if(imixrono.equals("Α ΗΜΙΧ")){
//                    totalDataForBothTeams[19][1] ++;
//                }else{
//                    totalDataForBothTeams[20][1] ++;
//                }
                break;

            // ------ ΔΙΕΙΣΔΥΣΗ -------

            case "ΕΠΙΤ ΔΙΕΙΣΔΥΣΗ":
                totalDataForBothTeams[20][1] ++;
                totalDataForBothTeams[22][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][18] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][19] ++;
                break;

            case "ΑΠΟΤ ΔΙΕΙΣΔΥΣΗ":
                totalDataForBothTeams[20][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][18] ++;
                break;

            // ------ KΟΨΙΜΟ -------

            case "ΚΟΨΙΜΟ":
//                System.out.println("Κοψιμο");
                break;

            // ------ ΦΑΟΥΛ (Φ-) ------
            case "ΦΑΟΥΛ ΣΩΜΑΤΙΚΗ ΕΠΑΦΗ":
            case "ΦΑΟΥΛ ΧΕΡΙ":
            case "ΦΑΟΥΛ ΤΡΑΒΗΓ ΦΑΝ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][14] ++;
                increaseFaoulOnDangerousPositionB(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][1] ++;
                }else{
                    totalDataForBothTeams[7][1] ++;
                }
                break;

            case "ΦΑΟΥΛ ΤΑΚΛΙΝ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][14] ++;
                totalDataForBothTeams[15][1] ++;
                increaseFaoulOnDangerousPositionB(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][1] ++;
                }else{
                    totalDataForBothTeams[7][1] ++;
                }
                break;

            case "ΦΑΟΥΛ ΣΚΛΗΡΟ ΦΑΟΥΛ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][14] ++;
//                totalDataForBothTeams[16][1] ++;
                increaseFaoulOnDangerousPositionB(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][1] ++;
                }else{
                    totalDataForBothTeams[7][1] ++;
                }
                break;

            // ------ ΥΠΕΡ (Φ+) ------
            case "ΥΠΕΡ":
            case "ΥΠΕΡ ΚΕΡΔΙΣΜΕΝΟ ΠΕΝΑΛΤΙ":
//                increaseForPerformanceTeamBSuccess(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][15] ++;
                break;



            // ------ ΛΑΘΟΣ ------
            case "ΛΑΘΟΣ ΚΟΝΤΡΟΛ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][9] ++;
                totalDataForBothTeams[17][1] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][1] ++;
                }else{
                    totalDataForBothTeams[11][1] ++;
                }
                break;

            case "ΛΑΘΟΣ ΚΑΚΗ ΠΑΣΑ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][9] ++;
                totalDataForBothTeams[18][1] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][1] ++;
                }else{
                    totalDataForBothTeams[11][1] ++;
                }
                break;

            case "ΛΑΘΟΣ ΕΚΤΟΣ ΓΡΑΜ":
//                increaseForPerformanceTeamBFail(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][9] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][1] ++;
                }else{
                    totalDataForBothTeams[11][1] ++;
                }
                break;

            case "ΚΛΕΨΙΜΟ":
//                increaseForPerformanceTeamBSuccess(time, 1);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][10] ++;
                break;

            case "ΠΡΟΩΘΗΜΕΝΗ ΠΑΣΑ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][17] ++;
                break;

            // ------- ΚΑΡΤΕΣ -------
            case "ΚΙΤΡΙΝΗ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][16] = 1;
                break;
            case "2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][16] = 2;
                break;
            case "ΚΟΚΚΙΝΗ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][16] = 3;
                break;


            // ------ ΓΚΟΛ ------
            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ":
//                increaseForPerformanceTeamBSuccess(time, 5);
//                increaseForPerformanceTeamAFail(time, 5);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams [12][1] ++ ;
                totalDataForBothTeams[0][1] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ":
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ":
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ":
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ":
//                increaseForPerformanceTeamBSuccess(time, 5);
//                increaseForPerformanceTeamAFail(time, 5);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams [13][1] ++ ;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
//                increaseForPerformanceTeamBSuccess(time, 5);
//                increaseForPerformanceTeamAFail(time, 5);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams [14][1] ++ ;
                totalDataForBothTeams [12][1] ++ ;

                totalDataForBothTeams[0][1] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
//                increaseForPerformanceTeamBSuccess(time, 5);
//                increaseForPerformanceTeamAFail(time, 5);
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][1] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][3] ++;
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][2] ++;
                totalDataForBothTeams [14][1] ++ ;
                totalDataForBothTeams [13][1] ++ ;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][1] ++ ;
                }else{
                    totalDataForBothTeams[1][1] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][1] ++;
                }else{
                    totalDataForBothTeams[9][1] ++;
                }
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΥΤΟΓΚΟΛ":
//                increaseForPerformanceTeamBFail(time, 5);
//                increaseForPerformanceTeamASuccess(time, 5);
//                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][13] ++;
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΣΙΣΤ":
                arrayDataTeamIntB[ getThePosOfPlayerOfB(number) ][4] ++;
                break;

            default:
                break;
        }
    }

    private void increaseFaoulOnDangerousPositionB(String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("true")){
            if(posOfField.equals("05")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("14")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("25")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("34")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("45")) {
                totalDataForBothTeams[16][1] ++;
            }
        }else{
            if(posOfField.equals("00")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("11")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("22")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("31")) {
                totalDataForBothTeams[16][1] ++;
            }else if(posOfField.equals("40")) {
                totalDataForBothTeams[16][1] ++;
            }
        }

    }

    private void increaseThePositionForEustoxaGemismataTeamB(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("false")){
            if(posOfField.equals("03")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfEustoxaGemismataTeamB[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfEustoxaGemismataTeamB[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfEustoxaGemismataTeamB[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfEustoxaGemismataTeamB[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfEustoxaGemismataTeamB[3][0]++;
            }
        }
    }
    private void increaseThePositionForGemismataTeamB(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("false")){
            if(posOfField.equals("03")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfSynolikaGemismataTeamB[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfSynolikaGemismataTeamB[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfSynolikaGemismataTeamB[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfSynolikaGemismataTeamB[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfSynolikaGemismataTeamB[3][0]++;
            }
        }
    }

    private void increaseThePositionForTelikesEntosStoxouTeamB(String imixrono, String posOfField, String wordIsDefault){
        if(wordIsDefault.equals("false")){
            if(posOfField.equals("03")) {
                arrayPositionsOfTelikesEntosTeamB[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfTelikesEntosTeamB[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfTelikesEntosTeamB[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfTelikesEntosTeamB[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfTelikesEntosTeamB[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfTelikesEntosTeamB[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfTelikesEntosTeamB[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfTelikesEntosTeamB[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfTelikesEntosTeamB[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfTelikesEntosTeamB[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfTelikesEntosTeamB[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfTelikesEntosTeamB[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfTelikesEntosTeamB[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfTelikesEntosTeamB[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfTelikesEntosTeamB[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfTelikesEntosTeamB[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfTelikesEntosTeamB[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfTelikesEntosTeamB[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfTelikesEntosTeamB[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfTelikesEntosTeamB[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfTelikesEntosTeamB[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfTelikesEntosTeamB[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfTelikesEntosTeamB[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfTelikesEntosTeamB[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfTelikesEntosTeamB[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfTelikesEntosTeamB[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfTelikesEntosTeamB[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfTelikesEntosTeamB[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfTelikesEntosTeamB[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfTelikesEntosTeamB[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfTelikesEntosTeamB[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfTelikesEntosTeamB[3][0]++;
            }
        }
    }
    private void increaseThePositionForTelikesTeamB(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("false")){ // oi 8eseis an einai false ienai san na einai gia tin team A
            if(posOfField.equals("03")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfSynolikesTelikesTeamB[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfSynolikesTelikesTeamB[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfSynolikesTelikesTeamB[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfSynolikesTelikesTeamB[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfSynolikesTelikesTeamB[3][0]++;
            }
        }
    }


    private void increaseForGemismataForB(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("true")){
            if (imixrono.equals("Α ΗΜΙΧ")){
                if(isTheRightSideForAttackForTeamB(posOfField)){
                    totalDataForBothTeams[3][1] ++;
                }else if(isTheLeftSideForAttackForTeamB(posOfField)){
                    totalDataForBothTeams[2][1] ++;
                }
            }else{
                if(isTheRightSideForAttackForTeamB(posOfField)){
                    totalDataForBothTeams[5][1] ++;
                }else if(isTheLeftSideForAttackForTeamB(posOfField)){
                    totalDataForBothTeams[4][1] ++;
                }
            }
        }else{
            if (imixrono.equals("Α ΗΜΙΧ")){
                if(isTheRightSideForAttackForTeamBNotDefault(posOfField)){
                    totalDataForBothTeams[3][1] ++;
                }else if(isTheLeftSideForAttackForTeamBNotDefault(posOfField)){
                    totalDataForBothTeams[2][1] ++;
                }
            }else{
                if(isTheRightSideForAttackForTeamBNotDefault(posOfField)){
                    totalDataForBothTeams[5][1] ++;
                }else if(isTheLeftSideForAttackForTeamBNotDefault(posOfField)){
                    totalDataForBothTeams[4][1] ++;
                }
            }
        }
    }

    private boolean isTheRightSideForAttackForTeamA(String posOfField) {
        return (posOfField.equals("43") || posOfField.equals("44") || posOfField.equals("45") ) ;
    }
    private boolean isTheRightSideForAttackForTeamBNotDefault(String posOfField) {
        return (posOfField.equals("43") || posOfField.equals("44") || posOfField.equals("45") );
    }


    private boolean isTheLeftSideForAttackForTeamA(String posOfField) {
        return (posOfField.equals("03") || posOfField.equals("04") || posOfField.equals("05"));
    }
    private boolean isTheLeftSideForAttackForTeamBNotDefault(String posOfField) {
        return (posOfField.equals("03") || posOfField.equals("04") || posOfField.equals("05"));
    }


    private boolean isTheRightSideForAttackForTeamANotDefault(String posOfField) {
        return (posOfField.equals("00") || posOfField.equals("01") || posOfField.equals("02"));
    }
    private boolean isTheRightSideForAttackForTeamB(String posOfField) {
        return (posOfField.equals("00") || posOfField.equals("01") || posOfField.equals("02"));
    }


    private boolean isTheLeftSideForAttackForTeamANotDefault(String posOfField) {
        return (posOfField.equals("40") || posOfField.equals("41") || posOfField.equals("42"));
    }
    private boolean isTheLeftSideForAttackForTeamB(String posOfField) {
        return (posOfField.equals("40") || posOfField.equals("41") || posOfField.equals("42"));
    }


    private void increaseForTeamA(int time, String action, String number, String posOfField, String imixrono, String wordIsDefault) {
        switch (action)
        {
            // ------- ΤΕΛΙΚΕΣ ΕΝΤΟΣ -------
            case "ΣΟΥΤ ΕΝΤΟΣ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΝΤΟΣ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ":
            case "ΣΟΥΤ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamASuccess(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++; // ΕΝΤΟΣ
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams[13][0] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);

                break;

            case "ΣΟΥΤ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΝΤΟΣ ΔΟΚΑΡΙ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ ΔΟΚΑΡΙ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams[21][0] ++;
                totalDataForBothTeams[13][0] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ":
            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamASuccess(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][6] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][5] ++;
                totalDataForBothTeams[12][0] ++;
                totalDataForBothTeams[0][0] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;
//
//
            case "ΚΕΦΑΛΙΑ ΕΝΤΟΣ ΔΟΚΑΡΙ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][6] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][5] ++;
                totalDataForBothTeams[21][0] ++;
                totalDataForBothTeams[12][0] ++;
                totalDataForBothTeams[0][0] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            // ------- ΤΕΛΙΚΕΣ ΕΚΤΟΣ -------
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ":
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][5] ++;
//                increaseForPerformanceTeamAFail(time, 1);
                totalDataForBothTeams[12][0] ++;
                totalDataForBothTeams[0][0] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                break;


            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΚΕΦΑΛΙΑ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][5] ++;
//                increaseForPerformanceTeamAFail(time, 1);
                totalDataForBothTeams[12][0] ++;
                totalDataForBothTeams[0][0] ++ ;
                totalDataForBothTeams[21][0] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΣΟΥΤ ΕΚΤΟΣ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ":
            case "ΣΟΥΤ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamAFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams[13][0] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                break;


            case "ΣΟΥΤ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΔΟΚΑΡΙ":
//            case "ΣΟΥΤ ΑΠΟ ΚΟΡΝΕΡ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΔΟΚΑΡΙ":
            case "ΣΟΥΤ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
            case "ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ ΔΟΚΑΡΙ ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ":
//                increaseForPerformanceTeamAFail(time, 1);
                if(action.contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))  arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams[13][0] ++;
                totalDataForBothTeams[21][0] ++;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΠΟΚΡΟΥΣΗ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][11] ++;
                break;



            // ------- ΓΕΜΙΣΜΑΤΑ -------
            case "ΕΠΙΤ ΓΕΜ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][8] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][7] ++;
//                increaseForPerformanceTeamASuccess(time, 1);
                increaseForGemismataForA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForGemismataTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForEustoxaGemismataTeamA(imixrono, posOfField, wordIsDefault);
                break;
            case "ΑΠΟΤ ΓΕΜ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][7] ++;
//                increaseForPerformanceTeamAFail(time, 1);
                increaseForGemismataForA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForGemismataTeamA(imixrono, posOfField, wordIsDefault);
                break;


            case "ΕΠΕΜΒΑΣΗ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][12] ++;
                totalDataForBothTeams[19][0] ++;
//                if(imixrono.equals("Α ΗΜΙΧ")){
//                    totalDataForBothTeams[19][0] ++;
//                }else{
//                    totalDataForBothTeams[20][0] ++;
//                }
                break;

            // ------ ΔΙΕΙΣΔΥΣΗ -------
            // πρεπει να προσεκω τον πινακα που θα δημιουργησω τους μετρητες - μαλλον τελευταια κελια θα προστεθουν
            case "ΕΠΙΤ ΔΙΕΙΣΔΥΣΗ":
                totalDataForBothTeams[20][0] ++;
                totalDataForBothTeams[22][0] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][18] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][19] ++;
                break;

            case "ΑΠΟΤ ΔΙΕΙΣΔΥΣΗ":
                totalDataForBothTeams[20][0] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][18] ++;
                break;

            // ------ KΟΨΙΜΟ -------

            case "ΚΟΨΙΜΟ":
                System.out.println("Κοψιμο");
                break;

            // ------ ΦΑΟΥΛ (Φ-) ------
            case "ΦΑΟΥΛ ΣΩΜΑΤΙΚΗ ΕΠΑΦΗ":
            case "ΦΑΟΥΛ ΧΕΡΙ":
            case "ΦΑΟΥΛ ΤΡΑΒΗΓ ΦΑΝ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][14] ++;
                increaseFaoulOnDangerousPositionA(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][0] ++;
                }else{
                    totalDataForBothTeams[7][0] ++;
                }
                break;

            case "ΦΑΟΥΛ ΤΑΚΛΙΝ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][14] ++;
                totalDataForBothTeams[15][0] ++;
                increaseFaoulOnDangerousPositionA(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][0] ++;
                }else{
                    totalDataForBothTeams[7][0] ++;
                }
                break;

            case "ΦΑΟΥΛ ΣΚΛΗΡΟ ΦΑΟΥΛ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][14] ++;
//                totalDataForBothTeams[16][0] ++;
                increaseFaoulOnDangerousPositionA(posOfField,wordIsDefault);
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[6][0] ++;
                }else{
                    totalDataForBothTeams[7][0] ++;
                }
                break;

            // ------ ΥΠΕΡ (Φ+) ------
            case "ΥΠΕΡ":
            case "ΥΠΕΡ ΚΕΡΔΙΣΜΕΝΟ ΠΕΝΑΛΤΙ":
//                increaseForPerformanceTeamASuccess(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][15] ++;
                break;



            // ------ ΛΑΘΟΣ ------
            case "ΛΑΘΟΣ ΚΟΝΤΡΟΛ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][9] ++;
                totalDataForBothTeams[17][0] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][0] ++;
                }else{
                    totalDataForBothTeams[11][0] ++;
                }
                break;

            case "ΛΑΘΟΣ ΚΑΚΗ ΠΑΣΑ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][9] ++;
                totalDataForBothTeams[18][0] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][0] ++;
                }else{
                    totalDataForBothTeams[11][0] ++;
                }
                break;

            case "ΛΑΘΟΣ ΕΚΤΟΣ ΓΡΑΜ":
//                increaseForPerformanceTeamAFail(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][9] ++;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[10][0] ++;
                }else{
                    totalDataForBothTeams[11][0] ++;
                }
                break;

            case "ΚΛΕΨΙΜΟ":
//                increaseForPerformanceTeamASuccess(time, 1);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][10] ++;
                break;

            case "ΠΡΟΩΘΗΜΕΝΗ ΠΑΣΑ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][17] ++;
                break;

            // ------- ΚΑΡΤΕΣ -------
            case "ΚΙΤΡΙΝΗ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][16] = 1;
                break;
            case "2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][16] = 2;
                break;
            case "ΚΟΚΚΙΝΗ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][16] = 3;
                break;


            // ------ ΓΚΟΛ ------
            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ":
//                increaseForPerformanceTeamASuccess(time, 5);
//                increaseForPerformanceTeamBFail(time, 5);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][1] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams [12][0] ++ ;
                totalDataForBothTeams[0][0] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ":
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ":
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ":
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ":
//                increaseForPerformanceTeamASuccess(time, 5);
//                increaseForPerformanceTeamBFail(time, 5);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][1] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams [13][0] ++ ;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;


            case "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
//                increaseForPerformanceTeamASuccess(time, 5);
//                increaseForPerformanceTeamBFail(time, 5);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][1] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams [14][0] ++ ;
                totalDataForBothTeams [12][0] ++ ;
                totalDataForBothTeams[0][0] ++ ;
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΓΚΟΛ ΑΠΟ ΣΟΥΤ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
            case "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ + ΣΤΑΤΙΚΗ ΦΑΣΗ":
//                increaseForPerformanceTeamASuccess(time, 5);
//                increaseForPerformanceTeamBFail(time, 5);
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][1] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][3] ++;
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][2] ++;
                totalDataForBothTeams [14][0] ++ ;
                totalDataForBothTeams [13][0] ++ ;
                if(isInsideOfSmallArea(posOfField)){
                    totalDataForBothTeams[0][0] ++ ;
                }else{
                    totalDataForBothTeams[1][0] ++ ;
                }
                if(imixrono.equals("Α ΗΜΙΧ")){
                    totalDataForBothTeams[8][0] ++;
                }else{
                    totalDataForBothTeams[9][0] ++;
                }
                increaseThePositionForTelikesTeamA(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamA(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΥΤΟΓΚΟΛ":
//                increaseForPerformanceTeamAFail(time, 5);
//                increaseForPerformanceTeamBSuccess(time, 5);
//                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][13] ++;
                increaseThePositionForTelikesTeamB(imixrono, posOfField, wordIsDefault);
                increaseThePositionForTelikesEntosStoxouTeamB(imixrono, posOfField, wordIsDefault);
                break;

            case "ΑΣΙΣΤ":
                arrayDataTeamIntA[ getThePosOfPlayerOfA(number) ][4] ++;
                break;

            default:
                break;
        }
    }

    private void increaseFaoulOnDangerousPositionA(String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("false")){
            if(posOfField.equals("05")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("14")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("25")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("34")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("45")) {
                totalDataForBothTeams[16][0] ++;
            }
        }else{
            if(posOfField.equals("00")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("11")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("22")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("31")) {
                totalDataForBothTeams[16][0] ++;
            }else if(posOfField.equals("40")) {
                totalDataForBothTeams[16][0] ++;
            }
        }
    }

    private void increaseThePositionForEustoxaGemismataTeamA(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("true")){
            if(posOfField.equals("03")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfEustoxaGemismataTeamA[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfEustoxaGemismataTeamA[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfEustoxaGemismataTeamA[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfEustoxaGemismataTeamA[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfEustoxaGemismataTeamA[3][0]++;
            }
        }
    }
    private void increaseThePositionForGemismataTeamA(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("true")){
            if(posOfField.equals("03")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfSynolikaGemismataTeamA[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfSynolikaGemismataTeamA[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfSynolikaGemismataTeamA[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfSynolikaGemismataTeamA[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfSynolikaGemismataTeamA[3][0]++;
            }
        }
    }

    private void increaseThePositionForTelikesEntosStoxouTeamA(String imixrono, String posOfField, String wordIsDefault){
        if(wordIsDefault.equals("true")){
            if(posOfField.equals("03")) {
                arrayPositionsOfTelikesEntosTeamA[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfTelikesEntosTeamA[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfTelikesEntosTeamA[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfTelikesEntosTeamA[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfTelikesEntosTeamA[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfTelikesEntosTeamA[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfTelikesEntosTeamA[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfTelikesEntosTeamA[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfTelikesEntosTeamA[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfTelikesEntosTeamA[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfTelikesEntosTeamA[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfTelikesEntosTeamA[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfTelikesEntosTeamA[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfTelikesEntosTeamA[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfTelikesEntosTeamA[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfTelikesEntosTeamA[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfTelikesEntosTeamA[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfTelikesEntosTeamA[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfTelikesEntosTeamA[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfTelikesEntosTeamA[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfTelikesEntosTeamA[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfTelikesEntosTeamA[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfTelikesEntosTeamA[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfTelikesEntosTeamA[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfTelikesEntosTeamA[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfTelikesEntosTeamA[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfTelikesEntosTeamA[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfTelikesEntosTeamA[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfTelikesEntosTeamA[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfTelikesEntosTeamA[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfTelikesEntosTeamA[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfTelikesEntosTeamA[3][0]++;
            }
        }
    }
    private void increaseThePositionForTelikesTeamA(String imixrono, String posOfField, String wordIsDefault) {
        if(wordIsDefault.equals("true")){
            if(posOfField.equals("03")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][0]++;
            }else if(posOfField.equals("04")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][0]++;
            }else if(posOfField.equals("05")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][0]++;
            }else if(posOfField.equals("13")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][1]++;
            }else if(posOfField.equals("14")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][1]++;
            }else if(posOfField.equals("15")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][1]++;
            }else if(posOfField.equals("24")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][2]++;
            }else if(posOfField.equals("25")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][2]++;
            }else if(posOfField.equals("26")) {
                arrayPositionsOfSynolikesTelikesTeamA[1][2]++;
            }else if(posOfField.equals("27")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][2]++;
            }else if(posOfField.equals("33")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][3]++;
            }else if(posOfField.equals("34")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][3]++;
            }else if(posOfField.equals("35")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][3]++;
            }else if(posOfField.equals("43")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][4]++;
            }else if(posOfField.equals("44")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][4]++;
            }else if(posOfField.equals("45")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][4]++;
            }

        }else{
            if(posOfField.equals("00")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][4]++;
            }else if(posOfField.equals("01")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][4]++;
            }else if(posOfField.equals("02")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][4]++;
            }else if(posOfField.equals("10")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][3]++;
            }else if(posOfField.equals("11")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][3]++;
            }else if(posOfField.equals("12")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][3]++;
            }else if(posOfField.equals("20")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][2]++;
            }else if(posOfField.equals("21")) {
                arrayPositionsOfSynolikesTelikesTeamA[1][2]++;
            }else if(posOfField.equals("22")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][2]++;
            }else if(posOfField.equals("23")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][2]++;
            }else if(posOfField.equals("30")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][1]++;
            }else if(posOfField.equals("31")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][1]++;
            }else if(posOfField.equals("32")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][1]++;
            }else if(posOfField.equals("40")) {
                arrayPositionsOfSynolikesTelikesTeamA[0][0]++;
            }else if(posOfField.equals("41")) {
                arrayPositionsOfSynolikesTelikesTeamA[2][0]++;
            }else if(posOfField.equals("42")) {
                arrayPositionsOfSynolikesTelikesTeamA[3][0]++;
            }
        }
    }

    private void increaseForGemismataForA(String imixrono, String posOfField, String wordIsDefault ) {
        if(wordIsDefault.equals("true")){
            if (imixrono.equals("Α ΗΜΙΧ")){
                if(isTheRightSideForAttackForTeamA(posOfField)){
                    totalDataForBothTeams[3][0] ++;
                }else if(isTheLeftSideForAttackForTeamA(posOfField)){
                    totalDataForBothTeams[2][0] ++;
                }
            }else{
                if(isTheRightSideForAttackForTeamA(posOfField)){
                    totalDataForBothTeams[5][0] ++;
                }else if(isTheLeftSideForAttackForTeamA(posOfField)){
                    totalDataForBothTeams[4][0] ++;
                }
            }
        }else{
            if (imixrono.equals("Α ΗΜΙΧ")){
                if(isTheRightSideForAttackForTeamANotDefault(posOfField)){
                    totalDataForBothTeams[3][0] ++;
                }else if(isTheLeftSideForAttackForTeamANotDefault(posOfField)){
                    totalDataForBothTeams[2][0] ++;
                }
            }else {
                if(isTheRightSideForAttackForTeamANotDefault(posOfField)){
                    totalDataForBothTeams[5][0] ++;
                }else if(isTheLeftSideForAttackForTeamANotDefault(posOfField)){
                    totalDataForBothTeams[4][0] ++;

                }
            }
        }
    }

    private boolean isInsideOfSmallArea(String posOfField) {

        return  ( posOfField.equals("10") || posOfField.equals("20") ||
                posOfField.equals("21") || posOfField.equals("30") ||
                posOfField.equals("15") || posOfField.equals("26") ||
                posOfField.equals("27") || posOfField.equals("35") ) ;
    }

    private int getThePosOfPlayerOfA(String number) {
        int num = Integer.parseInt(number);

        for(int i = 0 ; i < numberOfPlayersTeamA; i++){
            if(arrayDataTeamIntA[i][0] == num){
                return i;
            }
        }

        return 0;
    }

    private int getThePosOfPlayerOfB(String number) {
        int num = Integer.parseInt(number);

        for(int i = 0 ; i < numberOfPlayersTeamB; i++){
            if(arrayDataTeamIntB[i][0] == num){
                return i;
            }
        }

        return 0;
    }

    private void addTheNamesOfPlayersInSecondColumn() {
        if (numberOfPlayersTeamA == numberOfPlayersTeamB){
            for(int j = 0 ; j < numberOfPlayersTeamA; j++)
            {
                arrayDataOfNamesStringTeamA[j] = allPlayersOfTeamA.get(j).getSurname();
                arrayDataOfNamesStringTeamB[j] = allPlayersOfTeamB.get(j).getSurname();
            }
        }else {
            for (int j = 0; j < numberOfPlayersTeamA; j++)
                arrayDataOfNamesStringTeamA[j] = allPlayersOfTeamA.get(j).getSurname();
            for (int i = 0; i < numberOfPlayersTeamB; i++)
                arrayDataOfNamesStringTeamB[i] = allPlayersOfTeamB.get(i).getSurname();
        }
    }

    private void addTheNumbersOfTshirtsInFirstColumn() {
        if (numberOfPlayersTeamA == numberOfPlayersTeamB){
            for(int j = 0 ; j < numberOfPlayersTeamA; j++)
            {
                arrayDataTeamIntA[j][0] = Integer.parseInt(allPlayersOfTeamA.get(j).getNumber());
                arrayDataTeamIntB[j][0] = Integer.parseInt(allPlayersOfTeamB.get(j).getNumber());
            }
        }
        else{
            for(int j = 0 ; j < numberOfPlayersTeamA; j++)
                arrayDataTeamIntA[j][0] = Integer.parseInt(allPlayersOfTeamA.get(j).getNumber());
            for(int i = 0 ; i < numberOfPlayersTeamB; i++)
                arrayDataTeamIntB[i][0] = Integer.parseInt(allPlayersOfTeamB.get(i).getNumber());
        }
    }

//    private void printA() {
//        System.out.println( numberOfPlayersTeamA );
//        for(int i = 0; i < numberOfPlayersTeamA; i++)
//        {
//            for(int j = 0; j < 18; j++)
//            {
//                System.out.printf("%5d ", arrayDataTeamIntA[i][j]);
//            }
//            System.out.println();
//        }
//    }
//
//    private void showList() {
//        for(DataLiveActions e : eventsList){
//            System.out.println("------>  " + e.toString());
//        }
//    }

    public void openDocument() throws IOException {

        createFile();

        // FIRST PAGE
        createHeaderTemplate();
        tableRowPage1 = new Table(UnitValue.createPercentArray(30)).useAllAvailableWidth().setFixedLayout();
        createThe2TablesForTeamA();
        createTheGeneralStatsTable();
        createThe2TablesForTeamB();
//        createThePerformanceTableForTeamA();
//        createThePerformanceTableForTeamB();
        document.add(tableRowPage1);

        if(!totalTimePossession.equals("0#0"))
        {
            // SECOND PAGE
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            createHeader("ΚΑΤΟΧΗ - ΕΠΙΘΕΣΕΙΣ");
            createTheTableNameOfTeamsForTheSecondPage();
            document.add(tableAttacksNameTeam);

            tableRowPage2 = createTheCorrectTableFormatForTheSecondPage();
            createTheTableForTeamAOfSecondPage();
            createThePossessionStatsOfSecondPage();
            createTheTableForTeamBOfSecondPage();
            document.add(tableRowPage2);

        }


        // THIRD PAGE
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        createHeader("ΑΤΟΜΙΚΑ ΣΤΑΤΙΣΤΙΚΑ\n"+nameTeamA.replace(",","."));
        createTheBasicTableOfA();
        document.add(tableBig);
        createCellForCoachA();


        // FOURTH PAGE
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        createHeader("ΑΤΟΜΙΚΑ ΣΤΑΤΙΣΤΙΚΑ\n"+nameTeamB.replace(",","."));
        createTheBasicTableOfB();
        document.add(tableBig);
        createCellForCoachB();
    }

    private void createThePossessionStatsOfSecondPage() {
        cellForPossession = new Cell(1,10).setBorder(Border.NO_BORDER);

        totalPossessionTable = new Table(new float[]{2, 8, 2});
        totalPossessionTable.setWidth(UnitValue.createPercentValue(100))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        String[] clearTotalTime = totalTimePossession.split("#");
        String[] possessionTeams = getThePossession(clearTotalTime[0], clearTotalTime[1]);
        String minClearTotalTimeTeamA = getTheMinutesFormat(Integer.parseInt(clearTotalTime[0]));
        String minClearTotalTimeTeamB = getTheMinutesFormat(Integer.parseInt(clearTotalTime[1]));

        String[] clearLeftTime = leftTimePossession.split("#");
        String[] clearCenterTime = centerTimePossession.split("#");
        String[] clearRightTime = rightTimePossession.split("#");

        int clearTimeOfDefenseOfTeamA = Integer.parseInt(clearTotalTime[0]) -
                ( Integer.parseInt(clearLeftTime[0]) +
                        Integer.parseInt(clearCenterTime[0]) +
                        Integer.parseInt(clearRightTime[0])
                );
        String minutesClearTimeOfDefenseOfTeamA = getTheMinutesFormat(clearTimeOfDefenseOfTeamA);

        int clearTimeOfDefenseOfTeamB = Integer.parseInt(clearTotalTime[1]) -
                ( Integer.parseInt(clearLeftTime[1]) +
                        Integer.parseInt(clearCenterTime[1]) +
                        Integer.parseInt(clearRightTime[1])
                );
        String minutesClearTimeOfDefenseOfTeamB = getTheMinutesFormat(clearTimeOfDefenseOfTeamB);

        for(int lines=0 ; lines <= 2; lines++) {
            for (int columns = 0; columns <= 2; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(0, 5, 0, 5);

                switch (columns) {
                    case 0:
                        if(lines == 0 ){
                            cell.add(new Paragraph(possessionTeams[0]).setFont(normalFont));
                        }else if (lines == 1){
                            cell.add(new Paragraph(minClearTotalTimeTeamA).setFont(normalFont));
                        }else {
                            cell.add(new Paragraph(minutesClearTimeOfDefenseOfTeamA).setFont(normalFont));
                        }
                        break;
                    case 1:
                        if(lines == 0 ){
                            cell.add(new Paragraph("ΚΑΤΟΧΗ %").setFont(normalFont));
                        }else if (lines == 1){
                            cell.add(new Paragraph("ΚΑΘ. ΧΡΟΝΟΣ ΚΑΤΟΧΗΣ").setFont(normalFont));
                        }else {
                            cell.add(new Paragraph("ΚΑΘ. ΧΡΟΝ. ΣΕ ΑΜΥΝΑ").setFont(normalFont));
                        }
                        break;
                    case 2:
                        if(lines == 0 ){
                            cell.add(new Paragraph(possessionTeams[1]).setFont(normalFont));
                        }else if (lines == 1){
                            cell.add(new Paragraph(minClearTotalTimeTeamB).setFont(normalFont));
                        }else {
                            cell.add(new Paragraph(minutesClearTimeOfDefenseOfTeamB).setFont(normalFont));
                        }
                        break;
                }

                totalPossessionTable.addCell(cell);
            }
        }

        cellForPossession.add(totalPossessionTable);

        cellForPossession.add(new Paragraph("*ΧΡΟΝΟΣ=(ΛΕΠΤΑ:ΔΕΥΤΕΡΟΛΕΠΤΑ)")
                .setFont(normalFont).setMarginTop(10));
        tableRowPage2.addCell(cellForPossession);
    }

    private String getTheMinutesFormat(int time) {
        return String.format("%02d:%02d", (time / 3600 * 60 + ((time % 3600) / 60)), (time % 60));
    }

    private String[] getThePossession(String s1, String s2, String s3) {
        double m = Integer.parseInt(s1);
        double n = Integer.parseInt(s2);
        double c = Integer.parseInt(s3);

        double posA = m / (m+n+c);
        double posB = n / (m+n+c);
        double posC = c / (m+n+c);

        double numA = Math.round(posA * 100.0) / 100.0;
        double numB = Math.round(posB * 100.0) / 100.0;
        double numC = Math.round(posC * 100.0) / 100.0;

        String[] result = new String[3];

        String possessionLeft = String.valueOf(numA).split("\\.")[1] ;
        String possessionRight = String.valueOf(numB).split("\\.")[1] ;
        String possessionCenter = String.valueOf(numC).split("\\.")[1] ;

        result[0] = possessionLeft;
        result[1] = possessionRight;
        result[2] = possessionCenter;

        if(result[0].length() == 1) {
            result[0] += "0";
        }else if(result[1].length() == 1) {
            result[1] += "0";
        }else if(result[2].length() == 1) {
            result[2] += "0";
        }

        return result;
    }

    private String[] getThePossession(String s1, String s2) {

        double m = Integer.parseInt(s1);
        double n = Integer.parseInt(s2);

        double posA = m / (m+n);
        double posB = n / (m+n);

        double numA = Math.round(posA * 100.0) / 100.0;
        double numB = Math.round(posB * 100.0) / 100.0;

        if(numA == 1.0) {
            String[] result = { "100", "0" };
            return result;
        }else if(numB == 1.0){
            String[] result = { "0", "100" };
            return result;
        }

        String possessionTeamA = String.valueOf(numA).split("\\.")[1] ;
        String possessionTeamB = String.valueOf(numB).split("\\.")[1] ;

        String[] result = { possessionTeamA, possessionTeamB };

        if(result[0].length() == 1){
            result[0] += "0";
            result[1] += "0";
        }

        return result;
    }

    private void createTheTableNameOfTeamsForTheSecondPage() {
        tableAttacksNameTeam = getTableNameTeam();

        cellAttacksNameTeam = new Cell().setBorder(Border.NO_BORDER);
        cellAttacksNameTeam.add(new Paragraph
                ("ΕΠΙΘΕΣΕΙΣ "+nameTeamA.replace(",","."))
                .setBold().setFont(normalFont).setFontSize(14));
        tableAttacksNameTeam.addCell(cellAttacksNameTeam);

        cellAttacksNameTeam = new Cell().setBorder(Border.NO_BORDER);
        cellAttacksNameTeam.add(new Paragraph
                ("")
                .setBold().setFont(normalFont).setFontSize(14));
        tableAttacksNameTeam.addCell(cellAttacksNameTeam);

        cellAttacksNameTeam = new Cell().setBorder(Border.NO_BORDER);
        cellAttacksNameTeam.add(new Paragraph
                ("ΕΠΙΘΕΣΕΙΣ "+nameTeamB.replace(",","."))
                .setBold().setFont(normalFont).setFontSize(14));
        tableAttacksNameTeam.addCell(cellAttacksNameTeam);
    }

    private Table createTheCorrectTableFormatForTheSecondPage() {

        return new Table(UnitValue.createPercentArray(30))
                .useAllAvailableWidth()
                .setMarginTop(15)
                .setMarginBottom(15)
                .setPadding(5)
                .setHeight(420).setFixedLayout()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
    }

    private void createTheTableForTeamBOfSecondPage()throws MalformedURLException {
        cellForSecondPageFieldsB =
                new Cell(2,10).setBorder(Border.NO_BORDER);

        cellForSecondPageFieldsB.
                add(new Paragraph(Integer.parseInt(possessionAttackSidesTeamB[1]) + "%")
                        .setFontSize(15))
                .setBold();

        cellForSecondPageFieldsB.
                add(new Paragraph("\n\n\n\n\n\n" + Integer.parseInt(possessionAttackSidesTeamB[2]) + "%")
                        .setFontSize(15))
                .setBold();

        cellForSecondPageFieldsB.
                add(new Paragraph("\n\n\n\n\n\n" + Integer.parseInt(possessionAttackSidesTeamB[0]) + "%")
                        .setFontSize(15))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBold();

        Image img = new Image(ImageDataFactory.create(IMG_ATTACK_TEAM_B));
//        img.setAutoScale(true);

        cellForSecondPageFieldsB
                .setNextRenderer(new ImageBackgroundCellRenderer(
                        cellForSecondPageFieldsB, img));

        tableRowPage2.addCell(cellForSecondPageFieldsB);
    }

    private void createTheTableForTeamAOfSecondPage() throws MalformedURLException {

        // Calculate The Attack Possessions of both Teams HERE
        String[] leftPos = leftTimePossession.split("#");
        String[] rightPos = rightTimePossession.split("#");
        String[] centerPos = centerTimePossession.split("#");

        possessionAttackSidesTeamA= getThePossession(leftPos[0], rightPos[0], centerPos[0]);
        possessionAttackSidesTeamB = getThePossession(leftPos[1], rightPos[1], centerPos[1]);

        cellForSecondPageFieldsA =
                new Cell(2,10).setBorder(Border.NO_BORDER);

        cellForSecondPageFieldsA.
                add(new Paragraph(Integer.parseInt(possessionAttackSidesTeamA[0]) + "%")
                        .setFontSize(15))
                .setBold();

        cellForSecondPageFieldsA.
                add(new Paragraph("\n\n\n\n\n\n" + Integer.parseInt(possessionAttackSidesTeamA[2]) + "%")
                        .setFontSize(15))
                .setBold();

        cellForSecondPageFieldsA.
                add(new Paragraph("\n\n\n\n\n\n" + Integer.parseInt(possessionAttackSidesTeamA[1]) + "%")
                        .setFontSize(15))
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setBold();

        Image img = new Image(ImageDataFactory.create(IMG_ATTACK_TEAM_A));
//        img.setAutoScale(true);

        cellForSecondPageFieldsA
                .setNextRenderer(new ImageBackgroundCellRenderer(
                        cellForSecondPageFieldsA, img));

        tableRowPage2.addCell(cellForSecondPageFieldsA);
    }

    public Table getTableNameTeam() {

        return new Table(3)
                .useAllAvailableWidth()
                .setMarginTop(15)
                .setHeight(28)
                .setFixedLayout()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
    }

    private class ImageBackgroundCellRenderer extends CellRenderer {
        protected Image img;

        public ImageBackgroundCellRenderer(Cell modelElement, Image img) {
            super(modelElement);
            this.img = img;
        }

        @Override
        public void draw(DrawContext drawContext) {
            img.scaleToFit(getOccupiedAreaBBox().getWidth()-20, getOccupiedAreaBBox().getHeight()-20);
            drawContext.getCanvas().addXObject(img.getXObject(), getOccupiedAreaBBox());
            super.draw(drawContext);
        }
    }

    private void createThe2TablesForTeamB() {

        cellForFieldsB = new Cell(2, 8);

        tableTelikesTeamB = new Table(new float[]{4, 4, 4, 4, 4});

        tableTelikesTeamB.setMarginTop(5);
        tableTelikesTeamB.setWidth(UnitValue.createPercentValue(96))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 3; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(5, 5, 5, 5);

                String val;
                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 1:
                            cell = new Cell(2,1).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 2:
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell = new Cell(2,1).setBorderLeft(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 4:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamB.addCell(cell);
                }else if(lines==1){
                    switch (columns) {
                        case 2:
                            cell.setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            tableTelikesTeamB.addCell(cell);
                            break;
                    }
                }else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamB.addCell(cell);
                }else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamB.addCell(cell);
                }

            }
        }

        cellForFieldsB.add(
                new Paragraph("ΤΕΛΙΚΕΣ ΕΝΤΟΣ ΣΤΟΧΟΥ/ΣΥΝΟΛΙΚΕΣ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(35));
        cellForFieldsB.add(tableTelikesTeamB);


        // ----------------------------------------------- SECOND FIELD ------------------------------------------------
        tableGemismataTeamB = new Table(new float[]{4, 4, 4, 4, 4});

        tableGemismataTeamB.setMarginTop(5);
        tableGemismataTeamB.setWidth(UnitValue.createPercentValue(96))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 3; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(5, 5, 5, 5);
                String val;

                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 1:
                            cell = new Cell(2,1).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 2:
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell = new Cell(2,1).setBorderLeft(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 4:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamB.addCell(cell);
                }else if(lines==1){
                    switch (columns) {
                        case 2:
                            cell.setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            tableGemismataTeamB.addCell(cell);
                            break;
                    }
                }else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamB.addCell(cell);
                }else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamB[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamB[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamB.addCell(cell);
                }
            }
        }

        cellForFieldsB.add(
                new Paragraph("ΕΥΣΤΟΧΑ ΓΕΜΙΣΜΑΤΑ/ΣΥΝΟΛΙΚΑ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(35));
        cellForFieldsB.add(tableGemismataTeamB);

        tableRowPage1.addCell(cellForFieldsB);
    }

    private void createThe2TablesForTeamA() {

        cellForFieldsA = new Cell(2, 8);

        tableTelikesTeamA = new Table(new float[]{4, 4, 4, 4, 4});

        tableTelikesTeamA.setMarginTop(5);
        tableTelikesTeamA.setWidth(UnitValue.createPercentValue(96))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 3; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(5, 5, 5, 5);
                String val;
                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 1:
                            cell = new Cell(2,1).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 2:
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell = new Cell(2,1).setBorderLeft(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 4:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamA.addCell(cell);
                }else if(lines==1){
                    switch (columns) {
                        case 2:
                            cell.setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            tableTelikesTeamA.addCell(cell);
                            break;
                    }
                }else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamA.addCell(cell);
                }else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfTelikesEntosTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikesTelikesTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableTelikesTeamA.addCell(cell);
                }
            }
        }

        cellForFieldsA.add(
                new Paragraph("ΤΕΛΙΚΕΣ ΕΝΤΟΣ ΣΤΟΧΟΥ/ΣΥΝΟΛΙΚΕΣ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(35));
        cellForFieldsA.add(tableTelikesTeamA);


        // ----------------------------------------------- SECOND FIELD ------------------------------------------------
        tableGemismataTeamA = new Table(new float[]{4, 4, 4, 4, 4});

        tableGemismataTeamA.setMarginTop(5);
        tableGemismataTeamA.setWidth(UnitValue.createPercentValue(96))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 3; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(5, 5, 5, 5);
                String val;

                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 1:
                            cell = new Cell(2,1).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 2:
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell = new Cell(2,1).setBorderLeft(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                        case 4:
                            cell = new Cell(2,1).setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("\n  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph("\n"+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamA.addCell(cell);
                }else if(lines==1){
                    switch (columns) {
                        case 2:
                            cell.setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            tableGemismataTeamA.addCell(cell);
                            break;
                    }
                }else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderBottom(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderBottom(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamA.addCell(cell);
                }else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.setBorderTop(Border.NO_BORDER)
                                    .setBorderRight(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER)
                                    .setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER)
                                    .setBorderTop(Border.NO_BORDER);
                            val = arrayPositionsOfEustoxaGemismataTeamA[lines][columns]
                                    +"/"+arrayPositionsOfSynolikaGemismataTeamA[lines][columns]+"";
                            if(val.equals("0/0"))
                                cell.add(new Paragraph("  *  ").setFont(normalFont));
                            else
                                cell.add(new Paragraph(""+val).setFont(normalFont));
                            break;
                    }
                    tableGemismataTeamA.addCell(cell);
                }
            }
        }

        cellForFieldsA.add(
                new Paragraph("ΕΥΣΤΟΧΑ ΓΕΜΙΣΜΑΤΑ/ΣΥΝΟΛΙΚΑ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(35));
        cellForFieldsA.add(tableGemismataTeamA);

        tableRowPage1.addCell(cellForFieldsA);
    }

    private void createTheGeneralStatsTable() {

        cellForStats = new Cell(1,14);

        totalStatsMidTable = new Table(new float[]{2, 10, 2});

        totalStatsMidTable.setWidth(UnitValue.createPercentValue(100))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 21; lines++) {
            for (int columns = 0; columns <= 2; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(0, 5, 0, 5);

                if(lines != 20)
                {
                    int f = -1; // means that the stats of the teams are equals
                    if(totalDataForBothTeams[lines][0] > totalDataForBothTeams[lines][1])
                        f = 0;
                    else if(totalDataForBothTeams[lines][0] < totalDataForBothTeams[lines][1])
                        f = 2;

                    switch (columns) {
                        case 0:
                            if(f == 0)
                                cell.add(new Paragraph(totalDataForBothTeams[lines][0]+"").setFont(normalFont).setBold());
                            else
                                cell.add(new Paragraph(totalDataForBothTeams[lines][0]+"").setFont(normalFont));
                            break;
                        case 1:
                            cell.add(new Paragraph(statsNameForMiddleTotalStats[lines]).setFont(normalFont));
                            break;
                        case 2:
                            if(f == 2)
                                cell.add(new Paragraph(totalDataForBothTeams[lines][1]+"").setFont(normalFont).setBold().setBold());
                            else
                                cell.add(new Paragraph(totalDataForBothTeams[lines][1]+"").setFont(normalFont));
                            break;
                    }
                }else{

                    int f = -1; // means that the stats of the teams are equals
                    if(totalDataForBothTeams[22][0] > totalDataForBothTeams[22][1])
                        f = 0;
                    else if(totalDataForBothTeams[22][0] < totalDataForBothTeams[22][1])
                        f = 2;

                    switch (columns) {
                        case 0:
                            if(f == 0)
                                cell.add(new Paragraph(
                                        totalDataForBothTeams[lines][0]+"("+totalDataForBothTeams[22][0]+")")
                                        .setFont(normalFont).setBold());
                            else
                                cell.add(new Paragraph(
                                        totalDataForBothTeams[lines][0]+"("+totalDataForBothTeams[22][0]+")")
                                        .setFont(normalFont));

                            break;
                        case 1:
                            cell.add(new Paragraph(statsNameForMiddleTotalStats[lines]).setFont(normalFont));
                            break;
                        case 2:
                            if(f == 2)
                                cell.add(new Paragraph(
                                        totalDataForBothTeams[lines][1]+"("+totalDataForBothTeams[22][1]+")")
                                        .setFont(normalFont).setBold());
                            else
                                cell.add(new Paragraph(
                                        totalDataForBothTeams[lines][1]+"("+totalDataForBothTeams[22][1]+")")
                                        .setFont(normalFont));

                            break;
                    }
                }
                totalStatsMidTable.addCell(cell);
            }
        }

        cellForStats.add(totalStatsMidTable);
        tableRowPage1.addCell(cellForStats);
    }

    private void createCellForCoachB() {
        tableCoachB = new Table(new float[]{1});

        tableCoachB.setWidth(UnitValue.createPercentValue(50))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell cell = new Cell();
        cell.setPaddings(0,0,0,0);
        cell.add(new Paragraph("ΠΡΟΠΟΝΗΤΗΣ : " + coachB).setFont(normalFont));

        tableCoachB.addCell(cell);

        document.add(tableCoachB);
    }

    private void createCellForCoachA() {
        tableCoachA = new Table(new float[]{1});

        tableCoachA.setWidth(UnitValue.createPercentValue(50))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        Cell cell = new Cell();
        cell.setPaddings(0,0,0,0);
        cell.add(new Paragraph("ΠΡΟΠΟΝΗΤΗΣ : " + coachA).setFont(normalFont));

        tableCoachA.addCell(cell);

        document.add(tableCoachA);
    }

    private void createTheBasicTableOfB() throws MalformedURLException {
        tableBig = new Table(new float[]{1.5f,7,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,
                1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f, 1.5f});
        tableBig.setWidth(UnitValue.createPercentValue(100))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        String name ;
        int c = 0;

        for(int lines=0 ; lines <= numberOfPlayersTeamB; lines++){
            for(int columns=0 ; columns <= 16; columns++){

                if(lines > 0) {
                    Cell cell = new Cell();
                    cell.setPaddings(0,0,0,0);
                    String val = "0" ;
                    String val2 = "0" ;
                    switch (columns)
                    {
                        case 1:
                            name = arrayDataOfNamesStringTeamB[lines-1] ;
                            cell.add(new Paragraph(name.substring(0, Math.min(name.length(), 10))).setFont(normalFont));
                            break;

                        case 0:
                            val = String.valueOf( arrayDataTeamIntB[lines-1][0] );

                            if(c < counterForPlayerWhoPlayedTeamB)
                                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                            else
                                cell.setBackgroundColor(ColorConstants.WHITE);
                            cell.add(new Paragraph(val).setFont(normalFont).setBold());
                            c++;
                            break;
                        case 2:
                            val = String.valueOf( arrayDataTeamIntB[lines-1][1] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 3: // Τελικη
                            val = String.valueOf( arrayDataTeamIntB[lines-1][2] );
                            val2 = String.valueOf( arrayDataTeamIntB[lines-1][3] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 4: // Ασιστ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][4] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 5: // Κεφαλ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][5] );
                            val2 = String.valueOf( arrayDataTeamIntB[lines-1][6] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 6: // Γεμισμ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][7] );
                            val2 = String.valueOf( arrayDataTeamIntB[lines-1][8] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 7: // Λαθη
                            val = String.valueOf( arrayDataTeamIntB[lines-1][9] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 8: // Κλεψιμο
                            val = String.valueOf( arrayDataTeamIntB[lines-1][10] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 9: // Αποκρ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][11] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 10: // Επεμβ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][12] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 11: // ΧΕ
                            val = String.valueOf( arrayDataTeamIntB[lines-1][13] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 12: // Φ -
                            val = String.valueOf( arrayDataTeamIntB[lines-1][14] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 13: // Φ +
                            val = String.valueOf( arrayDataTeamIntB[lines-1][15] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 14: // Καρτες (Kιτρ + Κοκκ)
                            Image img;
                            if(arrayDataTeamIntB[lines-1][16] == 1){
                                img = new Image(ImageDataFactory.create(IMG_CARD_YELLOW));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else if(arrayDataTeamIntB[lines-1][16] == 2){
                                img = new Image(ImageDataFactory.create(IMG_CARDS));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else if(arrayDataTeamIntB[lines-1][16] == 3){
                                img = new Image(ImageDataFactory.create(IMG_CARD_RED));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else{
                                cell.add(new Paragraph("-").setFont(normalFont));
                            }
                            break;
                        case 15: // Πασες Αναπτυξης
                            val = String.valueOf( arrayDataTeamIntB[lines-1][17] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 16: // Διεισδυσεις : Συνολικες(Επιτυχημενες)
                            val = String.valueOf( arrayDataTeamIntB[lines-1][18] );
                            val2 = String.valueOf( arrayDataTeamIntB[lines-1][19] );
                            cell.add(new Paragraph(val+"("+val2+")").setFont(normalFont));
                            break;

                        default:
                            cell.add(new Paragraph("0").setFont(normalFont));
                            break;
                    }
                    tableBig.addCell(cell);
                }
                else{
                    Cell cell = new Cell();
                    cell.setPaddings(0, 0, 0, 0);
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("Νο").setFont(normalFont).setBold());
                            break;
                        case 1:
//                            String useTeamB = nameTeamB;
                            cell.add(new Paragraph("ΟΝΟΜΑ").setFont(normalFont).setBold().setFontSize(13));
                            break;
                        case 2:
                            cell.add(new Paragraph("ΓΚΟΛ").setFont(normalFont).setBold());
                            break;
                        case 3:
                            cell.add(new Paragraph("ΤΕΛ").setFont(normalFont).setBold());
                            break;
                        case 4:
                            cell.add(new Paragraph("ΑΣΙΣΤ").setFont(normalFont).setBold());
                            break;
                        case 5:
                            cell.add(new Paragraph("ΚΕΦ").setFont(normalFont).setBold());
                            break;
                        case 6:
                            cell.add(new Paragraph("ΓΕΜ").setFont(normalFont).setBold());
                            break;
                        case 7:
                            cell.add(new Paragraph("ΛΑΘ").setFont(normalFont).setBold());
                            break;
                        case 8:
                            cell.add(new Paragraph("ΚΛΕΨ").setFont(normalFont).setBold());
                            break;
                        case 9:
                            cell.add(new Paragraph("ΑΠΟΚ").setFont(normalFont).setBold());
                            break;
                        case 10:
                            cell.add(new Paragraph("ΕΠΕΜ").setFont(normalFont).setBold());
                            break;
                        case 11:
                            cell.add(new Paragraph("ΧΕ").setFont(normalFont).setBold());
                            break;
                        case 12:
                            cell.add(new Paragraph("Φ -").setFont(normalFont).setBold());
                            break;
                        case 13:
                            cell.add(new Paragraph("Φ +").setFont(normalFont).setBold());
                            break;
                        case 14:
                            cell.add(new Paragraph("ΚΑΡΤ").setFont(normalFont).setBold());
                            break;
                        case 15:
                            cell.add(new Paragraph("Π. ΑΝ.").setFont(normalFont).setBold());
                            break;
                        case 16:
                            cell.add(new Paragraph("Δ").setFont(normalFont).setBold());
                            break;
                        default:
                            cell.add(new Paragraph("").setFont(normalFont).setBold());
                            break;
                    }
                    tableBig.addCell(cell);
                }
            }
        }
    }

    private void createTheBasicTableOfA() throws MalformedURLException {
        tableBig = new Table(new float[]{1.5f,7,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,
                1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f,1.5f, 1.5f});
        tableBig.setWidth(UnitValue.createPercentValue(100))
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        String name ;
        int c = 0 ;

        for(int lines=0 ; lines <= numberOfPlayersTeamA; lines++){
            for(int columns=0 ; columns <= 16; columns++){

                if(lines > 0) {
                    Cell cell = new Cell();
                    cell.setPaddings(0,0,0,0);
                    String val = "0" ;
                    String val2 = "0" ;
                    switch (columns)
                    {
                        case 1:
                            name = arrayDataOfNamesStringTeamA[lines-1] ;
                            cell.add(new Paragraph(name.substring(0, Math.min(name.length(), 10))).setFont(normalFont));
                            break;

                        case 0:
                            val = String.valueOf( arrayDataTeamIntA[lines-1][0] );

                            if(c < counterForPlayerWhoPlayedTeamA)
                                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                            else
                                cell.setBackgroundColor(ColorConstants.WHITE);
                            cell.add(new Paragraph(val).setFont(normalFont).setBold());
                            c++;
                            break;
                        case 2:
                            val = String.valueOf( arrayDataTeamIntA[lines-1][1] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 3: // Τελικη
                            val = String.valueOf( arrayDataTeamIntA[lines-1][2] );
                            val2 = String.valueOf( arrayDataTeamIntA[lines-1][3] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 4: // Ασιστ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][4] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 5: // Κεφαλ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][5] );
                            val2 = String.valueOf( arrayDataTeamIntA[lines-1][6] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 6: // Γεμισμ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][7] );
                            val2 = String.valueOf( arrayDataTeamIntA[lines-1][8] );
                            cell.add(new Paragraph(val +"(" + val2 + ")").setFont(normalFont));
                            break;
                        case 7: // Λαθη
                            val = String.valueOf( arrayDataTeamIntA[lines-1][9] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 8: // Κλεψιμο
                            val = String.valueOf( arrayDataTeamIntA[lines-1][10] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 9: // Αποκρ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][11] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 10: // Επεμβ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][12] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 11: // ΧΕ
                            val = String.valueOf( arrayDataTeamIntA[lines-1][13] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 12: // Φ -
                            val = String.valueOf( arrayDataTeamIntA[lines-1][14] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 13: // Φ +
                            val = String.valueOf( arrayDataTeamIntA[lines-1][15] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 14: // Καρτες (Kιτρ + Κοκκ)
                            Image img;
                            if(arrayDataTeamIntA[lines-1][16] == 1){
                                img = new Image(ImageDataFactory.create(IMG_CARD_YELLOW));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else if(arrayDataTeamIntA[lines-1][16] == 2){
                                img = new Image(ImageDataFactory.create(IMG_CARDS));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else if(arrayDataTeamIntA[lines-1][16] == 3){
                                img = new Image(ImageDataFactory.create(IMG_CARD_RED));
                                cell.add(img.setHorizontalAlignment(HorizontalAlignment.CENTER));
                            }else{
                                cell.add(new Paragraph("-").setFont(normalFont));
                            }
                            break;
                        case 15: // Πασες Αναπτυξης
                            val = String.valueOf( arrayDataTeamIntA[lines-1][17] );
                            cell.add(new Paragraph(val).setFont(normalFont));
                            break;
                        case 16: // Διεισδυσεις : Συνολικες(Επιτυχημενες)
                            val = String.valueOf( arrayDataTeamIntA[lines-1][18] );
                            val2 = String.valueOf( arrayDataTeamIntA[lines-1][19] );
                            cell.add(new Paragraph(val+"("+val2+")").setFont(normalFont));
                            break;

                        default:
                            cell.add(new Paragraph("0").setFont(normalFont));
                            break;
                    }

                    tableBig.addCell(cell);
                }
                else{
                    Cell cell = new Cell();
                    cell.setPaddings(0, 0, 0, 0);
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("Νο").setFont(normalFont).setBold());
                            break;
                        case 1:
                            //String useTeamA = nameTeamA;
                            cell.add(new Paragraph("ΟΝΟΜΑ").setFont(normalFont).setBold().setFontSize(13));
                            break;
                        case 2:
                            cell.add(new Paragraph("ΓΚΟΛ").setFont(normalFont).setBold());
                            break;
                        case 3:
                            cell.add(new Paragraph("ΤΕΛ").setFont(normalFont).setBold());
                            break;
                        case 4:
                            cell.add(new Paragraph("ΑΣΙΣΤ").setFont(normalFont).setBold());
                            break;
                        case 5:
                            cell.add(new Paragraph("ΚΕΦ").setFont(normalFont).setBold());
                            break;
                        case 6:
                            cell.add(new Paragraph("ΓΕΜ").setFont(normalFont).setBold());
                            break;
                        case 7:
                            cell.add(new Paragraph("ΛΑΘ").setFont(normalFont).setBold());
                            break;
                        case 8:
                            cell.add(new Paragraph("ΚΛΕΨ").setFont(normalFont).setBold());
                            break;
                        case 9:
                            cell.add(new Paragraph("ΑΠΟΚ").setFont(normalFont).setBold());
                            break;
                        case 10:
                            cell.add(new Paragraph("ΕΠΕΜ").setFont(normalFont).setBold());
                            break;
                        case 11:
                            cell.add(new Paragraph("ΧΕ").setFont(normalFont).setBold());
                            break;
                        case 12:
                            cell.add(new Paragraph("Φ -").setFont(normalFont).setBold());
                            break;
                        case 13:
                            cell.add(new Paragraph("Φ +").setFont(normalFont).setBold());
                            break;
                        case 14:
                            cell.add(new Paragraph("KΑΡΤ").setFont(normalFont).setBold());
                            break;
                        case 15:
                            cell.add(new Paragraph("Π. ΑΝ.").setFont(normalFont).setBold());
                            break;
                        case 16:
                            cell.add(new Paragraph("Δ").setFont(normalFont).setBold());
                            break;
                        default:
                            cell.add(new Paragraph("").setFont(normalFont).setBold());
                            break;
                    }
                    tableBig.addCell(cell);
                }
            }
        }
    }

    private void createFile() throws IOException {
        //String useTeamA = nameTeamA;String useTeamB = nameTeamB;
//        if (nameTeamA.length()>6) useTeamA = useTeamA.substring(0,6);
//        if (nameTeamB.length()>7) useTeamB = useTeamB.substring(0,6);
        // Create File
        dest = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/Matches/"+nameTeamA.replace(",","")+"-"+nameTeamB.replace(",","")+".pdf";
        file = new File(dest);
        file.getParentFile().mkdirs();

        //Initialize PDF writer
        writer = new PdfWriter(dest);

        //Initialize PDF document
        pdf = new PdfDocument(writer);

        pdf.setDefaultPageSize(PageSize.A4.rotate());

        // Initialize document
        document = new Document(pdf);
        document.setMargins(1, 5, 1, 5);

        String f = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/Fonts/arialuni.ttf" ;

        normalFont = PdfFontFactory.createFont( f , "Identity-H", true);
    }

    private void createHeader(String header) {

        tableHead = new Table(1).setMarginBottom(15)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setTextAlignment(TextAlignment.CENTER)
                .useAllAvailableWidth().setFixedLayout();

        Cell cell = new Cell().setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph(header).setFontSize(16)
                        .setFont(normalFont).setBold());

        tableHead.addCell(cell);
        document.add(tableHead);
    }

    private void createHeaderTemplate() throws MalformedURLException {
        tableHeaderTemplate = new Table(UnitValue.createPercentArray(20))
                .useAllAvailableWidth().setFixedLayout();

        IMG = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/logo.png" ;
        IMG_CARDS = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/cards.png" ;
        IMG_CARD_YELLOW = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/yellowcard.png" ;
        IMG_CARD_RED = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/redcard.png" ;
        IMG_ATTACK_TEAM_A = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/areaAttackTeamA.png" ;
        IMG_ATTACK_TEAM_B = Environment.getExternalStorageDirectory().toString()+"/Download/NOT_DELETE/areaAttackTeamB.png" ;

        Image img = new Image(ImageDataFactory.create(IMG));
        img.setAutoScale(true);

        Cell cellLogo = new Cell(1, 3).add(img);
        tableHeaderTemplate.addCell(cellLogo);

        createTheMiddleHeaderData();

        // Golden SPOSNSOR
        Cell sponsorLogo = new Cell(1, 3)
                .add(new Paragraph("")
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER));
        tableHeaderTemplate.addCell(sponsorLogo);

        document.add(tableHeaderTemplate);
    }

    private void createTheMiddleHeaderData() {

        tableRow1Head = new Table(UnitValue.createPercentArray(20)).useAllAvailableWidth().setFixedLayout();


        Cell cellRacing;
        if(!numberRacingStr.equals("0")){
            cellRacing = new Cell(1, 5)
                    .add(new Paragraph(numberRacingStr+"η ΑΓΩΝΙΣΤΙΚΗ").setFont(normalFont)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .setBorder(Border.NO_BORDER);
        }else{
            cellRacing = new Cell(1, 5)
                    .add(new Paragraph("ΦΙΛΙΚΟ").setFont(normalFont)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .setBorder(Border.NO_BORDER);
        }
        tableRow1Head.addCell(cellRacing);

        Cell cellStats = new Cell(1, 10)
                .add(new Paragraph("ΣΤΑΤΙΣΤΙΚΑ ΑΓΩΝΑ").setFont(normalFont)
                        .setBold()
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setTextAlignment(TextAlignment.CENTER))
                .setBorder(Border.NO_BORDER);
        tableRow1Head.addCell(cellStats);

        Cell cellField = new Cell(1, 5)
                .add(new Paragraph(dateStr+","+fieldStr).setFont(normalFont)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER);
        tableRow1Head.addCell(cellField);


        tableRow2Head = new Table(UnitValue.createPercentArray(20)).useAllAvailableWidth().setFixedLayout();

        Cell cellTime = new Cell(1, 5)
                .add(new Paragraph("ΩΡΑ ΕΝΑΡΞΗΣ : " + startTimeStr).setFont(normalFont)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .setBorder(Border.NO_BORDER);
        tableRow2Head.addCell(cellTime);

        Table table = createScoreHeader();
        Cell cellScore = new Cell(1, 10).setBorder(Border.NO_BORDER);
        cellScore.add(table);
        tableRow2Head.addCell(cellScore);

        Cell cellCat = new Cell(1, 5)
                .add(new Paragraph(nameOrganizationStr).setFont(normalFont)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .setHorizontalAlignment(HorizontalAlignment.CENTER)
                        .setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER);
        tableRow2Head.addCell(cellCat);

        Cell middleInfo = new Cell(1, 14);
        middleInfo.add(tableRow1Head);
        middleInfo.add(tableRow2Head);

        tableHeaderTemplate.addCell(middleInfo);
    }

    private Table createScoreHeader() {
        Table table = new Table(3);
        //String useTeamA = nameTeamA;
        //String useTeamB = nameTeamB;
//        if (nameTeamA.length()>6) useTeamA = useTeamA.substring(0,6);
//        if (nameTeamB.length()>7) useTeamB = useTeamB.substring(0,6);
        Cell cell1 = new Cell()
                .add(new Paragraph(nameTeamA.replace(",",".")).setFont(normalFont))
                .setFont(normalFont)
                .setBold()
                .setFontSize(13)
                .setPaddings(0,4,0,4)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell1);

        String[] arrOfStr = scoreStr.split("#");

        Cell cell2 = new Cell()
                .add(new Paragraph(arrOfStr[0] +" - "+ arrOfStr[1]).setFont(normalFont))
                .setFont(normalFont)
                .setBold()
                .setFontSize(13)
                .setPaddings(0,4,0,4)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell2);

        Cell cell3 = new Cell()
                .add(new Paragraph(nameTeamB.replace(",",".")).setFont(normalFont))
                .setFont(normalFont)
                .setBold()
                .setFontSize(13)
                .setPaddings(0,4,0,4)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell3);

        table.setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
//        document.add(table);

        return table;
    }

    public void closeDocument(){
        document.close();
    }

//    public void viewPDF(){
//        Intent intent = new Intent(, ViewPdfActivity.class);
//        intent.putExtra("path", file.getAbsolutePath());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

}


///////////////////////////////// CODE FOR PERFORMANCE --------------------------------------------------------------------------

//    private void showThe2ArraysWithRes() {
//        for(int i = 0 ; i < 6 ; i++){
//            System.out.println( " A ---->   " + performanceTeamA[i]);
//            System.out.println( " B ---->   " + performanceTeamB[i]);
//        }
//    }


//    private void increaseForPerformanceTeamBSuccess(int time, int points) {
//        if(time < 15){
//            countersForPerformanceOfTeams[0][2] += points;
//        }else if(time < 30){
//            countersForPerformanceOfTeams[1][2] += points;
//        }else if(time <= 48){
//            countersForPerformanceOfTeams[2][2] += points;
//        }else if(time < 60){
//            countersForPerformanceOfTeams[3][2] += points;
//        }else if(time < 75){
//            countersForPerformanceOfTeams[4][2] += points;
//        }else if(time < 96){
//            countersForPerformanceOfTeams[5][2] += points;
//        }
//    }
//
//    private void increaseForPerformanceTeamBFail(int time, int points) {
//        if(time < 15){
//            countersForPerformanceOfTeams[0][3] += points;
//        }else if(time < 30){
//            countersForPerformanceOfTeams[1][3] += points;
//        }else if(time <= 48){
//            countersForPerformanceOfTeams[2][3] += points;
//        }else if(time < 60){
//            countersForPerformanceOfTeams[3][3] += points;
//        }else if(time < 75){
//            countersForPerformanceOfTeams[4][3] += points;
//        }else if(time < 96){
//            countersForPerformanceOfTeams[5][3] += points;
//        }
//    }
//
//    private void increaseForPerformanceTeamAFail(int time, int points) {
//        if(time < 15){
//            countersForPerformanceOfTeams[0][1] += points;
//        }else if(time < 30){
//            countersForPerformanceOfTeams[1][1] += points;
//        }else if(time <= 48){
//            countersForPerformanceOfTeams[2][1] += points;
//        }else if(time < 60){
//            countersForPerformanceOfTeams[3][1] += points;
//        }else if(time < 75){
//            countersForPerformanceOfTeams[4][1] += points;
//        }else if(time < 96){
//            countersForPerformanceOfTeams[5][1] += points;
//        }
//    }
//
//    private void increaseForPerformanceTeamASuccess(int time, int points) {
//        if(time < 15){
//            countersForPerformanceOfTeams[0][0] += points;
//        }else if(time < 30){
//            countersForPerformanceOfTeams[1][0] += points;
//        }else if(time <= 48){
//            countersForPerformanceOfTeams[2][0] += points;
//        }else if(time < 60){
//            countersForPerformanceOfTeams[3][0] += points;
//        }else if(time < 75){
//            countersForPerformanceOfTeams[4][0] += points;
//        }else if(time < 96){
//            countersForPerformanceOfTeams[5][0] += points;
//        }
//    }

/*
     private void createThePerformanceTableForTeamA() {
        divideTheResultsForPerformanceTeamA();

        cellForPerfA = new Cell(1,8);

        performanceTableTeamA = new Table(new float[]{2,4,4,4,4});

        performanceTableTeamA.setMarginTop(10);
        performanceTableTeamA.setWidth(UnitValue.createPercentValue(95))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 6; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(0, 5, 0, 5);

                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("ΧΡΟΝΟΣ").setFont(normalFont));
                            break;
                        case 1:
                            cell.add(new Paragraph("25%").setFont(normalFont));
                            break;
                        case 2:
                            cell.add(new Paragraph("50%").setFont(normalFont));
                            break;
                        case 3:
                            cell.add(new Paragraph("75%").setFont(normalFont));
                            break;
                        case 4:
                            cell.add(new Paragraph("100%").setFont(normalFont));
                            break;
                    }
                }else if(lines==1){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("0-15").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("15-30").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("30-45").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==4){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("45-60").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==5){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("60-75").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==6){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("75-90").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamA[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                performanceTableTeamA.addCell(cell);
            }
        }

        cellForPerfA.add(
                new Paragraph("ΑΠΟΔΟΣΗ ΑΝΑ 15 ΛΕΠΤΑ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(80));
        cellForPerfA.add(performanceTableTeamA);
        tableRowPage1.addCell(cellForPerfA);
    }

    private void divideTheResultsForPerformanceTeamA() {
        if(countersForPerformanceOfTeams[0][1] != 0)
            performanceTeamA[0] = (double)countersForPerformanceOfTeams[0][0] / (double)countersForPerformanceOfTeams[0][1];
        if(countersForPerformanceOfTeams[1][1] != 0)
            performanceTeamA[1] = (double)countersForPerformanceOfTeams[1][0] / (double)countersForPerformanceOfTeams[1][1];
        if(countersForPerformanceOfTeams[2][1] != 0)
            performanceTeamA[2] = (double)countersForPerformanceOfTeams[2][0] / (double)countersForPerformanceOfTeams[2][1];
        if(countersForPerformanceOfTeams[3][1] != 0)
            performanceTeamA[3] = (double)countersForPerformanceOfTeams[3][0] / (double)countersForPerformanceOfTeams[3][1];
        if(countersForPerformanceOfTeams[4][1] != 0)
            performanceTeamA[4] = (double)countersForPerformanceOfTeams[4][0] / (double)countersForPerformanceOfTeams[4][1];
        if(countersForPerformanceOfTeams[5][1] != 0)
            performanceTeamA[5] = (double)countersForPerformanceOfTeams[5][0] / (double)countersForPerformanceOfTeams[5][1];
    }

    private void createThePerformanceTableForTeamB() {
        divideTheResultsForPerformanceTeamB();

        cellForPerfB = new Cell(1,8);

        performanceTableTeamB = new Table(new float[]{2,4,4,4,4});

        performanceTableTeamB.setMarginTop(10);
        performanceTableTeamB.setWidth(UnitValue.createPercentValue(95))
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        for(int lines=0 ; lines <= 6; lines++) {
            for (int columns = 0; columns <= 4; columns++) {

                Cell cell = new Cell();
                cell.setPaddings(0, 5, 0, 5);

                if(lines==0){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("ΧΡΟΝΟΣ").setFont(normalFont));
                            break;
                        case 1:
                            cell.add(new Paragraph("25%").setFont(normalFont));
                            break;
                        case 2:
                            cell.add(new Paragraph("50%").setFont(normalFont));
                            break;
                        case 3:
                            cell.add(new Paragraph("75%").setFont(normalFont));
                            break;
                        case 4:
                            cell.add(new Paragraph("100%").setFont(normalFont));
                            break;
                    }
                }else if(lines==1){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("0-15").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==2){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("15-30").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==3){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("30-45").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==4){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("45-60").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==5){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("60-75").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                else if(lines==6){
                    switch (columns) {
                        case 0:
                            cell.add(new Paragraph("75-90").setFont(normalFont));
                            break;
                        case 1:
                            cell.setBorderRight(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 0.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 2:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 3:
                            cell.setBorderRight(Border.NO_BORDER);
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 1.5)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                        case 4:
                            cell.setBorderLeft(Border.NO_BORDER);
                            if(performanceTeamB[lines-1] < 2.0)
                                cell.add(new Paragraph("").setFont(normalFont));
                            else
                                cell.setBackgroundColor(ColorConstants.DARK_GRAY);
                            break;
                    }
                }
                performanceTableTeamB.addCell(cell);
            }
        }
        cellForPerfB.add(
                new Paragraph("ΑΠΟΔΟΣΗ ΑΝΑ 15 ΛΕΠΤΑ")
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold().setMarginTop(80));
        cellForPerfB.add(performanceTableTeamB);
        tableRowPage1.addCell(cellForPerfB);
    }

    private void divideTheResultsForPerformanceTeamB() {
        if(countersForPerformanceOfTeams[0][3] != 0 )
            performanceTeamB[0] = (double)countersForPerformanceOfTeams[0][2] / (double)countersForPerformanceOfTeams[0][3];
        if(countersForPerformanceOfTeams[1][3] != 0 )
            performanceTeamB[1] = (double)countersForPerformanceOfTeams[1][2] / (double)countersForPerformanceOfTeams[1][3];
        if(countersForPerformanceOfTeams[2][3] != 0 )
            performanceTeamB[2] = (double)countersForPerformanceOfTeams[2][2] / (double)countersForPerformanceOfTeams[2][3];
        if(countersForPerformanceOfTeams[3][3] != 0 )
            performanceTeamB[3] = (double)countersForPerformanceOfTeams[3][2] / (double)countersForPerformanceOfTeams[3][3];
        if(countersForPerformanceOfTeams[4][3] != 0 )
            performanceTeamB[4] = (double)countersForPerformanceOfTeams[4][2] / (double)countersForPerformanceOfTeams[4][3];
        if(countersForPerformanceOfTeams[5][3] != 0 )
            performanceTeamB[5] = (double)countersForPerformanceOfTeams[5][2] / (double)countersForPerformanceOfTeams[5][3];
    }
*/
