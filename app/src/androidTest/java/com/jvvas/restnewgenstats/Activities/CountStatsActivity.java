package com.jvvas.restnewgenstats.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import com.google.gson.Gson;
import com.jvvas.restnewgenstats.Api.RequestsOfCountStatsActivity;
import com.jvvas.restnewgenstats.Api.RequestsOfRosterReview;
import com.jvvas.restnewgenstats.Dialog.AreYouSureDialog;
import com.jvvas.restnewgenstats.Dialog.ConfTeamDialog;
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
import com.jvvas.restnewgenstats.Objects.Live;
import com.jvvas.restnewgenstats.Objects.LiveUpdater;
import com.jvvas.restnewgenstats.Objects.Player;
import com.jvvas.restnewgenstats.Objects.Team;
import com.jvvas.restnewgenstats.Objects.TemplatePdf;
import com.jvvas.restnewgenstats.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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


    private int WRITE_STORAGE_PERMISSION_CODE = 101;
    // ______________ Lib's _____________
    private DroidNet mDroidNet;
    private Retrofit retrofit;
    private RequestsOfCountStatsActivity requests;
    private int pixels;

    // ___________ Fragments ___________
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
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
    private TextView textViewDelays ,connectionStatus, textViewStatus, allaghRight, allaghLeft,
            leftName, rightName;

    ListView leftList, rightList, eventList;
    private Chronometer chronometer;
    private Spinner spinnerMenu;

    // __________ Objects ___________
    private ArrayList<DataLiveActions> liveActionsList = new ArrayList<DataLiveActions>();
    private EventAdapter eventAdapter;
    private BasicPlayerAdapter leftAdapter;
    private BasicPlayerAdapter rightAdapter;
    private Team leftTeam, rightTeam;
    private LiveUpdater statUpdater;
    private String location = " ", gameStatus = "Default", playerButtonAction = "default",
            numberOfShirt = "0", amForAction = "0";
    private boolean isFriendly, isParatasiSelected = false,  isTeamAForAction = true,
            buttonsUnclickable = true, running = false;
    public static boolean isDefault = true;
    // _____________ DTOs __________________
    private MatchDTO matchDTO;
    private TeamDTO teamDtoA;

    Animation scalingAnimation;
    AlertDialog dialog;
    private int counterOfClicks = 0;
    private Handler handler = new Handler();
//    private long pauseOffset;
    protected TemplatePdf templatePdf;
    private String scoreStr, pCenterTimeStr = "0#0", pLeftTimeStr = "0#0",
            pRightTimeStr = "0#0", pTotalTimeStr = "0#0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_stats);

        setupRetrofit();
        takeIntentInfo();
        addFragmentField();
        droidNetAndMetrics();
        initializeTheButtons();
        makeLists();

//        checkIfLiveObjectExists(); to take all the events back

        createChronometerFormat();
        clickButtons();


    }

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

        //Red and yellow card onClicks. Firstly check if the card clicked is already "selected"
        //If it is, means we want to "stop" the addCard action... so make the action default and
        //The card faded. If it isn't, make the other card fades(in case that was selected) and this
        //one gets "bolder". Also change the action text.
        redCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerButtonAction.equals("red")) {
                    playerButtonAction = "default";
                    redCardButton.setBackgroundResource(R.drawable.faded_red_card);
                    return;
                }
                playerButtonAction = "red";
                redCardButton.setBackgroundResource(R.drawable.red_card);
                yellowCardButton.setBackgroundResource(R.drawable.faded_yellow_card);
            }
        });

        yellowCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerButtonAction.equals("yellow")) {
                    playerButtonAction = "default";
                    yellowCardButton.setBackgroundResource(R.drawable.faded_yellow_card);
                    return;
                }
                playerButtonAction = "yellow";
                yellowCardButton.setBackgroundResource(R.drawable.yellow_card);
                redCardButton.setBackgroundResource(R.drawable.faded_red_card);
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
                openConfigureDialog(leftTeam);
                return false;
            }
        });

        rightName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openConfigureDialog(rightTeam);
                return false;
            }
        });

