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
            if (Request.QueryString["StudentNumber"]==null) {
                return; //make sure the profile page is accessed by choosing a student to view
            }
            //populate student profile
            try
            {
                var student = client.GetStudent(Convert.ToInt32(Request.QueryString["StudentNumber"].ToString()));
                String resultsLeft = "<img src='images/avatar.png' alt='Avatar' style='width:100px;height:70px;padding:5px;'>";
                resultsLeft += "<p style='font-size:10px'>"+student.StudentNumber+" <br />ID Number: ####<br />Email: "+student.StudentEmail+"<br />Contact Number: ####</p>";
                ProfileLeft.InnerHtml = resultsLeft;
                String resultsRight = "<h2 style='color:#5023af;'><b>"+student.StudentName.ToUpper()+" "+student.StudentSurname.ToUpper()+"</b></h2>";
                resultsRight += "<p style='font-size:10px'>Campus: ####<br />Faculty: ####<br />File Number: ####<br />Gender: ####</p>";
                ProfileRight.InnerHtml = resultsRight;
            }
            catch (Exception exception) {
               Console.WriteLine(exception.Message);
            
            }
            //populate student diary entries

            //populate student appointments

            //populate student Moods

            //populate student call logs
        }
    }
}