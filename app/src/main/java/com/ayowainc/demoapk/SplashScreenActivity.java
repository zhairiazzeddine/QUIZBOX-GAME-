package com.ayowainc.demoapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.ayowainc.demoapk.User.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Animation start_animation, splashText_animation, splashText1_animation, logo_animation;
    Button start_btn;
    TextView txtSplashText, txtSplashText1;
    ImageView logo_imageView;
    LottieAnimationView lottieAnimationView;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Animations
        start_animation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);
        splashText_animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left);
        splashText1_animation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right);
        logo_animation = AnimationUtils.loadAnimation(this, R.anim.anim_top);

        //Hooks
        start_btn = findViewById(R.id.start_btn);
        txtSplashText = findViewById(R.id.sp_tv);
        txtSplashText1 = findViewById(R.id.sp_tv1);
        logo_imageView = findViewById(R.id.sp_logo);
        lottieAnimationView = findViewById(R.id.Logloading);

        //Start Animation
        start_btn.setAnimation(start_animation);
        logo_imageView.setAnimation(logo_animation);
        txtSplashText1.setAnimation(splashText1_animation);
        txtSplashText.setAnimation(splashText_animation);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


    }

}