//        findViewById(R.id.imageView_editEvent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editAction(eventAdapter.getSelectedText());
//            }
//        });

        findViewById(R.id.button_removeEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventAdapter.deleteSelected();
            }
        });

        findViewById(R.id.button_flagEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventAdapter.flagSelected();
            }
        });
    }

    private void swapTeams()
    {
        isDefault = !isDefault;

        leftTeam = leftAdapter.getTeam();
        rightTeam = rightAdapter.getTeam();

        leftName.setText(rightTeam.getName());
        rightName.setText(leftTeam.getName());

        allaghLeft.setBackgroundColor(rightTeam.getColour());
        allaghRight.setBackgroundColor(leftTeam.getColour());

        leftAdapter.changeTeam(rightTeam);
        rightAdapter.changeTeam(leftTeam);


        String leftGoals = textViewGoalLeft.getText().toString();
        String rightGoals = textViewGoalRight.getText().toString();
        textViewGoalLeft.setText(rightGoals);
        textViewGoalRight.setText(leftGoals);

        Team temporaryTeam = leftTeam;
        leftTeam = rightTeam;
        rightTeam = temporaryTeam;
    }



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

    private void makeLists() {
        leftName.setText(leftTeam.getName());
        rightName.setText(rightTeam.getName());

        allaghLeft.setBackgroundColor(leftTeam.getColour());
        allaghRight.setBackgroundColor(rightTeam.getColour());

        leftTeam.sortByNumber();
        rightTeam.sortByNumber();

        leftAdapter = new BasicPlayerAdapter(this, true, true, leftTeam);
        leftList.setAdapter(leftAdapter);
        rightAdapter = new BasicPlayerAdapter(this, false, false, rightTeam);
        rightList.setAdapter(rightAdapter);

        eventAdapter = new EventAdapter();
        eventList.setAdapter(eventAdapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                eventAdapter.setSelected(position);
            }
        });

    }

    private void addFragmentField() {
        openFragment(fieldFragment);
    }

    private void openFragment(Fragment fragToOpen) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragToOpen);
        fragmentTransaction.commit();
    }

    public void takeIntentInfo() {
        isFriendly = getIntent().getBooleanExtra("isFriendly", false);
        leftTeam = (Team) getIntent().getSerializableExtra("teamA");
        rightTeam = (Team) getIntent().getSerializableExtra("teamB");
        if (!isFriendly) {
            matchDTO = (MatchDTO) getIntent().getSerializableExtra("matchDTO");
            teamDtoA = matchDTO.getTeamA();
            //These are supposed to set things to 0 for new games
            resetStats();
            sendWholeDTO();
            statUpdater = new LiveUpdater(matchDTO.getLive());
        }else
            statUpdater = new LiveUpdater(new LiveDTO());
    }

    private void resetStats(){
        matchDTO.setStatus("Pre Game");
        matchDTO.setDelaysA(0);
        matchDTO.setDelaysB(0);
        matchDTO.setDelaysExtraA(0);
        matchDTO.setDelaysExtraB(0);
        matchDTO.setLive(new LiveDTO());
        matchDTO.setActionsLive(new ArrayList<String>());
    }

    private void droidNetAndMetrics() {
        // Hides the navigation bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        pixels = Math.round(10 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        //No use for friendly game
        if (!isFriendly) {
//            mDroidNet = DroidNet.getInstance();
            try {
                mDroidNet = DroidNet.getInstance();
            } catch (IllegalStateException fuckYouTooAndroid) {
                DroidNet.init(getBaseContext().getApplicationContext());
                mDroidNet = DroidNet.getInstance();
            }
            mDroidNet.addInternetConnectivityListener(this);
        }
    }

    private void setupRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.ngstats.gr/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        requests = retrofit.create(RequestsOfCountStatsActivity.class);
    }

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
    }

    // ____________________ Override Methods ____________
    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        if (isConnected) {
            //do Stuff with internet
            connectionStatus.setBackgroundResource(R.color.green);
            connectionStatus.setText("ONLINE");
        } else {
            //no internet
            connectionStatus.setBackgroundResource(R.color.red);
            connectionStatus.setText("OFFLINE");        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        if(text.equals("Switch Sides")){
            swapTeams();
        }else if(text.equals("Events")){
            //openEventsAct();
        }else if(text.equals("Stats")){
            openStatsDialog();
        }else if(text.equals("Create Pdf")){
            //try {
                checkForThePermission();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
        }else if(text.equals("Telephones")){
            openTelephonesDialog();
        }
        spinnerMenu.setSelection(0);
    }

    //Opens the telephones dialog that just displays some info
    private void openTelephonesDialog() {
        TelephonesDialog telDialog = new TelephonesDialog();
        telDialog.show(getSupportFragmentManager(), "Telephone Dialog");
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    private void openConfigureDialog(Team team)
    {
        FragmentManager fm = getSupportFragmentManager();
        ConfTeamDialog statsDialog = new ConfTeamDialog();
        statsDialog.setTeam(team);
        statsDialog.show(fm, "");
    }

    private void openStatsDialog(){
        if (isFriendly) return;
        FragmentManager fm = getSupportFragmentManager();
        StatsDialog statsDialog = new StatsDialog();
        statsDialog.setLive(matchDTO.getLive());
        statsDialog.setTeams(leftTeam.getName(),rightTeam.getName());
        statsDialog.setLeftIsA(isDefault);
        statsDialog.show(fm, "");
    }

    private void openPlayerChange(Team team)
    {
        playerButtonAction = "changePlayer";
        yellowCardButton.setBackgroundResource(R.drawable.faded_yellow_card);
        redCardButton.setBackgroundResource(R.drawable.faded_red_card);
        changePlayer = new ChangePlayerFragment();
        changePlayer.setTeam(team);
        openFragment(changePlayer);
        if (team.getIsTeamA()) rightList.setVisibility(View.INVISIBLE);
        else leftList.setVisibility(View.INVISIBLE);
    }

    private void increaseOffside(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("offside", isTeamA, true);
            addEvent("ΟΦΣΑΙΝΤ | " + (isTeamA ? "A" : "B") + " |");
        }
    }

    private void increaseCornerLeft(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("cornerLeft", isTeamA, true);
            addEvent("ΚΟΡΝΕΡ ΑΡΙΣΤΕΡΗ ΠΛΕΥΡΑ | " + (isTeamA ? "A" : "B") + " |");
        }
    }

    private void increaseCornerRight(boolean isTeamA, ImageView img) {
        if (doubleClicked()) {
            startScaleAnimation(img);
            changeStat("cornerRight", isTeamA, true);
            addEvent("ΚΟΡΝΕΡ ΔΕΞΙΑ ΠΛΕΥΡΑ | " + (isTeamA ? "A" : "B") + " |");
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
        textViewGoal.setBackgroundColor(Color.parseColor("#c2c2c2"));
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
        team.replace(playerIn,playerOut);
        playerIn.setSelection(true);
        playerOut.setSelection(null);

        TeamDTO targetTeam;
        if (matchDTO.getTeamA().getName().equals(team.getName()))
            targetTeam = matchDTO.getTeamA();
        else
            targetTeam = matchDTO.getTeamB();
        PlayerDTO cPlayerDto;
        ArrayList<PlayerDTO> currentPlayersList = new ArrayList<>();
        for(Player p : team.getAllPlayers()){
            cPlayerDto = new PlayerDTO(p) ;
            currentPlayersList.add(cPlayerDto);
        }
        targetTeam.setPlayers(currentPlayersList);
        team.sortByNumber();
        notifyAdapters();
        sendWholeDTO();
    }

    private void createTheStatusDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CountStatsActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_status,null);

        Button buttonOk =  mView.findViewById(R.id.button_OkStatus);
        Button buttonOkDelays =  mView.findViewById(R.id.button_OkDelays);
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

        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdBtn_PreGame.isChecked())
                {
                    pauseChronometer();
                    textViewStatus.setText("Pre Game");
                    gameStatus = "Pre Game";
                    chronometer.setText("00:00");
                    setUnclickableAllButtons();
                    //descriptionOfMatch = editTextDescription.getText().toString();
                    dialog.dismiss();
                }
                else if(rdBtn_FirstHalf.isChecked())
                {
                    if(!running){
                        startAChronometer();
                        textViewStatus.setText("First Half");
                        gameStatus = "First Half";
                        dialog.dismiss();
                        setClickableAllButtons();
                        textViewDelays.setText("");
                    }
                }
                else if(rdBtn_HalfTime.isChecked())
                {
                    pauseChronometer();
                    textViewStatus.setText("Half Time");
                    gameStatus = "Half Time";
                    if(!isParatasiSelected)
                        chronometer.setText("45:00");
                    else
                        chronometer.setText("105:00");
                    setUnclickableAllButtons();
                    textViewDelays.setText("");
                    dialog.dismiss();
                }
                else if(rdBtn_SecondHalf.isChecked())
                {
                    if(!running){
                        startBChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Second Half");
                        gameStatus = "Second Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }
                }
                else if(rdBtn_FinalGame.isChecked())
                {
                    pauseChronometer();
                    textViewStatus.setText("Final");
                    gameStatus = "Final";
                    dialog.dismiss();
                    openAreYouSureDialog();
                }
                else if(rdBtn_NormalEnd.isChecked())
                {
                    pauseChronometer();
                    chronometer.setText("90:00");
                    setUnclickableAllButtons();
                    textViewStatus.setText("Normal Time Finish");
                    gameStatus = "Normal Time Finish";
                    textViewDelays.setText("");
                    dialog.dismiss();
                }
                else if(rdBtn_ExtraTime.isChecked())
                {
                    if (!running)
                    {
                        isParatasiSelected = true;
                        startAChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Extra First Half");
                        gameStatus = "Extra First Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }

                }
                else if(rdBtn_ExtraTimeSecond.isChecked())
                {
                    if (!running)
                    {
                        isParatasiSelected = true;
                        startBChronometer();
                        setClickableAllButtons();
                        textViewStatus.setText("Extra Second Half");
                        gameStatus = "Extra Second Half";
                        textViewDelays.setText("");
                        dialog.dismiss();
                    }

                }
                else if(rdBtn_Penalty.isChecked())
                {
                    isParatasiSelected = false;
                    pauseChronometer();
                    textViewStatus.setText("Penalty");
                    gameStatus = "Penalty";
                    dialog.dismiss();
                    openFragment(penaltyFragment);
                }


                // _______________  POST REQUEST HERE  ___________________
                if (!isFriendly){
                    matchDTO.setStatus(gameStatus);
                    updateStat("status",gameStatus);
                }
            }
        });

        buttonOkDelays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delaysString = editTextDelays.getText().toString().trim();
                if( !(delaysString.isEmpty()) ){
                    int del = Integer.parseInt(delaysString); // only for the check
                    if(del>=1){
                        textViewDelays.setText(" +" + delaysString +" ");
                        addEvent("ΚΑΘΥΣΤΕΡΗΣΕΙΣ "+del+" ΛΕΠΤΑ");
                        dialog.dismiss();
                    }
                }

                // _______________  POST REQUEST HERE  ____________________
