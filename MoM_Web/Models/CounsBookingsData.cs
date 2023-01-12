using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class CounsBookingsData
    {

        public int CounsID { get; set; }
        public int January { get; set; } 
        public int February { get; set; }
        public int March { get; set; }
        public int April { get; set; }
        public int May { get; set; }
        public int June { get; set; }
        public int July { get; set; }
        public int August { get; set; }
        public int September { get; set; }
        public int October { get; set; }
        public int November { get; set; }
        public int December { get; set; }

        //Function to increment the number of bookings for a given month
        public void IncrementForMonth(string monthName) {
            switch (monthName.ToUpper()) {
                case "JANUARY":
                    January++;
                    break;
                case "FEBRUARY":
                    February++;
                    break;
                case "MARCH":
                    March++;
                    break;
                case "APRIL":
                    April++;
                    break;
                case "MAY":
                    May++;
                    break;
                case "JUNE":
                    June++;
                    break;
                case "JULY":
                    July++;
                    break;
                case "AUGUST":
                    August++;
                    break;
                case "SEPTEMBER":
                    September++;
                    break;
                case "OCTOBER":
                    October++;
                    break;
                case "NOVEMBER":
                    November++;
                    break;
                case "DECEMBER":
                    December++;
                    break;
            }
        }

        internal int getDataForMonth(string monthname)
        {
            int data = 0;
            switch (monthname.ToUpper())
            {
                case "JANUARY":
                    data=January;
                    break;
                case "FEBRUARY":
                    data = February;
                    break;
                case "MARCH":
                    data=March;
                    break;
                case "APRIL":
                    data=April;
                    break;
                case "MAY":
                    data= May;
                    break;
                case "JUNE":
                    data=June;
                    break;
                case "JULY":
                    data=July;
                    break;
                case "AUGUST":
                    data=August;
                    break;
                case "SEPTEMBER":
                    data=September;
                    break;
                case "OCTOBER":
                    data=October;
                    break;
                case "NOVEMBER":
                    data=November;
                    break;
                case "DECEMBER":
                    data= December;
                    break;

            }
            return data;
        }
    }
}