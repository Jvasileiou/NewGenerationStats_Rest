package com.jvvas.restnewgenstats.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droidnet.DroidListener;
import com.droidnet.DroidNet;
import com.jvvas.restnewgenstats.Adapters.BasicPlayerAdapter;
import com.jvvas.restnewgenstats.Adapters.EventAdapter;
import com.jvvas.restnewgenstats.Api.RequestsOfCountStatsActivity;
import com.jvvas.restnewgenstats.Dialog.AreYouSureDialog;
import com.jvvas.restnewgenstats.Dialog.ConfTeamDialog;
import com.jvvas.restnewgenstats.Dialog.EditEventDialog;
import com.jvvas.restnewgenstats.Dialog.EventDisplayDialog;
import com.jvvas.restnewgenstats.Dialog.StatsDialog;
import com.jvvas.restnewgenstats.Dialog.TelephonesDialog;
import com.jvvas.restnewgenstats.Fragments.ChangePlayerFragment;
import com.jvvas.restnewgenstats.Fragments.Dieisdusi1V1Fragment;
import com.jvvas.restnewgenstats.Fragments.FaoulFragment;
import com.jvvas.restnewgenstats.Fragments.FieldFragment;
import com.jvvas.restnewgenstats.Fragments.GemismaFragment;
import com.jvvas.restnewgenstats.Fragments.GoalFragment;
import com.jvvas.restnewgenstats.Fragments.LathosFragment;
import com.jvvas.restnewgenstats.Fragments.PenaltyFragment;
import com.jvvas.restnewgenstats.Fragments.TelikiFragment;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.LiveDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.MatchDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.PlayerDTO;
import com.jvvas.restnewgenstats.JavaDataTransferObjects.TeamDTO;
import com.jvvas.restnewgenstats.Objects.DataLiveActions;
import com.jvvas.restnewgenstats.Objects.LiveUpdater;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.Objects.TemplatePdf;
import com.jvvas.restnewgenstats.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountStatsActivity extends AppCompatActivity implements DroidListener, AdapterView.OnItemSelectedListener, AreYouSureDialog.DialogListener {

    // Constants for permission request codes
    private static final int WRITE_STORAGE_PERMISSION_CODE = 100;
    private static final int MANAGE_STORAGE_PERMISSION_CODE = 101;

    // ______________ Lib's _____________
    private RequestsOfCountStatsActivity requests;
    private int pixels;

    // ___________ Fragments ___________
    private FieldFragment fieldFragment = new FieldFragment();
    private LathosFragment lathosFragment = new LathosFragment();
    private FaoulFragment faoulFragment = new FaoulFragment();
    private GemismaFragment gemismaFragment = new GemismaFragment();
    private TelikiFragment telikiFragment = new TelikiFragment();
    private GoalFragment goalFragment = new GoalFragment();
    private PenaltyFragment penaltyFragment = new PenaltyFragment();
    private Dieisdusi1V1Fragment dieisdusi1V1Fragment = new Dieisdusi1V1Fragment();
    private ChangePlayerFragment changePlayer;

    // ___________  Fields & Buttons _____________
    private ImageView imgViewCorner_LLeft, imgViewCorner_RLeft, imgViewCorner_LRight,
            imgViewCorner_RRight, imgViewOffside_Left, imgViewOffside_Right;
    private ImageView redCardButton, yellowCardButton;

    private TextView textViewTeliki, textViewGemisma, textViewFaoul, textViewLathos,
            textViewGoal, textView1V1, textViewGoalLeft, textViewGoalRight;
    private TextView textViewDelays, connectionStatus, textViewStatus, allaghRight, allaghLeft,
            leftName, rightName;

    private ListView leftList, rightList, eventList;
    private Chronometer chronometer;
    private Spinner spinnerMenu;

    private EventAdapter eventAdapter;

    // __________ Objects ___________
    private BasicPlayerAdapter leftAdapter;
    private BasicPlayerAdapter rightAdapter;
    private Team leftTeam, rightTeam;
    private LiveUpdater statUpdater;
    private String location = " ", gameStatus = "Default", playerButtonAction = "default",
            numberOfShirt = "0", amForAction = "0";
    private boolean isFriendly = false, isParatasiSelected = false, isTeamAForAction = true,
            buttonsUnclickable = true, running = false;
    public static boolean isDefault = true;

    // _____________ DTOs __________________
    private MatchDTO matchDTO;

    Animation scalingAnimation;
    AlertDialog dialog;
    private int counterOfClicks = 0;
    private Handler handler = new Handler();
    protected TemplatePdf templatePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_stats);

        initializeTheButtons();
        setupRetrofit();
        takeIntentInfo();
        addFragmentField();
        droidNetAndMetrics();
        makeLists();
        createChronometerFormat();
        clickButtons();

        setUnclickableAllButtons();
    }

    // ______________________________________ SET UP ______________________________________________
    // Retrofit
    private void setupRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        requests = retrofit.create(RequestsOfCountStatsActivity.class);
    }

    // DroidNet
    private void droidNetAndMetrics() {
        // Hides the navigation bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        pixels = Math.round(10 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        //No use for friendly game
        if (!isFriendly) {
            DroidNet mDroidNet;
            try {
                mDroidNet = DroidNet.getInstance();
            } catch (IllegalStateException fuckYouTooAndroid) {
                DroidNet.init(getBaseContext().getApplicationContext());
                mDroidNet = DroidNet.getInstance();
            }
            mDroidNet.addInternetConnectivityListener(this);
        }
    }

    // MatchDTO / Other Intent info
    public void takeIntentInfo() {
        isFriendly = getIntent().getBooleanExtra("isFriendly", false);
        boolean isContinuing = getIntent().getBooleanExtra("continuing", false);
        leftTeam = (Team) getIntent().getSerializableExtra("teamA");
        rightTeam = (Team) getIntent().getSerializableExtra("teamB");
        if (!isFriendly) {
            matchDTO = (MatchDTO) getIntent().getSerializableExtra("matchDTO");
            //These are supposed to set things to 0 for new games
            if (!isContinuing)
                resetStats();
            else {
                String[] goals = matchDTO.getLive().getGoal().split("#");
                textViewGoalLeft.setText(goals[isDefault ? 0 : 1]);
                textViewGoalRight.setText(goals[isDefault ? 1 : 0]);
            }
        } else {
            matchDTO = new MatchDTO("", "", "", 0, "", "", "", "", "", "", "", 0, 0, 0, 0, "", "",
                    new LiveDTO(), new ArrayList<String>(), null, null, null, null, null, null);
        }
        statUpdater = new LiveUpdater(matchDTO.getLive());
        gameStatus = matchDTO.getStatus();
    }

    // Reset matchDTO info and send it to base (New Game)
    private void resetStats() {
        matchDTO.setStatus("Pre Game");
        matchDTO.setDelaysA(0);
        matchDTO.setDelaysB(0);
        matchDTO.setDelaysExtraA(0);
        matchDTO.setDelaysExtraB(0);
        matchDTO.setLive(new LiveDTO());
        matchDTO.setActionsLive(new ArrayList<String>());
        sendWholeDTO();
    }


    private void addFragmentField() {
        openFragment(fieldFragment);
    }

    // ____________________________________ FIND VIEWS ____________________________________________

    @SuppressLint("SetTextI18n")
    private void initializeTheButtons() {

        //Top of the activity
        textViewStatus = findViewById(R.id.textView_Status);
        connectionStatus = findViewById(R.id.textView_Connection);
        chronometer = findViewById(R.id.chronometer_Time);
        chronometer.setText("00:00");

        //Corners and offside buttons for left team
        imgViewCorner_RRight = findViewById(R.id.imageView_CornerRRight);
        imgViewOffside_Right = findViewById(R.id.imageView_OffsideRight);
        imgViewCorner_RLeft = findViewById(R.id.imageView_CornerRLeft);
        //and for right team
        imgViewCorner_LRight = findViewById(R.id.imageView_CornerLRight);
        imgViewOffside_Left = findViewById(R.id.imageView_OffsideLeft);
        imgViewCorner_LLeft = findViewById(R.id.imageView_CornerLLeft);

        //Action buttons bellow the field fragment
        textViewTeliki = findViewById(R.id.textView_Teliki);
        textViewGemisma = findViewById(R.id.textView_Gemisma);
        textView1V1 = findViewById(R.id.textView_1V1);
        textViewFaoul = findViewById(R.id.textView_Foul);
        textViewLathos = findViewById(R.id.textView_Lathos);
        textViewGoal = findViewById(R.id.textView_Goal);
        //TextViews that keep track of score
        textViewGoalLeft = findViewById(R.id.textView_GoalLeft);
        textViewGoalRight = findViewById(R.id.textView_GoalRight);

        //The two card buttons and the changes for both teams
        redCardButton = findViewById(R.id.imageView_redCard);
        yellowCardButton = findViewById(R.id.imageView_yellowCard);
        allaghLeft = findViewById(R.id.textView_AllagiA);
        allaghRight = findViewById(R.id.textView_AllagiΒ);

        //The menu on top left
        spinnerMenu = findViewById(R.id.spinner_Menu);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter
                .createFromResource(this, R.array.menu, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMenu.setAdapter(adapterSpinner);
        spinnerMenu.setOnItemSelectedListener(this);

        //Text view for game delays next to the chronometer
        textViewDelays = findViewById(R.id.textView_Delays);

        leftName = findViewById(R.id.textView_TeamA);
        rightName = findViewById(R.id.textView_TeamB);
        leftList = findViewById(R.id.listView_versionOne);
        rightList = findViewById(R.id.listView_versionTwo);
        eventList = findViewById(R.id.listView_events);
        final CountStatsActivity rootActivity = this;
        eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fm = getSupportFragmentManager();
                EditEventDialog statsDialog = new EditEventDialog();
                statsDialog.setTeams(eventAdapter.getTeamA(), eventAdapter.getTeamB());
                statsDialog.setEvent((DataLiveActions) eventAdapter.getItem(i));
                statsDialog.setRootActivity(rootActivity);
                statsDialog.show(fm, "");
                return false;
            }
        });
    }

    // ______________________________________ OnClick _____________________________________________
    private void clickButtons() {
        textViewLathos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textViewLathos.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(lathosFragment);
            }
        });

        textViewFaoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textViewFaoul.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(faoulFragment);
            }
        });

        textViewGemisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textViewGemisma.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(gemismaFragment);
            }
        });

        textView1V1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textView1V1.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(dieisdusi1V1Fragment);
            }
        });

        textViewTeliki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textViewTeliki.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(telikiFragment);
            }
        });

        textViewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreyColorAllTheActionsButtons();
                textViewGoal.setBackgroundColor(Color.parseColor("#6d7660"));
                openFragment(goalFragment);
            }
        });


        // __________ CORNER & OFFSIDE _________

        imgViewCorner_RRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCornerRight(leftTeam.getIsTeamA(), imgViewCorner_RRight);
            }
        });
        imgViewCorner_RLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCornerLeft(leftTeam.getIsTeamA(), imgViewCorner_RLeft);
            }
        });
        imgViewOffside_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseOffside(leftTeam.getIsTeamA(), imgViewOffside_Right);
            }
        });
        imgViewCorner_LRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCornerRight(rightTeam.getIsTeamA(), imgViewCorner_LRight);
            }
        });
        imgViewCorner_LLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseCornerLeft(rightTeam.getIsTeamA(), imgViewCorner_LLeft);
            }
        });
        imgViewOffside_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseOffside(rightTeam.getIsTeamA(), imgViewOffside_Left);
            }
        });

        // _____________ Status Dialog _______________

        textViewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTheStatusDialog();
            }
        });

        //Red and yellow card onClicks
        redCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeCardButtons();
                if (playerButtonAction.equals("red")) {
                    playerButtonAction = "default";
                    return;
                }
                playerButtonAction = "red";
                redCardButton.setBackgroundResource(R.drawable.red_card);
            }
        });

        yellowCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeCardButtons();
                if (playerButtonAction.equals("yellow")) {
                    playerButtonAction = "default";
                    return;
                }
                playerButtonAction = "yellow";
                yellowCardButton.setBackgroundResource(R.drawable.yellow_card);
            }
        });

        allaghLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayerChange(leftTeam);
            }
        });

        allaghRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayerChange(rightTeam);
            }
        });

        leftName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openConfigureDialog(leftTeam, isDefault ? matchDTO.getTeamA() : matchDTO.getTeamB());
                return false;
            }
        });

        rightName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openConfigureDialog(rightTeam, isDefault ? matchDTO.getTeamB() : matchDTO.getTeamA());
                return false;
            }
        });

        findViewById(R.id.button_removeEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLiveActions deleted = eventAdapter.deleteEvent();
                if (deleted != null)
                    deletedEvent(deleted);
            }
        });

        findViewById(R.id.button_flagEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventAdapter.toggleFlag();
            }
        });
    }

    // ______________________________ CHRONOMETER / LISTS _________________________________________

    // Fix the chronometer format
    private void createChronometerFormat() {
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int m = (int) time / 60000;
                int s = (int) (time - m * 60000) / 1000;
                String t = (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
                chronometer.setText(t);
            }
        });
    }

    // Get the two 3 lists ready. Two for player, one for events
    private void makeLists() {
        leftName.setText(leftTeam.getName());
        rightName.setText(rightTeam.getName());

        allaghLeft.setBackgroundColor(leftTeam.getColour());
        allaghRight.setBackgroundColor(rightTeam.getColour());

        leftAdapter = new BasicPlayerAdapter(this, leftTeam, true, pixels);
        leftList.setAdapter(leftAdapter);
        rightAdapter = new BasicPlayerAdapter(this, rightTeam, false, pixels);
        rightList.setAdapter(rightAdapter);

        eventAdapter = new EventAdapter(this, leftTeam, rightTeam);
        eventList.setAdapter(eventAdapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                eventAdapter.setSelected(position);
            }
        });
        if (!isFriendly) eventAdapter.parseEvents(matchDTO.getActionsLive());
    }


    // _________________________________ OVERRIDE METHODS _________________________________________
    @SuppressLint("SetTextI18n")
    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            //do Stuff with internet
            connectionStatus.setBackgroundResource(R.color.green);
            connectionStatus.setText("ONLINE");
        } else {
            //no internet
            connectionStatus.setBackgroundResource(R.color.red);
            connectionStatus.setText("OFFLINE");
        }
    }

    // Menu
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        switch (text) {
            case "Switch Sides":
                swapTeams();
                break;
            case "Events":
                openEventDialog();
                break;
            case "Stats":
                openStatsDialog();
                break;
            case "Create Pdf":
                checkForThePermission();
                break;
            case "Telephones":
                openTelephonesDialog();
                break;
        }
        spinnerMenu.setSelection(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    // ___________________________________ OPEN STUFF _____________________________________________

    private void openTelephonesDialog() {
        TelephonesDialog telDialog = new TelephonesDialog();
        telDialog.show(getSupportFragmentManager(), "Telephone Dialog");
    }

    private void openConfigureDialog(Team team, TeamDTO teamDto) {
        FragmentManager fm = getSupportFragmentManager();
        ConfTeamDialog statsDialog = new ConfTeamDialog();
        statsDialog.setTeam(team);
        statsDialog.setTeamDTO(teamDto);
        statsDialog.show(fm, "");
    }

    private void openStatsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        StatsDialog statsDialog = new StatsDialog();
        statsDialog.setLive(matchDTO.getLive());
        statsDialog.setTeams(leftTeam.getName(), rightTeam.getName());
        statsDialog.setLeftIsA(isDefault);
        statsDialog.show(fm, "");
    }

    private void openPlayerChange(Team team) {
        playerButtonAction = "changePlayer";
        fadeCardButtons();
        changePlayer = new ChangePlayerFragment();
        changePlayer.setTeam(team);
        openFragment(changePlayer);
        if (team.equals(leftAdapter.getTeam())) rightList.setVisibility(View.INVISIBLE);
        else leftList.setVisibility(View.INVISIBLE);
    }

    // _________________________________ CORNER / OFFSIDE _________________________________________
    private void increaseOffside(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("offside", isTeamA, true);
            addTeamEvent("ΟΦΣΑΙΝΤ", "offside", isTeamA);
        }
    }

    private void increaseCornerLeft(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("cornerLeft", isTeamA, true);
            addTeamEvent("ΚΟΡΝΕΡ ΑΡΙΣΤΕΡΗ ΠΛΕΥΡΑ", "cornerLeft", isTeamA);
        }
    }

    private void increaseCornerRight(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("cornerRight", isTeamA, true);
            addTeamEvent("ΚΟΡΝΕΡ ΔΕΞΙΑ ΠΛΕΥΡΑ", "cornerRight", isTeamA);
        }
    }

    private boolean doubleClicked() {
        counterOfClicks++;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                counterOfClicks = 0;
            }
        };
        if (counterOfClicks == 1) {
            Toast.makeText(getBaseContext(), "Double Click", Toast.LENGTH_SHORT).show();
            handler.postDelayed(run, 300);
            return false;
        } else return counterOfClicks == 2;
    }
    // ___________________________________ HELPFUL FOR ACTIONS ____________________________________

    public void startScaleAnimationForTextViews(TextView txtvButtons) {
        scalingAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale);
        txtvButtons.startAnimation(scalingAnimation);
    }

    private void startScaleAnimation(ImageView img) {
        scalingAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale);
        img.startAnimation(scalingAnimation);
    }

    public void hideOtherTeam() {
        if ((isTeamAForAction && isDefault) || (!isTeamAForAction && !isDefault))
            rightList.setVisibility(View.INVISIBLE);
        else leftList.setVisibility(View.INVISIBLE);
    }

    public void hideThisTeam() {
        if ((isTeamAForAction && isDefault) || (!isTeamAForAction && !isDefault))
            leftList.setVisibility(View.INVISIBLE);
        else rightList.setVisibility(View.INVISIBLE);
    }

    public boolean isDefaultValues() {
        return numberOfShirt.equals("0");
    }

    public void setGreyColorAllTheActionsButtons() {
        textViewTeliki.setBackgroundColor(Color.parseColor("#c2c2c2"));
        textViewGemisma.setBackgroundColor(Color.parseColor("#c2c2c2"));
        textView1V1.setBackgroundColor(Color.parseColor("#c2c2c2"));
        textViewGoal.setBackgroundColor(Color.parseColor("#6A6A6A"));
        textViewFaoul.setBackgroundColor(Color.parseColor("#c2c2c2"));
        textViewLathos.setBackgroundColor(Color.parseColor("#c2c2c2"));
    }

    public void returnToFieldFrag() {
        openFragment(fieldFragment);
        leftList.setVisibility(View.VISIBLE);
        rightList.setVisibility(View.VISIBLE);
        restoreDefaultValues();
        setGreyColorAllTheActionsButtons();
    }

    public void restoreDefaultValues() {
        this.numberOfShirt = "0";
        this.amForAction = "0";
        this.location = " ";
        this.playerButtonAction = "default";
        notifyAdapters();
    }

    public void makePlayerChange(Team team, Player playerIn, Player playerOut) {
        playerIn.setSelection(true);
        playerOut.setSelection(null);

        team.addRemoved(playerOut);
        team.addEntered(playerIn);

        playerChanged(team);
    }


    // 2 CALLS OF THIS METHOD :
    // 1 for making the change
    // 1 for deleting the change
    public void playerChanged(Team team) {
        if (isFriendly) return;

        TeamDTO targetTeam;
        if (matchDTO.getTeamA().getName().equals(team.getName()))
            targetTeam = matchDTO.getTeamA();
        else
            targetTeam = matchDTO.getTeamB();

        PlayerDTO cPlayerDto;
        ArrayList<PlayerDTO> currentPlayersListDTO = new ArrayList<>();
        for (Player p : team.getAllPlayers()) {
            cPlayerDto = new PlayerDTO(p);
            currentPlayersListDTO.add(cPlayerDto);
        }
        targetTeam.setPlayers(currentPlayersListDTO);
        notifyAdapters();

        // -----------------------------
        // We need to edit some players in order to NOT
        // send some basic players as UNSET
        // NOT THE BEST WAY

        // BASIC
        // Αρχικοί βασικοί: basic + removed - entered
        ArrayList<Player> firstBasic = new ArrayList<>(team.getBasicPlayers());
        firstBasic.addAll(team.getRemoved());
        firstBasic.removeAll(team.getEntered());
        ArrayList<PlayerDTO> firstBasicDTO = new ArrayList<>();
        for (Player p : firstBasic) {
            cPlayerDto = new PlayerDTO(p, "basic");
            firstBasicDTO.add(cPlayerDto);
        }

        // REPLACED
        // Αρχικοί αναπληρωματικοί: replacement + entered
        ArrayList<Player> firstReplaced = new ArrayList<>(team.getReplacementPlayers());
        firstReplaced.addAll(team.getEntered());
        ArrayList<PlayerDTO> firstReplacedDTO = new ArrayList<>();
        for (Player p : firstReplaced) {
            cPlayerDto = new PlayerDTO(p, "replacement");
            firstReplacedDTO.add(cPlayerDto);
        }

        // UNSET : Replaced - Entered
        ArrayList<Player> unsetPlayers = new ArrayList<>(team.getReplacementPlayers());
        unsetPlayers.removeAll(team.getEntered());
        ArrayList<PlayerDTO> unsetPlayersDTO = new ArrayList<>();
        for (Player p : unsetPlayers) {
            cPlayerDto = new PlayerDTO(p, "unset");
            unsetPlayersDTO.add(cPlayerDto);
        }

        // All Players = First Basic + First Replaced + Unset PLayers
        firstBasicDTO.addAll(firstReplacedDTO);
        firstBasicDTO.addAll(unsetPlayersDTO);

        targetTeam.setPlayers(firstBasicDTO);
        //              CAUTION
        ////  -----------------------------

        sendWholeDTO();
    }

    private void createTheStatusDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CountStatsActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_status, null);

        Button buttonOk = mView.findViewById(R.id.button_OkStatus);
        Button buttonOkDelays = mView.findViewById(R.id.button_OkDelays);
        Button buttonStartFrom = mView.findViewById(R.id.button_StartChrFrom);
        final RadioButton rdBtn_PreGame = mView.findViewById(R.id.radioButton_PreGame);
        final RadioButton rdBtn_FirstHalf = mView.findViewById(R.id.radioButton_FirstHalf);
        final RadioButton rdBtn_HalfTime = mView.findViewById(R.id.radioButton_HalfTime);
        final RadioButton rdBtn_SecondHalf = mView.findViewById(R.id.radioButton_SecondHalf);
        final RadioButton rdBtn_FinalGame = mView.findViewById(R.id.radioButton_Final);
        final RadioButton rdBtn_ExtraTime = mView.findViewById(R.id.radioButton_ExtraTime);
        final RadioButton rdBtn_ExtraTimeSecond = mView.findViewById(R.id.radioButton_ExtraTimeSecond);
        final RadioButton rdBtn_Penalty = mView.findViewById(R.id.radioButton_Penalty);
        final RadioButton rdBtn_NormalEnd = mView.findViewById(R.id.radioButton_NormalEnd);
        final EditText editTextMinutes = mView.findViewById(R.id.editText_Min);
        final EditText editTextDelays = mView.findViewById(R.id.editText_Delays);
        switch (gameStatus) {
            case "First Half":
                rdBtn_FirstHalf.setChecked(true);
                break;
            case "Half Time":
                rdBtn_HalfTime.setChecked(true);
                break;
            case "Second Half":
                rdBtn_SecondHalf.setChecked(true);
                break;
            case "Normal Time Finish":
                rdBtn_NormalEnd.setChecked(true);
                break;
            case "Extra First Half":
                rdBtn_ExtraTime.setChecked(true);
                break;
            case "Extra Second Half":
                rdBtn_ExtraTimeSecond.setChecked(true);
                break;
            case "Penalty":
                rdBtn_Penalty.setChecked(true);
                break;
            case "Final":
                rdBtn_FinalGame.setChecked(true);
                break;
            case "Pre Game":
                rdBtn_PreGame.setChecked(true);
            default:
                rdBtn_HalfTime.setEnabled(false);
                rdBtn_SecondHalf.setEnabled(false);
                rdBtn_NormalEnd.setEnabled(false);
                rdBtn_ExtraTime.setEnabled(false);
                rdBtn_ExtraTimeSecond.setEnabled(false);
                rdBtn_Penalty.setEnabled(false);
                rdBtn_FinalGame.setEnabled(false);
        }
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (rdBtn_PreGame.isChecked()) {
                    pauseChronometer();
                    textViewStatus.setText("Pre Game");
                    gameStatus = "Pre Game";
                    chronometer.setText("00:00");
                    setUnclickableAllButtons();
                    //descriptionOfMatch = editTextDescription.getText().toString();
                    dialog.dismiss();
                } else if (rdBtn_FirstHalf.isChecked()) {
                    if (!running) {
                        startAChronometer();
                        textViewStatus.setText("First Half");
                        gameStatus = "First Half";
                        dialog.dismiss();
                        setClickableAllButtons();
                        textViewDelays.setText("");
                    }
                } else if (rdBtn_HalfTime.isChecked()) {
                    pauseChronometer();
                    textViewStatus.setText("Half Time");
                    gameStatus = "Half Time";
                    if (!isParatasiSelected)
                        chronometer.setText("45:00");
                    else
                        chronometer.setText("105:00");
                    setUnclickableAllButtons();
                    textViewDelays.setText("");
                    dialog.dismiss();
                } else if (rdBtn_SecondHalf.isChecked()) {
                    if (!running) {
                        startBChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Second Half");
                        gameStatus = "Second Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }
                } else if (rdBtn_FinalGame.isChecked()) {

                    openAreYouSureDialog();
                } else if (rdBtn_NormalEnd.isChecked()) {
                    pauseChronometer();
                    chronometer.setText("90:00");
                    setUnclickableAllButtons();
                    textViewStatus.setText("Normal Time Finish");
                    gameStatus = "Normal Time Finish";
                    textViewDelays.setText("");
                    dialog.dismiss();
                } else if (rdBtn_ExtraTime.isChecked()) {
                    if (!running) {
                        isParatasiSelected = true;
                        startAChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Extra First Half");
                        gameStatus = "Extra First Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }

                } else if (rdBtn_ExtraTimeSecond.isChecked()) {
                    if (!running) {
                        isParatasiSelected = true;
                        startBChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Extra Second Half");
                        gameStatus = "Extra Second Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }

                } else if (rdBtn_Penalty.isChecked()) {
                    isParatasiSelected = false;
                    pauseChronometer();
                    textViewStatus.setText("Penalty");
                    gameStatus = "Penalty";
                    dialog.dismiss();
                    openFragment(penaltyFragment);
                }


                // _______________  POST REQUEST HERE  ___________________
                if (!isFriendly) {
                    matchDTO.setStatus(gameStatus);
                    updateStat("status", gameStatus);
                }
            }
        });

        buttonOkDelays.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String delaysString = editTextDelays.getText().toString().trim();
                if (!(delaysString.isEmpty())) {
                    int del = Integer.parseInt(delaysString); // only for the check
                    if (del >= 1) {
                        textViewDelays.setText(" +" + delaysString + " ");
                        addRawEvent("ΚΑΘΥΣΤΕΡΗΣΕΙΣ " + del + " ΛΕΠΤΑ");
                        dialog.dismiss();
                    }
                }

                if (!isFriendly) {
                    switch (gameStatus) {
                        case "First Half":
                            matchDTO.setDelaysA(Integer.parseInt(delaysString));
                            updateStat("delaysA", matchDTO.getDelaysA().toString());
                            break;
                        case "Second Half":
                            matchDTO.setDelaysB(Integer.parseInt(delaysString));
                            updateStat("delaysB", matchDTO.getDelaysB().toString());
                            break;
                        case "Extra First Half":
                            matchDTO.setDelaysExtraA(Integer.parseInt(delaysString));
                            updateStat("delaysExtraA", matchDTO.getDelaysExtraA().toString());
                            break;
                        case "Extra Second Half":
                            matchDTO.setDelaysExtraB(Integer.parseInt(delaysString));
                            updateStat("delaysExtraB", matchDTO.getDelaysExtraB().toString());
                            break;
                    }

                }
            }
        });

        buttonStartFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NOT Null
                String min = editTextMinutes.getText().toString();
                if (!(min.matches(""))) {
                    int minutes = Integer.parseInt(min);
                    startChronometer(minutes);
                    dialog.dismiss();
                } else {
                    Toast.makeText(getBaseContext(), "Enter Minutes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void swapTeams() {
        isDefault = !isDefault;

        leftName.setText(rightTeam.getName());
        rightName.setText(leftTeam.getName());

        allaghLeft.setBackgroundColor(rightTeam.getColour());
        allaghRight.setBackgroundColor(leftTeam.getColour());

        leftAdapter.setTeam(rightTeam);
        rightAdapter.setTeam(leftTeam);

        String leftGoals = textViewGoalLeft.getText().toString();
        String rightGoals = textViewGoalRight.getText().toString();
        textViewGoalLeft.setText(rightGoals);
        textViewGoalRight.setText(leftGoals);

        Team temporaryTeam = leftTeam;
        leftTeam = rightTeam;
        rightTeam = temporaryTeam;
    }

    private void openFragment(Fragment fragToOpen) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragToOpen);
        fragmentTransaction.commit();
    }

    public void startAChronometer() {
        if (!running) {
            chronometer.setBase(isParatasiSelected ? SystemClock.elapsedRealtime() - (90 * 60000) :
                    SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }
    }

    public void startBChronometer() {
        if (!running) {
            chronometer.setBase(isParatasiSelected ? SystemClock.elapsedRealtime() - (105 * 60000) :
                    SystemClock.elapsedRealtime() - (45 * 60000));
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            running = false;
        }
    }

    public void startChronometer(int min) {
        chronometer.setBase(SystemClock.elapsedRealtime() - (min * 60000));
        chronometer.start();
        running = true;
    }


    private void openAreYouSureDialog() {
        AreYouSureDialog d = new AreYouSureDialog();
        d.show(getSupportFragmentManager(), "dialog");
    }

    private void setUnclickableAllButtons() {
        buttonsUnclickable = true;
        imgViewCorner_LLeft.setClickable(false);
        imgViewCorner_RLeft.setClickable(false);
        imgViewCorner_LRight.setClickable(false);
        imgViewCorner_RRight.setClickable(false);
        imgViewOffside_Left.setClickable(false);
        imgViewOffside_Right.setClickable(false);
        textViewTeliki.setClickable(false);
        textViewGemisma.setClickable(false);
        textView1V1.setClickable(false);
        textViewFaoul.setClickable(false);
        textViewLathos.setClickable(false);
        textViewGoal.setClickable(false);
        redCardButton.setClickable(false);
        yellowCardButton.setClickable(false);
        allaghLeft.setClickable(false);
        allaghRight.setClickable(false);
    }

    private void setClickableAllButtons() {
        buttonsUnclickable = false;
        imgViewCorner_LLeft.setClickable(true);
        imgViewCorner_RLeft.setClickable(true);
        imgViewCorner_LRight.setClickable(true);
        imgViewCorner_RRight.setClickable(true);
        imgViewOffside_Left.setClickable(true);
        imgViewOffside_Right.setClickable(true);
        textViewTeliki.setClickable(true);
        textViewGemisma.setClickable(true);
        textView1V1.setClickable(true);
        textViewFaoul.setClickable(true);
        textViewLathos.setClickable(true);
        textViewGoal.setClickable(true);
        redCardButton.setClickable(true);
        yellowCardButton.setClickable(true);
        allaghLeft.setClickable(true);
        allaghRight.setClickable(true);
    }

    public void configureEnd() {

        allaghLeft.setBackgroundColor(leftTeam.getColour());
        allaghRight.setBackgroundColor(rightTeam.getColour());
        notifyAdapters();

        sendWholeDTO();
    }

    public void deletedEvent(DataLiveActions deleted) {
        if (!deleted.getStat().equals(""))
            changeStat(deleted.getStat(), deleted.isTeamA(), false);
        Team team = deleted.isTeamA() ? eventAdapter.getTeamA() : eventAdapter.getTeamB();
        switch (deleted.getStat()) {
            case "redCard":
                team.getPlayer(deleted.getPlayer()).setHasRedCard(false);
                break;
            case "yellowCard":
                Player player = team.getPlayer(deleted.getPlayer());
                player.setYellowCards(player.getYellowCards() - 1);
                if (deleted.getEvent().contains("ΚΟΚΚΙΝΗ")) {
                    team.getPlayer(deleted.getPlayer()).setHasRedCard(false);
                    changeStat("redCard", team.getIsTeamA(), false);
                }
                break;
        }
        if (deleted.getEvent().contains("ΓΚΟΛ")) {
            if (deleted.getEvent().contains("ΑΥΤΟΓΚΟΛ"))
                changeStat("goal", !team.getIsTeamA(), false);
            else
                changeStat("goal", team.getIsTeamA(), false);
        } else if (deleted.getEvent().contains("ΔΟΚΑΡΙ"))
            changeStat("dokari", team.getIsTeamA(), false);
        else if (deleted.getEvent().contains("ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ"))
            changeStat("xameniEukairia", team.getIsTeamA(), false);
        else if (deleted.getEvent().contains("ΑΛΛΑΓΗ")) {
            Pattern pattern = Pattern.compile("(?:.*)\\D+(\\d+)\\D+(\\d+)(?:.*)", Pattern.UNICODE_CASE);
            Matcher matcher = pattern.matcher(deleted.getEvent());
            if (matcher.matches()) {
                team.getPlayer(matcher.group(2)).setSelection(false);
                team.getPlayer(matcher.group(1)).setSelection(true);
                team.getRemoved().remove(team.getPlayer(matcher.group(1)));
                playerChanged(team);
            }
        }
        notifyAdapters();
    }

    // _______________________ For Dialog after Final ______________________
    @SuppressLint("SetTextI18n")
    @Override
    public void changeTheStatusToFinal() {
        textViewStatus.setText("Final");
        gameStatus = "Final";
        setUnclickableAllButtons();
        pauseChronometer();
        if (!isFriendly) {
            matchDTO.setStatus(gameStatus);
            updateStat("status", gameStatus);
        }
        dialog.dismiss();
    }


    public void addEvent(String text, String stat) {
        eventAdapter.addEvent(new DataLiveActions(getMinute(), text, stat,
                numberOfShirt, isTeamAForAction, location, getTimePeriod(), isDefault));
        matchDTO.setActionsLive(eventAdapter.getActionsForDB());
        updateActionList();
    }

    public void addEvent(String text, String stat, String player, boolean isTeamA) {
        eventAdapter.addEvent(new DataLiveActions(getMinute(), text, stat,
                player, isTeamA, location, getTimePeriod(), isDefault));
        matchDTO.setActionsLive(eventAdapter.getActionsForDB());
        updateActionList();
    }

    public void addTeamEvent(String text, String stat, boolean isTeamA) {
        eventAdapter.addEvent(new DataLiveActions(getMinute(), text, stat, isTeamA,
                getTimePeriod(), isDefault));
        matchDTO.setActionsLive(eventAdapter.getActionsForDB());
        updateActionList();
    }

    public void addRawEvent(String text) {
        eventAdapter.addEvent(new DataLiveActions(getMinute(), text, getTimePeriod(), isDefault));
        matchDTO.setActionsLive(eventAdapter.getActionsForDB());
        updateActionList();
    }

    public String getTimePeriod() {
        switch (gameStatus) {
            case "Pre Game":
                return "ΠΡΙΝ ΕΝΑΡ";
            case "First Half":
                return "Α ΗΜΙΧ";
            case "Half Time":
                return "ΗΜΙΧ";
            case "Second Half":
                return "Β ΗΜΙΧ";
            case "Normal Time Finish":
                return "ΤΕΛΟΣ ΚΑΝΟΝ";
            case "Extra First Half":
                return "ΠΑΡΑΤ A ΗΜΙΧ";
            case "Extra Second Half":
                return "ΠΑΡΑΤ Β ΗΜΙΧ";
            case "Penalty":
                return "ΠΕΝΑΛΤΙ";
            default:
                return "ΜΗ ΕΠΙΛΕΓ";
        }
    }

    public int getMinute() {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        return (int) time / 60000 + 1;
    }

    // ______________________ Frequently Used Methods ______________________________

    public void notifyAdapters() {
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
        eventAdapter.notifyDataSetChanged();
    }

    public void showToast(String text) {
        Toast.makeText(CountStatsActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void changeStat(String stat, boolean isTeamA, boolean increase) {
        String value = statUpdater.increaseOrDecreaseStat(stat, isTeamA, increase);
        if (stat.equals("goal"))
            checkGoals(stat, isTeamA, increase);
        updateLive(stat, value);
    }

    public void changeStat(String stat, boolean increase) {
        String value = statUpdater.increaseOrDecreaseStat(stat, isTeamAForAction, increase);
        if (stat.equals("goal"))
            checkGoals(stat, isTeamAForAction, increase);
        updateLive(stat, value);
    }

    public void fadeCardButtons() {
        yellowCardButton.setBackgroundResource(R.drawable.faded_yellow_card);
        redCardButton.setBackgroundResource(R.drawable.faded_red_card);
    }

    private void checkGoals(String stat, boolean isTeamA, boolean increase) {
        String[] goals = matchDTO.getLive().getGoal().split("#");
        updateLive(stat, matchDTO.getLive().getGoal());
        textViewGoalLeft.setText(goals[isDefault ? 0 : 1]);
        textViewGoalRight.setText(goals[isDefault ? 1 : 0]);
        if (isParatasiSelected)
            changeStat("extraTimeScore", isTeamA, increase);
    }


    // _______________________________________ PDF ________________________________________________
    //For saving the PDF, check if we already have the required permission
    private void checkForThePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {  // Android 11+
            // Check if MANAGE_EXTERNAL_STORAGE permission is granted
            if (Environment.isExternalStorageManager()) {
                createPdf();  // Permission already granted, proceed to create PDF
            } else {
                requestManageExternalStoragePermission();  // Request permission
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {  // Android 10
            // No need for storage permissions in Android 10 (Scoped Storage is used)
            createPdf();
        } else {
            // For Android 9 and below: check WRITE_EXTERNAL_STORAGE permission
            if (ContextCompat.checkSelfPermission(CountStatsActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                createPdf();  // Permission granted, proceed to create PDF
            } else {
                requestStoragePermissions();  // Request WRITE_EXTERNAL_STORAGE permission
            }
        }
    }

    // Request MANAGE_EXTERNAL_STORAGE permission for Android 11+ (API level 30+)
    private void requestManageExternalStoragePermission() {
        new AlertDialog.Builder(this)
                .setTitle("Storage Permission Needed")
                .setMessage("We need full access to storage for saving PDFs. Please allow this permission.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivityForResult(intent, MANAGE_STORAGE_PERMISSION_CODE);
                        } catch (Exception e) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivityForResult(intent, MANAGE_STORAGE_PERMISSION_CODE);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(CountStatsActivity.this, "Storage permission denied", Toast.LENGTH_SHORT).show();
                    }
                })
                .create()
                .show();
    }

    // Request WRITE_EXTERNAL_STORAGE permission for Android 9 and below
    private void requestStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Storage Permission Needed")
                    .setMessage("We need storage permission for saving any PDFs that will be created")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CountStatsActivity.this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);
        }
    }

    // Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with creating the PDF
                createPdf();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Handle MANAGE_EXTERNAL_STORAGE result for Android 11+
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MANAGE_STORAGE_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // Permission granted, proceed with creating the PDF
                    createPdf();
                } else {
                    // Permission denied, show a message
                    Toast.makeText(this, "Full Storage Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    //Method for creating the pdf. Friendly games get no pdf.
    private void createPdf() {
        if (isFriendly) return;

        String scoreStr = Objects.toString(matchDTO.getLive().getGoal(), "");
        String startTimeStr = Objects.toString(matchDTO.getTime(), "");
        String dateStr = Objects.toString(matchDTO.getDate(), "");
        String fieldStr = Objects.toString(matchDTO.getField(), "");
        String nameTeamA = Objects.toString(matchDTO.getTeamA().getName(), "");
        String nameTeamB = Objects.toString(matchDTO.getTeamB().getName(), "");
        String nameOrganizationStr = Objects.toString(matchDTO.getOrganization(), "");

        String numberRacingStr = Objects.toString(matchDTO.getMatchDay().toString(), "");
        String coachA = isDefault ? Objects.toString(leftTeam.getCoachFullName(), "") :
                Objects.toString(rightTeam.getCoachFullName(), "");
        String coachB = isDefault ? Objects.toString(rightTeam.getCoachFullName(), "") :
                Objects.toString(leftTeam.getCoachFullName(), "");

        String pCenterTimeStr = "0#0";
        String pTotalTimeStr = "0#0";
        String pRightTimeStr = "0#0";
        String pLeftTimeStr = "0#0";
        TemplatePdf tempPdf = new TemplatePdf(this, eventAdapter.getEventsForPDF(),
                leftAdapter, rightAdapter, numberRacingStr,
                startTimeStr, dateStr, fieldStr, nameTeamA, nameTeamB,
                nameOrganizationStr, scoreStr, coachA, coachB,
                pTotalTimeStr, pLeftTimeStr, pCenterTimeStr,
                pRightTimeStr);

        setTemplatePdf(tempPdf);

        try {
            getTemplatePdf().openDocument();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getTemplatePdf().closeDocument();

        Toast.makeText(getBaseContext(), "Pdf created in Downloads/NOT_DELETE/Matches",
                Toast.LENGTH_SHORT).show();
    }


    // ________________________________ RETROFIT REQUESTS _________________________________________
    private void sendWholeDTO() {
        if (isFriendly) return;
        Call<Void> call = requests.postMatch(matchDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("---------> ", t.getMessage());
            }
        });
    }

    private void updateLive(String fieldName, String value) {
        if (isFriendly) return;
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        HashMap<String, String> live = new HashMap<>();
        live.put(fieldName, value);
        fields.put("LIVE", live);
        Call<Void> call = requests.postSomeFields(fields);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("---------> ", t.getMessage());
            }
        });
    }

    private void updateStat(String fieldName, String value) {
        if (isFriendly) return;
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        fields.put(fieldName, value);
        Call<Void> call = requests.postSomeFields(fields);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("---------> ", t.getMessage());
            }
        });
    }

    private void updateActionList() {
        if (isFriendly) return;
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        fields.put("ActionsLive", matchDTO.getActionsLive());
        Call<Void> call = requests.postSomeFields(fields);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Log.e("---------> ", t.getMessage());
            }
        });
    }

    // ____________________________________ ACCESSORS _____________________________________________
    public TemplatePdf getTemplatePdf() {
        return templatePdf;
    }

    public String getPlayerButtonAction() {
        return playerButtonAction;
    }

    public String getAmForAction() {
        return amForAction;
    }

    public String getNumberOfShirt() {
        return numberOfShirt;
    }

    public boolean getIsTeamAForAction() {
        return isTeamAForAction;
    }

    public String getLocation() {
        return location;
    }

    public ChangePlayerFragment getChangePlayer() {
        return changePlayer;
    }

    public boolean getButtonsUnclickable() {
        return buttonsUnclickable;
    }

    // ______________________________________ MUTATORS ____________________________________________
    public void setLocation(String location) {
        this.location = location;
    }

    public void setTemplatePdf(TemplatePdf templatePdf) {
        this.templatePdf = templatePdf;
    }

    public void setPlayerButtonFields(String numberOfShirt, boolean isTeamA, String amForAction) {
        this.numberOfShirt = numberOfShirt;
        this.isTeamAForAction = isTeamA;
        this.amForAction = amForAction;
    }

    public EventAdapter getEventAdapter() {
        return eventAdapter;
    }

    private void openEventDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EventDisplayDialog eventDialog = new EventDisplayDialog();
        eventDialog.setRootActivity(this);
        eventDialog.show(fm, "");
    }

    public void sortEvents() {
        Collections.sort(eventAdapter.getEvents(), new Comparator<DataLiveActions>() {
            @Override
            public int compare(DataLiveActions dataLiveActions, DataLiveActions t1) {
                return dataLiveActions.getMinute() - t1.getMinute();
            }
        });
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
