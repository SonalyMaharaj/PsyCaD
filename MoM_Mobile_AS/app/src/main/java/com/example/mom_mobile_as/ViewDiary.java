package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewDiary extends AppCompatActivity {

    private DataServiceReference client=new DataServiceReference(ViewDiary.this);
    EditText txtViewDiary;
    Button btnSaveEntry;
    ImageView iv_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary);
        getSupportActionBar().hide();//hide the App name Title bar


        iv_arrow = findViewById(R.id.backArrow);
        txtViewDiary=findViewById(R.id.txtDiary);
        btnSaveEntry=findViewById(R.id.btnSaveEntry);

        //GO BACK to the previous page if arrow clicked
        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //GO BACK to the previous page
                Intent intent = new Intent(ViewDiary.this, DiaryActivity.class);
                startActivity(intent);
            }
        });

        //display the details of the clicked Diary entry
        SessionManager sessionManager=new SessionManager(ViewDiary.this);
        int StudentNumber=sessionManager.getSession();

        DiaryModel   diary=GetPassedDiaryModel(); //get Diary Model that was passed.

        txtViewDiary.setText(diary.getDiaryDescription());//set the diary description

        //Save Diary Entry
        btnSaveEntry.setOnClickListener(E-> {

            if(String.valueOf(txtViewDiary.getText()).length()<=10){
                //if the entry is Empty or Not big enough then don't save it
                Toast.makeText(ViewDiary.this,"Can't Save Empty Entry",Toast.LENGTH_LONG).show();
            }
            else{
            client.EditDairy(diary.getDiaryId(), txtViewDiary.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(ViewDiary.this, response.toString(), Toast.LENGTH_LONG).show();

                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(ViewDiary.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        });
    }


    public DiaryModel GetPassedDiaryModel(){
        DiaryModel diary = (DiaryModel) getIntent().getSerializableExtra("diary");
        return diary;
    }
}