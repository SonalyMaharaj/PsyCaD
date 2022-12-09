using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class MoodClass
    {

        public int MoodId { get; set; }
        public string MoodTime { get; set; }
        public DateTime MoodDate { get; set; }
        public string MoodEmotion { get; set; }

        public int StudentNumber { get; set; }
    }
}