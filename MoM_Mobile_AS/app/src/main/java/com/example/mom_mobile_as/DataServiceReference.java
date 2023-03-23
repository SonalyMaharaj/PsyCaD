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

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

public class DataServiceReference {

    private Context context;
    Models.StudentModel student;
    private String APIURL="http://172.21.224.1/api/";
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
                                response.get("StudentDOB").toString(),
                                response.get("Campus").toString()
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

    public void AddStudent(Models.StudentModel Student, IMoMVolleyListener volleyListener){
        String url=APIURL+"Student//AddStudent";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    //if the return value is true
                    volleyListener.OnResponse("Successfully registered");
                }
                else{
                    //if false is returned might be because the Student Number already exists in the database
                    volleyListener.OnError("registration failed, Student already registered");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError("Student Might Already exist, go to login or forgot password \n"+error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("StudentNumber", String.valueOf(Student.getStudentNumber()));
                params.put("StudentName",Student.getStudentName());
                params.put("StudentSurname", Student.getStudentSurname());
                params.put("StudentEmail",Student.getStudentEmail());
                params.put("StudentPassword",Secrecy.HashPassword(Student.getStudentPassword()));
                params.put("StudentGender","X");
                params.put("StudentDOB","01/01/2023");
                params.put("StudentQualification", "X");
                params.put("Campus",Student.getCampus());

                return  params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void getStudent(String StudentNumber,IMoMVolleyListener volleyResponseListener){
        //method to get a particular Student using StudentNumber
        SessionManager sessionManager=new SessionManager(context);//

        String url=APIURL+"Student//GetStudent?StudentNumber="+sessionManager.getSession();

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
                                    response.get("StudentDOB").toString(),
                                    response.get("Campus").toString()
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

    //This function returns a List of Students.
    public void getStudents(IMoMVolleyListener volleyListener){
        String url=APIURL+"Student//GetStudents";

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //create an ArrayList of StudentModels
                ArrayList<Models.StudentModel> studentModels=new ArrayList<>();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject=(JSONObject)response.get(i);//get JSON object at index i
                        Models.StudentModel studentModel=new Models.StudentModel();
                        studentModel.setStudentNumber(Integer.parseInt(jsonObject.get("StudentNumber").toString()));
                        studentModel.setStudentName(jsonObject.get("StudentName").toString());
                        studentModel.setStudentSurname(jsonObject.get("StudentSurname").toString());
                        studentModel.setStudentEmail(jsonObject.get("StudentEmail").toString());
                        studentModel.setStudentDOB(jsonObject.get("StudentDOB").toString());
                        studentModel.setCampus(jsonObject.get("Campus").toString());
                        studentModels.add(studentModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        volleyListener.OnError(e.toString());
                    }

                }
                volleyListener.OnResponse(studentModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }


    public void EditStudent(Models.StudentModel student, IMoMVolleyListener volleyListener){
        String url=APIURL +"Student/EditStudent";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    volleyListener.OnResponse("Successful");
                }
                else{
                    volleyListener.OnError("Failed to edit user");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("StudentNumber", String.valueOf(student.getStudentNumber()));
                params.put("StudentName",student.getStudentName());
                params.put("StudentSurname", student.getStudentSurname());
                params.put("StudentEmail",student.getStudentEmail());
                params.put("StudentPassword",Secrecy.HashPassword(student.getStudentPassword()));
                params.put("StudentGender","X");
                params.put("StudentDOB","01/01/2023");
                params.put("StudentQualification", "X");

                return  params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void ChangePassword(Models.StudentModel student, IMoMVolleyListener volleyListener){
        String url=APIURL +"Student/ChangePassword";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    volleyListener.OnResponse("Successful");
                }
                else{
                    volleyListener.OnError("Failed to edit user");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("StudentNumber", String.valueOf(student.getStudentNumber()));
                params.put("StudentPassword",Secrecy.HashPassword(student.getStudentPassword()));

                return  params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    //THis function will request to change the Campus the Student wants to book from.

    public void ChangeCampus(String Campus, IMoMVolleyListener volleyListener){
        String url=APIURL +"Student/ChangeCampus";

        SessionManager sessionManager=new SessionManager(context);
        int StudentNumber=sessionManager.getSession();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    volleyListener.OnResponse("Successful");
                }
                else{
                    volleyListener.OnError("Failed to edit user");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("StudentNumber", String.valueOf(StudentNumber));
                params.put("Campus",Campus);
                return  params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
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

        SessionManager sessionManager=new SessionManager(context);

        String url=APIURL+"Mood/GetMoods?StudentNumber="+sessionManager.getSession();

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Models.MoodModel> moodModels=new ArrayList<>();

                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object= (JSONObject) response.get(i); //get JSON OBJECT
                        String strDate= object.get("MoodDate").toString(); //get a Mood
                        StringTokenizer stringTokenizer=new StringTokenizer(strDate,"T"); //take only the date, without the default time 00:00:00

                        String strTime=object.get("MoodTime").toString();
                        Models.MoodModel mood=new Models.MoodModel(object.get("MoodEmotion").toString(),stringTokenizer.nextToken(),strTime,Integer.parseInt(object.get("MoodIntegerImage").toString()));
                        moodModels.add(mood); //add mood to the list of moods
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //TODO: CHECK THE API AND ENSURE THAT THE DATABASE HAS AN OPTION FOR THE INTEGER IMAGE VAlUES
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
        String url=APIURL+"Mood/LogMood";

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



    public void LogCall(String CallNumber, String CallName, IMoMVolleyListener volleyListener){

        String url=APIURL+"CallLog/LogCall";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("true")){
                    volleyListener.OnResponse("Calling...");
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
                params.put("TelHolder", CallName);
                params.put("TelNumber",CallNumber);
                params.put("StudentNumber", String.valueOf(StudentNum));
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    //This function will request Call Logs from the database, specifically for this Student

    public void getCallLogs(IMoMVolleyListener volleyListener){
        SessionManager sessionManager=new SessionManager(context); //create a Session Manager Object

        int StudentNumber=sessionManager.getSession(); //get Student Number stored at in the SessionManager
        String url=APIURL+"CallLog/getCallLogs?StudentNumber="+StudentNumber;

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Models.CallLogModel> callLogModels=new ArrayList<>();

                for(int i=0;i<response.length();i++){

                    try {
                        //convert to JSON object
                        JSONObject jsonObject= (JSONObject) response.get(i);
                        //create a CallLogModel
                        Models.CallLogModel callLog=new Models.CallLogModel();
                        //POPULATE callLog fields
                        callLog.setCallID(Integer.parseInt(jsonObject.get("CallId").toString()));
                        callLog.setTelHolder(jsonObject.get("TelHolder").toString());
                        callLog.setTelNumber(jsonObject.get("TelNumber").toString());
                        callLog.setCallTime(jsonObject.get("CallTime").toString());
                        callLog.setCallDate(jsonObject.get("CallDate").toString());

                        callLogModels.add(callLog);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //call back
                volleyListener.OnResponse(callLogModels);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest); //add request to request Queue
    }

    public void addMedicine(Models.Medicine medicine, IMoMVolleyListener volleyListener){
        String url=APIURL+"Medicine/AddMedicine";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    //send back a success message
                    volleyListener.OnResponse("Medicine saved Successfully");
                }
                else{
                    volleyListener.OnError("Medicine failed to save");
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
                params.put("NameOfMedication",medicine.getMedicineName());
                params.put("Category", medicine.getMedicineCategory());
                params.put("StudentNumber", String.valueOf(StudentNum));
                params.put("NameOfDoctor", medicine.getNameOfDoctor());
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest); //add request to request Queue
    }

    public void EditMedicine(Models.Medicine medicine, IMoMVolleyListener volleyListener){
        String url=APIURL+"Medicine/EditMedicine";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    //send back a success message
                    volleyListener.OnResponse("Medicine saved Successfully");
                }
                else{
                    volleyListener.OnError("Medicine failed to save");
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
                params.put("NameOfMedication",medicine.getMedicineName());
                params.put("Category", medicine.getMedicineCategory());
                params.put("StudentNumber", String.valueOf(StudentNum));
                params.put("NameOfDoctor", medicine.getNameOfDoctor());
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest); //add request to request Queue
    }

    public void GetStudentMedicines(int studentNumber, IMoMVolleyListener volleyListener){
        SessionManager sessionManager=new SessionManager(context); //create a Session Manager Object

        int StudentNumber=sessionManager.getSession(); //get Student Number stored at in the SessionManager
        String url=APIURL+"Medicine/GetMedicines?StudentNumber="+StudentNumber;

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Models.Medicine> medicines=new ArrayList<>();

                for(int i=0;i<response.length();i++){

                    try {
                        //convert to JSON object
                        JSONObject jsonObject= (JSONObject) response.get(i);
                        //create a CallLogModel
                        Models.Medicine medicine=new Models.Medicine();
                        //POPULATE callLog fields
                        medicine.setMedicineID(Integer.parseInt(jsonObject.get("ID").toString()));
                        medicine.setNameOfDoctor(jsonObject.get("NameOfDoctor").toString());
                        medicine.setMedicineName(jsonObject.get("MedicineName").toString());
                        medicine.setMedicineCategory(jsonObject.get("Category").toString());
                        medicine.setStudentNumber(Integer.parseInt(jsonObject.get("ID").toString()));

                        medicines.add(medicine);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //call back
                volleyListener.OnResponse(medicines);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyListener.OnError(error.toString());
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest); //add request to request Queue
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


