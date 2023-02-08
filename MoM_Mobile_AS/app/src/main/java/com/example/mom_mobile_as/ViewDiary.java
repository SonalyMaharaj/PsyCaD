package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);

        //display the details of the clicked Diary entry
    }


    public DiaryModel GetPassedDiaryModel(){
        DiaryModel diary = (DiaryModel) getIntent().getSerializableExtra("diary");
        return diary;
    }
}