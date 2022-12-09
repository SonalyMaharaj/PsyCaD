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
    public class MedicineTrackerController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/Medicine/GetMedicine")]
        [HttpGet]
        public IHttpActionResult GetMedicine(int medicineId)
        {

            var med = (from a in db.MeidicineTrackers
                       where a.Id.Equals(medicineId)
                       select a).FirstOrDefault();

            if (med==null) 
            {
                return Ok(false);
            }

            MedicineTrackerClass medicineTrackerClass = new MedicineTrackerClass {ID=med.Id,Date=med.Date,NameOfMedicine=med.NameOfMedication,Category=med.Category,StudentNumber=med.StudentNumber,NameOfDoctor=med.NameOfDoctor, IsActive=Convert.ToInt32(med.IsActive) };

            return Ok(medicineTrackerClass);
        }
        
        [Route("api/Medicine/GetMedicines")]
        [HttpGet]
        public IHttpActionResult GetMedicines(int StudentNumber)
        {
            List<MedicineTrackerClass> medicines = new List<MedicineTrackerClass>();
            var list = (from a in db.MeidicineTrackers
                        where a.StudentNumber.Equals(StudentNumber)
                        select a);

            foreach (var med in list) {
                MedicineTrackerClass medicineTrackerClass = new MedicineTrackerClass { ID = med.Id, Date = med.Date, NameOfMedicine = med.NameOfMedication, Category = med.Category, StudentNumber = med.StudentNumber, NameOfDoctor = med.NameOfDoctor, IsActive = Convert.ToInt32(med.IsActive) };

                medicines.Add(medicineTrackerClass);
            }

            return Ok(medicines);


        }
        
        [Route("api/Medicine/LogMedicine")]
        [HttpPost]
        public IHttpActionResult LogMedicine(MedicineTrackerClass medicine)
        {
            if (medicine==null) {
                return Ok(false);
            }

            MeidicineTracker medicineTracker = new MeidicineTracker 
            {
            Date = medicine.Date,
            NameOfMedication = medicine.NameOfMedicine,
            Category = medicine.Category,
            StudentNumber = medicine.StudentNumber,
            NameOfDoctor = medicine.NameOfDoctor,
            IsActive=1,
        };
            db.MeidicineTrackers.InsertOnSubmit(medicineTracker);
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
        
        [Route("api/Medicine/RemoveMedicine")]
        [HttpGet]
        public IHttpActionResult RemoveMedicine(int medicineID)
        {
            var medicine = (from a in db.MeidicineTrackers
                            where a.Id.Equals(medicineID)
                            select a).FirstOrDefault();

            if (medicine == null) {
                return Ok(false);
            }

            medicine.IsActive = 0; //deactivate
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
        
        [Route("api/Medicine/EditMedicine")]
        [HttpPost]
        public IHttpActionResult EditMedicine(MedicineTrackerClass medicine)
        {
            if (medicine==null) {
                return Ok(false);
            }

            var med = (from a in db.MeidicineTrackers
                       where a.Id.Equals(medicine.ID)
                       select a).FirstOrDefault();

            if (med==null) {
                return Ok(false);
            }

            med.Date = medicine.Date;
            med.NameOfMedication = medicine.NameOfMedicine;
            med.Category = medicine.Category;
            med.StudentNumber = medicine.StudentNumber;
            med.NameOfDoctor = medicine.NameOfDoctor;
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
