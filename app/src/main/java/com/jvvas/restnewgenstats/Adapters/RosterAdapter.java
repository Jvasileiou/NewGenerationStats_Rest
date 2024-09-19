package com.jvvas.restnewgenstats.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

public class RosterAdapter extends BaseAdapter {

    private Team team;
    private Context context;

    private TextView playerCount;

    public RosterAdapter(Context context) {
        this.context = context;
    }


    // ______________________________ ADAPTER IMPLEMENTATION ______________________________________
    //Just need to override these methods. Don't need them tho
    @Override
    public int getCount() {
        if (team != null)
            return team.getAllPlayers().size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (team != null)
            return team.getAllPlayers().get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.player_info, viewGroup, false);

        //Get every item in the view so we can change appropriately for every item
        final TextView playerName = view.findViewById(R.id.textView_name);
        final TextView playerSurname = view.findViewById(R.id.textView_surname);
        final TextView playerNumber = view.findViewById(R.id.textView_number);
        final TextView playerPosition = view.findViewById(R.id.textView_position);
        final CheckBox selectedBasic = view.findViewById(R.id.checkBox_selectedB);
        final CheckBox selectedReplacement = view.findViewById(R.id.checkBox_selectedA);

        //Access the current player
        final Player currentPlayer = team.getAllPlayers().get(i);
        //Code for making the checkboxes unable to be selected if a number of them
        //from the same category is already selected
        // --------------------------------REPLACEMENT-----------------------------------------
        selectedReplacement.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked && currentPlayer.isReplacement()) {
                            currentPlayer.setSelection(null);
                        } else if (isChecked && !currentPlayer.isReplacement()) {
                            selectedBasic.setChecked(false);
                            currentPlayer.setSelection(false);
                        }
                        updatePlayerCount();
                    }
                }
        );
        // ----------------------------------- BASIC ------------------------------------------
        selectedBasic.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked && currentPlayer.isBasic()) {
                            currentPlayer.setSelection(null);
                        } else if (isChecked && !currentPlayer.isBasic()) {
                            if (getBasicSelected() == 11) {
                                selectedBasic.setChecked(false);
                                Toast.makeText(context, "Already selected 11 players",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            selectedReplacement.setChecked(false);
                            currentPlayer.setSelection(true);
                        }
                        updatePlayerCount();
                    }
                }
        );

        //Code for checking the check boxes for every player selected before
        if (currentPlayer.isBasic())
            selectedBasic.setChecked(true);
        else if (currentPlayer.isReplacement())
            selectedReplacement.setChecked(true);

        //Fill all the info for the current player
        playerName.setText(currentPlayer.getName());
        playerNumber.setText(currentPlayer.getNumber());
        playerSurname.setText(currentPlayer.getSurname());
        playerPosition.setText(currentPlayer.getPosition());


        return view;
    }

    // __________________________________ ACCESSORS MUTATORS ______________________________________
    public int getBasicSelected() {
        return team.getBasicPlayerNumber();
    }

    public String findIfThereIsAnyInvalidInput() {
        return team.checkIfThereIsAnyInvalidInput();
    }

    private int getReplacementSelected() {
        return team.getReplacementPlayerNumber();
    }

    public void setPlayerCount(TextView playerCount) {
        this.playerCount = playerCount;
    }

    @SuppressLint("SetTextI18n")
    public void updatePlayerCount() {
        if (playerCount != null) playerCount.setText("B/A:" + getBasicSelected() + "/"
                + getReplacementSelected());
    }

    //Use this to change current team
    public void changeTeam(Team newTeam) {
        team = newTeam;
        this.notifyDataSetChanged();
    }

}