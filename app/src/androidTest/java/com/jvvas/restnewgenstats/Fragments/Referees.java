package com.jvvas.restnewgenstats.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jvvas.restnewgenstats.R;
import com.jvvas.restnewgenstats.Activities.RosterReview;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Referees extends Fragment {

    private EditText surnameFirst, nameFirst, organizationFirst, surnameSideA, nameSideA,
            organizationSideA, surnameSideB, nameSideB, organizationSideB, surnameFourth,
            nameFourth, organizationFourth;
    private String firstAM, sideAAM, sideBAM, fourthAM;
    View rootView;


    public Referees() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.info_referees, container, false);

        initViews();
        getAllAMs();
        setListeners();

        return rootView;
    }

    private void initViews()
    {
        surnameFirst = rootView.findViewById(R.id.editText2_surnameFirst);
        nameFirst = rootView.findViewById(R.id.editText2_nameFirst);
        organizationFirst = rootView.findViewById(R.id.editText2_organizationFirst);
        surnameSideA = rootView.findViewById(R.id.editText2_surnameSideA);
        nameSideA = rootView.findViewById(R.id.editText2_nameSideA);
        organizationSideA = rootView.findViewById(R.id.editText2_organizationSideA);
        surnameSideB = rootView.findViewById(R.id.editText2_surnameSideB);
        nameSideB = rootView.findViewById(R.id.editText2_nameSideB);
        organizationSideB = rootView.findViewById(R.id.editText2_organizationSideB);
        surnameFourth = rootView.findViewById(R.id.editText2_surnameFourth);
        nameFourth = rootView.findViewById(R.id.editText2_nameFourth);
        organizationFourth = rootView.findViewById(R.id.editText2_organizationFourth);
    }

    private void getAllAMs()
    {
        RosterReview act = ((RosterReview) Objects.requireNonNull(getActivity()));
        firstAM = act.getRefereeId();
        sideAAM = act.getRefereeHelperAId();
        sideBAM = act.getRefereeHelperBId();
        fourthAM = act.getRefereeDId();
    }

    private void setListeners()
    {
        View.OnFocusChangeListener nameChange = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                {
                    EditText loseFocus = (EditText) view;
//                    refRef.child(getCorrectAM(loseFocus))
//                            .child("name").setValue(loseFocus.getText().toString());
                }
            }
        };
        View.OnFocusChangeListener surnameChange = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                {
                    EditText loseFocus = (EditText) view;
//                    refRef.child(getCorrectAM(loseFocus))
//                            .child("surname").setValue(loseFocus.getText().toString());
                }
            }
        };
        View.OnFocusChangeListener organizationChange = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus)
                {
                    EditText loseFocus = (EditText) view;
//                    refRef.child(getCorrectAM(loseFocus))
//                            .child("organization").setValue(loseFocus.getText().toString());
                }
            }
        };

        surnameFirst.setOnFocusChangeListener(surnameChange);
        surnameSideA.setOnFocusChangeListener(surnameChange);
        surnameSideB.setOnFocusChangeListener(surnameChange);
        surnameFourth.setOnFocusChangeListener(surnameChange);

        nameFirst.setOnFocusChangeListener(nameChange);
        nameSideA.setOnFocusChangeListener(nameChange);
        nameSideB.setOnFocusChangeListener(nameChange);
        nameFourth.setOnFocusChangeListener(nameChange);

        organizationFirst.setOnFocusChangeListener(organizationChange);
        organizationSideA.setOnFocusChangeListener(organizationChange);
        organizationSideB.setOnFocusChangeListener(organizationChange);
        organizationFourth.setOnFocusChangeListener(organizationChange);
    }

    private String getCorrectAM(EditText changed)
    {
        if (changed == surnameFirst || changed == nameFirst || changed == organizationFirst)
            return firstAM;
        else if (changed == surnameSideA || changed == nameSideA || changed == organizationSideA)
            return sideAAM;
        else if (changed == surnameSideB || changed == nameSideB || changed == organizationSideB)
            return sideBAM;
        return fourthAM;
    }

    public void setFirst(String surname,String name, String organization)
    {
        surnameFirst.setText(surname);
        nameFirst.setText(name);
        organizationFirst.setText(organization);
    }

    public void setSideA(String surname,String name, String organization)
    {
        surnameSideA.setText(surname);
        nameSideA.setText(name);
        organizationSideA.setText(organization);
    }

    public void setSideB(String surname,String name, String organization)
    {
        surnameSideB.setText(surname);
        nameSideB.setText(name);
        organizationSideB.setText(organization);
    }

    public void setFourth(String surname,String name, String organization)
    {
        surnameFourth.setText(surname);
        nameFourth.setText(name);
        organizationFourth.setText(organization);
    }

    public EditText getSurnameFirst() {
        return surnameFirst;
    }

    public void setSurnameFirst(EditText surnameFirst) {
        this.surnameFirst = surnameFirst;
    }

    public EditText getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(EditText nameFirst) {
        this.nameFirst = nameFirst;
    }

    public EditText getOrganizationFirst() {
        return organizationFirst;
    }

    public void setOrganizationFirst(EditText organizationFirst) {
        this.organizationFirst = organizationFirst;
    }

    public EditText getSurnameSideA() {
        return surnameSideA;
    }

    public void setSurnameSideA(EditText surnameSideA) {
        this.surnameSideA = surnameSideA;
    }

    public EditText getNameSideA() {
        return nameSideA;
    }

    public void setNameSideA(EditText nameSideA) {
        this.nameSideA = nameSideA;
    }

    public EditText getOrganizationSideA() {
        return organizationSideA;
    }

    public void setOrganizationSideA(EditText organizationSideA) {
        this.organizationSideA = organizationSideA;
    }

    public EditText getSurnameSideB() {
        return surnameSideB;
    }

    public void setSurnameSideB(EditText surnameSideB) {
        this.surnameSideB = surnameSideB;
    }

    public EditText getNameSideB() {
        return nameSideB;
    }

    public void setNameSideB(EditText nameSideB) {
        this.nameSideB = nameSideB;
    }

    public EditText getOrganizationSideB() {
        return organizationSideB;
    }

    public void setOrganizationSideB(EditText organizationSideB) {
        this.organizationSideB = organizationSideB;
    }

    public EditText getSurnameFourth() {
        return surnameFourth;
    }

    public void setSurnameFourth(EditText surnameFourth) {
        this.surnameFourth = surnameFourth;
    }

    public EditText getNameFourth() {
        return nameFourth;
    }

    public void setNameFourth(EditText nameFourth) {
        this.nameFourth = nameFourth;
    }

    public EditText getOrganizationFourth() {
        return organizationFourth;
    }

    public void setOrganizationFourth(EditText organizationFourth) {
        this.organizationFourth = organizationFourth;
    }

    public String getFirstAM() {
        return firstAM;
    }

    public void setFirstAM(String firstAM) {
        this.firstAM = firstAM;
    }

    public String getSideAAM() {
        return sideAAM;
    }

    public void setSideAAM(String sideAAM) {
        this.sideAAM = sideAAM;
    }

    public String getSideBAM() {
        return sideBAM;
    }

    public void setSideBAM(String sideBAM) {
        this.sideBAM = sideBAM;
    }

    public String getFourthAM() {
        return fourthAM;
    }

    public void setFourthAM(String fourthAM) {
        this.fourthAM = fourthAM;
    }
}
