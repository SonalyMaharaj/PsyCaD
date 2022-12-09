using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MindOverMatterRestAPI.Models
{
    public class MedicineTrackerClass
    {
        public int ID { get; set;}
        public DateTime Date { get; set; }
        public string NameOfMedicine { get; set; }

        public string Category { get; set; }

        public int StudentNumber { get; set; }

        public string NameOfDoctor { get; set; }

        public int IsActive { get; set; }
    }
}