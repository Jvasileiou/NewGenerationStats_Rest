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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

public class FaoulFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;
    private SwitchCompat switchKerdismenoPenalty;
    private CheckBox checkBoxTravigmaFanelas,checkBoxTaklin,checkBoxXeri,
            checkBox_SkliroFaoul, checkBox_SomatikiEpafi;
    private TextView textViewEscOfFaoul , textViewOkOfFaoul;
    private Group hideForWon, showForWon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_faoul, container, false);

        rootActivity = ((CountStatsActivity) getActivity());
        initializeTheButtons();
        addClickOnButtons();

        return rootView;
    }

    private void initializeTheButtons() {
        checkBox_SkliroFaoul = rootView.findViewById(R.id.checkBox_SkliroFaoul);
        checkBox_SomatikiEpafi = rootView.findViewById(R.id.checkBox_SomatikiEpafi);
        checkBoxTravigmaFanelas = rootView.findViewById(R.id.checkBox_TravigmaFanelas);
        checkBoxTaklin = rootView.findViewById(R.id.checkBox_Taklin);
        checkBoxXeri = rootView.findViewById(R.id.checkBox_Xeri);
        switchKerdismenoPenalty = rootView.findViewById(R.id.switch_KerdismenoPenalty);
        switchKerdismenoPenalty.setVisibility(View.GONE);
        textViewEscOfFaoul = rootView.findViewById(R.id.textView_EscOfFaoul);
        textViewOkOfFaoul = rootView.findViewById(R.id.textView_OkOfFaoul);
        hideForWon = rootView.findViewById(R.id.group_hideForWon);
        showForWon = rootView.findViewById(R.id.group_showForWon);
        showForWon.setVisibility(View.GONE);
    }

    private void addClickOnButtons() {

        checkBoxTravigmaFanelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foulAdded("ΦΑΟΥΛ ΤΡΑΒΗΓ ΦΑΝ");
            }
        });

        checkBoxTaklin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foulAdded("ΦΑΟΥΛ ΤΑΚΛΙΝ");
            }
        });

        checkBoxXeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foulAdded("ΦΑΟΥΛ ΧΕΡΙ");
            }
        });

        checkBox_SkliroFaoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foulAdded("ΦΑΟΥΛ ΣΚΛΗΡΟ ΦΑΟΥΛ");
            }
        });

        checkBox_SomatikiEpafi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foulAdded("ΦΑΟΥΛ ΣΩΜΑΤΙΚΗ ΕΠΑΦΗ");
            }
        });

        textViewOkOfFaoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check for default values
                if (isNotDefaultActionValue()){
                    setCheckBoxesFalse();
                    rootActivity.changeStat("faoulYper",true);
                    if(switchKerdismenoPenalty.isChecked()){
                        rootActivity.addEvent("ΥΠΕΡ ΚΕΡΔΙΣΜΕΝΟ ΠΕΝΑΛΤΥ","faoulYper");
                        switchKerdismenoPenalty.setChecked(false);
                    }else{
                        rootActivity.addEvent("ΥΠΕΡ","faoulYper");
                    }
                    rootActivity.returnToFieldFrag();
                }
            }
        });

        textViewEscOfFaoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckBoxesFalse();
                rootActivity.returnToFieldFrag();
                switchKerdismenoPenalty.setChecked(false);
            }
        });
    }

    private void foulAdded(String event) {

        if (isNotDefaultActionValue()){
            rootActivity.changeStat("faoulKata",true);
            rootActivity.hideThisTeam();
            hideForWon.setVisibility(View.GONE);
            showForWon.setVisibility(View.VISIBLE);
            if(isInTheArea())
                switchKerdismenoPenalty.setVisibility(View.VISIBLE);
            rootActivity.addEvent(event, "faoulKata");
            rootActivity.restoreDefaultValues();
        }
    }

    private boolean isInTheArea() {
        return (rootActivity.getLocation().equals("10") || rootActivity.getLocation().equals("20")||
                rootActivity.getLocation().equals("21") || rootActivity.getLocation().equals("30")||
                rootActivity.getLocation().equals("15") || rootActivity.getLocation().equals("26")||
                rootActivity.getLocation().equals("27") || rootActivity.getLocation().equals("35"));
    }

    private void setCheckBoxesFalse() {
        checkBoxXeri.setChecked(false);
        checkBoxTaklin.setChecked(false);
        checkBox_SkliroFaoul.setChecked(false);
        checkBox_SomatikiEpafi.setChecked(false);
        checkBoxTravigmaFanelas.setChecked(false);
    }


    private boolean isNotDefaultActionValue() {
        if (rootActivity.isDefaultValues())
        {
            Toast.makeText(getContext(), "Click on a PLAYER", Toast.LENGTH_LONG).show();
            setCheckBoxesFalse();
            return false;
        }
        return true;
    }



}