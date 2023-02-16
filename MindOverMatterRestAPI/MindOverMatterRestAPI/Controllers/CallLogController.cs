using MindOverMatterRestAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace MindOverMatterRestAPI.Controllers
{
    public class CallLogController : ApiController
    {
        DataClasses1DataContext db = new DataClasses1DataContext();

        [Route("api/CallLog/LogCall")]
        [HttpPost]
        public IHttpActionResult LogCall(CallModel call) {

            //add new mood
            if (call == null)
            {
                return Ok(false);
            }

            CallLog callLog = new CallLog
            {
                TelHolder = call.TelHolder,
                TelNumber = call.TelNumber,
                CallDate = DateTime.Now,
                CallTime = DateTime.Now.TimeOfDay.ToString(),
                StudentNumber =Convert.ToInt32(call.StudentNumber)
            };

            db.CallLogs.InsertOnSubmit(callLog);

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

        [Route("api/CallLog/getCallLogs")]
        [HttpGet]
        public IHttpActionResult getCallLogs() {

            List<CallModel> calls = new List<CallModel>();
            var list = (from a in db.CallLogs
                        select a);
            if (list == null)
            {
                return Ok(false); //return false if the list is null
            }

            var listArray = list.ToArray<CallLog>(); //convert the returned list to an Array

            for (int i = listArray.Length - 1; i >= 0; i--)
            {
                //Store the CalLogs from recent to Old, ascending Order and Descending Order in the DB
                CallModel call = new CallModel { CallId = listArray[i].CallId, TelHolder = listArray[i].TelHolder, TelNumber = listArray[i].TelNumber, CallDate = Convert.ToString(listArray[i].CallDate), CallTime = listArray[i].CallTime, StudentNumber = Convert.ToString(listArray[i].StudentNumber) };
                calls.Add(call);

            }

            return Ok(calls);
        }

        [Route("api/CallLog/getCallLogs")]
        [HttpGet]
        public IHttpActionResult getCallLogs(int StudentNumber)
        {

            List<CallModel> calls = new List<CallModel>();
            var list = (from a in db.CallLogs
                        where a.StudentNumber.Equals(StudentNumber)
                        select a);
            if (list == null)
            {
                return Ok(false); //return false if the list is null
            }

            var listArray = list.ToArray<CallLog>(); //convert the returned list to an Array

            for (int i= listArray.Length-1;i>=0;i--) {
                //Store the CalLogs from recent to Old, ascending Order and Descending Order in the DB
                CallModel call = new CallModel { CallId = listArray[i].CallId, TelHolder = listArray[i].TelHolder, TelNumber = listArray[i].TelNumber, CallDate = Convert.ToString(listArray[i].CallDate), CallTime = listArray[i].CallTime, StudentNumber = Convert.ToString(listArray[i].StudentNumber) };
                calls.Add(call);

            }

            return Ok(calls);
        }


    }
}
