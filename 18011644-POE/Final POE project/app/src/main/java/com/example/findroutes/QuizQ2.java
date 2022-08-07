package com.example.findroutes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizQ2 extends AppCompatActivity {
    // Declaring
    TextView scorefinaL;
    RadioButton Q2_1_1,Q2_2_2;
    RadioGroup rbg1_q2,rbg2_q2;
    Button finish;
    int SCORE_Q2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_q2);
        // assigning variables
        scorefinaL=(TextView)findViewById(R.id.textView10);
        rbg1_q2=(RadioGroup)findViewById(R.id.RBG1_Q2);
        rbg2_q2=(RadioGroup)findViewById(R.id.RBG2_Q2);
        finish=(Button)findViewById(R.id.finish_btn);
        // converting score textview into int variable
        //SCORE_Q2=scorefinaL.getText().length();
        // getting score from previous activity
        Intent mIntent=getIntent();
        int Score= mIntent.getIntExtra("score",0);
        SCORE_Q2=Score;
        scorefinaL.setText("Score:"+Score);
        // setting an action listener to finish button
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Declaring variables to get checked radio button on radio group
                int Q2_1=rbg1_q2.getCheckedRadioButtonId();
                int Q2_2=rbg2_q2.getCheckedRadioButtonId();
                Q2_1_1=findViewById(Q2_1);
                Q2_2_2=findViewById(Q2_2);
                // checking the exact checked variable
                if(Q2_1_1.getText().equals("Current location")&&Q2_2_2.getText().equals("Satellite view"))
                {
                   int add =10;
                   SCORE_Q2=SCORE_Q2+add;
                   scorefinaL.setText("Score:"+SCORE_Q2);

                }else
                    if(Q2_2_2.getText().equals("Street view") &&Q2_1_1.getText().equals("Current location"))
                    {
                        int add =5;
                        SCORE_Q2=SCORE_Q2+add;
                        scorefinaL.setText("Score:"+SCORE_Q2);

                    }else
                        if(Q2_2_2.getText().equals("Satellite view")&&Q2_1_1.getText().equals("Destination"))
                        {
                            int add =5;
                            SCORE_Q2=SCORE_Q2+add;
                            scorefinaL.setText("Score:"+SCORE_Q2);
                        }
                NavigateToMenu(SCORE_Q2);




            }
        });
    }
    public void NavigateToMenu(int score)
    {
        Intent intent= new Intent(QuizQ2.this,Done.class);
        intent.putExtra("score",score);
        startActivity(intent);

    }

}