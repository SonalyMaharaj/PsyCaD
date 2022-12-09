using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class Mood
    {
        public int MoodId { get; set; }
        public string MoodEmotion { get; set; }
        public string MoodTime { get; set; }
    }
}