package com.example.mom_mobile_as;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.function.DoubleUnaryOperator;

public class DiaryActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    ImageView iv_add;
    ListView lvDiaries;
    ProgressBar progressBar;
    DataServiceReference client=new DataServiceReference(DiaryActivity.this);

    ArrayList<DiaryModel> InitialDiaryEntries; //this variable will store  List of Fixed Diary Entries requested from the API
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        lvDiaries=findViewById(R.id.lvDiaryEntries);
        iv_arrow = findViewById(R.id.backArrow);
        progressBar=findViewById(R.id.progressBar);
        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(DiaryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        iv_add = findViewById(R.id.addButton);

        iv_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivity is the name of new created EmptyActivity.
                Intent intent = new Intent(DiaryActivity.this, AddDiaryActivity.class);
                startActivity(intent);
            }
        });

        populateDiaryEntries();

        //handle List View on Click

        lvDiaries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get Diary at index i
               DiaryModel diary=InitialDiaryEntries.get(i);
               //create intent
               Intent intent=new Intent(getApplicationContext(),ViewDiary.class);
               //parse the object needed to the next activity
               intent.putExtra("diary", diary);
               startActivity(intent);
            }
        });

    }


    public void populateDiaryEntries(){

        //Get Diary Entries Objects
        client.getMyDiaryEntries(new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {
                //GetThe returned List of Diary entry objects

                InitialDiaryEntries =(ArrayList<DiaryModel>) response; //initialize the diary entries
                //store this  track of this
                //pass the objects list to the DiaryTitles generator
                //ArrayList<String> DiariesTitles=GenerateDiaryTitles(InitialDiaryEntries); //DiaryTitles are names displayed in the ListView

                //pass the titles to an arrayAdapter
               // ArrayAdapter diariesAdapter=new ArrayAdapter( DiaryActivity.this,android.R.layout.simple_list_item_1, DiariesTitles);//this, android.R.layout.simple_list_item_1, DiariesTitles);
                //pass the adapter to the ListView
                lvDiaries.setAdapter(new DiaryAdapterView(DiaryActivity.this,R.layout.list_row,InitialDiaryEntries));

                //pro
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void OnError(String error) {
                Toast.makeText(DiaryActivity.this,error,Toast.LENGTH_LONG);
            }
        });



    }

    //Generate DiaryEntries Names
    private ArrayList<String> GenerateDiaryTitles(ArrayList<DiaryModel> diaryEntries) {

        ArrayList<String> titles=new ArrayList<>();

        for (DiaryModel diaryModel:diaryEntries) {
            titles.add(diaryModel.getDiaryTitle()); //add title to list of titles
        }


        return  titles; //return titles
    }

    //create a custom made Adapter View, namely 'DiaryAdapterView', this will allow the ListVIew to accept Images and Texts
    public  class DiaryAdapterView extends ArrayAdapter<DiaryModel> {

        private Context context;
        private int resource;
        public DiaryAdapterView(@NonNull Context context, int resource, @NonNull ArrayList<DiaryModel> objects) {
            super(context, resource, objects);
            this.context=context;
            this.resource=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(resource,parent,false);

            ImageView imageView=convertView.findViewById(R.id.ivImage);

            TextView txtMoodName=convertView.findViewById(R.id.txtName);

            TextView txtMoodDate=convertView.findViewById(R.id.txtMoodDate);

            imageView.setImageResource(R.drawable.diary_icon);
            txtMoodName.setText(getItem(position).getDiaryTitle());
            //create String Tokenizer to trim the date and store it
            String date=getItem(position).getDiaryDate().toString();
            StringTokenizer stringTokenizer=new StringTokenizer(date,"T");
            txtMoodDate.setText(stringTokenizer.nextToken()); //+" "+ getItem(position).getMoodTime()+":00:00"); //set the date and the time


            return convertView;
        }
    }


}