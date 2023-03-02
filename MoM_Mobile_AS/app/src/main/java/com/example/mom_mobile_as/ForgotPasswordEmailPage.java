package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgotPasswordEmailPage extends AppCompatActivity {
    DataServiceReference client=new DataServiceReference(this);
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_email_page);
        btnNext=findViewById(R.id.btnNext);

        //GET all the students

        //check student with this email address
        //if the student exists, capture the student model, pass it as a parameter to the intent and redirect to the ForgotPasswordCodePage
        btnNext.setOnClickListener(e->{
            OpenForgotPasswordCodePage();
        });

    }

    public  void OpenForgotPasswordCodePage(){
        Intent forgotPswrd_Intent = new Intent(this, ForgotPasswordCodePage.class);
        startActivity(forgotPswrd_Intent);
    }
}