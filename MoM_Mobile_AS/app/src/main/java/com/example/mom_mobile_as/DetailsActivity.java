package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity
{
    private DataServiceReference client=new DataServiceReference(this);
    private GridView gvCampus;
    private Button btnSaveDetails;
    private  int ClickedCampusPosition;
    Integer[] image={
            R.drawable.grinning, R.drawable.sob,
            R.drawable.no_mouth, R.drawable.face_with_thermometer,
    };

    String[] CampusNames={
            "APK","APB","DFC","SWT"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        gvCampus = findViewById(R.id.gvCampus);
        btnSaveDetails = (Button) findViewById(R.id.btnSaveDetails);

        //CREATE A GRID VIEW AND POPULATE EMOJIS TO IT
        gvCampus.setAdapter(new ImageAdapterGridView(this));

        gvCampus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClickedCampusPosition = i;
                Toast.makeText(DetailsActivity.this,CampusNames[i], Toast.LENGTH_SHORT).show();
            }
        });
        if(ClickedCampusPosition!=-1){
        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the Campus details

                client.ChangeCampus(CampusNames[ClickedCampusPosition], new DataServiceReference.IMoMVolleyListener() {
                    @Override
                    public void OnResponse(Object response) {
                        Toast.makeText(DetailsActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        openHomeActivity();
                    }

                    @Override
                    public void OnError(String error) {
                        Toast.makeText(DetailsActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        }
        else {
            Toast.makeText(DetailsActivity.this,"Choose Campus", Toast.LENGTH_SHORT).show();
        }
    }


    public void openHomeActivity()
    {
        Intent Home_intent = new Intent(this, HomeActivity.class);
        startActivity(Home_intent);
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
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
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