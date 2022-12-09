using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Runtime.Serialization;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class DiaryController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Diary/GetDiaryEntries")]
        [HttpGet]
        public IHttpActionResult GetDiaryEntries()
        {
            List<DiaryClass> diaries = new List<DiaryClass>();
            List<Diary> list = new List<Diary>(from a in db.Diaries
                                               select a);
            var listarray = list.ToArray<Diary>();
            //Sort the diaries to start with the recent added diary
            for (int i = listarray.Length - 1; i >= 0; i--)
            {
                DiaryClass diary = new DiaryClass { DiaryId = listarray[i].DiaryId, Title = listarray[i].DiaryTitle, Date = Convert.ToDateTime(listarray[i].DiaryDate),DiaryFlaggedWords= listarray[i].FlaggedWords, Description = listarray[i].DiaryDescription,IsFlagged = Convert.ToInt32(listarray[i].IsFlagged), StudentNumber = Convert.ToInt32(listarray[i].StudentNumber) };
                diaries.Add(diary);
            }

            return Ok(diaries);
        }

        [Route("api/Diary/GetStudentDiaryEntries")]
        [HttpGet]
        public IHttpActionResult GetStudentDiaryEntries(int studentnum)
        {
            List<DiaryClass> diaries = new List<DiaryClass>();
            List<Diary> list =new List<Diary>(from a in db.Diaries
                        where a.StudentNumber.Equals(studentnum)
                        select a);
            var listarray = list.ToArray<Diary>();
            //Sort the diaries to start with the recent added diary
            for (int i=listarray.Length-1;i>=0;i--)
            {
                DiaryClass diary = new DiaryClass { DiaryId = listarray[i].DiaryId, Title = listarray[i].DiaryTitle, Date = Convert.ToDateTime(listarray[i].DiaryDate), DiaryFlaggedWords = listarray[i].FlaggedWords, Description = listarray[i].DiaryDescription, IsFlagged = Convert.ToInt32(listarray[i].IsFlagged), StudentNumber = Convert.ToInt32(listarray[i].StudentNumber) };
                diaries.Add(diary);
            }

            return Ok(diaries);
        }

        //Save the diary entry to the database
        [Route("api/Diary/LogDiaryEntry")]
        [HttpPost]
        public IHttpActionResult logDiaryEntry(DiaryClass diaryClass)
        {

            if (diaryClass == null) {
                return Ok(false);
            }

            //determine whether the entry is flagged or not
            string wordsflagged = HelperClass.DetermineFlag(diaryClass.Description);
            int isflagged = 0;
            if (!wordsflagged.ToUpper().Equals("NONE"))
            {
                //if it has flagged words, then mark as flagged
                isflagged = 1;
            }
            else
            {
                isflagged = 0;
            }
            var diary = new Diary
            {
                DiaryTitle = diaryClass.Title,
                StudentNumber = diaryClass.StudentNumber,
                DiaryDescription = diaryClass.Description,
                FlaggedWords =wordsflagged, //get flagged words
                IsFlagged= isflagged,
                DiaryDate = diaryClass.Date,
            };

            db.Diaries.InsertOnSubmit(diary);
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

        [Route("api/Diary/GetDiaryEntry")]
        [HttpGet]
        public IHttpActionResult GetDiaryEntry(int DiaryId)
        {
            //get the specified diary
            var diary = (from a in db.Diaries
                         where a.DiaryId.Equals(DiaryId)
                         select a).FirstOrDefault();
            if (diary==null) {
                return Ok(false);
            }

            DiaryClass diaryClass = new DiaryClass { DiaryId = diary.DiaryId, Title = diary.DiaryTitle, Date = Convert.ToDateTime(diary.DiaryDate), DiaryFlaggedWords = diary.FlaggedWords, Description = diary.DiaryDescription, IsFlagged = Convert.ToInt32(diary.IsFlagged), StudentNumber = Convert.ToInt32(diary.StudentNumber) };

            return Ok(diaryClass); 
        }

        [Route("api/Diary/EditDiaryEntry")]
        [HttpPost]
        public IHttpActionResult EditDiaryEntry(DiaryClass diaryClass)
        {
            //request diary with given id
            var diary = (from a in db.Diaries
                         where a.DiaryId.Equals(diaryClass.DiaryId)
                         select a).FirstOrDefault();

            //change diary fields
            diary.DiaryTitle = diaryClass.Title.Trim();
            diary.DiaryDescription = diaryClass.Description.Trim();
            diary.FlaggedWords = HelperClass.DetermineFlag(diaryClass.Description); //get flagged words
            if(!diary.FlaggedWords.ToUpper().Equals("NONE")) {
                //if it has flagged words, then mark as flagged
                diary.IsFlagged = 1;
            }
            else {
                diary.IsFlagged = 0;
            }
            //NOTE the flagged field is just  a helper field that will help with the display on the WEB app
            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
        }


        [Route("api/Diary/FlagDiaryEntry")]
        [HttpGet]
        public IHttpActionResult FlagDiaryEntry(int DiaryId)
        {
            //request diary with given id
            var diary = (from a in db.Diaries
                         where a.DiaryId.Equals(DiaryId)
                         select a).FirstOrDefault();

            //change diary fields
            diary.IsFlagged = 1; //set to 1 if diary is being flagged, -1 if it was once flagged, and 0 if entry was never flagged

            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
        } 
        
        [Route("api/Diary/RemoveFlagDiaryEntry")]
        [HttpGet]
        public IHttpActionResult RemoveFlagDiaryEntry(int diaryID)
        {
            //request diary with given id
            var diary = (from a in db.Diaries
                         where a.DiaryId.Equals(diaryID)
                         select a).FirstOrDefault();

            //change diary fields
            diary.IsFlagged = -1; //set to 1 if diary is being flagged, -1 if it was once flagged, and 0 if entry was never flagged

            try
            {
                //save changes
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();
                return Ok(false);
            }
        }

    }
}
