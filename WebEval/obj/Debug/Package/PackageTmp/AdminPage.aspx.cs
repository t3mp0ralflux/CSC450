using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Data;
using System.Text.RegularExpressions;

namespace WebEval
{
    public partial class AdminPage : System.Web.UI.Page
    {
        private SqlConnection conn = new SqlConnection(Properties.Settings.Default.DBCon);
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                gvbind();
                gvbind2();
                gvbind3();
            }
        }
        protected void gvbind()
        {
            conn.Open();
            SqlCommand cmd = new SqlCommand("Select * from users", conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();
            da.Fill(ds);
            conn.Close();
            if (ds.Tables[0].Rows.Count > 0)
            {
                GridView1.DataSource = ds;
                GridView1.DataBind();
            }
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
        protected void GridView1_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            GridViewRow row = (GridViewRow)GridView1.Rows[e.RowIndex];
            Label lbldeleteid = (Label)row.FindControl("lblID");
            conn.Open();
            SqlCommand cmd = new SqlCommand("delete FROM Users where Id='" + Convert.ToInt32(GridView1.DataKeys[e.RowIndex].Value.ToString()) + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind();
        }
        protected void GridView1_RowEditing(object sender, GridViewEditEventArgs e)
        {
            GridView1.EditIndex = e.NewEditIndex;
            gvbind();
        }
        protected void GridView1_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            int userid = Convert.ToInt32(GridView1.DataKeys[e.RowIndex].Value.ToString());
            GridViewRow row = (GridViewRow)GridView1.Rows[e.RowIndex];
            Label lblID = (Label)row.FindControl("lblID");
            //TextBox txtname=(TextBox)gr.cell[].control[];  
            TextBox textId = (TextBox)row.Cells[0].Controls[0];
            TextBox textUsername = (TextBox)row.Cells[1].Controls[0];
           // TextBox textc = (TextBox)row.Cells[2].Controls[0];
            //TextBox textadd = (TextBox)row.FindControl("txtadd");  
            //TextBox textc = (TextBox)row.FindControl("txtc");  
            GridView1.EditIndex = -1;
            conn.Open();
            //SqlCommand cmd = new SqlCommand("SELECT * FROM detail", conn);  
            SqlCommand cmd = new SqlCommand("update Users set Id='" + textId.Text + "', Username ='" + textUsername.Text + "' where Id='" + userid + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind();
            //GridView1.DataBind();  
        }
        protected void GridView1_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            GridView1.PageIndex = e.NewPageIndex;
            gvbind();
        }
        protected void GridView1_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            GridView1.EditIndex = -1;
            gvbind();
        }

        protected void gvbind2()
        {
            conn.Open();
            SqlCommand cmd = new SqlCommand("Select * from questions", conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();
            da.Fill(ds);
            conn.Close();
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
        protected void GridView2_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            GridViewRow row = (GridViewRow)GridView2.Rows[e.RowIndex];
            Label lbldeleteid = (Label)row.FindControl("lblID");
            conn.Open();
            SqlCommand cmd = new SqlCommand("delete FROM questions where Id='" + Convert.ToInt32(GridView2.DataKeys[e.RowIndex].Value.ToString()) + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind2();
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
            conn.Open();
            //SqlCommand cmd = new SqlCommand("SELECT * FROM detail", conn);  
            SqlCommand cmd = new SqlCommand("update questions set Id='" + textId.Text + "', question ='" + textUsername.Text + "' where Id='" + userid + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind2();
            //GridView1.DataBind();  

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
            conn.Open();
            SqlCommand cmd = new SqlCommand("Select * from responses", conn);
            SqlDataAdapter da = new SqlDataAdapter(cmd);
            DataSet ds = new DataSet();
            da.Fill(ds);
            conn.Close();
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
        protected void GridView3_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            GridViewRow row = (GridViewRow)GridView3.Rows[e.RowIndex];
            Label lbldeleteid = (Label)row.FindControl("lblID");
            conn.Open();
            SqlCommand cmd = new SqlCommand("delete FROM responses where Id='" + Convert.ToInt32(GridView3.DataKeys[e.RowIndex].Value.ToString()) + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind3();
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
            //TextBox txtname=(TextBox)gr.cell[].control[];  
            TextBox textId = (TextBox)row.Cells[0].Controls[0];
            TextBox textQuestionNumber = (TextBox)row.Cells[1].Controls[0];
            TextBox textc = (TextBox)row.Cells[2].Controls[0];
            //TextBox textadd = (TextBox)row.FindControl("txtadd");  
            //TextBox textc = (TextBox)row.FindControl("txtc");  
            GridView3.EditIndex = -1;
            conn.Open();
            //SqlCommand cmd = new SqlCommand("SELECT * FROM detail", conn);  
            SqlCommand cmd = new SqlCommand("update responses set Id='" + textId.Text + "', Username ='" + textQuestionNumber.Text + "' where Id='" + userid + "'", conn);
            cmd.ExecuteNonQuery();
            conn.Close();
            gvbind3();
            //GridView3.DataBind();  
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
                conn.Open();
                SqlCommand cmd = new SqlCommand("INSERT into Users (Id, Username) VALUES ('" + new850.Text + "', '" + newName.Text + "')", conn);
                try
                {
                    cmd.ExecuteNonQuery();
                }
                catch (SqlException ex)
                {
                    newUserError.Text = ex.ToString();
                    throw;
                }
                
                conn.Close();
            }
            new850.Text = "";
            newName.Text = "";
            gvbind();
        }
    }
}