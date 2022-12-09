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
    public class SessionNotesController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();
        [Route("api/SessionNote/GetSessionNotes")]
        [HttpGet]
        public IHttpActionResult GetSessionNotes()
        {
            List<SessionNotesClass> notes = new List<SessionNotesClass>();
            var list = (from a in db.SessionNotes
                        select a);
            foreach (var a in list)
            {
                SessionNotesClass note = new SessionNotesClass { SessionId = a.SessionId, SessionDate = Convert.ToDateTime(a.SessionDate), SessionDescription = a.SessionDescription, SessionRating = Convert.ToInt32(a.SessionRating), StudentNumber = Convert.ToInt32(a.StudentNumber), PsychologistId = Convert.ToInt32(a.PsychologistId) };
                notes.Add(note);
            }

            return Ok(notes);
        }
        
        [Route("api/SessionNote/GetStudentSessionNotes")]
        [HttpGet]
        public IHttpActionResult GetStudentSessionNotes(int studentnum)
        {
            List<SessionNotesClass> notes = new List<SessionNotesClass>();
            var list = (from a in db.SessionNotes
                        where a.StudentNumber.Equals(studentnum)
                        select a);
            if (list==null) {
                return Ok(false);
                    }
            foreach (var a in list)
            {
                SessionNotesClass note = new SessionNotesClass { SessionId = a.SessionId, SessionDate = Convert.ToDateTime(a.SessionDate), SessionDescription = a.SessionDescription, SessionRating = Convert.ToInt32(a.SessionRating), StudentNumber = Convert.ToInt32(a.StudentNumber), PsychologistId = Convert.ToInt32(a.PsychologistId) };
                notes.Add(note);
            }

            return Ok(notes);
        }



        [Route("api/SessionNote/GetSessionNote")]
        [HttpGet]
        public IHttpActionResult GetSessionNote(int SessionId)
        {
            //get the specified session note
            var note = (from a in db.SessionNotes
                        where a.SessionId.Equals(SessionId)
                        select a).FirstOrDefault();
            if (note == null)
            {
                return Ok(false);
            }

            SessionNotesClass sessionNotesClass = new SessionNotesClass { SessionId = note.SessionId, SessionDate = Convert.ToDateTime(note.SessionDate), SessionDescription = note.SessionDescription, SessionRating = Convert.ToInt32(note.SessionRating), StudentNumber = Convert.ToInt32(note.StudentNumber), PsychologistId = Convert.ToInt32(note.PsychologistId) };

            return Ok(sessionNotesClass);
        }

        [Route("api/SessionNote/AddSessionNote")]
        [HttpPost]
        public IHttpActionResult AddSessionNote(SessionNotesClass sessionNotesClass)
        {
            if (sessionNotesClass==null) {
                return Ok(false);
            }
            SessionNote session_Note = new SessionNote 
            {
                SessionDate=sessionNotesClass.SessionDate,
                SessionDescription=sessionNotesClass.SessionDescription,
                SessionRating=sessionNotesClass.SessionRating,
                StudentNumber=sessionNotesClass.StudentNumber,
                PsychologistId=sessionNotesClass.PsychologistId
            };

            db.SessionNotes.InsertOnSubmit(session_Note); //insert new session note
            try {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e) 
            {
                e.GetBaseException();
                return Ok(false);
            }
        }
    }
}
