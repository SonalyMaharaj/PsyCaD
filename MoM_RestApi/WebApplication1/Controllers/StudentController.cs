using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApplication1.Controllers
{
    
    public class StudentController : ApiController
    {

        [Route("api/Student/LoginStudent")]
        [HttpGet]
        public IHttpActionResult LoginStudent(string username, string password) 
        {
            return null;
        }

        [Route("api/Student/GetStudent")]
        [HttpGet]
        public IHttpActionResult GetStudent(int adminID)
        {

            return null;
        }

        [Route("api/Student/GetStudents")]
        [HttpGet]
        public IHttpActionResult GetStudents() 
        {
            return null;
        }

    }
}
