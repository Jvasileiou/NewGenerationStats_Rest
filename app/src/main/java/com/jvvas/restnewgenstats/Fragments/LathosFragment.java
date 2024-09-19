package com.jvvas.restnewgenstats.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

public class LathosFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private CheckBox checkBoxKontrol, checkBoxPasa ;
    private TextView textViewEscOfLathos, textViewOkOfLathos;
    private Group hideForSteal,showForSteal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lathos, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }


    private void initializeTheButtons() {
        checkBoxKontrol = rootView.findViewById(R.id.checkBox_Kontrol);
        checkBoxPasa = rootView.findViewById(R.id.checkBox_Pasa);
        textViewEscOfLathos = rootView.findViewById(R.id.textView_EscOfLathos);
        textViewOkOfLathos = rootView.findViewById(R.id.textView_OkOfLathos);
        hideForSteal = rootView.findViewById(R.id.group_hideForSteal);
        showForSteal = rootView.findViewById(R.id.group_showForSteal);
        showForSteal.setVisibility(View.GONE);
    }

    private void addClickOnButtons() {

        checkBoxKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mistakeAdded("ΛΑΘΟΣ ΚΟΝΤΡΟΛ");
            }
        });

        checkBoxPasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mistakeAdded("ΛΑΘΟΣ ΚΑΚΗ ΠΑΣΑ");
            }
        });

        textViewOkOfLathos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.changeStat("klepsimo",true);
                    rootActivity.addEvent("ΚΛΕΨΙΜΟ", "klepsimo");
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textViewEscOfLathos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.returnToFieldFrag();
            }
        });
    }

    private void mistakeAdded(String event) {

        if (isNotDefaultActionValue()){
            rootActivity.changeStat("lathos",true);
            rootActivity.hideThisTeam();
            hideForSteal.setVisibility(View.GONE);
            showForSteal.setVisibility(View.VISIBLE);
            rootActivity.addEvent(event,"lathos");
            rootActivity.restoreDefaultValues();
            makeBothCheckboxesFalse();
        }
    }

    private void makeBothCheckboxesFalse()
    {
        checkBoxKontrol.setChecked(false);
        checkBoxPasa.setChecked(false);
    }



    private boolean isNotDefaultActionValue() {
        if (rootActivity.isDefaultValues())
        {
            makeBothCheckboxesFalse();
            Toast.makeText(getContext(), "Click on a PLAYER", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}

