package com.example.mom_mobile_as;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity
{
    private Button btnDiary;
    private Button btnMood;
    private Button btnCall;
    private Button btnBookings;
    private ProgressBar progressBar;
    private TextView txtWelcomeMessage;
    private ImageView imgvSettings;

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
        btnBookings=findViewById(R.id.btnBookings);
        btnMood = (Button) findViewById(R.id.MoodButton);
        btnCall = (Button) findViewById(R.id.CallButton);
        imgvSettings = findViewById(R.id.SettingsButton);

        btnBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create an AlertDialog object
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                // set the view of the AlertDialog to your layout file
                builder.setView(getLayoutInflater().inflate(R.layout.activity_pop_up_window, null));
                // create the AlertDialog and show it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnDiary.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openDiaryActivity();
            }
        });

        //Redirect to mood page
        btnMood.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMoodActivity();
            }
        });

        //Redirect to call page
        btnCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openCallActivity();
            }
        });
        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        imgvSettings.setOnClickListener(new View.OnClickListener()
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
                    txtWelcomeMessage.setText(("Hello, "+student.StudentName).toUpperCase());

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

    public void OpenBookingForm(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }
//This Code is for the pop_up_window layout file, since it is a pop up, it cannot read its activity file but shares an activity file with the HomeActivity, an intent(redirect) was not used, so it its own activity was not created.

    public void OpenKingsway(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenBunting(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenDFC(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenSWT(View view) {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }
}