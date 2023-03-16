package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class AddDiaryActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    EditText txtDiaryEntry;
    Button btnSaveEntry;
    DataServiceReference client=new DataServiceReference(AddDiaryActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        getSupportActionBar().hide();

        iv_arrow = findViewById(R.id.backArrow);
        txtDiaryEntry=findViewById(R.id.txtDiary);
        btnSaveEntry=findViewById(R.id.btnSaveEntry);

        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivity is the name of new created EmptyActivity.
                Intent intent = new Intent(AddDiaryActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });


        btnSaveEntry.setOnClickListener(e->{
            if(String.valueOf(txtDiaryEntry.getText()).length()<=10){
                //if the entry is Empty or Not big enough then don't save it
                Toast.makeText(AddDiaryActivity.this,"Fill in Entry",Toast.LENGTH_LONG).show();
            }
            else{
                //Add Diary Entry
                client.AddDiary(String.valueOf(txtDiaryEntry.getText()), new DataServiceReference.IMoMVolleyListener() {
                    @Override
                    public void OnResponse(Object response) {
                        Toast.makeText(AddDiaryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        //clear/empty the text Area
                        txtDiaryEntry.setText("");
                    }

                    @Override
                    public void OnError(String error) {
                        Toast.makeText(AddDiaryActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}