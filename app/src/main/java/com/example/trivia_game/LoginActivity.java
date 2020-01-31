package com.example.trivia_game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author khaleel esa
 * this is the login page here the user can sign in and start the game
 * if the user does not has an account he can sign up
 */
public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button loginBtn;
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
        linearlayout_Signup.setVisibility(View.INVISIBLE);
        linearlayout_login.setVisibility(View.VISIBLE);

        loginEmail = (EditText)findViewById(R.id.login_email);
        loginPass = (EditText)findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }
     /* checking if the user existing in the database
     * and checks if the user is admin or a normal user*/
    void checksSignedUser(){
        mAuth.fetchSignInMethodsForEmail(loginEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();
                if (task.getResult().getSignInMethods().size() == 0){
                    Toast.makeText(LoginActivity.this, "User not registered!", Toast.LENGTH_SHORT).show();
                }else if(email.equals("admin@gmail.com")&& password.equals("123456"))
                {
                    Intent intent = new Intent(LoginActivity.this, Admin_Activity.class);
                    startActivity(intent);
                }
                else {

                    Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                    intent.putExtra("message", email );
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    /*this is the register hint if the user isnt register yet
    * it will take him to the register file*/
    public void logtoreg (View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    /*the login button wich uses the checksigned user method */
    public void btnLogin (View view) {

        Toast.makeText(LoginActivity.this, "PROCESSING….", Toast.LENGTH_LONG).show();
        String email = loginEmail.getText().toString().trim();
        String password = loginPass.getText().toString().trim();
        if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        checksSignedUser();
                    }else {
                        Toast.makeText(LoginActivity.this, "Couldn’t login, Check email or password", Toast.LENGTH_SHORT).show();
                    } } });
        }else {
            Toast.makeText(LoginActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}