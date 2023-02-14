using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class MoodController : ApiController
    {

        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Mood/GetMoods")]
        [HttpGet]
        public IHttpActionResult GetMoods()
        {
            List<MoodClass> moods = new List<MoodClass>();
            var list = (from a in db.Moods
                        select a);
            if (list==null) {
                return Ok(false); //return false if the list is null
            }
            foreach (var a in list)
            {
                MoodClass mood = new MoodClass { MoodId = a.MoodId, MoodTime = a.MoodTime, MoodDate = Convert.ToDateTime(a.MoodDate), MoodEmotion = a.MoodEmotion, MoodIntegerImage= Convert.ToInt32(a.MoodIntegerImage),StudentNumber = Convert.ToInt32(a.StudentNumber) };
                moods.Add(mood);
            }

            return Ok(moods);
        }

        [Route("api/Mood/GetMoods")]
        [HttpGet]
        public IHttpActionResult GetMoods(int StudentNumber)
        {
            List<MoodClass> moods = new List<MoodClass>();
            var list = (from a in db.Moods
                        where a.StudentNumber.Equals(StudentNumber)
                        select a);
            if (list == null)
            {
                return Ok(false); //return false if the list is null
            }
            foreach (var a in list)
            {
                MoodClass mood = new MoodClass { MoodId = a.MoodId, MoodTime = a.MoodTime, MoodDate = Convert.ToDateTime(a.MoodDate), MoodEmotion = a.MoodEmotion, MoodIntegerImage = Convert.ToInt32(a.MoodIntegerImage), StudentNumber = Convert.ToInt32(a.StudentNumber) };
                moods.Add(mood);
            }

            return Ok(moods);
        }

   

        [Route("api/Mood/getMood")]
        [HttpGet]
        public IHttpActionResult getMood(int id)
        {
            //get the specified mood
            var mood = (from a in db.Moods
                        where a.MoodId.Equals(id)
                        select a).FirstOrDefault();
            if (mood == null)
            {
                return Ok(false);
            }

            MoodClass moodClass = new MoodClass { MoodId = mood.MoodId, MoodDate = Convert.ToDateTime(mood.MoodDate), MoodTime = mood.MoodTime, MoodEmotion = mood.MoodEmotion,MoodIntegerImage= Convert.ToInt32(mood.MoodIntegerImage), StudentNumber = Convert.ToInt32(mood.StudentNumber) };

            return Ok(moodClass);
        }
        
        [Route("api/Mood/LogMood")]
        [HttpPost]
        public IHttpActionResult LogMood(MoodClass mood)
        {
            //add new mood
            if (mood == null)
            {
                return Ok(false);
            }

            Mood moodClass = new Mood
            {
             MoodDate = Convert.ToDateTime(mood.MoodDate).Date,
             MoodTime = mood.MoodTime,
             MoodEmotion = mood.MoodEmotion,
             MoodIntegerImage=mood.MoodIntegerImage,
             StudentNumber = mood.StudentNumber,
            };

            db.Moods.InsertOnSubmit(moodClass);

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

    }
}
