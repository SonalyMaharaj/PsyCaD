using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using HashPass;

namespace MoM_Web
{
    public partial class Login : System.Web.UI.Page
    {
        ServiceReference.MoMServiceClient client = new ServiceReference.MoMServiceClient();
        protected void Page_Load(object sender, EventArgs e)
        {
            
        }

        public void btnLogin_Clicked(object sender, EventArgs e)
        {
            var user = client.loginUser(username.Value, Secrecy.HashPassword(password.Value));

            if (user == null)
            {

                lblstatus.Visible = true;
                return;
            }

            Session["UserType"] = user.userType;
            Session["UserId"] = user.UserId;
            Session["UserName"] = user.Name + " " + user.surname;

            Response.Redirect("Home.aspx");
        }
    }
}