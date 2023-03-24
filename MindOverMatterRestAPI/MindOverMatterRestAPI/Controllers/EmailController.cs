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
    public class EmailController : Controller
    {

        [Route("api/Email/SendMail")]
        [HttpGet]
        public IHttpActionResult SendMail(String studentEmail)
        {
            SendMail();
            return Ok("");
        }

        public static async Task SendMail()
        {
            var message = new MimeMessage();
            message.From.Add(new MailboxAddress("Reports", "reports@vxworkflow.co.za"));
            message.To.Add(new MailboxAddress("peaceful", "peacefulmoyo7@gmail.com"));
            message.Subject = "Test email from C# using MailKit";
            message.Body = new TextPart("plain")
            {
                Text = "THIS IS AN EMAIL BODY TRYOUT SOLOMON"
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

        //public
    }
}