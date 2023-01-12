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
                return;
            }

            int studentnumber = Convert.ToInt32(Request.QueryString["StudentNumber"]);
            var student = client.GetStudent(studentnumber);
            if (student==null)
            {
                ProfileLeft.InnerHtml = "STUDENT NOT FOUND";
                return; //if the student is not found then display nothing
            }
            //generate html for the Left block of the profile
            String resultLeft = "<img src='images/avatar.png' alt='Avatar' style='width:100px;height:70px;padding:5px;'>" +
                "<p style='font-size:10px'>"+student.StudentNumber+" <br />ID Number: #######<br />Email: "+student.StudentEmail+"<br />Contact Number: #####</p>";

            ProfileLeft.InnerHtml = resultLeft;

            //generate html for the right block of the profile

            /*<h2 style='color:#5023af;'><b>Jane Doe</b></h2>
                         <p style='font-size:10px'>Campus: APK<br />Faculty: Science<br />File Number: xxxxxxxxxx<br />Gender: Female</p>*/

            String resultRight = "<h2 style='color:#5023af;'><b>"+student.StudentName+" "+student.StudentSurname+"</b></h2>" +
                "<p style='font-size:10px'>Campus: ###<br />Faculty: Science<br />File Number: xxxxxxxxxx<br />Gender: ####</p>";

            ProfileRight.InnerHtml = resultRight;


            //populate student diary entries
            populateDiaryEntries();
            //populate student appointments
            appointmentBody.InnerHtml=populateAppointments();
            //populate student Moods
            populateMoods();
            //populate student call logs
            populateCallLogs();
        }

        public void populateDiaryEntries()
        { 

        }

        public String populateAppointments() 
        {
            var appointments = client.getStudentBookings(Convert.ToInt32(Request.QueryString["StudentNumber"])); //request list of appointments for specific psychologist
            String results = "";
            foreach (var appoint in appointments) 
            {
                var psychologist = client.GetPsychologist(appoint.PsychologistId); //get psychologist who was appointed by the student for this specific appointment
                results += "<tr>";
                results += "<td>"+appoint.BookDate+"</td>";
                results += "<td>"+psychologist.PsychologistName.Substring(0,1)+" "+psychologist.PsychologistSurname+" "+psychologist.PsychologistId+"</td>"; // e.g S MOYO ID: 2001210
                results += "<tr>";
            }

           return results;

        }

        public void populateMoods() 
        {
            
        }

        public void populateCallLogs() 
        {
            
        }
    }
}