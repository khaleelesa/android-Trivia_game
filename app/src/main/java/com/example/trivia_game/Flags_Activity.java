package com.example.trivia_game;

import android.content.DialogInterface;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author khaleel esa
 * this is the flags trivia activity here we assgine the quations of this trivia using an array of images and buttons
 */
public class Flags_Activity extends AppCompatActivity {

    private TextView countLabel1,timer;
    private ImageView questionImage1;
    private Button answerBtn11;
    private Button answerBtn22;
    private Button answerBtn33;
    private Button answerBtn44;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    private String email;
    private DatabaseReference mDatabase;
   private MediaPlayer mp_p,mp_c,mp_nc,mp_r ;
    private CountDownTimer count;


    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"Image Name", "Right Answer", "Choice1", "Choice2", "Choice3"}
            {"image_german", "germany", "egypt", "united states", "italy"},
            {"image_uk",  "united kingdom","turkey","united states", "japan"},
            {"image_japan", "japan" ,"china", "germany","italy","united states"},
            {"image_usa",   "united states", "germany","united kingdom", "italy"},
            {"image_uk",  "united kingdom", "italy","united states", "germany" },
    };

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
        linearlayout_game.setVisibility(View.INVISIBLE);
        linearlayout_flags.setVisibility(View.VISIBLE);
        linearlayout_animals.setVisibility(View.INVISIBLE);
        linearlayout_shaps.setVisibility(View.INVISIBLE);
        linearlayout_Signup.setVisibility(View.INVISIBLE);
        linearlayout_login.setVisibility(View.INVISIBLE);

        countLabel1 = findViewById(R.id.countLabel1);
        questionImage1 = findViewById(R.id.questionImage1);
        timer = findViewById(R.id.timer1);
        answerBtn11 = findViewById(R.id.answerBtn11);
        answerBtn22 = findViewById(R.id.answerBtn22);
        answerBtn33 = findViewById(R.id.answerBtn33);
        answerBtn44 = findViewById(R.id.answerBtn44);

        countdown();
        // Create quizArray from quizData.
        for (int i = 0; i < quizData.length; i++) {
            // Prepare array.
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); // Image Name
            tmpArray.add(quizData[i][1]); // Right Answer
            tmpArray.add(quizData[i][2]); // Choice1
            tmpArray.add(quizData[i][3]); // Choice2
            tmpArray.add(quizData[i][4]); // Choice3

            // Add tmpArray to quizArray.
            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }

    public void showNextQuiz() {

        // Update quizCountLabel.
        countLabel1.setText("Q" + quizCount);

        // Generate random number between 0 and 4 (quizArray's size -1)
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // Pick one quiz set.
        ArrayList<String> quiz = quizArray.get(randomNum);

        // Set Image and Right Answer.
        // Array format: {"Image Name", "Right Answer", "Choice1", "Choice2", "Choice3"}
        questionImage1.setImageResource(
                getResources().getIdentifier(quiz.get(0), "drawable", getPackageName()));
        rightAnswer = quiz.get(1);

        // Remove "Image Name" from quiz and shuffle choices.
        quiz.remove(0);
        Collections.shuffle(quiz);

        // Set choices.
        answerBtn11.setText(quiz.get(0));
        answerBtn22.setText(quiz.get(1));
        answerBtn33.setText(quiz.get(2));
        answerBtn44.setText(quiz.get(3));

        // Remove this quiz from quizArray.
        quizArray.remove(randomNum);

    }

    //coutdown timer
    public void countdown() {
        mp_p=MediaPlayer.create(getApplicationContext(), R.raw.countdown_boom);
        mp_p.start();
        count = new CountDownTimer(21000, 1000) {
            public void onTick(long millisUntilFinished) {

                timer.setText("tic toc : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("done!");
                showResult();
            }
        }.start();

    }

    public void checkAnswer(View view) {
         mp_c = MediaPlayer.create(getApplicationContext(), R.raw.correct_answer);
         mp_nc = MediaPlayer.create(getApplicationContext(), R.raw.wrong_answer);
        // Get pushed button.
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // Correct!!
            alertTitle = "Correct!";
            rightAnswerCount++;

        } else {
            // Wrong
            alertTitle = "Wrong...";
        }

        // Create Dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        if(alertTitle == "Correct!"){
            mp_c.start();
            builder.setIcon(R.drawable.correct);
        }

        else{
            mp_nc.start();
            builder.setIcon(R.drawable.worng);
        }

        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizArray.size() < 1) {
                    // quizArray is empty.
                    count.cancel();
                    mp_p.stop();
                    showResult();

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void showResult() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("userScores").child("Flages_Scores");
        Intent intent = getIntent();
        email = intent.getStringExtra("message");
        Score user_score=new Score(rightAnswerCount,email);
        String beforealt = email.split("\\@")[0];
        mDatabase.child(beforealt).setValue(user_score);
         mp_r = MediaPlayer.create(getApplicationContext(), R.raw.shortapplause);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setIcon(R.drawable.result);
        builder.setMessage(rightAnswerCount + " / 5");
        mp_r.start();


        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recreate();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Flags_Activity.this, GameActivity.class);
                intent.putExtra("message", email);
                startActivity(intent);
            }
        });
        builder.show();
    }

    public void onBackPressed(){
        count.cancel();
        mp_p.stop();
        Intent intent = new Intent(Flags_Activity.this, GameActivity.class);
        intent.putExtra("message", email);
        startActivity(intent);
    }
}
