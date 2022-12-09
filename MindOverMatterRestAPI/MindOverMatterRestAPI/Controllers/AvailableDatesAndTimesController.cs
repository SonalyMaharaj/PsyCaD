using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class AvailableDatesAndTimesController : ApiController
    {



        DataClasses1DataContext db = new DataClasses1DataContext();


        //allocate the available slots
        [Route("api/DatesTimes/allocateSlot")]
        [HttpPost]
        public IHttpActionResult allocateSlot(AvailableDatesAndTimesClass avDandT)
        {
            if (avDandT == null)
            {
                //IF THE GIVEN Class is null
                return Ok(false);
            }
            //check if the slot is already found in the db
            var slot = (from a in db.UnAvailableDateAndTimes
                        where a.PsychologistId.Equals(avDandT.PsychologistId) &&
                        a.Date.Equals(avDandT.Date)
                        && a.Time.Equals(avDandT.Time)
                        select a).FirstOrDefault();
            if (slot != null)
            {

                slot.IsAvailable = 1; //mark as available

            }
            else {
                var user = new UnAvailableDateAndTime
                {
                    PsychologistId = avDandT.PsychologistId,
                    Date = avDandT.Date,
                    Time = avDandT.Time,
                    IsAvailable = 1
                };
                db.UnAvailableDateAndTimes.InsertOnSubmit(user);
            }

        try
        {
            db.SubmitChanges();
           return Ok(true);
        }
        catch (Exception ex)
        {
            ex.GetBaseException();

            return Ok(false);
        }
        }

        //deactivate slots
        [Route("api/DatesTimes/deactivateSlot")]
        [HttpPost]
        public IHttpActionResult deactivateSlot(AvailableDatesAndTimesClass avDandT)
        {
            if (avDandT==null) {
                //IF THE GIVEN Class is null
                return Ok(false);
            }

        var slot = (from a in db.UnAvailableDateAndTimes
                    where a.PsychologistId.Equals(avDandT.PsychologistId) &&
                    a.Date.Equals(avDandT.Date)
                    && a.Time.Equals(avDandT.Time)
                    select a).FirstOrDefault();
            if (slot != null) { 

                    slot.IsAvailable = 0;

            }
            else {
                //if the slot is not found on the database
                var user = new UnAvailableDateAndTime
                {
                    PsychologistId = avDandT.PsychologistId,
                    Date = avDandT.Date,
                    Time = avDandT.Time,
                    IsAvailable = 0 //exercise is not available
                };
                db.UnAvailableDateAndTimes.InsertOnSubmit(user);
            }
        


        try
        {
            db.SubmitChanges();
            return Ok(true);
        }
        catch (Exception ex)
        {
            ex.GetBaseException();

            return Ok(false);
        }

        throw new NotImplementedException();
        }

        //class for checking wether the given slot is available

        [Route("api/DatesTimes/isSlotAvailable")]
        [HttpGet]
        public IHttpActionResult isSlotAvailable(AvailableDatesAndTimesClass avDandT)
        {
            if (avDandT == null)
            {
                //IF THE GIVEN Class is null
                return Ok(false);
            }

            var slotavail = (from a in db.UnAvailableDateAndTimes
                             where a.Date.Equals(avDandT.Date) &&
                         a.Time.Equals(avDandT.Time)
                         select a).FirstOrDefault();
            //return true if the slot is found
        if (slotavail.IsAvailable.Equals(1))
        {
           return Ok( true);
        }
        else return Ok(false);

        }


        [Route("api/DatesTimes/getUnAvailableSlots")]
        [HttpGet]
        public IHttpActionResult getUnAvailableSlots(int PsychologistId)
        {
        List<AvailableDatesAndTimesClass> slots = new List<AvailableDatesAndTimesClass>();
        var objects = (from a in db.UnAvailableDateAndTimes
                       where a.PsychologistId.Equals(PsychologistId) &&
                       a.IsAvailable.Equals(0)
                       select a);

        foreach (UnAvailableDateAndTime a in objects)
        {
                //create new class
            AvailableDatesAndTimesClass avDandT = new AvailableDatesAndTimesClass {ID=a.ID, PsychologistId = a.PsychologistId,Date=Convert.ToDateTime(a.Date),Time=a.Time,IsAvailable= Convert.ToInt32(a.IsAvailable)};
            slots.Add(avDandT);
        }
        return Ok(slots); //return list of AvailableDatesAndTimesClass

        }

        //helper class for returning the list of available times of the day
        [Route("api/DatesTimes/getTimesPerDay")]
        [HttpGet]
        public IHttpActionResult getTimesPerDay() 
        {
            int min = 8;
            int max = 15;
            List<String> listoftimes = new List<string>();
            for (int i=min;i<=max;i++) 
            {
                string time = "";
                if (i < 10) //if the index is 0-9 we should add a prefix of zero e.g 7-->07:00:00
                {
                    time = "0" + i + ":00:00";
                }
                else //e.g 10--> 10:00:00 instead of 010:00:00
                {
                    time =i + ":00:00";
                }
                
                listoftimes.Add(time);
            }
            return Ok(listoftimes);

        }
}
}
