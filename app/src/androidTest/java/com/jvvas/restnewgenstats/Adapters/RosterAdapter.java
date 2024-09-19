package com.jvvas.restnewgenstats.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

    private Team team = new Team("Empty",false);
    private Context context;

    private TextView playerCount;

    public RosterAdapter(Context context)
    {
        this.context = context;
    }

    //Use this to change current team
    public void changeTeam(Team newTeam)
    {
        team = newTeam;
        this.notifyDataSetChanged();
    }

    //Just need to override these methods. Don't need them tho
    @Override
    public int getCount() {
        return team.getAllPlayers().size();
    }

    @Override
    public Object getItem(int i) {
        return team.getAllPlayers().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
            view = ((Activity)context).getLayoutInflater().inflate(R.layout.player_info,null);

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
            selectedReplacement.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (!isChecked && team.getReplacementPlayers().contains(currentPlayer)) {
                                team.getReplacementPlayers().remove(currentPlayer);
                                currentPlayer.setSelection(null);
                            }
                            else if (isChecked &&
                                    !team.getReplacementPlayers().contains(currentPlayer)) {
                                team.addReplacementPlayer(currentPlayer);
                                team.getBasicPlayers().remove(currentPlayer);
                                selectedBasic.setChecked(false);
                                currentPlayer.setSelection(false);
                            }
                            updatePlayerCount();
                        }
                    }
            );

            selectedBasic.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                            if (!isChecked && team.getBasicPlayers().contains(currentPlayer)) {
                                team.getBasicPlayers().remove(currentPlayer);
                                currentPlayer.setSelection(null);
                            }
                            else if (isChecked&&!team.getBasicPlayers().contains(currentPlayer))
                            {
                                if (getBasicSelected()==11)
                                {
                                    selectedBasic.setChecked(false);
                                    Toast.makeText(context,"Already selected 11 players",
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                                team.addBasicPlayer(currentPlayer);
                                team.getReplacementPlayers().remove(currentPlayer);
                                selectedReplacement.setChecked(false);
                                currentPlayer.setSelection(true);
                            }
                            updatePlayerCount();
                        }
                    }
            );

            //Code for checking the check boxes for every player selected before
            if (team.getReplacementPlayers().contains(currentPlayer))
                selectedReplacement.setChecked(true);
            else if (team.getBasicPlayers().contains((currentPlayer)))
                selectedBasic.setChecked(true);

            //Fill all the info for the current player
            playerName.setText(currentPlayer.getName());
            playerNumber.setText(currentPlayer.getNumber());
            playerSurname.setText(currentPlayer.getSurname());
            playerPosition.setText(currentPlayer.getPosition());


            return view;
    }

    public int getBasicSelected() { return team.getBasicPlayers().size(); }
    private int getReplacementSelected() { return team.getReplacementPlayers().size(); }

    public void setPlayerCount(TextView playerCount){
        this.playerCount = playerCount;
    }

    @SuppressLint("SetTextI18n")
    public void updatePlayerCount() {
        if (playerCount!= null) playerCount.setText("B/A:"+getBasicSelected()+"/"
                +getReplacementSelected());
    }

}