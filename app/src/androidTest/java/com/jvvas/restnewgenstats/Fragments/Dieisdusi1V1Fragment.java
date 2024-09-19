package com.jvvas.restnewgenstats.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

public class Dieisdusi1V1Fragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private TextView textView_EpitiximeniDieisdusi, textView_ApotiximeniDieisdusi,
            textView_EscOf1V1, textView_OkOf1V1 ;
    private Group hideForStop,showForStop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dieisdusi1_v1, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons() {
        textView_EscOf1V1 = rootView.findViewById(R.id.textView_EscOfDieisdusi);
        textView_OkOf1V1 = rootView.findViewById(R.id.textView_OkOfDieisdusi);

        textView_EpitiximeniDieisdusi = rootView.findViewById(R.id.textView_EpituximeniDieisdusi);
        textView_ApotiximeniDieisdusi = rootView.findViewById(R.id.textView_ApotuximeniDieisdusi);
        hideForStop = rootView.findViewById(R.id.group_hideForStop);
        showForStop = rootView.findViewById(R.id.group_showForStop);
        showForStop.setVisibility(View.GONE);
    }

    private void addClickOnButtons() {

        textView_EpitiximeniDieisdusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.changeStat("dieisdusiIn",true);
                    rootActivity.addEventToList("ΕΠΙΤ ΔΙΕΙΣΔΥΣΗ | ");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textView_ApotiximeniDieisdusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    showForStop.setVisibility(View.VISIBLE);
                    hideForStop.setVisibility(View.INVISIBLE);
                    rootActivity.startScaleAnimationForTextViews(textView_ApotiximeniDieisdusi);
                    rootActivity.changeStat("dieisdusiOut",true);
                    rootActivity.addEventToList("ΑΠΟΤ ΔΙΕΙΣΔΥΣΗ | ");
                    rootActivity.restoreDefaultValues();
                    rootActivity.hideThisTeam();
                }

            }
        });

        textView_OkOf1V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNotDefaultActionValue()){
                    rootActivity.changeStat("kopsimo",true);
                    rootActivity.addEventToList("ΚΟΨΙΜΟ | ");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textView_EscOf1V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.returnToFieldFrag();
            }
        });
    }

    private boolean isNotDefaultActionValue() {
        if (rootActivity.isDefaultValues())
        {
            Toast.makeText(getContext(), "Click on a PLAYER", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
