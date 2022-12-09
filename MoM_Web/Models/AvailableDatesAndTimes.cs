using System;
using System.Collections.Generic;
using System.Text;

namespace MoM_Web.Models
{
    public class AvailableDatesAndTimes
    {
        //this function matches the date "as key" and its corresponding list of times "as value"
        public string date { get; set; }
        public List<String> times { get; set; }

        
        

    }
}
