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

import com.google.gson.Gson;
import com.jvvas.restnewgenstats.Api.RequestsOfLoginActivity;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.RefereeDTO;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    //Database related
    private Retrofit retrofit;
    private RequestsOfLoginActivity requests;
    private Gson gson = new Gson();
    private MatchDTO matchDTO;
    private String key;
    // Layout related
    private EditText editTextKeyMatch ;
    private Button buttonKeyStartNewGame ;
    private Button continueButton;
    private Button startFriendlyGameButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        initTheButtons();
        setClickOnTheButtons();
    }

    private void setClickOnTheButtons() {

        buttonKeyStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKey();
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
//                continueGame();
//                showRefs();
            }
        });
    }

    private void showRefs() {

        String refereesJSON = "[\n" +
                "  {\n" +
                "    \"referee_id\": \"10000001\",\n" +
                "    \"surname\": \"\\u0398\\u0395\\u039f\\u0394\\u03a9\\u03a1\\u039f\\u03a0\\u039f\\u03a5\\u039b\\u039f\\u03a3\",\n" +
                "    \"name\": \"\\u03a3\\u03a4\\u0395\\u03a1\\u0393\\u0399\\u039f\\u03a3\",\n" +
                "    \"organization\": \"\\u03a6\\u039b\\u03a9\\u03a1\\u0399\\u039d\\u0391\\u03a3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000002\",\n" +
                "    \"surname\": \"\\u03a6\\u039f\\u03a5\\u03a3\\u03a4\\u0391\\u039d\\u0391\\u03a3\",\n" +
                "    \"name\": \"\\u039a\\u03a9\\u039d\\u03a3\\u03a4\\u0391\\u039d\\u03a4\\u0399\\u039d\\u039f\\u03a3\",\n" +
                "    \"organization\": \"\\u0398\\u0395\\u03a3\\/\\u039a\\u0397\\u03a3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000003\",\n" +
                "    \"surname\": \"\\u03a4\\u039f\\u03a1\\u039f\\u03a3\\u0399\\u0391\\u0394\\u0397\\u03a3\",\n" +
                "    \"name\": \"\\u039a\\u03a5\\u03a1\\u0399\\u0391\\u039a\\u039f\\u03a3\",\n" +
                "    \"organization\": \"\\u039c\\u0391\\u039a\\u0395\\u0394\\u039f\\u039d\\u0399\\u0391\\u03a3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000004\",\n" +
                "    \"surname\": \"\\u039a\\u039f\\u03a1\\u03a9\\u039d\\u0391\\u03a3\",\n" +
                "    \"name\": \"\\u0391\\u039d\\u0391\\u03a3\\u03a4\\u0391\\u03a3\\u0399\\u039f\\u03a3\",\n" +
                "    \"organization\": \"\\u03a0\\u0395\\u0399\\u03a1\\u0391\\u0399\\u0391\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000005\",\n" +
                "    \"surname\": \"\\u03a4\\u03a3\\u039f\\u03a0\\u039f\\u03a5\\u039b\\u0399\\u0394\\u0397\\u03a3\",\n" +
                "    \"name\": \"\\u03a0\\u0391\\u039d\\u0391\\u0393\\u0399\\u03a9\\u03a4\\u0397\\u03a3\",\n" +
                "    \"organization\": \"\\u039e\\u0391\\u039d\\u0398\\u0397\\u03a3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000006\",\n" +
                "    \"surname\": \"\\u039a\\u0397\\u039b\\u0399\\u039a\\u0399\\u03a9\\u03a4\\u0397\\u03a3\",\n" +
                "    \"name\": \"\\u0394\\u0397\\u039c\\u0397\\u03a4\\u03a1\\u0397\\u03a3\",\n" +
                "    \"organization\": \"\\u039e\\u0391\\u039d\\u0398\\u0397\\u03a3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000007\",\n" +
                "    \"surname\": \"\\u039a\\u039f\\u03a5\\u0392\\u0391\\u039b\\u0391\\u039a\\u0397\\u03a3\",\n" +
                "    \"name\": \"\\u03a0\\u0391\\u039d\\u0391\\u0393\\u0399\\u03a9\\u03a4\\u0397\\u03a3\",\n" +
                "    \"organization\": \"\\u0394\\u03a1\\u0391\\u039c\\u0391\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"referee_id\": \"10000008\",\n" +
                "    \"surname\": \"\\u03a7\\u039f\\u03a1\\u039f\\u0396\\u039f\\u0393\\u039b\\u039f\\u03a5\",\n" +
                "    \"name\": \"\\u0392\\u0391\\u03a3\\u0399\\u039b\\u0395\\u0399\\u039f\\u03a3\",\n" +
                "    \"organization\": \"\\u0394\\u03a1\\u0391\\u039c\\u0391\"\n" +
                "  }\n" +
                "]" ;

        ArrayList<RefereeDTO> refs =   new ArrayList<RefereeDTO>(
                Arrays.asList(  gson.fromJson(refereesJSON, RefereeDTO[].class) )
        );

        System.out.println(refs.toString());

    }

    private void makeFriendlyGame()
    {
        Team teamA = new Team("Ομάδα Α",true);
        teamA.makeSamplePlayers();
        Team teamB = new Team("Ομάδα Β",false);
        teamB.makeSamplePlayers();

        Intent intent = new Intent(LoginActivity.this, CountStatsActivity.class);
        intent.putExtra("teamA",teamA);
        intent.putExtra("teamB",teamB);
        intent.putExtra("isFriendly",true);

        startActivity( intent );
    }

    private void checkKey() {

        requests = retrofit.create(RequestsOfLoginActivity.class);

        if (editTextKeyMatch.getText().toString().equals("") )
            showToast("Το πεδίο είναι κενό");
        else
        {
            progressBar.setVisibility(View.VISIBLE);
            key = editTextKeyMatch.getText().toString();

            // GET REQUEST CODE HERE
//            getTheMatchDTO();
            getTheMatchDTO(key);
        }
    }


    private void getTheMatchDTO(final String key) {

        Call<MatchDTO> call = requests.getMatchDTO(key);

        call.enqueue(new Callback<MatchDTO>() {
            @Override
            public void onResponse(Call<MatchDTO> call, Response<MatchDTO> response) {

                if(!response.isSuccessful()){
                    Log.e("Code : ", response.code()+"");
                    showToast("Δεν υπάρχει το παιχνίδι με αυτό το Key");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                matchDTO = response.body();
                Log.e("matchDTO", matchDTO.toString());
                startRosterActivity();
            }

            @Override
            public void onFailure(Call<MatchDTO> call, Throwable t) {
                Log.e( "---------> ", t.getMessage() );
            }
        });

    }

    private void startRosterActivity() {
        matchDTO.setMatchId(key);

        Intent intent = new Intent(this, RosterReview.class);

        intent.putExtra("matchDTO", matchDTO);
//        intent.putExtra("key", key);

        progressBar.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    private void initTheButtons() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        editTextKeyMatch = findViewById(R.id.editText_KeyInput) ;
        buttonKeyStartNewGame = findViewById(R.id.button_StartNewGame) ;
        continueButton = findViewById(R.id.button_Continue);
        startFriendlyGameButton = findViewById(R.id.button_StartFriendlyGame);
    }

    private void showToast(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}



//    private void getTheMatchDTO() {
//
//        String matchJSON = "{\n" +
//                "  \"league\": \"DOKIMI\",\n" +
//                "  \"league_id\": \"L0000001\",\n" +
//                "  \"season\": \"2016-2017\",\n" +
//                "  \"match_day\": 1,\n" +
//                "  \"field\": \"\",\n" +
//                "  \"place\": \"\",\n" +
//                "  \"organization\": null,\n" +
//                "  \"status\": \"Default\",\n" +
//                "  \"date\": \"31-05-2020\",\n" +
//                "  \"time\": \"19:00\",\n" +
//                "  \"teamA\": {\n" +
//                "    \"team_id\": \"ae_falirou\",\n" +
//                "    \"name\": \"\\u0391\\u0395 \\u03a6\\u03b1\\u03bb\\u03ae\\u03c1\\u03bf\\u03c5\",\n" +
//                "    \"colour\": null,\n" +
//                "    \"coach\": {\n" +
//                "      \"coach_id\": \"karamicha\",\n" +
//                "      \"surname\": \"\\u039a\\u03b1\\u03c1\\u03b1\\u03c3\\u03c4\\u03ac\\u03b8\\u03b7\\u03c2\",\n" +
//                "      \"name\": \"\\u039c\\u03b9\\u03c7\\u03b1\\u03bb\\u03b7\\u03c2\"\n" +
//                "    },\n" +
//                "    \"players\": [\n" +
//                "      {\n" +
//                "        \"player_id\": \"302\",\n" +
//                "        \"surname\": \"\\u03a3\\u03c0\\u03b1\\u03bd\\u03cc\\u03c0\\u03bf\\u03c5\\u03bb\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u039c\\u03b9\\u03c7\\u03b1\\u03bb\\u03b7\\u03c2\",\n" +
//                "        \"number\": 7,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"303\",\n" +
//                "        \"surname\": \"\\u03a1\\u03ad\\u03c0\\u03c0\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u0395\\u03bb\\u03bd\\u03c4\\u03b5\\u03c1\",\n" +
//                "        \"number\": 97,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"304\",\n" +
//                "        \"surname\": \"\\u039d.\\u03a1\\u03ad\\u03c0\\u03c0\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u03a0\\u03b1\\u03bd\\u03b1\\u03b3\\u03b9\\u03c9\\u03c4\\u03b7\\u03c2\",\n" +
//                "        \"number\": 16,\n" +
//                "        \"position\": \"GK\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"305\",\n" +
//                "        \"surname\": \"\\u03a3\\u03b1\\u03b3\\u03bc\\u03b1\\u03c4\\u03cc\\u03c0\\u03bf\\u03c5\\u03bb\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u039c\\u03b1\\u03c1\\u03b9\\u03bf\\u03c2\",\n" +
//                "        \"number\": 11,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"306\",\n" +
//                "        \"surname\": \"\\u039b\\u03b1\\u03bb\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u039d\\u03c4\\u03b9\\u03bc\\u03b9\\u03c4\\u03c1\\u03bf\",\n" +
//                "        \"number\": 8,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"307\",\n" +
//                "        \"surname\": \"\\u0394\\u03ad\\u03ba\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u0393\\u03b9\\u03c9\\u03c1\\u03b3\\u03bf\\u03c2\",\n" +
//                "        \"number\": 15,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"308\",\n" +
//                "        \"surname\": \"\\u03a1\\u03ac\\u03ba\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u039d\\u03b9\\u03ba\\u03bb\\u03b1\\u03c2\",\n" +
//                "        \"number\": 9,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"309\",\n" +
//                "        \"surname\": \"\\u039a\\u03bb\\u03b5\\u03b9\\u03c4\\u03c3\\u03b9\\u03ce\\u03c4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u039a\\u03b5\\u03bd\\u03b1\\u03bd\\u03c4\",\n" +
//                "        \"number\": 17,\n" +
//                "        \"position\": \"RM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"310\",\n" +
//                "        \"surname\": \"\\u039d\\u03ac\\u03c4\\u03c3\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u0391\\u03bd\\u03c4\\u03c1\\u03b5\",\n" +
//                "        \"number\": 91,\n" +
//                "        \"position\": \"RM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"311\",\n" +
//                "        \"surname\": \"\\u0391\\u03bd\\u03b8\\u03cc\\u03c0\\u03bf\\u03c5\\u03bb\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u03a0\\u03b5\\u03c4\\u03c1\\u03bf\\u03c2\",\n" +
//                "        \"number\": 20,\n" +
//                "        \"position\": \"CF\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"312\",\n" +
//                "        \"surname\": \"\\u0395\\u03c5\\u03c3\\u03c4\\u03c1\\u03b1\\u03c4\\u03b9\\u03ac\\u03b4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u039a\\u03c9\\u03c3\\u03c4\\u03b1\\u03c2\",\n" +
//                "        \"number\": 25,\n" +
//                "        \"position\": \"CF\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"313\",\n" +
//                "        \"surname\": \"\\u03a0\\u03b1\\u03c0\\u03b1\\u03c1\\u03af\\u03b6\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"Theodoros\",\n" +
//                "        \"number\": 56,\n" +
//                "        \"position\": \"CF\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"314\",\n" +
//                "        \"surname\": \"\\u03a0\\u03b1\\u03bc\\u03c0\\u03ad\\u03c1\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0393\\u03b9\\u03b1\\u03bd\\u03bd\\u03b7\\u03c2\",\n" +
//                "        \"number\": 18,\n" +
//                "        \"position\": \"RW\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"315\",\n" +
//                "        \"surname\": \"\\u03a4\\u03c3\\u03b9\\u03cc\\u03b3\\u03ba\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u0391\\u03bb\\u03b5\\u03c6\",\n" +
//                "        \"number\": 5,\n" +
//                "        \"position\": \"LW\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"316\",\n" +
//                "        \"surname\": \"\\u03a6\\u03c1\\u03bf\\u03c3\\u03cd\\u03bd\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0393\\u03b9\\u03b1\\u03bd\\u03bd\\u03b7\\u03c2\",\n" +
//                "        \"number\": 89,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"317\",\n" +
//                "        \"surname\": \"\\u03a0\\u03b1\\u03c1\\u03bf\\u03cd\\u03c3\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u03a7\\u03c1\\u03b7\\u03c3\\u03c4\\u03bf\\u03c2\",\n" +
//                "        \"number\": 4,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"318\",\n" +
//                "        \"surname\": \"\\u0391\\u03c3\\u03c0\\u03b9\\u03ce\\u03c4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u039c\\u03b1\\u03c1\\u03ba\\u03bf\",\n" +
//                "        \"number\": 6,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"319\",\n" +
//                "        \"surname\": \"\\u0391\\u03bd\\u03b1\\u03c3\\u03c4\\u03b1\\u03c3\\u03b9\\u03ac\\u03b4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u039b\\u03bf\\u03c5\\u03ba\\u03b1\\u03c2\",\n" +
//                "        \"number\": 1,\n" +
//                "        \"position\": \"GK\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"320\",\n" +
//                "        \"surname\": \"\\u039a\\u03b1\\u03c3\\u03b9\\u03b1\\u03bd\\u03cc\\u03c2\",\n" +
//                "        \"name\": \"\\u0392\\u03b1\\u03c3\\u03b9\\u03bb\\u03b7\\u03c2\",\n" +
//                "        \"number\": 99,\n" +
//                "        \"position\": \"LB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"321\",\n" +
//                "        \"surname\": \"\\u03a3\\u03c5\\u03bc\\u03b5\\u03c9\\u03bd\\u03af\\u03b4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0392\\u03b1\\u03c3\\u03b9\\u03bb\\u03b7\\u03c2\",\n" +
//                "        \"number\": 3,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"teamB\": {\n" +
//                "    \"team_id\": \"paok\",\n" +
//                "    \"name\": \"\\u03a0\\u0391\\u039f\\u039a\",\n" +
//                "    \"colour\": null,\n" +
//                "    \"coach\": {\n" +
//                "      \"coach_id\": \"fereampe\",\n" +
//                "      \"surname\": \"\\u03a6\\u03b5\\u03c1\\u03b5\\u03ca\\u03c1\\u03b1\",\n" +
//                "      \"name\": \"\\u0391\\u03bc\\u03c0\\u03b5\\u03bb\"\n" +
//                "    },\n" +
//                "    \"players\": [\n" +
//                "      {\n" +
//                "        \"player_id\": \"150\",\n" +
//                "        \"surname\": \"\\u03a0\\u03b1\\u03c3\\u03c7\\u03b1\\u03bb\\u03b1\\u03ba\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0391\\u03bb\\u03ad\\u03be\\u03b1\\u03bd\\u03b4\\u03c1\\u03bf\\u03c2\",\n" +
//                "        \"number\": 1,\n" +
//                "        \"position\": \"GK\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"151\",\n" +
//                "        \"surname\": \"\\u03a3\\u03bf\\u03ac\\u03c1\\u03b5\\u03c2\",\n" +
//                "        \"name\": \"\\u03a1\\u03bf\\u03bd\\u03c4\\u03c1\\u03af\\u03b3\\u03ba\\u03bf\",\n" +
//                "        \"number\": 2,\n" +
//                "        \"position\": \"RB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"152\",\n" +
//                "        \"surname\": \"\\u039c\\u03ac\\u03c4\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u039b\\u03b5\\u03bf\\u03bd\\u03ac\\u03c1\\u03bd\\u03c4\\u03bf\",\n" +
//                "        \"number\": 3,\n" +
//                "        \"position\": \"RB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"153\",\n" +
//                "        \"surname\": \"\\u0399\\u03bd\\u03b3\\u03ba\\u03b1\\u03c3\\u03bf\\u03bd\",\n" +
//                "        \"name\": \"\\u03a3\\u03b2\\u03ad\\u03c1\\u03b9\\u03c1\",\n" +
//                "        \"number\": 4,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"154\",\n" +
//                "        \"surname\": \"\\u0392\\u03b1\\u03c1\\u03ad\\u03bb\\u03b1\",\n" +
//                "        \"name\": \"\\u03a6\\u03b5\\u03c1\\u03bd\\u03b1\\u03bd\\u03c4\\u03bf\",\n" +
//                "        \"number\": 5,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"15478\",\n" +
//                "        \"surname\": \"\\u03a4\\u03c3\\u03b1\\u03bf\\u03c5\\u03c3\\u03af\\u03b4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u039d\\u03af\\u03ba\\u03bf\\u03c2\",\n" +
//                "        \"number\": 67,\n" +
//                "        \"position\": \"LWB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"155\",\n" +
//                "        \"surname\": \"\\u039c\\u03b9\\u03c7\\u03ac\\u03b9\",\n" +
//                "        \"name\": \"\\u0395\\u03bd\\u03ad\\u03b1\",\n" +
//                "        \"number\": 19,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"156\",\n" +
//                "        \"surname\": \"\\u039a\\u03c1\\u03ad\\u03c3\\u03c0\\u03bf\",\n" +
//                "        \"name\": \"\\u0386\\u03bd\\u03c7\\u03b5\\u03bb\",\n" +
//                "        \"number\": 15,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"157\",\n" +
//                "        \"surname\": \"\\u0393\\u03b9\\u03b1\\u03bd\\u03bd\\u03bf\\u03cd\\u03bb\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0394\\u03b7\\u03bc\\u03ae\\u03c4\\u03c1\\u03b7\\u03c2\",\n" +
//                "        \"number\": 23,\n" +
//                "        \"position\": \"CB\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"158\",\n" +
//                "        \"surname\": \"\\u039c\\u03b9\\u03c7\\u03b1\\u03b7\\u03bb\\u03af\\u03b4\\u03b7\\u03c2\",\n" +
//                "        \"name\": \"\\u0393\\u03b9\\u03ac\\u03bd\\u03bd\\u03b7\\u03c2\",\n" +
//                "        \"number\": 49,\n" +
//                "        \"position\": \"RM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"159\",\n" +
//                "        \"surname\": \"\\u039a\\u03b1\\u03bd\\u03c4\\u03bf\\u03c5\\u03c1\\u03b9\",\n" +
//                "        \"name\": \"\\u039f\\u03bc\\u03b1\\u03c1\",\n" +
//                "        \"number\": 7,\n" +
//                "        \"position\": \"RM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"160\",\n" +
//                "        \"surname\": \"\\u039c\\u03b1\\u03bf\\u03c5\\u03c1\\u03af\\u03c3\\u03b9\\u03bf\",\n" +
//                "        \"name\": \"\\u03a7\\u03bf\\u03c3\\u03ad\",\n" +
//                "        \"number\": 8,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"161\",\n" +
//                "        \"surname\": \"\\u03a0\\u03ad\\u03bb\\u03ba\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u0394\\u03b7\\u03bc\\u03ae\\u03c4\\u03c1\\u03b7\\u03c2\",\n" +
//                "        \"number\": 10,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"162\",\n" +
//                "        \"surname\": \"\\u039c\\u03b5\\u03bb\\u03b9\\u03cc\\u03c0\\u03bf\\u03c5\\u03bb\\u03bf\\u03c2\",\n" +
//                "        \"name\": \"\\u0394\\u03b7\\u03bc\\u03ae\\u03c4\\u03c1\\u03b7\\u03c2\",\n" +
//                "        \"number\": 14,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"163\",\n" +
//                "        \"surname\": \"\\u0392\\u03ad\\u03c1\\u03bd\\u03bc\\u03c0\\u03bb\\u03bf\\u03c5\\u03bc\",\n" +
//                "        \"name\": \"\\u03a0\\u03cc\\u03bd\\u03c4\\u03bf\\u03c5\\u03c2\",\n" +
//                "        \"number\": 97,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"164\",\n" +
//                "        \"surname\": \"\\u0392\\u03b9\\u03b5\\u03b9\\u03c1\\u03af\\u03bd\\u03b9\\u03b1\",\n" +
//                "        \"name\": \"\\u0391\\u03bd\\u03c4\\u03b5\\u03bb\\u03af\\u03bd\\u03bf\",\n" +
//                "        \"number\": 20,\n" +
//                "        \"position\": \"LW\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"165\",\n" +
//                "        \"surname\": \"\\u039c\\u03c0\\u03af\\u03c3\\u03b5\\u03c3\\u03b2\\u03b1\\u03c1\",\n" +
//                "        \"name\": \"\\u039d\\u03c4\\u03b9\\u03ad\\u03b3\\u03ba\\u03bf\",\n" +
//                "        \"number\": 21,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"166\",\n" +
//                "        \"surname\": \"\\u0395\\u03c3\\u03af\\u03c4\\u03b9\",\n" +
//                "        \"name\": \"\\u0386\\u03bd\\u03c4\\u03b5\\u03c1\\u03c3\\u03bf\\u03bd\",\n" +
//                "        \"number\": 24,\n" +
//                "        \"position\": \"DM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"167\",\n" +
//                "        \"surname\": \"\\u039a\\u03ac\\u03c4\\u03c3\\u03b5\",\n" +
//                "        \"name\": \"\\u0388\\u03c1\\u03b3\\u03ba\\u03bf\\u03c5\\u03c2\",\n" +
//                "        \"number\": 26,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"168\",\n" +
//                "        \"surname\": \"\\u039c\\u03af\\u03c3\\u03b9\\u03c4\\u03c2\",\n" +
//                "        \"name\": \"\\u0393\\u03b9\\u03cc\\u03b6\\u03b9\\u03c0\",\n" +
//                "        \"number\": 31,\n" +
//                "        \"position\": \"CM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"169\",\n" +
//                "        \"surname\": \"\\u039d\\u03c4\\u03ac\\u03b3\\u03ba\\u03bb\\u03b1\\u03c2\",\n" +
//                "        \"name\": \"\\u0391\\u03b3\\u03ba\\u03bf\\u03cd\\u03c3\\u03c4\\u03bf\",\n" +
//                "        \"number\": 33,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"170\",\n" +
//                "        \"surname\": \"\\u0386\\u03ba\\u03c0\\u03bf\\u03bc\\u03c0\",\n" +
//                "        \"name\": \"\\u03a4\\u03c3\\u03bf\\u03cd\\u03bc\\u03c0\\u03b1\",\n" +
//                "        \"number\": 47,\n" +
//                "        \"position\": \"CF\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"171\",\n" +
//                "        \"surname\": \"\\u03a3\\u03b2\\u03b9\\u03bd\\u03c4\\u03ad\\u03c1\\u03c3\\u03ba\\u03b9\",\n" +
//                "        \"name\": \"\\u039a\\u03ac\\u03c1\\u03bf\\u03bb\",\n" +
//                "        \"number\": 9,\n" +
//                "        \"position\": \"AM\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"172\",\n" +
//                "        \"surname\": \"\\u039b\\u03b7\\u03bc\\u03bd\\u03b9\\u03cc\\u03c2\",\n" +
//                "        \"name\": \"\\u0394\\u03b7\\u03bc\\u03ae\\u03c4\\u03c1\\u03b7\\u03c2\",\n" +
//                "        \"number\": 18,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"173\",\n" +
//                "        \"surname\": \"\\u0396\\u03b1\\u03bc\\u03c0\\u03ac\",\n" +
//                "        \"name\": \"\\u039b\\u03b5\\u03bf\\u03bd\\u03ac\\u03c1\\u03bd\\u03c4\\u03bf\",\n" +
//                "        \"number\": 98,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"player_id\": \"174\",\n" +
//                "        \"surname\": \"\\u03a3\\u03c4\\u03bf\\u03c7\",\n" +
//                "        \"name\": \"\\u039c\\u03af\\u03c1\\u03bf\\u03c3\\u03bb\\u03b1\\u03b2\",\n" +
//                "        \"number\": 99,\n" +
//                "        \"position\": \"SS\",\n" +
//                "        \"selection\": null\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"colour\": \"#\",\n" +
//                "  \"basicPlayers\": \"#\",\n" +
//                "  \"replacementPlayers\": \"#\",\n" +
//                "  \"delaysA\": 0,\n" +
//                "  \"delaysB\": 0,\n" +
//                "  \"delaysExtraA\": 0,\n" +
//                "  \"delaysExtraB\": 0,\n" +
//                "  \"referee\": {\n" +
//                "    \"referee_id\": \"10000001\",\n" +
//                "    \"surname\": \"\\u0398\\u0395\\u039f\\u0394\\u03a9\\u03a1\\u039f\\u03a0\\u039f\\u03a5\\u039b\\u039f\\u03a3\",\n" +
//                "    \"name\": \"\\u03a3\\u03a4\\u0395\\u03a1\\u0393\\u0399\\u039f\\u03a3\",\n" +
//                "    \"organization\": \"\\u03a6\\u039b\\u03a9\\u03a1\\u0399\\u039d\\u0391\\u03a3\"\n" +
//                "  },\n" +
//                "  \"refereeHelperA\": {\n" +
//                "    \"referee_id\": \"10000003\",\n" +
//                "    \"surname\": \"\\u03a4\\u039f\\u03a1\\u039f\\u03a3\\u0399\\u0391\\u0394\\u0397\\u03a3\",\n" +
//                "    \"name\": \"\\u039a\\u03a5\\u03a1\\u0399\\u0391\\u039a\\u039f\\u03a3\",\n" +
//                "    \"organization\": \"\\u039c\\u0391\\u039a\\u0395\\u0394\\u039f\\u039d\\u0399\\u0391\\u03a3\"\n" +
//                "  },\n" +
//                "  \"refereeHelperB\": {\n" +
//                "    \"referee_id\": \"10000002\",\n" +
//                "    \"surname\": \"\\u03a6\\u039f\\u03a5\\u03a3\\u03a4\\u0391\\u039d\\u0391\\u03a3\",\n" +
//                "    \"name\": \"\\u039a\\u03a9\\u039d\\u03a3\\u03a4\\u0391\\u039d\\u03a4\\u0399\\u039d\\u039f\\u03a3\",\n" +
//                "    \"organization\": \"\\u0398\\u0395\\u03a3\\/\\u039a\\u0397\\u03a3\"\n" +
//                "  },\n" +
//                "  \"refereeD\": {\n" +
//                "    \"referee_id\": \"10000008\",\n" +
//                "    \"surname\": \"\\u03a7\\u039f\\u03a1\\u039f\\u0396\\u039f\\u0393\\u039b\\u039f\\u03a5\",\n" +
//                "    \"name\": \"\\u0392\\u0391\\u03a3\\u0399\\u039b\\u0395\\u0399\\u039f\\u03a3\",\n" +
//                "    \"organization\": \"\\u0394\\u03a1\\u0391\\u039c\\u0391\"\n" +
//                "  },\n" +
//                "  \"LIVE\": {},\n" +
//                "  \"ActionsLive\": []\n" +
//                "}" ;
//
//        matchDTO = gson.fromJson(matchJSON, MatchDTO.class);
//
//        startRosterActivity();
//    }

