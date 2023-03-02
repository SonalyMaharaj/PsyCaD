package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgotPasswordNewPass extends AppCompatActivity {

    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_new_pass);

        btnSave=findViewById(R.id.btnSave);

        btnSave.setOnClickListener(e->{
            OpenLoginPage();
        });
    }


    public void OpenLoginPage(){
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}