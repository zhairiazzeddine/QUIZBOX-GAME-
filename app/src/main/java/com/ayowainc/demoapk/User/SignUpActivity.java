package com.ayowainc.demoapk.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
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

public class SignUpActivity extends AppCompatActivity {

    //Variables
    TextView txtAlreadyAUser;
    TextInputLayout textInputLayout_Email, textInputLayout_Password, textInputLayout_UserName, textInputLayout_FullName;
    Button SignUp_btn;
    LottieAnimationView lottieAnimationView;
    FirebaseAuth firebaseAuth;


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Hooks
        textInputLayout_FullName = findViewById(R.id.reg_FullName);
        textInputLayout_UserName = findViewById(R.id.reg_UserName);
        textInputLayout_Email = findViewById(R.id.reg_Email);
        textInputLayout_Password = findViewById(R.id.reg_PassWord);
        SignUp_btn = findViewById(R.id.reg_SignUp);
        txtAlreadyAUser = findViewById(R.id.login_tv);
        lottieAnimationView = findViewById(R.id.Regloading);
        firebaseAuth = FirebaseAuth.getInstance();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        txtAlreadyAUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);

            }

        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean validateName() {
        String val = Objects.requireNonNull(textInputLayout_FullName.getEditText()).getText().toString();

        if (val.isEmpty()) {
            textInputLayout_FullName.setError("Field cannot be empty");
            return false;
        } else {
            textInputLayout_FullName.setError(null);
            textInputLayout_FullName.setErrorEnabled(false);
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean validateUserName() {
        String val = Objects.requireNonNull(textInputLayout_UserName.getEditText()).getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            textInputLayout_UserName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            textInputLayout_UserName.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            textInputLayout_UserName.setError("White Spaces are not allowed");
            return false;
        } else {
            textInputLayout_UserName.setError(null);
            textInputLayout_UserName.setErrorEnabled(false);
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean validatePassword() {
        String val = Objects.requireNonNull(textInputLayout_Password.getEditText()).getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            textInputLayout_Password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            textInputLayout_Password.setError("Password is too weak");
            return false;
        } else {
            textInputLayout_Password.setError(null);
            textInputLayout_Password.setErrorEnabled(false);
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean validateEmail() {
        String val = Objects.requireNonNull(textInputLayout_Email.getEditText()).getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            textInputLayout_Email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            textInputLayout_Email.setError("Invalid email address");
            return false;
        } else {
            textInputLayout_Email.setError(null);
            textInputLayout_Email.setErrorEnabled(false);
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void signup(View view) {


        if (!validateName() | !validatePassword() | !validateEmail() | !validateUserName())//validate when sign up button is clicked
        {
            return;
        }

        //Get all the values
        final String fullname = Objects.requireNonNull(textInputLayout_FullName.getEditText()).getText().toString();
        final String username = Objects.requireNonNull(textInputLayout_UserName.getEditText()).getText().toString();
        final String email = Objects.requireNonNull(textInputLayout_Email.getEditText()).getText().toString();
        final String password = Objects.requireNonNull(textInputLayout_Password.getEditText()).getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    assert user != null;
                    String Email = user.getEmail();
                    String uid = user.getUid();

                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("fullname", fullname);
                    hashMap.put("email", Email);
                    hashMap.put("password", password);
                    hashMap.put("username", username);
                    hashMap.put("image", "");
                    hashMap.put("uid", uid);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("The QuizBoxers");
                    reference.child(uid).setValue(hashMap);


                    lottieAnimationView.setVisibility(View.VISIBLE);
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(SignUpActivity.this, "verification email sent to " + email, Toast.LENGTH_LONG).show();
                                Intent emailVerify = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(emailVerify);

                            } else {
                                Toast.makeText(SignUpActivity.this, "Check Internet Connection" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    lottieAnimationView.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Check Internet Connection" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
