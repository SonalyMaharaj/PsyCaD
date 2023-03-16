package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity
{
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();//hide the App name Title bar

        imageView = findViewById(R.id.backArrow);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void LogOut(View view) {
        //This method will remove the Session and Open Login Screen

        SessionManager sessionManager=new SessionManager(SettingsActivity.this);
        sessionManager.removeSession(); //remove the current Session

        //MOVE to the LogIn Activity Method
        Intent logoutIntent = new Intent(this, MainActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutIntent);
    }

    public void openDetailtsActivity(View view) {
        Intent intent = new Intent(SettingsActivity.this, DetailsActivity.class);
        startActivity(intent);
    }
}