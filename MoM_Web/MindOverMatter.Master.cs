using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace MoM_Web
{
    public partial class MindOverMatter : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["UserType"]==null)  //if no user is logged in
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (Session["UserType"].ToString()=="Psychologist") { 
            
            }

            else if (Session["UserType"].ToString() == "Admin")
            {

            }
            else 
            {
                Response.Redirect("Login.aspx");
            }
        }
    }
}