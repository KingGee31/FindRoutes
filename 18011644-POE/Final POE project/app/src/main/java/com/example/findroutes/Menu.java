package com.example.findroutes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {
    // Declaring variables to use
    Button Game,Map,trackD;
    String Email;
    TextView uName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // getting users email from previous activity


        // initializing Variables
        uName=(TextView)findViewById(R.id.textView11);
        Game=(Button)findViewById(R.id.Game_btn);
        Map=(Button)findViewById(R.id.Map_btn);
        trackD=(Button)findViewById(R.id.Distance_btn);

        Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
// calling a method to navigate to quiz game
NavigateToGame();
            }
        });
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // calling a method to navigate to map
                NavigateToMap();
            }
        });
        trackD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this,TrackRoutes.class);
                startActivity(intent);
            }
        });
    }
    public void NavigateToMap()
    {
        Intent intent= new Intent(this,MapActivity.class);

        startActivity(intent);

    }
    public void NavigateToGame()
    {
       Intent intent= new Intent(this,Quiz_Q1.class);
       startActivity(intent);

    }

}