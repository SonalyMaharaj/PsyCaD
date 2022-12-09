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
    public class ExerciseController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Exercise/ActivateExercise")]
        [HttpGet]
        public IHttpActionResult ActivateExercise(int exerciseId)
        {
  
            var ex = (from a in db.Exercises
                      where a.ExerciseId.Equals(exerciseId)
                      select a).FirstOrDefault();
            if (ex != null)
            {
                ex.IsActive = '1'; //activate
            }
            try
            {
                db.SubmitChanges(); 
                return Ok(true);
            }
            catch (InvalidDataContractException exerption)
            {
                exerption.GetBaseException();

                return Ok(false);
            }

        }
        
        [Route("api/Exercise/DeActivateExercise")]
        [HttpGet]
        public IHttpActionResult DeActivateExercise(int exerciseId)
        {
            var ex = (from a in db.Exercises
                      where a.ExerciseId.Equals(exerciseId)
                      select a).FirstOrDefault();
            if (ex != null)
            {
                ex.IsActive = '0'; //deactivate
            }
            try
            {
                db.SubmitChanges(); 
                return Ok(true);
            }
            catch (InvalidDataContractException exerption)
            {
                exerption.GetBaseException();

                return Ok(false);
            }
        }
        

        [Route("api/Exercise/EditExercise")]
        [HttpPost]
        public IHttpActionResult EditExercise(ExerciseClass exerciseClass)
        {

            if (exerciseClass==null) {
                return Ok(false);
            }
            var exercise = (from e in db.Exercises
                            where e.ExerciseId.Equals(exerciseClass.ExerciseId)
                            select e).FirstOrDefault();

            exercise.ExerciseName = exerciseClass.ExerciseName.Trim();
            exercise.ExerciseDescription = exerciseClass.ExerciseDescription.Trim();
            exercise.MediaPath = exerciseClass.MediaPath;
            //edit status & active

            try
            {
                db.SubmitChanges(); //does not want to update
                return Ok(true);
            }
            catch (InvalidDataContractException ex)
            {
                ex.GetBaseException();

                return Ok(false);
            }

        }

        [Route("api/Exercise/GetExercise")]
        [HttpGet]
        public IHttpActionResult GetExercise(int exerciseid)
        {
            var addEx = (from ex in db.Exercises
                             where ex.ExerciseId.Equals(exerciseid)
                             select ex).FirstOrDefault();
            ExerciseClass exerciseClass = new ExerciseClass { ExerciseId=addEx.ExerciseId,ExerciseName=addEx.ExerciseName,ExerciseDescription=addEx.ExerciseDescription,MediaPath=addEx.MediaPath,IsActive=Convert.ToChar( addEx.IsActive )};
            return Ok(exerciseClass);
        }


        [Route("api/Exercise/GetExercises")]
        [HttpGet]
        public IHttpActionResult GetExercises()
        {
            List<ExerciseClass> listExercises = new List<ExerciseClass>();

            var ex = (from e in db.Exercises

                      select e);
            if (ex==null) {
                return Ok(false);
            }
            foreach (Exercise e in ex)
            {
                ExerciseClass exerciseClass = new ExerciseClass { ExerciseId = e.ExerciseId, ExerciseName = e.ExerciseName, ExerciseDescription = e.ExerciseDescription, MediaPath = e.MediaPath, IsActive = Convert.ToChar(e.IsActive) };

                listExercises.Add(exerciseClass);
            }
            return Ok(listExercises);
        }

        [Route("api/Exercise/addExercise")]
        [HttpPost]
        public IHttpActionResult addExercise(ExerciseClass exercise)
        {
            if (exercise==null) {
                return Ok(false);
            }
            var newExercise = new Exercise
            {
                ExerciseId = exercise.ExerciseId,
                ExerciseName = exercise.ExerciseName,
                MediaPath=exercise.MediaPath,
                ExerciseDescription = exercise.ExerciseDescription,
                IsActive = exercise.IsActive,

            };
            db.Exercises.InsertOnSubmit(newExercise);

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e)
            {
                e.GetBaseException();
                return Ok(false);
            }


        }

        [Route("api/Exercise/prescribeExercise")]
        [HttpPost]
        public IHttpActionResult prescribeExercise(PrescribeExerciseClass prescribeExercise)
        {
            //prescribe new Exercise
            var isprescribed = (from a in db.PrescribeExercises
                                  where a.StudentNumber.Equals(prescribeExercise.StudentNumber)
                                  && a.PsychologistId.Equals(prescribeExercise.PsychologistId)
                                  && a.ExerciseId.Equals(prescribeExercise.ExerciseId)
                                  && a.IsPrescribed.Equals(1)
                                  select a).FirstOrDefault();
            if (isprescribed!=null)
            {
                //if the exercise is prescribed the Psychologist should not be allowed to prescribed the same exercise(duplicate prescription)
                return Ok("Duplicate");
            }
            //if (onceprescribed != null)
            //{
                //if the exercise was once prescribed, then update
                //onceprescribed.IsLatestCompleted = 0;
                //onceprescribed.isprescribed = 1;


            //}
            //else
            //{
                var prescribe = new PrescribeExercise
                {
                    PsychologistId = prescribeExercise.PsychologistId,
                    StudentNumber = prescribeExercise.StudentNumber,
                    ExerciseId = prescribeExercise.ExerciseId,
                    DatePrescribed =Convert.ToDateTime(prescribeExercise.DatePrescribed),
                    IsLatestCompleted = 0,
                    NumCompletion = 0,
                    IsPrescribed = 1 //1-> prescribed and 0->not prescribed

                };
                db.PrescribeExercises.InsertOnSubmit(prescribe);
            //}


            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e)
            {
                e.GetBaseException();
                return Ok(false);
            }

        }

        [Route("api/Exercise/UnPrescribeExercise")]
        [HttpPost]
        public IHttpActionResult UnPrescribeExercise(PrescribeExerciseClass prescribeExercise)
        {
            //Unprescribe Exercise
            var onceprescribed = (from a in db.PrescribeExercises
                                  where a.StudentNumber.Equals(prescribeExercise.StudentNumber)
                                  && a.PsychologistId.Equals(prescribeExercise.PsychologistId)
                                  && a.ExerciseId.Equals(prescribeExercise.ExerciseId)
                                  && a.IsPrescribed.Equals(1)
                                  select a).FirstOrDefault();
            if (onceprescribed == null)
            {
                return Ok(false);
                
            }
            //if the exercise was once prescribed, then update
            onceprescribed.IsPrescribed = 0; //not prescribed

            try
            {
                db.SubmitChanges();
                return Ok(true);
            }
            catch (InvalidDataContractException e)
            {
                e.GetBaseException();
                return Ok(false);
            }
        }

        [Route("api/Exercise/exerciseCompleted")]
        [HttpPost]
        public IHttpActionResult exerciseCompleted(PrescribeExerciseClass prescribeExercise)
        {
            //prescribe new Exercise
            var onceprescribed = (from a in db.PrescribeExercises
                                  where a.StudentNumber.Equals(prescribeExercise.StudentNumber)
                                  && a.PsychologistId.Equals(prescribeExercise.PsychologistId)
                                  && a.ExerciseId.Equals(prescribeExercise.ExerciseId)
                                  && a.IsLatestCompleted.Equals(0) //making sure that it does not take the 1st duplicate exercise that has already been complited
                                  select a).FirstOrDefault();
            if (onceprescribed != null)
            {
                //if the exercise was once prescribed, then update
                onceprescribed.IsLatestCompleted = 1;
                onceprescribed.NumCompletion++;
                onceprescribed.IsPrescribed = 0; //not prescribed
                try
                {
                    db.SubmitChanges();
                    return Ok(true);
                }
                catch (InvalidDataContractException e)
                {
                    e.GetBaseException();
                    return Ok(false);
                }

            }
            else
            {
                return Ok(false);
            }



        }

        [Route("api/Exercise/getStudentPrescribedExercises")]
        [HttpGet]
        public IHttpActionResult getStudentPrescribedExercises(int studentNum)
        {

            var prescribed = (from a in db.PrescribeExercises
                              where a.StudentNumber.Equals(studentNum)
                              select a);
            List<PrescribeExerciseClass> exercises = new List<PrescribeExerciseClass>();

            foreach (PrescribeExercise e in prescribed)
            {
                PrescribeExerciseClass exerciseClass = new PrescribeExerciseClass { ID=e.ID,PsychologistId=e.PsychologistId,ExerciseId=e.ExerciseId,StudentNumber=Convert.ToInt32(e.StudentNumber), DatePrescribed=Convert.ToDateTime(e.DatePrescribed),IsLatestCompleted=Convert.ToInt32(e.IsLatestCompleted),NumCompletion=Convert.ToInt32(e.NumCompletion),IsPrescribed=Convert.ToInt32( e.IsPrescribed) };

                exercises.Add(exerciseClass);
            }
            return Ok(exercises);

        }

        [Route("api/Exercise/getAllPrescribedExercises")]
        [HttpGet]
        public IHttpActionResult getAllPrescribedExercises()
        {
            //get all exercises once prescribed and completed
            var prescribed = (from a in db.PrescribeExercises
                              select a);
            List<PrescribeExerciseClass> exercises = new List<PrescribeExerciseClass>();

            foreach (PrescribeExercise e in prescribed)
            {
                PrescribeExerciseClass exerciseClass = new PrescribeExerciseClass { ID = e.ID, PsychologistId = e.PsychologistId, ExerciseId = e.ExerciseId, StudentNumber = Convert.ToInt32(e.StudentNumber), DatePrescribed = Convert.ToDateTime(e.DatePrescribed), IsLatestCompleted = Convert.ToInt32(e.IsLatestCompleted), NumCompletion = Convert.ToInt32(e.NumCompletion), IsPrescribed = Convert.ToInt32(e.IsPrescribed) };

                exercises.Add(exerciseClass);
            }
            return Ok(exercises);

        }

    }
}
