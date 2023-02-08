package com.example.mom_mobile_as;

import android.content.Context;
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

import java.util.ArrayList;

public class DataServiceReference {

    private Context context;
    Models.StudentModel student;

    public interface IMoMVolleyListener{
        public void OnResponse(Object response);
        public  void  OnError(String error);
    }

    //Constructor
    public  DataServiceReference(Context context){
        this.context=context;
    }

    public void loginStudent(String username, String password,IMoMVolleyListener volleyListener){
        String url="http://192.168.56.1/api/Login/loginStudent?username="+username+"&password="+Secrecy.HashPassword(password);

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
        String url="http://192.168.56.1//api//Student//GetStudent?StudentNumber=200001";

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
        String url="http://192.168.56.1/api/Diary/GetStudentDiaryEntries?StudentNumber="+sessionManager.getSession();

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
                        Toast.makeText(context,diary.getDiaryTitle(),Toast.LENGTH_LONG).show();

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



}


