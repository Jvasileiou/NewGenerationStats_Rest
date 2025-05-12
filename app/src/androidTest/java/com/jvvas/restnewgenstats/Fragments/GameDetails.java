package com.jvvas.restnewgenstats.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jvvas.restnewgenstats.Dialog.ColorDialog;
import com.jvvas.restnewgenstats.R;

import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameDetails extends Fragment implements ColorDialog.OnInputSelected{

    private static final String TAG = "GameDetails";

    public EditText time, teamA, teamB, field, place ,organization;
    public TextView date,id ;

    private ImageView colorA,colorB;
    private DatePickerDialog datePickerDialog;
    private TextView textViewDatePicker;
    private String selectedTeam;
    View rootView;


    //Used by colour picker dialog to send back the selected colour
    @Override
    public void sendInput(String input) {
        int currentColor = Color.parseColor(input);

        if(selectedTeam.equals("A"))
            colorA.setBackgroundColor(currentColor);

        else if(selectedTeam.equals("B"))
            colorB.setBackgroundColor(currentColor);
    }

    public GameDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.info_game, container, false);

        initializeViews();
        setTheClickListeners();
        setTheListeners();

        return rootView;
    }

    public void initializeViews() {
        System.out.println("FINDING VIEWS");
        id = (TextView) rootView.findViewById(R.id.textView2_ID);
        date = (TextView) rootView.findViewById(R.id.editText2_gameDate);

        time = (EditText) rootView.findViewById(R.id.editText2_gameTime);
        teamA = (EditText) rootView.findViewById(R.id.editText2_homeTeam);
        teamB = (EditText) rootView.findViewById(R.id.editText2_otherTeam);
        field =  (EditText) rootView.findViewById(R.id.editText2_gameField);
        place = (EditText) rootView.findViewById(R.id.editText2_place);
        organization = (EditText) rootView.findViewById(R.id.editText2_organiz);

        textViewDatePicker = rootView.findViewById(R.id.textViewButtonDatePicker);
        colorA = rootView.findViewById(R.id.imageView_ColorA);
        colorB = rootView.findViewById(R.id.imageView_ColorB);
    }

    private void setTheClickListeners() {
        textViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(dayOfMonth + "-" + (month+1) + "-" + year);
                            }

                        },year,month,day);
                datePickerDialog.show();
            }
        });

        colorA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker("A");
            }
        });

        colorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker("B");
            }
        });
    }

    private void openColorPicker(final String team) {
        selectedTeam = team;
        ColorDialog colorDialog = new ColorDialog();
        colorDialog.setTargetFragment(GameDetails.this , 1);
        if (getFragmentManager() != null) {
            colorDialog.show(getFragmentManager(),"ColorDialog");
        }
    }

    //Method to make the wanted changes to the data we read if user changes one
    private void setTheListeners()
    {
        //When a textView loses focus, get its content, update database and the info we display
        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setGameTime(textViewChange((TextView)view,gameRef.child("time")));
                }
            }
        });
        teamA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setTeamAName(textViewChange((TextView)view,gameRef.child("teamA")));
                }
            }
        });

        teamB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setTeamBName(textViewChange((TextView)view,gameRef.child("teamB")));
                }
            }
        });

        field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setGameField(textViewChange((TextView)view,gameRef.child("field")));
                }
            }
        });

        place.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setGamePlace(textViewChange((TextView)view,gameRef.child("place")));
                }
            }
        });

        organization.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus)
                {
//                    ((RosterReview) Objects.requireNonNull(getActivity()))
//                            .setGameOrganization(textViewChange((TextView)view,
//                                    gameRef.child("organization")));
                }
            }
        });

    }

    //Method that takes a textView, updates database at given reference with the text and returns it
//    private String textViewChange(TextView t, DatabaseReference update)
//    {
//        String text = t.getText().toString();
//        update.setValue(text);
//        return text;
//    }

    public void setDetails(String sTime,String sDate, String sTeamA,String sTeamB,
                           String sID,String sField,String sPlace,String sOrganization)
    {
        time.setText(sTime);
        date.setText(sDate);
        teamA.setText(sTeamA);
        teamB.setText(sTeamB);
        id.setText(sID);
        field.setText(sField);
        place.setText(sPlace);
        organization.setText(sOrganization);
    }

    //Method to get the selected colours for the wanted team. Needed in RosterReview
    public int getColour(int team)
    {
        return (team == 1) ? ((ColorDrawable)colorA.getBackground()).getColor() :
                ((ColorDrawable)colorB.getBackground()).getColor();
    }

    public EditText getTime() {
        return time;
    }

    public EditText getTeamA() {
        return teamA;
    }

    public EditText getTeamB() {
        return teamB;
    }

    public EditText getField() {
        return field;
    }

    public EditText getPlace() {
        return place;
    }

    public EditText getOrganization() {
        return organization;
    }

    public TextView getDate() {
        return date;
    }

    public ImageView getColorA() {
        return colorA;
    }

    public ImageView getColorB() {
        return colorB;
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    public TextView getTextViewDatePicker() {
        return textViewDatePicker;
    }

    public String getSelectedTeam() {
        return selectedTeam;
    }


}