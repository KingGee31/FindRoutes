package com.example.findroutes;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {
// Declaring variables
    TextView EMAIL;
    TextView PASSWORD,Prompt;
    Button register;
    Button login;
    // database variables
    private static final String DB_URL="jdbc:mysql://192.168.96.19/18011644_amo";
    private static final String USER="amo";
    private static final String PASS="amo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initializing variables
        Prompt=(TextView)findViewById(R.id.textView2_txt);
        EMAIL=(TextView)findViewById(R.id.Lemail_txt);
        PASSWORD=(TextView)findViewById(R.id.Lpassword);
        register=(Button)findViewById(R.id.Lregister);
        login=(Button)findViewById(R.id.Llogin_btn);

        // action listeners if buttons are clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // NavigateToMap();
                UserLogin login= new UserLogin();
                login.execute("");
               // Register.Send send = new Register.Send();
             //   send.execute("");

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling message to navigate to registration page
                OpenRegistrationPage();

            }
        });
    }
    public void OpenRegistrationPage(){
        Intent intent= new Intent(this,Register.class);
        startActivity(intent);
    }

    public void NavigateToMenu()
    {
        Intent intent= new Intent(Login.this,Menu.class);

        startActivity(intent);

    }
    private class  UserLogin extends AsyncTask<String,String,String>
    {
        String msg="";
        // getting users input

        String userEmail=EMAIL.getText().toString();
        String userPassword=  PASSWORD.getText().toString();




        @Override
        protected void onPreExecute() {
            Prompt.setText("Processing Login");
        }

        @Override
        protected String doInBackground(String... strings)
        {

            if(userEmail.trim().equals("")||userPassword.trim().equals(""))
            {
                msg="fill all fields";
            }
           else
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
                       // String query="SELECT * FROM user where email='"+userEmail+"' and Password='"+userPassword+"')";
                       // SELECT  `name`, `surname`, `email`, `Password` FROM `user` WHERE email="" and Password=""


                        String query="SELECT  `name`, `surname`, `email`, `Password` FROM `user` WHERE email='"+userEmail+"' and Password='"+userPassword+"';"
;
                        Statement statement=conn.createStatement();
                        ResultSet resultSet= statement.executeQuery(query);
                       if(resultSet.next())
                       {

                           msg="Login good";
                           conn.close();
                           // taking users email to another activity
                           // tried to take users email to the next activity but it kept on giving an error

                           NavigateToMenu();
                       }else
                           {
                               msg="connection failed";
                       }


                    }


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
            Prompt.setText(msg);
        }
    }

}