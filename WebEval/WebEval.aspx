<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebEval.aspx.cs" Inherits="WebEval.WebEval" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>UNCW WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css" />
</head>
<body style="height: 605px">
    <form id="form1" runat="server">
    <div>    
        <h1 style="text-align: left">
            <asp:Image ID="Image1" runat="server" Height="87px" ImageUrl="house_teal.gif" style="text-align: left" Width="156px" />
            <asp:Label ID="Label1" runat="server" Text="WebEval"></asp:Label>
        </h1>
        <p style="text-align: left">
            <asp:Label runat="server">UNCW Id Number</asp:Label>
            <br />
            <asp:TextBox ID="dblUNCWId" class="form-control" placeholder="Enter 850 number" runat="server"></asp:TextBox>
            <asp:Button ID="btnLoginSubmit" class="btn-success" runat="server" Text="Submit" Width="112px" OnClick="btnLoginSubmit_Click" />
        </p>
        <p style="text-align: left">

            <asp:Label ID="lblTest" runat="server" Text="This kinda Worked"></asp:Label>

        </p>
            </div>
        </form>
</body>
</html>
