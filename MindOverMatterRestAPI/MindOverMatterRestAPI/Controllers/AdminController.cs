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
    public class AdminController : ApiController
    {

        DataClasses1DataContext db = new DataClasses1DataContext();

        public AdminController() {
           
        }

        [Route("api/Admin/getAdmin")]
        [HttpGet]
        public IHttpActionResult GetAdmin(int AdminId)
        {
            var addAdmin = (from a in db.Admins
                                where a.AdminId.Equals(AdminId)
                                select a).FirstOrDefault();

            if (addAdmin==null) {
                return Ok(false);
            }
            AdminClass admin = new AdminClass {AdminId=addAdmin.AdminId, AdminName=addAdmin.AdminName,AdminSurname=addAdmin.AdminSurname,AdminEmail=addAdmin.AdminEmail,AdminPassword=addAdmin.AdminPassword};
            return Ok(admin);
        }

        //HELPER FUNCTION: for taking every data from the database and editing the passwords to use hash passwords
        /*[Route("api/Admin/HashAllPasswords")]
        [HttpGet]
        public IHttpActionResult hashAllPasswords() {
           var admins = (from a in db.Admins
                          select a);
            var counsellors = (from a in db.Counsellors
                               select a);

            var students = (from a in db.Students
                            where a.Student_num.Equals(200021)
                            select a);

            foreach (var a in admins) {
                a.Admin_password = HashPass.Secrecy.HashPassword(a.Admin_password);
            }
            
            foreach (var a in counsellors) {
                a.Couns_password = HashPass.Secrecy.HashPassword(a.Couns_password);

            }
            foreach (var a in students) {
                a.Stu_password = HashPass.Secrecy.HashPassword(a.Stu_password);
            }

            try {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e) {
                e.GetBaseException();
                return Ok(false);
            }
        
        }*/

       /* [Route("api/Admin/hashStudentPassword")]
        [HttpGet]
        public IHttpActionResult hashStudentPassword(int StudentNum)
        {

            var students = (from a in db.Students
                            where a.Student_num.Equals(StudentNum)
                            select a);
            foreach (var a in students)
            {
                a.Stu_password = HashPass.Secrecy.HashPassword(a.Stu_password);
            }

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

        }*/

    }
}
