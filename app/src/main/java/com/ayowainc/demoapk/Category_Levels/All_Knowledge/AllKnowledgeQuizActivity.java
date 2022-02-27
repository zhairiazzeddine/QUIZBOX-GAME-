package com.ayowainc.demoapk.Category_Levels.All_Knowledge;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ayowainc.demoapk.MenuHomeScreenActivity;
import com.ayowainc.demoapk.R;
import com.ayowainc.demoapk.User.LoginActivity;
import com.ayowainc.demoapk.User.UserProfileActivity;
import com.ayowainc.demoapk.questionsModelClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllKnowledgeQuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn, ShareQue_btn, Next_btn;
    LinearLayout linearLayout, linearLayout1;
    TextView txtQuestions, txtnumberIndicator;
    Dialog dialog;
    private int count = 0;
    private int position = 0;
    private List<questionsModelClass> list;
    private int score = 0;
    private InterstitialAd mInterstitialAd;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_questions_view);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        ////Google ads- interstatial Integration-------------------///////////////
        MobileAds.initialize(this, getString(R.string.admob_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });


        //All Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout = findViewById(R.id.main_content);
        linearLayout1 = findViewById(R.id.options_layout);
        txtQuestions = findViewById(R.id.question_view);
        txtnumberIndicator = findViewById(R.id.no_of_questions_view);
        ShareQue_btn = findViewById(R.id.share_que_btn);
        Next_btn = findViewById(R.id.next_btn);


        final MediaPlayer level_lose = MediaPlayer.create(this, R.raw.level_lose);///Play sound when user loses level
        final MediaPlayer level_won = MediaPlayer.create(this, R.raw.applause_wav);///Play sound when user wins level

        ShareQue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = list.get(position).getQuestions() + "\n" +
                        "A :" + " " + list.get(position).getOptionA() + "\n" +
                        "B :" + " " + list.get(position).getOptionB() + "\n" +
                        "C :" + " " + list.get(position).getOptionC() + "\n" +
                        "D :" + " " + list.get(position).getOptionD();
                String shareSub = "Your subject here";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        dialog = new Dialog(this, R.style.AnimateDialog);

        ///////////////////////////////////////////////////////////////////// ADD YOUR QUESTIONS HERE FOR ALL KNOWLEDGE CATEGORY//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////ALL KNOWLEDGE QUESTIONS////////////////////  70 QUETIONS  ////////////////////////////////////////////////////////////////////
        list = new ArrayList<>();
        list.add(new questionsModelClass("Carl and the Passions changed band name to what", "Beach Boys", "Bad Boys", "City Boys", "CarlP Band", "Beach Boys"));
        list.add(new questionsModelClass("How many rings on the Olympic flag", "5", "4", "6", "7", "5"));
        list.add(new questionsModelClass("What colour is vermilion a shade of", "red", "green", "blue", "yellow", "red"));
        list.add(new questionsModelClass("King Zog ruled which country", "Germany", "Albania", "Ghana", "Uganda", "Albania"));
        list.add(new questionsModelClass("What colour is Spock's blood", "Green", "red", "yellow", "blue", "Green"));
        list.add(new questionsModelClass("Where in your body is your patella", "Knee", "Shoulder", "Head", "Leg", "Knee"));
        list.add(new questionsModelClass("What spirit is mixed with ginger beer in a Moscow mule", "Bellaire", "Beer", "Vodka", "Coke", "Vodka"));
        list.add(new questionsModelClass("Who betrayed Jesus to the Romans", "Satan", "John", "Peter", "Judas Escariot", "Judas Escariot"));
        list.add(new questionsModelClass("On television what was Flipper", "Shark", "Lizard", "Dolphin", "Snake", "Dolphin"));
        list.add(new questionsModelClass("In the song Waltzing Matilda - What is a Jumbuck", "Goat", "Dog", "Sheep", "Chicken", "Sheep"));
        list.add(new questionsModelClass("How many feet in a fathom", "Six", "five", "seven", "four", "Six"));
        list.add(new questionsModelClass("which film had song Springtime for Hitler", "the crew", "the actors", "the editors", "the producers", "the producers"));
        list.add(new questionsModelClass("Name the legless fighter pilot of World War 2", "Douglas Baden", "Douglas Bader", "Douglas Badain", "Douglas Bador", "Douglas Bader"));
        list.add(new questionsModelClass("What does ring a ring a roses refer to", "The Black Thief", "The Black Sheep", "The Black Sea", "The Black Death", "The Black Death"));
        list.add(new questionsModelClass("Whose nose grew when he told a lie", "Mickey Mouse", "Pinocchio", "Super Mario", "Spiderman", "Pinocchio"));
        list.add(new questionsModelClass("Which award has the words for valour on it", "Victoria Cross", "the oscars", "bet award", "grammy award", "Victoria Cross"));
        list.add(new questionsModelClass("If you had pogonophobia what would you be afraid of", "Rain", "Beards", "blood", "ring", "Beards"));
        list.add(new questionsModelClass("What in business terms is the IMF", "International Monetary Fine", "International Monetary Fund", "Intentional Monetary Fund", "International Monetary Film", "International Monetary Fund"));
        list.add(new questionsModelClass("Which country grows the most fruit", "China", "Brazil", "England", "Japan", "China"));
        list.add(new questionsModelClass("Which company is owned by Bill Gates", "Nissan Motors", "Facebook", "Amazon", "Microsoft", "Microsoft"));
        list.add(new questionsModelClass("What kind of animal is a lurcher", "Dog", "Hen", "Snake", "Bird", "Dog"));
        list.add(new questionsModelClass("What is the currency of Austria", "Dollar", "Schilling", "Pounds Sterling", "Cedis", "Schilling"));
        list.add(new questionsModelClass("How did Alfred Nobel make his money", "He invented Dynamite", "He invented Cars", "He invented Bulb", "He invented Airplane", "He invented Dynamite"));
        list.add(new questionsModelClass("Which car company makes the Celica", "Opel", "Mercedes Benz", "Ford", "Toyota", "Toyota"));
        list.add(new questionsModelClass("Which country had The Dauphin as a ruler", "France", "England", "Spain", "Poland", "France"));
        list.add(new questionsModelClass("Which country had the guns of Naverone installed", "France", "Turkey", "Libya", "Iraq", "Turkey"));
        list.add(new questionsModelClass("Ictheologists study what", "Dogs", "Turkey", "Fish", "Tigers", "Fish"));
        list.add(new questionsModelClass("Who or what lives in a formicarium", "Scorpion", "Ants", "Snake", "Lizard", "Ants"));
        list.add(new questionsModelClass("What links - Goa - Kerula - Assam - Bihar", "India", "Bangladesh", "Japan", "Singapore", "India"));
        list.add(new questionsModelClass("Which country do Sinologists study", "India", "China", "Japan", "Singapore", "China"));
        list.add(new questionsModelClass("What is Orchesis - either professional or amateur", "Art of Dancing", "Art of Killing", "Art of Stealing", "None of thesess", "Art of Dancing"));
        list.add(new questionsModelClass("Taken literally what should you see in a HippodromeHorses", "Dogs", "Sheeps", "Dolphins", "Horses", "Horses"));
        list.add(new questionsModelClass("What 1991 film won best film, actor, actress, director Oscars", "Bad Boys", "James Bond", "Silence of the Lambs", "Batman", "Silence of the Lambs"));
        list.add(new questionsModelClass("What was the capital of Ethiopia", "Accra", "Addis Ababa", "Cairo", "Abuja", "Addis Ababa"));
        list.add(new questionsModelClass("Giacomo Agostini - 122 Grand Prix 15 world titles what sport", "Football", "Swimming", "Motorcycle Racing", "Javlin Throwing", "Motorcycle Racing"));
        list.add(new questionsModelClass("What is the largest state in the USA", "Oklahoma", "Alaska", "Ohio", "New York", "Alaska"));
        list.add(new questionsModelClass("Led Deighton trilogy Game Set Match What 3 Capitals", "Berlin Mexico London", "Berlin Mexico Paris", "Berlin Mexico Madrid", "Rome Mexico London", "Berlin Mexico London"));
        list.add(new questionsModelClass("Whose autobiography was The long walk to Freedom", "Kwame Nkrumah", "Bill Clinton", "Nelson Mandela", "George Bush", "Nelson Mandela"));
        list.add(new questionsModelClass("Clyde Tonbaugh discovered what planet in 1930", "Mars", "Saturn", "Mercury", "Pluto", "Pluto"));
        list.add(new questionsModelClass("Who ran through the streets naked crying Eureka", "John Dalton", "Archimedes", "Isaac Newton", "Shakespear", "Archimedes"));
        list.add(new questionsModelClass("Who won the World Series in 1987", "Ohio twins", "New York twins", "Alaska twins", "Minnesota twins", "Minnesota twins"));
        list.add(new questionsModelClass("In the Old Testament what book comes between Obadiah - Micah", "Jonah", "Ruth", "Esther", "Revelation", "Jonah"));
        list.add(new questionsModelClass("What was the world’s first high level programming language 1957", "Python", "IBM FORTRAN", "C++", "Java", "IBM FORTRAN"));
        list.add(new questionsModelClass("Consumption was the former name of which disease", "Tuberculosis", "HIV/AIDS", "COVID-19", "Malaria", "Tuberculosis"));
        list.add(new questionsModelClass("If someone said they were from Hellas - which country?", "Greece", "Ghana", "Germany", "Guinea Bissau", "Greece"));
        list.add(new questionsModelClass("Portugal has had six Kings with what first name", "Daniel", "Peter", "John", "Isaac", "John"));
        list.add(new questionsModelClass("Kimberlite contains what precious item", "Gold", "Crude oil", "Bauxite", "Diamonds", "Diamonds"));
        list.add(new questionsModelClass("What animal lives in a drey", "Snake", "Squirrel", "Goat", "Ants", "Squirrel"));
        list.add(new questionsModelClass("Where in France do claret wines come from", "Bordeux", "Paris", "Lille", "Monaco", "Bordeux"));
        list.add(new questionsModelClass("Which country set up the world’s first chemistry lab in 1650", "China", "United State Of America", "Netherlands", "Russia", "Netherlands"));
        list.add(new questionsModelClass("What animals name translates as water horse", "Hippopotamus", "Elephant", "Tilapia", "Octopus", "Hippopotamus"));
        list.add(new questionsModelClass("In 1643 Evangalisa Torichelli invented the first what", "Eureka Can", "Bomb", "Thermometer", "Barometer", "Barometer"));
        list.add(new questionsModelClass("What links Brazil, Uruguay, Mozambique and Angola", "Colonies of Portugal", "Colonies of Spain", "Colonies of France", "Colonies of Germany", "Colonies of Portugal"));
        list.add(new questionsModelClass("What people founded cheese making in England", "Chinese", "English", "Romans", "Germans", "Romans"));
        list.add(new questionsModelClass("What colour is the flesh of the Charentais melon", "Orange", "Green", "Red", "Brown", "Orange"));
        list.add(new questionsModelClass("Which country grows the most potatoes", "Russia", "Ukraine", "India", "China", "Russia"));
        list.add(new questionsModelClass("What countries people had the longest life expectation", "Iceland", "Poland", "Slovenia", "Serbia", "Iceland"));
        list.add(new questionsModelClass("In which country are you most likely to die from a scorpion sting", "Mexico", "Argentina", "Colombia", "Peru", "Mexico"));
        list.add(new questionsModelClass("What was invented by Dr Albert Southwick in 1881", "Electric chair", "Machine Gun", "Tank", "C4", "Electric chair"));
        list.add(new questionsModelClass("What was made illegal in England in 1439", "Sleeping", "Smoking", "Alcohol intake", "Kissing", "Kissing"));
        list.add(new questionsModelClass("In what month did the Russian October revolution take place", "December", "November", "September", "June", "November"));
        list.add(new questionsModelClass("Which food did Victorians deride as little bags of mystery", "Rice", "Tea", "Sausage", "Pizza", "Sausage"));
        list.add(new questionsModelClass("In the human body where is your occiput", "Back of head", "Spinal Cord", "Knee", "Heart", "Back of head"));
        list.add(new questionsModelClass("In which city was Bob Hope born", "Madrid", "Alaska", "Monaco", "London", "London"));
        list.add(new questionsModelClass("Which country had the first women MPs 19 in 1907", "Finland", "Sao Tome Principal", "Poland", "Netherlands", "Finland"));
        list.add(new questionsModelClass("Rene Laennac invented which aid for doctors in 1810", "None of these", "Thermometer", "IV tube", "Stethoscope", "Stethoscope"));
        list.add(new questionsModelClass("Paul Robeson the singer of old man river had what profession", "Musician", "Lawyer", "Nurse", "Doctor", "Lawyer"));
        list.add(new questionsModelClass("What is the staple food of one third of the worlds population", "Rice", "Pizza", "Hot dogs", "Coffe and Tea", "Rice"));
        list.add(new questionsModelClass("In 1901 which brand of car was seen for the first time", "Toyota", "Nissan", "Ford", "Mercedes", "Mercedes"));
        list.add(new questionsModelClass("Barry Allen was the alter ego of which DC comic superhero", "Amazing SpiderMan", "Superman", "The Flash", "Batman", "The Flash"));


        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAns((Button) v);
                }
            });
        }

        txtnumberIndicator.setText(position + 1 + "/" + list.size());

        playAnim(txtQuestions, 0, list.get(position).getQuestions());
        Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;
                if (position == list.size()) {
                    //Score Activities
                    if (score <= 30) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_fail_20_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), AllKnowledgeQuizActivity.class); //If User get 20% let him or her play again
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 50) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_pass_50_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), AllKnowledgeQuizActivity.class); ///If User get 50% let him or her play again
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 69) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_70_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MenuHomeScreenActivity.class); ///If User get 70% let him to next category
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score == 70) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_100_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MenuHomeScreenActivity.class); ///If User get 100% promote him or her to next category
                                startActivity(BG);
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });


                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    }
                    return;
                }

                count = 0;
                playAnim(txtQuestions, 0, list.get(position).getQuestions());
            }
        });

        navigationDrawer();
    }
    ///////////////////////////////////////////////////////////////////ANIMATING SCREEN/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }
                    playAnim(linearLayout1.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        txtnumberIndicator.setText(position + 1 + "/" + list.size());
                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);


                    playAnim(view, 1, data);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void checkAns(Button selectedOptions) {
        enableOptions(false);
        Next_btn.setEnabled(true);
        Next_btn.setAlpha(1);
        if (selectedOptions.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            //correct Answer
            score++;
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.ding);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));
            mp.start();
        } else {
            //wrong Answer
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong_buzzer);
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF2B55")));
            Button correctOption = linearLayout1.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));

            mp.start();
        }
    }

    private void enableOptions(boolean enable) {
        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout1.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2133A0")));
            }
        }
    }


    ///////////////////////////////////////////////////////////////////ALL ABOUT NAVIGATION DRAWER/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        navToggler_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    ////////////////////////////////////////////////////////////ANIMATE NAV DRAWER////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.cat_heading));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset*(1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                linearLayout.setScaleX(offsetScale);
                linearLayout.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth()*slideOffset;
                final float xOffsetDiff = linearLayout.getWidth()*diffScaledOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                linearLayout.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.home) {
            Intent home = new Intent(getApplicationContext(), MenuHomeScreenActivity.class);
            startActivity(home);
            AllKnowledgeQuizActivity.super.onBackPressed();

        } else if (menuItem.getItemId() == R.id.rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=")));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thedonuttech.tk")));
            }
        } else if (menuItem.getItemId() == R.id.user_profile) {
            Intent user_profile = new Intent(getApplicationContext(), UserProfileActivity.class);
            startActivity(user_profile);
        } else if (menuItem.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

