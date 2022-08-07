package com.example.findroutes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {
    // Declaring variables
    Button Register_BTN;
    Button Login_BTN;
// creating a log to check if we can make google maps
private static  final String TAG="MainActivity";
private static final int ERROR_DIALOG_REQUEST=9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing buttons
        Register_BTN=(Button)findViewById(R.id.Register_btn);
        Login_BTN=(Button)findViewById(R.id.Login_btn);
        // calling a method
        isServiceOK();

        // action listener if buttons are clicked
        Register_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calling a method to navigate
                OpenRegistrationPage();
            }
        });
        Login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // call method to navigate
                OpenLoginPage();
                // navigating to maps



            }
        });

    }
    public void OpenLoginPage(){
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }

    public void OpenRegistrationPage(){
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }

    // method to check a version
    public boolean isServiceOK(){

        Log.d(TAG,"isServiceOK: checking google services version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available== ConnectionResult.SUCCESS)
        {
            //everything is good
            Log.d(TAG,"isServicesOk: Google play services is working");
            return true;

        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG,"isServicesOK: an error occurred but it can be fixed");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"cannot resolve the error",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}