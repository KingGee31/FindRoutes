package com.example.findroutes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class Done extends AppCompatActivity {
    // Declaration
    TextView date,score;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        date=(TextView)findViewById(R.id.textView15);
        ok=(Button)findViewById(R.id.button2);
score=(TextView)findViewById(R.id.textView13);
        // date string
        String NowTime=java.text.DateFormat.getDateTimeInstance().format(new Date());
        date.setText("Time Completed:"+ NowTime);
        // getting results score
        Intent mIntent=getIntent();
        int Score= mIntent.getIntExtra("score",0);

        // parsing score to score textview
        score.setText("Score:"+Score);
        // setting action listener to ok button
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // calling a method to navigate back to menu
                NavigateToMenu();

            }
        });


    }
    public void NavigateToMenu()
    {
        Intent intent= new Intent(this,Menu.class);
        startActivity(intent);

    }
}