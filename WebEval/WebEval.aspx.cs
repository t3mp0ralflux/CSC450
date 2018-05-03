/*
Created by: Brent Belanger, Henry Ni, Will Jacobs, Mike Mingo
Written April, 2018
WebEval.aspx

The purpose of this program is to provide an interface for students to record audio responses to questions in a questionnaire.
This is the main body of the code-behind that drives the front-end.

Last revised on 4/28/2018 by BCB.

INPUT: UNCW ID
OUTPUT: Logged into the site for voice recordings

*/
using System;
using System.Data;
using System.Web.UI;
using MySql.Data.MySqlClient;

namespace WebEval
{
    public partial class WebEval : System.Web.UI.Page
    {
        //initial DT assignments
        DataTable questions = new DataTable();
        DataTable users = new DataTable();
        DataTable responses = new DataTable();

        //some global variables
        public int questionCounter;
        public string query;
        public object recording;

        
        protected void Page_Load(object sender, EventArgs e)
        {
            /***********************************
             ************ Page Load ************
             ***********************************
             * 
             * INPUT: None
             * OUTPUT: Page Load
             * PURPOSE: This initialized the page on first load, but not on subsequent postbacks.
             */

            if (!IsPostBack)
            {
                //setup the MySql data connections.  Change to match the local DB when it's made.
                MySqlConnectionStringBuilder conn_string = new MySqlConnectionStringBuilder();
                conn_string.Server = "localhost";
                conn_string.UserID = "root";
                conn_string.Password = "YES";
                conn_string.Database = "webeval";

                //initial view setup
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
                dgvResponses.Visible = true;

                //grab the questions from the DB
                query = "SELECT question FROM questions";
                questions = queryDB(questions, query);

                //initialize the quesitons Session and counter Session.
                //Without Session, the variables get wiped.
                Session["questions"] = questions;
                Session["counter"] = questionCounter;

                //format the responses table to be accurate to the DB
                responses.Columns.Add(new DataColumn("QuestionNumber"));
                responses.Columns.Add(new DataColumn("Response"));
                responses.Columns.Add(new DataColumn("UserName"));

                //query the response table to save stored responses
                query = "SELECT QuestionNumber, Response, UserName FROM responses ORDER BY QuestionNumber ASC";
                responses = queryDB(responses, query);

                Session["responses"] = responses;
                
            }
        }

        
        protected void btnLoginSubmit_Click(object sender, EventArgs e)
        /* 
         ******************************************
         ************ Login to System *************
         ******************************************
         * 
         * INPUT: UNCW Id from earlier
         * OUTPUT: user screen
         * PURPOSE: This function initializes the view for the user and displays the controls needed to record
         */
        {
            //if the user exists...
            if (dblUNCWId.Text != "")
            {
                query = "SELECT * FROM Users WHERE Id = '" + dblUNCWId.Text + "'";
                users = queryDB(users, query);
                Session["users"] = users;

                if (users.Rows[0]["Id"].ToString() == "choij")
                {
                    Session.Abandon();
                    Response.Redirect("AdminPage.aspx", false);
                };

                //display login and show controls
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
                btnLogout.Visible = true;

                // this is set aside for now as there is no way to store responses.
               // dgvResponses.DataSource = Session["responses"];
               // dgvResponses.DataBind();

            }
        }

        protected void right_arrow_Click(object sender, ImageClickEventArgs e)
        {
            /* 
             ***********************************************************
             ************ Click The Right Direction Button *************
             ***********************************************************
             * 
             * INPUT: Button Click from user
             * OUTPUT: Question and responses cycle to the next question in the queue
             * PURPOSE: This function scrolls the questions and responses to the next question / response in the list.
             *          If at the end of the list, it doesn't continue and stays on the last question.
             */

            //local variable setup
            int counter = Convert.ToInt32(Session["counter"].ToString());
            int responseCounter;

            //pull the session questions and responses from earlier
            questions = Session["questions"] as DataTable;
            responses = Session["responses"] as DataTable;

            //if at the start
            if (counter == -1)
            {
                counter = 0;
                responseCounter = 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");

            }
            //if at the end of the questions list
            else if (counter == questions.Rows.Count - 1)
            {
                counter = questions.Rows.Count - 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");
            }
            //for all other scenarios
            else
            {
                counter += 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");

            }

            //set the global counter to one more
            Session["counter"] = counter;
            responseCounter = counter + 1;
            DataTable temp = new DataTable();

            //change the results to match the counter increase
            DataRow[] results = responses.Select("QuestionNumber = " + responseCounter);
            foreach (DataRow dr in results)
            {
                temp.ImportRow(dr);
            }

            //bind the data to the datatable and display
            dgvResponses.DataSource = temp;
            dgvResponses.DataBind();

        }

        protected void left_arrow_Click(object sender, ImageClickEventArgs e)
        {
            /* 
             ***********************************************************
             ************ Click The Left Direction Button *************
             ***********************************************************
             * 
             * INPUT: Button Click from user
             * OUTPUT: Question and responses cycle to the next question in the queue
             * PURPOSE: This function scrolls the questions and responses to the previous question / response in the list.
             *          If at the end of the list, it doesn't continue and stays on the last question.
             */

            //set the local counter to the session one
            int counter = Convert.ToInt32(Session["counter"].ToString());
            int responseCounter;

            //pull the session questions and responses from earlier
            questions = Session["questions"] as DataTable;
            responses = Session["responses"] as DataTable;

            if (counter == -1)
            {
                counter = 0;
                responseCounter = 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");

            }
            else if (counter == 0)
            {
                counter = 0;
                responseCounter = 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");

            }
            else
            {
                counter -= 1;
                lblQuestion.Text = questions.Rows[counter].Field<string>("question");

            }
            //set the session counter to the local one
            Session["counter"] = counter;
            responseCounter = counter - 1;
            DataTable temp = new DataTable();

            //change the results to match the counter increase
            DataRow[] results = responses.Select("QuestionNumber = " + responseCounter);
            foreach (DataRow dr in results)
            {
                temp.ImportRow(dr);
            }

            //bind the data to the datatable and display
            dgvResponses.DataSource = temp;
            dgvResponses.DataBind();
        }

        public DataTable queryDB(DataTable dt, string query)
        {
            /* 
             *********************************************
             ************ Query the Database *************
             *********************************************
             * 
             * INPUT: DataTable and a Query for that DataTable
             * OUTPUT: A DataTable populated with the query results
             * PURPOSE: This function executes sql queries to the database.
             */
            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                da.Fill(dt);
                return dt;
            }
        }

        protected void btnLogout_Click(object sender, EventArgs e)
        {
            /* 
             *********************************
             ************ Logout *************
             *********************************
             * 
             * INPUT: Button click by user
             * OUTPUT: Return to login screen
             * PURPOSE: This function ends the session and logs the user out.
             */
            Session.Abandon();
            Response.Redirect("WebEval.aspx", true);
        }

        protected void btnUpload_Click(object sender, ImageClickEventArgs e)
        {

        }
    }


}