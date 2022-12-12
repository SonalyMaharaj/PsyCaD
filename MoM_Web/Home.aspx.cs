using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace MoM_Web
{
    public partial class Home : System.Web.UI.Page
    {
        ServiceReference.MoMServiceClient client = new ServiceReference.MoMServiceClient();
        protected void Page_Load(object sender, EventArgs e)
        {
            string result = "";

            var Students = client.GetPsychologistStudents(Convert.ToInt32(Session["UserId"]));

            foreach (var s in Students) {
                result += "<tr>";
                result += "<td onclick='rowClick("+s.StudentNumber+"); '>" + s.StudentNumber+"</td>";
                result += "<td onclick='rowClick(" + s.StudentNumber + "); '>" + s.StudentName+"</td>";
                result += "<td onclick='rowClick(" + s.StudentNumber + "); '>" + s.StudentSurname+"</td>";
                result += "<td onclick='rowClick(" + s.StudentNumber + "); '>" + s.StudentEmail+"</td>";
                result += "<td onclick='rowClick(" + s.StudentNumber + "); '>" + s.StudentDOB+"</td>";
                result += "<th><button type='button' style='background-color:transparent;border-width:1px;border-radius:10px;'><i class='fa fa-trash-o' aria-hidden='true'></i></button></th>";
                result += "</tr>";
            }

            tablebody.InnerHtml = result;

        }
    }
}