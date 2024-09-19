package com.jvvas.restnewgenstats.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

public class ChangePlayerFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;

    private ListView replacements;
    private ReplacementAdapter repAdapter;
    private Team team;
    private TextView playerOut, okButton, escButton;
    private Player playerToChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_player_change, container, false);


        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons()
    {
        okButton = rootView.findViewById(R.id.button_okPlayerChange);
        escButton = rootView.findViewById(R.id.button_escPlayerChange);
        playerOut = rootView.findViewById(R.id.textView_playerOut);
        replacements = rootView.findViewById(R.id.listView_replacements);
        repAdapter = new ReplacementAdapter();
        replacements.setAdapter(repAdapter);
    }

    private void addClickOnButtons()
    {
        //On clicking an item from the list make that the selected player
        replacements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                repAdapter.setSelected(position);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start by checking if we have selected a player both for in and out
                if(repAdapter.selectedIn >= 0 && playerToChange != null)
                {
                    //Add the event and make the player change
                    rootActivity.addEvent("ΑΛΛΑΓΗ | ΕΞΩ:" + playerToChange.getNumber() +" ΜΕΣΑ:"+
                            team.getReplacementPlayers().get(repAdapter.selectedIn).getNumber()
                            +"| "+ (rootActivity.getIsTeamAForAction() ? "A" : "B") +" |");
                    rootActivity.makePlayerChange(team,
                            team.getReplacementPlayers()
                                    .get(repAdapter.selectedIn), playerToChange);
                    //Then return to field fragment
                    rootActivity.returnToFieldFrag();
                }
                else
                    //Tell user he needs to select more
                    Toast.makeText(getContext(),
                            "Select both player IN and OUT", Toast.LENGTH_LONG).show();
            }
        });

        //Esc just returns to field fragment
        escButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.returnToFieldFrag();
            }
        });
    }

    //This is needed to give the fragment the team that changes a player
    public void setTeam(Team team)
    {
        this.team = team;
    }

    //Change the player that is being changed and display his info
    @SuppressLint("SetTextI18n")
    public void setPlayerOut(Player player)
    {
        playerToChange = player;
        playerOut.setText(playerToChange.getSurname()
                +" "+playerToChange.getName()+"     "
                +playerToChange.getNumber());
    }

    //Adapter for displaying the replacement players
    class ReplacementAdapter extends BaseAdapter
    {
        //Default value is -1 so no player is selected to be replaced
        int selectedIn = -1;
        @Override
        public int getCount() {
            return team.getReplacementPlayers().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void setSelected(int position)
        {
            selectedIn = position;
            notifyDataSetChanged();
        }


        @SuppressLint({"SetTextI18n", "ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.replacement_player_info, null);
            if (i == selectedIn)
                view.setBackgroundColor(team.getColour());

            Player currentPlayer = team.getReplacementPlayers().get(i);

            TextView name = view.findViewById(R.id.textView_playerInName);
            TextView number = view.findViewById(R.id.textView_playerInNum);

            name.setText(currentPlayer.getSurname()+" "+currentPlayer.getName());
            number.setText(currentPlayer.getNumber());
            return view;
        }
    }

}