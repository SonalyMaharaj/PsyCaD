using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class PsychologistClass
    {

        public int PsychologistId { get; set; }
        public string PsychologistName { get; set; }
        public string PsychologistSurname { get; set; }
        public string PsychologistEmail { get; set; }

        public string PsychologistPassword { get; set; }
        public string Campus { get; set; }
        public int NumStudentsLinked { get; set; }
        public char IsActive { get; set; }
        public int AdminId { get; set; }

    }
}