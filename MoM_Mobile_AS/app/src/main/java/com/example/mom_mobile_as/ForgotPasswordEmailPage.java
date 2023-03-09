package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ForgotPasswordEmailPage extends AppCompatActivity {
    DataServiceReference client=new DataServiceReference(this);
    private Button btnNext;
    private EditText txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_email_page);
        btnNext=findViewById(R.id.btnNext);
        txtEmail=findViewById(R.id.txtEmail);
        btnNext.setOnClickListener(e->{
            //get the email address from the user input
            String EmailInput=txtEmail.getText().toString();
            if(EmailInput.length()<=5){ //make sure the input is not empty
                Toast.makeText(this,"Enter Valid Email Address",Toast.LENGTH_SHORT).show();
                return;
            }
            //GET all the students
           client.getStudents(new DataServiceReference.IMoMVolleyListener() {
                @Override
                public void OnResponse(Object response) {
                    //Cast response to ArrayList
                    ArrayList<Models.StudentModel> students= (ArrayList<Models.StudentModel>) response;
                    //check student with this email address
                    Boolean studentExist=false; //this will be a flag if a student exists after the iteration
                    Models.StudentModel ExistingStudent=new Models.StudentModel();
                    for (Models.StudentModel checkStudent:students) {
                        if(EmailInput.equals(checkStudent.getStudentEmail())){
                            ExistingStudent=checkStudent;
                            studentExist=true;
                            break;
                        }
                    }
                    //if the student exists, capture the student model, pass it as a parameter to the intent and redirect to the ForgotPasswordCodePage
                    if(studentExist==true){
                        OpenForgotPasswordCodePage(ExistingStudent);
                    }
                    else{
                        //student email does not exist, it is not registered
                        Toast.makeText(ForgotPasswordEmailPage.this,"Email is not registered",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void OnError(String error) {

                }
            });
        });

    }

    public  void OpenForgotPasswordCodePage(Models.StudentModel student){
        Intent forgotPswrd_Intent = new Intent(this, ForgotPasswordCodePage.class);
        forgotPswrd_Intent.putExtra("student",student);
        startActivity(forgotPswrd_Intent);
    }
}