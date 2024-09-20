package com.jvvas.restnewgenstats.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jvvas.restnewgenstats.Api.RequestsOfLoginActivity;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    //Database related
    private Retrofit retrofit;
    private RequestsOfLoginActivity requests;
    private MatchDTO matchDTO;
    private String key;
    // Layout related
    private EditText editTextKeyMatch;
    private Button buttonKeyStartNewGame;
    private Button continueButton;
    private Button startFriendlyGameButton;
    private ProgressBar progressBar;
    // Team objects for continue
    private Team teamA, teamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up retrofit and the layout functionality

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initTheButtons();
        setClickOnTheButtons();
    }

    // ____________________________________ LAYOUT INIT ___________________________________________
    // Find buttons/views
    private void initTheButtons() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        editTextKeyMatch = findViewById(R.id.editText_KeyInput);
        buttonKeyStartNewGame = findViewById(R.id.button_StartNewGame);
        continueButton = findViewById(R.id.button_Continue);
        startFriendlyGameButton = findViewById(R.id.button_StartFriendlyGame);
    }

    // Three buttons to start a game. New Game, Friendly Game and Continue
    private void setClickOnTheButtons() {
        buttonKeyStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKey(true);
            }
        });
        startFriendlyGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeFriendlyGame();
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKey(false);
            }
        });
    }


    // __________________________________ DATABASE CHECKS _________________________________________

    // Checks if the key is not empty and requests the matchDTO with the given key
    // Arguments specifies if we start a new game or selected continue
    private void checkKey(boolean isNewGame) {

        requests = retrofit.create(RequestsOfLoginActivity.class);

        if (editTextKeyMatch.getText().toString().isEmpty())
            showToast("Το πεδίο είναι κενό");
        else {
            progressBar.setVisibility(View.VISIBLE);
            key = editTextKeyMatch.getText().toString();

            getTheMatchDTO(key, isNewGame);
        }
    }

    // Try getting the matchDTO
    // Start a New Game or Continue on Success
    private void getTheMatchDTO(final String key, final boolean isNewGame) {
        Call<MatchDTO> call = requests.getMatchDTO(key);

        call.enqueue(new Callback<MatchDTO>() {
            @Override
            public void onResponse(@NotNull Call<MatchDTO> call, @NotNull Response<MatchDTO> response) {
                if (!response.isSuccessful()) {
                    Log.e("Code : ", response.code() + "");
                    showToast("Δεν υπάρχει το παιχνίδι με αυτό το Key");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                matchDTO = response.body();

                Log.e("matchDTO", Objects.requireNonNull(matchDTO).toString());
                if (isNewGame) startRosterActivity();
                else continueGame();
            }

            @Override
            public void onFailure(@NotNull Call<MatchDTO> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("---------> ", t.getMessage());
            }
        });

    }


    // _____________________________________ CONTINUE _____________________________________________

    // Try making the teams from the matchDTO
    // If they have 11 basic players each continue the game
    private void continueGame() {
        if (makeTeams())
            startCountStatsActivity(false);
        else {
            progressBar.setVisibility(View.INVISIBLE);
            showToast("Selected game has not be initialised. Select New Game");
        }
    }

    // Creates the teams and checks if they have enough basic players selected
    private boolean makeTeams() {
        teamA = new Team(matchDTO.getTeamA(), true);
        teamB = new Team(matchDTO.getTeamB(), false);

        return teamA.getBasicPlayerNumber() == 11 && teamB.getBasicPlayerNumber() == 11;
    }

    // ___________________________________ NEW ACTIVITY ___________________________________________

    // Start RosterReview Activity (New Game)
    private void startRosterActivity() {
        if (matchDTO.getStatus().equals("Final")){
            showToast("Το παιχνίδι έχει οριστικοποιηθεί");
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        matchDTO.setMatchId(key);

        Intent intent = new Intent(this, RosterReview.class);

        intent.putExtra("matchDTO", matchDTO);

        progressBar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    // Start CountStartActivity (Continue, Friendly Game)
    private void startCountStatsActivity(boolean isFriendly) {
        if (!isFriendly)
            matchDTO.setMatchId(key);

        Intent intent = new Intent(this, CountStatsActivity.class);

        intent.putExtra("matchDTO", matchDTO);
        intent.putExtra("teamA", teamA);
        intent.putExtra("teamB", teamB);
        intent.putExtra("isFriendly", isFriendly);
        intent.putExtra("continuing", !isFriendly);

        progressBar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    // __________________________________ TOAST/FRIENDLY __________________________________________
    // Displays given string in a toast
    private void showToast(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    // Start a game with no database information
    private void makeFriendlyGame() {
        // Make two teams with sample players
        teamA = new Team("Ομάδα Α", true);
        teamB = new Team("Ομάδα Β", false);

        // Start CountStatsActivity for a friendly game
        startCountStatsActivity(true);
    }
}

