using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebEval
{
    public partial class WebEval : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            lblTest.Visible = false;
        }

        protected void btnLoginSubmit_Click(object sender, EventArgs e)
        {
            if(dblUNCWId.Text == "test"){
                dblUNCWId.Visible = false;
                btnLoginSubmit.Visible = false;
                lblTest.Visible = true;
            }
        }
    }
}