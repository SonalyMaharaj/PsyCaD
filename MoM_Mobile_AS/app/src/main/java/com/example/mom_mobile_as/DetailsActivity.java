package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailsActivity extends AppCompatActivity
{
    private Button btnDeatils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnDeatils = (Button) findViewById(R.id.buttonSave);
        btnDeatils.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openHomeActivity();
            }
        });
    }

    public void openHomeActivity()
    {
        Intent Home_intent = new Intent(this, HomeActivity.class);
        startActivity(Home_intent);
    }
}