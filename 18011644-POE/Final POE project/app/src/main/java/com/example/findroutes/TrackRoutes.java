package com.example.findroutes;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TrackRoutes extends AppCompatActivity {
    // Declaring variables
    EditText CurrentLocationOn,DestinationLocationOn;
    TextView list1,list2;
    TextView error;
    Button trackRoute,saveLocation,viewlocation;
    TextView getall;
    // database variables
    private static final String DB_URL="jdbc:mysql://192.168.96.19/18011644_amo";
    private static final String USER="amo";
    private static final String PASS="amo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_routes);

        // assigning variables
       // getall=(TextView)findViewById(R.id.DisplayLocations_FromDatabase);
        error=(TextView)findViewById(R.id.textView17);
        CurrentLocationOn=(EditText)findViewById(R.id.et_Enter_Current_Location);
        DestinationLocationOn=(EditText)findViewById(R.id.et_Enter_Destination_Location);
        trackRoute=(Button)findViewById(R.id.Get_Route_btn);
        saveLocation=(Button)findViewById(R.id.Save_btn);
        viewlocation=(Button)findViewById(R.id.View_locations);
        list1=(TextView)findViewById(R.id.textView19);
        list2=(TextView)findViewById(R.id.textView18);


       // Display=(ListView)findViewById(R.id.RetriveDisplay_data);



        // action listener to when the trackroute button is clicked
        trackRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get values from edit text views
                String C_location=CurrentLocationOn.getText().toString();
                String F_Destination=DestinationLocationOn.getText().toString();

                // checking if edit text views are not empty
                if(C_location.equals("")&& F_Destination.equals(""))
                {
                    Toast.makeText(TrackRoutes.this, "fill both text fields ", Toast.LENGTH_SHORT).show();

                }
                else
                    {
                        DisplayTrack(C_location,F_Destination);

                }
            }


        });
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Send send= new Send();
                send.execute("");
            }
        });
        viewlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getLocations getLocations= new getLocations();
                getLocations.execute("");

            }
        });

    }
    private void DisplayTrack(String c_location, String f_destination)
    {
        // if the device does not have a google installed, then redirect it to play store
        try{
            Uri uri=Uri.parse("https://www.google.co.in/maps/dir/"+c_location+"/"+f_destination);
            // initialize intent with action view
            Intent intent= new Intent(Intent.ACTION_VIEW,uri);
            // set package
            intent.setPackage("com.google.android.apps.maps");
            // set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // start activity
            startActivity(intent);
        }catch (ActivityNotFoundException e)
        {
            // when google map is not installed
            // initialize uri
            Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            // initialize intent with action view
            Intent intent= new Intent(Intent.ACTION_VIEW,uri);
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // start intent
            startActivity(intent);
        }
    }
    private class  Send extends AsyncTask<String,String,String>
    {
        String msg="";
        // getting users input
        String Current=CurrentLocationOn.getText().toString();
        String Destination=DestinationLocationOn.getText().toString();




        @Override
        protected void onPreExecute() {
            error.setText("saving locations loading");
        }

        @Override
        protected String doInBackground(String... strings)
        {

            if(Current.trim().equals("")||Destination.trim().equals(""))
            {
                msg="fill all fields";
            }else
            {
                try
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn= DriverManager.getConnection(DB_URL,USER,PASS);
                    if(conn==null)
                    {
                        msg="Connection goes wrong";

                    }else
                    {
                        String query="INSERT INTO `locations`(`locationId`, `Location1`, `Location2`) VALUES ('','"+Current+"','"+Destination+"')";
                        Statement statement=conn.createStatement();
                        statement.execute(query);

                        msg="Location saved ";
                        //  Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();
                      //  OpenLogin();

                    }
                    conn.close();

                }
                catch (Exception e)
                {

                    msg=" problem with  problem with ";
                    e.printStackTrace();

                }

            }

            return  msg;

        }

        @Override
        protected void onPostExecute(String msg)
        {
            error.setText(msg);
        }
    }
    private class  getLocations extends AsyncTask<String,String,String>
    {
        String msg="";





        @Override
        protected void onPreExecute() {
            error.setText("loading locations");
        }

        @Override
        protected String doInBackground(String... strings)
        {



                try
                {
                    ArrayList<String> cars = new ArrayList<String>();
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn= DriverManager.getConnection(DB_URL,USER,PASS);
                    if(conn==null)
                    {
                        msg="Connection goes wrong";

                    }else
                    {
                        String query="SELECT  `Location1`, `Location2` FROM `locations`";
                       // SELECT  `Location1`, `Location2` FROM `locations`
                        //SELECT  `Location1`, `Location2` FROM `locations`
                        Statement statement=conn.createStatement();
                        ResultSet rs =statement.executeQuery(query);
                        while(rs.next())
                        {
                            //String a=rs.getString("Location1");
                          //  String b=rs.getString("Location2");
                        //   String finala=a+b;
                        //    cars.add(finala);

                         list1.setText(rs.getString(1));
                        list2.setText(rs.getString(2));
                         //String retrieved = rs.getString(1)+"\n "+rs.getString(2);

                       //  list1.setText(retrieved);




                        }
                        statement.close();
                       // statement.execute(query);
                        msg="Location found ";
                        //  Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();
                        //  OpenLogin();

                    }
                    conn.close();

                }
                catch (Exception e)
                {

                    msg=" connection error ";
                    e.printStackTrace();

                }



            return  msg;

        }

        @Override
        protected void onPostExecute(String msg)
        {
            error.setText(msg);
        }
    }
}