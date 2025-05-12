package com.jvvas.restnewgenstats.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

import java.util.Objects;

public class GoalFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private SwitchCompat switchApoStatikiFasi;
    private TextView textViewGoal_Sout, textViewGoal_Kefalia,textViewGoal_Penalty,
            textViewGoal_ApFaoul, textViewGoal_ApoKorner, textViewGoal_Autogoal ,
            textView_EscOfGoal, textView_OkOfGoalAssist;
    private String goalNumber;
    private Group hideForAssist, showForAssist;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_goal, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons() {
        textView_EscOfGoal = rootView.findViewById(R.id.textView_EscOfGoal);
        textView_OkOfGoalAssist = rootView.findViewById(R.id.textView_YesOfAssistGoal);
        textViewGoal_Autogoal = rootView.findViewById(R.id.textView_Autogoal);
        textViewGoal_Sout = rootView.findViewById(R.id.textViewGoal_Sout);
        textViewGoal_Kefalia = rootView.findViewById(R.id.textViewGoal_Kefalia);
        textViewGoal_Penalty =  rootView.findViewById(R.id.textViewGoal_Penalty);
        textViewGoal_ApFaoul = rootView.findViewById(R.id.textViewGoal_ApeFaoul);
        textViewGoal_ApoKorner = rootView.findViewById(R.id.textViewGoal_ApoKorner);
        switchApoStatikiFasi = rootView.findViewById(R.id.switchApoStatikiFasi);
        hideForAssist = rootView.findViewById(R.id.group_hideForAssist);
        showForAssist = rootView.findViewById(R.id.group_showForAssit);
        showForAssist.setVisibility(View.GONE);
    }


    private void addClickOnButtons() {

        textViewGoal_Sout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick((TextView)v, "shootIn", "ΓΚΟΛ ΑΠΟ ΣΟΥΤ");
            }
        });

        textViewGoal_Kefalia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick((TextView)v, "kefaliaIn", "ΓΚΟΛ ΑΠΟ ΚΕΦΑΛΙΑ");
            }
        });

        textViewGoal_Penalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick((TextView)v, "penalty", "ΓΚΟΛ ΑΠΟ ΠΕΝΑΛΤΙ");
            }
        });

        textViewGoal_ApFaoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick((TextView)v, "apoFaoulIn", "ΓΚΟΛ ΑΠΟ ΦΑΟΥΛ");
            }
        });

        textViewGoal_ApoKorner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOnClick((TextView)v, "apoKornerIn", "ΓΚΟΛ ΑΠΟ ΚΟΡΝΕΡ");
            }
        });

        textViewGoal_Autogoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.startScaleAnimationForTextViews(textViewGoal_Autogoal);
                    rootActivity.changeStat("goal",!rootActivity.getIsTeamAForAction(),true);
                    rootActivity.addEvent("ΑΥΤΟΓΚΟΛ", "");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textView_EscOfGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchApoStatikiFasi.setChecked(false);
                rootActivity.returnToFieldFrag();
            }
        });

        textView_OkOfGoalAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    if (((CountStatsActivity) Objects.requireNonNull(getActivity()))
                            .getNumberOfShirt().equals(goalNumber))
                    {
                        Toast.makeText(getContext(), "Click on a PLAYER to add ASSIST",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    rootActivity.changeStat("assist",true);
                    rootActivity.addEvent("ΑΣΙΣΤ", "assist");
                    rootActivity.returnToFieldFrag();
                    switchApoStatikiFasi.setChecked(false);
                }
            }
        });

    }

    //This is what all buttons about scoring a goal do
    private void buttonOnClick(TextView selected, String stat, String event)
    {
        //Start by checking that a player is selected
        if (isNotDefaultActionValue()){
            //Increase goal stat,play an animation and hide the opponent team to possibly add assist
            rootActivity.changeStat("goal",true);
            rootActivity.startScaleAnimationForTextViews(selected);
            rootActivity.hideOtherTeam();
            //Then also increase the stat about the way of scoring
            rootActivity.changeStat(stat,true);
            //Finally add the event, note who scored it and hide everything for maybe adding assist
            rootActivity.addEvent(checkForTheSwitches(event),stat);
            goalNumber = rootActivity.getNumberOfShirt();
            showForAssist.setVisibility(View.VISIBLE);
            hideForAssist.setVisibility(View.GONE);
        }
    }

    //Updates the event to be added if we had statikiFasi
    private String checkForTheSwitches(String message)
    {
        if (switchApoStatikiFasi.isChecked())
        {
            message += " + ΣΤΑΤΙΚΗ ΦΑΣΗ";
            switchApoStatikiFasi.setChecked(false);
        }
        return message;
    }

    //Method that checks if there is a player selected, so we can add stats/events
    private boolean isNotDefaultActionValue() {
        if (rootActivity.isDefaultValues())
        {
            Toast.makeText(getContext(), "Click on a PLAYER", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
