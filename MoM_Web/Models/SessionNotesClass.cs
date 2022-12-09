using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class SessionNotesClass
    {
        public int SessionId { get; set; }
        public DateTime SessionDate { get; set; }
        public string SessionDescription { get; set; }
        public int SessionRating { get; set; }
        public int StudentNumber { get; set; }
        public int PsychologistId { get; set; }
    }
}