using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class CallModel
    {

		public int CallId { get; set; }
		public String TelHolder { get; set; }
		public String TelNumber { get; set; }
		public String CallDate { get; set; }
		public String CallTime { get; set; }
		public String StudentNumber { get; set; }

	}
}