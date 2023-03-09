package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.StringTokenizer;

public class RegisterActivity extends AppCompatActivity
{
    DataServiceReference client=new DataServiceReference(RegisterActivity.this);
    private Button btnRegsiter;
    private EditText txtNameSurname;
    private EditText txtStudentNumber;
    private EditText txtEmail;
    private EditText txtPassword1;
    private EditText txtPassword2;
    String studentName="";
    String studentSurname="";
    String password="";
    Integer StudentNumber;
    String studentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegsiter = (Button) findViewById(R.id.buttonSave);
        txtNameSurname=findViewById(R.id.txtNameSurname);
        txtStudentNumber=findViewById(R.id.txtStudentNum);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword1=findViewById(R.id.txtPassword);
        txtPassword2=findViewById(R.id.txtConfirmPassword);



        btnRegsiter.setOnClickListener(e->{
            //Create a Student object and pass it to the register student method

            Models.StudentModel student=setStudent();
            if(student==null){
                Toast.makeText(this,"fill in correct information",Toast.LENGTH_SHORT).show();
            }
            else {
                //Add student
                client.AddStudent(student, new DataServiceReference.IMoMVolleyListener() {
                    @Override
                    public void OnResponse(Object response) {
                        Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        openMainActivity(); //redirect to login page
                    }

                    @Override
                    public void OnError(String error) {
                        Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
    //TODO: restrict the input data on the Entry boxes to deny anything that may promote SQL Injection or so ...
    public Models.StudentModel setStudent(){
        Models.StudentModel student = null;

        //set student number and Email
        try {

            StudentNumber = Integer.parseInt(txtStudentNumber.getText().toString()); //if this is not an integer, it will throw an exception
            //generate email
            if(txtEmail.getText().toString().length()<=1){
                return  null; //if the email field is null
            }
            else if(txtPassword1.getText().toString().length()<=8){
                //if the password entry is empty
                Toast.makeText(RegisterActivity.this, "Enter password with at-least 8 chars",Toast.LENGTH_LONG).show();
                return  null;
            }

            studentEmail = txtEmail.getText().toString();
            //generate name and surname
            if (checkIfThereIsSpace(txtNameSurname.getText().toString())) {
                studentName = getNameAndSurname(txtNameSurname.getText().toString())[0];
                studentSurname = getNameAndSurname(txtNameSurname.getText().toString())[1];
            } else {
                Toast.makeText(RegisterActivity.this, "Enter Name and Surname on the 1st entry field", Toast.LENGTH_LONG).show();
                return null;
            }

            //generate Password
            if (ConfirmPasswords(txtPassword1.getText().toString(), txtPassword2.getText().toString())) {
                //if the passwords match, then continue registration
                password =txtPassword1.getText().toString();
            } else {
                //don't continue, but prompt user to rectify
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                return null;
            }

            student = new Models.StudentModel();
            student.setStudentName(studentName);
            student.setStudentSurname(studentSurname);
            student.setStudentNumber(StudentNumber);
            student.setStudentEmail(studentEmail);
            student.setStudentPassword(password);

        }
        catch(NumberFormatException numberFormatException){
            Toast.makeText(this,"fill in correct student number",Toast.LENGTH_SHORT).show();
            return  null;
        }
        catch(Exception exception){
            Toast.makeText(this,exception.toString(),Toast.LENGTH_LONG).show();
            return  null;
         }
        finally {
            return student;
        }
    }


    public boolean ConfirmPasswords(String password1, String password2){
        //This function checks whether the passwords in the two fields are the same(passwrod and Confirm password)
        return password1.equals(password2) ? true : false;
    }


    public boolean checkIfThereIsSpace(String text){
        //check if the Name and Surname are separated by a space.
        return text.contains(" ") & !text.equals("") ? true : false ;
    }

    //This function seperates the Name and Surname provided
    public String[] getNameAndSurname(String text){
        StringTokenizer stringTokenizer=new StringTokenizer(text," "); //append space at the end of the nameaNDSurname incase a Student did not seperate them
        String[] nameSurname=new String[2];
        nameSurname[0]=stringTokenizer.nextToken();
        nameSurname[1]=stringTokenizer.nextToken();
        return  nameSurname;
    }




    public void openMainActivity()
    {
        Intent Main_intent = new Intent(this, MainActivity.class);
        startActivity(Main_intent);
    }
}