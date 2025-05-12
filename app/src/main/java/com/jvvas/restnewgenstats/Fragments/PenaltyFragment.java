package com.jvvas.restnewgenstats.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;

import java.util.HashMap;
import java.util.Map;

public class PenaltyFragment extends Fragment {

    private View rootView;
    private CountStatsActivity rootActivity;


    private TextView textViewOkOfPenalty,textViewScorePenaltyA,textViewScorePenaltyB;
    private CheckBox firstGoalA,secondGoalA,thirdGoalA, fourthGoalA, fifthGoalA ,
            firstGoalB,secondGoalB,thirdGoalB, fourthGoalB, fifthGoalB,
            firstMissedA,secondMissedA,thirdMissedA, fourthMissedA, fifthMissedA ,
            firstMissedB,secondMissedB,thirdMissedB, fourthMissedB, fifthMissedB ;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,CheckBox> allCheckBoxes = new HashMap<>();
    private CheckBox ch1,ch2;
    private int counterOfPenalties = 1 ; // First Penalty

    private Button escOfPenalty;


    private int counterAPenalties, counterBPenalties;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_penalty, container, false);

        //Get rootActivity so we don't do that every time we need a method it has
        rootActivity = ((CountStatsActivity) getActivity());
        //Find all buttons and add their onClicks
        initializeTheButtons();
        addClickOnButtons();
        addAllCheckBoxesIntoHashMap();

        return rootView;
    }

    private void addAllCheckBoxesIntoHashMap() {
        allCheckBoxes.put(0,firstGoalA);
        allCheckBoxes.put(1,firstMissedA);

        allCheckBoxes.put(4,secondGoalA);
        allCheckBoxes.put(5,secondMissedA);

        allCheckBoxes.put(8,thirdGoalA);
        allCheckBoxes.put(9,thirdMissedA);

        allCheckBoxes.put(12,fourthGoalA);
        allCheckBoxes.put(13,fourthMissedA);

        allCheckBoxes.put(16,fifthGoalA);
        allCheckBoxes.put(17,fifthMissedA);

        // -------------------------------------

        allCheckBoxes.put(2,firstGoalB);
        allCheckBoxes.put(3,firstMissedB);

        allCheckBoxes.put(6,secondGoalB);
        allCheckBoxes.put(7,secondMissedB);

        allCheckBoxes.put(10,thirdGoalB);
        allCheckBoxes.put(11,thirdMissedB);

        allCheckBoxes.put(14,fourthGoalB);
        allCheckBoxes.put(15,fourthMissedB);

        allCheckBoxes.put(18,fifthGoalB);
        allCheckBoxes.put(19,fifthMissedB);
    }

    private void addClickOnButtons() {

        // -------------------------------------------------------- A TEAM --------------------------------------------------------
        // 1
        firstGoalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        firstMissedA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 2 ---------------------------
        secondGoalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        secondMissedA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 3 ---------------------------
        thirdGoalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        thirdMissedA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 4 ---------------------------
        fourthGoalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        fourthMissedA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 5 ---------------------------
        fifthGoalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        fifthMissedA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // -------------------------------------------------------- B TEAM --------------------------------------------------------
        // 1
        firstGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        firstMissedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 2 ---------------------------
        secondGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        secondMissedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 3 ---------------------------
        thirdGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        thirdMissedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 4 ---------------------------
        fourthGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        fourthMissedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // 5 ---------------------------
        fifthGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        fifthMissedB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penaltyOnClick((CheckBox)v);
            }
        });

        // ================================================================

        textViewOkOfPenalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rootActivity.getIsTeamAForAction())
                {
                    if(counterOfPenalties <= 2 )
                    {
                        doThePenaltyJob(0,1,"A");
                    }else if(counterOfPenalties <= 4 )
                    {
                        doThePenaltyJob(4,5,"A");
                    }else if(counterOfPenalties <= 6 )
                    {
                        doThePenaltyJob(8,9,"A");
                    }else if(counterOfPenalties <= 8 )
                    {
                        doThePenaltyJob(12,13,"A");
                    }else if(counterOfPenalties <= 10 )
                    {
                        doThePenaltyJob(16,17,"A");
                    }
                }
                else
                {
                    if(counterOfPenalties <= 2 )
                    {
                        doThePenaltyJob(2,3,"B");
                    }else if(counterOfPenalties <= 4 )
                    {
                        doThePenaltyJob(6,7,"B");
                    }else if(counterOfPenalties <= 6 )
                    {
                        doThePenaltyJob(10,11,"B");
                    }else if(counterOfPenalties <= 8 )
                    {
                        doThePenaltyJob(14,15,"B");
                    }else if(counterOfPenalties <= 10 )
                    {
                        doThePenaltyJob(18,19,"B");
                    }
                }

            }
        });

        escOfPenalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.returnToFieldFrag();
            }
        });

    }

    private void penaltyOnClick(CheckBox selected)
    {
        if (rootActivity.isDefaultValues())
        {
            Toast.makeText(getContext(), "Click on a PLAYER", Toast.LENGTH_LONG).show();
            selected.setChecked(false);
        }else
            textViewOkOfPenalty.setVisibility(View.VISIBLE);
    }

    private void doThePenaltyJob(int i, int j, String team) {
        if (!areBothChecked(i, j)) {
            counterOfPenalties++;
            if (isGoal()) {
                rootActivity.changeStat("penaltyScore",true);

                if(team.equals("A")) {
                    counterAPenalties++;
                    textViewScorePenaltyA.setText(String.valueOf(counterAPenalties));
                }else {
                    counterBPenalties++;
                    textViewScorePenaltyB.setText(String.valueOf(counterBPenalties));
                }
                rootActivity.addEvent("ΠΕΝΑΛΤΙ ΕΥΣΤΟΧΟ", "penaltyScore");
            }else if(isMissed())
                rootActivity.addEvent("ΠΕΝΑΛΤΙ ΑΣΤΟΧΟ", "");
            textViewOkOfPenalty.setVisibility(View.INVISIBLE);
            rootActivity.restoreDefaultValues();
        }
        if (counterOfPenalties == 11) {
            counterOfPenalties = 1;
            uncheckAll();
        }
    }

    private void uncheckAll() {
        CheckBox ch;
        for (Map.Entry<Integer, CheckBox> integerCheckBoxEntry : allCheckBoxes.entrySet()) {
            ch =  integerCheckBoxEntry.getValue();
            ch.setChecked(false);
        }
    }

    private boolean isGoal() {
        return ch1.isChecked() ;
    }

    private boolean isMissed() {
        return ch2.isChecked() ;
    }

    private boolean areBothChecked(int i, int j)
    {
        ch1 = allCheckBoxes.get(i);
        ch2 = allCheckBoxes.get(j);

        return (ch1.isChecked() &&  ch2.isChecked());
    }


    private void initializeTheButtons() {
        textViewOkOfPenalty = rootView.findViewById(R.id.textView_OkOfPenalty);
        textViewOkOfPenalty.setVisibility(View.INVISIBLE);
        textViewScorePenaltyA = rootView.findViewById(R.id.textView_PenaltyScoreA);
        textViewScorePenaltyB = rootView.findViewById(R.id.textView_PenaltyScoreB);
        counterAPenalties = 0;
        counterBPenalties = 0;

        firstGoalA = rootView.findViewById(R.id.checkBox_FirstGoalA);
        secondGoalA = rootView.findViewById(R.id.checkBox_SecondGoalA);
        thirdGoalA = rootView.findViewById(R.id.checkBox_ThirdGoalA);
        fourthGoalA = rootView.findViewById(R.id.checkBox_FourthGoalA);
        fifthGoalA = rootView.findViewById(R.id.checkBox_FifthGoalA);

        firstGoalB = rootView.findViewById(R.id.checkBox_FirstGoalB);
        secondGoalB = rootView.findViewById(R.id.checkBox_SecondGoalB);
        thirdGoalB = rootView.findViewById(R.id.checkBox_ThirdGoalB);
        fourthGoalB = rootView.findViewById(R.id.checkBox_FourthGoalB);
        fifthGoalB = rootView.findViewById(R.id.checkBox_FifthGoalB);

        firstMissedA = rootView.findViewById(R.id.checkBox_FirstMissedA);
        secondMissedA = rootView.findViewById(R.id.checkBox_SecondMissedA);
        thirdMissedA = rootView.findViewById(R.id.checkBox_ThirdMissedA);
        fourthMissedA = rootView.findViewById(R.id.checkBox_FourthMissedA);
        fifthMissedA = rootView.findViewById(R.id.checkBox_FifthMissedA);

        firstMissedB = rootView.findViewById(R.id.checkBox_FirstMissedB);
        secondMissedB = rootView.findViewById(R.id.checkBox_SecondMissedB);
        thirdMissedB = rootView.findViewById(R.id.checkBox_ThirdMissedB);
        fourthMissedB = rootView.findViewById(R.id.checkBox_FourthMissedB);
        fifthMissedB = rootView.findViewById(R.id.checkBox_FifthMissedB);

        escOfPenalty = rootView.findViewById(R.id.textView_EscOfPenalty);
    }

}
