package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    ImageView iv_log;
    Button btnER24;
    Button btnCrisis;
    Button btnProtectionServices;
    Button btnCampusHealth;

    DataServiceReference client=new DataServiceReference(CallActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        btnER24=findViewById(R.id.btnER24);
        btnCrisis=findViewById(R.id.btnCrisis);
        btnCampusHealth=findViewById(R.id.btnCampusHealth);
        btnProtectionServices=findViewById(R.id.btnProtectionServices);

        iv_arrow = findViewById(R.id.backArrow);
        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(CallActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        iv_log = findViewById(R.id.logButton);
        iv_log.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(CallActivity.this, CallLogActivity.class);
                startActivity(intent);
            }
        });

        //OPEN CALL_PHONE AND DISPLAY THE RELEVANT.
        btnER24.setOnClickListener(e->{
            //save the phone call to the DB
            String phoneNum="0659001011";
            client.LogCall(phoneNum, btnER24.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(CallActivity.this,response.toString(), Toast.LENGTH_LONG);
                    //call the phone when the log was successfully saved
                    OpenCallPhone(phoneNum);
                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(CallActivity.this,error.toString(), Toast.LENGTH_LONG);
                }
            });
        });

        btnCrisis.setOnClickListener(e->{
            //Save the Phone call to the DB
            String phoneNum="0800055555";
            client.LogCall(phoneNum, btnCrisis.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(CallActivity.this,response.toString(), Toast.LENGTH_LONG);
                    //call the phone when the log was successfully saved
                    OpenCallPhone(phoneNum);
                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(CallActivity.this,error.toString(), Toast.LENGTH_LONG);
                }
            });
        });

        btnCampusHealth.setOnClickListener(e->{
            //Save the Phone call to the DB
            String phoneNum="0814071011";
            client.LogCall(phoneNum, btnCampusHealth.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(CallActivity.this,response.toString(), Toast.LENGTH_LONG);
                    //call the phone when the log was successfully saved
                    OpenCallPhone(phoneNum);
                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(CallActivity.this,error.toString(), Toast.LENGTH_LONG);
                }
            });
        });

        btnProtectionServices.setOnClickListener(e->{
            //Save the Phone call to the DB
            String phoneNum="0824040666";
            client.LogCall(phoneNum, btnProtectionServices.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(CallActivity.this,response.toString(), Toast.LENGTH_LONG);
                    //call the phone when the log was successfully saved
                    OpenCallPhone(phoneNum);
                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(CallActivity.this,error.toString(), Toast.LENGTH_LONG);
                }
            });
        });
    }

    public void OpenCallPhone(String telephone)
    {
        Intent callIntent=new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+telephone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            //If the Permission is NOT GRANTED, let the user Know to grant the Permission
            Toast.makeText(CallActivity.this,"Permissions: Allow App to access phone", Toast.LENGTH_LONG);
            return;
        }
        startActivity(callIntent);
    }

    public  void saveLog(){

    }
}