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

public class TelikiFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private TextView textView_SoutEntos, textView_KefaliaEntos, textView_ApoFaoulEntos,
            textView_XamenoPenaltyEntos, textView_SoutEktos, textView_KefaliaEktos,
            textView_ApoFaoulEktos, textView_XamenoPenaltyEktos, textView_EscOfTeliki ,
            textView_OkOfTeliki;
    private SwitchCompat switchXameniEukairia, switchDokari;
    private Group showForSave,hideForSave;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_teliki, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons() {
        textView_EscOfTeliki = rootView.findViewById(R.id.textView_EscOfTeliki);
        textView_OkOfTeliki = rootView.findViewById(R.id.textView_OkOfTeliki);
        textView_SoutEntos = rootView.findViewById(R.id.textView_SoutEntos);
        textView_KefaliaEntos = rootView.findViewById(R.id.textView_KefaliaEntos);
        textView_ApoFaoulEntos = rootView.findViewById(R.id.textView_ApoFaoulEntos);
        textView_XamenoPenaltyEntos = rootView.findViewById(R.id.textView_XamenoPenaltyEntos);
        textView_SoutEktos = rootView.findViewById(R.id.textView_SoutEktos);
        textView_KefaliaEktos = rootView.findViewById(R.id.textView_KefaliaEktos);
        textView_ApoFaoulEktos = rootView.findViewById(R.id.textView_ApoFaoulEktos);
        textView_XamenoPenaltyEktos = rootView.findViewById(R.id.textView_XamenoPenaltyEktos);
        switchXameniEukairia = rootView.findViewById(R.id.switchXameniEukairia);
        switchDokari = rootView.findViewById(R.id.switchDokari);
        switchDokari.setChecked(false);
        switchXameniEukairia.setChecked(false);
        showForSave = rootView.findViewById(R.id.group_showForSave);
        hideForSave = rootView.findViewById(R.id.group_hideForSave);
        showForSave.setVisibility(View.GONE);
    }

    private void addClickOnButtons() {

        textView_SoutEktos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEktos("shootOut","ΣΟΥΤ ΕΚΤΟΣ");
            }
        });

        textView_KefaliaEktos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEktos("kefaliaOut","ΚΕΦΑΛΙΑ ΕΚΤΟΣ");
            }
        });

        textView_ApoFaoulEktos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEktos("apoFaoulOut","ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΚΤΟΣ");
            }
        });

        textView_XamenoPenaltyEktos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEktos("xamenoPenaltyOut","ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΚΤΟΣ");
            }
        });

        textView_SoutEntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEntos((TextView) v, "shootIn","ΣΟΥΤ ΕΝΤΟΣ");
            }
        });

        textView_KefaliaEntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEntos((TextView) v, "kefaliaIn","ΚΕΦΑΛΙΑ ΕΝΤΟΣ");
            }
        });

        textView_ApoFaoulEntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEntos((TextView) v, "apoFaoulIn","ΣΟΥΤ ΑΠΟ ΦΑΟΥΛ ΕΝΤΟΣ");
            }
        });


        textView_XamenoPenaltyEntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickForEntos((TextView) v, "xamenoPenaltyIn","ΧΑΜΕΝΟ ΠΕΝΑΛΤΙ ΕΝΤΟΣ");
            }
        });

        textView_OkOfTeliki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotDefaultActionValue()){
                    rootActivity.changeStat("apokrousi",true);
                    rootActivity.addEvent("ΑΠΟΚΡΟΥΣΗ", "apokrousi");
                    rootActivity.returnToFieldFrag();
                }
            }
        });
        textView_EscOfTeliki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.returnToFieldFrag();
            }
        });
    }

    private void onClickForEntos(TextView selected,String stat, String event)
    {
        if (isNotDefaultActionValue()){
            rootActivity.startScaleAnimationForTextViews(selected);
            rootActivity.changeStat(stat,true);
            rootActivity.hideThisTeam();
            rootActivity.addEvent(checkForTheSwitches(event), stat);
            showForSave.setVisibility(View.VISIBLE);
            hideForSave.setVisibility(View.GONE);
            rootActivity.restoreDefaultValues();
        }
    }

    private void onClickForEktos(String stat, String event)
    {
        if (isNotDefaultActionValue()){
            rootActivity.changeStat(stat,true);
            rootActivity.addEvent(checkForTheSwitches(event), stat);
            rootActivity.returnToFieldFrag();
        }
    }

    private String checkForTheSwitches(String message)
    {
        if (switchDokari.isChecked())
        {
            rootActivity.changeStat("dokari",true);
            message += " ΔΟΚΑΡΙ";
            switchDokari.setChecked(false);
        }
        if (switchXameniEukairia.isChecked())
        {
            rootActivity.changeStat("xameniEukairia",true);
            message += " ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ";
            switchXameniEukairia.setChecked(false);
        }
        return message;
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