package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Random;
public class ForgotPasswordCodePage extends AppCompatActivity {
    private Button btnVerify;
    private EditText txtUserOTPInput;
    private String generatedOTP; //this OTP will not be stored on the database, will generate only when the page is loaded, and when the User request new OTP generation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_code_page);
        getSupportActionBar().hide();//hide the App name Title bar

        btnVerify=findViewById(R.id.btnVerify);
        txtUserOTPInput=findViewById(R.id.txtMyOTP);
        //Generate OTP
        generatedOTP=generateOTP();
        //get passed student
        Models.StudentModel student=GetPassedStudent();
        //send email with this OTP to the given email address
        String StudentEmail=student.getStudentEmail().toString(); //only one email address
        String emailBody=generateEmailBody(student.getStudentName());
        try {
            //TO DO: Send email that contains OTP to the Student
            //Toast.makeText(ForgotPasswordCodePage.this,generatedOTP,Toast.LENGTH_LONG).show();
            //TODO: Send email that contains OTP to the Student
            //sendEmail(StudentEmail,"MindMatter ForgotPassword OTP",emailBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnVerify.setOnClickListener(e->{
            //Confirm the provided OTP with the one generated
            String input=txtUserOTPInput.getText().toString();
            Boolean OTPMatch=false;
            if(generatedOTP.equals(input)){
                OTPMatch=true;
            }

            //if the OTPs match, Pass the StudentModel forward to the NewPassword Intent and redirect
            if(OTPMatch){
                OpenNewPasswordPage(student);
            }
            else{
                //OTP does not Match
                Toast.makeText(this,"OTP does not Match",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendEmail(String studentEmail, String subject,String body) throws Exception {
        /// watch: https://www.youtube.com/watch?v=xtZI23hxetw&t=357s

        EmailSender emailSender=new EmailSender(ForgotPasswordCodePage.this,getAssets().open("secretclient.json"));
        emailSender.sendMail("psycadotponly@gmail.com",studentEmail,subject,body);

    }

    public String generateEmailBody(String studentName){
        //this function generates a personalised email body for the student,
        String emailBody="Hi "+studentName+" \n\n"+"This is your OTP for renewing your MindOverMatter Password" +
                "\n\n"+generatedOTP.toUpperCase()+"\n\n"+" stay safe and remember we will not send you an email asking personal details" +
                "\n\n"+"MindOverMatter";

        return  emailBody;
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

    private String generateOTP(){
        //this function will generate a unique OTP of 5 digits
        Random rand=new Random();

        int randNum=0;
        String generatedOTP="";
        for(int i=0;i<=5;i++){
            //generate a unique number 5 times
            randNum=rand.nextInt(9);
            generatedOTP+=randNum; //append the generated digit to the OTP digits
        }

        return generatedOTP;
    }




}