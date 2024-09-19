package com.jvvas.restnewgenstats.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Api.RequestsOfRosterReview;
import com.jvvas.restnewgenstats.Dialog.PlayerInfoDialog;
import com.jvvas.restnewgenstats.Fragments.GameDetails;
import com.jvvas.restnewgenstats.Fragments.Referees;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.PlayerDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.RefereeDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;
import com.jvvas.restnewgenstats.Adapters.RosterAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RosterReview extends AppCompatActivity {

    private Retrofit retrofit;

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private GameDetails matchDetails;
    private Referees referees;
    private RosterAdapter adapter;

    private MatchDTO matchDTO;
    private TeamDTO teamDtoA, teamDtoB;
    private RefereeDTO refDTO, refHelpADto, refHelpBDto, refHelpDDto;

    private Team teamA, teamB;

    private TextView title, playerCount;
    private Button confirm, previous, button_newPlayer;
    private TextView coach;
    private Group listItems;

    //  0 = gameDetails , 1 = teamA , 2 = teamB , 3 = referees
    private int displayedNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_review);

        // Set up retrofit and the layout functionality

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initViews();
        addButtonsOnClick();

        // Get intent info and set up any objects needed
        getDataFromLoginActivity();
        prepareList();
        getMatchDetails();

        // Display the match details
        displayMachDetails();
    }
    // ___________________________________ LAYOUT INIT ____________________________________________
    // Find buttons/views
    private void initViews() {
        title = findViewById(R.id.textview_team);
        previous = findViewById(R.id.button_previous);
        confirm = findViewById(R.id.button_confirm);
        button_newPlayer = findViewById(R.id.button_newPlayer);
        coach = findViewById(R.id.textView_coach);
        listItems = findViewById(R.id.group_listItems);
        listItems.setVisibility(View.GONE);
        playerCount = findViewById(R.id.textView_rosterPlayerCount);
    }

    // Add the buttons on Click methods for swapping between viewed content or making a new player
    private void addButtonsOnClick() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousFragment();
            }
        });

        button_newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayerInfoDialog(false, null);
            }
        });
    }

    // Get the roster adapter ready
    private void prepareList() {
        ListView roster = findViewById(R.id.listview_roster);
        adapter = new RosterAdapter(this);
        roster.setAdapter(adapter);
        adapter.setPlayerCount(playerCount);

        roster.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                openPlayerInfoDialog(true, (Player) adapter.getItem(i));
                return false;
            }
        });
    }

    // Open player dialog for edit or new player
    private void openPlayerInfoDialog(boolean editing, Player edited){
        FragmentManager fm = getSupportFragmentManager();
        PlayerInfoDialog infoDialog = new PlayerInfoDialog();

        if(editing)
            infoDialog.setEditedPlayer(edited);

        infoDialog.setTeam((displayedNow == 1) ? teamA : teamB);
        infoDialog.setListToUpdate(adapter);
        infoDialog.show(fm, "");
    }

    // _________________________________ CHANGING DISPLAY _________________________________________
    // Method for going backwards in displays (previous button, back button)
    private void previousFragment() {
        switch (displayedNow) {
            case 0:
                finish();
                break;
            case 1:
                displayMachDetails();
                break;
            case 2:
                displayTeamA();
                break;
            case 3:
                displayTeamB();
                break;
        }
    }
    // Method for going frontwards in displays (confirm button)
    private void nextFragment(){
        switch (displayedNow) {
            case 0:
                displayTeamA();
                break;
            case 1:
                if (adapter.getBasicSelected() == 11){
                    String msg = adapter.findIfThereIsAnyInvalidInput();
                    if(msg!=null && msg.equals("notFoundAnyInvalidInput")){
                        displayTeamB();
                    }else {
                        showToast(msg);
                    }
                }else
                    showToast("Less than 11 players selected");
                break;
            case 2:
                if (adapter.getBasicSelected() == 11){
                    String msg = adapter.findIfThereIsAnyInvalidInput();
                    if(msg!=null && msg.equals("notFoundAnyInvalidInput")){
                       displayReferees();
                    }else {
                        showToast(msg);
                    }
                }
                else
                    showToast("Less than 11 players selected");
                break;
            case 3:
                startNextActivity();
                break;
        }
    }
    // Update database and open next activity
    private void startNextActivity() {
        updateRefereesOfMatchDto();
        updateTheMatch();
    }
    // Sends the matchDTO to the database so what we changed is updated
    private void updateTheMatch() {
        RequestsOfRosterReview requests = retrofit.create(RequestsOfRosterReview.class);
        Call<Void> call = requests.postMatch(matchDTO);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Intent intent = new Intent(RosterReview.this, CountStatsActivity.class);

                intent.putExtra("teamA", teamA);
                intent.putExtra("teamB", teamB);
                intent.putExtra("matchDTO", matchDTO);

                RosterReview.this.startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("---------> ", t.getMessage());
            }
        });
    }

    // ____________________________ INTENT INFO AND MAKE TEAMS ____________________________________

    // Get intent info and make the teams
    private void getDataFromLoginActivity() {
        matchDTO = (MatchDTO) getIntent().getSerializableExtra("matchDTO");

        if (matchDTO.getColour() == null || matchDTO.getColour().equals("#")) {
            matchDTO.setColour("#01FF70##0074D9");
            System.out.println(matchDTO.toString());
        }

        teamDtoA = matchDTO.getTeamA();
        teamA = new Team(teamDtoA, true);
        teamDtoB = matchDTO.getTeamB();
        teamB = new Team(teamDtoB, false);
    }

    // _________________________________ DISPLAYING INFO __________________________________________

    @SuppressLint("SetTextI18n")
    private void displayMachDetails() {
        displayedNow = 0;
        listItems.setVisibility(View.INVISIBLE);
        matchDetails = new GameDetails();

        openFragment(matchDetails);

        title.setText("GAME DETAILS");
        // Makes sure the transaction is made so we don't setDetails to null reference
        ViewTreeObserver vto = findViewById(R.id.forFrag).getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                matchDetails.setDetails(matchDTO.getTime(), matchDTO.getDate(), teamA.getName(),
                        teamB.getName(), matchDTO.getMatchId(), matchDTO.getField(),
                        matchDTO.getPlace(), matchDTO.getOrganization(),String.valueOf(matchDTO.getMatchDay()));
            }
        });
    }

    private void displayTeamA() {
        displayedNow = 1;
        removeFragment(matchDetails);
        listItems.setVisibility(View.VISIBLE);

        makeDisplayForTeam(teamA);
        updateValuesOfMatchDto();
        adapter.updatePlayerCount();
    }

    private void displayTeamB() {
        displayedNow = 2;
        removeFragment(referees);
        listItems.setVisibility(View.VISIBLE);

        teamDtoA.setPlayers(createPlayerDTOs(teamA));

        makeDisplayForTeam(teamB);
        adapter.updatePlayerCount();
    }

    @SuppressLint("SetTextI18n")
    private void displayReferees() {
        displayedNow = 3;
        listItems.setVisibility(View.INVISIBLE);

        teamDtoB.setPlayers(createPlayerDTOs(teamB));

        referees = new Referees();
        openFragment(referees);

        title.setText("REFEREES");
        setReferees();
    }

    private void setReferees() {
        if (refDTO != null)
            referees.setFirst(refDTO.getSurname(), refDTO.getName(), refDTO.getOrganization());

        if (refHelpADto != null)
            referees.setSideA(refHelpADto.getSurname(), refHelpADto.getName(), refHelpADto.getOrganization());

        if (refHelpBDto != null)
            referees.setSideB(refHelpBDto.getSurname(), refHelpBDto.getName(), refHelpBDto.getOrganization());

        if (refHelpDDto != null)
            referees.setFourth(refHelpDDto.getSurname(), refHelpDDto.getName(), refHelpDDto.getOrganization());
    }

    // _____________________________________ HELPFUL METHODS ______________________________________
    private ArrayList<PlayerDTO> createPlayerDTOs(Team team){
        PlayerDTO cPlayerDto;
        ArrayList<PlayerDTO> currentPlayersList = new ArrayList<>();
        for (Player p : team.getAllPlayers()) {
            cPlayerDto = new PlayerDTO(p);
            currentPlayersList.add(cPlayerDto);
        }
        return currentPlayersList;
    }

    // Referees / Match details are fragments on top of the list
    // Need to remove these when displaying the list again
    private void removeFragment(Fragment toRemove) {
        if (toRemove != null) {
            final android.support.v4.app.FragmentTransaction
                    fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(toRemove);
            fragmentTransaction.commit();
        }
    }

    private void openFragment(Fragment toOpen){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.forFrag, toOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @SuppressLint("SetTextI18n")
    private void makeDisplayForTeam(Team team) {
        title.setText("ΡΟΣΤΕΡ " + team.getName());
        adapter.changeTeam(team);
        coach.setText(team.getCoachFullName());
    }


    // Override back button not to call previous fragment or the activity might close
    @Override
    public void onBackPressed() {
        previousFragment();
    }

    // Displays given string in a toastM11
    private void showToast(String text) {
        Toast.makeText(RosterReview.this, text, Toast.LENGTH_LONG).show();
    }



    private void getMatchDetails() {
        refDTO = matchDTO.getReferee();
        refHelpADto = matchDTO.getRefereeHelperA();
        refHelpBDto = matchDTO.getRefereeHelperB();
        refHelpDDto = matchDTO.getRefereeD();

        if (matchDTO.getTime()==null || matchDTO.getDate()==null || matchDTO.getField()==null ||
            matchDTO.getPlace()==null || matchDTO.getReferee()==null ||
            matchDTO.getRefereeHelperA()==null || matchDTO.getRefereeHelperB()==null ||
            matchDTO.getRefereeD()==null || matchDTO.getOrganization()==null)
                showToast("Υπάρχει κενό πεδίο στις πληροφορίες του αγώνα!");
    }

    // ____________________________________ DTO UPDATES ___________________________________________

    private void updateValuesOfMatchDto() {
        if (matchDetails != null) {
            matchDTO.setField(matchDetails.getField().getText().toString());
            matchDTO.setDate(matchDetails.getDate().getText().toString());
            matchDTO.setTime(matchDetails.getTime().getText().toString());
            matchDTO.setPlace(matchDetails.getPlace().getText().toString());

            teamA.setColour(matchDetails.getColour(1));
            teamB.setColour(matchDetails.getColour(2));

            matchDTO.setColour(teamA.getHexColour() + "#" + teamB.getHexColour());
            teamDtoA.setColour(teamA.getHexColour());
            teamDtoB.setColour(teamB.getHexColour());
        }
    }

    private void updateRefereesOfMatchDto() {

        if (matchDTO.getReferee() != null) {
            matchDTO.getReferee().setSurname(referees.getSurnameFirst().getText().toString());
            matchDTO.getReferee().setName(referees.getNameFirst().getText().toString());
            matchDTO.getReferee().setOrganization(referees.getOrganizationFirst().getText().toString());
        }

        if (matchDTO.getRefereeHelperA() != null) {
            matchDTO.getRefereeHelperA().setSurname(referees.getSurnameSideA().getText().toString());
            matchDTO.getRefereeHelperA().setName(referees.getNameSideA().getText().toString());
            matchDTO.getRefereeHelperA().setOrganization(referees.getOrganizationSideA().getText().toString());
        }

        if (matchDTO.getRefereeHelperB() != null) {
            matchDTO.getRefereeHelperB().setSurname(referees.getSurnameSideB().getText().toString());
            matchDTO.getRefereeHelperB().setName(referees.getNameSideB().getText().toString());
            matchDTO.getRefereeHelperB().setOrganization(referees.getOrganizationSideB().getText().toString());
        }

        if (matchDTO.getRefereeD() != null) {
            matchDTO.getRefereeD().setSurname(referees.getSurnameFourth().getText().toString());
            matchDTO.getRefereeD().setName(referees.getNameFourth().getText().toString());
            matchDTO.getRefereeD().setOrganization(referees.getOrganizationFourth().getText().toString());
        }
    }

}
