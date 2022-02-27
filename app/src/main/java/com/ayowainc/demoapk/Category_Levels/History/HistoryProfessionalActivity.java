package com.ayowainc.demoapk.Category_Levels.History;

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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryProfessionalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn, ShareQue_btn, Next_btn;
    LinearLayout content, options_Container;
    TextView Questions, questions_no_indicator;
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

        //All Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        content = findViewById(R.id.main_content);
        options_Container = findViewById(R.id.options_layout);
        Questions = findViewById(R.id.question_view);
        questions_no_indicator = findViewById(R.id.no_of_questions_view);
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

        dialog = new Dialog(this, R.style.AnimateDialog);


        list = new ArrayList<>();

        ///////////////////////////////////////////////////////////////////// ADD YOUR QUESTIONS HERE FOR HISTORY PROFESSIONAL CATEGORY////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////PROFESSIONAL HISTORY QUESTIONS////////////////////////////////////////////////////////////////////////////////////////
        list.add(new questionsModelClass("In which year did the United States enter the First World War?", "1917", "1920", "1921", "1918", "1917"));
        list.add(new questionsModelClass("Which new British military force was established in 1918?", "FRA", "RAF", "ARF", "FAR", "RAF"));
        list.add(new questionsModelClass("The German attack on which country caused Britain to enter the Second World War?", "Ghana", "Portugal", "Poland", "France", "Poland"));
        list.add(new questionsModelClass(" According to Churchill, he had nothing to offer in 1940 but what?", "Blood, toil, tears, and sweat", "Blood, money, tears, and sweat", "Blood, toil, tears, and houses", "horses, toil, tears, and sweat", "Blood, toil, tears, and sweat"));
        list.add(new questionsModelClass("Which member of the British royal family was murdered by IRA in 1979?", "Lord Martin", "Lord Brown", "Lord Mountbatten", "Lord Mountle", "Lord Mountbatten"));
        list.add(new questionsModelClass("Golda Mer was the Prime Minister of which country from 1970-1974?", "Israel", "India", "Iraq", "Colombia", "Israel"));
        list.add(new questionsModelClass("What was the meaning of Mahatma Gandhi’s message ‘Satyagraha’?", "violent message", "War message", "poverty message", "Non-violent message", "Non-violent message"));
        list.add(new questionsModelClass("Whose presidency was known a ‘’ the businessman’s administration’’?", "Eisenhower", "Barack Obama", "Golda Mer", "Bill Clinton", "Eisenhower"));
        list.add(new questionsModelClass("In the 1970s, because of the Watergate scandal, who became the first Us attorney general to serve prison time?", "John Mills", "Nelson Mandela", "John Mitchell", "George Bush", "John Mitchell"));
        list.add(new questionsModelClass("In which year was Margaret Thatcher first elected Prime Minister in Britain?", "1979", "1980", "1981", "1990", "1979"));
        list.add(new questionsModelClass("Who was president when the USA entered the first World War?", "Bush", "Coolidge", "Clinton", "Obama", "Coolidge"));
        list.add(new questionsModelClass("In which decade was London’s ‘Crystal Palace’ destroyed by fire?", "1960’s", "1950’s", "1920’s", "1930’s", "1930’s"));
        list.add(new questionsModelClass("Which two African politicians jointly won the Nobel Peace Prize in 1993?", "Nelson Mandela and Fredrick W de Klerk", "Nelson Mandela and Kwame Nkrumah", "Nelson Mandela and Robert Mugabe", "Nelson Mandela and Goodluck Jonathan", "Nelson Mandela and Fredrick W de Klerk"));
        list.add(new questionsModelClass("Which war ended in 1902 with the treaty of Vereeniging?", "The Vietinam War", "The Great War", "The Boer War", "The Yaa Asantewa War", "The Boer War"));
        list.add(new questionsModelClass("Who became President of the Fifth Republic on 8th January 1959?", "Kwame Nkrumah", "Charles De Gaulle", "Nelson Mandela", "Barrack Obama", "Charles De Gaulle"));
        list.add(new questionsModelClass("Who, in 1963 said: ‘’ Let us seek to satisfy our thirst for freedom by drinking from the cup of hatred and bitterness’’?", "Martin Luther King", "Shakespear", "Davinci", "Nelson Mandela", "Martin Luther King"));
        list.add(new questionsModelClass("In which state was Henry Ford born in 1863? It was also the state in which he died in 1947.", "Toronto", "New York", "Michigan", "Oklahoma", "Michigan"));
        list.add(new questionsModelClass("Which two countries signed a peace agreement at Camp David in 1978?", "Egypt and Israel", "Egypt and Ghana", "Ghana and Israel", "Egypt and South Africa", "Egypt and Israel"));
        list.add(new questionsModelClass("Who was president of the USA throughout World War One?", "Coolidge", "Woodrow Wilson", "Kwame Nkrumah", "Bill Clinton", "Woodrow Wilson"));
        list.add(new questionsModelClass("The Treaty of Panmunjom ended which war?", "Japan War", "Vietnam War", "China War", "Korean war", "Korean war"));


        for (int i = 0; i < 4; i++) {
            options_Container.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAns((Button) v);
                }
            });
        }


        questions_no_indicator.setText(position + 1 + "/" + list.size());

        playAnim(Questions, 0, list.get(position).getQuestions());
        Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;
                if (position == list.size()) {

                    //Score Activities
                    if (score <= 7) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_fail_20_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), HistoryProfessionalActivity.class); //If User get 20% let him or her play again
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

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 12) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_pass_50_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_lose.start();

                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), HistoryProfessionalActivity.class); ///If User get 50% let him or her play again
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

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 19) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_70_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), HistoryProfessionalActivity.class); ///If User get 70% let him to next level
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

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score == 20) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_100_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);

                        level_won.start();

                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), HistoryProfessionalActivity.class); ///If User get 100% promote him or her to next level
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


                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.onBackPressed();
                        dialog.show();
                    }
                    return;
                }

                count = 0;
                playAnim(Questions, 0, list.get(position).getQuestions());
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
                    playAnim(options_Container.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        questions_no_indicator.setText(position + 1 + "/" + list.size());
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
            Button correctOption = options_Container.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));

            mp.start();
        }
    }

    private void enableOptions(boolean enable) {
        for (int i = 0; i < 4; i++) {
            options_Container.getChildAt(i).setEnabled(enable);
            if (enable) {
                options_Container.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2133A0")));
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
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth()*slideOffset;
                final float xOffsetDiff = content.getWidth()*diffScaledOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);

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
            HistoryProfessionalActivity.super.onBackPressed();

        } else if (menuItem.getItemId() == R.id.rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=")));
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elifcoding.blogspot.com")));
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

