package com.example.findroutes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Quiz_Q1 extends AppCompatActivity  {
    // Declarations of variables
    RadioGroup group1,group2;
    TextView Time,Score,see;
 Button next;
    RadioButton RBT;
    RadioButton RBT2;
    private static final long COUNTDOWN_IN_MILLIS=30000;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__q1);

        // assigning variables
        group1=(RadioGroup)findViewById(R.id.Quize_RBG1);
        group2=(RadioGroup)findViewById(R.id.Quize_RBG2);
        Time=(TextView)findViewById(R.id.time_txt);
        Score=(TextView)findViewById(R.id.SCORE_txt);
        see=(TextView)findViewById(R.id.textView7);
        next=(Button)findViewById(R.id.givenbtn);
        // Starting countdown clock
        timeLeftInMillis=COUNTDOWN_IN_MILLIS;
        StartCountDown();
        // when next button is clicked
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // int variable for score
        int SCORE=Score.getText().length();
        SCORE=0;
        // this is set to get selected radio button
        int radioButton=group1.getCheckedRadioButtonId();
        int grp2=group2.getCheckedRadioButtonId();
        RBT2=findViewById(grp2);
        RBT=findViewById(radioButton);

        if(RBT.getText().equals("No") && RBT2.getText().equals("Grey"))
        {
         int x=5;
         SCORE=SCORE+x;


            Score.setText("Score:"+SCORE);

        }else   if(RBT.getText().equals("No") && RBT2.getText().equals("Red"))
        {
            int x=5;
            SCORE=SCORE+x;


            Score.setText("Score:"+SCORE);

        }
        else if(RBT2.getText().equals("Blue")&& RBT.getText().equals("yes"))
        {
            int increment =5;

            SCORE=SCORE+increment;
            Score.setText("Score:"+SCORE);

        } else  if (RBT.getText().equals("yes")&&RBT2.getText().equals("Blue"))
        {
            int increment =5;

            SCORE=SCORE+increment;
            Score.setText("Score:"+SCORE);
        }
        else  if (RBT.getText().equals("No")&&RBT2.getText().equals("Blue"))
        {
            int increment =15;

            SCORE=SCORE+increment;
            Score.setText("Score:"+SCORE);
        }else
            {
                int increment =0;

                SCORE=SCORE+increment;
                Score.setText("Score:"+SCORE);

        }
        // Passing score to another activity

        NavigateToMenu(SCORE);
        countDownTimer.cancel();


    }
});



    }
    private void StartCountDown()
    {
        countDownTimer= new CountDownTimer(timeLeftInMillis,1000) {
         @Override
         public void onTick(long millisUntilFinished) {
             timeLeftInMillis=millisUntilFinished;
             updateCountDownText();
         }

         @Override
         public void onFinish()
         {
             timeLeftInMillis=0;
             updateCountDownText();

         }
     }.start();

    }
    public void updateCountDownText()
    {
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;
        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        //setting my time text view to real time ticking
        Time.setText(timeFormatted);
        // if time is less than 10 sec the time text will turn red
        if(timeLeftInMillis<10000)
        {
            Time.setTextColor(Color.RED);

        }

    }

    public void NavigateToMenu(int score)
    {
        Intent intent= new Intent(Quiz_Q1.this,QuizQ2.class);
        intent.putExtra("score",score);
        startActivity(intent);

    }



}