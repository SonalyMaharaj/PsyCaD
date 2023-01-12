using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class StudentClass
    {

        public int StudentNumber { get; set; }

        public string StudentName { get; set; }

        public string StudentSurname { get; set; }

        public string StudentEmail { get; set; }

        public string StudentPassword { get; set; }

        public char StudentGender { get; set; }
        public string StudentDOB { get; set; }
        public string StudentQualification { get; set; }

    }
}