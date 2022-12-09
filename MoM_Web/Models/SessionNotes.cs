using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class SessionNotes
    {
        public int SessionId { get; set; }
        public int SessionRating { get; set; }
        public string SessionDescription { get; set; }
    }
}