package com.example.mom_mobile_as;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    //THIS IS A LOGIN PAGE
    private Button btnLogin;
    private EditText txtUsername;
    private  EditText txtPassword;
    DataServiceReference client; //client that will be communicating with the API on our behalf
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsername=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        client=new DataServiceReference(MainActivity.this);

        btnLogin.setOnClickListener(e->{
            //login Student

                client.loginStudent(txtUsername.getText().toString(), txtPassword.getText().toString(), new DataServiceReference.IMoMVolleyListener() {
                    @Override
                    public void OnResponse(Object response) {
                        //after this function has been called back(from DataServiceReference LoginStudent function) do the following

                        try {
                            //returns a Student Object
                            Models.StudentModel student = (Models.StudentModel) response; //(might throw an Invalid cast exception)

                            //save the Session of the Logged in User
                            SessionManager sessionManager=new SessionManager(MainActivity.this);
                            sessionManager.saveSession(student);

                            //redirect to the Home Screen
                            openHomeActivity();
                            //display welcome message
                            Toast.makeText(MainActivity.this, "Welcome "+student.StudentName, Toast.LENGTH_SHORT).show();

                        }catch (Exception exception)
                        {
                            exception.printStackTrace();
                            Toast.makeText(MainActivity.this, "Login Failed ", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void OnError(String error) {
                        //after this function has been called back do the following
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                        txtPassword.setText(""); //clear the password field
                    }
                });


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkSession();
    }

    private void checkSession() {

        //check if Student is Logged In, move to the Home Page

        SessionManager sessionManager=new SessionManager(MainActivity.this);
        int StudentNumber=sessionManager.getSession();

        if(StudentNumber!=-1){
            //if StudentNumber is not equal to -1 then Student number is still logged in
            openHomeActivity();
        }

    }

    public void openHomeActivity(){
        //this code will redirect to the HomeActivity page
        Intent Home_Intent = new Intent(this, HomeActivity.class);
        Home_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Home_Intent);
    }

    public void SaveSession(){

    }

    public void openRegisterActivity(View view) {
        Intent Register_intent = new Intent(this, RegisterActivity.class);
        startActivity(Register_intent);
    }

    public void openForgotPasswordActivity(View view){
        Intent forgotPswrd_Intent = new Intent(this, ForgotPasswordEmailPage.class);
        startActivity(forgotPswrd_Intent);
    }
}