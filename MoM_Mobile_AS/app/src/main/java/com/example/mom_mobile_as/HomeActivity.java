package com.example.mom_mobile_as;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApkChecksum;
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

import kotlin.random.Random;

public class HomeActivity extends AppCompatActivity
{
    private Button btnDiary;
    private Button btnMood;
    private Button btnCall;
    private Button btnBookings;
    private ProgressBar progressBar;
    private TextView txtWelcomeMessage;
    private ImageView imgvSettings;

    private DataServiceReference client=new DataServiceReference(HomeActivity.this);
    private Models.StudentModel student;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();//hide the App name Title bar

        //Redirect to diary page
        progressBar=findViewById(R.id.progressBar);
        txtWelcomeMessage=findViewById(R.id.welcomeMessage);
        btnDiary = (Button) findViewById(R.id.EDiaryButton);
        btnBookings=findViewById(R.id.btnBookings);
        btnMood = (Button) findViewById(R.id.MoodButton);
        btnCall = (Button) findViewById(R.id.CallButton);
        imgvSettings = findViewById(R.id.SettingsButton);


        InitializeStudent(); //Display Welcome Home Page Message and Initialize the Student object

        btnBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              switch(student.getCampus()){
                  case "APB":
                      OpenAPB();
                      break;
                  case "DFC":
                      OpenDFC();
                      break;
                  case "SWT":
                      OpenSWT();
                      break;
                  default:
                      //redirect to APK
                      OpenAPK();
                      break;
              }
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


    }

    public  void InitializeStudent(){

        SessionManager sessionManager=new SessionManager(HomeActivity.this);
        int StudentNumber=sessionManager.getSession();

        client.getStudent(String.valueOf(StudentNumber) , new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {

                try{
                    //initialize the Student object
                    student=(Models.StudentModel)response;
                    //display the Welcome Message
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

    public void OpenAPK() {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APKPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenAPB() {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/APBPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenDFC() {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/DFCPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void OpenSWT() {

        //redirect to the Booking Form
        Intent Call_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://outlook.office365.com/owa/calendar/SWTPsyCaD@ujac.onmicrosoft.com/bookings/"));
        startActivity(Call_intent);

    }

    public void DisplayPopUp(){
        // create an AlertDialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        // set the view of the AlertDialog to your layout file
        builder.setView(getLayoutInflater().inflate(R.layout.activity_pop_up_window, null));
        // create the AlertDialog and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}