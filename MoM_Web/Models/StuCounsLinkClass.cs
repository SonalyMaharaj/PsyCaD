using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class StuCounsLinkClass
    {

        public int LinkID { get; set; }
        public int PsychologistId { get; set; }
        public int StudentNumber { get; set; }
        public int IsLinked { get; set; }

    }
}