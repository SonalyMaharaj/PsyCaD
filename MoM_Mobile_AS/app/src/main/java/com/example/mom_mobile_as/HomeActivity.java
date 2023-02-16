package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
{
    private Button btnDiary;
    private Button btnMood;
    private Button btnCall;
    private ProgressBar progressBar;
    private TextView txtWelcomeMessage;
    ImageView imageView;

    DataServiceReference client=new DataServiceReference(HomeActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Redirect to diary page
        progressBar=findViewById(R.id.progressBar);
        txtWelcomeMessage=findViewById(R.id.welcomeMessage);
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

        DisplayWelcomeMessage(); //Display Welcome Home Page Message

    }

    public  void DisplayWelcomeMessage(){

        SessionManager sessionManager=new SessionManager(HomeActivity.this);
        int StudentNumber=sessionManager.getSession();

        client.getStudent(String.valueOf(StudentNumber) , new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {

                try{
                    //display the Welcome Message
                    Models.StudentModel student=(Models.StudentModel)response;
                    txtWelcomeMessage.setText("Hello, "+student.StudentName);

                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {

                Toast.makeText(HomeActivity.this,"Error Occurred",Toast.LENGTH_LONG);
            }
        });

        //remove the progress Bar after successful load
        progressBar.setVisibility(View.GONE);
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

    public void BookingForm(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
        startActivity(Call_intent);

    }
}