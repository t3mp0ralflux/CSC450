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
        
        DataTable questions;
        DataTable question1;
        DataTable question2;
        DataTable question3;
        DataTable question4;
        DataTable question5;

        public int questionCounter;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                questions = new DataTable();
                question1 = new DataTable();
                question2 = new DataTable();
                question3 = new DataTable();
                question4 = new DataTable();
                question5 = new DataTable();


                questionCounter = -1;
                dblUNCWId.Visible = true;
                btnLoginSubmit.Visible = true;
                lblIdNumber.Text = "UNCW ID Number";
                btnLeft.Visible = false;
                btnRight.Visible = false;
                lblQuestion.Visible = false;
                btnRecStop.Visible = false;
                btnPlayPause.Visible = false;
                questions.Columns.Add("Questions");
                questions.Rows.Add("Please Describe a system briefly");
                questions.Rows.Add("How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?");
                questions.Rows.Add("How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?");
                questions.Rows.Add("How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?");
                questions.Rows.Add("Tell me about possible “Unexpected Outcomes” of this system");
                questions.Rows.Add("Any suggestions to improve “Unexpected Outcomes” this system?");
                Session["questions"] = questions;
                Session["counter"] = questionCounter;
                question1.Columns.Add("Name");
                question1.Columns.Add("Response");
                question1.Rows.Add("Stacy", "Question1");
                Session["question1"] = question1;
                
            }
        }

        protected void btnLoginSubmit_Click(object sender, EventArgs e)
        {
            if (dblUNCWId.Text == "test")
            {
                lblTitle.Text = "Hello " + dblUNCWId.Text;
                dblUNCWId.Visible = false;
                btnLoginSubmit.Visible = false;
                btnRight.Visible = true;
                btnLeft.Visible = true;
                btnRecStop.Visible = true;
                btnPlayPause.Visible = true;
                lblQuestion.Visible = true;
                dgvResponses.DataSource = Session["question1"];
                dgvResponses.DataBind();
                
                
            }
        }

        protected void right_arrow_Click(object sender, ImageClickEventArgs e)
        {
            int counter = Convert.ToInt32(Session["counter"].ToString());
            questions = Session["questions"] as DataTable;

            if (counter == -1)
            {
                counter = 0;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            else if(counter == questions.Rows.Count-1)
            {
                counter = questions.Rows.Count-1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            else
            {
                counter += 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            Session["counter"] = counter;
        }

        protected void left_arrow_Click(object sender, ImageClickEventArgs e)
        {
            int counter = Convert.ToInt32(Session["counter"].ToString());
            questions = Session["questions"] as DataTable;

            if (counter == -1)
            {
                counter = 0;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            else if (counter == 0)
            {
                counter = 0;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            else
            {
                counter -= 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("Questions");
            }
            Session["counter"] = counter;
        }
    }
}