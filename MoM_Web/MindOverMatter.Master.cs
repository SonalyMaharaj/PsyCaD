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
        {/* Session["UserType"] = user.userType;
            Session["UserId"] = user.UserId;*/

               //Ensure that every page under this master will only load or be viewed by Someone who has logged in
            if (Session["UserType"]==null) {
                Response.Redirect("Login.aspx");     
            }
        }
    }
}