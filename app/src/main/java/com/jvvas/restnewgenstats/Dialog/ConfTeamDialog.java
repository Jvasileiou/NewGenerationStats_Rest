package com.jvvas.restnewgenstats.Dialog;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;
import com.jvvas.restnewgenstats.Adapters.RosterAdapter;

import java.util.Objects;

public class ConfTeamDialog extends DialogFragment implements ColorDialog.OnInputSelected{

    private View rootView;
    private CountStatsActivity rootActivity;
    private TextView ok,title,coach;
    private ImageView colour;
    private ListView playerList;
    private RosterAdapter adapter;

    private Team team;
    private TeamDTO teamDto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_configure_team, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        findItems();
        setOnClicks();
        setCancelable(false);
        prepareListAndTexts();

        return rootView;
    }

    private void findItems()
    {
        ok = rootView.findViewById(R.id.textView_OkOfConf);
        title = rootView.findViewById(R.id.textView_configureTitle);
        coach = rootView.findViewById(R.id.textView_editCoach);

        playerList = rootView.findViewById(R.id.listView_rosterConf);
        colour = rootView.findViewById(R.id.imageView_teamColour);
    }

    private void setOnClicks()
    {
        //Long clicking a player should open the edit player dialog for him
        playerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = rootActivity.getSupportFragmentManager();
                PlayerInfoDialog statsDialog = new PlayerInfoDialog();
                statsDialog.setEditedPlayer((Player) adapter.getItem(i));

                statsDialog.setListToUpdate(adapter);
                statsDialog.show(fm, "");
                return false;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getBasicSelected() != 11)
                    Toast.makeText(rootActivity,"Select 11 basic players",Toast.LENGTH_LONG).show();
                else
                {
                    team.setCoachFullName(coach.getText().toString());
                    ColorDrawable drawable = (ColorDrawable) colour.getBackground();
                    team.setColour(drawable.getColor());
                    team.sortAllByNumber();
                    rootActivity.configureEnd();
                    getDialog().dismiss();
                }
            }
        });

        colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        coach.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentManager fm = rootActivity.getSupportFragmentManager();
                CoachInfoDialog statsDialog = new CoachInfoDialog();

                statsDialog.setTeams(teamDto, team);

                statsDialog.show(fm, "");
                return false;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void prepareListAndTexts()
    {
        title.setText("ΕΠΙΛΟΓΕΣ "+team.getName());
        coach.setText(team.getCoachFullName());
        colour.setBackgroundColor(team.getColour());

        adapter = new RosterAdapter(rootActivity);
        adapter.changeTeam(team);
        playerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeamDTO(TeamDTO teamDto){
        this.teamDto = teamDto;
    }

    private void openColorPicker() {
        ColorDialog colorDialog = new ColorDialog();
        colorDialog.setmOnInputSelected(this);
        colorDialog.show(Objects.requireNonNull(getFragmentManager()),"ColorDialog");
    }

    @Override
    public void sendInput(String input) {
        int currentColor = Color.parseColor(input);
        colour.setBackgroundColor(currentColor);
    }
}
