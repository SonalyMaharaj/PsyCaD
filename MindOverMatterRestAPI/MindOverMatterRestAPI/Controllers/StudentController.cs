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
    public class StudentController : ApiController
    {

        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Student/EditStudent")]
        [HttpPost]
        public IHttpActionResult EditStudent(StudentClass studentClass)
        {
            if (studentClass==null) {
                return Ok(false);
            }
            var student = (from s in db.Students
                           where s.StudentNumber.Equals(studentClass.StudentNumber)
                           select s).FirstOrDefault();

            student.StudentName = studentClass.StudentName.Trim();
            student.StudentSurname = studentClass.StudentSurname.Trim();
            student.StudentEmail = studentClass.StudentEmail.Trim();

            try
            {
                db.SubmitChanges(); //does not want to update
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }

        }

        [Route("api/Student/GetStudent")]
        [HttpGet]
        public IHttpActionResult GetStudent(int StudentNumber)
        {
            var student = (from a in db.Students
                           where a.StudentNumber.Equals(StudentNumber)
                           select a).FirstOrDefault();

            if (student==null) {
                return Ok(false);
            }
            StudentClass studentClass = new StudentClass { StudentNumber = student.StudentNumber, StudentName = student.StudentName, StudentSurname = student.StudentSurname, StudentEmail = student.StudentEmail,StudentGender=Convert.ToChar(student.StudentGender), StudentDOB=Convert.ToString(student.StudentDOB),StudentQualification=student.StudentQualification};

            return Ok(studentClass);
        }

        [Route("api/Student/GetStudents")]
        [HttpGet]
        public IHttpActionResult GetStudents()
        {
            List<StudentClass> students = new List<StudentClass>();
            var list = (from a in db.Students
                        select a);

            if (list==null) {
                return Ok(false);
            }

            foreach (var student in list)
            {
                StudentClass studentClass = new StudentClass { StudentNumber = student.StudentNumber, StudentName = student.StudentName, StudentSurname = student.StudentSurname, StudentEmail = student.StudentEmail, StudentGender = Convert.ToChar(student.StudentGender), StudentDOB = Convert.ToString(student.StudentDOB), StudentQualification = student.StudentQualification };

                students.Add(studentClass);
            }

            return Ok(students);
        }


        [Route("api/Student/AddStudent")]
        [HttpPost]
        public IHttpActionResult AddStudent(StudentClass studentClass)
        {
            if (studentClass==null) {
                return Ok(false);
            }

            Student student = new Student
            {
                StudentName=studentClass.StudentName,
                StudentSurname=studentClass.StudentSurname,
                StudentEmail=studentClass.StudentEmail,
                StudentPassword= Secrecy.HashPassword(studentClass.StudentPassword),
                StudentGender=studentClass.StudentGender,
                StudentDOB= Convert.ToDateTime(studentClass.StudentDOB),
                StudentQualification=studentClass.StudentQualification
            };

            db.Students.InsertOnSubmit(student);

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
    }
}
