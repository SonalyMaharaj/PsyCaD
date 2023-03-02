package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForgotPasswordEmailPage extends AppCompatActivity {
    DataServiceReference client=new DataServiceReference(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_email_page);

        //GET all the students
        client.
        //check student with this email address
        //if the student exists, capture the student model, pass it as a parameter to the intent and redirect to the ForgotPasswordCodePage
    }
}