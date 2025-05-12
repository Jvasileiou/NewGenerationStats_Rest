package com.jvvas.restnewgenstats.Dialog;


import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.Fragments.FieldFragment;
import com.jvvas.restnewgenstats.Objects.DataLiveActions;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditEventDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private View rootView;

    private FieldFragment fieldFragment;
    private Button minIncrease, minDecrease, changeTeam, esc, confirm;
    private TextView current_min, team_name;
    private Spinner spinnerEvent, spinnerPlayer, spinnerPeriod;
    private ArrayAdapter<CharSequence> adapterEvent, adapterPlayer, adapterPeriod;

    private String[] playerList = {" ", " "};
    private Team teamA, teamB;

    private DataLiveActions event;
    private boolean currTeamIsA = true;

    private CountStatsActivity rootActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.dialog_edit_event, container, false);
        //Find the editTexts and set theirListeners
        findViews();
        makeAdapters();
        initListeners();
        openField();

        setEventInfo();
        return rootView;
    }

    // ____________________________________ DIALOG INIT ___________________________________________
    //Find all editTexts the dialog contains
    private void findViews() {
        spinnerEvent = rootView.findViewById(R.id.spinner_event_string);
        spinnerPlayer = rootView.findViewById(R.id.spinner_event_player);
        spinnerPeriod = rootView.findViewById(R.id.spinner_event_period);

        minIncrease = rootView.findViewById(R.id.button_min_increase);
        minDecrease = rootView.findViewById(R.id.button_min_decrease);
        changeTeam = rootView.findViewById(R.id.button_change_team);

        current_min = rootView.findViewById(R.id.textView_event_min);
        team_name = rootView.findViewById(R.id.textView_event_team);

        // Esc/Confirm buttons on the bottom of the dialog
        esc = rootView.findViewById(R.id.button_event_esc);
        confirm = rootView.findViewById(R.id.button_event_confirm);

    }

    private void makeAdapters() {
        adapterEvent = ArrayAdapter.createFromResource(
                Objects.requireNonNull(getContext()), R.array.event_strings,
                android.R.layout.simple_spinner_item);
        adapterEvent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEvent.setAdapter(adapterEvent);
        spinnerEvent.setBackgroundResource(R.drawable.info_edittext_background);
        spinnerEvent.setOnItemSelectedListener(this);
        // -----------------------------------------------------------------------------------------
        adapterPlayer = new ArrayAdapter<CharSequence>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, playerList);

        adapterPlayer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPlayer.setAdapter(adapterPlayer);
        spinnerPlayer.setBackgroundResource(R.drawable.info_edittext_background);
        // -----------------------------------------------------------------------------------------
        adapterPeriod = ArrayAdapter.createFromResource(
                Objects.requireNonNull(getContext()), R.array.game_times,
                android.R.layout.simple_spinner_item);
        adapterPeriod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPeriod.setAdapter(adapterPeriod);
        spinnerPeriod.setBackgroundResource(R.drawable.info_edittext_background);
    }

    private void initListeners() {
        changeTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currTeamIsA = !currTeamIsA;
                setUpTeam(currTeamIsA ? teamA : teamB);
            }
        });
        minIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_min.setText(String.valueOf(Integer.parseInt(current_min.getText().toString()) + 1));
            }
        });
        minDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int min = Integer.parseInt(current_min.getText().toString());
                if (min > 1)
                    current_min.setText(String.valueOf(min - 1));
            }
        });

        //Esc should just close the dialog
        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //Confirm should be making a new player and notify both database and the roster list
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (event != null)
                    updateEvent();
                else
                    makeEvent();
                rootActivity.sortEvents();
                rootActivity.notifyAdapters();
                dismiss();
            }
        });
    }

    private void openField() {
        fieldFragment = new FieldFragment();
        fieldFragment.setInCountStats(false);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fieldFragment);
        fragmentTransaction.commit();
    }

    public void setTeams(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    private void setUpTeam(Team team) {
        team_name.setText(team.getName());
        playerList = team.getPlayerNumberArray();
        adapterPlayer = new ArrayAdapter<CharSequence>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, playerList);
        spinnerPlayer.setAdapter(adapterPlayer);
        spinnerPlayer.setSelection(0);
    }

    public void setEvent(DataLiveActions event) {
        this.event = event;
    }

    private void setEventInfo() {
        if (event != null) {
            currTeamIsA = event.isTeamA();
            setUpTeam(currTeamIsA ? teamA : teamB);
            current_min.setText(String.valueOf(event.getMinute()));
            spinnerPeriod.setSelection(adapterPeriod.getPosition(event.getTimePeriod()));
            if (event.isRaw() || event.getEvent().contains("ΑΛΛΑΓΗ")){
                rawEvent();
                return;
            }
            spinnerPlayer.setSelection(adapterPlayer.getPosition(event.getPlayer()));
            spinnerEvent.setSelection(adapterEvent.getPosition(event.getEvent()));

            if (event.isJustTeam()) {
                spinnerPlayer.setEnabled(false);
            }
            setAcceptedChanges();
            spinnerEvent.setSelection(adapterEvent.getPosition(event.getEvent()));
        } else {
            setUpTeam(currTeamIsA ? teamA : teamB);
            current_min.setText(String.valueOf(((CountStatsActivity) getActivity()).getMinute()));
            spinnerPeriod.setSelection(adapterPeriod.getPosition(rootActivity.getTimePeriod()));
        }

    }

    private void rawEvent(){
        String[] rawText = new String[1];
        rawText[0] = event.getEvent();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, rawText);
        spinnerEvent.setAdapter(adapter);

        spinnerEvent.setEnabled(false);
        spinnerPlayer.setEnabled(false);
        changeTeam.setEnabled(false);
    }


    private void setAcceptedChanges() {
        String[] newArray;
        String[] eventStrings = getResources().getStringArray(R.array.event_strings);

        int position = adapterEvent.getPosition(event.getEvent());
        if (position == 1 || position == 2) // corner
            newArray = Arrays.copyOfRange(eventStrings, 1, 3);
        else if (position == 8 || position == 9) // dieisdusi
            newArray = Arrays.copyOfRange(eventStrings, 8, 10);
        else if (position >= 10 && position <= 14) // foul
            newArray = Arrays.copyOfRange(eventStrings, 10, 15);
        else if (position == 15 || position == 16) // foul yper
            newArray = Arrays.copyOfRange(eventStrings, 15, 17);
        else if (position == 17 || position == 18) // gemisma
            newArray = Arrays.copyOfRange(eventStrings, 17, 19);
        else if (position >= 21 && position <= 30) // goal
            newArray = Arrays.copyOfRange(eventStrings, 21, 31);
        else if (position == 32 || position == 33) // lathos
            newArray = Arrays.copyOfRange(eventStrings, 32, 34);
        else if (position >= 36 && position <= 67) // teliki
            newArray = Arrays.copyOfRange(eventStrings, 36, 68);
        else {
            spinnerEvent.setEnabled(false);
            return;
        }

        adapterEvent = new ArrayAdapter<CharSequence>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, newArray);
        spinnerEvent.setAdapter(adapterEvent);
    }

    private void updateEvent() {
        event.setMinute(Integer.parseInt(current_min.getText().toString()));
        event.setTimePeriod(spinnerPeriod.getSelectedItem().toString());
        if (!event.isRaw() && !event.getEvent().contains("ΑΛΛΑΓΗ")) {
            rootActivity.deletedEvent(event);
            event.setEvent(spinnerEvent.getSelectedItem().toString());

            event.setIsTeamA(currTeamIsA);
            if (!event.isJustTeam()) {
                event.setPlayer(spinnerPlayer.getSelectedItem().toString());
                event.setLocation(fieldFragment.getLocation());
            }
            updateStatsForNewEvent(event);
        }
    }

    private void makeEvent() {
        if (spinnerEvent.getSelectedItemPosition() < 3)
            event = new DataLiveActions(Integer.parseInt(current_min.getText().toString()),
                    spinnerEvent.getSelectedItem().toString(),
                    "STAT", // gets changed below, so it doesn't matter
                    currTeamIsA,
                    spinnerPeriod.getSelectedItem().toString(),
                    true);
        else
            event = new DataLiveActions(Integer.parseInt(current_min.getText().toString()),
                    spinnerEvent.getSelectedItem().toString(),
                    "STAT", // gets changed below, so it doesn't matter
                    spinnerPlayer.getSelectedItem().toString(),
                    currTeamIsA,
                    fieldFragment.getLocation(),
                    spinnerPeriod.getSelectedItem().toString(),
                    true);
        event.setStat(event.findStat());
        rootActivity.getEventAdapter().addEvent(event);
        updateStatsForNewEvent(event);
    }

    private void updateStatsForNewEvent(DataLiveActions newEvent){
        if (eventContainsCards())
            checkCards();
        if (!newEvent.getStat().equals(""))
            rootActivity.changeStat(newEvent.getStat(),newEvent.isTeamA(),true);
        if (newEvent.getEvent().contains("ΓΚΟΛ")) {
            if (newEvent.getEvent().contains("ΑΥΤΟΓΚΟΛ"))
                rootActivity.changeStat("goal", !event.isTeamA(), true);
            else
                rootActivity.changeStat("goal", event.isTeamA(), true);
        }else if (newEvent.getEvent().contains("ΔΟΚΑΡΙ"))
            rootActivity.changeStat("dokari",event.isTeamA(),true);
        else if (newEvent.getEvent().contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))
            rootActivity.changeStat("xameniEukairia",event.isTeamA(),true);
    }

    private void checkCards() {
        String selectedItem = (String) spinnerEvent.getSelectedItem();
        Team newEventTeam = currTeamIsA ? teamA : teamB;

        switch (selectedItem) {
            case "ΚΙΤΡΙΝΗ":
                newEventTeam.getPlayer(spinnerPlayer.getSelectedItem().toString())
                        .setYellowCards(1);
                break;
            case "2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ":
                newEventTeam.getPlayer(spinnerPlayer.getSelectedItem().toString())
                        .setYellowCards(2);
                break;
            case "ΚΟΚΚΙΝΗ":
                newEventTeam.getPlayer(spinnerPlayer.getSelectedItem().toString())
                        .setHasRedCard(true);
                break;
        }

    }

    public void setRootActivity(CountStatsActivity rootActivity) {
        this.rootActivity = rootActivity;
    }

    private boolean eventContainsCards(){
        return ((String) spinnerEvent.getSelectedItem()).contains("ΚΙΤΡΙΝΗ") ||
                ((String) spinnerEvent.getSelectedItem()).contains("ΚΟΚΚΙΝΗ");
    }
    //Implementing the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (event == null && position < 3) spinnerPlayer.setEnabled(false);
        else spinnerPlayer.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

