package com.example.trivia_game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author khaleel esa
 * the sign up activity here a new user can signup
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText emailField,passwordField;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView loginTxtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*setting the layout visibility*/
        LinearLayout linearlayout_game=(LinearLayout)findViewById(R.id.game_layout);
        LinearLayout linearlayout_Signup=(LinearLayout)findViewById(R.id.SignUpLayout);
        LinearLayout linearlayout_login=(LinearLayout)findViewById(R.id.loginLayout);
        LinearLayout linearlayout_main=(LinearLayout)findViewById(R.id.main_layout);
        LinearLayout linearlayout_shaps=(LinearLayout)findViewById(R.id.genral_layout);
        LinearLayout linearlayout_flags=(LinearLayout)findViewById(R.id.flags_layout);
        LinearLayout linearlayout_animals=(LinearLayout)findViewById(R.id.animals_layout);
        LinearLayout linearlayout_admin=(LinearLayout)findViewById(R.id.admin_layout);
        linearlayout_admin.setVisibility(View.INVISIBLE);
        linearlayout_main.setVisibility(View.INVISIBLE);
        linearlayout_game.setVisibility(View.INVISIBLE);
        linearlayout_flags.setVisibility(View.INVISIBLE);
        linearlayout_animals.setVisibility(View.INVISIBLE);
        linearlayout_shaps.setVisibility(View.INVISIBLE);
        linearlayout_Signup.setVisibility(View.VISIBLE);
        linearlayout_login.setVisibility(View.INVISIBLE);

        loginTxtView = (TextView)findViewById(R.id.loginTxtView);
        emailField = (EditText)findViewById(R.id.emailField);
        passwordField = (EditText)findViewById(R.id.passwordField);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    /* if the user already signed up he can login */
    public void regtologin (View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void SignUp (View view) {
        String emailID = emailField.getText().toString();
        String paswd = passwordField.getText().toString();
        if (emailID.isEmpty()) {
            emailField.setError("Provide your Email first!");
            emailField.requestFocus();
        } else if (paswd.isEmpty()) {
            passwordField.setError("Set your password");
            passwordField.requestFocus();
        } else if (emailID.isEmpty() && paswd.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
        } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
            mAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registeration Succesful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
                        intent.putExtra("message", emailField.getText().toString() );
                        startActivity(intent);
                    }
                }
            });
        } else {
            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    }
