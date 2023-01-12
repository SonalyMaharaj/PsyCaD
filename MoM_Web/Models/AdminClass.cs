using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class AdminClass
    {

        public int AdminId { get; set; }
        public string AdminName { get; set; }
        public string AdminSurname { get; set; }

        public string AdminEmail { get; set; }

        public string AdminPassword { get; set; }

    }
}