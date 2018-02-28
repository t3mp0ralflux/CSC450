<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebEval.aspx.cs" Inherits="WebEval.WebEval" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>UNCW WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css" />
    </head>
<body style="height: 605px">
    <form id="form1" runat="server">
    <asp:ScriptManager runat="server" ID="sManager"></asp:ScriptManager>
    <div>    
        <h1 style="text-align: left">
            <asp:Image ID="Image1" runat="server" Height="87px" ImageUrl="images/house_teal.gif" style="text-align: left" Width="156px" />
            <asp:Label ID="lblTitle" runat="server" Text="WebEval"></asp:Label>
        </h1>
        <p style="text-align: left">
            <asp:Label runat="server" ID="lblIdNumber" Visible="False">UNCW Id Number</asp:Label>
            <br />
            <asp:TextBox ID="dblUNCWId" class="form-control" placeholder="Enter 850 number" runat="server" Visible="False"></asp:TextBox>
            <asp:Button ID="btnLoginSubmit" class="btn-success" runat="server" Text="Submit" Width="112px" OnClick="btnLoginSubmit_Click" Visible="False" UseSubmitBehavior="False" />
        </p>
        <div style="width: 800px; margin:0 auto; text-align: center;">
            <asp:UpdatePanel ID="text_update" runat="server" UpdateMode="Always">
                <ContentTemplate>
            <asp:Label ID="lblQuestion" runat="server" Text="Press the right arrow to start the questionnaire."></asp:Label>
                    </ContentTemplate>
                </asp:UpdatePanel>
        </div>
        <div>
        <asp:updatepanel ID="button_update" runat="server" style="width:800px; margin:0 auto; text-align: center;" UpdateMode="Conditional">
            <ContentTemplate>
            <asp:ImageButton ID="btnLeft" UseSubmitBehavior="false" runat="server" ImageUrl="images/left_arrow.png" Height="77px" Width="173px" OnClick="left_arrow_Click"/>
            <div class="divider"></div>

            <asp:ImageButton ID="btnRight" UseSubmitBehavior="false" runat="server" Height="77px" ImageUrl="images/right_arrow.png" Width="173px" OnClick="right_arrow_Click" />

                <br />
                <asp:ImageButton ID="btnRecStop" runat="server" Height="70px" ImageUrl="images/record_button.png" Width="70px" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnPlayPause" runat="server" Height="70px" ImageUrl="images/play_button.png" Width="70px" />

                </ContentTemplate>
            </asp:updatepanel>
            </div>
    </div>

</form>
</body>
</html>
