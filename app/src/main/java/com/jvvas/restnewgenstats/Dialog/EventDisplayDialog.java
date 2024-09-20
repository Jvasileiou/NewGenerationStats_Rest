package com.jvvas.restnewgenstats.Dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jvvas.restnewgenstats.Activities.CountStatsActivity;
import com.jvvas.restnewgenstats.Adapters.EventAdapter;
import com.jvvas.restnewgenstats.Objects.DataLiveActions;
import com.jvvas.restnewgenstats.R;

public class EventDisplayDialog  extends DialogFragment {

    private Button back, edit, insert;
    private View rootView;

    private CountStatsActivity rootActivity;
    private EventAdapter eventAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullscreen_dialog_style);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.dialog_display_events, container, false);

        findViews();
        makeList();
        addOnClicks();

        return rootView;
    }

    private void findViews(){
        back = rootView.findViewById(R.id.button_EVback);
        edit = rootView.findViewById(R.id.button_EVedit);
        insert = rootView.findViewById(R.id.button_EVinsert);
    }

    private void makeList(){
        eventAdapter = rootActivity.getEventAdapter();
        ListView eventList = rootView.findViewById(R.id.listview_EVevents);
        eventList.setAdapter(eventAdapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                eventAdapter.setSelected(position);
            }
        });
    }

    private void addOnClicks(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLiveActions event = eventAdapter.getSelectedEvent();
                if (event != null) {
                    FragmentManager fm = rootActivity.getSupportFragmentManager();
                    EditEventDialog statsDialog = new EditEventDialog();
                    statsDialog.setTeams(eventAdapter.getTeamA(), eventAdapter.getTeamB());
                    statsDialog.setEvent(eventAdapter.getSelectedEvent());
                    statsDialog.setRootActivity(rootActivity);
                    statsDialog.show(fm, "");
                }
                else rootActivity.showToast("Select an event");
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = rootActivity.getSupportFragmentManager();
                EditEventDialog statsDialog = new EditEventDialog();
                statsDialog.setTeams(eventAdapter.getTeamA(), eventAdapter.getTeamB());
                statsDialog.setRootActivity(rootActivity);
                statsDialog.show(fm, "");
            }
        });
    }




    public void setRootActivity(CountStatsActivity root){
        rootActivity = root;
    }
}
