using HashPass;
using MailKit.Net.Smtp;
using MailKit.Security;
using MimeKit;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;
using HttpGetAttribute = System.Web.Http.HttpGetAttribute;
using RouteAttribute = System.Web.Mvc.RouteAttribute;

namespace MindOverMatterRestAPI.Controllers
{
    public class EmailController : ApiController
    {

        [Route("api/Email/SendMail")]
        [HttpGet]
        public async Task<IHttpActionResult> SendMailAsync(String studentEmail)
        {
            //generate OTP and return it to the client
            String OTPString=generateOTP();
            //generate body
            String body=generateEmailBody(OTPString);
            //send the email
            await SendMail(studentEmail,body);
            //create an object that will contain this hashed OTP so that it does not get corrupted
            OTP otp = new OTP { generatedOTP=Secrecy.HashPassword(OTPString)};
            return Ok(otp);
        }

        public static async Task SendMail(String studentEmail,String body)
        {
            var message = new MimeMessage();
            message.From.Add(new MailboxAddress("MoM OTP ...", "psycadotponly@gmail.com"));
            message.To.Add(new MailboxAddress("PsyCaDStudent", studentEmail));
            message.Subject = "MindOverMatter: reset password OTP";
            message.Body = new TextPart("plain")
            {
                Text = body
            };

            using (var client = new SmtpClient())
            {
                client.Timeout = 10000;

                client.ServerCertificateValidationCallback = (s, c, h, e) => true;
                await client.ConnectAsync("mail.vxworkflow.co.za", 465, SecureSocketOptions.SslOnConnect);
                await client.AuthenticateAsync("reports@vxworkflow.co.za", "LbNC^wRS97N!u^^o@");
                await client.SendAsync(message);
                await client.DisconnectAsync(true);
            }
        }

        private String generateOTP()
        {

            String[] bigAlphabets = ("A;B;C;D;E;F;G;H;I;J;K;L;M;N;O;P;Q;R;S;T;U;V;W;X;Y;Z").Split(';'); //seperate values from A to Z by a ";" and split them into an array of strings
            String[] smallAlphabets = ("a;b;c;d;e;f;g;h;i;j;k;l;m;n;o;p;q;r;s;t;u;v;w;x;y;z").Split(';'); //seperate values from A to Z by a ";" and split them into an array of strings
            String[] digits = ("1;2;3;4;5;6;7;8;9;0").Split(';');

            //create an OTP of 6 characters.
            Random random = new Random();
            //randomly choose 2 values from big_alphabets
            int bigAlphabetIndex = random.Next(1,200) % bigAlphabets.Length ;
            String bigAlphabet1 = bigAlphabets[bigAlphabetIndex];
            bigAlphabetIndex = random.Next(1, 200) % bigAlphabets.Length ;//generate another random index
            String bigAlphabet2 = bigAlphabets[bigAlphabetIndex];

            //randomly choose 2 values from small_alphabets
            int smallAlphabetIndex = random.Next(1, 200) % smallAlphabets.Length;
            String smallAlphabet1 = smallAlphabets[smallAlphabetIndex];
            smallAlphabetIndex = random.Next(1, 200) % smallAlphabets.Length; //generate another random index
            String smallAlphabet2 = smallAlphabets[smallAlphabetIndex]; ;
            //randomly choose 2 values from digits
            int digitsIndex = random.Next(1, 200) % digits.Length;
            String digit1 = digits[digitsIndex];
            digitsIndex = random.Next(1, 200) % digits.Length; //generate another random index
            String digit2 = digits[digitsIndex];

            //combine the values together and return
            String finalOTP = bigAlphabet1+digit1+bigAlphabet2+smallAlphabet1+digit2+smallAlphabet2;
            return finalOTP;
        }

        private String generateEmailBody(String OTP) {
            String body = "Hello " + Environment.NewLine + Environment.NewLine +
                "Find the following OTP in Order to reset your password" + Environment.NewLine+Environment.NewLine + "OTP: " + OTP +Environment.NewLine+"" + Environment.NewLine +
                "Thank you!" + Environment.NewLine +
                "MindOverMatter";

            return body;
        }

        private class OTP
        {
            public String generatedOTP { get; set; }
        }
    }

}