using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApplication1.Controllers
{
    public class PsychologistController : ApiController
    {
        [Route("api/Psychologist/Login")]
        [HttpGet]
        public IHttpActionResult login(string username, string password) 
        {

            return null;

        }



        [Route("api/Psychologist/LoginStudent")]
        [HttpGet]
        public IHttpActionResult GetPsychologist(int PsychID) 
        { 

            return null;

        }

        [Route("api/Psychologist/LoginStudent")]
        [HttpGet]
        public IHttpActionResult GetPsychologists() 
        {
            return null;
        }

    }
}
