package com.jvvas.restnewgenstats.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jvvas.restnewgenstats.Objects.DataLiveActions;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter{

    private ArrayList<DataLiveActions> events;
    private Context context;
    private Team teamA,teamB;
    private int selectedIndex = -1;


    public EventAdapter(Context context,Team teamA, Team teamB){
        events = new ArrayList<>();

        this.context = context;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public void addEvent(DataLiveActions newEvent){
        events.add(newEvent);
        selectedIndex = events.size()-1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (selectedIndex == i)
            view = LayoutInflater.from(context).inflate(R.layout.selected_event_item, viewGroup, false);
        else
            view = LayoutInflater.from(context).inflate(R.layout.event_item, viewGroup, false);

        TextView eventDescription = view.findViewById(R.id.textView_description);
        TextView eventTime = view.findViewById(R.id.textView_eventTime);
        ImageView flag = view.findViewById(R.id.imageView_redFlag);
        ImageView teamColour = view.findViewById(R.id.imageView_teamColour);

        DataLiveActions currentEvent = events.get(i);

        if (currentEvent.isFlagged()) flag.setVisibility(View.VISIBLE);
        else flag.setVisibility(View.INVISIBLE);

        eventTime.setText(fixMinFormat(currentEvent.getMinute(),currentEvent.getTimePeriod()));
        eventDescription.setText(setUpTeam(currentEvent, teamColour));

        return view;
    }

    private String fixMinFormat(int minute, String timePeriod){
        int maxMin = 1000;
        switch (timePeriod) {
            case "Α ΗΜΙΧ":
                maxMin = 45;
                break;
            case "Β ΗΜΙΧ":
                maxMin = 90;
                break;
            case "ΠΑΡΑΤ A ΗΜΙΧ":
                maxMin = 105;
                break;
            case "ΠΑΡΑΤ Β ΗΜΙΧ":
                maxMin = 120;
                break;
            case "ΠΕΝΑΛΤΙ":
                break;
        }
        if (minute < maxMin)
                return minute+"'";
        else
                return maxMin+"+"+(minute-maxMin)+"'";
    }

    private String setUpTeam(DataLiveActions event, ImageView teamColour){
        if (event.isRaw())
            return event.display();
        if(event.isTeamA()) {
            teamColour.setBackgroundColor(teamA.getColour());
            return event.display().replaceAll("\\$\\$\\$", teamA.getName());
        }
        else {
            teamColour.setBackgroundColor(teamB.getColour());
            return event.display().replaceAll("\\$\\$\\$", teamB.getName());
        }
    }

    public void toggleFlag(){
        if (selectedIndex < 0) return;
        events.get(selectedIndex).toggleFlag();
        notifyDataSetChanged();
    }

    public DataLiveActions deleteEvent(){
        if (selectedIndex < 0) return null;
        DataLiveActions deleted = events.get(selectedIndex);
        events.remove(selectedIndex);
        selectedIndex = events.size()-1;
        notifyDataSetChanged();
        if (deleted.isRaw())
            return null;
        return deleted;
    }

    public void setSelected(int position){
        selectedIndex = position;
        notifyDataSetChanged();
    }

    public DataLiveActions getSelectedEvent(){
        return (selectedIndex >= 0 && selectedIndex < events.size()) ?
                events.get(selectedIndex) : null;
    }

    public ArrayList<String> getActionsForDB(){
        ArrayList<String> actions = new ArrayList<>();
        for (DataLiveActions event: events){
            actions.add(event.toString());
        }
        return actions;
    }

    public ArrayList<DataLiveActions> getEvents(){
        return events;
    }
    public void setEvents(ArrayList<DataLiveActions> events){
        this.events = events;
        selectedIndex = events.size()-1;
        notifyDataSetChanged();
    }

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void parseEvents(ArrayList<String> eventList){
        events = new ArrayList<>();
        for (String event: eventList) {
            DataLiveActions newEvent = new DataLiveActions(event);
            events.add(newEvent);
            Team team = newEvent.isTeamA() ? teamA : teamB;
            if(newEvent.getEvent().contains("ΚΟΚΚΙΝΗ") && newEvent.getEvent().contains("ΚΙΤΡΙΝΗ")) {
                team.getPlayer(newEvent.getPlayer()).setYellowCards(2);
            }else if (newEvent.getEvent().contains("ΚΟΚΚΙΝΗ")){
                team.getPlayer(newEvent.getPlayer()).setHasRedCard(true);
            }else if (newEvent.getEvent().contains("ΚΙΤΡΙΝΗ")){
                team.getPlayer(newEvent.getPlayer()).setYellowCards(1);
            }
        }
    }

    public ArrayList<DataLiveActions> getEventsForPDF(){
        ArrayList<DataLiveActions> eventsForPDF = new ArrayList<>();
        for (DataLiveActions event: events){
            if (!event.isRaw())
                eventsForPDF.add(event);
        }
        return eventsForPDF;
    }
}
