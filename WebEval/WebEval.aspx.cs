using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace WebEval
{
    public partial class WebEval : System.Web.UI.Page
    {

        //protected string[] _question;
        protected void Page_Load(object sender, EventArgs e)
        {
                dblUNCWId.Visible = true;
                btnLoginSubmit.Visible = true;
                lblIdNumber.Visible = true;
            //_question[0] = "Please Describe a system briefly";
            //_question[1] = "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?";
            //_question[2] = "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?";
            //_question[3] = "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?";
            //_question[4] = "Tell me about possible “Unexpected Outcomes” of this system";
            //_question[5] = "Any suggestions to improve “Unexpected Outcomes” this system?";

        }

        protected void btnLoginSubmit_Click(object sender, EventArgs e)
        {
            if (dblUNCWId.Text == "test")
            {
                dblUNCWId.Visible = false;
                btnLoginSubmit.Visible = false;
                lblIdNumber.Visible = false;
                
            }
        }
    }
}