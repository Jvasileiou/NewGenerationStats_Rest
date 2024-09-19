package com.jvvas.restnewgenstats.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.Fragments.ChangePlayerFragment;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

import java.util.Objects;

public class BasicPlayerAdapter extends BaseAdapter {
    private CountStatsActivity rootActivity;
    private Team team;
    private boolean isLeft;
    private int pixels;

    @Override
    public int getCount() {
        return team.getBasicPlayerNumber();
    }

    @Override
    public Object getItem(int i) {
        return team.getBasicPlayer(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (isLeft)
            view = LayoutInflater.from(rootActivity).inflate(R.layout.basic_player_one, viewGroup,false);
        else
            view = LayoutInflater.from(rootActivity).inflate(R.layout.basic_player_two, viewGroup,false);

        // Find the view's items
        Button playerButton = view.findViewById(R.id.button_number);
        final ImageView redCard = view.findViewById(R.id.imageView_redCard);
        final ImageView yellowCard = view.findViewById(R.id.imageView_yellowCardOne);
        final ImageView secYellowCard = view.findViewById(R.id.imageView_yellowCardTwo);
        Button plusSign = view.findViewById(R.id.button_plusSign);


        // Get the player and change visibility and appearance of items and set info
        final Player currentPlayer = team.getBasicPlayer(i);

        playerButton.setBackgroundColor(team.getColour());
        fixCross(plusSign);
        // Cards
        if (!currentPlayer.hasRedCard())
            redCard.setVisibility(View.INVISIBLE);

        if (currentPlayer.getYellowCards() == 0) {
            yellowCard.setVisibility(View.INVISIBLE);
            secYellowCard.setVisibility(View.INVISIBLE);
        } else if (currentPlayer.getYellowCards() == 1) {
            secYellowCard.setVisibility(View.INVISIBLE);
        }
        // Player button
        if (currentPlayer.getPosition().equals("GK"))
            playerButton.setText(currentPlayer.getNumber() + "(T)");
        else
            playerButton.setText(currentPlayer.getNumber());
        // Selected player button change
        if (rootActivity.getAmForAction().equals(currentPlayer.getPlayerId())) {
            playerButton.setBackgroundColor(Color.BLACK);
            playerButton.setTextColor(Color.WHITE);
        } else
            playerButton.setTextColor(Color.BLACK);

        // Add buttons onClick methods
        plusSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rootActivity.getButtonsUnclickable()) return;
                addForwardingPass(currentPlayer);
            }
        });

        playerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rootActivity.getButtonsUnclickable()) return;
                if (currentPlayer.hasRedCard()) {
                    rootActivity.showToast("Player has a Red Card");
                } else {
                    rootActivity.fadeCardButtons();
                    switch (rootActivity.getPlayerButtonAction()) {
                        case "red":
                            redCase(currentPlayer);
                            break;
                        case "yellow":
                            yellowCase(currentPlayer);
                            break;
                        case "changePlayer":
                            changePlayerCase(currentPlayer);
                            break;
                        default:
                            defaultCase(currentPlayer);
                    }
                }
                rootActivity.notifyAdapters();
            }
        });


        return view;
    }

    private void defaultCase(Player currentPlayer){
        rootActivity.setPlayerButtonFields(currentPlayer.getNumber(),
                team.getIsTeamA(), currentPlayer.getPlayerId());
    }

    private void redCase(Player currentPlayer){
        currentPlayer.setHasRedCard(true);
        rootActivity.addEvent("ΚΟΚΚΙΝΗ", "redCard", currentPlayer.getNumber(), team.getIsTeamA());
        rootActivity.changeStat("redCard", team.getIsTeamA(), true);
        rootActivity.restoreDefaultValues();
    }

    private void yellowCase(Player currentPlayer){
        if (currentPlayer.getYellowCards() == 0) {
            currentPlayer.setYellowCards(1);
            rootActivity.addEvent("ΚΙΤΡΙΝΗ", "yellowCard",
                    currentPlayer.getNumber(), team.getIsTeamA());
        } else if (currentPlayer.getYellowCards() == 1) {
            currentPlayer.setYellowCards(2);
            rootActivity.addEvent("2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ", "yellowCard",
                    currentPlayer.getNumber(), team.getIsTeamA());
            rootActivity.changeStat("redCard",team.getIsTeamA(), true);
        }
        rootActivity.changeStat("yellowCard",team.getIsTeamA(), true);
        rootActivity.restoreDefaultValues();
    }

    private void changePlayerCase(Player currentPlayer){
        ChangePlayerFragment changePlayer = rootActivity.getChangePlayer();
        if (changePlayer != null) {
            changePlayer.setPlayerOut(currentPlayer);
            rootActivity.setPlayerButtonFields(currentPlayer.getNumber(),
                    team.getIsTeamA(), currentPlayer.getPlayerId());
        }
    }

    private void addForwardingPass(Player currentPlayer){
        if (currentPlayer.hasRedCard())
            rootActivity.showToast("Player has a red card");

        rootActivity.addEvent("ΠΡΟΩΘΗΜΕΝΗ ΠΑΣΑ", "forwardingPass", currentPlayer.getNumber(), team.getIsTeamA());
        rootActivity.changeStat("forwardingPass", team.getIsTeamA(), true);
    }

    private void fixCross(Button plusSign) {
        LayerDrawable layers = (LayerDrawable) plusSign.getBackground();
        ((GradientDrawable) layers.findDrawableByLayerId(R.id.blackLineOne)).setStroke(pixels, team.getColour());
        RotateDrawable rotate = (RotateDrawable) layers.findDrawableByLayerId(R.id.blackLineTwo);
        ((GradientDrawable) Objects.requireNonNull(rotate.getDrawable())).setStroke(pixels, team.getColour());
    }

    public BasicPlayerAdapter(CountStatsActivity rootActivity, Team team, boolean isLeft, int pixels) {
        this.rootActivity = rootActivity;
        this.team = team;
        this.isLeft = isLeft;
        this.pixels = pixels;
    }

    public void setTeam(Team team) {
        this.team = team;
        notifyDataSetChanged();
    }

    public Team getTeam(){
        return team;
    }
}
