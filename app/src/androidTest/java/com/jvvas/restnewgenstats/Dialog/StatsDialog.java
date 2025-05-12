package com.jvvas.restnewgenstats.Dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.LiveDTO;
import com.jvvas.restnewgenstats.R;

import java.util.Objects;

public class StatsDialog extends DialogFragment {

    //Boolean to see if we need to invert teams
    private boolean leftIsA;
    //Live keeps all stats
    private LiveDTO liveMatch;

    private String leftName, rightName;

    private TextView telikesL,apokrouseisL,kornerL,foulL,gemismataL,lathiL,yellowL,redL,offsideL,
            assistsL,
            telikesR,apokrouseisR,kornerR,foulR,gemismataR,lathiR,yellowR,redR,offsideR,assistsR;
    private String[] telikesEustoxes, telikes ,apokrouseis,korner,foul,gemismataEustoxa, gemismata,
            lathi, yellow, red, offside,assists;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.dialog_stats, container, false);

        findViews();
        readData();
        displayStats();

        return rootView;
    }



    public void setLive(LiveDTO live){
        liveMatch = live;
    }

    public void setTeams(String leftName, String rightName){
        this.leftName = leftName;
        this.rightName = rightName;
    }

    public void setLeftIsA(boolean leftIsA){
        this.leftIsA = leftIsA;
    }

    //Everything from the fragment we need
    public void findViews()
    {

        ((TextView)rootView.findViewById(R.id.textView_statsNameL)).setText(leftName);
        ((TextView)rootView.findViewById(R.id.textView_statsNameR)).setText(rightName);
        //All info on the left
        telikesL = rootView.findViewById(R.id.textView_TelikesΑ);
        apokrouseisL = rootView.findViewById(R.id.textView_ApokrouseisA);
        kornerL = rootView.findViewById(R.id.textView_KornerA);
        foulL  = rootView.findViewById(R.id.textView_FoulsA);
        gemismataL = rootView.findViewById(R.id.textView_GemismataA);
        lathiL = rootView.findViewById(R.id.textView_LathiA);
        yellowL = rootView.findViewById(R.id.textView_KitrinesA);
        redL = rootView.findViewById(R.id.textView_KokkinesA);
        offsideL = rootView.findViewById(R.id.textView_OffsidesA);
        assistsL = rootView.findViewById(R.id.textView_AssistA);
        //All info on the right
        telikesR = rootView.findViewById(R.id.textView_TelikesΒ);
        apokrouseisR = rootView.findViewById(R.id.textView_ApokrouseisB);
        kornerR = rootView.findViewById(R.id.textView_KornerB);
        foulR  = rootView.findViewById(R.id.textView_FoulsB);
        gemismataR = rootView.findViewById(R.id.textView_GemismataB);
        lathiR = rootView.findViewById(R.id.textView_LathiB);
        yellowR = rootView.findViewById(R.id.textView_KitrinesB);
        redR = rootView.findViewById(R.id.textView_KokkinesB);
        offsideR = rootView.findViewById(R.id.textView_OffsidesB);
        assistsR = rootView.findViewById(R.id.textView_AssistB);
    }

    //Read data from the live object and save them for display. Need to split them to two parts
    public void readData()
    {
        telikes =
                parseTotal(liveMatch.getPenalty()+","+
                        liveMatch.getApoFaoulOut()+","+
                        liveMatch.getApoKornerOut()+","+
                        liveMatch.getKefaliaOut()+","+
                        liveMatch.getShootOut()+","+
                        liveMatch.getXamenoPenaltyOut()+","+

                        liveMatch.getApoFaoulIn()+","+
                        liveMatch.getApoKornerIn()+","+
                        liveMatch.getKefaliaIn()+","+
                        liveMatch.getShootIn()+","+
                        liveMatch.getXamenoPenaltyIn()).split("#");

        telikesEustoxes =
                parseTotal(liveMatch.getPenalty()+","+
                        liveMatch.getApoFaoulIn()+","+
                        liveMatch.getApoKornerIn()+","+
                        liveMatch.getKefaliaIn()+","+
                        liveMatch.getShootIn()+","+
                        liveMatch.getXamenoPenaltyIn()).split("#");

        gemismata = parseTotal(liveMatch.getGemismaOut()+","+
                liveMatch.getGemismaIn()).split("#");

        gemismataEustoxa = liveMatch.getGemismaIn().split("#");

        korner = parseTotal(liveMatch.getCornerLeft()+","+
                liveMatch.getCornerRight()).split("#");
        apokrouseis = liveMatch.getApokrousi().split("#");
        assists = liveMatch.getAssist().split("#");
        foul  = liveMatch.getFaoulKata().split("#");
        lathi = liveMatch.getLathos().split("#");
        yellow = liveMatch.getYellowCard().split("#");
        red = liveMatch.getRedCard().split("#");
        offside = liveMatch.getOffside().split("#");
    }


    //Some stats consist of many fields. This parses many strings into one final of same format
    public String parseTotal(String manyLines)
    {
        //Every field is divided with ","
        String[] manyValues = manyLines.split(",");
        //These are the final values;
        int valueA = 0,valueB = 0;
        //Go through every string and add the value before "#" to A and the value after to B
        for (String value:manyValues)
        {
            String[] values = value.split("#");
            valueA += Integer.parseInt(values[0]);
            valueB += Integer.parseInt(values[1]);
        }
        //Return the final values
        return valueA+"#"+valueB;
    }


    @SuppressLint("SetTextI18n")
    public void displayStats()
    {
        //According to if left is teamA, change one of the teams to have the 2nd values
        int leftIndex = 0, rightIndex = 0;
        if (leftIsA) rightIndex = 1;
        else leftIndex = 1;
        //Set all left texts
        telikesL.setText(telikes[leftIndex]+"("+telikesEustoxes[leftIndex]+")");
        gemismataL.setText(gemismata[leftIndex]+"("+gemismataEustoxa[leftIndex]+")");
        apokrouseisL.setText(apokrouseis[leftIndex]);
        kornerL.setText(korner[leftIndex]);
        foulL.setText(foul[leftIndex]);
        lathiL.setText(lathi[leftIndex]);
        yellowL.setText(yellow[leftIndex]);
        redL.setText(red[leftIndex]);
        offsideL.setText(offside[leftIndex]);
        assistsL.setText(assists[leftIndex]);
        //Set all right texts
        telikesR.setText(telikes[rightIndex]+"("+telikesEustoxes[rightIndex]+")");
        gemismataR.setText(gemismata[rightIndex]+"("+gemismataEustoxa[rightIndex]+")");
        apokrouseisR.setText(apokrouseis[rightIndex]);
        kornerR.setText(korner[rightIndex]);
        foulR.setText(foul[rightIndex]);
        lathiR.setText(lathi[rightIndex]);
        yellowR.setText(yellow[rightIndex]);
        redR.setText(red[rightIndex]);
        offsideR.setText(offside[rightIndex]);
        assistsR.setText(assists[rightIndex]);
    }

}
