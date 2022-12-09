using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MindOverMatterRestAPI.Models
{
    public class NotificationClass
    {
        public int ID { get; set; }
        public DateTime DateOfNotification { get; set; }
        public string Description { get; set; }
        public string IsActive { get; set; }
        
        public int StudentNumber { get; set; }

        public int PsychologistId { get; set; }

    }
}