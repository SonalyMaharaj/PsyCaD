package com.example.mom_mobile_as;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordNewPass extends AppCompatActivity {
    DataServiceReference client=new DataServiceReference(this);
    private Button btnSave;
    private EditText txtPassword1;
    private EditText txtPassword2;
    private String password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_new_pass);

        btnSave=findViewById(R.id.btnSave);
        txtPassword1=findViewById(R.id.txtPassword);
        txtPassword2=findViewById(R.id.txtConfirmPassword);

        btnSave.setOnClickListener(e->{
            Models.StudentModel student=GetPassedStudent();
            if (ConfirmPasswords(txtPassword1.getText().toString(), txtPassword2.getText().toString())) {
                //if the passwords match, then continue registration
                password =txtPassword1.getText().toString();
                //if the passwords match, then Edit/change the student password before passing it to the function
                student.setStudentPassword(password);
                client.ChangePassword(student, new DataServiceReference.IMoMVolleyListener() {
                    @Override
                    public void OnResponse(Object response) {
                        //redirect to the login page, once the password has been successully changed
                        OpenLoginPage();
                    }

                    @Override
                    public void OnError(String error) {
                        Toast.makeText(ForgotPasswordNewPass.this, error, Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                //don't continue, but prompt user to rectify
                Toast.makeText(ForgotPasswordNewPass.this, "Passwords do not match", Toast.LENGTH_LONG).show();

            }
        });
    }

    public boolean ConfirmPasswords(String password1, String password2){
        //This function checks whether the passwords in the two fields are the same(passwrod and Confirm password)
        return password1.equals(password2) ? true : false;
    }

    public void OpenLoginPage(){
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    public Models.StudentModel GetPassedStudent(){
        Models.StudentModel student = (Models.StudentModel) getIntent().getSerializableExtra("student");
        return student;
    }

}