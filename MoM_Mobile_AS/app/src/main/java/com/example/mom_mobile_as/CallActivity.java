package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;

public class CallActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    ImageView iv_log;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

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
    }
}