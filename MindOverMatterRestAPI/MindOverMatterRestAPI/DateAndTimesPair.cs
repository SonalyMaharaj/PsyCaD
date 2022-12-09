using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MindOverMatterRestAPI
{
    public class DateAndTimesPair
    {
        //single date and its pair of times
        public DateTime date { get; set; }
        public List<String> times { get; set; }
    }
}