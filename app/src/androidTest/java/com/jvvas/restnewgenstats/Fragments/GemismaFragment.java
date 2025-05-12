package com.jvvas.restnewgenstats.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

public class GemismaFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private TextView textView_EpitiximenoGemisma, textView_ApotiximenoGemisma,
            textView_EscOfGemisma, textView_OkOfGemisma ;
    private Group hideForInteraction,showForInteraction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gemisma, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons() {
        textView_EscOfGemisma = rootView.findViewById(R.id.textView_EscOfGemisma);
        textView_OkOfGemisma = rootView.findViewById(R.id.textView_OkOfGemisma);
        textView_EpitiximenoGemisma = rootView.findViewById(R.id.textView_EpitiximenoGemisma);
        textView_ApotiximenoGemisma = rootView.findViewById(R.id.textView_ApotiximenoGemisma);
        hideForInteraction = rootView.findViewById(R.id.group_hideForInteraction);
        showForInteraction = rootView.findViewById(R.id.group_showForInteraction);
        showForInteraction.setVisibility(View.GONE);
    }

    private void addClickOnButtons() {
        textView_EpitiximenoGemisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.changeStat("gemismaIn",true);
                    rootActivity.addEventToList("ΕΠΙΤ ΓΕΜ | ");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textView_ApotiximenoGemisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    showForInteraction.setVisibility(View.VISIBLE);
                    hideForInteraction.setVisibility(View.GONE);
                    rootActivity.startScaleAnimationForTextViews(textView_ApotiximenoGemisma);
                    rootActivity.changeStat("gemismaOut",true);
                    rootActivity.hideThisTeam();
                    rootActivity.addEventToList("ΑΠΟΤ ΓΕΜ | ");
                    rootActivity.restoreDefaultValues();
                }

            }
        });

        textView_OkOfGemisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.changeStat("epemvasi",true);
                    rootActivity.addEventToList("ΕΠΕΜΒΑΣΗ | ");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textView_EscOfGemisma.setOnClickListener(new View.OnClickListener() {
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
