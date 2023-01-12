using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class DiaryClass
    {
        public int DiaryId { get; set; }
        public string Title { get; set; }
        public DateTime Date { get; set; }
        public string Description { get; set; }
        public string DiaryFlaggedWords { get; set; }
        public int IsFlagged { get; set; }
        public int StudentNumber { get; set; }

    }
}