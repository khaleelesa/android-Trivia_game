package com.example.trivia_game;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author khaleel esa
 * this is the main triva page where you choose the kind of triviva you want to play
 */
public class GameActivity extends AppCompatActivity {

    public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* here we set the layout visiblity */
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
        linearlayout_game.setVisibility(View.VISIBLE);
        linearlayout_flags.setVisibility(View.INVISIBLE);
        linearlayout_animals.setVisibility(View.INVISIBLE);
        linearlayout_shaps.setVisibility(View.INVISIBLE);
        linearlayout_Signup.setVisibility(View.INVISIBLE);
        linearlayout_login.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
         email = intent.getStringExtra("message");
    }


    public void Go_to_qus (View view) {

        Intent intent = new Intent(GameActivity.this, Genral_Activity.class);
        intent.putExtra("message", email);
        startActivity(intent);
    }

    public void Go_to_flags (View view) {
        Intent intent = new Intent(GameActivity.this, Flags_Activity.class);
        intent.putExtra("message", email);
        startActivity(intent);
    }
    public void Go_to_animals (View view) {

        Intent intent = new Intent(GameActivity.this, Animals_Activity.class);
        intent.putExtra("message", email);
        startActivity(intent);
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}