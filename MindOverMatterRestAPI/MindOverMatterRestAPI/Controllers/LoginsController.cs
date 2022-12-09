using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class LoginsController : ApiController
    {

        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Login/loginStudent")]
        [HttpGet]
        public IHttpActionResult loginStudent(string useremail, string password)
        {

            //select student from database
            var student = (from a in db.Students
                           where a.StudentEmail.Equals(useremail)
                           && a.StudentPassword.Equals(password)
                           select a).FirstOrDefault();

            if (student == null) {
                return Ok(false);
            }
            StudentClass studentClass = new StudentClass {StudentNumber=student.StudentNumber,StudentName=student.StudentName,StudentSurname=student.StudentSurname,StudentEmail=student.StudentEmail};
            //return student
            return Ok(studentClass);


        }


        [Route("api/Login/loginUser")]
        [HttpGet]
        public IHttpActionResult loginUser(string username, string password)
        {

            User loggeduser = null;

            //user is not in Students list, check Psychologist list
            var findPsychologist = loginPsychologist(username, password);
            if (findPsychologist != null)
            {
                loggeduser = new User { UserId = findPsychologist.PsychologistId, Name = findPsychologist.PsychologistName, surname = findPsychologist.PsychologistSurname, email = findPsychologist.PsychologistEmail, userType = "Psychologist" };
                return Ok(loggeduser);
            }
            //user is not in Psychologist list, check Admin list
            var findAdmin = loginAdmin(username, password);
            if (findAdmin != null)
            {
                loggeduser = new User { UserId = findAdmin.AdminId, Name = findAdmin.AdminName, surname = findAdmin.AdminSurname, email = findAdmin.AdminEmail, userType = "Admin" };
                return Ok(loggeduser);
            }

            //user not Found
            return loginStudent(username, password); //Login student or return false
        }


        //HELPER FUNCTION
        private Psychologist loginPsychologist(string username, string password)
        {
            //select Psychologist from database
            var pschc = (from a in db.Psychologists
                         where a.PsychologistEmail.Equals(username)
                         && a.PsychologistPassword.Equals(password)
                         select a).FirstOrDefault();

            //return Psychologist id
            if (pschc != null)
            {
                return pschc;
            }
            else
            {
                return null;
            }
        }

       //HELPER FUNCTION
        private Admin loginAdmin(string username, string password)
        {
            //select Admin from database
            var admin = (from a in db.Admins
                         where a.AdminEmail.Equals(username)
                         && a.AdminPassword.Equals(password)
                         select a).FirstOrDefault();

            //return Admin id
            if (admin != null)
            {
                return admin;
            }
            else
            {
                return null;
            }
        }

    }
}
