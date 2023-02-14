package com.example.mom_mobile_as;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.ArrayList;

public class MoodActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    ImageView iv_add;
    ListView lvMoods;
    DataServiceReference client=new DataServiceReference(MoodActivity.this);

    Integer[] images={
            R.drawable.grinning, R.drawable.sob,R.drawable.no_mouth,
            R.drawable.face_with_thermometer, R.drawable.rage,R.drawable.sunglasses,
            R.drawable.partying_face,R.drawable.scream,R.drawable.persevere
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        iv_arrow = findViewById(R.id.backArrow);
        lvMoods=findViewById(R.id.lvMoodLogs);
        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(MoodActivity.this, HomeActivity.class);
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
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(MoodActivity.this, AddMoodActivity.class);
                startActivity(intent);
            }
        });

        //request all the MOODS from the database, and initialize the images variable
        client.getMoods(new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {
                ArrayList<Models.MoodModel> moodModels = (ArrayList<Models.MoodModel>) response;

        //use the created custom AdapterView on the list view
        MoodAdapterView moodAdapterView=new MoodAdapterView(MoodActivity.this,R.layout.list_row,moodModels);
        lvMoods.setAdapter(moodAdapterView);
            }
            @Override
            public void OnError(String error) {
                Toast.makeText(MoodActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //create a custom made Adapter View, namely 'MoodAdapterView', this will allow the ListVIew to accept Images and Texts
    public  class MoodAdapterView extends ArrayAdapter<Models.MoodModel>{

        private Context context;
        private int resource;
        public MoodAdapterView(@NonNull Context context, int resource, @NonNull ArrayList<Models.MoodModel> objects) {
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

            imageView.setImageResource(getItem(position).getMoodIntegerImage());
            txtMoodName.setText(getItem(position).getMoodName());
            txtMoodDate.setText(getItem(position).getMoodDate());


            return convertView;
        }
    }
}