package com.jvvas.restnewgenstats.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.R;


public class FieldFragment extends Fragment {

    private ImageView imgViewPoint00,imgViewPoint01,imgViewPoint02,imgViewPoint03,imgViewPoint04,
            imgViewPoint05, imgViewPoint10,imgViewPoint11,imgViewPoint12,imgViewPoint13,
            imgViewPoint14,imgViewPoint15, imgViewPoint20,imgViewPoint21,imgViewPoint22,
            imgViewPoint23,imgViewPoint24,imgViewPoint25,imgViewPoint26,imgViewPoint27,
            imgViewPoint30,imgViewPoint31,imgViewPoint32,imgViewPoint33,imgViewPoint34,
            imgViewPoint35, imgViewPoint40,imgViewPoint41,imgViewPoint42,imgViewPoint43,
            imgViewPoint44,imgViewPoint45;

    private View rootView;
    private ImageView selectedImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_field, container, false);

        initializeButtons();
        addClickOnButtons();

        return rootView;
    }

    private void addClickOnButtons() {
        imgViewPoint00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("00");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint00.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint00 ;
            }
        });
        imgViewPoint01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("01");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint01.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint01 ;
            }
        });
        imgViewPoint02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("02");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint02.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint02 ;
            }
        });
        imgViewPoint03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("03");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint03.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint03 ;
            }
        });
        imgViewPoint04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("04");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint04.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint04 ;
            }
        });
        imgViewPoint05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("05");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint05.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint05 ;
            }
        });

        // -------------------------------------------------------------------------

        imgViewPoint10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("10");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint10.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint10 ;
            }
        });
        imgViewPoint11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("11");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint11.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint11 ;
            }
        });
        imgViewPoint12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("12");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint12.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint12 ;
            }
        });
        imgViewPoint13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("13");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint13.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint13 ;
            }
        });
        imgViewPoint14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("14");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint14.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint14 ;
            }
        });
        imgViewPoint15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("15");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint15.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint15 ;
            }
        });

        // -------------------------------------------------------------------------

        imgViewPoint20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("20");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint20.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint20 ;
            }
        });
        imgViewPoint21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("21");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint21.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint21 ;
            }
        });
        imgViewPoint22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("22");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint22.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint22 ;
            }
        });
        imgViewPoint23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("23");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint23.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint23 ;
            }
        });
        imgViewPoint24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("24");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint24.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint24 ;
            }
        });
        imgViewPoint25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("25");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint25.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint25 ;
            }
        });
        imgViewPoint26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("26");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint26.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint26 ;
            }
        });
        imgViewPoint27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("27");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint27.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint27;
            }
        });

        // -------------------------------------------------------------------------

        imgViewPoint30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("30");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint30.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint30 ;
            }
        });
        imgViewPoint31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("31");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint31.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint31 ;
            }
        });
        imgViewPoint32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("32");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint32.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint32 ;
            }
        });
        imgViewPoint33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("33");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint33.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint33 ;
            }
        });
        imgViewPoint34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("34");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint34.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint34 ;
            }
        });
        imgViewPoint35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("35");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint35.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint35 ;
            }
        });

        // -------------------------------------------------------------------------

        imgViewPoint40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("40");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint40.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint40 ;
            }
        });
        imgViewPoint41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("41");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint41.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint41 ;
            }
        });
        imgViewPoint42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("42");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint42.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint42 ;
            }
        });
        imgViewPoint43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("43");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint43.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint43 ;
            }
        });
        imgViewPoint44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("44");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint44.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint44 ;
            }
        });
        imgViewPoint45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CountStatsActivity)getActivity()).setLocation("45");
                selectedImg.setBackgroundResource(R.drawable.buttons_field);
                imgViewPoint45.setBackgroundResource(R.drawable.buttons_field_selected);
                selectedImg = (ImageView) imgViewPoint45 ;
            }
        });
    }

    private void initializeButtons() {
        imgViewPoint00 = rootView.findViewById(R.id.imageView_Point00);
        imgViewPoint01 = rootView.findViewById(R.id.imageView_Point01);
        imgViewPoint02 = rootView.findViewById(R.id.imageView_Point02);
        imgViewPoint03 = rootView.findViewById(R.id.imageView_Point03);
        imgViewPoint04 = rootView.findViewById(R.id.imageView_Point04);
        imgViewPoint05 = rootView.findViewById(R.id.imageView_Point05);
        // ------------
        imgViewPoint10 = rootView.findViewById(R.id.imageView_Point10);
        imgViewPoint11 = rootView.findViewById(R.id.imageView_Point11);
        imgViewPoint12 = rootView.findViewById(R.id.imageView_Point12);
        imgViewPoint13 = rootView.findViewById(R.id.imageView_Point13);
        imgViewPoint14 = rootView.findViewById(R.id.imageView_Point14);
        imgViewPoint15 = rootView.findViewById(R.id.imageView_Point15);
        // ------------
        imgViewPoint20 = rootView.findViewById(R.id.imageView_Point20);
        imgViewPoint21 = rootView.findViewById(R.id.imageView_Point21);
        imgViewPoint22 = rootView.findViewById(R.id.imageView_Point22);
        imgViewPoint23 = rootView.findViewById(R.id.imageView_Point23);
        imgViewPoint24 = rootView.findViewById(R.id.imageView_Point24);
        imgViewPoint25 = rootView.findViewById(R.id.imageView_Point25);
        imgViewPoint26 = rootView.findViewById(R.id.imageView_Point26);
        imgViewPoint27 = rootView.findViewById(R.id.imageView_Point27);
        // ------------
        imgViewPoint30 = rootView.findViewById(R.id.imageView_Point30);
        imgViewPoint31 = rootView.findViewById(R.id.imageView_Point31);
        imgViewPoint32 = rootView.findViewById(R.id.imageView_Point32);
        imgViewPoint33 = rootView.findViewById(R.id.imageView_Point33);
        imgViewPoint34 = rootView.findViewById(R.id.imageView_Point34);
        imgViewPoint35 = rootView.findViewById(R.id.imageView_Point35);
        // ------------
        imgViewPoint40 = rootView.findViewById(R.id.imageView_Point40);
        imgViewPoint41 = rootView.findViewById(R.id.imageView_Point41);
        imgViewPoint42 = rootView.findViewById(R.id.imageView_Point42);
        imgViewPoint43 = rootView.findViewById(R.id.imageView_Point43);
        imgViewPoint44 = rootView.findViewById(R.id.imageView_Point44);
        imgViewPoint45 = rootView.findViewById(R.id.imageView_Point45);
        // Default - init
        selectedImg = imgViewPoint00;
    }

}
