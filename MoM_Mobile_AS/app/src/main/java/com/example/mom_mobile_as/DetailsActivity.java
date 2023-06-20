package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity
{
    private DataServiceReference client=new DataServiceReference(this);
    private Button btnSaveDetails;
    private Spinner psychologistSpinner;
    private ArrayList<Models.PsychologistModel> pyschologists;
    private String selelctedPsychologist;
    private  int ClickedCampusPosition;


    String[] CampusNames={
            "APK","APB","DFC","SWC"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();
        pyschologists=new ArrayList<>(); //this list will store all the available psychologists
        ClickedCampusPosition=-1;//initialize the campus index to -1 at load time.
        //gvCampus = findViewById(R.id.gvCampus);
        btnSaveDetails = (Button) findViewById(R.id.btnSaveDetails);
        psychologistSpinner=findViewById(R.id.psychologistSpinner);

        //populate the Psychologists
        client.getPsychologists(new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {
                pyschologists=(ArrayList<Models.PsychologistModel>) response; //downcast returned value
            }

            @Override
            public void OnError(String error) {
                Toast.makeText(DetailsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        psychologistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if the spinner is selected at start up.

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the Campus details

                if(ClickedCampusPosition!=-1) //if campus clicked == true
                {
                client.ChangePsychologist(CampusNames[ClickedCampusPosition],getSelelctedPsychologist().getPsychologistID(), new DataServiceReference.IMoMVolleyListener() {
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
            else {
                Toast.makeText(DetailsActivity.this,"Choose Campus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void generatePsychologistsList(String CampusName){
        ArrayList<Models.PsychologistModel> pyschlist=new ArrayList<>(); //store list of Psychologists at the given campus
        try {

            for (Models.PsychologistModel psychologist : pyschologists) {
                if(psychologist.getCampus().toString().equals(CampusName)){
                    //if the Psychologist is registered at this campus,add to List
                    pyschlist.add(psychologist);
                }
            }
        }
        catch(Exception exception){

            Toast.makeText(DetailsActivity.this, exception.toString(),Toast.LENGTH_LONG ).show();
        }

        //populate the spinner
        ArrayAdapter<Models.PsychologistModel> adapter=new ArrayAdapter<Models.PsychologistModel>(this, android.R.layout.simple_list_item_1,pyschlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psychologistSpinner.setAdapter(adapter);
    }

    private Models.PsychologistModel getSelelctedPsychologist(){
        Models.PsychologistModel psychologist=new Models.PsychologistModel();
        if(psychologistSpinner.isSelected()){
            psychologist= (Models.PsychologistModel) psychologistSpinner.getSelectedItem(); //get the currently selected item
        }
        else{

        }
        return  psychologist;
    }

    private void DisplaySelectedCampus(){
        //display the currently chosen campus
        String campus= CampusNames[ClickedCampusPosition];
        Toast.makeText(DetailsActivity.this, campus,Toast.LENGTH_SHORT).show();
    }

    public void openHomeActivity()
    {
        Intent Home_intent = new Intent(this, HomeActivity.class);
        startActivity(Home_intent);
    }

    public void apkButtonClicked(View view) {
        //generate list of APK psychologists
        ClickedCampusPosition=0; //index for the campus on the CampusNames array
        Toast.makeText(DetailsActivity.this,CampusNames[ClickedCampusPosition],Toast.LENGTH_LONG).show();
        generatePsychologistsList(CampusNames[ClickedCampusPosition]);
    }

    public void apbButtonClicked(View view) {
        //generate List of APB psychologist
        ClickedCampusPosition=1; //index for the campus on the CampusNames array
        Toast.makeText(DetailsActivity.this,CampusNames[ClickedCampusPosition],Toast.LENGTH_LONG).show();
        generatePsychologistsList(CampusNames[ClickedCampusPosition]);
    }
    public void dfcButtonClicked(View view) {
        //generate List of DFC psychologists
        ClickedCampusPosition=2;
        Toast.makeText(DetailsActivity.this,CampusNames[ClickedCampusPosition],Toast.LENGTH_LONG).show();
        generatePsychologistsList(CampusNames[ClickedCampusPosition]);
    }
    public void swtButtonClick(View view) {
        //generate List of SWT psychologist
        ClickedCampusPosition=3;
        Toast.makeText(DetailsActivity.this,CampusNames[ClickedCampusPosition],Toast.LENGTH_LONG).show();
        generatePsychologistsList(CampusNames[ClickedCampusPosition]);
    }


   /* //create a custom made ImageAdapterGridView, this will allow the GridView to accept Images
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
    }*/
}