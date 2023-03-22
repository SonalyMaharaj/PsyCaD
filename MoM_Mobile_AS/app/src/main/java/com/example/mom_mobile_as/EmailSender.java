package com.example.mom_mobile_as;

import static javax.mail.Message.RecipientType.TO;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import com.sendgrid.*;

public class EmailSender {
    private static Context context;
    public EmailSender(Context context){
        this.context=context;
    }


    public void sendEmail(String studentEmail,String emailBody) throws IOException{
        Email from=new Email("pycadotponly@gmail.com");
        Email to=new Email("peacefulmoyo7@gmail.com");
        String subject="MindOverMatter reset-password OTP";
        Content content=new Content("text/plain",emailBody);
        Mail mail=new Mail(from,subject,to,content);
        try{
        SendGrid sg=new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request=new Request();


            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response=sg.api(request);

            Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            //if the email failed to send
            Toast.makeText(context,"OTP generation failed "+e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
