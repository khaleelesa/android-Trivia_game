package com.example.trivia_game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @author khaleel esa
 * this is the admin class where tue admin can see the scores of the signed users
 * to get to the admin page you have to sign in the user admin
 * the admin email: admin@gmail.com , password:123456
 */
public class Admin_Activity extends AppCompatActivity {


    private DatabaseReference Mixed_databaseReference,Flags_databaseReference,Animals_databaseReference;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // here we set the visibility of the admin layout to visible and the other layouts to invisible
        LinearLayout linearlayout_game = (LinearLayout) findViewById(R.id.game_layout);
        LinearLayout linearlayout_Signup = (LinearLayout) findViewById(R.id.SignUpLayout);
        LinearLayout linearlayout_login = (LinearLayout) findViewById(R.id.loginLayout);
        LinearLayout linearlayout_main = (LinearLayout) findViewById(R.id.main_layout);
        LinearLayout linearlayout_shaps = (LinearLayout) findViewById(R.id.genral_layout);
        LinearLayout linearlayout_flags = (LinearLayout) findViewById(R.id.flags_layout);
        LinearLayout linearlayout_animals = (LinearLayout) findViewById(R.id.animals_layout);
        LinearLayout linearlayout_admin = (LinearLayout) findViewById(R.id.admin_layout);
        linearlayout_admin.setVisibility(View.VISIBLE);
        linearlayout_main.setVisibility(View.INVISIBLE);
        linearlayout_game.setVisibility(View.INVISIBLE);
        linearlayout_flags.setVisibility(View.INVISIBLE);
        linearlayout_animals.setVisibility(View.INVISIBLE);
        linearlayout_shaps.setVisibility(View.INVISIBLE);
        linearlayout_Signup.setVisibility(View.INVISIBLE);
        linearlayout_login.setVisibility(View.INVISIBLE);

        //here we get the paths of the childs in the database there are three type of trivia
        Mixed_databaseReference = FirebaseDatabase.getInstance().getReference("userScores/Mixed_Scores");
        Flags_databaseReference = FirebaseDatabase.getInstance().getReference("userScores/Flages_Scores");
        Animals_databaseReference = FirebaseDatabase.getInstance().getReference("userScores/Animals_Scores");
        //here we set the listview and set an adapter to it
        listView = (ListView) findViewById(R.id.records_view);
        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        //here using the addChildEventListener we take the value from the database and put it in the arraylist that we conecct to the listview using the adapter
        Mixed_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(arrayList.contains("-- Mixed_Scores --"))
                {
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
                else {
                    arrayList.add("-- Mixed_Scores --");
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //here using the addChildEventListener we take the value from the database and put it in the arraylist that we conecct to the listview using the adapter
        Flags_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(arrayList.contains("-- Flages_Scores --"))
                {
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
                else {
                    arrayList.add("-- Flages_Scores --");
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //here using the addChildEventListener we take the value from the database and put it in the arraylist that we conecct to the listview using the adapter
        Animals_databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(arrayList.contains("-- Animals_Scores --"))
                {
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
                else {
                    arrayList.add("-- Animals_Scores --");
                    String value = dataSnapshot.getValue(Score.class).toString();
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


    }



    public void onBackPressed(){

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

}