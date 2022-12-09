using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.Serialization;
using System.Web.Http;

namespace MindOverMatterRestAPI.Models
{
    public class StudentPsychologistLinkController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/StudentPsychologistLink/getLinkInfoByStudent")]
        [HttpGet]
        public IHttpActionResult getLinkInfoByStudent(int StudentNumber)
        {
            var obj = (from a in db.StudentPsychologistLinks
                        where a.StudentNumber.Equals(StudentNumber)
                        select a).FirstOrDefault();
            if (obj==null) {
                return Ok(false);
            }
            StudentPsychologistLink link = new StudentPsychologistLink { LinkID=obj.LinkID,PsychologistId=Convert.ToInt32(obj.PsychologistId),StudentNumber= Convert.ToInt32(obj.StudentNumber),IsLinked= Convert.ToInt32(obj.IsLinked) };

            return Ok(link);
        }

        [Route("api/StudentPsychologistLink/LinkStudentToPsychologist")]
        [HttpPost]
        public IHttpActionResult LinkStudentToPsychologist(StudentAndPsychologistIDPair StudentAndPsychologistIDPair)
        {
            var psychologist = (from a in db.Psychologists
                         where a.PsychologistId.Equals(StudentAndPsychologistIDPair.PsychologistId)
                         select a).FirstOrDefault();


            StudentPsychologistLink PsycologistLink = new StudentPsychologistLink
            {
                PsychologistId = StudentAndPsychologistIDPair.PsychologistId,
                StudentNumber = StudentAndPsychologistIDPair.StudentNumber,
                IsLinked = 1
            };

            //edit the number of students linked to psychologist

            //save changes
            psychologist.NumStudentsLinked = psychologist.NumStudentsLinked + 1;
            db.StudentPsychologistLinks.InsertOnSubmit(PsycologistLink);
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


        [Route("api/StudentPsychologistLink/UnLinStudentFromPsychologist")]
        [HttpPost]
        public IHttpActionResult UnLinStudentFromPsychologist(StudentAndPsychologistIDPair StudentAndPsychologistIDPair)
        {
            var CSlink = (from a in db.StudentPsychologistLinks
                          where a.PsychologistId.Equals(StudentAndPsychologistIDPair.PsychologistId)
                          && a.StudentNumber.Equals(StudentAndPsychologistIDPair.StudentNumber)
                          select a).FirstOrDefault();
            CSlink.IsLinked = 0;

            var psychologist = (from a in db.Psychologists
                         where a.PsychologistId.Equals(StudentAndPsychologistIDPair.PsychologistId)
                         select a).FirstOrDefault();

            //edit the number of students linked to psychologist
            if (CSlink != null)
            {
                psychologist.NumStudentsLinked = psychologist.NumStudentsLinked - 1;
            }

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
