using HashPass;
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
    public class PsychologistController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();
        [Route("api/Psychologist/EditPsychologist")]
        [HttpPost]
        public IHttpActionResult EditPsychologist(PsychologistClass psychologist)
        {
            if (psychologist == null) {
                //if the given Psychologist null
                return Ok(false);
            }

            var psych = (from c in db.Psychologists
                         where c.PsychologistId.Equals(psychologist)
                         select c).FirstOrDefault();


            psych.PsychologistName = psychologist.PsychologistName.Trim();
            psych.PsychologistSurname = psychologist.PsychologistSurname.Trim();
            psych.PsychologistEmail = psychologist.PsychologistEmail.Trim();
            psych.IsActive = psychologist.IsActive;

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }
        }
        
        [Route("api/Psychologist/ActivatePsychologist")]
        [HttpGet]
        public IHttpActionResult ActivatePsychologist(int psychologistId)
        {
            if (psychologistId < 0) {
                //if the given Psychologist null
                return Ok(false);
            }

            var psychologist = (from c in db.Psychologists
                         where c.PsychologistId.Equals(psychologistId)
                         select c).FirstOrDefault();

            
            psychologist.IsActive ='1'; //activate the Psychologist

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }
        }
        
        [Route("api/Psychologist/DeactivatePsychologist")]
        [HttpGet]
        public IHttpActionResult DeactivatePsychologist(int psychologistId)
        {
            if (psychologistId < 0)
            {
                //if the given Psychologist null
                return Ok(false);
            }

            var psychologist = (from c in db.Psychologists
                         where c.PsychologistId.Equals(psychologistId)
                         select c).FirstOrDefault();


            psychologist.IsActive = '0'; //activate the Psychologist

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }
        }


        [Route("api/Psychologist/GetPsychologist")]
        [HttpGet]
        public IHttpActionResult GetPsychologist(int psychologistId)
        {
            var cons = (from a in db.Psychologists
                        where a.PsychologistId.Equals(psychologistId)
                        select a).FirstOrDefault();
            if (cons==null) {
                //return false if the 
                return Ok(false);
            }
            PsychologistClass Psychologist = new PsychologistClass { PsychologistId = cons.PsychologistId, PsychologistName = cons.PsychologistName, PsychologistSurname = cons.PsychologistSurname, PsychologistEmail = cons.PsychologistEmail, NumStudentsLinked = Convert.ToInt32(cons.NumStudentsLinked),IsActive=cons.IsActive ,AdminId =Convert.ToInt32( cons.AdminId )};
            return Ok(Psychologist);
        }

        [Route("api/Psychologist/GetPsychologists")]
        [HttpGet]
        public IHttpActionResult GetPsychologists()
        {
            List<PsychologistClass> Psychologists = new List<PsychologistClass>();
            var list = (from a in db.Psychologists
                        select a);
            if (list == null)
            {
                return Ok(false);
            }
                foreach (Psychologist cons in list)
                {
                    PsychologistClass Psychologist = new PsychologistClass { PsychologistId = cons.PsychologistId, PsychologistName = cons.PsychologistName, PsychologistSurname = cons.PsychologistName, PsychologistEmail = cons.PsychologistName, NumStudentsLinked = Convert.ToInt32(cons.NumStudentsLinked), IsActive = cons.IsActive, AdminId = Convert.ToInt32(cons.AdminId) };

                    Psychologists.Add(Psychologist);
                }
            
            return Ok(Psychologists);
        }

        

        [Route("api/Psychologist/AddPsychologist")]
        [HttpPost]
        public IHttpActionResult AddPsychologist(PsychologistClass Psychologist)
        {
            if (Psychologist==null) {
                //check the validity of the Psychologist
                return Ok(false);
            }

            var newPsychologist = new Psychologist
            {
                PsychologistName = Psychologist.PsychologistName,
                PsychologistSurname = Psychologist.PsychologistSurname,
                PsychologistEmail = Psychologist.PsychologistEmail,
                PsychologistPassword = Secrecy.HashPassword(Psychologist.PsychologistPassword),
                IsActive='0'
            };

            db.Psychologists.InsertOnSubmit(newPsychologist);

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e)
            {
                e.GetBaseException();
                return Ok(false);
            }
        }

        [Route("api/Psychologist/getMinPsychologist")]
        [HttpGet]
        public IHttpActionResult getMinPsychologist()
        {

            //determined by using the Min calculation algorithm
            Psychologist nextPsychologist = new Psychologist();
            var list = (from a in db.Psychologists
                        where a.IsActive.Equals('1')
                        select a); //get all the  Psychologists that are active
            List<Psychologist> Psychologists = new List<Psychologist>(list);
            int min = 10000000;
            if (list==null)
            {
                return Ok(false); //if there are no Psychologists on the system
            }

            nextPsychologist = Psychologists[0]; //return the 1st Psychologist on the table
            min = nextPsychologist.NumStudentsLinked;
            foreach (Psychologist c in Psychologists)
            {
                if (c.NumStudentsLinked < min)
                {
                    nextPsychologist = c;
                    min = c.NumStudentsLinked; //update min value
                }
            }
            PsychologistClass PsychologistClass = new PsychologistClass {PsychologistId= nextPsychologist.PsychologistId, PsychologistName=nextPsychologist.PsychologistName, PsychologistSurname= nextPsychologist.PsychologistSurname, PsychologistEmail= nextPsychologist.PsychologistEmail, NumStudentsLinked= nextPsychologist.NumStudentsLinked, IsActive= nextPsychologist.IsActive };
            return Ok(nextPsychologist);

        }

        [Route("api/Psychologist/GetPsychologistStudents")]
        [HttpGet]
        public IHttpActionResult GetPsychologistStudents(int psychologistId)
        {
            List<StudentClass> linkedstudents = new List<StudentClass>();

            var list = (from a in db.StudentPsychologistLinks
                        where a.PsychologistId.Equals(psychologistId)
                        select a);

            foreach (var a in list)
            {
                Student studentobj = GetStudent(Convert.ToInt32(a.StudentNumber));
                //create the studentClass
                StudentClass student = new StudentClass { StudentNumber=studentobj.StudentNumber, StudentName=studentobj.StudentName,StudentSurname=studentobj.StudentSurname,StudentEmail=studentobj.StudentEmail};
                linkedstudents.Add(student);
            }
            return Ok(linkedstudents);
        }


        //HELPER FUNCTION
        public Student GetStudent(int StudentNumber)
        {
            var student = (from a in db.Students
                           where a.StudentNumber.Equals(StudentNumber)
                           select a).FirstOrDefault();

            return student;
        }

        
    }
}
