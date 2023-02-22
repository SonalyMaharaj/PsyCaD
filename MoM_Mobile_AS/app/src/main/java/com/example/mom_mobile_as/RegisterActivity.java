package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity
{
    private Button btnRegsiter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegsiter = (Button) findViewById(R.id.buttonSave);
        btnRegsiter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMainActivity();
            }
        });
    }

    public void openMainActivity()
    {
        Intent Main_intent = new Intent(this, MainActivity.class);
        startActivity(Main_intent);
    }
}