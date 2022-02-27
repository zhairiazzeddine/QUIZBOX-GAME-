package com.ayowainc.demoapk.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.ayowainc.demoapk.MenuHomeScreenActivity;
import com.ayowainc.demoapk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ImageView app_imageView;
    TextView txtWelcome, txtHelloThere, txtSignUpToContinue, txtNewUser;
    TextInputLayout textInputLayout_Email, textInputLayout_Password;
    Button Login_btn;
    LottieAnimationView lottieAnimationView;
    Dialog dialog;
    FirebaseAuth firebaseAuth;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Hooks
        Login_btn = findViewById(R.id.go_btn);
        txtNewUser = findViewById(R.id.signup_tv);
        app_imageView = findViewById(R.id.signin_logo);
        txtWelcome = findViewById(R.id.welc_txt);
        txtHelloThere = findViewById(R.id.hello_txt);
        txtSignUpToContinue = findViewById(R.id.signin_to_continue_txt);
        textInputLayout_Email = findViewById(R.id.email);
        textInputLayout_Password = findViewById(R.id.password);
        lottieAnimationView = findViewById(R.id.Logloading);
        firebaseAuth = FirebaseAuth.getInstance();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                //open sign up activity
                startActivity(signUpActivity);

            }

        });

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified()) {
            Intent loginActivity = new Intent(getApplicationContext(), MenuHomeScreenActivity.class);//Take me back to login screen when sign up is successfull
            startActivity(loginActivity);
        }

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validate Login Info
                if (!ValidateEmail() | !validatePassword()) {
                    return;
                }

                final String email = Objects.requireNonNull(textInputLayout_Email.getEditText()).getText().toString();
                final String password = Objects.requireNonNull(textInputLayout_Password.getEditText()).getText().toString();

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            if (Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getAdditionalUserInfo()).isNewUser()) {

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String Email = user.getEmail();
                                String uid = user.getUid();

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("fullname", "");
                                hashMap.put("email", Email);
                                hashMap.put("password", password);
                                hashMap.put("username", "");
                                hashMap.put("image", "");
                                hashMap.put("uid", uid);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("The QuizBoxers");
                                reference.child(uid).setValue(hashMap);

                            }

                            if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                lottieAnimationView.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, "I wish you LUCK!", Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MenuHomeScreenActivity.class);
                                startActivity(mainIntent);

                            } else if (!firebaseAuth.getCurrentUser().isEmailVerified()) {
                                lottieAnimationView.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Error: Email is not Verified!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
            }

        });

        dialog = new Dialog(this);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Dialog PopUp for Forgot Password Screen setup
    public void forgot_pass(View view) {

        Button Close_btn;
        final TextInputLayout textInputLayoutEmail;
        final Button Verify_btn;
        final FirebaseAuth forgotPassAuth;
        final ProgressBar progressBar_load;

        dialog.setContentView(R.layout.activity_forgot__password);
        Close_btn = dialog.findViewById(R.id.close_fp);
        Verify_btn = dialog.findViewById(R.id.verify);
        textInputLayoutEmail = dialog.findViewById(R.id.fp_email);
        progressBar_load = dialog.findViewById(R.id.pro_gress);
        forgotPassAuth = FirebaseAuth.getInstance();

        Close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ///Button click listener for Verify
        Verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Objects.requireNonNull(textInputLayoutEmail.getEditText()).getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    textInputLayoutEmail.setError("Email Required");
                    return;
                }

                forgotPassAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {///Send Password reset email to user

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar_load.setVisibility(View.VISIBLE);//Load Progress bar
                            progressBar_load.setMax(5000);
                            Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password", Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(mainIntent);

                        } else {
                            progressBar_load.setVisibility(View.GONE);//Hide Progress Bar
                            Toast.makeText(LoginActivity.this, "Failed to send reset email", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////  VALIDATE EMAIL AND PASSWORD  /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Boolean ValidateEmail() {
        String val = Objects.requireNonNull(textInputLayout_Email.getEditText()).getText().toString();
        if (val.isEmpty()) {
            textInputLayout_Email.setError("Field cannot be empty");
            return false;
        } else {
            textInputLayout_Email.setError(null);
            textInputLayout_Email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Objects.requireNonNull(textInputLayout_Password.getEditText()).getText().toString();
        if (val.isEmpty()) {
            textInputLayout_Password.setError("Field cannot be empty");
            return false;
        } else {
            textInputLayout_Password.setError(null);
            textInputLayout_Password.setErrorEnabled(false);
            return true;
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