//                if(gameStatus.equals("First Half")){
//                    if (!isFriendly) gameRef.child("delaysA").setValue(delaysString);
//                }else if(gameStatus.equals("Second Half")){
//                    if (!isFriendly) gameRef.child("delaysB").setValue(delaysString);
//                }else if(gameStatus.equals("Extra First Half")){
//                    if (!isFriendly) gameRef.child("delaysExtraA").setValue(delaysString);
//                }else if(gameStatus.equals("Extra Second Half")){
//                    if (!isFriendly) gameRef.child("delaysExtraB").setValue(delaysString);
//                }
                if(!isFriendly){
                    if (gameStatus.equals("First Half")) {
                        matchDTO.setDelaysA(Integer.parseInt(delaysString));
                        updateStat("delaysA",matchDTO.getDelaysA().toString());
                    }
                    else if (gameStatus.equals("Second Half")){
                        matchDTO.setDelaysB(Integer.parseInt(delaysString));
                        updateStat("delaysB",matchDTO.getDelaysB().toString());
                    }
                    else if (gameStatus.equals("Extra First Half")){
                        matchDTO.setDelaysExtraA(Integer.parseInt(delaysString));
                        updateStat("delaysExtraA",matchDTO.getDelaysExtraA().toString());
                    }
                    else if (gameStatus.equals("Extra Second Half")){
                        matchDTO.setDelaysExtraB(Integer.parseInt(delaysString));
                        updateStat("delaysExtraB",matchDTO.getDelaysExtraB().toString());
                    }

                }
            }
        });

        buttonStartFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NOT Null
                String min = editTextMinutes.getText().toString();
                if(!(min.matches(""))){
                    int minutes = Integer.parseInt(min);
                    startChronomoter(minutes);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getBaseContext(), "Enter Minutes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void startAChronometer(){
        if(!isParatasiSelected){
            if(!running){
                chronometer.setBase(SystemClock.elapsedRealtime() - (0 * 60000 + 0 * 1000));
                //chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running=true;
            }
        }else{
            if(!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - (90 * 60000 + 0 * 1000));
                chronometer.start();
                running = true;
            }
        }

    }

    public void startBChronometer(){
        if(!isParatasiSelected){
            if(!running){
                chronometer.setBase(SystemClock.elapsedRealtime() - (45* 60000 + 0 * 1000));
                chronometer.start();
                running=true;
            }
        }else{
            if(!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - (105 * 60000 + 0 * 1000));
                chronometer.start();
                running = true;
            }
        }
    }

    public void pauseChronometer(){
        if(running){
            chronometer.stop();
//            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running=false;
        }
    }

    public void startChronomoter(int min){
        chronometer.setBase(SystemClock.elapsedRealtime() - (min * 60000 + 0 * 1000));
        chronometer.start();
        running=true;
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

        if (!isFriendly) sendWholeDTO();
        // ______________ POST CODE HERE ______________________
    }

    // _________________________ GETTER and SETTERS ______________________________

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getIsTeamAForAction()
    {
        return isTeamAForAction;
    }

    public String getNumberOfShirt()
    {
        return numberOfShirt;
    }

    // _______________________ For Dialog after Final ______________________
    @Override
    public void changeTheStatusToFinal() {
        textViewStatus.setText("Final");
        gameStatus = "Final";
        setUnclickableAllButtons();
        pauseChronometer();
        dialog.dismiss();
    }
    // _________________________ Edit Events _________________________________

    private void editAction(String actionText)
    {
        String[] actionParts = actionText.split("\\|");

        eventAdapter.setEdit(true);

        if(actionParts[0].contains("ΓΚΟΛ"))
        {
            openFragment(goalFragment);
        }
        else if (actionParts[0].contains("ΣΟΥΤ") || actionParts[0].contains("ΚΕΦΑΛΙΑ"))
        {
            openFragment(telikiFragment);
        }
        else if (actionParts[0].contains("ΦΑΟΥΛ"))
        {
            openFragment(faoulFragment);
        }
        else if (actionParts[0].contains("ΓΕΜ"))
        {
            openFragment(gemismaFragment);
        }
        else if (actionParts[0].contains("ΛΑΘΟΣ"))
        {
            openFragment(lathosFragment);
        }
        else if (actionParts[0].contains("ΚΙΤΡΙΝΗ"))
        {
            Toast.makeText(this, "Click on a player",
                    Toast.LENGTH_LONG).show();
            playerButtonAction = "yellow";
            yellowCardButton.setBackgroundResource(R.drawable.yellow_card);
        }
        else if (actionParts[0].contains("ΚΟΚΚΙΝΗ"))
        {
            Toast.makeText(this, "Click on a player",
                    Toast.LENGTH_LONG).show();
            playerButtonAction = "red";
            redCardButton.setBackgroundResource(R.drawable.red_card);
        }
        else if (actionParts[0].contains("AΣΙΣΤ"))
        {
            Toast.makeText(this, "Click on a player",
                    Toast.LENGTH_LONG).show();
            playerButtonAction = "editAssist";
        }
        else
        {
            Toast.makeText(this, "EDIT NOT AVAILABLE",
                    Toast.LENGTH_LONG).show();
            eventAdapter.setEdit(false);
        }
    }

    public void readBaseEvents()
    {
        ArrayList<String> ev = eventAdapter.getEvents();
        Team currentTeam; Pattern pattern; Matcher matcher1;
        for (String singleEvent: ev)
        {
            if (singleEvent.contains("| A |")) currentTeam = leftTeam;
            else currentTeam = rightTeam;
            if (singleEvent.contains("ΚΙΤΡΙΝΗ"))
            {
                pattern = Pattern.compile("(?:.*)\\| (\\d+) \\|(?:.*)");
                matcher1 = pattern.matcher(singleEvent);
                if (matcher1.matches())
                {
                    currentTeam.yellowFromNumber(matcher1.group(1));
                }
            }
            else if (singleEvent.contains("ΚΟΚΚΙΝΗ"))
            {
                pattern = Pattern.compile("(?:.*)\\| (\\d+) \\|(?:.*)");
                matcher1 = pattern.matcher(singleEvent);
                if (matcher1.matches())
                {
                    currentTeam.redFromNumber(matcher1.group(1));
                }
            }
            else if (singleEvent.contains("ΑΛΛΑΓΗ"))
            {
                pattern = Pattern.compile("(?:.*)\\D+(\\d+)\\D+(\\d+)(?:.*)", Pattern.UNICODE_CASE);
                matcher1 = pattern.matcher(singleEvent);
                if (matcher1.matches())
                {
                    currentTeam.changeFromReadingActions(matcher1.group(2),matcher1.group(1));
                }
            }
        }
        notifyAdapters();
    }

    public void undoAction(String actionText)
    {
        String[] actionParts = actionText.split("\\|");

        int index = 2;
        if (actionParts.length <= index) index = actionParts.length -1;
        if ((actionParts[0].contains("ΚΟΡΝΕΡ") || actionParts[0].contains("ΟΦΣΑΙΝΤ"))
                && !(actionParts[0].contains("ΓΚΟΛ")|| actionParts[0].contains("ΕΚΤΟΣ")
                || actionParts[0].contains("ΕΝΤΟΣ"))) index = 1;
        boolean teamA;
        if (actionParts[index].contains("A")) teamA = true;
        else teamA = false;

        if(actionParts[0].contains("ΓΚΟΛ")) {
            if (actionParts[0].contains("ΑΥΤΟΓΚΟΛ")) teamA = !teamA;
            changeStat("goal", teamA, false);
        }
        if (actionParts[0].contains("ΕΚΤΟΣ") || actionParts[0].contains("ΕΝΤΟΣ"))
        {
            if (actionParts[0].contains("ΔΟΚΑΡΙ"))
            {
                changeStat("dokari",teamA,false);
            }
            if (actionParts[0].contains("ΧΑΜΕΝΗ")) // ΧΑΜΕΝΗ ΕΥΚΑΙΡΙΑ
            {
                changeStat("xameniEukairia",teamA,false);
            }
            if (actionParts[0].contains(("ΕΚΤΟΣ")))
            {
                if(actionParts[0].contains("ΚΟΡΝΕΡ"))
                {
                    changeStat("apoKornerOut",teamA,false);
                }
                else if (actionParts[0].contains("ΚΕΦΑΛΙΑ"))
                {
                    changeStat("kefaliaOut",teamA,false);
                }
                else if (actionParts[0].contains("ΦΑΟΥΛ"))
                {
                    changeStat("apoFaoulOut",teamA,false);
                }
                else if (actionParts[0].contains("ΠΕΝΑΛΤΙ"))
                {
                    changeStat("xamenoPenaltyOut",teamA,false);
                }
                else
                {
                    changeStat("shootOut",teamA,false);
                }
            }
            else
            {
                if(actionParts[0].contains("ΚΟΡΝΕΡ"))
                {
                    changeStat("apoKornerIn",teamA,false);
                }
                else if (actionParts[0].contains("ΚΕΦΑΛΙΑ"))
                {
                    changeStat("kefaliaIn",teamA,false);
                }
                else if (actionParts[0].contains("ΦΑΟΥΛ"))
                {
                    changeStat("apoFaoulIn",teamA,false);
                }
                else if (actionParts[0].contains("ΠΕΝΑΛΤΙ"))
                {
                    changeStat("xamenoPenaltyIn",teamA,false);
                }
                else
                {
                    changeStat("shootIn",teamA,false);
                }
            }
        }
        else if (actionParts[0].contains("ΑΠΟΚΡΟΥΣΗ"))
        {
            changeStat("apokrousi",teamA,false);
        }
        else if (actionParts[0].contains("ΚΟΡΝΕΡ"))
        {
            if (actionParts[0].contains("ΑΡΙΣΤΕΡΗ"))
            {
                changeStat("cornerLeft",teamA,false);
            }
            else
            {
                changeStat("cornerRight",teamA,false);
            }
        }
        else if (actionParts[0].contains("ΦΑΟΥΛ"))
        {
            changeStat("faoulKata",teamA,false);
        }
        else if (actionParts[0].contains("ΥΠΕΡ"))
        {
            changeStat("faoulYper",teamA,false);
        }
        else if (actionParts[0].contains("ΓΕΜ"))
        {
            if (actionParts[0].contains("ΕΠΙΤ"))
            {
                changeStat("gemismaIn",teamA,false);
            }
            else
            {
                changeStat("gemismaOut",teamA,false);
            }
        }
        else if (actionParts[0].contains("ΕΠΕΜΒΑΣΗ"))
        {
            changeStat("epemvasi",teamA,false);
        }
        else if (actionParts[0].contains("ΛΑΘΟΣ"))
        {
            changeStat("lathos",teamA,false);
        }
        else if (actionParts[0].contains("ΚΙΤΡΙΝΗ"))
        {
            int yellowCardNumber;
            if ((leftTeam.getIsTeamA() && teamA) || (!leftTeam.getIsTeamA() && !teamA))
            {
                leftTeam.reduceYellow(actionParts[1]);
                yellowCardNumber = leftTeam.getTotalYellowCards();
            }
            else
            {
                rightTeam.reduceYellow(actionParts[1]);
                yellowCardNumber = rightTeam.getTotalYellowCards();
            }
            if (teamA)
            {
//                String liveChange = liveMatch.updateYellowCards(yellowCardNumber,"A");
//                if (!isFriendly)databaseLive.child("yellowCard").setValue(liveChange);
            }
            else
            {
//                String liveChange = liveMatch.updateYellowCards(yellowCardNumber,"B");
//                if (!isFriendly)databaseLive.child("yellowCard").setValue(liveChange);
            }
            notifyAdapters();
        }
        else if (actionParts[0].contains("ΚΟΚΚΙΝΗ"))
        {
            int redCardNumber;
            if ((leftTeam.getIsTeamA() && teamA) || (!leftTeam.getIsTeamA() && !teamA))
            {
                leftTeam.reduceRed(actionParts[1]);
                redCardNumber = leftTeam.getTotalRedCards();
            }
            else
            {
                rightTeam.reduceRed(actionParts[1]);
                redCardNumber = rightTeam.getTotalRedCards();
            }
            if (teamA)
            {
//                String liveChange = liveMatch.updateRedCards(redCardNumber,"A");
//                if (!isFriendly)databaseLive.child("redCard").setValue(liveChange);
            }
            else
            {
//                String liveChange = liveMatch.updateRedCards(redCardNumber,"B");
//                if (!isFriendly)databaseLive.child("redCard").setValue(liveChange);
            }
            notifyAdapters();
        }else if(actionParts[0].contains("ΔΙΕΙΣΔΥΣΗ"))
        {
            if(actionParts[0].contains("ΑΠΟΤ")){
                changeStat("dieisdusiOut",teamA,false);
            }else{
                changeStat("dieisdusiIn",teamA,false);
            }
        }else if(actionParts[0].contains("ΚΟΨΙΜΟ")){
            changeStat("kopsimo",teamA,false);
        }
        else if (actionParts[0].contains("ΟΦΣΑΙΝΤ"))
        {
            changeStat("offside",teamA,false);
        }
        else if (actionParts[0].contains("ΚΛΕΨΙΜΟ"))
        {
            changeStat("klepsimo",teamA,false);
        }
        else if (actionParts[0].contains("ΠΕΝΑΛΤΙ"))
        {
            if (actionParts[0].contains("ΕΥΣΤΟΧΟ"))
            {
                changeStat("penaltyScore",teamA,false);
            }
        }
        else if (actionParts[0].contains("ΑΣΙΣΤ"))
        {
            changeStat("assist",teamA,false);
        }
        else if (actionParts[0].contains("ΑΛΛΑΓΗ"))
        {
            Pattern pattern = Pattern.compile("\\D+(\\d+)\\D+(\\d+)", Pattern.UNICODE_CASE);
            Matcher matcher1 = pattern.matcher(actionParts[1]);
            if (matcher1.matches())
            {
                System.out.println("GROUPS: OUT"+matcher1.group(1)+" IN "+matcher1.group(2));
                if ((teamA && leftTeam.getIsTeamA())|| (!teamA && !leftTeam.getIsTeamA()))
                {
                    leftTeam.replaceFromNumbers(matcher1.group(2),matcher1.group(1));
                }
                else
                {
                    rightTeam.replaceFromNumbers(matcher1.group(2),matcher1.group(1));
                }
            }
            notifyAdapters();
        }
        else if (actionParts[0].contains("ΠΑΣΑ"))
        {
            changeStat("forwardingPass",teamA,false);
        }
    }

    public void addEventToList(String info) {
        addEvent(info+numberOfShirt+ " | " +(isTeamAForAction?"A":"B")+" | " +location);
    }

    public void addEvent(String event){
        eventAdapter.addEvent(event);
    }

    // ______________________ Frequently Used Methods ______________________________

    public void notifyAdapters() {
        leftAdapter.notifyDataSetChanged();
        rightAdapter.notifyDataSetChanged();
    }

    public void changeStat(String stat, boolean isTeamA, boolean increase) {
        String value = statUpdater.increaseOrDecreaseStat(stat, isTeamA, increase);
        checkGoals(stat,isTeamA,increase);
        if (!isFriendly ) updateLive(stat, value);
    }

    public void changeStat(String stat, boolean increase) {
        String value = statUpdater.increaseOrDecreaseStat(stat, isTeamAForAction, increase);
        checkGoals(stat,isTeamAForAction,increase);
        if (!isFriendly) updateLive(stat, value);
    }

    private void checkGoals(String stat, boolean isTeamA, boolean increase) {
        if (stat.equals("goal")) {
            String[] goals = matchDTO.getLive().getGoal().split("#");
            updateLive(stat, matchDTO.getLive().getGoal());
            textViewGoalLeft.setText(goals[isDefault ? 0 : 1]);
            textViewGoalRight.setText(goals[isDefault ? 1 : 0]);
            if(isParatasiSelected)
                changeStat("extraTimeScore", isTeamA, increase);
        }
    }

    private void sendWholeDTO() {
        Call<Void> call = requests.postMatch( matchDTO ) ;

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    return;
                }
                System.out.println("SUCCESS");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e( "---------> ", t.getMessage() );
            }


        });
    }

    private void updateLive(String fieldName, String value) {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        HashMap<String, String> live = new HashMap<>();
        live.put(fieldName,value);
        fields.put("LIVE", live);
        Call<Void> call = requests.postSomeFields( fields ) ;

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.request();

                Log.e( "---------> ", t.getMessage() );
            }


        });
    }

    private void updateStat(String fieldName, String value) {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        fields.put(fieldName,value);
        Call<Void> call = requests.postSomeFields( fields ) ;

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.request();

                Log.e( "---------> ", t.getMessage() );
            }


        });
    }

    private void updateActionList() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("match_id", matchDTO.getMatchId());
        fields.put("ActionsLive", matchDTO.getActionsLive());
        Call<Void> call = requests.postSomeFields( fields ) ;

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                call.request();

                Log.e( "---------> ", t.getMessage() );
            }


        });
    }
    //Mutators(set)
    public void setScoreStr(String scoreStr) {
        this.scoreStr = scoreStr;
    }
    public void setpCenterTimeStr(String pCenterTimeStr) { this.pCenterTimeStr = pCenterTimeStr; }
    public void setpLeftTimeStr(String pLeftTimeStr) {
        this.pLeftTimeStr = pLeftTimeStr;
    }
    public void setpRightTimeStr(String pRightTimeStr) {
        this.pRightTimeStr = pRightTimeStr;
    }
    public void setpTotalTimeStr(String pTotalTimeStr) {
        this.pTotalTimeStr = pTotalTimeStr;
    }

    public void setTemplatePdf(TemplatePdf templatePdf) {
        this.templatePdf = templatePdf;
    }
    // ______________________________ PDF __________________________________________
    //For saving the pdf need storage permission
    //Firstly check if we already gave that permission
    private void checkForThePermission() {

        if (ContextCompat.checkSelfPermission(CountStatsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            createPdf();
        else
            requestStoragePermissions();
    }

    // Dialog that explains why we need the permission
    // It shows when the user denied the permission before
    private void requestStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Storage Permission Needed").setMessage
                    ("We need storage permission for saving any PDFs that will be created")
                    //This is when the user click ok. It requests storage permission from him
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CountStatsActivity.this, new String[]
                                            {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    WRITE_STORAGE_PERMISSION_CODE);
                            createPdf();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_PERMISSION_CODE);
        }
    }

    //Finally we need to check if we have the permission to make the pdf
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WRITE_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                createPdf();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Method for creating the pdf. Friendly games get no pdf.
    private void createPdf() {
        if (isFriendly) return;

        setScoreStr(matchDTO.getLive().getGoal());
        //setpTotalTimeStr(dataSnapshot.child("pTotalTime").getValue().toString());
        //setpLeftTimeStr(dataSnapshot.child("pLeftTime").getValue().toString());
        //setpRightTimeStr(dataSnapshot.child("pRightTime").getValue().toString());
        //setpCenterTimeStr(dataSnapshot.child("pCenterTime").getValue().toString());


        String startTimeStr = matchDTO.getTime();
        String dateStr = matchDTO.getDate();
        String fieldStr = matchDTO.getField();
        String nameTeamA = matchDTO.getTeamA().getName();
        String nameTeamB = matchDTO.getTeamB().getName();
        String nameOrganizationStr = matchDTO.getOrganization();

        String numberRacingStr = "racing";
        String coachA = isDefault ? leftTeam.getCoachFullName() :
                        rightTeam.getCoachFullName();
        String coachB = isDefault ? rightTeam.getCoachFullName() :
                        leftTeam.getCoachFullName();

        TemplatePdf tempPdf = new TemplatePdf(this, liveActionsList,
                        leftAdapter, rightAdapter, numberRacingStr,
                        startTimeStr, dateStr, fieldStr, nameTeamA, nameTeamB,
                        nameOrganizationStr, getScoreStr(), coachA, coachB,
                        getpTotalTimeStr(), getpLeftTimeStr(), getpCenterTimeStr(),
                        getpRightTimeStr()
                );

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

    public TemplatePdf getTemplatePdf() {
        return templatePdf;
    }

    public String getScoreStr() {
        return scoreStr;
    }
    public String getpCenterTimeStr() {
        return pCenterTimeStr;
    }
    public String getpLeftTimeStr() {
        return pLeftTimeStr;
    }
    public String getpTotalTimeStr() {
        return pTotalTimeStr;
    }
    public String getpRightTimeStr() {
        return pRightTimeStr;
    }

    // __________________________  Extra Classes ___________________________________
    public class BasicPlayerAdapter extends BaseAdapter {
        private Context context;
        private Team team;
        private boolean left;
        private boolean isTeamA;

        BasicPlayerAdapter(Context context, boolean left, boolean isTeamA, Team team) {
            this.context = context;
            this.left = left;
            this.isTeamA = isTeamA;
            this.team = team;
        }

        public void changeTeam(Team team)
        {
            isTeamA = !isTeamA;
            this.team = team;
            this.notifyDataSetChanged();
        }

        public Team getTeam()
        {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public boolean isTeamA() {
            return isTeamA;
        }

        public void setTeamA(boolean teamA) {
            isTeamA = teamA;
        }

        @Override
        public int getCount() {
            return team.getBasicPlayers().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (left)
                view = getLayoutInflater().inflate(R.layout.basic_player_one, null);

            else
                view = getLayoutInflater().inflate(R.layout.basic_player_two, null);

            Button allNumbersList = view.findViewById(R.id.button_number);
            allNumbersList.setBackgroundColor(team.getColour());

            final ImageView redCard = view.findViewById(R.id.imageView_redCard);
            final ImageView yellowCard = view.findViewById(R.id.imageView_yellowCardOne);
            final ImageView secYellowCard = view.findViewById(R.id.imageView_yellowCardTwo);
            Button plusSign = view.findViewById(R.id.button_plusSign);


            LayerDrawable layers = (LayerDrawable)plusSign.getBackground();
            ((GradientDrawable) layers.findDrawableByLayerId(R.id.blackLineOne)).setStroke(pixels,team.getColour());
            RotateDrawable rotate = (RotateDrawable) layers.findDrawableByLayerId(R.id.blackLineTwo);
            ((GradientDrawable)rotate.getDrawable()).setStroke(pixels,team.getColour());

            final Player currentPlayer = team.getBasicPlayer(i);
            final boolean hasRedCard = team.hasRedCard(currentPlayer.getPlayerId());

            if (!hasRedCard)
            {
                redCard.setVisibility(View.INVISIBLE);
            }

            final int yellowCards = team.getYellowCards(currentPlayer.getPlayerId());
            if ( yellowCards == 0) {
                yellowCard.setVisibility(View.INVISIBLE);
                secYellowCard.setVisibility(View.INVISIBLE);
            }
            else if (yellowCards == 1)
            {
                secYellowCard.setVisibility(View.INVISIBLE);
            }


            // ______________ Listener _________________


            allNumbersList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String teamAorB;
                    if (team.getIsTeamA()) teamAorB = "A";
                    else teamAorB = "B";
                    if (hasRedCard && !eventAdapter.getEdit())
                    {
                        Toast.makeText(getBaseContext(),"Player has a Red Card",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        yellowCardButton.setBackgroundResource(R.drawable.faded_yellow_card);
                        redCardButton.setBackgroundResource(R.drawable.faded_red_card);

                        switch (playerButtonAction) {

                            case "default":
                                numberOfShirt = currentPlayer.getNumber();
                                isTeamAForAction = team.getIsTeamA();
                                amForAction = currentPlayer.getPlayerId();
                                break;


                            case "red":
                                playerButtonAction = "default";
                                if (!team.hasRedCard(currentPlayer.getPlayerId()))
                                {
                                    team.toggleRedCard(currentPlayer.getPlayerId());
                                    addEvent("ΚΟΚΚΙΝΗ | "+ currentPlayer.getNumber() + " | "+ teamAorB +" |");
                                }


                                statUpdater.updateRedCards(team.getTotalRedCards(),teamAorB);
                                if (!isFriendly) updateLive("redCard",matchDTO.getLive().getRedCard());
                                restoreDefaultValues();

                                break;


                            case "yellow":
                                playerButtonAction = "default";
                                if (yellowCards == 0)
                                {
                                    team.toggleYellowCard(currentPlayer.getPlayerId());
                                    addEvent("ΚΙΤΡΙΝΗ | "+ currentPlayer.getNumber() + " | "+ teamAorB +" |");
                                }
                                else if (yellowCards == 1)
                                {
                                    team.toggleYellowCard(currentPlayer.getPlayerId());
                                    team.toggleRedCard(currentPlayer.getPlayerId());
                                    addEvent(" 2Η ΚΙΤΡΙΝΗ -> ΚΟΚΚΙΝΗ | "+
                                            currentPlayer.getNumber() + " | "+ teamAorB+" |");

                                    statUpdater.updateRedCards(team.getTotalRedCards(),teamAorB);

                                }

                                statUpdater.updateYellowCards(team.getTotalYellowCards(),teamAorB);
                                if (!isFriendly) updateLive("yellowCard",matchDTO.getLive().getYellowCard());
                                restoreDefaultValues();
                                break;
                            case "changePlayer":

                                if (changePlayer != null)
                                {
                                    changePlayer.setPlayerOut(currentPlayer);
                                    numberOfShirt = currentPlayer.getNumber();
                                    isTeamAForAction = team.getIsTeamA();
                                    amForAction = currentPlayer.getPlayerId();
                                }
                                break;
                            case "editAssist":
                                playerButtonAction = "default";
                                addEvent("AΣΙΣΤ | " + currentPlayer.getNumber() + " | " +
                                        teamAorB );
                                restoreDefaultValues();
                        }
                    }
                    notifyAdapters();
                }
            });

            if (currentPlayer.getPosition().equals("GK"))
                allNumbersList.setText(currentPlayer.getNumber()+"(T)");
            else
                allNumbersList.setText(currentPlayer.getNumber());


            if (amForAction.equals(currentPlayer.getPlayerId()))
            {
                allNumbersList.setBackgroundColor(Color.BLACK);
                allNumbersList.setTextColor(Color.WHITE);
            }
            else
            {
                allNumbersList.setTextColor(Color.BLACK);
            }

            plusSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (buttonsUnclickable || hasRedCard) return;
                    String teamAorB;
                    if (team.getIsTeamA()) teamAorB = "A";
                    else teamAorB = "B";
                    addEvent("ΠΡΟΩΘΗΜΕΝΗ ΠΑΣΑ | "+ currentPlayer.getNumber() + " | "+ teamAorB +" |");
                    changeStat("forwardingPass",team.getIsTeamA(),true);
                }
            });

            return view;
        }



    }



    // -----------------  ________________________  -------------------

    class EventAdapter extends BaseAdapter {
        private ArrayList<String> events = new ArrayList<>();
        private int selectedIndex;
        private boolean edit = false;

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void addStoredEvents() {
            String elements[];
            System.out.println("Size Events : ----- " + liveActionsList.size());
            for (DataLiveActions e : liveActionsList) {
                elements = e.toString().split(":", 2);
                events.add(elements[0] + "'," + elements[1] + ",false");
            }

            // selectedIndex = getCount()-1;
            readBaseEvents();
            this.notifyDataSetChanged();
        }

        public void setEdit(boolean edit)
        {
            this.edit = edit;
        }

        public void addEvent(String event)
        {
            String extraInfo = "";
            int maxMin = 1000;
            if (gameStatus.equals("First Half")){
                extraInfo = " | Α ΗΜΙΧ | " + isDefault+"";
                maxMin = 45;
            }
            else if (gameStatus.equals("Second Half")) {
                extraInfo = " | Β ΗΜΙΧ | " + isDefault+"";
                maxMin = 90;
            }
            else if (gameStatus.equals("Extra First Half")) {
                extraInfo = " | ΠΑΡΑΤ A ΗΜΙΧ | " + isDefault+"";
                maxMin = 105;
            }
            else if (gameStatus.equals("Extra Second Half")) {
                extraInfo = " | ΠΑΡΑΤ Β ΗΜΙΧ | " + isDefault+"";
                maxMin = 120;
            }
            else if (gameStatus.equals("Penalty")) {
                extraInfo = " | ΠΕΝΑΛΤΙ | " + isDefault+"";
            }

            if (!edit)
            {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int m = (int) time / 60000 + 1;
                DataLiveActions dataAction;
                if (m < maxMin){
                    events.add(m+"',"+event+extraInfo+",false");
                    dataAction = new DataLiveActions(m + " : " + event+extraInfo);
                }
                else{
                    events.add(maxMin+"+"+(m-maxMin)+"',"+event+extraInfo+",false");
                    //dataAction = new DataLiveActions(maxMin+"+"+(m-maxMin) + " : " + event+extraInfo);
                    dataAction = new DataLiveActions(m + " : " + event+extraInfo);
                }

                liveActionsList.add(dataAction);

                selectedIndex = getCount()-1;
            }
            else
            {
                undoAction(events.get(selectedIndex));
                changeEvent(event);
                edit = false;
            }
            this.notifyDataSetChanged();
            remakeActionList();
        }

        public void setSelected(int index)
        {
            selectedIndex = index;
            this.notifyDataSetChanged();
        }

        public void deleteSelected()
        {
            if(events.size()>0)
            {
                undoAction(events.get(selectedIndex));
                events.remove(selectedIndex);
                liveActionsList.remove(selectedIndex);
                remakeActionList();

            }
            if(selectedIndex==events.size())selectedIndex--;
            this.notifyDataSetChanged();
        }

        public void flagSelected()
        {
            if(getCount()==0) return;
            String[] currentInfo = events.get(selectedIndex).split(",");
            if(currentInfo[2].equals("false"))
            {
                events.set(selectedIndex,currentInfo[0]+","+currentInfo[1]+",true");
            }
            else events.set(selectedIndex,currentInfo[0]+","+currentInfo[1]+",false");
            remakeActionList();
            this.notifyDataSetChanged();
        }


        public boolean getEdit() { return edit; }

        public ArrayList<String> getEvents()
        {
            return events;
        }

        public void changeEvent(String newEvent)
        {
            String[] info = events.get(selectedIndex).split(",");
            events.set(selectedIndex,info[0]+","+newEvent+","+info[2]);
            liveActionsList.set(selectedIndex,new DataLiveActions(info[0]+":"+newEvent+","+info[2]));
        }

        public String getSelectedText()
        {
            return events.get(selectedIndex);
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(i == selectedIndex)
                view = getLayoutInflater().inflate(R.layout.selected_event_item, null);

            else
                view = getLayoutInflater().inflate(R.layout.event_item, null);


            String[] currentEventInfo = events.get(i).split(",");

            TextView eventDescription = view.findViewById(R.id.textView_description);
            TextView eventTime = view.findViewById(R.id.textView_eventTime);
            ImageView flag = view.findViewById(R.id.imageView_redFlag);
            ImageView teamColour = view.findViewById(R.id.imageView_teamColour);

            //Changed just display... we can change what is in base but need to keep a or b for the app
            String displayed;
            if (leftTeam.getIsTeamA())
            {
                displayed = currentEventInfo[1].replaceAll("\\| A \\|","| "+leftTeam.getName()+" |");
                displayed = displayed.replaceAll("\\| B \\|","| "+rightTeam.getName()+" |");
            }
            else
            {
                displayed = currentEventInfo[1].replaceAll("\\| B \\|","| "+leftTeam.getName()+" |");
                displayed = displayed.replaceAll("\\| A \\|","| "+rightTeam.getName()+" |");
            }

            if (currentEventInfo[1].matches("(.*?)\\| A \\|(.*?)") && leftTeam.getIsTeamA())
            {
                teamColour.setBackgroundColor(leftTeam.getColour());
            }
            else if (currentEventInfo[1].matches("(.*?)\\| A \\|(.*?)"))
            {
                teamColour.setBackgroundColor(rightTeam.getColour());
            }
            else if (currentEventInfo[1].matches("(.*?)\\| B \\|(.*?)") && leftTeam.getIsTeamA())
            {
                teamColour.setBackgroundColor(rightTeam.getColour());
            }
            else if (currentEventInfo[1].matches("(.*?)\\| B \\|(.*?)") )
            {
                teamColour.setBackgroundColor(leftTeam.getColour());
            }

            if (currentEventInfo[2].equals("true")) flag.setVisibility(View.VISIBLE);
            else flag.setVisibility(View.INVISIBLE);

            eventTime.setText(currentEventInfo[0]);
            eventDescription.setText(displayed);

            return view;
        }

        public void remakeActionList(){
            if (isFriendly) return;
            ArrayList<String> newList = new ArrayList<>();
            for (DataLiveActions event: liveActionsList){
                newList.add(event.getAction());
            }
            matchDTO.setActionsLive(newList);
            updateActionList();
        }
    }




}
