 package com.jvvas.restnewgenstats.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Api.RequestsOfLoginActivity;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class RosterReview extends AppCompatActivity {

     private Retrofit retrofit;
     private RequestsOfRosterReview requests;

     private FragmentManager fragmentManager = getSupportFragmentManager();;

    private GameDetails matchDetails ;
    private Referees referees ;
    private RosterAdapter adapter;

    private MatchDTO matchDTO;
    private TeamDTO teamDtoA, teamDtoB;
    private RefereeDTO refDTO, refHelpADto, refHelpBDto, refHelpDDto;

    private Team teamA, teamB ;

    private TextView title,playerCount;
    private Button confirm, previous, button_newPlayer;
    private TextView coach;
    private Group listItems;
    private String refereeId , refereeHelperAId , refereeHelperBId,refereeDId ;
    private String gameTime, gameDate, gameField, gamePlace, gameOrganization;

    //  0 = gameDetails , 1 = teamA , 2 = teamB , 3 = referees
    private int displayedNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_review);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initViews();
        getDataFromLoginActivity();
        prepareList();
        addButtonsOnClick();
        getMatchDetails();
        displayMachDetails();
    }

    private void getDataFromLoginActivity() {

        matchDTO = (MatchDTO) getIntent().getSerializableExtra("matchDTO");

        if(matchDTO.getColour().equals(null) ||
                (matchDTO.getColour().equals("#")) )
        {
            matchDTO.setColour("#01FF70##0074D9");
            System.out.println(matchDTO.toString());
        }

        teamDtoA = matchDTO.getTeamA();
        teamA = getTheTeamFromDTO(teamDtoA, true);
        teamDtoB = matchDTO.getTeamB();
        teamB = getTheTeamFromDTO(teamDtoB, false);

//        String[] parts = matchDTO.getColour().split("#");
//        String c1 = parts[1];
//        String c2 = parts[3];
//        teamDtoA.setColour("#"+c1);
//        teamDtoB.setColour("#"+c2);
//        Log.e("colooooooor here 1" ,teamDtoA.getColour()+"" ) ;
// Mallon einai nulll epeidi einai serilizable
//            teamA.setColour(Color.rgb(255,220,0));
//            teamB.setColour(Color.rgb(57,204,204));
    }

    private Team getTheTeamFromDTO(TeamDTO currentTeamDto, boolean isTeamA) {
        Team t = new Team(currentTeamDto.getName(), isTeamA);

        List<PlayerDTO> listAllPlayers = currentTeamDto.getPlayers();
        List<Player> allPlayers = new ArrayList<>();
        for(PlayerDTO p : listAllPlayers){
            Boolean selection = null;
            if (p.getSelection() != null)
                selection = p.getSelection().equals("basic");
            allPlayers.add( new Player(
                    p.getPlayerId(), p.getSurname(), p.getName(),
                    p.getNumber() + "" , p.getPosition() , selection
            ) );
        }
        t.setAllPlayers((ArrayList<Player>) allPlayers);
        return t;
    }

    private void prepareList(){
        ListView roster = findViewById(R.id.listview_roster);
        adapter = new RosterAdapter(this);
        roster.setAdapter(adapter);
        adapter.setPlayerCount(playerCount);

        roster.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = getSupportFragmentManager();
                PlayerInfoDialog infoDialog = new PlayerInfoDialog();
                infoDialog.setEditedPlayer((Player) adapter.getItem(i));
                infoDialog.setTeam((displayedNow == 1) ? teamA : teamB);
//                infoDialog.setTeamAndRef(getCurrentTeam(),getTeamsRef());

                infoDialog.setListToUpdate(adapter);
                infoDialog.show(fm, "");
                return false;
            }
        });
    }

    private void getMatchDetails() {
        boolean temp = false ;

        if(! matchDTO.getTime().equals(null) )
            gameTime = matchDTO.getTime() ;
        else
            temp = true;

        if(! matchDTO.getDate().equals(null) )
            gameDate = matchDTO.getDate() ;
        else
            temp = true;

        if(! matchDTO.getField().equals(null) )
            gameField = matchDTO.getField() ;
        else
            temp = true;

        if(! matchDTO.getPlace().equals(null) )
            gamePlace = matchDTO.getPlace() ;
        else
            temp = true;

        if( !(matchDTO.getReferee() == null) ){
            refDTO = matchDTO.getReferee() ;
            refereeId = refDTO.getRefereeId();
        }else
            temp = true;

        if ( !(matchDTO.getRefereeHelperA() == null )){
            refHelpADto = matchDTO.getRefereeHelperA() ;
            refereeHelperAId = refHelpADto.getRefereeId();
        }else
            temp = true;

        if ( !(matchDTO.getRefereeHelperB() == null )){
            refHelpBDto = matchDTO.getRefereeHelperB() ;
            refereeHelperBId = refHelpBDto.getRefereeId() ;
        }else
            temp = true;

        if ( !(matchDTO.getRefereeD() == null )){
            refHelpDDto = matchDTO.getRefereeD() ;
            refereeDId =  refHelpDDto.getRefereeId() ;
        }else
            temp = true;

        if (temp){
            Toast.makeText(RosterReview.this, "Υπάρχει κενό πεδίο ή Λείπει κάποιος διαιτητής", Toast.LENGTH_LONG).show();
        }
    }

    private void addButtonsOnClick(){
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (displayedNow==0)
                    displayTeamA();

                else if (displayedNow==1 && adapter.getBasicSelected() == 11)
                    displayTeamB();

                else if (displayedNow==1)
                    Toast.makeText(RosterReview.this,
                            "You have selected less than 11 players", Toast.LENGTH_LONG).show();

                else if (displayedNow==2&& adapter.getBasicSelected() == 11)
                    displayReferees();

                else if (displayedNow==2)
                    Toast.makeText(RosterReview.this,
                            "You have selected less than 11", Toast.LENGTH_LONG).show();

                else
                    startNextActivity();
            }
        });

        previous.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                previousFragment();
            }
        });

        button_newPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                PlayerInfoDialog newPlDialog = new PlayerInfoDialog();

