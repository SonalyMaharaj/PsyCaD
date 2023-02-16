package com.example.mom_mobile_as;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//TODO: THIS FUNCTION WILL HANDLE THE DISPLAY OF THE LIST OF CALL LOGS
public class CallLogActivity extends AppCompatActivity
{
    ImageView iv_arrow;
    ListView lvCallLogs;
    ProgressBar progressBar;
    DataServiceReference client=new DataServiceReference(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log);

        lvCallLogs=findViewById(R.id.lvCallLogs);
        progressBar=findViewById(R.id.progressBar);
        iv_arrow = findViewById(R.id.backArrow);

        iv_arrow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent(CallLogActivity.this, CallActivity.class);
                startActivity(intent);
            }
        });

        //Get Call Logs
        client.getCallLogs(new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {
                ArrayList<Models.CallLogModel> callLogModels=(ArrayList<Models.CallLogModel>) response; //Down Cast the response to CallLogModels arrayList
                lvCallLogs.setAdapter(new CallLogAdapterView(CallLogActivity.this, R.layout.list_row,callLogModels)); //Set Adapter

                //REMOVE THE Progress Bar
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void OnError(String error) {
                Toast.makeText(CallLogActivity.this,error,Toast.LENGTH_LONG).show();
            }
        });
    }

    //create a custom made Adapter View, namely 'CallLogAdapterView', this will allow the ListVIew to accept Images and Texts
    public  class CallLogAdapterView extends ArrayAdapter<Models.CallLogModel> {

        private Context context;
        private int resource;
        public CallLogAdapterView(@NonNull Context context, int resource, @NonNull ArrayList<Models.CallLogModel> objects) {
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

            imageView.setImageResource(R.drawable.phone_icon);
            txtMoodName.setText(getItem(position).getTelHolder());
            txtMoodDate.setText(getItem(position).getCallDate()); //+" "+ getItem(position).getMoodTime()+":00:00"); //set the date and the time


            return convertView;
        }
    }
}

