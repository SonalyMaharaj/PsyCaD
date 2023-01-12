using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace MoM_Web
{
    public partial class Profile : System.Web.UI.Page
    {
        ServiceReference.MoMServiceClient client = new ServiceReference.MoMServiceClient();
        protected void Page_Load(object sender, EventArgs e)
        {
            if () { 
            
            }
            var student = client.GetStudent();
            String result = "<img src='images/avatar.png' alt='Avatar' style='width:100px;height:70px;padding:5px;'>" +
                "<p style='font-size:10px'>217031068 <br />ID Number: 9903010181085<br />Email: janedoe@gmail.com<br />Contact Number: 0725669054</p>";

            ProfileLeft.InnerHtml = result;
        }
    }
}