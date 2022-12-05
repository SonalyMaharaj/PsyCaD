using Microsoft.Ajax.Utilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace WebApplication1.Controllers
{
    public class AdminController : ApiController
    {

        [Route("api/Admin/Login")]
        [HttpGet]
        public IHttpActionResult login(string username, string password)
        {
            return null;
        }


    }
}