//                newPlDialog.setTeamAndRef(getCurrentTeam(),getTeamsRef());
                newPlDialog.setTeam((displayedNow == 1) ? teamA : teamB);
                newPlDialog.setListToUpdate(adapter);
                newPlDialog.show(fm, "");
            }
        });
    }

    private void previousFragment(){
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

    private void startNextActivity(){
        requests = retrofit.create(RequestsOfRosterReview.class);
        updateRefereesOfMatchDto();
        updateTheMatch();
    }

     private void updateTheMatch() {
         Call<Void> call = requests.postMatch( matchDTO ) ;
//         Call<MatchDTO> call = requests.putMatch( matchDTO.getMatchId() ,  matchDTO ) ;

         Log.e("PERFECT LIFE" , teamDtoA.toString()) ;

         call.enqueue(new Callback<Void>() {
             @Override
             public void onResponse(Call<Void> call, Response<Void> response) {
                 if(!response.isSuccessful()){
                     return;
                 }
                 Intent intent = new Intent(RosterReview.this, CountStatsActivity.class);

                 intent.putExtra("teamA",teamA);
                 intent.putExtra("teamB",teamB);
                 intent.putExtra("isFriendly",false);
                 intent.putExtra("matchDTO", matchDTO);

                 RosterReview.this.startActivity( intent );
                 finish();
             }

             @Override
             public void onFailure(Call<Void> call, Throwable t) {
                 Log.e( "---------> ", t.getMessage() );
             }


         });
     }

     private void updateRefereesOfMatchDto() {

        if (matchDTO.getReferee() != null){
            matchDTO.getReferee().setSurname(referees.getSurnameFirst().getText().toString());
            matchDTO.getReferee().setName(referees.getNameFirst().getText().toString());
            matchDTO.getReferee().setOrganization(referees.getOrganizationFirst().getText().toString());
        }

        if (matchDTO.getRefereeHelperA() != null){
             matchDTO.getRefereeHelperA().setSurname(referees.getSurnameSideA().getText().toString());
             matchDTO.getRefereeHelperA().setName(referees.getNameSideA().getText().toString());
             matchDTO.getRefereeHelperA().setOrganization(referees.getOrganizationSideA().getText().toString());
        }

         if (matchDTO.getRefereeHelperB() != null){
             matchDTO.getRefereeHelperB().setSurname(referees.getSurnameSideB().getText().toString());
             matchDTO.getRefereeHelperB().setName(referees.getNameSideB().getText().toString());
             matchDTO.getRefereeHelperB().setOrganization(referees.getOrganizationSideB().getText().toString());
         }

         if (matchDTO.getRefereeD() != null){
             matchDTO.getRefereeD().setSurname(referees.getSurnameFourth().getText().toString());
             matchDTO.getRefereeD().setName(referees.getNameFourth().getText().toString());
             matchDTO.getRefereeD().setOrganization(referees.getOrganizationFourth().getText().toString());
         }
    }

    @SuppressLint("SetTextI18n")
    private void displayMachDetails()
    {
        displayedNow = 0;
        listItems.setVisibility(View.INVISIBLE);
        matchDetails = new GameDetails();

//        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction
                fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.forFrag, matchDetails);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        title.setText("GAME DETAILS");
        ViewTreeObserver vto = findViewById(R.id.forFrag).getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                matchDetails.setDetails( gameTime, gameDate, teamA.getName(), teamB.getName(),
                        matchDTO.getMatchId() , gameField, gamePlace, gameOrganization );
                //findViewById(R.id.forFrag).getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void displayTeamA(){
        removeFragment(matchDetails);
        listItems.setVisibility(View.VISIBLE);
        displayedNow = 1;
        makeDisplayForTeam(teamA);
        updateValuesOfMatchDto();
        adapter.updatePlayerCount();
    }

    private void updateValuesOfMatchDto() {

        if (matchDetails != null){
            matchDTO.setField(matchDetails.getField().getText().toString());
            matchDTO.setDate(matchDetails.getDate().getText().toString());
            matchDTO.setTime(matchDetails.getTime().getText().toString());
            matchDTO.setPlace(matchDetails.getPlace().getText().toString());

            teamA.setColour(matchDetails. getColour(1));
            teamB.setColour(matchDetails.getColour(2));

            matchDTO.setColour(teamA.getHexColour() + "#" + teamB.getHexColour());
            teamDtoA.setColour( teamA.getHexColour());
            teamDtoB.setColour( teamB.getHexColour());
        }
    }

    private void displayTeamB()
    {
        removeFragment(referees);
        listItems.setVisibility(View.VISIBLE);

        // Create playersDto for Team A
        PlayerDTO cPlayerDto;
        ArrayList<PlayerDTO> currentPlayersList = new ArrayList<>();
        for(Player p : teamA.getAllPlayers()){
            cPlayerDto = new PlayerDTO(p) ;
            currentPlayersList.add(cPlayerDto);
        }
        teamDtoA.setPlayers(currentPlayersList);

        displayedNow = 2;
        makeDisplayForTeam(teamB);
        adapter.updatePlayerCount();
    }

    @SuppressLint("SetTextI18n")
    private void displayReferees()
    {
        displayedNow = 3;
        listItems.setVisibility(View.INVISIBLE);

        // Create playersDto for Team B
        PlayerDTO cPlayerDto;
        ArrayList<PlayerDTO> currentPlayersList = new ArrayList<>();
        for(Player p : teamB.getAllPlayers()){
            cPlayerDto = new PlayerDTO(p) ;
            currentPlayersList.add(cPlayerDto);
            System.out.println(cPlayerDto);
        }
        teamDtoB.setPlayers(currentPlayersList);

        referees = new Referees();

//        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction
                fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.forFrag, referees);
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

        title.setText("REFEREES");
        setReferees();
    }

    private void setReferees() {
        if ( refDTO != null )
            referees.setFirst(refDTO.getSurname(), refDTO.getName(), refDTO.getOrganization());

        if ( refHelpADto != null )
            referees.setSideA(refHelpADto.getSurname(), refHelpADto.getName(), refHelpADto.getOrganization());

        if ( refHelpBDto != null )
            referees.setSideB(refHelpBDto.getSurname(), refHelpBDto.getName(), refHelpBDto.getOrganization());

        if ( refHelpDDto != null )
            referees.setFourth(refHelpDDto.getSurname(), refHelpDDto.getName(), refHelpDDto.getOrganization());
    }

    private void removeFragment(Fragment toRemove)
    {
        if (toRemove!=null)
        {
//            FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction
                    fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(toRemove);
            fragmentTransaction.commit();
        }
    }

    @SuppressLint("SetTextI18n")
    private void makeDisplayForTeam(Team team)
    {
        title.setText("ΡΟΣΤΕΡ " + team.getName());
        adapter.changeTeam(team);
        coach.setText(team.getCoachFullName());
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        previousFragment();
    }

    // -------- Getters -----
    public String getRefereeId() {
        return refereeId;
    }

    public String getRefereeHelperAId() {
        return refereeHelperAId;
    }

    public String getRefereeHelperBId() {
        return refereeHelperBId;
    }

    public String getRefereeDId() {
        return refereeDId;
    }


}
