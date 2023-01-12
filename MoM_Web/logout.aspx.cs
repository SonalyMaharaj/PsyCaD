using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace MoM_Web
{
    public partial class logout : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Session["UserType"] = null;
            Session["UserName"] = null;
            Session["UserId"] =null;

            Response.Redirect("Login.aspx");
        }
    }
}