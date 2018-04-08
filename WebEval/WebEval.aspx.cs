using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using MySql.Data.MySqlClient;

namespace WebEval
{
    public partial class WebEval : System.Web.UI.Page
    {
        
        DataTable questions = new DataTable();
        DataTable users = new DataTable();
        DataTable responses = new DataTable();

        
        

        public int questionCounter;
        public string query;
        public object recording;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                MySqlConnectionStringBuilder conn_string = new MySqlConnectionStringBuilder();
                conn_string.Server = "localhost";
                conn_string.UserID = "root";
                conn_string.Password = "password";
                conn_string.Database = "webeval";
                

                questionCounter = -1;
                dblUNCWId.Visible = true;
                btnLoginSubmit.Visible = true;
                lblIdNumber.Text = "UNCW ID Number";
                btnLeft.Visible = false;
                btnRight.Visible = false;
                lblQuestion.Visible = false;
                btnRecord.Visible = false;
                btnPlay.Visible = false;
                btnReset.Visible = false;
                btnStop.Visible = false;
                dgvResponses.Visible = false;

                query = "SELECT question FROM questions";
                questions = queryDB(questions, query);


                Session["questions"] = questions;
                Session["counter"] = questionCounter;               
            }
        }

        protected void btnLoginSubmit_Click(object sender, EventArgs e)
        {

            if (dblUNCWId.Text != "")
            {
                query = "SELECT * FROM Users WHERE Id = '" + dblUNCWId.Text + "'";
                users = queryDB(users, query);
                Session["users"] = users;
                if (users.Rows[0]["Id"].ToString() == "choij") {
                    //ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('Record Inserted Successfully')", true);
                    Response.Redirect("AdminPage.aspx", false);
                };

                lblTitle.Text = "Hello " + users.Rows[0]["Username"].ToString();
                dblUNCWId.Visible = false;
                btnLoginSubmit.Visible = false;
                btnRight.Visible = true;
                btnLeft.Visible = true;
                btnRecord.Visible = true;
                btnPlay.Visible = true;
                btnReset.Visible = true;
                btnStop.Visible = true;
                lblQuestion.Visible = true;
                dgvResponses.Visible = true;
                
                dgvResponses.DataSource = Session["reponses"];
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
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            else if(counter == questions.Rows.Count-1)
            {
                counter = questions.Rows.Count-1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            else
            {
                counter += 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
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
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            else if (counter == 0)
            {
                counter = 0;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            else
            {
                counter -= 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            Session["counter"] = counter;
        }

        protected DataTable queryDB(DataTable dt, string query)
       {
            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                da.Fill(dt);
                return dt;
            }
        }

        protected void saveRecording(object recording, string qNumber, string userID)
        {

        }
    }
}