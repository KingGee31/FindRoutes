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
import java.sql.Statement;

public class Register extends AppCompatActivity {
// Declaring variables
    TextView name;
    TextView surname;
    TextView email;
    TextView password;
    TextView cpassword;
    Button clear;
    Button proceed;
    TextView Tresults;
    // database variables
    private static final String DB_URL="jdbc:mysql://10.91.226.93/18011644_amo";
    private static final String USER="amo";
    private static final String PASS="amo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // initializing variables
        name=(TextView)findViewById(R.id.Rname_txt);
        surname=(TextView)findViewById(R.id.Rsurname_txt);
        email=(TextView)findViewById(R.id.Remail_txt);
        password=(TextView)findViewById(R.id.Rpassword_txt);
        cpassword=(TextView)findViewById(R.id.RconfirmP_txt);
        Tresults=(TextView)findViewById(R.id.textView);
        clear=(Button)findViewById(R.id.Clear_btn);
        proceed=(Button)findViewById(R.id.Proceed_btn);

        // action listeners to navigate to other pages

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // calling method to clear text fields
                ClearVariables();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // after registering calling a method to navigate to registration page
              //  OpenLogin();
                Send send = new Send();
                send.execute("");

            }
        });
    }

    public void OpenLogin()
    {
        Intent intent= new Intent(this,Login.class);
        startActivity(intent);
    }
    public void ClearVariables()
    {

    }
    private class  Send extends AsyncTask<String,String,String>
    {
        String msg="";
        // getting users input
        String userName=name.getText().toString();
        String userSurname=surname.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();
        String userCP=cpassword.getText().toString();



        @Override
        protected void onPreExecute() {
            Tresults.setText("Processing Registration");
        }

        @Override
        protected String doInBackground(String... strings)
        {
            // these variables are for validation to make sure users enter the correct information
            String ValidSurname = "[a-zA-Z]+\\.?";
            String ValidName = "[a-zA-Z]+\\.?";
            String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[@_.]).*$";
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if(userName.trim().equals("")||userSurname.trim().equals("")||userEmail.trim().equals("")||userPassword.trim().equals("")||userCP.trim().equals(""))
            {
                msg="fill all fields";
            }else
                if(!userPassword.equals(userCP))
                {
                    msg="Password does not match";
                }else if(!userEmail.matches(emailPattern))
                {
                    msg="Invalid email";
                } else
                    if(userPassword.length()<6)
                    {
                        msg="Password too short";
                    }else if(!userPassword.matches(PASSWORD_PATTERN))
                    {
                        msg="Password too weak";
                    }
                    else if(!userName.matches(ValidName))
                    {
                        msg="Enter real name";
                    }else if(userName.length()<3)
                    {
                        msg="Name should have more than two letters";
                    }else if(!userSurname.matches(ValidSurname))
                    {
                        msg="Enter real surname";
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
                                String query="INSERT INTO `user`(`UserID`, `name`, `surname`, `email`, `Password`) VALUES ('','"+userName+"','"+userSurname+"','"+userEmail+"','"+userPassword+"')";
                                Statement statement=conn.createStatement();
                                statement.execute(query);
                                msg="Registration complete";
                                //  Toast.makeText(Register.this, "Registration complete", Toast.LENGTH_SHORT).show();
                                OpenLogin();

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
            Tresults.setText(msg);
        }
    }
}