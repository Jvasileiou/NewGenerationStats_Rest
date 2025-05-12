package com.jvvas.restnewgenstats.Dialog;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jvvas.restnewgenstats.JavaDataTransferObjects.CoachDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

public class CoachInfoDialog extends DialogFragment {

    private View rootView;
    private EditText name, surname, id;
    private Button esc, confirm;

    private TeamDTO teamDTO;
    private Team team;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.dialog_coach_info, container, false);
        //Find the editTexts and set theirListeners
        findEditTexts();
        initListeners();
        //If we gave a player to edit set his info on display
        setInfo();
        return rootView;
    }

    private void findEditTexts(){
        name = rootView.findViewById(R.id.editText_CnewName);
        surname = rootView.findViewById(R.id.editText_CnewSurname);
        id = rootView.findViewById(R.id.editText_CnewID);

        esc = rootView.findViewById(R.id.button_CnewEsc);
        confirm = rootView.findViewById(R.id.button_CnewConfirm);
    }

    private void initListeners(){
        esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamDTO.setCoach(new CoachDTO(id.getText().toString(), surname.getText().toString(),
                        name.getText().toString()));

                team.setCoachFullName(teamDTO.getCoach().getName() + " " + teamDTO.getCoach().getSurname());
                dismiss();
            }
        });
    }

    private void setInfo(){
        id.setText(teamDTO.getCoach().getCoachId());
        name.setText(teamDTO.getCoach().getName());
        surname.setText(teamDTO.getCoach().getSurname());
    }

    public void setTeams(TeamDTO teamDTO, Team team){
        this.teamDTO = teamDTO;
        this.team = team;
    }
}
