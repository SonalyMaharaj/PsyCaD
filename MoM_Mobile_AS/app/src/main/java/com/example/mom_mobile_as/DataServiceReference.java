package com.example.mom_mobile_as;

import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DataServiceReference {

    private Context context;
    Models.StudentModel student;
    private String APIURL="http://172.28.208.1/api/";
    public interface IMoMVolleyListener{
        public void OnResponse(Object response);
        public  void  OnError(String error);
    }

    //Constructor
    public  DataServiceReference(Context context){
        this.context=context;
    }

    public void loginStudent(String username, String password,IMoMVolleyListener volleyListener){
        String url=APIURL+"Login/loginStudent?username="+username+"&password="+Secrecy.HashPassword(password);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //create a Student Model Object from the JSONObject returned from the request and share that Student Model with function caller
                try {
                    //if Student is Null, then Don't login
                    if(response.get("StudentName").toString().equals("NULL")){
                        volleyListener.OnError("Login Failed. \nIncorrect username or password. Try Again!");

                    }
                    else {
                        //if Student is not Null, then pull Student details and loginStudent
                        student =new Models.StudentModel(
                                Integer.parseInt(response.get("StudentNumber").toString()),
                                response.get("StudentName").toString(),
                                response.get("StudentSurname").toString(),
                                response.get("StudentEmail").toString(),
                                response.get("StudentDOB").toString()
                        );

                        volleyListener.OnResponse(student);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }



    public void getStudent(String StudentNumber,IMoMVolleyListener volleyResponseListener){
        //method to get a particular Student using StudentNumber
        String url=APIURL+"Student//GetStudent?StudentNumber=200001";

        //request a JSONObject
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            //create a Student Model Object from the JSONObject returned from the request and share that Student Model with function caller
                            student =new Models.StudentModel(
                                    Integer.parseInt(response.get("StudentNumber").toString()),
                                    response.get("StudentName").toString(),
                                    response.get("StudentSurname").toString(),
                                    response.get("StudentEmail").toString(),
                                    response.get("StudentDOB").toString()
                            );
                            volleyResponseListener.OnResponse(student);
                        }
                        catch (Exception exception){
                            Toast.makeText(context,""+exception,Toast.LENGTH_SHORT).show();

                            volleyResponseListener.OnError(exception.toString());
                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,""+error,Toast.LENGTH_SHORT).show();
                        volleyResponseListener.OnError(error.toString());
                    }
                });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    public  void getMyDiaryEntries(IMoMVolleyListener volleyListener){
        SessionManager sessionManager=new SessionManager(context);
        String url=APIURL+"Diary/GetStudentDiaryEntries?StudentNumber="+sessionManager.getSession();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                ArrayList<DiaryModel> diaryModels=new ArrayList<>(); //diaries
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object= (JSONObject) response.get(i); //get Object at index i
                        //create a DiaryModel
                        DiaryModel diary=new DiaryModel();
                        diary.setDiaryId(Integer.parseInt(object.get("DiaryId").toString()));
                        diary.setDiaryTitle(object.get("Title").toString());
                        diary.setDiaryDate(object.get("Date").toString());
                        diary.setDiaryDescription(object.get("Description").toString());
                        diary.setFlaggedWords(object.get("DiaryFlaggedWords").toString());
                        diary.setStudentNumber(object.get("StudentNumber").toString());
                        //Toast.makeText(context,diary.getDiaryTitle(),Toast.LENGTH_LONG).show();

                        //add this model to the list of models
                        diaryModels.add(diary);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();

                    };
                }

                //call back and send this list of models to the Diary Activity
                volleyListener.OnResponse(diaryModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


    public void getDiary(IMoMVolleyListener volleyListener){
        String url=APIURL+"";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    //create an Add Diary Entry Function.
    //api/Diary/LogDiaryEntry
    //(String Title, int StudentNumber, String Description, DateTime Date)
    public void AddDiary(String Description,IMoMVolleyListener volleyListener) {
        int StudentNumber = GetStudentNumber(); //get Student Number
        Toast.makeText(context,""+StudentNumber, Toast.LENGTH_SHORT).show();
        String DiaryTitle = ""; //generate DiaryTitle
        try {
            DiaryTitle = Description.substring(0, 50); //1st 50 character positions must be stored as title.
        } catch (Exception e) {
            DiaryTitle = Description; //if failed to cut the Diary Description to 50 characters, store the description to be the title.
        }
        finally {


        String url = APIURL+"Diary//LogDiaryEntry";
        String finalDiaryTitle = DiaryTitle;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            response->{
                if(response.toString().equals("true")){
                    volleyListener.OnResponse("Diary Entry Saved");
                }
                else{
                    volleyListener.OnError("Entry Failed to be Saved, try again");
                }

            },
            error->{volleyListener.OnError(String.valueOf(error));}){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("Title", finalDiaryTitle);
                    params.put("StudentNumber", String.valueOf(StudentNumber));
                    params.put("Description", Description);
                    return  params;
                }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    }

    public void EditDairy(int DiaryID, String Description, IMoMVolleyListener volleyListener) {

        String url = APIURL+"Diary//EditDiaryEntry";

        String DiaryTitle = ""; //generate DiaryTitle
        try {
            DiaryTitle = Description.substring(0, 50); //1st 50 character positions must be stored as title.
        } catch (Exception e) {
            DiaryTitle = Description; //if failed to cut the Diary Description to 50 characters, store the description to be the title.
        }
        finally {
            String finalDiaryTitle = DiaryTitle;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("true")){
                    volleyListener.OnResponse("Diary Entry Saved");
                }
                else{
                    volleyListener.OnError("Entry Failed to be Saved, try again");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("DiaryId", String.valueOf(DiaryID));
                params.put("Title", finalDiaryTitle);
                params.put("Description", Description);
                return params;
            }
        };

            MySingleton.getInstance(context).addToRequestQueue(stringRequest); //Add to request queue
    }
    }

    public  void getMoods(IMoMVolleyListener volleyListener){

        String url=APIURL+"Mood/GetMoods?StudentNumber=200001";

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Models.MoodModel> moodModels=new ArrayList<>();

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object= (JSONObject) response.get(i); //get JSON OBJECT
                        Models.MoodModel mood=new Models.MoodModel(object.get("MoodEmotion").toString(),object.get("MoodDate").toString(),Integer.parseInt(object.get("MoodIntegerImage").toString()));
                        moodModels.add(mood); //add mood to the list of moods
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //TODO: CHECK THE API AND ENSURE THAT THE DATABASE HAS AN OPTION FOR THE INTERGER IMAGE VLAUES
                volleyListener.OnResponse(moodModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    //SAVE THE MOOD TO THE API
    public  void LogMood(String MoodName, int MoodIntegerImage, IMoMVolleyListener volleyListener){
        String url=APIURL+"";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("true")){
                    volleyListener.OnResponse("Mood Saved");
                }
                else{
                    volleyListener.OnError("Failed to save Mood");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() {

               SessionManager sessionManager=new SessionManager(context);
               int StudentNum=sessionManager.getSession();
                Map<String, String> params = new HashMap<>();
                params.put("MoodEmotion", MoodName);
                params.put("MoodIntegerImage",String.valueOf(MoodIntegerImage));
                params.put("StudentNumber", String.valueOf(StudentNum));
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    //create an Edit Diary Entry Function

    //HELPER FUNCTIONS
    public  void EditDiary(){

    }
    public int GetStudentNumber(){
        SessionManager sessionManager=new SessionManager(context);

      return sessionManager.getSession(); //Session contains StudentNumber
    }


}


