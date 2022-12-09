using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class BookConsultationClass
    {

        public int BookNumber { get; set; }
        public DateTime BookDate { get; set; }
        public string BookTime { get; set; }

        public int StudentNumber { get; set; }

        public int PsychologistId { get; set; }

    }
}