package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity
{
    private Button btnDiary;
    private Button btnMood;
    private Button btnCall;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Redirect to diary page
        btnDiary = (Button) findViewById(R.id.EDiaryButton);
        btnDiary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openDiaryActivity();
            }
        });

        //Redirect to mood page
        btnMood = (Button) findViewById(R.id.MoodButton);
        btnMood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMoodActivity();
            }
        });

        //Redirect to call page
        btnCall = (Button) findViewById(R.id.CallButton);
        btnCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openCallActivity();
            }
        });

        imageView = findViewById(R.id.SettingsButton);

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
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    public void openDiaryActivity()
    {
        Intent Diary_intent = new Intent(this, DiaryActivity.class);
        startActivity(Diary_intent);
    }

    public void openMoodActivity()
    {
        Intent Mood_intent = new Intent(this, MoodActivity.class);
        startActivity(Mood_intent);
    }

    public void openCallActivity()
    {
        Intent Call_intent = new Intent(this, CallActivity.class);
        startActivity(Call_intent);
    }
}