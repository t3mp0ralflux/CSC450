<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AdminPage.aspx.cs" Inherits="WebEval.AdminPage" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Admin Page for WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css" />
</head>
<body style="text-align: center">
    <form id="form1" runat="server">
        <asp:ScriptManager runat="server" ID="sManager"></asp:ScriptManager>
    <div>
        <h1>Admin Page for WebEval</h1>
    </div>
        
        <div>  
           <h2>Edit Current Data:</h2> 
                <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="false" DataKeyNames="id" OnPageIndexChanging="GridView1_PageIndexChanging" OnRowCancelingEdit="GridView1_RowCancelingEdit" OnRowDeleting="GridView1_RowDeleting" OnRowEditing="GridView1_RowEditing" OnRowUpdating="GridView1_RowUpdating">  
                    <Columns>  
                        <asp:BoundField DataField="Id" HeaderText="User ID" />  
                        <asp:BoundField DataField="Username" HeaderText="User Name" />  
                        <asp:CommandField ShowEditButton="true" />  
                        <asp:CommandField ShowDeleteButton="true" /> </Columns>  
                </asp:GridView>  
            </div>  
        <div class="divider"></div>
        <div>  
                <asp:GridView ID="GridView2" runat="server" AutoGenerateColumns="false" DataKeyNames="id" OnPageIndexChanging="GridView2_PageIndexChanging" OnRowCancelingEdit="GridView2_RowCancelingEdit" OnRowDeleting="GridView2_RowDeleting" OnRowEditing="GridView2_RowEditing" OnRowUpdating="GridView2_RowUpdating">  
                    <Columns>  
                        <asp:BoundField DataField="Id" HeaderText="ID" />  
                        <asp:BoundField DataField="question" HeaderText="Question" />  
                        <asp:CommandField ShowEditButton="true" />  
                        <asp:CommandField ShowDeleteButton="true" /> </Columns>  
                </asp:GridView>  
            </div>  
        <div class="divider"></div>
        <div>  
                <asp:GridView ID="GridView3" runat="server" AutoGenerateColumns="false" DataKeyNames="id" OnPageIndexChanging="GridView3_PageIndexChanging" OnRowCancelingEdit="GridView3_RowCancelingEdit" OnRowDeleting="GridView3_RowDeleting" OnRowEditing="GridView3_RowEditing" OnRowUpdating="GridView3_RowUpdating">  
                    <Columns>  
                        <asp:BoundField DataField="Id" HeaderText="ID" />  
                        <asp:BoundField DataField="QuestionNumber" HeaderText="Question Number" />  
                        <asp:BoundField DataField="Response" HeaderText="Response" />  
                        <asp:BoundField DataField="UserName" HeaderText="Username" />  
                        <asp:CommandField ShowEditButton="true" />  
                        <asp:CommandField ShowDeleteButton="true" /> </Columns>  
                </asp:GridView>  
            </div> 
        <div class="divider"></div> 
        <h2>Add New Data:</h2>
        <div style="text-align:left;">
            Add User:
            <asp:TextBox ID="new850" placeholder="Enter 850 number" runat="server"></asp:TextBox>
            <div class="divider"></div>
            <asp:TextBox ID="newName" placeholder="Enter Username" runat="server"></asp:TextBox>
            <div class="divider"></div>

            <asp:Button ID="newUserSubmit" runat="server" Text="Submit" OnClick="newUserSubmit_Click" />
            <div class="divider"></div>
            <asp:Label ID="newUserError" runat="server" Text=""></asp:Label>
        </div>
        
    </form>
    </body>
</html>
