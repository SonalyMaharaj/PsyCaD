package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMoodActivity extends AppCompatActivity
{
    DataServiceReference client=new DataServiceReference(AddMoodActivity.this);

    ImageView iv_arrow;
    GridView gvMoods;
    Button btnSave;
    int ClickedMoodPosition=-1; //
    Integer[] image={
            R.drawable.grinning, R.drawable.sob,R.drawable.no_mouth,
            R.drawable.face_with_thermometer, R.drawable.rage,R.drawable.sunglasses,
            R.drawable.partying_face,R.drawable.scream,R.drawable.persevere
    };

    String[] MoodNames={
        "Happy","Sad","Indifferent","Sick"
            ,"Angry","Cool","Party","Shocked","Awful"
};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mood);

        iv_arrow = findViewById(R.id.backArrow);
        gvMoods=findViewById(R.id.gvEmojis);
        btnSave=findViewById(R.id.btnSave);
        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivity is the name of new created EmptyActivity.
                goBack();
            }
        });
        ArrayList<Image> listemojis=new ArrayList<>();

        //TODO: CREATE A GRID VIEW AND POPULATE EMOJIS TO IT
        gvMoods.setAdapter(new ImageAdapterGridView(this));

        gvMoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClickedMoodPosition=i;
                Toast.makeText(AddMoodActivity.this,MoodNames[i], Toast.LENGTH_SHORT).show();
            }
        });


        //save the Mood that was Chosen
        btnSave.setOnClickListener(e->{
            //TODO: SAVE THE SELECTED MOOD TO THE DATABASE
            if(ClickedMoodPosition!=-1){
            client.LogMood(MoodNames[ClickedMoodPosition], image[ClickedMoodPosition], new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    Toast.makeText(AddMoodActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    goBack();
                }

                @Override
                public void OnError(String error) {
                    Toast.makeText(AddMoodActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            });
            }
            else{
                Toast.makeText(AddMoodActivity.this,"Choose a Mood", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goBack(){
        Intent moodIntent = new Intent(AddMoodActivity.this, MoodActivity.class);
        startActivity(moodIntent);
    }

    //create a custom made ImageAdapterGridView, this will allow the GridView to accept Images
    private class ImageAdapterGridView extends BaseAdapter {

        private Context context;
        public ImageAdapterGridView(Context context) {
            this.context=context;
        }

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if(convertView==null){
                //create new imageview
                imageView=new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(250,250));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(16,16,16,16);
            }
            else{
                imageView=(ImageView) convertView;
            }
            imageView.setImageResource(image[position]);
            return imageView;
        }
    }
}