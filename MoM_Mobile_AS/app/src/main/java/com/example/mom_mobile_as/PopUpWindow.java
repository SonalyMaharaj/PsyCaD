package com.example.mom_mobile_as;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class PopUpWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
    }

/*
    public void viewCampuses(View view){
        PopUpWindow popUpWindow=new PopUpWindow();
    }

    public ArrayList<Campus> getUJCampuses(){
        ArrayList<Campus> Ujcampuses=new ArrayList<>();

        //there are only 4 campuses, which are static in nature, so we will just create this data statically
        Ujcampuses.add(new Campus("Kingsway Campus","Location: Auckland Park"));
        Ujcampuses.add(new Campus("Buntin-Road Campus","Location: Auckland Park"));
        Ujcampuses.add(new Campus("Doornfontein Campus","Location: Doornfontein"));
        Ujcampuses.add(new Campus("Soweto Campus","Location: Soweto"));

        return  Ujcampuses;
    }

    //THIS CLASS WILL STORE INFORMATION ABOUT THE UJ CAMPUSES, this will allow dynamic display which will allow same display or fit in any screen size
    public class Campus{
        private int IntegerImage;
        private String campusName;
        private String campusLocation;

        public Campus(String campusName, String campusLocation){
           IntegerImage= R.drawable.ujlogo;
            this.campusName=campusName;
            this.campusLocation=campusLocation;
        }

        public String getCampusName() {
            return campusName;
        }

        public int getIntegerImage() {
            return IntegerImage;
        }

        public String getCampusLocation() {
            return campusLocation;
        }
    }


    //create a custom made Adapter View, namely 'CampusAdapterView', this will allow the ListVIew to accept buttons
    public  class CampusAdapterView extends ArrayAdapter<Campus> {

        private Context context;
        private int resource;
        public CampusAdapterView(@NonNull Context context, int resource, @NonNull ArrayList<Campus> objects) {
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

            imageView.setImageResource(getItem(position).getIntegerImage());
            txtMoodName.setText(getItem(position).getCampusName());
            txtMoodDate.setText(getItem(position).getCampusLocation());


            return convertView;
        }
    }*/
}