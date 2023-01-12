using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MoM_Web.Models
{
    public class PrescribeExerciseClass
    {

        public int ID { get; set; }
        public int PsychologistId { get; set; }
        public int ExerciseId { get; set; }
        public int StudentNumber { get; set; }

        public DateTime DatePrescribed { get; set; }

        public int IsLatestCompleted { get; set; }

        public int NumCompletion { get; set; }

        public int IsPrescribed { get; set; }

    }
}