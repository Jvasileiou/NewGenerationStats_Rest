package com.jvvas.restnewgenstats.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jvvas.restnewgenstats.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Referees extends Fragment {

    private EditText surnameFirst, nameFirst, organizationFirst, surnameSideA, nameSideA,
            organizationSideA, surnameSideB, nameSideB, organizationSideB, surnameFourth,
            nameFourth, organizationFourth;
    View rootView;


    public Referees() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.info_referees, container, false);

        initViews();

        return rootView;
    }

    // ____________________________________ LAYOUT INIT ___________________________________________

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

    // ____________________________________ SET - GET ____________________________________________


    public void setFirst(String surname,String name, String organization) {
        surnameFirst.setText(surname);
        nameFirst.setText(name);
        organizationFirst.setText(organization);
    }

    public void setSideA(String surname,String name, String organization) {
        surnameSideA.setText(surname);
        nameSideA.setText(name);
        organizationSideA.setText(organization);
    }

    public void setSideB(String surname,String name, String organization) {
        surnameSideB.setText(surname);
        nameSideB.setText(name);
        organizationSideB.setText(organization);
    }

    public void setFourth(String surname,String name, String organization) {
        surnameFourth.setText(surname);
        nameFourth.setText(name);
        organizationFourth.setText(organization);
    }

    public EditText getSurnameFirst() {
        return surnameFirst;
    }
    public EditText getNameFirst() {
        return nameFirst;
    }
    public EditText getOrganizationFirst() {
        return organizationFirst;
    }

    public EditText getSurnameSideA() {
        return surnameSideA;
    }
    public EditText getNameSideA() {
        return nameSideA;
    }
    public EditText getOrganizationSideA() {
        return organizationSideA;
    }

    public EditText getSurnameSideB() {
        return surnameSideB;
    }
    public EditText getNameSideB() {
        return nameSideB;
    }
    public EditText getOrganizationSideB() {
        return organizationSideB;
    }

    public EditText getSurnameFourth() {
        return surnameFourth;
    }
    public EditText getNameFourth() {
        return nameFourth;
    }
    public EditText getOrganizationFourth() {
        return organizationFourth;
    }



}
