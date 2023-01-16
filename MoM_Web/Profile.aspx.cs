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
            /*          <tr>
                            <td>28 January 2022 10am</td>
                            <td>Death</td>
                            <td><input type='checkbox' class='checkstyle' name='attend'/></td>
                        </tr>*/

            var diaries = client.GetStudentDiaryEntries(Convert.ToInt32(Request.QueryString["StudentNumber"])); //fetch Student's diaries
            String result = "";
            foreach (var diary in diaries) {
                result += "<tr>";
                result += "<td>28 January 2022 10am</td>";
                result += "<td>Death</td>";
                result += "<td><input type='checkbox' class='checkstyle' name='attend'/></td>";
                result += "</tr>";
            }

            diaryBody.InnerHtml = result;

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
            /*<tr>
                            <td>01 May 2022 1pm</td>
                            <td>Happy</td>
                        </tr>*/

            var moods = client.GetMoods();
            String result = "";
            foreach (var mood in moods)
            {
                result += "<tr>";
                result += "<td>"+mood.MoodDate+"</td>";
                result += "<td>"+mood.MoodEmotion+"</td>";
                result += "</tr>";
            }
            moodBody.InnerHtml = result;
        }

        public void populateCallLogs() 
        {
            //code for populating call logs
        }
    }
}