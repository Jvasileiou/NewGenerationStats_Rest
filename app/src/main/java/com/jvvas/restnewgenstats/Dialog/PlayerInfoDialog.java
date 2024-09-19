package com.jvvas.restnewgenstats.Dialog;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;
import com.jvvas.restnewgenstats.Adapters.RosterAdapter;

import java.util.Objects;

public class PlayerInfoDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    private View rootView;
    private Team team;

    //Where player info goes
    private EditText newAM, newName, newSurname, newNumber;
    private String newPositionString = "Position"; // DEFAULT VALUE

    private Button esc, confirm;

    private Spinner spinnerNewPosition;
    private Player editedPlayer;
    private ArrayAdapter<CharSequence> adapter;


    private RosterAdapter listToUpdate;
    private boolean editing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.dialog_player_info, container, false);
        //Find the editTexts and set theirListeners
        findEditTexts();
        initListeners();
        //If we gave a player to edit set his info on display
        if (editing) setInfo();
        return rootView;
    }

    // ____________________________________ DIALOG INIT ___________________________________________
    //Find all editTexts the dialog contains
    private void findEditTexts()
    {
        //Items for the players information
        newAM = rootView.findViewById(R.id.editText_newID);
        newName = rootView.findViewById(R.id.editText_newName);
        newSurname = rootView.findViewById(R.id.editText_newSurname);
        newNumber = rootView.findViewById(R.id.editText_newNumber);
        spinnerNewPosition = rootView.findViewById(R.id.spinner_NewPosition);
        //Setting up the position spinner
        adapter = ArrayAdapter.createFromResource(
                Objects.requireNonNull(getContext()), R.array.newPosition,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNewPosition.setAdapter(adapter);
        spinnerNewPosition.setOnItemSelectedListener(this);
        spinnerNewPosition.setBackgroundResource(R.drawable.info_edittext_background);
        //Esc/Confirm buttons on the bottom of the dialog
        esc = rootView.findViewById(R.id.button_newEsc);
        confirm = rootView.findViewById(R.id.button_newConfirm);
        //No need to keep title, we just set it
        if (editing)
            ((TextView) rootView.findViewById(R.id.textView_dialogTitle))
                    .setText(getString(R.string.editPlayerTitle));
        else
            ((TextView) rootView.findViewById(R.id.textView_dialogTitle))
                    .setText(getString(R.string.newPlayerTitle));
    }

    private void initListeners()
    {
        //Esc should just close the dialog
        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        //Confirm should be making a new player and notify both database and the roster list
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkNoEmptyField()) {
                    Toast.makeText(getActivity(),
                            "Empty Field in the Player Info or Reselect the Position", Toast.LENGTH_LONG).show();
                }
                else if (editing){
                    updatePlayer();
                    getDialog().dismiss();
                }
                else{
                    makeNewPlayer();
                    getDialog().dismiss();
                }
            }
        });
    }


    //Implementing the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newPositionString = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // _____________________________________ SETTERS ______________________________________________
    //Method for displaying the info of the given player
    private void setInfo()
    {
        newAM.setText(editedPlayer.getPlayerId());
        newAM.setEnabled(false);
        newAM.setFocusable(false);
        newName.setText(editedPlayer.getName());
        newSurname.setText(editedPlayer.getSurname());
        newNumber.setText(editedPlayer.getNumber());
        spinnerNewPosition.setSelection(adapter.getPosition(editedPlayer.getPosition()));
    }

    //Set the player to edit
    public void setEditedPlayer(Player editedPlayer) {
        this.editedPlayer = editedPlayer;
        editing = true;
    }

    public void setTeam(Team team){
        this.team = team;
    }

    //Sets the adapter we need to update in order to display new players
    public void setListToUpdate(RosterAdapter adapter) {
        listToUpdate = adapter;
    }

    // _______________________ METHODS FOR UPDATING/MAKING NEW PLAYER _____________________________
    //Checks if we have no empty fields and updates the players
    private void updatePlayer() {
        if (checkNoEmptyField()) {
            editedPlayer.setName(newName.getText().toString());
            editedPlayer.setSurname(newSurname.getText().toString());
            editedPlayer.setNumber(newNumber.getText().toString());
            editedPlayer.setPosition(newPositionString);
            listToUpdate.notifyDataSetChanged();
        }
    }

    //Check that we have info in all editTexts
    private boolean checkNoEmptyField()
    {
        return !(newPositionString.equals("Position") || newName.getText().toString().equals("") ||
                newSurname.getText().toString().equals("") ||
                newNumber.getText().toString().equals("") ||
                newAM.getText().toString().equals(""));
    }


    private void makeNewPlayer(){
        Player newPL = new Player(newAM.getText().toString(),
                newSurname.getText().toString(),
                newName.getText().toString() ,
                newNumber.getText().toString(),
                newPositionString,
                null);
        team.addPlayer(newPL);
    }


}

