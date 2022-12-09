using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class BookConsultationController : ApiController
    {

        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Consultation/BookConsultation")]
        [HttpPost]
        public IHttpActionResult BookConsultation(BookConsultationClass consultation)
        {
            if (consultation == null) {
                return Ok(false);
            }
            //new user created
            var booking = new BookConsultation
            {
                BookDate = consultation.BookDate,
                BookTime = consultation.BookTime,
                StudentNumber = consultation.StudentNumber,
                PsychologistId = consultation.PsychologistId,

            };

            db.BookConsultations.InsertOnSubmit(booking);
            try
            {
                db.SubmitChanges();
                //deactive slot
                deactivateSlot(Convert.ToInt32( booking.PsychologistId),Convert.ToDateTime(booking.BookDate),booking.BookTime);
                return Ok(true);
            }
            catch (Exception ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }

        }

        [Route("api/Consultation/getBookings")]
        [HttpGet]
        public IHttpActionResult getBookings()
        {
            var bookings = new List<BookConsultationClass>();
            var list = (from a in db.BookConsultations
                        select a);
            if (list==null) {
                return Ok(false);
            }
            foreach (BookConsultation a in list)
            {
                BookConsultationClass book = new BookConsultationClass {BookNumber=a.BookNumber,BookDate =Convert.ToDateTime( a.BookDate),BookTime =a.BookTime,StudentNumber= Convert.ToInt32(a.StudentNumber),PsychologistId=Convert.ToInt32( a.PsychologistId) };
                bookings.Add(book);
            }
            return Ok(bookings); //return List of BookConsultation class
        }


        [Route("api/Consultation/getPsychologistBookings")]
        [HttpGet]
        public IHttpActionResult getPsychologistBookings(int PsychologistId)
        {
            var bookings = new List<BookConsultationClass>();
            var list = (from a in db.BookConsultations
                        where a.PsychologistId.Equals(PsychologistId)
                        select a);

            if (list == null)
            {
                return Ok(false);
            }
            foreach (BookConsultation a in list)
            {
                BookConsultationClass book = new BookConsultationClass { BookNumber = a.BookNumber, BookDate = Convert.ToDateTime(a.BookDate), BookTime = a.BookTime, StudentNumber = Convert.ToInt32(a.StudentNumber), PsychologistId = Convert.ToInt32(a.PsychologistId) };
                bookings.Add(book);
            }
            return Ok(bookings); //return List of BookConsultation class
        }


        [Route("api/Consultation/getStudentBookings")]
        [HttpGet]
        public IHttpActionResult getStudentBookings(int StudentNumber)
        {
            var bookings = new List<BookConsultationClass>();
            var list = (from a in db.BookConsultations
                        where a.StudentNumber.Equals(StudentNumber)
                        select a);

            if (list == null)
            {
                return Ok(false);
            }
            foreach (BookConsultation a in list)
            {
                BookConsultationClass book = new BookConsultationClass { BookNumber = a.BookNumber, BookDate = Convert.ToDateTime(a.BookDate), BookTime = a.BookTime, StudentNumber = Convert.ToInt32(a.StudentNumber), PsychologistId = Convert.ToInt32(a.PsychologistId) };
                bookings.Add(book);
            }
            return Ok(bookings); //return List of BookConsultation class
        }

        [Route("api/Consultation/getBooking")]
        [HttpPost]
        public IHttpActionResult getBooking(BookConsultationClass bookConsultationClass)
        {
            if (bookConsultationClass==null) {
                return Ok(false);
            }
            var booking = (from a in db.BookConsultations
                           where a.PsychologistId.Equals(bookConsultationClass.PsychologistId)
                           && a.StudentNumber.Equals(bookConsultationClass.StudentNumber)
                           && a.BookDate.Equals(bookConsultationClass.BookDate)
                           select a).FirstOrDefault();

            if (booking==null) {
                return Ok(false);
            }
            BookConsultationClass booked = new BookConsultationClass { BookNumber = booking.BookNumber, BookDate = Convert.ToDateTime(booking.BookDate), BookTime = booking.BookTime, StudentNumber = Convert.ToInt32(booking.StudentNumber), PsychologistId = Convert.ToInt32(booking.PsychologistId) };

            return Ok(booked);
        }


        //deactivate slots HELPER function
        private bool deactivateSlot(int userID, DateTime date, string time)
        {
            var slot = (from a in db.UnAvailableDateAndTimes
                        where a.PsychologistId.Equals(userID) &&
                        a.Date.Equals(date)
                        && a.Time.Equals(time)
                        select a).FirstOrDefault();



            //if the slot is not found, log a new slot in the UNAvailable slots table
            if (slot == null)
            {
                UnAvailableDateAndTime newslot = new UnAvailableDateAndTime
                {
                    PsychologistId = userID,
                    Date = date,
                    Time = time,
                    IsAvailable = 0

                };
                db.UnAvailableDateAndTimes.InsertOnSubmit(newslot);
            }
            else {
                //if the slot is already in the table just disAble it
                slot.IsAvailable = 0;
            }
           
            try
            {
                db.SubmitChanges();
                return true;
            }
            catch (Exception ex)
            {
                ex.GetBaseException();

                return false;
            }

            throw new NotImplementedException();
        }

    }
}
