package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ForgotPasswordCodePage extends AppCompatActivity {
    private Button btnVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_code_page);

        btnVerify=findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(e->{
            OpenNewPasswordPage();
        });
    }

    public void OpenNewPasswordPage(){
        Intent forgotPswrd_Intent = new Intent(this, ForgotPasswordNewPass.class);
        startActivity(forgotPswrd_Intent);
    }
}