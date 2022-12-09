using MoM_Web.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class HelperClass
    {
        
        public static List<String> PrescribedExercisesStrings { get; set; } = new List<string>(); //Query strings for when prescribing exercises

        public static string apiurl { get; set; } = "http://192.168.56.1/api/";


       //public static string apiurl { get; set; } = "https://localhost:44358/api/";

        public static List<ExerciseClass> AvailableExercises { get; set; }

        public static List<CounsBookingsData> counsBookingsDatas { get; set; } = new List<CounsBookingsData>();

        public static List<BookConsultationClass> TrimBookings(List<BookConsultationClass> bookings, DateTime calendarInitialDate)
        {
            //this function only returns the bookings in the given range of monday to friday
            List<BookConsultationClass> finalbookings = new List<BookConsultationClass>();
            DateTime maxCalendar = calendarInitialDate.AddDays(6);
            foreach (var a in bookings) 
            {
                if (a.BookDate>=calendarInitialDate && a.BookDate<maxCalendar) {
                    
                    finalbookings.Add(a);
                }
            }
            return finalbookings;
        }
    }
}