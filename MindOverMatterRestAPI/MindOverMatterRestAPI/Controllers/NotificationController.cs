using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.Serialization;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class NotificationController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Notification/GetNotification")]
        [HttpGet]
        public IHttpActionResult GetNotification(int StudentNumber)
        {
            var notific = (from a in db.Notifications
                           where a.IsActive.Equals("1")
                           && a.StudentNumber.Equals(StudentNumber)
                           select a).FirstOrDefault(); //get the 1st notification that is active in the table

            if (notific==null) {
                return Ok(false); //if there is no active notification
            }
            var deact=DeActivateNotification(notific.Id); //deactivate exercise
            
            NotificationClass notificationClass = new NotificationClass { ID=notific.Id,DateOfNotification=notific.DateOfNotification,Description=notific.Description,IsActive=notific.IsActive};
            return Ok(notificationClass);
        }

        [Route("api/Notification/DeActivateNotification")]
        [HttpGet]
        public IHttpActionResult DeActivateNotification(int notificationId)
        {
            var notific = (from a in db.Notifications
                           where a.IsActive.Equals("1")
                           && a.Id.Equals(notificationId)
                           select a).FirstOrDefault(); //get the 1st notification that is active in the table

            
                notific.IsActive = "0"; //disable notification
            

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException exerption)
            {
                exerption.GetBaseException();

                return Ok(false);
            }


        }

        [Route("api/Notification/GetAllNotifications")]
        [HttpGet]
        public IHttpActionResult GetAllNotifications()
        {
            List<NotificationClass> notificationClasses = new List<NotificationClass>();
            var notifics = (from a in db.Notifications
                           select a); //get the 1st notification that is active in the table

            foreach (var a in notifics) {
                NotificationClass notificationClass = new NotificationClass { ID = a.Id, DateOfNotification = a.DateOfNotification, Description = a.Description, IsActive = a.IsActive };
                notificationClasses.Add(notificationClass);

            }
            return Ok(notificationClasses);

        }

        [Route("api/Notification/AddNotification")]
        [HttpPost]
        public IHttpActionResult AddNotification(NotificationClass notification)
        {
            Notification newnotification = new Notification
            {
                DateOfNotification = DateTime.Now,
                PsychologistId=notification.PsychologistId,
                StudentNumber=notification.StudentNumber,
                Description = notification.Description,
                IsActive = "1"
            };

            db.Notifications.InsertOnSubmit(newnotification);

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException exerption)
            {
                exerption.GetBaseException();

                return Ok(false);
            }

        }





    }
}
