using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class AvailableDatesAndTimesClass
    {

        public int ID { get; set; }

        public int PsychologistId { get; set; }

        public DateTime Date { get; set; }

        public string Time { get; set; }

        public int IsAvailable { get; set; }

    }
}