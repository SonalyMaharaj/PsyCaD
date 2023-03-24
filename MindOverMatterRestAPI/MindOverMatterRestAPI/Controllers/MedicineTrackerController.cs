using MailKit.Net.Smtp;
using MailKit.Security;
using MimeKit;
using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.Serialization;
using System.Threading.Tasks;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class MedicineTrackerController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Student/SendMail")]
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

        [Route("api/Medicine/GetMedicine")]
        [HttpGet]
        public IHttpActionResult GetMedicine(int medicineId)
        {

            var med = (from a in db.MedicineTrackers
                       where a.Id.Equals(medicineId)
                       select a).FirstOrDefault();

            if (med==null) 
            {
                return Ok(false);
            }

            MedicineTrackerClass medicineTrackerClass = new MedicineTrackerClass {ID=med.Id,Date=med.Date,NameOfMedicine=med.NameOfMedication,Category=med.Category,StudentNumber=med.StudentNumber,NameOfDoctor=med.NameOfDoctor, IsActive=Convert.ToInt32(med.IsActive) };

            return Ok(medicineTrackerClass);
        }
        
        [Route("api/Medicine/GetMedicines")]
        [HttpGet]
        public IHttpActionResult GetMedicines(int StudentNumber)
        {
            List<MedicineTrackerClass> medicines = new List<MedicineTrackerClass>();
            var list = (from a in db.MedicineTrackers
                        where a.StudentNumber.Equals(StudentNumber)
                        select a);

            foreach (var med in list) {
                MedicineTrackerClass medicineTrackerClass = new MedicineTrackerClass { ID = med.Id, Date = med.Date, NameOfMedicine = med.NameOfMedication, Category = med.Category, StudentNumber = med.StudentNumber, NameOfDoctor = med.NameOfDoctor, IsActive = Convert.ToInt32(med.IsActive) };

                medicines.Add(medicineTrackerClass);
            }

            return Ok(medicines);


        }
        
        [Route("api/Medicine/AddMedicine")]
        [HttpPost]
        public IHttpActionResult AddMedicine(MedicineTrackerClass medicine)
        {
            if (medicine==null) {
                return Ok(false);
            }

            MedicineTracker medicineTracker = new MedicineTracker 
            {
            Date = DateTime.Now,
            NameOfMedication = medicine.NameOfMedicine,
            Category = medicine.Category,
            StudentNumber = medicine.StudentNumber,
            NameOfDoctor = medicine.NameOfDoctor,
            IsActive=1,
        };
            db.MedicineTrackers.InsertOnSubmit(medicineTracker);
            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
          
        }
        
        [Route("api/Medicine/RemoveMedicine")]
        [HttpGet]
        public IHttpActionResult RemoveMedicine(int medicineID)
        {
            var medicine = (from a in db.MedicineTrackers
                            where a.Id.Equals(medicineID)
                            select a).FirstOrDefault();

            if (medicine == null) {
                return Ok(false);
            }

            medicine.IsActive = 0; //deactivate
            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
          
        } 
        
        [Route("api/Medicine/EditMedicine")]
        [HttpPost]
        public IHttpActionResult EditMedicine(MedicineTrackerClass medicine)
        {

            if (medicine==null) {
                return Ok(false);
            }

            var med = (from a in db.MedicineTrackers
                       where a.Id.Equals(medicine.ID)
                       select a).FirstOrDefault();

            if (med==null) {
                return Ok(false);
            }

            med.Date = DateTime.Now;
            med.NameOfMedication = medicine.NameOfMedicine;
            med.Category = medicine.Category;
            med.StudentNumber = medicine.StudentNumber;
            med.NameOfDoctor = medicine.NameOfDoctor;
            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
          
        }


    }
}
