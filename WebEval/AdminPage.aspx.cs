/*
Created by: Brent Belanger, Henry Ni, Will Jacobs, Mike Mingo
Written April, 2018
AdminPage.aspx

The purpose of this program is to provide an interface for an administrator to alter database data without having to interact directly with the DB.

Last revised on 4/28/2018 by BCB.

INPUT: admin name from main page
OUTPUT: Logged into the site for administrative purposes.

*/

using System;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Text.RegularExpressions;
using MySql.Data.MySqlClient;

namespace WebEval
{
    public partial class AdminPage : System.Web.UI.Page
    {
        //global variable
        // private SqlConnection conn = new SqlConnection(Properties.Settings.Default.DBCon);
        //MySqlConnectionStringBuilder conn = new MySqlConnectionStringBuilder(Properties.Settings.Default.DBCon);

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


                gvbind();
                gvbind2();
                gvbind3();
            }
        }
        protected void gvbind()
        {
            /*********************************************
             ************ Populate User Table ************
             *********************************************
             * 
             * INPUT: None
             * OUTPUT: User datatable Load
             * PURPOSE: This initialized the users datatable.
             */

            //set the query
            string query = "Select * from users";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                connection.Close();

                //if there are rows...
                if (ds.Tables[0].Rows.Count > 0)
                {
                    GridView1.DataSource = ds;
                    GridView1.DataBind();
                }
                //if there are no rows
                else
                {
                    ds.Tables[0].Rows.Add(ds.Tables[0].NewRow());
                    GridView1.DataSource = ds;
                    GridView1.DataBind();
                    int columncount = GridView1.Rows[0].Cells.Count;
                    GridView1.Rows[0].Cells.Clear();
                    GridView1.Rows[0].Cells.Add(new TableCell());
                    GridView1.Rows[0].Cells[0].ColumnSpan = columncount;
                    GridView1.Rows[0].Cells[0].Text = "No Records Found";
                }
            }
        }
        protected void GridView1_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            /**********************************************************
             ************ Delete Row from DT and update DB ************
             **********************************************************
             * 
             * INPUT: User deletes a row
             * OUTPUT: Row deleted from the DB and the datatable is updated to reflect change.
             * PURPOSE: This function is so that the administrator can delete users from the DB
             */

            string query = "delete FROM Users where Id='" + Convert.ToInt64(GridView1.DataKeys[e.RowIndex].Value.ToString()) + "'";


            GridViewRow row = (GridViewRow)GridView1.Rows[e.RowIndex];
            Label lbldeleteid = (Label)row.FindControl("lblID");

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                connection.Close();
                gvbind();
            }
        }
        protected void GridView1_RowEditing(object sender, GridViewEditEventArgs e)
        {
            /************************************
             ************ Change Row ************
             ************************************
             * 
             * INPUT: User Click
             * OUTPUT: Row gets new index
             * PURPOSE: This changes the row index for the DataTable
             */
            GridView1.EditIndex = e.NewEditIndex;
            gvbind();
        }
        protected void GridView1_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            /**************************************
             ************ Update a Row ************
             **************************************
             * 
             * INPUT: User updates info in a row
             * OUTPUT: Data is updated in the database and the datatable is refreshed to reflect changes.
             * PURPOSE: This allows the administrator to make changes, such as userID is different, misspelled name, and so forth.
             */

            int userid = Convert.ToInt32(GridView1.DataKeys[e.RowIndex].Value.ToString());
            GridViewRow row = (GridViewRow)GridView1.Rows[e.RowIndex];
            Label lblID = (Label)row.FindControl("lblID");
            TextBox textId = (TextBox)row.Cells[0].Controls[0];
            TextBox textUsername = (TextBox)row.Cells[1].Controls[0];
            GridView1.EditIndex = -1;

            //set the query
            string query = "update Users set Id='" + textId.Text + "', Username ='" + textUsername.Text + "' where Id='" + userid + "'";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                connection.Close();
                gvbind();
            }
        }
        protected void GridView1_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            /***********************************
             ************ Change Page Index ************
             ***********************************
             * 
             * INPUT: User Click
             * OUTPUT: Index changes
             * PURPOSE: This changes the page index of the GridView.
             */
            GridView1.PageIndex = e.NewPageIndex;
            gvbind();
        }
        protected void GridView1_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            /***********************************
             ************ Cancel Edit ************
             ***********************************
             * 
             * INPUT: User Cancels Edit
             * OUTPUT: No edit made
             * PURPOSE: This cancels the edit the user tried to make
             */

            GridView1.EditIndex = -1;
            gvbind();
        }

        //The remaining functions serve the same purposes as above, except for the gridview number

        protected void gvbind2()
        {

            string query = "Select * from questions";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                connection.Close();

                if (ds.Tables[0].Rows.Count > 0)
                {
                    GridView2.DataSource = ds;
                    GridView2.DataBind();
                }
                else
                {
                    ds.Tables[0].Rows.Add(ds.Tables[0].NewRow());
                    GridView2.DataSource = ds;
                    GridView2.DataBind();
                    int columncount = GridView2.Rows[0].Cells.Count;
                    GridView2.Rows[0].Cells.Clear();
                    GridView2.Rows[0].Cells.Add(new TableCell());
                    GridView2.Rows[0].Cells[0].ColumnSpan = columncount;
                    GridView2.Rows[0].Cells[0].Text = "No Records Found";
                }
            }
        }
        protected void GridView2_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            GridViewRow row = (GridViewRow)GridView2.Rows[e.RowIndex];
            Label lbldeleteid = (Label)row.FindControl("lblID");

            string query = "delete FROM questions where Id='" + Convert.ToInt64(GridView2.DataKeys[e.RowIndex].Value.ToString()) + "'";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                connection.Close();
                gvbind2();
            }
        }
        protected void GridView2_RowEditing(object sender, GridViewEditEventArgs e)
        {
            GridView2.EditIndex = e.NewEditIndex;
            gvbind2();
        }
        protected void GridView2_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            int userid = Convert.ToInt32(GridView2.DataKeys[e.RowIndex].Value.ToString());
            GridViewRow row = (GridViewRow)GridView2.Rows[e.RowIndex];
            Label lblID = (Label)row.FindControl("lblID");
            //TextBox txtname=(TextBox)gr.cell[].control[];  
            TextBox textId = (TextBox)row.Cells[0].Controls[0];
            TextBox textUsername = (TextBox)row.Cells[1].Controls[0];
            // TextBox textc = (TextBox)row.Cells[2].Controls[0];
            //TextBox textadd = (TextBox)row.FindControl("txtadd");  
            //TextBox textc = (TextBox)row.FindControl("txtc");  
            GridView2.EditIndex = -1;

            string query = "update questions set Id='" + textId.Text + "', question ='" + textUsername.Text + "' where Id='" + userid + "'";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                gvbind2();
            }
        }
        protected void GridView2_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            GridView2.PageIndex = e.NewPageIndex;
            gvbind2();
        }
        protected void GridView2_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            GridView2.EditIndex = -1;
            gvbind2();
        }

        protected void gvbind3()
        {

            string query = "Select * from responses";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                MySqlDataAdapter da = new MySqlDataAdapter(cmd);
                DataSet ds = new DataSet();
                da.Fill(ds);
                connection.Close();
                if (ds.Tables[0].Rows.Count > 0)
                {
                    GridView3.DataSource = ds;
                    GridView3.DataBind();
                }
                else
                {
                    ds.Tables[0].Rows.Add(ds.Tables[0].NewRow());
                    GridView3.DataSource = ds;
                    GridView3.DataBind();
                    int columncount = GridView3.Rows[0].Cells.Count;
                    GridView3.Rows[0].Cells.Clear();
                    GridView3.Rows[0].Cells.Add(new TableCell());
                    GridView3.Rows[0].Cells[0].ColumnSpan = columncount;
                    GridView3.Rows[0].Cells[0].Text = "No Records Found";
                }
            }
        }
        protected void GridView3_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {

            string query = "delete FROM responses where Id='" + Convert.ToInt64(GridView3.DataKeys[e.RowIndex].Value.ToString()) + "'";

            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                connection.Close();
                GridViewRow row = (GridViewRow)GridView3.Rows[e.RowIndex];
                Label lbldeleteid = (Label)row.FindControl("lblID");
                gvbind3();
            }
        }
        protected void GridView3_RowEditing(object sender, GridViewEditEventArgs e)
        {
            GridView3.EditIndex = e.NewEditIndex;
            gvbind3();
        }
        protected void GridView3_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            int userid = Convert.ToInt32(GridView3.DataKeys[e.RowIndex].Value.ToString());
            GridViewRow row = (GridViewRow)GridView3.Rows[e.RowIndex];
            Label lblID = (Label)row.FindControl("lblID");
            TextBox textId = (TextBox)row.Cells[0].Controls[0];
            TextBox textQuestionNumber = (TextBox)row.Cells[1].Controls[0];
            TextBox textc = (TextBox)row.Cells[2].Controls[0];
            GridView3.EditIndex = -1;

            string query = "update responses set Id='" + textId.Text + "', Username ='" + textQuestionNumber.Text + "' where Id='" + userid + "'";


            using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
            using (MySqlCommand cmd = new MySqlCommand(query, connection))
            {
                connection.Open();
                cmd.ExecuteNonQuery();
                connection.Close();
                gvbind3();
            }
        }
        protected void GridView3_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            GridView3.PageIndex = e.NewPageIndex;
            gvbind3();
        }
        protected void GridView3_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            GridView3.EditIndex = -1;
            gvbind3();
        }

        protected void newUserSubmit_Click(object sender, EventArgs e)
        {
            Regex regex = new Regex(@"^[0-9]+$");
            if (regex.IsMatch(new850.Text))
            {

                string query = "INSERT into Users (Id, Username) VALUES ('" + new850.Text + "', '" + newName.Text + "')";

                using (MySqlConnection connection = new MySqlConnection(Properties.Settings.Default.DBCon))
                using (MySqlCommand cmd = new MySqlCommand(query, connection))
                {
                    connection.Open();
                    cmd.ExecuteNonQuery();
                    connection.Close();
                    try
                    {
                        cmd.ExecuteNonQuery();
                    }
                    catch (SqlException ex)
                    {
                        newUserError.Text = ex.ToString();
                        throw;
                    }

                    connection.Close();
                }
                new850.Text = "";
                newName.Text = "";
                gvbind();
            }
        }
    }
}