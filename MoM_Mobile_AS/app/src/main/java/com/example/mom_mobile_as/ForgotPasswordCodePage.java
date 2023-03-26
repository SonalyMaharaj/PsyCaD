package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;
public class ForgotPasswordCodePage extends AppCompatActivity {
    private Button btnVerify;
    private EditText txtUserOTPInput;
    private String generatedOTP=new String(); //this OTP will not be stored on the database, will generate only when the page is loaded, and when the User request new OTP generation
    private DataServiceReference client=new DataServiceReference(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_code_page);
        getSupportActionBar().hide();//hide the App name Title bar

        btnVerify=findViewById(R.id.btnVerify);
        txtUserOTPInput=findViewById(R.id.txtMyOTP);
        //get passed student
        Models.StudentModel student=GetPassedStudent();
        //send email with this OTP to the given email address
        String StudentEmail=student.getStudentEmail().toString(); //only one email address
        //Send email to the Student and receive OTP
        client.sendEmail(StudentEmail, new DataServiceReference.IMoMVolleyListener() {
            @Override
            public void OnResponse(Object response) {
                try{
                    Models.OTP otp=(Models.OTP) response;
                    generatedOTP=otp.getGeneratedOTP(); //initialize the generatedOTP

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void OnError(String error) {
                Toast.makeText(ForgotPasswordCodePage.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        btnVerify.setOnClickListener(e->{
            //Confirm the provided OTP with the one generated
            String otp1=Secrecy.HashPassword(txtUserOTPInput.getText().toString()); //use the HashPassword function to hash the OTP
            String otp2=generatedOTP.toString();
            if((otp1).equals(otp2)){
                //if the OTPs match, Pass the StudentModel forward to the NewPassword Intent and redirect
                OpenNewPasswordPage(student);
            }
            else{
                //OTP does not Match
                Toast.makeText(this,"OTP does not Match",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendEmail(String studentEmail,String body) throws Exception {
        /// watch: https://www.youtube.com/watch?v=xtZI23hxetw&t=357s

        EmailSender emailSender=new EmailSender(this);

        emailSender.sendEmail(studentEmail,body); //send email to student

    }
    public Models.StudentModel GetPassedStudent(){
        Models.StudentModel student = (Models.StudentModel) getIntent().getSerializableExtra("student");
        return student;
    }


    public void OpenNewPasswordPage(Models.StudentModel student){
        Intent forgotPswrd_Intent = new Intent(this, ForgotPasswordNewPass.class);
        forgotPswrd_Intent.putExtra("student",student);
        startActivity(forgotPswrd_Intent);
    }





}